package com.lll.bitmaploader.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.widget.ImageView;

import com.lll.bitmaploader.cache.ImageCache;
import com.lll.bitmaploader.view.RecyclingBitmapDrawable;

import java.lang.ref.WeakReference;

/**
 * Version 1.0
 * Created by lll on 16/7/14.
 * Description 处理图片的入口(加载图片,默认图片等处理)
 * copyright generalray4239@gmail.com
 */
public abstract class ImageWorker {

    private static final int FADE_IN_TIME = 20;
    private ImageCache mImageCache;
    private ImageCache.ImageCacheParams mImageCacheParams;
    /*placeHolder image*/
    private Bitmap mLoadingBitmap;
    private boolean mFadeInBitmap = true;
    private boolean mExitTasksEarly = false;
    protected boolean mPauseWork = false;
    private final Object mPauseWorkLock = new Object(); protected Resources mResources;

    private static final int MESSAGE_CLEAR = 0;
    private static final int MESSAGE_INIT_DISK_CACHE = 1;
    private static final int MESSAGE_FLUSH = 2;
    private static final int MESSAGE_CLOSE = 3;

    /**
     * 图片加载的回调
     */
    public interface OnImageLoadedListener {
        void onImageLoaded(boolean success);
    }

    protected ImageWorker(Context ctx) {
        mResources = ctx.getResources();
    }

    /**
     * 加载图片
     * 加载图片的步骤:ImageCache->>DiskCache -->>网络
     *
     * @param data      图片路径
     * @param imageView
     * @param listener
     */
    public void loadImage(Object data, ImageView imageView, OnImageLoadedListener listener) {
        if (data == null) {
            return;
        }
        BitmapDrawable drawable = null;
        if (mImageCache != null) {
            drawable = mImageCache.getBitmapFromMemCache(String.valueOf(data));
        }

        if (drawable != null) {
            imageView.setImageDrawable(drawable);
            if (listener != null) {
                listener.onImageLoaded(true);
            }
        } else if (cancelPotentialWork(data, imageView)) {
            final BitmapWorkerTask task = new BitmapWorkerTask(data, imageView, listener);
            final AsyncDrawable asyncDrawable =
                    new AsyncDrawable(mResources, mLoadingBitmap, task);
            imageView.setImageDrawable(asyncDrawable);

            // NOTE: This uses a custom version of AsyncTask that has been pulled from the
            // framework and slightly modified. Refer to the docs at the top of the class
            // for more info on what was changed.
            task.executeOnExecutor(AsyncTask.DUAL_THREAD_EXECUTOR);
        }
    }

    public void loadImage(Object data, ImageView imageView) {
        this.loadImage(data, imageView, null);
    }


    /**
     * Set placeholder bitmap that shows when the the background thread is running.
     *
     * @param bitmap
     */
    public void setLoadingImage(Bitmap bitmap) {
        mLoadingBitmap = bitmap;
    }

    /**
     * Set placeholder bitmap that shows when the the background thread is running.
     *
     * @param resId
     */
    public void setLoadingImage(int resId) {
        mLoadingBitmap = BitmapFactory.decodeResource(mResources, resId);
    }

    public void addImageCache(FragmentManager manager, ImageCache.ImageCacheParams params) {
        mImageCacheParams = params;
        mImageCache = ImageCache.getInstance(manager, params);
        new CacheAsyncTask().execute(MESSAGE_INIT_DISK_CACHE);
    }

    /**
     * Adds an {@link ImageCache} to this {@link ImageWorker} to handle disk and memory bitmap
     * caching.
     *
     * @param activity
     * @param diskCacheDirectoryName See
     *                               {@link ImageCache.ImageCacheParams#ImageCacheParams(android.content.Context, String)}.
     */
    public void addImageCache(FragmentActivity activity, String diskCacheDirectoryName) {
        mImageCacheParams = new ImageCache.ImageCacheParams(activity, diskCacheDirectoryName);
        mImageCache = ImageCache.getInstance(activity.getSupportFragmentManager(), mImageCacheParams);
        new CacheAsyncTask().execute(MESSAGE_INIT_DISK_CACHE);
    }

    public void setImageFadeIn(boolean fadeIn) {
        mFadeInBitmap = fadeIn;
    }

