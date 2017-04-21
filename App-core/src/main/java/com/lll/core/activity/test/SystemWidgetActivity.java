package com.lll.core.activity.test;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.lll.common.ui.MusicVisualizer;
import com.lll.common.ui.imageView.CircleImageView;
import com.tima.core.R;
import com.lll.core.base.BaseActivity;

/**
 * Version 1.0
 * Created by lll on 16/10/19.
 * Description 系统控件加强的activity
 * copyright generalray4239@gmail.com
 */

public class SystemWidgetActivity extends BaseActivity {
    MusicVisualizer musicVisualizer;
    FrameLayout frameLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewdemo);

        musicVisualizer = (MusicVisualizer) findViewById(R.id.playing);
        frameLayout = (FrameLayout) findViewById(R.id.fl_t);
        createProgressView();
        testCircleImage();
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (musicVisualizer.getVisibility() == View.VISIBLE) {
                musicVisualizer.setVisibility(View.GONE);
            } else {
                musicVisualizer.setVisibility(View.VISIBLE);
            }
        }
        return super.onTouchEvent(event);
    }


    private void createProgressView() {
        CircleImageView imageView = new CircleImageView(this, 0xFFFAFAFA, 50);
        imageView.setImageDrawable(getResources().getDrawable(R.drawable.empty_photo));
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(50, 50);
        imageView.setLayoutParams(params);
        frameLayout.addView(imageView);
    }

    private void testCircleImage() {
//        CircleImageView2 c = (CircleImageView2) findViewById(R.id.civ);
//        c.setImageDrawable(getResources().getDrawable(R.drawable.bg_girl));
    }
}
