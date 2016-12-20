package com.lll.common.viewpager;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Path;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.v4.view.ViewPager;
import android.telecom.PhoneAccount;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * Version 1.0
 * Created by lll on 16/8/31.
 * Description 带指示器的ViewPager。用于展示分页
 * copyright generalray4239@gmail.com
 */
public class IndicatorViewPager extends FrameLayout {
    private ViewPager mViewPager;
    private LinearLayout mIndicatorLayout;
    private static final int LAYOUT_Direction_LEFT = 0x00;
    private static final int LAYOUT_Direction_CENTER = 0x01;
    private static final int LAYOUT_Direction_RIGHT = 0x02;

    private int dirMode = LAYOUT_Direction_CENTER;


    private Drawable mIndicatorID;


    public IndicatorViewPager(Context context) {
        this(context, null);
    }

    public IndicatorViewPager(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public IndicatorViewPager(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.IndicatorViewPager);
        mIndicatorID = array.getDrawable(R.styleable.IndicatorViewPager_indicatorBg);
        dirMode = array.getInt(R.styleable.IndicatorViewPager_indicatorLocation, LAYOUT_Direction_CENTER);
        array.recycle();

    }


    private void initIndicatorLayout() {
        mIndicatorLayout = new LinearLayout(getContext());
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

        int gravity = 0;
        switch (dirMode) {
            case LAYOUT_Direction_LEFT:
                params.leftMargin = 12;
                gravity = Gravity.LEFT;
                break;
            case LAYOUT_Direction_RIGHT:
                gravity = Gravity.RIGHT;
                params.rightMargin = 12;
                break;
            default:
                gravity = Gravity.CENTER_VERTICAL;
                break;
        }
        mIndicatorLayout.setLayoutParams(params);
        mIndicatorLayout.setGravity(gravity | Gravity.BOTTOM);
        addView(mIndicatorLayout);
    }

    public ViewPager getViewPager() {
        return mViewPager;
    }

    public void setViewPager(ViewPager pager) {
        if (pager.getAdapter() == null) {
            throw new RuntimeException("Viewpager mast call setAdapter ");
        }
        mViewPager = pager;
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mViewPager.setLayoutParams(params);
        addView(mViewPager);
        drawPagerIndicator();
    }

    /**
     * @param drawableId
     */
    public void setIndicatorDrawable(@DrawableRes int drawableId) {
        mIndicatorID = getResources().getDrawable(drawableId);
    }


    /**
     * 绘制指示器
     */
    public void drawPagerIndicator() {
        if (mIndicatorLayout == null) {
            initIndicatorLayout();
        } else {
            mIndicatorLayout.removeAllViews();
        }
        int childCount = mViewPager.getAdapter().getCount();
        if (childCount <= 1) {
            return;
        }
        for (int i = 0; i < childCount; i++) {
            ImageView imageView = new ImageView(getContext());
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(60, 60);
            if (i != childCount) {
                params.leftMargin = 10;
            }
            imageView.setLayoutParams(params);
            imageView.setBackgroundDrawable(mIndicatorID);
            mIndicatorLayout.addView(imageView);
        }
    }

    /**
     * 跟新指示器
     *
     * @param index
     */
    public void updateIndicator(int index) {
        for (int i = 0, m = mIndicatorLayout.getChildCount(); i < m; i++) {
            final View child = mIndicatorLayout.getChildAt(i);
            child.setSelected(i == index ? true : false);
        }
    }


}
