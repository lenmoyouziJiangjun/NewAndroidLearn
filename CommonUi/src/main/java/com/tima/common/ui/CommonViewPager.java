package com.tima.common.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.support.v4.view.ViewPager;
import android.view.View;

/**
 * Version 1.0
 * Created by lll on 16/6/23.
 * Description  带滑动页签的PagerAdapter
 * copyright generalray4239@gmail.com
 */
public class CommonViewPager extends ViewPager {

    /**是否显示页签*/
    private boolean isShowBookMark;
    /**页签对应的View*/
    private View mBookMarkView;


    public CommonViewPager(Context context) {
        super(context);
    }


    public void setShowBookMark(boolean isShow){
        this.isShowBookMark = isShow;
    }

    public void setBookMarkView(View view){
        this.mBookMarkView = view;
    }



    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(isShowBookMark){
            int pagerCount =getAdapter().getCount();
        }
    }
}
