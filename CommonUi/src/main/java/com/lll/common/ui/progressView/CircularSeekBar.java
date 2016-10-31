package com.lll.common.ui.progressView;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.RectF;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.SeekBar;

import com.tima.common.ui.R;

/**
 * Version 1.0
 * Created by lll on 16/10/19.
 * Description 圆形的 SeekBar
 * copyright generalray4239@gmail.com 60000
 */

public class CircularSeekBar extends View {


    /*dp2px*/
    private final float DPTOPX_SCALE = getResources().getDisplayMetrics().density;


    /* Default values */
    private static final float DEFAULT_CIRCLE_X_RADIUS = 30f;
    private static final float DEFAULT_CIRCLE_Y_RADIUS = 30f;
    private static final float DEFAULT_POINTER_RADIUS = 7f;
    private static final float DEFAULT_POINTER_HALO_WIDTH = 6f;
    private static final float DEFAULT_POINTER_HALO_BORDER_WIDTH = 2f;
    private static final float DEFAULT_CIRCLE_STROKE_WIDTH = 5f;
    private static final float DEFAULT_START_ANGLE = 270f; // Geometric (clockwise, relative to 3 o'clock)
    private static final float DEFAULT_END_ANGLE = 270f; // Geometric (clockwise, relative to 3 o'clock)
    private static final int DEFAULT_MAX = 100;
    private static final int DEFAULT_PROGRESS = 0;
    private static final int DEFAULT_CIRCLE_COLOR = Color.DKGRAY;
    private static final int DEFAULT_CIRCLE_PROGRESS_COLOR = Color.argb(235, 74, 138, 255);
    private static final int DEFAULT_POINTER_COLOR = Color.argb(235, 74, 138, 255);
    private static final int DEFAULT_POINTER_HALO_COLOR = Color.argb(135, 74, 138, 255);
    private static final int DEFAULT_POINTER_HALO_COLOR_ONTOUCH = Color.argb(135, 74, 138, 255);
    private static final int DEFAULT_CIRCLE_FILL_COLOR = Color.TRANSPARENT;
    private static final int DEFAULT_POINTER_ALPHA = 135;
    private static final int DEFAULT_POINTER_ALPHA_ONTOUCH = 100;
    private static final boolean DEFAULT_USE_CUSTOM_RADII = false;
    private static final boolean DEFAULT_MAINTAIN_EQUAL_CIRCLE = true;
    private static final boolean DEFAULT_MOVE_OUTSIDE_CIRCLE = false;
    private static final boolean DEFAULT_LOCK_ENABLED = true;

    /**
     * Minimum touch target size in DP. 48dp is the Android design recommendation
     */
    private final float MIN_TOUCH_TARGET_DP = 48;

    /*绘制的paint*/
    private Paint mCirclePaint;
    private Paint mCircleFillPaint;
    private Paint mCircleProgressPaint;
    private Paint mCircleProgressGlowPaint;
    private Paint mPointerPaint;
    private Paint mPointerHaloPaint;
    private Paint mPointerHaloBorderPaint;

    private float mCircleXRadius;
    private float mCircleYRadius;
    private float mPointerRadius;
    private float mPointerHaloWidth;
    private float mPointerHaloBorderWidth;
    private float mCircleStrokeWidth;

    /*颜色值*/
    private int mPointerColor;
    private int mPointerHaloColor;
    private int mPointerHaloColorOnTouch;
    private int mCircleColor;
    private int mCircleProgressColor;
    private int mCircleFillColor;

    /*触摸颜色*/
    private int mPointerAlpha = DEFAULT_POINTER_ALPHA;
    private int mPointerAlphaOnTouch = DEFAULT_POINTER_ALPHA_ONTOUCH;


    /*最大值*/
    private int mMax;
    private int mProgress;

    private boolean mCustomRadii;
    private boolean mMaintainEqualCircle;
    private boolean mMoveOutsideCircle;


    /*开始结束角度*/
    private float mStartAngle;
    private float mEndAngle;

    private float mTotalCircleDegrees;
    private float mProgressDegrees;
    /**
     * Represents the progress mark on the circle, in geometric degrees.
     * This is not provided by the user; it is calculated;
     */
    private float mPointerPosition;


    /**
     * Pointer position in terms of X and Y coordinates.
     */
    private float[] mPointerPositionXY = new float[2];


    private Path mCircleProgressPath;
    private Path mCirclePath;

    private RectF mCircleRectF = new RectF();


