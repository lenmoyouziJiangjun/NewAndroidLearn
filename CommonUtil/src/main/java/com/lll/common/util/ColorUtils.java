package com.lll.common.util;

import android.content.res.ColorStateList;

/**
 * Version 1.0
 * Created by lll on 16/12/8.
 * Description 颜色工具类
 * copyright generalray4239@gmail.com
 */

public class ColorUtils {


    /**
     * 创建一个 ColorStateList
     *
     * @param state  不同的状态
     * @param colors 不同状态对应的颜色
     * @return
     */
    public static ColorStateList createColorStateList(int[][] state, int[] colors) {
        return new ColorStateList(state, colors);
    }



}
