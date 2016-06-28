package com.lll.common.viewpager.banner.listener;

import android.support.v4.view.ViewPager;
import android.widget.ImageView;

import java.util.ArrayList;

/**
 * Version 1.0
 * Created by lll on 16/6/27.
 * Description 滚动的时候,添加指示器
 * copyright generalray4239@gmail.com
 */
public class BannerPageChangeListener implements ViewPager.OnPageChangeListener {
    //指示器
    private ArrayList<ImageView> pointViews;

    private int[] page_indicatorId;

    private ViewPager.OnPageChangeListener mPageChangeListener;


    public BannerPageChangeListener(ArrayList<ImageView> pointViews,int page_indicatorId[]){
        this.pointViews=pointViews;
        this.page_indicatorId = page_indicatorId;
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if(mPageChangeListener != null)mPageChangeListener.onPageScrolled(position,positionOffset,positionOffsetPixels);
    }



    @Override
    public void onPageScrollStateChanged(int state) {
        if(mPageChangeListener != null)mPageChangeListener.onPageScrollStateChanged(state);
    }


    @Override
    public void onPageSelected(int index) {
        for (int i = 0; i < pointViews.size(); i++) {
            pointViews.get(index).setImageResource(page_indicatorId[1]);
            if (index != i) {
                pointViews.get(i).setImageResource(page_indicatorId[0]);
            }
        }
        if(mPageChangeListener != null)mPageChangeListener.onPageSelected(index);
    }
}