    /**
     * When the user is touching the circle on ACTION_DOWN, this is set to true.
     * Used when touching the CircularSeekBar.
     */
    private boolean mUserIsMovingPointer = false;
    /**
     * Represents the clockwise distance from {@code mStartAngle} to the touch angle.
     * Used when touching the CircularSeekBar.
     */
    private float cwDistanceFromStart;

    /**
     * Represents the counter-clockwise distance from {@code mStartAngle} to the touch angle.
     * Used when touching the CircularSeekBar.
     */
    private float ccwDistanceFromStart;

    /**
     * Represents the clockwise distance from {@code mEndAngle} to the touch angle.
     * Used when touching the CircularSeekBar.
     */
    private float cwDistanceFromEnd;

    /**
     * Represents the counter-clockwise distance from {@code mEndAngle} to the touch angle.
     * Used when touching the CircularSeekBar.
     * Currently unused, but kept just in case.
     */
    @SuppressWarnings("unused")
    private float ccwDistanceFromEnd;

    /**
     * The previous touch action value for {@code cwDistanceFromStart}.
     * Used when touching the CircularSeekBar.
     */
    private float lastCWDistanceFromStart;

    /**
     * Represents the clockwise distance from {@code mPointerPosition} to the touch angle.
     * Used when touching the CircularSeekBar.
     */
    private float cwDistanceFromPointer;

    /**
     * Represents the counter-clockwise distance from {@code mPointerPosition} to the touch angle.
     * Used when touching the CircularSeekBar.
     */
    private float ccwDistanceFromPointer;

    /**
     * True if the user is moving clockwise around the circle, false if moving counter-clockwise.
     * Used when touching the CircularSeekBar.
     */
    private boolean mIsMovingCW;
    /**
     * The width of the circle used in the {@code RectF} that is used to draw it.
     * Based on either the View width or the custom X radius.
     */
    private float mCircleWidth;

    /**
     * The height of the circle used in the {@code RectF} that is used to draw it.
     * Based on either the View width or the custom Y radius.
     */
    private float mCircleHeight;


    /**
     * Used for enabling/disabling the lock option for easier hitting of the 0 progress mark.
     */
    private boolean lockEnabled = true;

    /**
     * Used for when the user moves beyond the start of the circle when moving counter clockwise.
     * Makes it easier to hit the 0 progress mark.
     */
    private boolean lockAtStart = true;

    /**
     * Used for when the user moves beyond the end of the circle when moving clockwise.
     * Makes it easier to hit the 100% (max) progress mark.
     */
    private boolean lockAtEnd = false;

    private OnCircularSeekBarChangeListener mOnCircularSeekBarChangeListener;

    /**
     * 拖动事件,参考seekBar
     */
    public interface OnCircularSeekBarChangeListener {
        /**
         * @param seekBar
         * @param progress
         * @param fromUser
         */
        void onProgressChanged(CircularSeekBar seekBar, int progress, boolean fromUser);

        void onStopTrackingTouch(CircularSeekBar seekBar);

        void onStartTrackingTouch(CircularSeekBar seekBar);

    }

    public CircularSeekBar(Context context) {
        super(context);
        init(null, 0);
    }

