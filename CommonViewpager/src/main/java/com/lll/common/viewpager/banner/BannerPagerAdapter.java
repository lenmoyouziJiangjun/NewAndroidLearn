package com.lll.common.viewpager.banner;

import android.support.v4.util.SimpleArrayMap;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.lll.common.viewpager.banner.holder.BannerHolder;
import com.lll.common.viewpager.banner.holder.BannerViewHolderCreator;

import java.util.List;

/**
 * Version 1.0
 * Created by lll on 16/6/23.
 * Description
 * copyright generalray4239@gmail.com
 */
public class BannerPagerAdapter<T> extends PagerAdapter {
    List<T> mDatas;
    /***/
    protected BannerViewHolderCreator mHolderCreator;
    private boolean canLoop = true;
    private BannerViewPager mViewPager;

    private final int MULTIPLE_COUNT = 300;

    //private


    /**
     * 外界传递creator和data进来
     * @param creator
     * @param datas
     */
    public BannerPagerAdapter(BannerViewHolderCreator creator,List datas){
        this.mHolderCreator = creator;
        this.mDatas = datas;
    }



    public int toRealPosition(int position) {
        int realCount = getRealCount();
        if (realCount == 0)
            return 0;
        int realPosition = position % realCount;
        return realPosition;
    }

    @Override
    public int getCount() {
        return canLoop ? getRealCount()*MULTIPLE_COUNT : getRealCount();
    }



    public int getRealCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    public void setCanLoop(boolean canLoop) {
        this.canLoop = canLoop;
    }

    public void setViewPager(BannerViewPager viewPager) {
        this.mViewPager = viewPager;
    }





    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    /**
     * 加载View,考虑如何实现重用View
     * @param container
     * @param position
     * @return
     */
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        int realPosition = toRealPosition(position);
        View view = getView(container,null,position);

        container.addView(view);
        return view;
    }

    /**
     * 移除View
     * @param container
     * @param position
     * @param object
     */
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        View view = (View) object;
        container.removeView(view);

        //super.destroyItem(container, position, object);
    }

    @Override
    public void finishUpdate(ViewGroup container) {
        int position = mViewPager.getCurrentItem();
        if (position==0){//第一个
            position = mViewPager.getFristItem();
        }else if(position == getCount()-1){//最后一个
            position = mViewPager.getLastItem();
        }
        //平滑滚动到具体某一个界面
        mViewPager.setCurrentItem(position,true);
    }

    /**
     * 创建View方法
     * @param container
     * @param view
     * @param position
     */
    private View getView(ViewGroup container,View view,int position){
        BannerHolder holder =null;
        if (view == null){
            holder = (BannerHolder)mHolderCreator.createHolder();
            //创建View
            view = holder.createView(container.getContext());
            view.setTag(holder);
        }else{
            holder = (BannerHolder) view.getTag();
        }
        //绑定数据
        if (mDatas !=null && !mDatas.isEmpty()){
            holder.updateItem(container.getContext(),position,mDatas.get(position));
        }
        return view;
    }
}