    public void setExitTasksEarly(boolean exitTasksEarly) {
        mExitTasksEarly = exitTasksEarly;
        setPauseWork(false);
    }

    protected abstract Bitmap processBitmap(Object data);

    /**
     * @return The {@link ImageCache} object currently being used by this ImageWorker.
     */
    protected ImageCache getImageCache() {
        return mImageCache;
    }

    public static void cancelWork(ImageView imageView) {
        final BitmapWorkerTask task = getBitmapWorkerTask(imageView);
        if (task != null) {
            task.cancel(true);
        }
    }

    /**
     * Returns true if the current work has been canceled or if there was no work in
     * progress on this image view.
     * @param data
     * @param imageView
     * @return
     */
    public static boolean cancelPotentialWork(Object data, ImageView imageView) {
        final BitmapWorkerTask task = getBitmapWorkerTask(imageView);
        if(task!=null){
            final Object bitmapData = task.mData;
            if(bitmapData ==null ||!bitmapData.equals(data)){
                task.cancel(true);
            }else{
                return false;
            }
        }
        return true;
    }

    /**
     *
     * @param imageView
     * @return
     */
    private static BitmapWorkerTask getBitmapWorkerTask(ImageView imageView){
        if (imageView != null) {
            final Drawable drawable = imageView.getDrawable();
            if (drawable instanceof AsyncDrawable) {
                final AsyncDrawable asyncDrawable = (AsyncDrawable) drawable;
                return asyncDrawable.getBitmapWorkerTask();
            }
        }
        return null;
    }


    /**
     * 图片加载task
     */
    private class BitmapWorkerTask extends AsyncTask<Void, Void, BitmapDrawable> {
        private Object mData;
        private final WeakReference<ImageView> imageViewReference;
        private final OnImageLoadedListener mOnImageLoadedListener;

        public BitmapWorkerTask(Object data, ImageView imageView) {
            mData = data;
            imageViewReference = new WeakReference<ImageView>(imageView);
            mOnImageLoadedListener = null;
        }

        public BitmapWorkerTask(Object data, ImageView imageView, OnImageLoadedListener listener) {
            mData = data;
            imageViewReference = new WeakReference<ImageView>(imageView);
            mOnImageLoadedListener = listener;
        }

