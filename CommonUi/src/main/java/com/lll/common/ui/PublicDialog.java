package com.lll.common.ui;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

/**
 * Version 1.0
 * Created by lll on 16/9/27.
 * Description 定义公共dialog
 * copyright generalray4239@gmail.com
 */

public class PublicDialog extends Dialog {

    public PublicDialog(Context context) {
        super(context);
    }

    public PublicDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    protected PublicDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    private void initDialog(){
        Window dialogWindow=getWindow();
        //设置屏幕中的位置
        dialogWindow.setGravity(Gravity.LEFT | Gravity.TOP);
        //设置基本属性
        final WindowManager.LayoutParams attrs = dialogWindow.getAttributes();
        attrs.width = (int)(getContext().getResources().getDisplayMetrics().widthPixels*0.6);
        attrs.height = 100;
        attrs.alpha = 50;//透明度
        attrs.x = 100;
        attrs.x = 200;
        dialogWindow.setAttributes(attrs);
    }
}
