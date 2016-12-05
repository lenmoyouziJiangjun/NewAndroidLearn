package com.tima.core.activity.test;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.lll.learn.base.BaseListStringActivity;
import com.lll.learn.v4.DragLayoutActivity;
import com.lll.learn.v4.DrawableCompatActivity;
import com.lll.learn.v4.NestedScrollActivity;
import com.lll.learn.v4.SwipeRefreshLayoutActivity;
import com.tima.core.R;

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
