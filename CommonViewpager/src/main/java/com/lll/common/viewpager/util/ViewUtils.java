package com.lll.common.viewpager.util;

import android.content.Context;

public class ViewUtils {


    public static int sp2dp(Context ctx, int sp){
        float density=ctx.getResources().getDisplayMetrics().density;
        return (int)(density*sp+0.5);
    }

}
