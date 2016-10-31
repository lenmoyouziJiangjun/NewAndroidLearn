package com.lll.common.ui.imageView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.animation.Animation;
import android.widget.ImageView;

/**
 * Version 1.0
 * Created by lll on 16/10/13.
 * Description 圆形ImageView
 * copyright generalray4239@gmail.com
 */

public class CircleImageView extends ImageView {
    private static final int KEY_SHADOW_COLOR = 0x1E000000;
    private static final int FILL_SHADOW_COLOR = 0x3D000000;

    private Paint mPint;

    private int mShadowRadius;

    private static final float X_OFFSET = 0f;
    private static final float Y_OFFSET = 1.75f;
    private static final float SHADOW_RADIUS = 3.5f;
    private static final int SHADOW_ELEVATION = 4;

    private Animation.AnimationListener mListener;

    private boolean elevationSupported() {
        return android.os.Build.VERSION.SDK_INT >= 21;
    }

    public CircleImageView(Context context, int color, final float radius) {
        super(context);
        final float density = context.getResources().getDisplayMetrics().density;
        final int diameter = (int) (radius * density * 2);
        final int shadowYOffset = (int) (density * Y_OFFSET);
        final int shadowXOffset = (int) (density * X_OFFSET);
        mShadowRadius = (int) (density * SHADOW_RADIUS);

        ShapeDrawable circle;
        if (elevationSupported()) {
            circle = new ShapeDrawable(new OvalShape());
            //elevation正视图，立视图。适配MD,设置该属性,
            ViewCompat.setElevation(this, SHADOW_ELEVATION * density);//设置组件"浮"起来的高度
        } else {
            OvalShape shape = new OvalShadow(mShadowRadius, diameter);
            circle = new ShapeDrawable(shape);
            //开启硬件加速
            ViewCompat.setLayerType(this, ViewCompat.LAYER_TYPE_SOFTWARE, circle.getPaint());

            //设置阴影
            circle.getPaint().setShadowLayer(mShadowRadius, shadowXOffset, shadowYOffset,
                    KEY_SHADOW_COLOR);
            final int padding = mShadowRadius;
            // set padding so the inner image sits correctly within the shadow.
            setPadding(padding, padding, padding, padding);
        }
        circle.getPaint().setColor(color);
        setBackgroundDrawable(circle);
    }

    public void setAnimationListener(Animation.AnimationListener listener) {
        mListener = listener;
    }

    @Override
    public void onAnimationStart() {
        super.onAnimationStart();
        if (mListener != null) {
            mListener.onAnimationStart(getAnimation());
        }
    }

    @Override
    public void onAnimationEnd() {
        super.onAnimationEnd();
        if (mListener != null) {
            mListener.onAnimationEnd(getAnimation());
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (!elevationSupported()) {
            setMeasuredDimension(getMeasuredWidth() + mShadowRadius * 2, getMeasuredHeight()
                    + mShadowRadius * 2);
        }
    }

    /**
     * Update the background color of the circle image view.
     *
     * @param colorRes Id of a color resource.
     */
    public void setBackgroundColorRes(int colorRes) {
        setBackgroundColor(getContext().getResources().getColor(colorRes));
    }

    @Override
    public void setBackgroundColor(int color) {
        if (getBackground() instanceof ShapeDrawable) {
            ((ShapeDrawable) getBackground()).getPaint().setColor(color);
        }
    }


    /**
     * 绘制原型
     */
    private class OvalShadow extends OvalShape {
        //定义shader的变化过程为阶梯变化
        private RadialGradient mRadialGradient;
        private Paint mShadowPaint;
        private int mCircleDiameter;


        public OvalShadow(int shadowRadius, int circleDiameter) {
            super();
            mShadowPaint = new Paint();
            mShadowRadius = shadowRadius;
            mCircleDiameter = circleDiameter;
            mRadialGradient = new RadialGradient(mCircleDiameter / 2, mCircleDiameter / 2,
                    mShadowRadius, new int[]{FILL_SHADOW_COLOR, Color.TRANSPARENT}, null, Shader.TileMode.CLAMP);
            mShadowPaint.setShader(mRadialGradient);
        }

        @Override
        public void draw(Canvas canvas, Paint paint) {
            final int viewWidth = CircleImageView.this.getWidth();
            final int viewHeight = CircleImageView.this.getHeight();
            //绘制阴影渲染
            canvas.drawCircle(viewWidth / 2, viewHeight / 2, (mCircleDiameter / 2 + mShadowRadius), mShadowPaint);

            //绘制圆
            canvas.drawCircle(viewWidth / 2, viewHeight / 2, mCircleDiameter / 2, paint);
        }
    }
}
