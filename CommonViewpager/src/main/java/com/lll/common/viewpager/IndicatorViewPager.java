package com.lll.common.viewpager;

import android.content.Context;
import android.graphics.Path;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * Version 1.0
 * Created by lll on 16/8/31.
 * Description 带指示器的ViewPager
 * copyright generalray4239@gmail.com
 */
public class IndicatorViewPager extends FrameLayout {
    private ViewPager mViewPager;
    private LinearLayout mIndicatorLayout;
    private static final int LAYOUT_Direction_LEFT = 0x10;
    private static final int LAYOUT_Direction_CENTER = 0x11;
    private static final int LAYOUT_Direction_RIGHT = 0x12;

    private int DirMode = LAYOUT_Direction_CENTER;

    private Context mContext;

    private int mIndicatorID;


    public IndicatorViewPager(Context context) {
        super(context);
    }

    public IndicatorViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public IndicatorViewPager(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }


    private void initIndicatorLayout() {
        mIndicatorLayout = new LinearLayout(mContext);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
//        LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams()
        mIndicatorLayout.setLayoutParams(params);

        mIndicatorLayout.setGravity(Gravity.CENTER_VERTICAL | Gravity.BOTTOM);
        addView(mIndicatorLayout);
    }

    public ViewPager getViewPager() {
        return mViewPager;
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
        int childCount = mViewPager.getChildCount();
        if (childCount <= 1) {
            return;
        }
        for (int i = 0; i < childCount; i++) {
            ImageView imageView = new ImageView(mContext);
//            imageView.setLayoutParams();
            imageView.setImageResource(mIndicatorID);
            mIndicatorLayout.addView(imageView);
        }
    }

}
