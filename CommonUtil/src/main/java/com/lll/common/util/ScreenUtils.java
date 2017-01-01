package com.lll.common.util;

import android.content.Context;
import android.view.WindowManager;

/**
 * Version 1.0
 * Created by lll on 16/12/5.
 * Description 屏幕工具类
 * <p>getWindowWidthAndHeight 获取屏幕宽高</p>
 * <p>getScreenDensityDpi 获取屏幕密度</p>
 * <p>sp2dp sp和dp之间的互转</p>
 * copyright generalray4239@gmail.com
 */

public class ScreenUtils {

    public static WindowManager getWindowManager(Context ctx) {
        return (WindowManager) ctx.getSystemService(Context.WINDOW_SERVICE);
    }


    /**
     * 获取屏幕宽高
     *
     * @param ctx
     * @return int[0] width; int[1] height
     */
    public static int[] getWindowWidthAndHeight(Context ctx) {
        WindowManager manager = getWindowManager(ctx);
        int[] attrs = new int[2];
        attrs[1] = manager.getDefaultDisplay().getHeight();
        attrs[0] = manager.getDefaultDisplay().getWidth();
        return attrs;
    }

    /**
     * 获取屏幕密度
     *
     * @param ctx
     * @return
     */
    public static int getScreenDensityDpi(Context ctx) {
        return ctx.getResources().getDisplayMetrics().densityDpi;
    }

    /**
     * 像素转dp
     *
     * @param sp
     * @return
     */
    public static int sp2dp(Context ctx, int sp) {
        return sp / (getScreenDensityDpi(ctx) / 160);
    }

    /**
     * dp转像素 公式为：px = dp*(dpi/160)
     *
     * @param dp
     * @return
     */
    public static int dp2sp(Context ctx, int dp) {
        return dp * (getScreenDensityDpi(ctx) / 160);
    }

    /**
     * 获取屏幕方向
     * @param ctx
     * @return
     */
    public static int getScreenOrientation(Context ctx) {
        WindowManager manager = getWindowManager(ctx);
        return manager.getDefaultDisplay().getOrientation();
    }

}
