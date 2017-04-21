package com.lll.core.activity.test;

import com.lll.learn.base.BaseListStringActivity;

import java.util.LinkedHashMap;
import java.util.Map;

public class CustomViewActivity extends BaseListStringActivity {


    @Override
    public Map<String, Class> getDatas() {
        Map<String, Class> maps = new LinkedHashMap<>(5);
        maps.put("系统自带控件", SystemWidgetActivity.class);
        maps.put("progressView", ProgressViewActivity.class);
        maps.put("缩略图测试", WindowActivity.class);

        return maps;
    }
}
