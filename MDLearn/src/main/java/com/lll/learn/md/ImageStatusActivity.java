package com.lll.learn.md;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.lll.common.util.StatusBarUtil;
import com.lll.learn.R;

public class ImageStatusActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.setTransparent(this);
        setContentView(R.layout.activity_image_status);

    }
}