        @Override
        protected BitmapDrawable doInBackground(Void... voids) {
            final String dataString = String.valueOf(mData);
            Bitmap bitmap = null;
            BitmapDrawable drawable = null;
            synchronized (mPauseWorkLock) {
                while (mPauseWork && !isCancelled()) {
                    try {
                        mPauseWorkLock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            if (mImageCache != null && isCancelled() && getAttachedImageView() != null
                    && !mExitTasksEarly) {
                bitmap = mImageCache.getBitmapFromDiskCache(dataString);
            }

            //没有获取到图片,放到子类去实现加载图片的逻辑
            if (bitmap == null && !isCancelled() && getAttachedImageView() != null
                    && !mExitTasksEarly) {
                bitmap = processBitmap(mData);
            }

            if (bitmap != null) {
                if (Utils.hasHoneycomb()) {
                    // Running on Honeycomb or newer, so wrap in a standard BitmapDrawable
                    drawable = new BitmapDrawable(mResources, bitmap);
                } else {
                    // Running on Gingerbread or older, so wrap in a RecyclingBitmapDrawable
                    // which will recycle automagically
                    drawable = new RecyclingBitmapDrawable(mResources, bitmap);
                }
                if (mImageCache != null) {
                    mImageCache.addBitmapToCache(dataString, drawable);
                }
            }

            return drawable;
        }

        @Override
        protected void onPostExecute(BitmapDrawable drawable) {
            boolean success = false;
            if (isCancelled() || mExitTasksEarly) {//已经取消或退出了
                drawable = null;
            }
            final ImageView imageView = getAttachedImageView();
            if (drawable != null && imageView != null) {
                success = true;
                setImageDrawable(imageView, drawable);
            }

            if (mOnImageLoadedListener != null) {
                mOnImageLoadedListener.onImageLoaded(success);
            }
        }

        @Override
        protected void onCancelled(BitmapDrawable drawable) {
            super.onCancelled(drawable);
            synchronized (mPauseWorkLock) {
                mPauseWorkLock.notifyAll();
            }
        }

        /**
         * @return
         */
        private ImageView getAttachedImageView() {
            final ImageView imageView = imageViewReference.get();
            final BitmapWorkerTask bitmapWorkerTask = getBitmapWorkerTask(imageView);

            if (this == bitmapWorkerTask) {
                return imageView;
            }
            return null;
        }
    }

    /**
     * A custom Drawable that will be attached to the imageView while the work is in progress.
     * Contains a reference to the actual worker task, so that it can be stopped if a new binding is
     * required, and makes sure that only the last started worker process can bind its result,
     * independently of the finish order.
     */
    private static class AsyncDrawable extends BitmapDrawable {
        private final WeakReference<BitmapWorkerTask> bitmapWorkerTaskReference;

        public AsyncDrawable(Resources resources, Bitmap bitmap, BitmapWorkerTask bitmapWorkerTask) {
            super(resources, bitmap);
            bitmapWorkerTaskReference = new WeakReference<BitmapWorkerTask>(bitmapWorkerTask);
        }

        public BitmapWorkerTask getBitmapWorkerTask() {
            return bitmapWorkerTaskReference.get();
        }
    }


    private void setImageDrawable(ImageView imgageView, Drawable drawable) {
        if (mFadeInBitmap) {//有过度效果
            //定义一个TransitionDrawable对象
            final TransitionDrawable transitionDrawable = new TransitionDrawable(new Drawable[]{
                    new ColorDrawable(Color.TRANSPARENT),
                    drawable
            });
            //设置holderDrawable
            imgageView.setBackgroundDrawable(new BitmapDrawable(mResources, mLoadingBitmap));
            //给控件设置drawable
            imgageView.setImageDrawable(transitionDrawable);
            //20毫秒后像前转换
            transitionDrawable.startTransition(FADE_IN_TIME);
            //20毫秒后向后转换
//            transitionDrawable.reverseTransition(FADE_IN_TIME);
        } else {
            imgageView.setImageDrawable(drawable);
        }
    }

    /**
     * Pause any ongoing background work. This can be used as a temporary
     * measure to improve performance. For example background work could
     * be paused when a ListView or GridView is being scrolled using a
     * {@link android.widget.AbsListView.OnScrollListener} to keep
     * scrolling smooth.
     * 在滑动的时候暂停加载,
     * 一旦调用了setPauseWork(true)就需要在activity和Fragment的生命周期介绍是一定要调用setPauseWork(false)
     * 否则后台线程一直运行there is a risk the background thread will never finish.
     *
     * @param pause
     */
    public void setPauseWork(boolean pause) {
        synchronized (mPauseWorkLock) {
            mPauseWork = pause;
            if (!mPauseWork) {
                mPauseWorkLock.notifyAll();
            }
        }
    }

    public void clearCache() {
        new CacheAsyncTask().execute(MESSAGE_CLEAR);
    }

    public void flushCache() {
        new CacheAsyncTask().execute(MESSAGE_FLUSH);
    }

    public void closeCache() {
        new CacheAsyncTask().execute(MESSAGE_CLOSE);
    }

    /**
     * 异步操作cache的处理
     */
    protected class CacheAsyncTask extends AsyncTask<Object, Void, Void> {

        @Override
        protected Void doInBackground(Object... params) {
            switch ((Integer) params[0]) {
                case MESSAGE_CLEAR:
                    clearCacheInternal();
                    break;
                case MESSAGE_INIT_DISK_CACHE:
                    initDiskCacheInternal();
                    break;
                case MESSAGE_FLUSH:
                    flushCacheInternal();
                    break;
                case MESSAGE_CLOSE:
                    closeCacheInternal();
                    break;
            }
            return null;
        }
    }

    protected void initDiskCacheInternal() {
        if (mImageCache != null) {
            mImageCache.initDiskCache();
        }
    }

    protected void clearCacheInternal() {
        if (mImageCache != null) {
            mImageCache.clearCache();
        }
    }

    protected void flushCacheInternal() {
        if (mImageCache != null) {
            mImageCache.flush();
        }
    }

    protected void closeCacheInternal() {
        if (mImageCache != null) {
            mImageCache.close();
        }
    }


}
