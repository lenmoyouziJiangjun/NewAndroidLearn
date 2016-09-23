package com.tima.core.activity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.view.MotionEvent;
import android.view.View;

import com.lll.common.ui.MusicVisualizer;
import com.tima.core.R;
import com.tima.core.base.BaseActivity;

/**
 * Version 1.0
 * Created by lll on 16/9/23.
 * Description  一些特殊View效果合集
 * copyright generalray4239@gmail.com
 */

public class ViewDemoActivity extends BaseActivity {
    MusicVisualizer musicVisualizer;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewdemo);

        musicVisualizer = (MusicVisualizer) findViewById(R.id.playing);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_DOWN) {
            if (musicVisualizer.getVisibility() == View.VISIBLE) {
                musicVisualizer.setVisibility(View.GONE);
            } else {
                musicVisualizer.setVisibility(View.VISIBLE);
            }
        }
        return super.onTouchEvent(event);
    }
}
