package com.lll.core.activity.test;

import android.os.Bundle;

import com.lll.common.util.ScreenUtils;
import com.lll.common.util.LogUtils;
import com.tima.core.R;
import com.lll.core.base.BaseActivity;

/**
 * Version 1.0
 * Created by lll on 16/11/28.
 * Description
 * copyright generalray4239@gmail.com
 */

public class WindowActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_window);
//        test();
//        getWindowInfo();
    }


//    private void test() {
//        ImageView img = (ImageView) findViewById(R.id.img);
//        img.setImageBitmap(BannerActivity.mThum);
//    }

    private void getWindowInfo() {
        int[] a = ScreenUtils.getWindowWidthAndHeight(this);
        LogUtils.e("宽度-------" + a[0]);
        LogUtils.e("高度-------" + a[1]);
    }


}
