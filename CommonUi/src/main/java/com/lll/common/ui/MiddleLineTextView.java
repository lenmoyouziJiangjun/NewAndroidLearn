package com.lll.common.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.TextView;

/**
 * Version 1.0
 * Created by lll on 16/6/30.
 * Description
 * copyright generalray4239@gmail.com
 */
public class MiddleLineTextView extends TextView {
    private Paint mLinePaint;

    public MiddleLineTextView(Context context) {
        super(context);
        initLinePaint();
    }

    public MiddleLineTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initLinePaint();
    }



    private void initLinePaint(){
        mLinePaint = new Paint();
//        mLinePaint.setColor(Color.parseColor("#999999"));
        mLinePaint.setColor(Color.RED);
        mLinePaint.setAntiAlias(true);
        mLinePaint.setStrokeWidth(20);
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        super.setText(text, type);
        postInvalidate();
    }



    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        getLeft(),getRight(),getBottom(),
        int y = (getBottom()-getTop())/2;
        Log.i("lll", "x="+getLeft()+"---"+getRight()+"--"+getWidth()+"--"+y+"----"+getX()+"----");

//        get
//        canvas.save();
//        get
//        getX()
        canvas.drawLine(0 ,y,getWidth(),y,mLinePaint);

//        postInvalidate();
//        canvas.restore();
    }
}
