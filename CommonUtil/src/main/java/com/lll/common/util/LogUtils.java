package com.lll.common.util;


import android.util.Log;

//import com.lll.common.util.BuildConfig;

/**
 * Description: log工具类
 * Version:
 * Created by lll on 2016/4/11.
 * CopyRight lll
 */
public class LogUtils {

    public static boolean isDebug = false;
    public static String TAG = "lll";
    public static String logSplitStr = "-----";

    /**
     * 获取log前缀
     *
     * @return
     */
    private static String getLogPrefix() {
        StackTraceElement stackTraceElement = Thread.currentThread().getStackTrace()[4];
        String className = stackTraceElement.getClassName();

        String simpleClassName = className.substring(className.lastIndexOf(".") + 1);
        String methodName = stackTraceElement.getMethodName();
        int num = stackTraceElement.getLineNumber();
        return simpleClassName + logSplitStr + methodName + logSplitStr + num + "\n";
    }


    public static void v(String msg) {
        if (isDebug) {
            String prefix = getLogPrefix();
            Log.v(TAG, prefix + msg);
        }
    }

    public static void v(String tag, String msg) {
        if (isDebug) {
            String prefix = getLogPrefix();
            Log.v(tag, prefix + msg);
        }
    }

    public static void d(String msg) {
        if (isDebug) {
            String prefix = getLogPrefix();
            Log.d(TAG, prefix + msg);
        }
    }

    public static void d(String tag, String msg) {
        if (isDebug) {
            String prefix = getLogPrefix();
            Log.d(tag, prefix + msg);
        }
    }

    public static void i(String msg) {
        if (isDebug) {
            String prefix = getLogPrefix();
            Log.i(TAG, prefix + msg);
        }
    }

    public static void i(String tag, String msg) {
        if (isDebug) {
            String prefix = getLogPrefix();
            Log.i(tag, prefix + msg);
        }
    }

    public static void w(String msg) {
        if (isDebug) {
            String prefix = getLogPrefix();
            Log.w(TAG, prefix + msg);
        }
    }

    public static void w(String tag, String msg) {
        if (isDebug) {
            String prefix = getLogPrefix();
            Log.w(tag, prefix + msg);
        }
    }

    public static void e(String msg) {
        if (isDebug) {
            String prefix = getLogPrefix();
            Log.e(TAG, prefix + msg);
        }
    }

    /**
     * @param params 可变参数，支持两个参数，如果一个参数，就只传log信息，如果两个，第一个是tag名称，第二个是log信息
     */
    public static void e(String... params) {
        if (isDebug) {
            String prefix = getLogPrefix();
            if (params.length == 2) {
                Log.e(params[0], prefix + params[1]);
            } else {
                Log.e(TAG, prefix + params[0]);
            }
        }
    }

    /**
     * @param e
     * @param params 可变参数，支持两个参数，如果一个参数，就只传log信息，如果两个，第一个是tag名称，第二个是log信息
     */
    public static void e(Throwable e, String... params) {
        if (isDebug) {
            String prefix = getLogPrefix();
            if (params.length == 2) {
                Log.e(params[0], prefix + params[1], e);
            } else {
                Log.e(TAG, prefix + params[0], e);
            }
        }
    }


    private static void writeLog2file(){}

}
