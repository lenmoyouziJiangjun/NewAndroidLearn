package com.lll.learn.v7;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.lll.learn.base.BaseListStringActivity;

import java.util.LinkedHashMap;
import java.util.Map;

public class V7LearnActivity extends BaseListStringActivity {


    @Override
    public Map<String, Class> getDatas() {
        Map<String, Class> maps = new LinkedHashMap<>(5);
        maps.put("ToolBar学习",ToolBarLearnActivity.class);
        maps.put("ToolBar学习2",ToolViewPagerActivity.class);
        maps.put("GridLayout学习",GridLayoutActivity.class);
        return maps;
    }
}
