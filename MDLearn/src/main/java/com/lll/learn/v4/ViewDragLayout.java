package com.lll.learn.v4;

import android.content.Context;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

//import com.lll.learn.R;

/**
 * Version 1.0
 * Created by lll on 16/9/27.
 * Description ViewDragHelper学习
 * copyright generalray4239@gmail.com
 */

public class ViewDragLayout extends RelativeLayout {

    private ViewDragHelper mDragHelper;

    private View mDragView;

    public ViewDragLayout(Context context) {
        this(context,null);
    }

    public ViewDragLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ViewDragLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initViewDragHelper();
    }


    private void initViewDragHelper(){
        mDragHelper = ViewDragHelper.create(this,1.5f,new DragHelperCallBack());
        mDragHelper.setMinVelocity(getContext().getResources().getDisplayMetrics().density);
    }


    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if(ev.getAction()==MotionEvent.ACTION_CANCEL){
            if(mDragHelper!=null){
                mDragHelper.cancel();
            }
        }
        return mDragHelper.shouldInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mDragHelper.processTouchEvent(event);
        return true;
    }

    /**
     *
     */
    private static class DragHelperCallBack extends ViewDragHelper.Callback{

        /*return true 才会拖动*/
        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            return true;
        }

        /**
         * 横向拖动
         * @param child
         * @param left
         * @param dx
         * @return
         */
        public int clampViewPositionHorizontal(View child, int left, int dx) {
            return left;
        }

        /**
         * 纵向拖动
         * @param child
         * @param top
         * @param dy
         * @return
         */
        public int clampViewPositionVertical(View child, int top, int dy) {
            return 0;
        }
    }
}
