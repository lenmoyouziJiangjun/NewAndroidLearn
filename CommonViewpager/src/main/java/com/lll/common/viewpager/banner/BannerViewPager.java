package com.lll.common.viewpager.banner;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewConfigurationCompat;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;

import com.lll.common.viewpager.banner.listener.OnItemClickListener;

/**
 * Version 1.0
 * Created by lll on 16/6/23.
 * Description 自定义广告ViewPager
 *            <p>
 *                1、需要循环滚动
 *                2、手指触摸的时候,停止
 *                3、支持自定义切换效果
 *
 *            </p>
 * copyright generalray4239@gmail.com
 */
public class BannerViewPager extends ViewPager {

    private OnPageChangeListener mOuterPageChangeListener;

    private OnItemClickListener onItemClickListener;
    private BannerPagerAdapter mAdapter;

    private boolean isCanScroll = true;
    private boolean canLoop = true;

    private float downX;


    private OnPageChangeListener onPageChangeListener = new OnPageChangeListener() {
        private float mPreviousPosition = -1;

        @Override
        public void onPageSelected(int position) {
            int realPosition = mAdapter.toRealPosition(position);
            if (mPreviousPosition != realPosition) {
                mPreviousPosition = realPosition;
                if (mOuterPageChangeListener != null) {
                    mOuterPageChangeListener.onPageSelected(realPosition);
                }
            }
        }

        @Override
        public void onPageScrolled(int position, float positionOffset,
                                   int positionOffsetPixels) {
            int realPosition = position;

            if (mOuterPageChangeListener != null) {
                if (realPosition != mAdapter.getRealCount() - 1) {
                    mOuterPageChangeListener.onPageScrolled(realPosition,
                            positionOffset, positionOffsetPixels);
                } else {
                    if (positionOffset > .5) {
                        mOuterPageChangeListener.onPageScrolled(0, 0, 0);
                    } else {
                        mOuterPageChangeListener.onPageScrolled(realPosition,
                                0, 0);
                    }
                }
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            if (mOuterPageChangeListener != null) {
                mOuterPageChangeListener.onPageScrollStateChanged(state);
            }
        }
    };

    public BannerViewPager(Context context) {
        super(context);
        init();
    }

    public BannerViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        super.addOnPageChangeListener(onPageChangeListener);
    }

    public void setAdapter(BannerPagerAdapter adapter,boolean canLoop){
         this.mAdapter =adapter;
        mAdapter.setCanLoop(canLoop);
        mAdapter.setViewPager(this);
        super.setAdapter(adapter);
        setCurrentItem(getFristItem(),true);
    }

    public int getFristItem() {
        return canLoop ? mAdapter.getRealCount() : 0;
    }

    public int getLastItem() {
        return mAdapter.getRealCount() - 1;
    }

    public boolean isCanScroll() {
        return isCanScroll;
    }

    public void setCanScroll(boolean isCanScroll) {
        this.isCanScroll = isCanScroll;
    }


    public int getRealItem() {
        return mAdapter != null ? mAdapter.toRealPosition(super.getCurrentItem()) : 0;
    }


    public boolean isCanLoop() {
        return canLoop;
    }

    public void setCanLoop(boolean canLoop) {
        this.canLoop = canLoop;
        if (canLoop == false) {
            setCurrentItem(getRealItem(), false);
        }
        if (mAdapter == null) return;
        mAdapter.setCanLoop(canLoop);
        mAdapter.notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }


    @Override
    public void addOnPageChangeListener(OnPageChangeListener listener) {
        mOuterPageChangeListener = listener;
    }

    @Override
    public void setOnPageChangeListener(OnPageChangeListener listener) {
        addOnPageChangeListener(listener);
    }


    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (isCanScroll){
            if(onItemClickListener !=null){
                int action = ev.getAction();
                switch (action){
                    case MotionEvent.ACTION_DOWN:
                        downX=getX();
                        break;
                    case MotionEvent.ACTION_UP:
                        float upX=getX();
                        if(Math.abs(upX-downX)<ViewConfiguration.getScrollFriction()){
                            onItemClickListener.onItemClick(getCurrentItem());
                        }
                        // ViewConfiguration.getScrollFriction();
                        downX =0;
                        break;
                    case MotionEvent.ACTION_MOVE:
                        break;

                }

            }
            return super.onTouchEvent(ev);
        }
        return false;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if(isCanScroll){
            return super.onInterceptTouchEvent(ev);
        }else{
            return  false;
        }
    }

    public BannerPagerAdapter getAdapter(){
        return mAdapter;
    }




}
