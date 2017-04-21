package com.lll.core.activity.test.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.RelativeLayout;

/**
 * Version 1.0
 * Created by lll on 16/12/14.
 * Description
 * copyright generalray4239@gmail.com
 */

public class TestRelativeLayout extends RelativeLayout {
    public TestRelativeLayout(Context context) {
        super(context);
        setWillNotDraw(false);
    }

    public TestRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        setWillNotDraw(false);
    }

    public TestRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setWillNotDraw(false);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Long startTime= SystemClock.currentThreadTimeMillis();
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Log.e("lll","-------RelativeLayout--measure时间----"+(SystemClock.currentThreadTimeMillis()-startTime));
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        Long startTime= SystemClock.currentThreadTimeMillis();
        super.onLayout(changed, l, t, r, b);
        Log.e("lll","-------RelativeLayout--onLayout时间----"+(SystemClock.currentThreadTimeMillis()-startTime));

    }

    @Override
    protected void onDraw(Canvas canvas) {
        Long startTime= SystemClock.currentThreadTimeMillis();
        super.onDraw(canvas);
        String a= "测试绘制RelativeLayout文字";
        canvas.drawText(a,0,a.length()-1,350,350,getPaint());
        Log.e("lll","-------RelativeLayout--onDraw时间----"+(SystemClock.currentThreadTimeMillis()-startTime));

    }

    private Paint getPaint(){
        Paint paint = new Paint();
        paint.setTextSize(50);
        paint.setColor(Color.RED);
        return paint;
    }
}
