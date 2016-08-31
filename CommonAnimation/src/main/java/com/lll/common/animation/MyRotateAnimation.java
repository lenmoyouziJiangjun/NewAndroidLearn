package com.lll.common.animation;

import android.view.animation.Animation;
import android.view.animation.Transformation;

/**
 * Description:自定义旋转动画
 * Version:
 * Created by lll on 2016/2/2.
 * CopyRight lll
 */
public class MyRotateAnimation extends Animation {
    /*起始度数*/
    private float mFromDegrees;
    private float mToDegrees;

    /*中心点*/
    private float mPivotX;
    private float mPivotY;

    /*x坐标类型
       One of Animation.ABSOLUTE, Animation.RELATIVE_TO_SELF, or Animation.RELATIVE_TO_PARENT.
    和值*/
    private int mPivotXType;
    private float mPivotXValue;

    private int mPivotYType;
    private float mPivotYValue;

    private boolean mCancelled;

    private float mDegree;

    /**
     * 代码定义的动画属性
     * @param fromDegrees
     * @param toDegrees
     * @param pivotXType
     * @param pivotXValue
     * @param pivotYType
     * @param pivotYValue
     */
    public MyRotateAnimation(float fromDegrees, float toDegrees, int pivotXType, float pivotXValue,
                             int pivotYType, float pivotYValue) {
        mFromDegrees = fromDegrees;
        mToDegrees = toDegrees;
        mPivotXType = pivotXType;
        mPivotXValue = pivotXValue;
        mPivotYType = pivotYType;
        mPivotYValue = pivotYValue;
        mCancelled = false;
        mDegree = fromDegrees;
    }

    public void initialize(int width, int height, int parentWidth, int parentHeight) {
        super.initialize(width, height, parentWidth, parentHeight);
        mPivotX = resolveSize(mPivotXType, mPivotXValue, width, parentWidth);
        mPivotY = resolveSize(mPivotYType, mPivotYValue, height, parentHeight);
    }

    public float getDegree() {
        return mDegree;
    }

    @Override
    public void cancel() {
        super.cancel();
        mCancelled = true;
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        if (!mCancelled) {
            mDegree = mFromDegrees + ((mToDegrees - mFromDegrees) * interpolatedTime);
        }
        t.getMatrix().setRotate(mDegree, mPivotX, mPivotY);
    }

}
