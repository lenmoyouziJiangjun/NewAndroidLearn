package com.lll.common.ui.utils;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.AnimRes;
import android.support.annotation.AttrRes;
import android.support.annotation.ColorInt;
import android.support.annotation.FloatRange;
import android.util.Log;

/**
 * Version 1.0
 * Created by lll on 16/10/26.
 * Description
 * copyright generalray4239@gmail.com
 */

public class ColorUtils {

    private static final String TAG = ColorUtils.class.getSimpleName();

    /**
     * 调整颜色的透明度
     *
     * @param color
     * @param factor
     * @return
     */
    public static int adjustAlpha(@ColorInt int color, @FloatRange(from = 0.0f, to = 1.0f) float factor) {
        int alpha = Math.round(Color.alpha(color) * factor);
        int red = Color.red(color);
        int green = Color.green(color);
        int blue = Color.blue(color);
        return Color.argb(alpha, red, green, blue);
    }

    public static int resolveColor(Context context, @AttrRes int attr) {
        return resolveColor(context, attr, 0);
    }

    /**
     * 获取配置的颜色
     *
     * @param ctx
     * @param attr
     * @param defValue 默认颜色
     * @return
     */
    public static int resolveColor(Context ctx, @AttrRes int attr, int defValue) {
        TypedArray ta = ctx.obtainStyledAttributes(new int[]{attr});
        try {
            return ta.getColor(0, defValue);
        } finally {
            ta.recycle();
        }
    }

    public static int shiftColor(@ColorInt int color, @FloatRange(from = 0.0f, to = 2.0f) float by) {
        if (by == 1f) {
            return color;
        }
        float[] hsv = new float[3];
        /*将rgb转为hsv:Hue色度，Saturation饱和度，Value 纯度
        *     hsv[0] is Hue [0 .. 360)
        *     hsv[1] is Saturation [0...1]
        *     hsv[2] is Value [0...1]
        *     */
        Color.colorToHSV(color, hsv);
        Log.e("lll", TAG + "H==" + hsv[0] + "    s==" + hsv[1] + "     v==" + hsv[2]);
        hsv[2] *= by;//调整饱和度
        return Color.HSVToColor(hsv);
    }


    /**
     * 是否是亮色
     *
     * @param color
     * @return
     */
    public static boolean isColorLight(@ColorInt int color) {
        if (color == Color.BLACK) {
            return false;
        } else if (color == Color.WHITE || color == Color.TRANSPARENT) {
            return true;
        }
        final double darkness = 1 - (0.299 * Color.red(color) + 0.587 * Color.green(color) + 0.114 * Color.blue(color)) / 255;
        return darkness < 0.4;
    }


    /**
     * 相反颜色
     */
    @ColorInt
    public static int invertColor(@ColorInt int color) {
        final int r = 255 - Color.red(color);
        final int g = 255 - Color.green(color);
        final int b = 255 - Color.blue(color);
        return Color.argb(Color.alpha(color), r, g, b);
    }

    /**
     * 去掉透明度
     * @param color
     * @return
     */
    public static int stripAlpha(@ColorInt int color) {
        return Color.rgb(Color.red(color), Color.green(color), Color.blue(color));
    }


    /**
     *
     * @param color1
     * @param color2
     * @param ratio
     * @return
     */
    public static int blendColors(int color1, int color2, float ratio) {
        final float inverseRatio = 1f - ratio;
        float a = (Color.alpha(color1) * inverseRatio) + (Color.alpha(color2) * ratio);
        float r = (Color.red(color1) * inverseRatio) + (Color.red(color2) * ratio);
        float g = (Color.green(color1) * inverseRatio) + (Color.green(color2) * ratio);
        float b = (Color.blue(color1) * inverseRatio) + (Color.blue(color2) * ratio);
        return Color.argb((int) a, (int) r, (int) g, (int) b);
    }

}
