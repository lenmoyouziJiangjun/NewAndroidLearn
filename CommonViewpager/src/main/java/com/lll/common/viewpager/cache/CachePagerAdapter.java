package com.lll.common.viewpager.cache;

import android.support.v4.view.PagerAdapter;
import android.view.View;

/**
 * Version 1.0
 * Created by lll on 16/6/27.
 * Description 实现view重用的pagerAdapter
 * copyright generalray4239@gmail.com
 */
public class CachePagerAdapter extends PagerAdapter {


    /**
     * 提供一个缓存对象
     * @param <T>
     */
    private class pageEntity<T>{
        private PageHolder mHolder;
        private T data;

        public void recycle(){
            data=null;
        }
    }


    @Override
    public int getCount() {
        return 0;
    }




    @Override
    public boolean isViewFromObject(View view, Object object) {
        return false;
    }
}
