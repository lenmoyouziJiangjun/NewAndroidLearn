package com.tima.core.activity.test;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.lll.common.ui.progressView.CircularSeekBar;
import com.tima.core.R;
import com.tima.core.base.BaseActivity;


/**
 * Version 1.0
 * Created by lll on 16/9/2.
 * Description 进度条测试
 * copyright generalray4239@gmail.com
 */
public class ProgressViewActivity extends BaseActivity {

    public static final String tag = ProgressViewActivity.class.getSimpleName();

    private CircularSeekBar mSeekBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress_view);
        initSeekBar();
    }

    private static int progress;

    private Runnable mUpdateCircularProgress = new Runnable() {
        @Override
        public void run() {
            if (mSeekBar != null) {
                progress++;
//                int p = progress % 360;
                mSeekBar.setProgress(progress);
            }
        }
    };

    private void initSeekBar() {
        mSeekBar = (CircularSeekBar) findViewById(R.id.song_progress_circular);
        mSeekBar.setOnSeekBarChangeListener(new CircularSeekBar.OnCircularSeekBarChangeListener() {
            @Override
            public void onProgressChanged(CircularSeekBar seekBar, int progress, boolean fromUser) {
//                Log.e("lll", tag + "-----seekBar---" + progress);
            }

            @Override
            public void onStopTrackingTouch(CircularSeekBar seekBar) {

            }

            @Override
            public void onStartTrackingTouch(CircularSeekBar seekBar) {

            }
        });
        mSeekBar.postDelayed(mUpdateCircularProgress, 50);
    }
}
