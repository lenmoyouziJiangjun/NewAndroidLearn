package com.lll.common.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Version 1.0
 * Created by lll on 17/1/2.
 * Description 带下拉刷新和上拉加载更多功能的Layout
 * 支持定义header和footer
 * copyright generalray4239@gmail.com
 */
public class PullToRefreshLayout<T> extends LinearLayout {
    private View headerView;
    private View footerView;


    public void setHeaderView(View view) {

    }

    public void setFooterView(View view) {

    }


    public PullToRefreshLayout(Context context) {
        super(context);
    }

    public PullToRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PullToRefreshLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
