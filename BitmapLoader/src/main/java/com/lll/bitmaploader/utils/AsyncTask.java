package com.lll.bitmaploader.utils;

import android.os.Handler;
import android.os.Message;
import android.os.Process;

import java.util.ArrayDeque;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Version 1.0
 * Created by lll on 16/7/22.
 * Description 自定义一个AsyncTask 实现Android原生AsyncTask类似功能。
 * 参考系统源码https://android.googlesource.com/platform/frameworks/base/+/jb-release/core/java/android/os/AsyncTask.java
 * <p>
 * copyright generalray4239@gmail.com
 */
public abstract class AsyncTask<Params, Progress, Result> {

    /*线程池属性*/
    private static final int CORE_POOL_SIZE = 5;
    private static final int MAXIMUM_POOL_SIZE = 128;
    private static final int KEEP_ALIVE = 1;//只保持一个线程下载

    /*消息*/
    private static final int MESSAGE_POST_RESULT = 0X11;
    private static final int MESSAGE_POST_PROGRESS = 0X12;

    /**
     * 线程状态,将枚举改为常量字符串
     */
    public enum Status {
        /**
         * Indicates that the task has not been executed yet.
         */
        PENDING,
        /**
         * Indicates that the task is running.
         */
        RUNNING,
        /**
         * Indicates that {@link AsyncTask#onPostExecute} has finished.
         */
        FINISHED,
    }

    /**
     * tip1:自定义线程的时候,最好跟每个线程取一个名字,方便调试
     */
    private static final ThreadFactory sThreadFactory = new ThreadFactory() {
        private final AtomicInteger mCount = new AtomicInteger(1);

        @Override
        public Thread newThread(Runnable runnable) {
            return new Thread(runnable, "AsyncTask *" + mCount.getAndIncrement());
        }
    };


    /**
     * 创建一个阻塞队列,队列大小为10
     */
    private static final BlockingDeque<Runnable> sPoolWorkQueue = new LinkedBlockingDeque<>(10);

    /**
     * 创建线程缓冲池
     */
    public static final Executor THREAD_POOL_EXECUTOR = new ThreadPoolExecutor(CORE_POOL_SIZE, MAXIMUM_POOL_SIZE, KEEP_ALIVE, TimeUnit.SECONDS, sPoolWorkQueue,
            sThreadFactory, new ThreadPoolExecutor.DiscardPolicy());

    /**
     * 串行消息线程池
     */
    public static final Executor SERIAL_EXECUTOR = Utils.hasHoneycomb() ? new SerialExecutor() :
            Executors.newSingleThreadExecutor(sThreadFactory);

    /**
     * 双重消息池
     */
    public static final Executor DUAL_THREAD_EXECUTOR = Executors.newFixedThreadPool(2, sThreadFactory);


    private static final InternalHandler sHandler = new InternalHandler();

    /*默认的线程池*/
    private static volatile Executor sDefaultExecutor = SERIAL_EXECUTOR;

    private final WorkerRunnable<Params, Result> mWorker;

    private final FutureTask<Result> mFuture;

    private volatile Status mStatus = Status.PENDING;

    //取消
    private final AtomicBoolean mCancelled = new AtomicBoolean();
    //激活
    private final AtomicBoolean mTaskInvoked = new AtomicBoolean();


    /**
     * @hide Used to force static handler to be created.
     */
    public static void init() {
        sHandler.getLooper();
    }

    /**
     * @hide
     */
    public static void setDefaultExecutor(Executor exec) {
        sDefaultExecutor = exec;
    }

    /**
     *
     */
    public AsyncTask() {
        mWorker = new WorkerRunnable<Params, Result>() {
            @Override
            public Result call() throws Exception {
                mTaskInvoked.set(true);
                Process.setThreadPriority(Process.THREAD_PRIORITY_BACKGROUND);
                /**
                 * 将doInBackground执行的结果传递回去
                 */
                return postResult(doInBackground(mParams));
            }
        };

        mFuture = new FutureTask<Result>(mWorker) {
            @Override
            protected void done() {
                try {
                    postResultIfNotInvoked(get());
                } catch (InterruptedException e) {
                    android.util.Log.w("lll", e);
                } catch (ExecutionException e) {
                    throw new RuntimeException("An error occured while executing doInBackground()",
                            e.getCause());
                } catch (CancellationException e) {
                    postResultIfNotInvoked(null);
                }
            }
        };
    }

