package com.lll.common.util;

/**
 * Version 1.0
 * Created by lll on 16/6/28.
 * Description
 * copyright generalray4239@gmail.com
 */
public class CheckNullUtil {
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
}
