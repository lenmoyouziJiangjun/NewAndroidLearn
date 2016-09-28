package com.lll.learn.recycleView;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Version 1.0
 * Created by lll on 16/9/23.
 * Description RecycleView的提示文字框
 * copyright generalray4239@gmail.com
 */

public class FastScroller extends LinearLayout {

    private static final int BUBBLE_ANIMATION_DURATION = 100;
    private static final int TRACK_SNAP_RANGE = 5;

    /*气泡文字*/
    private TextView mBubble;
    /*滑动框*/
    private View mHandle;

    /*显示隐藏动画*/
    private AnimatorSet mAnimatorSet;
    /*滑动工具类*/
    private ViewDragHelper mDragHelper;


    public FastScroller(Context context) {
        super(context);
    }

    public FastScroller(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FastScroller(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    /**
     * 显示文字
     */
    private void showBubble() {
        if (mAnimatorSet != null) {
            if (mAnimatorSet.isRunning()) {
                mAnimatorSet.cancel();
            }
        } else {
            mAnimatorSet = new AnimatorSet();
        }
        ObjectAnimator alphaA = new ObjectAnimator().ofFloat(mBubble, "alpha", 0f, 1f).setDuration(BUBBLE_ANIMATION_DURATION);
        ObjectAnimator xA = new ObjectAnimator().ofFloat(mBubble, "x", mBubble.getRight(), mBubble.getLeft()).setDuration(BUBBLE_ANIMATION_DURATION);
        mAnimatorSet.playSequentially(alphaA, xA);
    }

    /**
     * 隐藏气泡文字
     */
    private void hideBubble() {
        if (mAnimatorSet != null) {
            if (mAnimatorSet.isRunning()) {
                mAnimatorSet.cancel();
            }
        } else {
            mAnimatorSet = new AnimatorSet();
        }
        ObjectAnimator alphaA = new ObjectAnimator().ofFloat(mBubble, "alpha", 1f, 0f).setDuration(BUBBLE_ANIMATION_DURATION);
        ObjectAnimator xA = new ObjectAnimator().ofFloat(mBubble, "x", mBubble.getLeft(), mBubble.getRight()).setDuration(BUBBLE_ANIMATION_DURATION);
        mAnimatorSet.playSequentially(xA, alphaA);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_MOVE:
                break;
        }
        return false;
    }

    /*移动滑动条的位置位置*/
    private void moveHandle(float y){

    }

    /*移动*/
    private void moveBubble(float y){

    }
}
