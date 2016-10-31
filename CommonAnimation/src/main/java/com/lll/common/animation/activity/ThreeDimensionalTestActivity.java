package com.lll.common.animation.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.lll.common.animation.R;
/**
 * Description: 3D动画效果
 * Version:
 * Created by lll on 2016/2/2.
 * CopyRight lll
 */
public class ThreeDimensionalTestActivity extends AppCompatActivity {

    private static ThreeDimensionalTestActivity sMainActivity;

    public static ThreeDimensionalTestActivity getInstance() {
        return sMainActivity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sMainActivity = this;
        setContentView(R.layout.activity_three_dimensional_test);
    }
}
