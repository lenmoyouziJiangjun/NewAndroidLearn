package com.lll.common.ui.dialog;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

/**
 * Version 1.0
 * Created by lll on 16/11/14.
 * Description
 * copyright generalray4239@gmail.com
 */

public class SingleContainer {
    public static SingleContainer INSTANCE = new SingleContainer();
    public Handler mainHandler;
    public Context context;

    public static Handler getMainHandler() {
        if (INSTANCE.mainHandler == null) {
            INSTANCE.mainHandler = new Handler(Looper.getMainLooper());
        }
        return INSTANCE.mainHandler;
    }

    public static void init(Context context) {
        if (context != null && INSTANCE.context == null) {
            INSTANCE.context = context.getApplicationContext();
        }
    }

    public static Context getApplicationContext() {
        if (INSTANCE.context == null) {
            throw new IllegalStateException("请先调用 init(context)方法初始化");
        }
        return INSTANCE.context;
    }
}
