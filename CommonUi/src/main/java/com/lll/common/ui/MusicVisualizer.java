package com.lll.common.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import java.util.Random;

/**
 * Version 1.0
 * Created by lll on 16/9/23.
 * Description 模拟音乐播放的View
 * copyright generalray4239@gmail.com
 */

public class MusicVisualizer extends View {

    private Paint mPaint = new Paint();
    private Random mRandom = new Random();


    private Runnable animateView = new Runnable() {
        @Override
        public void run() {
            postDelayed(this, 150);//死循环执行
            invalidate();
        }
    };


    public MusicVisualizer(Context context) {
        this(context, null);
    }

    public MusicVisualizer(Context context, AttributeSet attrs) {
        super(context, attrs);
        removeCallbacks(animateView);
        post(animateView);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.BLUE);
        canvas.drawRect(getDimensionInPixel(0),getHeight() - (20 + mRandom.nextInt((int) (getHeight() / 1.5f) - 19)), getDimensionInPixel(7), getHeight(),mPaint);
        mPaint.setColor(Color.RED);
        canvas.drawRect(getDimensionInPixel(10), getHeight() - (20 + mRandom.nextInt((int) (getHeight() / 1.5f) - 19)), getDimensionInPixel(17), getHeight(), mPaint);
        mPaint.setColor(Color.BLACK);
        canvas.drawRect(getDimensionInPixel(20), getHeight() - (20 + mRandom.nextInt((int) (getHeight() / 1.5f) - 19)), getDimensionInPixel(27), getHeight(), mPaint);
    }

    public void setColor(int color) {
        mPaint.setColor(color);
        postInvalidate();
    }

    /**
     * dp转px
     * @param dp
     * @return
     */
    private int getDimensionInPixel(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics());
    }

    /**
     * 可见状态发生变化
     *
     * @param visibility
     */
    @Override
    protected void onWindowVisibilityChanged(int visibility) {
        super.onWindowVisibilityChanged(visibility);
        if (visibility == VISIBLE) {
            removeCallbacks(animateView);
            post(animateView);
        } else if (visibility == GONE) {
            removeCallbacks(animateView);
        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }
}
