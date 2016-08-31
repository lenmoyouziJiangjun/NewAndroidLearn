package com.lll.common.viewpager.tab;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.HorizontalScrollView;

/**
 * Version 1.0
 * Created by lll on 16/8/31.
 * Description 采用横向的ScrollView实现tab
 *             参考:https://github.com/astuetz/PagerSlidingTabStrip/
 * copyright generalray4239@gmail.com
 *
 */
public class ViewPagerTabStrip extends HorizontalScrollView{
    public ViewPagerTabStrip(Context context) {
        super(context);
    }

    public ViewPagerTabStrip(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ViewPagerTabStrip(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


}