    private void postResultIfNotInvoked(Result result) {
        final boolean wasTaskInvoked = mTaskInvoked.get();
        if (!wasTaskInvoked) {
            postResult(result);
        }
    }

    /**
     * 返回结果
     *
     * @param result 传递过来的result类型
     * @return
     */
    private Result postResult(Result result) {
        Message message = sHandler.obtainMessage(MESSAGE_POST_RESULT,
                new AsyncTaskResult<Result>(this, result));
        message.sendToTarget();
        return result;
    }

    public final Status getStatus() {
        return mStatus;
    }

    /**
     * 子类必须实现的方法
     *
     * @param params
     * @return
     */
    protected abstract Result doInBackground(Params... params);

    /**
     * Runs on the UI thread before {@link #doInBackground}.
     */
    protected void onPreExecute() {

    }

    /**
     * <p>Runs on the UI thread after {@link #doInBackground}. The
     * specified result is the value returned by {@link #doInBackground}.</p>
     * <p>
     * <p>This method won't be invoked if the task was cancelled.</p>
     *
     * @param result The result of the operation computed by {@link #doInBackground}.
     * @see #onPreExecute
     * @see #doInBackground
     * @see #onCancelled(Object)
     */
    @SuppressWarnings({"UnusedDeclaration"})
    protected void onPostExecute(Result result) {
    }

    /**
     * Runs on the UI thread after {@link #} is invoked.
     * The specified values are the values passed to {@link #}.
     *
     * @param values The values indicating progress.
     * @see #
     * @see #doInBackground
     */
    @SuppressWarnings({"UnusedDeclaration"})
    protected void onProgressUpdate(Progress... values) {
    }

    @SuppressWarnings({"UnusedParameters"})
    protected void onCancelled(Result result) {
        onCancelled();
    }

    /**
     * <p>Applications should preferably override {@link #onCancelled(Object)}.
     * This method is invoked by the default implementation of
     * {@link #onCancelled(Object)}.</p>
     * <p>
     * {@link #doInBackground(Object[])} has finished.</p>
     *
     * @see #onCancelled(Object)
     * @see #isCancelled()
     */
    protected void onCancelled() {
    }

    /**
     * Returns <tt>true</tt> if this task was cancelled before it completed
     * normally. If you are calling {#cancel(boolean)} on the task,
     * the value returned by this method should be checked periodically from
     * {@link #doInBackground(Object[])} to end the task as soon as possible.
     *
     * @return <tt>true</tt> if task was cancelled before it completed
     * @see (boolean)
     */
    public final boolean isCancelled() {
        return mCancelled.get();
    }

    /**
     * <p>Attempts to cancel execution of this task.  This attempt will
     * fail if the task has already completed, already been cancelled,
     * or could not be cancelled for some other reason. If successful,
     * and this task has not started when <tt>cancel</tt> is called,
     * this task should never run. If the task has already started,
     * then the <tt>mayInterruptIfRunning</tt> parameter determines
     * whether the thread executing this task should be interrupted in
     * an attempt to stop the task.</p>
     *
     * @param mayInterruptIfRunning
     * @return
     */
    public final boolean cancel(boolean mayInterruptIfRunning) {
        mCancelled.set(true);
        return mFuture.cancel(mayInterruptIfRunning);
    }

    /**
     * Waits if necessary for the computation to complete, and then
     * retrieves its result.
     *
     * @return
     * @throws Exception
     */
    public final Result get() throws Exception {
        return mFuture.get();
    }

    public final Result get(long timeout, TimeUnit unit) throws InterruptedException,
            ExecutionException, TimeoutException {
        return mFuture.get(timeout, unit);
    }

    /**
     * * Executes the task with the specified parameters. The task returns
     * itself (this) so that the caller can keep a reference to it.
     * <p>
     * <p>Note: this function schedules the task on a queue for a single background
     * thread or pool of threads depending on the platform version.  When first
     * introduced, AsyncTasks were executed serially on a single background thread.
     * Starting with {@link android.os.Build.VERSION_CODES#DONUT}, this was changed
     * to a pool of threads allowing multiple tasks to operate in parallel. Starting
     * {@link android.os.Build.VERSION_CODES#HONEYCOMB}, tasks are back to being
     * executed on a single thread to avoid common application errors caused
     * by parallel execution.  If you truly want parallel execution, you can use
     * the {@link #executeOnExecutor} version of this method
     * with {@link #THREAD_POOL_EXECUTOR}; however, see commentary there for warnings
     * on its use.
     * <p>
     * 必须在主线程中调用
     *
     * @param params
     * @return
     */
    public final AsyncTask<Params, Progress, Result> execute(Params... params) {
        return executeOnExecutor(sDefaultExecutor, params);
    }

