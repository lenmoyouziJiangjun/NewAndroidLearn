package com.lll.common.animation.timely;

import android.animation.TypeEvaluator;

/**
 * Version 1.0
 * Created by lll on 16/9/30.
 * Description 自定义动画的属性变化的处理逻辑
 * copyright generalray4239@gmail.com
 */

public class TimelyEvaluator implements TypeEvaluator<float[][]> {

    private float[][] _cachedPoints = null;

    /**
     * @param fraction   插值器计算出来的比例因子
     * @param startValue  开始值
     * @param endValue    结束值
     * @return
     */
    @Override
    public float[][] evaluate(float fraction, float[][] startValue, float[][] endValue) {
        int pointsCount = startValue.length;
        initCache(pointsCount);
        for (int i = 0; i < pointsCount; i++) {
            _cachedPoints[i][0] = startValue[i][0] + fraction * (endValue[i][0] - startValue[i][0]);
            _cachedPoints[i][1] = startValue[i][1] + fraction * (endValue[i][1] - startValue[i][1]);
        }
        return _cachedPoints;
    }

    private void initCache(int pointsCount) {
        if (_cachedPoints == null || _cachedPoints.length != pointsCount) {
            _cachedPoints = new float[pointsCount][2];
        }
    }

}
