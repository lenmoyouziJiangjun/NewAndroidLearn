package com.lll.common.test;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.lll.common.viewpager.R;

public class ViewPagerActivity extends AppCompatActivity {



    String[] titles={"广告banner",
            "横向引导页",
            "竖向引导页"

    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager);
    }
}
