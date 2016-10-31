package com.tima.core.activity;

import com.lll.learn.base.BaseListStringActivity;
import com.lll.learn.md.MDLearnActivity;
import com.lll.learn.recycleView.RecycleViewLearnActivity;
import com.lll.learn.v4.DrawableCompatActivity;
import com.lll.learn.v4.V4LearnActivity;
import com.lll.learn.v7.V7LearnActivity;
import com.tima.core.activity.test.AnimationTestActivity;
import com.tima.core.activity.test.CustomViewActivity;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Version 1.0
 * Created by lll on 16/9/2.
 * Description
 * copyright generalray4239@gmail.com
 */
public class BannerActivity extends BaseListStringActivity {
    @Override
    public Map<String, Class> getDatas() {
        Map<String,Class> map = new LinkedHashMap<>(10);
        map.put("MD学习", MDLearnActivity.class);
        map.put("support v7 包学习", V7LearnActivity.class);
        map.put("support v4 包学习", V4LearnActivity.class);
        map.put("RecycleView的学习", RecycleViewLearnActivity.class);
        map.put("图片加载", BitmapLoaderActivity.class);
        map.put("进入主界面", MainActivity.class);
        map.put("View效果合集", CustomViewActivity.class);
        map.put("动画效果",AnimationTestActivity.class);

        return map;
    }
}
