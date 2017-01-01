package com.lll.common.widget;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Version 1.0
 * Created by lll on 17/1/1.
 * Description 广告banner效果的ViewPager.
 * copyright generalray4239@gmail.com
 */
public class BannerViewPager extends IndicatorViewPager {

    private static final int ACTION_MOVE = 0X10;

    /**
     * 滑动到下一个的handle
     */
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            int item = getCurrentItem();
            int count = getAdapter().getCount();
            if (item != count - 1) {
                setCurrentItem(item++, true);
            } else if (item == count - 1) {
                setCurrentItem(0, true);
            }
            mHandler.sendMessageDelayed(mHandler.obtainMessage(ACTION_MOVE), 500);
        }
    };

    public BannerViewPager(Context context) {
        super(context);
    }

    public BannerViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (getAdapter() != null && getAdapter().getCount() > 0) {
            mHandler.sendEmptyMessage(ACTION_MOVE);
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (mHandler.hasMessages(ACTION_MOVE)) {
            mHandler.removeMessages(ACTION_MOVE);
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (mHandler.hasMessages(ACTION_MOVE)) {
                    mHandler.removeMessages(ACTION_MOVE);
                }
                break;
            case MotionEvent.ACTION_UP:
                mHandler.sendEmptyMessage(ACTION_MOVE);
                break;
        }

        return super.dispatchTouchEvent(ev);
    }
}
