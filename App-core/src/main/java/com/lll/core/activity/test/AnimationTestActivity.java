package com.lll.core.activity.test;

import com.lll.common.animation.activity.CustomerAnimationTestActivity;
import com.lll.common.animation.activity.PropertyAnimationTestActivity;
import com.lll.common.animation.activity.ThreeDimensionalTestActivity;
import com.lll.common.animation.activity.ViewAnimationTestActivity;
import com.lll.learn.base.BaseListStringActivity;


import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Version 1.0
 * Created by lll on 16/9/2.
 * Description 动画学习
 * copyright generalray4239@gmail.com
 */
public class AnimationTestActivity extends BaseListStringActivity {


    @Override
    public Map<String, Class> getDatas() {
        Map<String, Class> map = new LinkedHashMap<>(10);
        map.put("View Animation ", ViewAnimationTestActivity.class);
        map.put("Property Animation ", PropertyAnimationTestActivity.class);
        map.put("3D Animation", ThreeDimensionalTestActivity.class);
        map.put("Custom Animation", CustomerAnimationTestActivity.class);
        return map;
    }

}
