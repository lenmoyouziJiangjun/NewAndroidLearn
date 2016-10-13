package com.lll.common.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;

import static android.view.WindowManager.LayoutParams.FLAG_BLUR_BEHIND;

/**
 * Version 1.0
 * Created by lll on 16/10/11.
 * Description 类似IOS那种底部的Dialog
 * copyright generalray4239@gmail.com
 */

public class BottomDialog extends Dialog {
    public BottomDialog(Context context) {
        this(context,0);
    }

    public BottomDialog(Context context, int themeResId) {
        super(context, themeResId);
        initView(context);
        //设置自定义View
//        View view =LayoutInflater.from(context).inflate();
//        setContentView(view);
    }


    private void initView(Context context){
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        //Make sure dialog to be bottom and match width
        params.gravity = Gravity.BOTTOM | Gravity.FILL_HORIZONTAL;
//        getWindow().setBackgroundDrawable(null);这段代码有手机适配,替换成下面代码,也可以在style中指定
//        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        //Make sure dim background
        params.flags |= WindowManager.LayoutParams.FLAG_DIM_BEHIND;//在window下面的变暗
        getWindow().setAttributes(params);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

}
