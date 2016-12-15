package com.lll.common.util;

import android.app.Activity;
import android.content.Context;
import android.os.IBinder;
import android.view.inputmethod.InputMethodManager;

/**
 * Version 1.0
 * Created by lll on 16/12/9.
 * Description
 * copyright generalray4239@gmail.com
 */

public class KeyBoardUtils {

    public static void switchSoftKeyBoard(Activity context) {
        IBinder token = context.getCurrentFocus() == null ? null : context.getCurrentFocus().getWindowToken();
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInputFromWindow(token,0,0);
//        imm.hideSoftInputFromWindow(token,0);
    }

}
