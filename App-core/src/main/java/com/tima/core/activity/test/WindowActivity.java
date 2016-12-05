package com.tima.core.activity.test;

import android.os.Bundle;
import android.widget.ImageView;

import com.tima.core.R;
import com.tima.core.activity.BannerActivity;
import com.tima.core.base.BaseActivity;

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
        test();
    }

    private void test() {
        ImageView img = (ImageView) findViewById(R.id.img);
        img.setImageBitmap(BannerActivity.mThum);
    }


}
