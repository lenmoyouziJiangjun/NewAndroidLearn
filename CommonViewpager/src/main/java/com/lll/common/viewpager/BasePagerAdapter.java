package com.lll.common.viewpager;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Version 1.0
 * Created by lll on 16/6/23.
 * Description
 * copyright generalray4239@gmail.com
 */
public class BasePagerAdapter<T extends View> extends PagerAdapter {

    List<T> mPageList;

    public BasePagerAdapter(List<T> pages){
        this.mPageList = pages;
    }


    @Override
    public int getCount() {
        if (mPageList==null){
            return 0;
        }
        return mPageList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        int childCount = getCount();
        if (position<childCount){
            container.addView(mPageList.get(position),0);
            return mPageList.get(position);
        }else{
            return null;
        }
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        if (position < getCount()) {
            container.removeView(mPageList.get(position));
            //mPageList.get(position).recycle();
        }
    }
}