    public CircularSeekBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public CircularSeekBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs, defStyleAttr);
    }

    /**
     * @param attrs        自定义属性
     * @param defStyleAttr 自定义的style
     */
    private void init(AttributeSet attrs, int defStyleAttr) {
        final TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.CircularSeekBar, defStyleAttr, 0);
        initAttributes(typedArray);
        typedArray.recycle();

        initPaint();
    }

    /**
     * 初始化属性
     *
     * @param attrArray
     */
    private void initAttributes(TypedArray attrArray) {
        mCircleXRadius = attrArray.getFloat(R.styleable.CircularSeekBar_circle_x_radius, DEFAULT_CIRCLE_X_RADIUS) * DPTOPX_SCALE;
        mCircleYRadius = attrArray.getFloat(R.styleable.CircularSeekBar_circle_y_radius, DEFAULT_CIRCLE_Y_RADIUS) * DPTOPX_SCALE;

        mPointerRadius = attrArray.getFloat(R.styleable.CircularSeekBar_pointer_radius, DEFAULT_POINTER_RADIUS) * DPTOPX_SCALE;
        mPointerHaloWidth = attrArray.getFloat(R.styleable.CircularSeekBar_pointer_halo_width, DEFAULT_POINTER_HALO_WIDTH) * DPTOPX_SCALE;
        mPointerHaloBorderWidth = attrArray.getFloat(R.styleable.CircularSeekBar_pointer_halo_border_width, DEFAULT_POINTER_HALO_BORDER_WIDTH) * DPTOPX_SCALE;

        mCircleStrokeWidth = attrArray.getFloat(R.styleable.CircularSeekBar_circle_stroke_width, DEFAULT_CIRCLE_STROKE_WIDTH) * DPTOPX_SCALE;

        String tempColor = attrArray.getString(R.styleable.CircularSeekBar_pointer_color);

        if (tempColor != null) {
            try {
                mPointerColor = Color.parseColor(tempColor);
            } catch (Exception e) {
                e.printStackTrace();
                mPointerColor = DEFAULT_POINTER_COLOR;
            }
        }

        tempColor = attrArray.getString(R.styleable.CircularSeekBar_pointer_halo_color);
        if (tempColor != null) {
            try {
                mPointerHaloColor = Color.parseColor(tempColor);
            } catch (Exception e) {
                e.printStackTrace();
                mPointerHaloColor = DEFAULT_POINTER_HALO_COLOR;
            }
        }

        tempColor = attrArray.getString(R.styleable.CircularSeekBar_pointer_halo_color_ontouch);
        if (tempColor != null) {
            try {
                mPointerHaloColorOnTouch = Color.parseColor(tempColor);
            } catch (Exception e) {
                mPointerHaloColorOnTouch = DEFAULT_POINTER_HALO_COLOR_ONTOUCH;
            }
        }

        tempColor = attrArray.getString(R.styleable.CircularSeekBar_circle_color);
        if (tempColor != null) {
            try {
                mCircleColor = Color.parseColor(tempColor);
            } catch (Exception e) {
                mCircleColor = DEFAULT_CIRCLE_COLOR;
            }
        }

        tempColor = attrArray.getString(R.styleable.CircularSeekBar_circle_progress_color);
        if (tempColor != null) {
            try {
                mCircleProgressColor = Color.parseColor(tempColor);
            } catch (IllegalArgumentException e) {
                mCircleProgressColor = DEFAULT_CIRCLE_PROGRESS_COLOR;
            }
        }

        tempColor = attrArray.getString(R.styleable.CircularSeekBar_circle_fill);
        if (tempColor != null) {
            try {
                mCircleFillColor = Color.parseColor(tempColor);
            } catch (IllegalArgumentException e) {
                mCircleFillColor = DEFAULT_CIRCLE_FILL_COLOR;
            }
        }

        mPointerAlpha = Color.alpha(mPointerHaloColor);

        mPointerAlphaOnTouch = attrArray.getInt(R.styleable.CircularSeekBar_pointer_alpha_ontouch, DEFAULT_POINTER_ALPHA_ONTOUCH);
        if (mPointerAlphaOnTouch > 255 || mPointerAlphaOnTouch < 0) {
            //不在范围内,设置默认
            mPointerAlphaOnTouch = DEFAULT_POINTER_ALPHA_ONTOUCH;
        }

        mMax = attrArray.getInt(R.styleable.CircularSeekBar_max, DEFAULT_MAX);
        mProgress = attrArray.getInt(R.styleable.CircularSeekBar_progress, DEFAULT_PROGRESS);

        mCustomRadii = attrArray.getBoolean(R.styleable.CircularSeekBar_use_custom_radii, DEFAULT_USE_CUSTOM_RADII);
        mMaintainEqualCircle = attrArray.getBoolean(R.styleable.CircularSeekBar_maintain_equal_circle, DEFAULT_MAINTAIN_EQUAL_CIRCLE);
        mMoveOutsideCircle = attrArray.getBoolean(R.styleable.CircularSeekBar_move_outside_circle, DEFAULT_MOVE_OUTSIDE_CIRCLE);
        lockEnabled = attrArray.getBoolean(R.styleable.CircularSeekBar_lock_enabled, DEFAULT_LOCK_ENABLED);

        mStartAngle = ((360f + (attrArray.getFloat((R.styleable.CircularSeekBar_start_angle), DEFAULT_START_ANGLE) % 360f)) % 360f);
        mEndAngle = ((360f + (attrArray.getFloat((R.styleable.CircularSeekBar_end_angle), DEFAULT_END_ANGLE) % 360f)) % 360f);

        if (mStartAngle == mEndAngle) {
            mEndAngle = mStartAngle - .1f;
        }
    }


    /**
     * 初始画笔
     */
    private void initPaint() {
        mCirclePaint = new Paint();
        mCirclePaint.setAntiAlias(true);
        mCirclePaint.setDither(true);
        mCirclePaint.setColor(mCircleColor);
        mCirclePaint.setStrokeWidth(mCircleStrokeWidth);
        mCirclePaint.setStyle(Paint.Style.STROKE);
        mCirclePaint.setStrokeJoin(Paint.Join.ROUND);
        mCirclePaint.setStrokeCap(Paint.Cap.ROUND);

        mCircleFillPaint = new Paint();
        mCircleFillPaint.setAntiAlias(true);
        mCircleFillPaint.setDither(true);
        mCircleFillPaint.setColor(mCircleFillColor);
        mCircleFillPaint.setStyle(Paint.Style.FILL);

        mCircleProgressPaint = new Paint();
        mCircleProgressPaint.setAntiAlias(true);
        mCircleProgressPaint.setDither(true);
        mCircleProgressPaint.setColor(mCircleProgressColor);
        mCircleProgressPaint.setStrokeWidth(mCircleStrokeWidth);
        mCircleProgressPaint.setStyle(Paint.Style.STROKE);
        mCircleProgressPaint.setStrokeJoin(Paint.Join.ROUND);
        mCircleProgressPaint.setStrokeCap(Paint.Cap.ROUND);

        mCircleProgressGlowPaint = new Paint();
        mCircleProgressGlowPaint.set(mCircleProgressPaint);
        mCircleProgressGlowPaint.setMaskFilter(new BlurMaskFilter((5f * DPTOPX_SCALE), BlurMaskFilter.Blur.NORMAL));

        mPointerPaint = new Paint();
        mPointerPaint.setAntiAlias(true);
        mPointerPaint.setDither(true);
        mPointerPaint.setStyle(Paint.Style.FILL);
        mPointerPaint.setColor(mPointerColor);
        mPointerPaint.setStrokeWidth(mPointerRadius);

        mPointerHaloPaint = new Paint();
        mPointerHaloPaint.set(mPointerPaint);
        mPointerHaloPaint.setColor(mPointerHaloColor);
        mPointerHaloPaint.setAlpha(mPointerAlpha);
        mPointerHaloPaint.setStrokeWidth(mPointerRadius + mPointerHaloWidth);

        mPointerHaloBorderPaint = new Paint();
        mPointerHaloBorderPaint.set(mPointerPaint);
        mPointerHaloBorderPaint.setStrokeWidth(mPointerHaloBorderWidth);
        mPointerHaloBorderPaint.setStyle(Paint.Style.STROKE);
    }

    /**
     * Calculates the total degrees between mStartAngle and mEndAngle
     */
    private void calculateTotalDegress() {
        mTotalCircleDegrees = (360f - (mStartAngle - mEndAngle)) % 360f;
        if (mTotalCircleDegrees <= 0f) {
            mTotalCircleDegrees = 360f;
        }
    }

    private void calculateProgressDegrees() {
        mProgressDegrees = mPointerPosition - mStartAngle;
        mProgressDegrees = (mProgressDegrees < 0 ? 360f + mProgressDegrees : mProgressDegrees);
    }

    private void calculatePointerAngle() {
        float progressPercent = ((float) mProgress / (float) mMax);
        mPointerPosition = (progressPercent * mTotalCircleDegrees) + mStartAngle;
        mPointerPosition = mPointerPosition % 360f;
    }

    private void calculatePointerXYPosition() {
        //计算path的长度
        PathMeasure pm = new PathMeasure(mCircleProgressPath, false);
        boolean returnValue = pm.getPosTan(pm.getLength(), mPointerPositionXY, null);
        if (!returnValue) {
            pm = new PathMeasure(mCirclePath, false);
            returnValue = pm.getPosTan(0, mPointerPositionXY, null);
        }
    }

    private void initPath() {
        mCirclePath = new Path();
        mCirclePath.addArc(mCircleRectF, mStartAngle, mTotalCircleDegrees);

        mCircleProgressPath = new Path();
        mCircleProgressPath.addArc(mCircleRectF, mStartAngle, mProgressDegrees);
    }

    /**
     * Initialize the {@code RectF} objects with the appropriate values.
     */
    private void initRects() {
        mCircleRectF.set(-mCircleWidth, -mCircleHeight, mCircleWidth, mCircleHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.translate(this.getWidth() / 2, getHeight() / 2);

        canvas.drawPath(mCirclePath, mCirclePaint);
        canvas.drawPath(mCircleProgressPath, mCircleProgressGlowPaint);
        canvas.drawPath(mCircleProgressPath, mCircleProgressPaint);

        canvas.drawPath(mCirclePath, mCircleFillPaint);

        canvas.drawCircle(mPointerPositionXY[0], mPointerPositionXY[1], mPointerRadius + mPointerHaloWidth, mPointerHaloPaint);
        canvas.drawCircle(mPointerPositionXY[0], mPointerPositionXY[1], mPointerRadius, mPointerPaint);
        if (mUserIsMovingPointer) {
            canvas.drawCircle(mPointerPositionXY[0], mPointerPositionXY[1], mPointerRadius + mPointerHaloWidth + (mPointerHaloBorderWidth / 2f), mPointerHaloBorderPaint);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int height = getDefaultSize(getSuggestedMinimumHeight(), heightMeasureSpec);
        int width = getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec);

        if (mMaintainEqualCircle) {
            int min = Math.min(width, height);
            setMeasuredDimension(min, min);
        } else {
            setMeasuredDimension(width, height);
        }

        mCircleHeight = (float) height / 2f - mCircleStrokeWidth - mPointerRadius - (mPointerHaloBorderWidth * 1.5f);
        mCircleWidth = (float) width / 2f - mCircleStrokeWidth - mPointerRadius - (mPointerHaloBorderWidth * 1.5f);

        if (mCustomRadii) {
            if ((mCircleYRadius - mCircleStrokeWidth - mPointerRadius - mPointerHaloBorderWidth) < mCircleHeight) {
                mCircleHeight = mCircleYRadius - mCircleStrokeWidth - mPointerRadius - (mPointerHaloBorderWidth * 1.5f);
            }

            if ((mCircleXRadius - mCircleStrokeWidth - mPointerRadius - mPointerHaloBorderWidth) < mCircleWidth) {
                mCircleWidth = mCircleXRadius - mCircleStrokeWidth - mPointerRadius - (mPointerHaloBorderWidth * 1.5f);
            }
        }
        if (mMaintainEqualCircle) {
            float min = Math.min(mCircleHeight, mCircleWidth);
            mCircleHeight = min;
            mCircleWidth = min;
        }
        recalculateAll();
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //Convert coordinates to our internal coordinate system
        float x = event.getX() - getWidth() / 2;
        float y = event.getY() - getHeight() / 2;

        float distanceX = mCircleRectF.centerX() - x;
        float distanceY = mCircleRectF.centerY() - y;

        // Get the distance from the center of the circle in terms of a radius
        float touchEventRadius = (float) Math.sqrt((Math.pow(distanceX, 2) + Math.pow(distanceY, 2)));

        float minimumTouchTarget = MIN_TOUCH_TARGET_DP * DPTOPX_SCALE; // Convert minimum touch target into px
        float additionalRadius; // Either uses the minimumTouchTarget size or larger if the ring/pointer is larger

        if (mCircleStrokeWidth < minimumTouchTarget) { // If the width is less than the minimumTouchTarget, use the minimumTouchTarget
            additionalRadius = minimumTouchTarget / 2;
        } else {
            additionalRadius = mCircleStrokeWidth / 2; // Otherwise use the width
        }
        float outerRadius = Math.max(mCircleHeight, mCircleWidth) + additionalRadius; // Max outer radius of the circle, including the minimumTouchTarget or wheel width
        float innerRadius = Math.min(mCircleHeight, mCircleWidth) - additionalRadius; // Min inner radius of the circle, including the minimumTouchTarget or wheel width

        if (mPointerRadius < (minimumTouchTarget / 2)) { // If the pointer radius is less than the minimumTouchTarget, use the minimumTouchTarget
            additionalRadius = minimumTouchTarget / 2;
        } else {
            additionalRadius = mPointerRadius; // Otherwise use the radius
        }

        float touchAngle;
        touchAngle = (float) ((java.lang.Math.atan2(y, x) / Math.PI * 180) % 360); // Verified
        touchAngle = (touchAngle < 0 ? 360 + touchAngle : touchAngle); // Verified

        cwDistanceFromStart = touchAngle - mStartAngle; // Verified
        cwDistanceFromStart = (cwDistanceFromStart < 0 ? 360f + cwDistanceFromStart : cwDistanceFromStart); // Verified
        ccwDistanceFromStart = 360f - cwDistanceFromStart; // Verified

        cwDistanceFromEnd = touchAngle - mEndAngle; // Verified
        cwDistanceFromEnd = (cwDistanceFromEnd < 0 ? 360f + cwDistanceFromEnd : cwDistanceFromEnd); // Verified
        ccwDistanceFromEnd = 360f - cwDistanceFromEnd; // Verified

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                // These are only used for ACTION_DOWN for handling if the pointer was the part that was touched
                float pointerRadiusDegrees = (float) ((mPointerRadius * 180) / (Math.PI * Math.max(mCircleHeight, mCircleWidth)));
                cwDistanceFromPointer = touchAngle - mPointerPosition;
                cwDistanceFromPointer = (cwDistanceFromPointer < 0 ? 360f + cwDistanceFromPointer : cwDistanceFromPointer);
                ccwDistanceFromPointer = 360f - cwDistanceFromPointer;
                // This is for if the first touch is on the actual pointer.
                if (((touchEventRadius >= innerRadius) && (touchEventRadius <= outerRadius)) && ((cwDistanceFromPointer <= pointerRadiusDegrees) || (ccwDistanceFromPointer <= pointerRadiusDegrees))) {
                    setProgressBasedOnAngle(mPointerPosition);
                    lastCWDistanceFromStart = cwDistanceFromStart;
                    mIsMovingCW = true;
                    mPointerHaloPaint.setAlpha(mPointerAlphaOnTouch);
                    mPointerHaloPaint.setColor(mPointerHaloColorOnTouch);
                    recalculateAll();
                    invalidate();
                    if (mOnCircularSeekBarChangeListener != null) {
                        mOnCircularSeekBarChangeListener.onStartTrackingTouch(this);
                    }
                    mUserIsMovingPointer = true;
                    lockAtEnd = false;
                    lockAtStart = false;
                } else if (cwDistanceFromStart > mTotalCircleDegrees) { // If the user is touching outside of the start AND end
                    mUserIsMovingPointer = false;
                    return false;
                } else if ((touchEventRadius >= innerRadius) && (touchEventRadius <= outerRadius)) { // If the user is touching near the circle
                    setProgressBasedOnAngle(touchAngle);
                    lastCWDistanceFromStart = cwDistanceFromStart;
                    mIsMovingCW = true;
                    mPointerHaloPaint.setAlpha(mPointerAlphaOnTouch);
                    mPointerHaloPaint.setColor(mPointerHaloColorOnTouch);
                    recalculateAll();
                    invalidate();
                    if (mOnCircularSeekBarChangeListener != null) {
                        mOnCircularSeekBarChangeListener.onStartTrackingTouch(this);
                        mOnCircularSeekBarChangeListener.onProgressChanged(this, mProgress, true);
                    }
                    mUserIsMovingPointer = true;
                    lockAtEnd = false;
                    lockAtStart = false;
                } else { // If the user is not touching near the circle
                    mUserIsMovingPointer = false;
                    return false;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (mUserIsMovingPointer) {
                    if (lastCWDistanceFromStart < cwDistanceFromStart) {
                        if ((cwDistanceFromStart - lastCWDistanceFromStart) > 180f && !mIsMovingCW) {
                            lockAtStart = true;
                            lockAtEnd = false;
                        } else {
                            mIsMovingCW = true;
                        }
                    } else {
                        if ((lastCWDistanceFromStart - cwDistanceFromStart) > 180f && mIsMovingCW) {
                            lockAtEnd = true;
                            lockAtStart = false;
                        } else {
                            mIsMovingCW = false;
                        }
                    }

                    if (lockAtStart && mIsMovingCW) {
                        lockAtStart = false;
                    }
                    if (lockAtEnd && !mIsMovingCW) {
                        lockAtEnd = false;
                    }
                    if (lockAtStart && !mIsMovingCW && (ccwDistanceFromStart > 90)) {
                        lockAtStart = false;
                    }
                    if (lockAtEnd && mIsMovingCW && (cwDistanceFromEnd > 90)) {
                        lockAtEnd = false;
                    }
                    // Fix for passing the end of a semi-circle quickly
                    if (!lockAtEnd && cwDistanceFromStart > mTotalCircleDegrees && mIsMovingCW && lastCWDistanceFromStart < mTotalCircleDegrees) {
                        lockAtEnd = true;
                    }

                    if (lockAtStart && lockEnabled) {
                        // TODO: Add a check if mProgress is already 0, in which case don't call the listener
                        mProgress = 0;
                        recalculateAll();
                        invalidate();
                        if (mOnCircularSeekBarChangeListener != null) {
                            mOnCircularSeekBarChangeListener.onProgressChanged(this, mProgress, true);
                        }

                    } else if (lockAtEnd && lockEnabled) {
                        mProgress = mMax;
                        recalculateAll();
                        invalidate();
                        if (mOnCircularSeekBarChangeListener != null) {
                            mOnCircularSeekBarChangeListener.onProgressChanged(this, mProgress, true);
                        }
                    } else if ((mMoveOutsideCircle) || (touchEventRadius <= outerRadius)) {
                        if (!(cwDistanceFromStart > mTotalCircleDegrees)) {
                            setProgressBasedOnAngle(touchAngle);
                        }
                        recalculateAll();
                        invalidate();
                        if (mOnCircularSeekBarChangeListener != null) {
                            mOnCircularSeekBarChangeListener.onProgressChanged(this, mProgress, true);
                        }
                    } else {
                        break;
                    }

                    lastCWDistanceFromStart = cwDistanceFromStart;
                } else {
                    return false;
                }
                break;
            case MotionEvent.ACTION_UP:
                mPointerHaloPaint.setAlpha(mPointerAlpha);
                mPointerHaloPaint.setColor(mPointerHaloColor);
                if (mUserIsMovingPointer) {
                    mUserIsMovingPointer = false;
                    invalidate();
                    if (mOnCircularSeekBarChangeListener != null) {
                        mOnCircularSeekBarChangeListener.onStopTrackingTouch(this);
                    }
                } else {
                    return false;
                }
                break;
            case MotionEvent.ACTION_CANCEL: // Used when the parent view intercepts touches for things like scrolling
                mPointerHaloPaint.setAlpha(mPointerAlpha);
                mPointerHaloPaint.setColor(mPointerHaloColor);
                mUserIsMovingPointer = false;
                invalidate();
                break;
        }

        if (event.getAction() == MotionEvent.ACTION_MOVE && getParent() != null) {
            getParent().requestDisallowInterceptTouchEvent(true);
        }

        return true;
    }

    public int getProgress() {
        int progress = Math.round((float) mMax * mProgressDegrees / mTotalCircleDegrees);
        return progress;
    }

    public void setProgress(int progress) {
        if (mProgress != progress) {//小细节
            mProgress = progress;
            if (mOnCircularSeekBarChangeListener != null) {
                mOnCircularSeekBarChangeListener.onProgressChanged(this, progress, false);
            }
            recalculateAll();//重新计算
            invalidate();
        }
    }

    /**
     * 根据角度设置进度
     *
     * @param angle
     */
    private void setProgressBasedOnAngle(float angle) {
        mPointerPosition = angle;
        calculateProgressDegrees();
        mProgress = Math.round((float) mMax * mProgressDegrees / mTotalCircleDegrees);//凑整
    }

    /**
     * 重新计算所有
     */
    private void recalculateAll() {
        calculateTotalDegress();
        calculatePointerAngle();
        calculateProgressDegrees();

        initRects();
        initPath();

        calculatePointerXYPosition();
    }


    public boolean isLoackEnabled() {
        return lockEnabled;
    }

    public void setLockEnabled(boolean lockEnabled) {
        this.lockEnabled = lockEnabled;
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        Parcelable superState = super.onSaveInstanceState();

        Bundle state = new Bundle();
        state.putParcelable("PARENT", superState);
        state.putInt("MAX", mMax);
        state.putInt("PROGRESS", mProgress);
        state.putInt("mCircleColor", mCircleColor);
        state.putInt("mCircleProgressColor", mCircleProgressColor);
        state.putInt("mPointerColor", mPointerColor);
        state.putInt("mPointerHaloColor", mPointerHaloColor);
        state.putInt("mPointerHaloColorOnTouch", mPointerHaloColorOnTouch);
        state.putInt("mPointerAlpha", mPointerAlpha);
        state.putInt("mPointerAlphaOnTouch", mPointerAlphaOnTouch);
        state.putBoolean("lockEnabled", lockEnabled);

        return state;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        Bundle savedState = (Bundle) state;

        Parcelable superState = savedState.getParcelable("PARENT");
        super.onRestoreInstanceState(superState);

        mMax = savedState.getInt("MAX");
        mProgress = savedState.getInt("PROGRESS");
        mCircleColor = savedState.getInt("mCircleColor");
        mCircleProgressColor = savedState.getInt("mCircleProgressColor");
        mPointerColor = savedState.getInt("mPointerColor");
        mPointerHaloColor = savedState.getInt("mPointerHaloColor");
        mPointerHaloColorOnTouch = savedState.getInt("mPointerHaloColorOnTouch");
        mPointerAlpha = savedState.getInt("mPointerAlpha");
        mPointerAlphaOnTouch = savedState.getInt("mPointerAlphaOnTouch");
        lockEnabled = savedState.getBoolean("lockEnabled");

        initPaint();

        recalculateAll();
    }


    public void setOnSeekBarChangeListener(OnCircularSeekBarChangeListener l) {
        mOnCircularSeekBarChangeListener = l;
    }

    /**
     * Gets the circle color.
     *
     * @return An integer color value for the circle
     */
    public int getCircleColor() {
        return mCircleColor;
    }

    /**
     * Sets the circle color.
     *
     * @param color the color of the circle
     */
    public void setCircleColor(int color) {
        mCircleColor = color;
        mCirclePaint.setColor(mCircleColor);
        invalidate();
    }

    /**
     * Gets the circle progress color.
     *
     * @return An integer color value for the circle progress
     */
    public int getCircleProgressColor() {
        return mCircleProgressColor;
    }

    /**
     * Sets the circle progress color.
     *
     * @param color the color of the circle progress
     */
    public void setCircleProgressColor(int color) {
        mCircleProgressColor = color;
        mCircleProgressPaint.setColor(mCircleProgressColor);
        invalidate();
    }

    /**
     * Gets the pointer color.
     *
     * @return An integer color value for the pointer
     */
    public int getPointerColor() {
        return mPointerColor;
    }

    /**
     * Sets the pointer color.
     *
     * @param color the color of the pointer
     */
    public void setPointerColor(int color) {
        mPointerColor = color;
        mPointerPaint.setColor(mPointerColor);
        invalidate();
    }

    /**
     * Gets the pointer halo color.
     *
     * @return An integer color value for the pointer halo
     */
    public int getPointerHaloColor() {
        return mPointerHaloColor;
    }

    /**
     * Sets the pointer halo color.
     *
     * @param color the color of the pointer halo
     */
    public void setPointerHaloColor(int color) {
        mPointerHaloColor = color;
        mPointerHaloPaint.setColor(mPointerHaloColor);
        invalidate();
    }

    /**
     * Gets the pointer alpha value.
     *
     * @return An integer alpha value for the pointer (0..255)
     */
    public int getPointerAlpha() {
        return mPointerAlpha;
    }

    /**
     * Sets the pointer alpha.
     *
     * @param alpha the alpha of the pointer
     */
    public void setPointerAlpha(int alpha) {
        if (alpha >= 0 && alpha <= 255) {
            mPointerAlpha = alpha;
            mPointerHaloPaint.setAlpha(mPointerAlpha);
            invalidate();
        }
    }

    /**
     * Gets the pointer alpha value when touched.
     *
     * @return An integer alpha value for the pointer (0..255) when touched
     */
    public int getPointerAlphaOnTouch() {
        return mPointerAlphaOnTouch;
    }

    /**
     * Sets the pointer alpha when touched.
     *
     * @param alpha the alpha of the pointer (0..255) when touched
     */
    public void setPointerAlphaOnTouch(int alpha) {
        if (alpha >= 0 && alpha <= 255) {
            mPointerAlphaOnTouch = alpha;
        }
    }

    /**
     * Gets the circle fill color.
     *
     * @return An integer color value for the circle fill
     */
    public int getCircleFillColor() {
        return mCircleFillColor;
    }

    /**
     * Sets the circle fill color.
     *
     * @param color the color of the circle fill
     */
    public void setCircleFillColor(int color) {
        mCircleFillColor = color;
        mCircleFillPaint.setColor(mCircleFillColor);
        invalidate();
    }

    /**
     * Get the current max of the CircularSeekBar.
     *
     * @return Synchronized integer value of the max.
     */
    public synchronized int getMax() {
        return mMax;
    }

    /**
     * Set the max of the CircularSeekBar.
     * If the new max is less than the current progress, then the progress will be set to zero.
     * If the progress is changed as a result, then any listener will receive a onProgressChanged event.
     *
     * @param max The new max for the CircularSeekBar.
     */
    public void setMax(int max) {
        if (!(max <= 0)) { // Check to make sure it's greater than zero
            if (max <= mProgress) {
                mProgress = 0; // If the new max is less than current progress, set progress to zero
                if (mOnCircularSeekBarChangeListener != null) {
                    mOnCircularSeekBarChangeListener.onProgressChanged(this, mProgress, false);
                }
            }
            mMax = max;

            recalculateAll();
            invalidate();
        }
    }


}
