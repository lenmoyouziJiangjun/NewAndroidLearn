package com.lll.learn.md;


import com.lll.learn.base.BaseListStringActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MDLearnActivity extends BaseListStringActivity {



    @Override
    public Map getDatas() {
        Map<String,Class> datas=new HashMap();
        datas.put("状态栏沉浸效果",StatylePicActivity.class);
        datas.put("状态栏显示",TestStatus2Activity.class);
        datas.put("状态栏延升",ImageStatusActivity.class);
        datas.put("详情界面模板",DetailTemActivity.class);
        return datas;
    }


}
