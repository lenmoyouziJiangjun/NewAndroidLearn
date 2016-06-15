package com.tima.common.util;

import android.view.View;

/**
 * Description:
 * Version:
 * Created by lll on 2016/5/4.
 * CopyRight lll
 */
public class CommonUtils {

    /**
     * 非空校验
     * @param t
     * @param <T>
     * @return
     */
    public static <T> T checkNotNull(T t){
        if (t==null){
            throw new NullPointerException();
        }
        return t;
    }


    public static <T>T findViewById(View v, int id){
       return  (T)v.findViewById(id);
    }
}
