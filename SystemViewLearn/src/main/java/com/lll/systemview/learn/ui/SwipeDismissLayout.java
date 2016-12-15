package com.lll.systemview.learn.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.FrameLayout;

/**
 * Version 1.0
 * Created by lll on 16/12/14.
 * Description 滑动finish activity
 * copyright generalray4239@gmail.com
 */

public class SwipeDismissLayout extends FrameLayout {

    private static final float DISMISS_MIN_DRAG_WIDTH_RATIO = .33f;
    private boolean mUseDynamicTranslucency = true;

    /*手指按下位置*/
    private float mDownX;
    private float mDownY;
    private float mLastX;//最近一次X的位置

    //默认多少像素为滑动
    private int mSlop;
    private int mMinFlingVelocity;

    /*滑动状态*/
    private boolean mSwiping;
    private boolean mFinished;
    private boolean mDiscardIntercept;

    /*x方向的滑动距离*/
    private float mTranslationX;

    private VelocityTracker mVelocityTracker;
    private int mActiveTouchId;/*有效手指*/

    private OnSwipeStateChangeListener mOnSwipeStateChangeListener;

    private IntentFilter filter = new IntentFilter(Intent.ACTION_SCREEN_OFF);
    /**
     * 注册屏幕锁屏广播
     */
    private BroadcastReceiver mScreenOffReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (mFinished) {
                finished();
            } else {
                cancel();
            }
            resetMembers();
        }
    };

    public interface OnSwipeStateChangeListener {
        /**
         * 开始
         *
         * @param layout
         */
        void onSwipeStart(SwipeDismissLayout layout);

        /**
         * 滑动进度
         *
         * @param layout
         * @param progress  progress A number in [0, 1] representing how far to the
         *                  right the window has been swiped
         * @param translate translate A number in [0, w], where w is the width of the
         *                  layout. This is equivalent to progress * layout.getWidth().
         */
        void onSwipeProgressChanged(SwipeDismissLayout layout, float progress, float translate);

        /**
         * 结束
         *
         * @param layout
         */
        void onSwipeFinished(SwipeDismissLayout layout);

        /**
         * 滑动取消
         *
         * @param layout
         */
        void onSwipeCancelled(SwipeDismissLayout layout);
    }


    public SwipeDismissLayout(Context context) {
        this(context, null);
    }

    public SwipeDismissLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SwipeDismissLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        ViewConfiguration compat = ViewConfiguration.get(context);
        mSlop = compat.getScaledTouchSlop();
        mMinFlingVelocity = compat.getScaledMinimumFlingVelocity();
