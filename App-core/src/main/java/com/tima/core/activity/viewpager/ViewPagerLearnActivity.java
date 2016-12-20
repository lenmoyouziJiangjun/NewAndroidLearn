package com.tima.core.activity.viewpager;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.lll.learn.base.BaseListStringActivity;

import java.util.HashMap;
import java.util.Map;

public class ViewPagerLearnActivity extends BaseListStringActivity {


    @Override
    public Map<String, Class> getDatas() {
        Map map = new HashMap();
        map.put("ViewPager指示器",ViewPagerIndicatorActivity.class);
        map.put("ViewPager引导页", UserGuideActivity.class);
        map.put("tabLayout效果合集",TabLayoutTestActivity.class);
        return map;
    }
}
