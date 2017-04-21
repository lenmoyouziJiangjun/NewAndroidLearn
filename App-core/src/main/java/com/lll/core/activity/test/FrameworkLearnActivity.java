package com.lll.core.activity.test;

import com.lll.learn.base.BaseListStringActivity;
import com.lll.systemview.learn.testactivity.SwipeDismissLayoutTestActivity;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Version 1.0
 * Created by lll on 16/12/14.
 * Description
 * copyright generalray4239@gmail.com
 */

public class FrameworkLearnActivity extends BaseListStringActivity {



    @Override
    public Map<String, Class> getDatas() {
        Map<String, Class> datas = new LinkedHashMap<>();
        datas.put("SwipeDismissLayout学习", SwipeDismissLayoutTestActivity.class);
        return datas;
    }
}
