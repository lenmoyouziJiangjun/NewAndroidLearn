package com.lll.common.util;

import android.os.Bundle;

import com.lll.common.util.logger.LogUtils;

import java.util.Set;

/**
 * Version 1.0
 * Created by lll on 16/7/18.
 * Description
 * copyright generalray4239@gmail.com
 */
public class PrintUtils {


    private PrintUtils(){}

    /**
     * 输出bundle所有参数类型
     * @param bundle
     */
    public static void printBundle(Bundle bundle){
        Set<String> keys= bundle.keySet();
        for (String key: keys) {
            Object value=bundle.get(key);
            LogUtils.d("key======"+key+"=======value====="+String.valueOf(value));
        }
    }

}