    /**
     * * Executes the task with the specified parameters. The task returns
     * itself (this) so that the caller can keep a reference to it.
     * <p>
     * <p>This method is typically used with {@link #THREAD_POOL_EXECUTOR} to
     * allow multiple tasks to run in parallel on a pool of threads managed by
     * AsyncTask, however you can also use your own {@link java.util.concurrent.Executor} for custom
     * behavior.
     * <p>
     * <p><em>Warning:</em> Allowing multiple tasks to run in parallel from
     * a thread pool is generally <em>not</em> what one wants, because the order
     * of their operation is not defined.  For example, if these tasks are used
     * to modify any state in common (such as writing a file due to a button click),
     * there are no guarantees on the order of the modifications.
     * Without careful work it is possible in rare cases for the newer version
     * of the data to be over-written by an older one, leading to obscure data
     * loss and stability issues.  Such changes are best
     * executed in serial; to guarantee such work is serialized regardless of
     * platform version you can use this function with {@link #SERIAL_EXECUTOR}.
     * <p>
     * <p>This method must be invoked on the UI thread.
     *
     * @param exec
     * @param params
     * @return
     */
    public final AsyncTask<Params, Progress, Result> executeOnExecutor(Executor exec,
                                                                      Params... params) {
        if (mStatus != Status.PENDING) {
            switch (mStatus) {
                case RUNNING:
                    throw new IllegalStateException("Cannot execute task:"
                            + " the task is already running.");
                case FINISHED:
                    throw new IllegalStateException("Cannot execute task:"
                            + " the task has already been executed "
                            + "(a task can be executed only once)");
            }
        }
        //状态改为正在运行
        mStatus = Status.RUNNING;
        onPreExecute();//前面执行
        mWorker.mParams = params;
        exec.execute(mFuture);
        return this;
    }

    /**
     * Convenience version of {@link #execute(Object...)} for use with
     * a simple Runnable object. See {@link #execute(Object[])} for more
     * information on the order of execution.
     * @param runnable
     */
    public static void execute(Runnable runnable){
        sDefaultExecutor.execute(runnable);
    }

    /**
     * This method can be invoked from {@link #doInBackground} to
     * publish updates on the UI thread while the background computation is
     * still running. Each call to this method will trigger the execution of
     *
     * 子线程想跟主线程通信,只能通过handler来实现
     *
     * {@link #onProgressUpdate} on the UI thread.
     * @param values
     */
    protected  final void publishProgress(Progress ... values){
        if(!isCancelled()){
            sHandler.obtainMessage(MESSAGE_POST_PROGRESS,new AsyncTaskResult<Progress>(this, values)).sendToTarget();
        }
    }

    /**
     *
     * @param result
     */
    private void finish(Result result){
        if (isCancelled()) {
            onCancelled(result);
        } else {
            onPostExecute(result);
        }
        mStatus = Status.FINISHED;
    }

    /**
     * 串行Executor
     */
    private static class SerialExecutor implements Executor {
        final ArrayDeque<Runnable> mTasks = new ArrayDeque<>();
        Runnable mActive;//正在执行的任务

        @Override
        public synchronized void execute(final Runnable runnable) {
            mTasks.offer(new Runnable() {
                @Override
                public void run() {
                    try {
                        runnable.run();
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        scheduleNext();
                    }
                }
            });
            if (mActive == null) {
                scheduleNext();
            }
        }

        protected synchronized void scheduleNext() {
            if ((mActive = mTasks.poll()) != null) {
                THREAD_POOL_EXECUTOR.execute(mActive);
            }
        }
    }


    private static class InternalHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            AsyncTaskResult result = (AsyncTaskResult) msg.obj;
            switch (msg.what) {
                case MAXIMUM_POOL_SIZE:
                    result.mTask.finish(result.mData[0]);
                    break;
                case MESSAGE_POST_PROGRESS:
                    result.mTask.onProgressUpdate(result.mData);
                    break;
            }
        }
    }


    private static abstract class WorkerRunnable<Params, Result> implements Callable<Result> {
        Params[] mParams;
    }

    private static class AsyncTaskResult<Data> {
        final AsyncTask mTask;
        final Data[] mData;

        AsyncTaskResult(AsyncTask task, Data... data) {
            mTask = task;
            mData = data;
        }
    }

}
