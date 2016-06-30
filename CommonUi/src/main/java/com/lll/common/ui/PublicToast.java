package com.lll.common.ui;

import android.content.Context;
import android.widget.Toast;

/**
 * Description: 公共Toast，
 * Version:
 * Created by lll on 2016/5/3.
 * CopyRight lll
 */
public class PublicToast {
    private static Toast mToast;


    /**
     * 显示toast，解决连续点击，弹出多个toast的问题
     *
     * @param context
     * @param content
     */
    public static void showToast(Context context, String content) {
        if (mToast == null) {
            mToast = Toast.makeText(context, content, Toast.LENGTH_SHORT);
        } else {
            mToast.setText(content);
            mToast.setDuration(Toast.LENGTH_SHORT);
        }
        mToast.show();
    }



}
