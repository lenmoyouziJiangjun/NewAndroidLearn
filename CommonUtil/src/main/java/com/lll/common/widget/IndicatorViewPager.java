package com.lll.common.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;

/**
 * Version 1.0
 * Created by lll on 17/1/1.
 * Description 带指示器的ViewPager
 * copyright generalray4239@gmail.com
 */
public class IndicatorViewPager extends ViewPager {

    private boolean showIndicator;
    private Paint mCirclePaint;
    private int mRadious;
    private int mCurrentColor;
    private int mColor;
    private int padding;


    public IndicatorViewPager(Context context) {
        this(context, null);
    }

    public IndicatorViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        initCirclePaint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawIndicator(canvas);
    }

    public void initCirclePaint() {
        mCirclePaint = new Paint();
        mCirclePaint.setAntiAlias(true);

    }

    private void drawIndicator(Canvas canvas) {
        int count = getAdapter().getCount();
        if (count >= 2 && showIndicator) {
            int selected = getCurrentItem();
            int indicatorTotalWidth = count * mRadious * 2 + (count - 1) * padding;
            for (int i = 0; i < count; i++) {
                if (i == selected) {//绘制红色的圆
                    mCirclePaint.setColor(mCurrentColor);
                } else {//绘制白色的圆
                    mCirclePaint.setColor(mColor);
                }
                int x = 0;
                if (i == 0) {
                    x = (getWidth() - indicatorTotalWidth) / 2 + mRadious;
                } else {
                    x = (getWidth() - indicatorTotalWidth) / 2 + mRadious + i * 2 * mRadious + (i + 1) * padding;
                }

                int y = getBottom() - 12 - mRadious;
                canvas.drawCircle(x, y, mRadious, mCirclePaint);
            }
            canvas.restore();
        }
    }
}