//        TypedArray array = context.getTheme().obtainStyledAttributes(com.android.internal.R.styleable.Theme);
//        mUseDynamicTranslucency = !array.hasValue(
//                com.android.internal.R.styleable.Window_windowIsTranslucent);
//        array.recycle();
    }

    public void setOnSwipeStateChangeListener(OnSwipeStateChangeListener listener) {
        this.mOnSwipeStateChangeListener = listener;
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        getContext().registerReceiver(mScreenOffReceiver, filter);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        getContext().unregisterReceiver(mScreenOffReceiver);
    }

    private void finished() {
        if (mOnSwipeStateChangeListener != null) {
            mOnSwipeStateChangeListener.onSwipeFinished(this);
        }
    }

    private void cancel() {
        if (mOnSwipeStateChangeListener != null) {
            mOnSwipeStateChangeListener.onSwipeCancelled(this);
        }
    }

    private void resetMembers() {
        if (mVelocityTracker != null) {
            mVelocityTracker.recycle();
        }
        mVelocityTracker = null;
        mTranslationX = 0;
        mDownX = 0;
        mDownY = 0;
        mSwiping = false;
        mFinished = false;
        mDiscardIntercept = false;
    }

    private void setProgress(float deltaX) {
        mTranslationX = deltaX;
        if (mOnSwipeStateChangeListener != null && deltaX > 0) {
            mOnSwipeStateChangeListener.onSwipeProgressChanged(this, deltaX / getWidth(), deltaX);
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }

    /**
     * 事件是否传递给孩子
     *
     * @param ev
     * @return
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        ev.offsetLocation(mTranslationX, 0);
        switch (ev.getActionMasked()) {

            case MotionEvent.ACTION_DOWN:
                resetMembers();
                mDownX = ev.getRawX();
                mDownY = ev.getRawY();
                mActiveTouchId = ev.getPointerId(0);
                mVelocityTracker = VelocityTracker.obtain();
                mVelocityTracker.addMovement(ev);
                break;
            case MotionEvent.ACTION_MOVE:
                if (mVelocityTracker == null || mDiscardIntercept) {//不拦截
                    break;
                }
                int pointerIndex = ev.findPointerIndex(mActiveTouchId);
                if (pointerIndex == -1) {//invalid pointer
                    mDiscardIntercept = true;
                    break;
                }
                //计算x方向的偏移
                float dx = ev.getRawX() - mDownX;
                float x = ev.getX(mActiveTouchId);
                float y = ev.getY(mActiveTouchId);
                if (dx != 0 && canScroll(this, false, dx, x, y)) {
                    mDiscardIntercept = true;
                    break;
                }
                updateSwiping(ev);
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                Log.e("lll", "---pointer count---" + ev.getPointerCount());
                int actionIndex = ev.getActionIndex();
                mActiveTouchId = ev.getPointerId(actionIndex);
                break;
            case MotionEvent.ACTION_POINTER_UP:
                actionIndex = ev.getActionIndex();
                int pointerId = ev.getPointerId(actionIndex);
                if (pointerId == mActiveTouchId) {
                    int newActionIndex = actionIndex == 0 ? 1 : 0;
                    mActiveTouchId = ev.getPointerId(newActionIndex);
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                resetMembers();
                break;
        }

        return !mDiscardIntercept && mSwiping;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (mVelocityTracker == null) {
            return super.onTouchEvent(ev);
        }
        ev.offsetLocation(mTranslationX, 0);
        switch (ev.getActionMasked()) {
            case MotionEvent.ACTION_MOVE:
                mVelocityTracker.addMovement(ev);
                mLastX = ev.getRawX();
                updateSwiping(ev);
                if(mSwiping){
                    setProgress(ev.getRawX()-mDownX);
                }
                break;
            case MotionEvent.ACTION_UP:
                updateFinished(ev);
                if(mFinished){
                    finished();
                }else if(mSwiping){
                    cancel();
                }
                resetMembers();
                break;
            case MotionEvent.ACTION_CANCEL:
                cancel();
                resetMembers();
                break;
        }
        return true;
    }

    private void updateFinished(MotionEvent ev){
         float deltaX = ev.getRawX()-mDownX;
        mVelocityTracker.addMovement(ev);
        mVelocityTracker.computeCurrentVelocity(1000);
        if(!mFinished){
            if (deltaX > (getWidth() * DISMISS_MIN_DRAG_WIDTH_RATIO) &&
                    ev.getRawX() >= mLastX) {
                mFinished = true;
            }
        }
        if(mFinished && mSwiping){
            // Check if the user's finger is actually back
            if (deltaX < (getWidth() * DISMISS_MIN_DRAG_WIDTH_RATIO) ||
                    // or user is flinging back left
                    mVelocityTracker.getXVelocity() < -mMinFlingVelocity) {
                mFinished = false;
            }
        }
    }

    /**
     * @param v
     * @param checkV Whether the view v passed should itself be checked for scrollability (true),
     *               or just its children (false).
     * @param dx
     * @param x
     * @param y
     * @return true if child views of v can be scrolled by delta of dx.
     */
    private boolean canScroll(View v, boolean checkV, float dx, float x, float y) {
        if (v instanceof ViewGroup) {
            final ViewGroup group = (ViewGroup) v;
            final int scrollX = group.getScrollX();
            final int scrollY = group.getScrollY();
            final int count = group.getChildCount();
            for (int i = 0; i < count; i++) {
                final View view = group.getChildAt(i);
                if (x + scrollX >= view.getLeft() && x + scrollX < view.getRight() &&
                        y + scrollY >= view.getTop() && y + scrollY < view.getBottom() &&
                        canScroll(view, true, dx, x + scrollX - view.getLeft(), y + scrollY - view.getTop())) {
                    return true;
                }
            }
        }
        return checkV && v.canScrollHorizontally((int) -dx);
    }

    private void updateSwiping(MotionEvent ev) {
        if (!mSwiping) {
            float deltaX = ev.getRawX() - mDownX;
            float deltaY = ev.getRawY() - mDownY;
            if ((deltaX * deltaX) + (deltaY * deltaY) > mSlop * mSlop) {
                mSwiping = deltaX > mSlop * 2 && Math.abs(deltaY) < Math.abs(deltaX);
            } else {
                mSwiping = false;
            }
        }
    }
}
