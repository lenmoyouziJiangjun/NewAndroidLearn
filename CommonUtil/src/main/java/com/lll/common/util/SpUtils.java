package com.lll.common.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Description: SharedPreferences工具类
 * Version:
 * Created by lll on 2016/4/11.
 * CopyRight lll
 */
public class SpUtils {

    private static SpUtils mInstance;
    private static Object lock = new Object();
    private SharedPreferences mSp;
    private SharedPreferences.Editor mEditor;

    private SpUtils(Context ctx) {
        mSp = ctx.getSharedPreferences("sp", Context.MODE_PRIVATE);
        mEditor = mSp.edit();
    }

    public static SpUtils getInstance(Context context) {
        if (mInstance == null) {
            synchronized (lock) {
                mInstance = new SpUtils(context);
            }
        }
        return mInstance;
    }

    public boolean putString(String key, String value) {
        mEditor.putString(key, value);
        return mEditor.commit();
    }

    public String getString(String key) {
        String str = mSp.getString(key, null);
        return str;
    }

    public String getString(String key, String def) {
        String str = mSp.getString(key, def);
        return str;
    }

    public boolean putBoolean(String key, boolean value) {
        mEditor.putBoolean(key, value);
        return mEditor.commit();
    }

    public boolean getBoolean(String key) {
        boolean res = mSp.getBoolean(key, false);
        return res;
    }

    public boolean getBoolean(String key, boolean def) {
        boolean res = mSp.getBoolean(key, def);
        return res;
    }

    public Integer getInteger(String key) {
        Integer res = mSp.getInt(key, 0);
        return res;
    }

    public boolean putInteger(String key, Integer value) {
        mEditor.putInt(key, value);
        return mEditor.commit();
    }

    public boolean putLong(String key, long value) {
        mEditor.putLong(key, value);
        return mEditor.commit();
    }

//    public boolean putDouble(String key, double value) {
//        mEditor.put
//        return mEditor.commit();
//    }


    public long getLong(String key) {
        return mSp.getLong(key, 0);
    }


    public boolean contains(String key) {
        return mSp.contains(key);
    }

}
