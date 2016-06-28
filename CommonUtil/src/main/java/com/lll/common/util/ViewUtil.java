package com.lll.common.util;

import android.view.View;

/**
 * Version 1.0
 * Created by lll on 16/6/28.
 * Description
 * copyright generalray4239@gmail.com
 */
public class ViewUtil {

    /**
     * 查找View
     * @param v
     * @param id
     * @param <T>
     * @return
     */
    public static <T>T findViewById(View v, int id){

        return  (T)v.findViewById(id);
    }

}
