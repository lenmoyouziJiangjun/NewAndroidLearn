package com.lll.learn.v4;

import com.lll.learn.base.BaseListStringActivity;
import com.lll.learn.v7.GridLayoutActivity;
import com.lll.learn.v7.ToolBarLearnActivity;
import com.lll.learn.v7.ToolViewPagerActivity;

import java.util.LinkedHashMap;
import java.util.Map;

public class V4LearnActivity extends BaseListStringActivity {


    @Override
    public Map<String, Class> getDatas() {
        Map<String, Class> maps = new LinkedHashMap<>(5);
        maps.put("DrawableTintLearn", DrawableCompatActivity.class);
        maps.put("NestedScroll测试",NestedScrollActivity.class);
        return maps;
    }
}
