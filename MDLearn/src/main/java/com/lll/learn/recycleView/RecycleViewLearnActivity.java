package com.lll.learn.recycleView;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.lll.learn.AnimatedRecyclerView;
import com.lll.learn.AsyncListUtilActivity;
import com.lll.learn.GridLayoutManagerActivity;
import com.lll.learn.LinearLayoutManagerActivity;
import com.lll.learn.R;
import com.lll.learn.StaggeredGridLayoutManagerActivity;
import com.lll.learn.base.BaseListStringActivity;

import java.util.LinkedHashMap;
import java.util.Map;

public class RecycleViewLearnActivity extends BaseListStringActivity {



    @Override
    public Map<String, Class> getDatas() {
        Map datas = new LinkedHashMap(10);
        datas.put("RecycleView动画", AnimatedRecyclerView.class);
        datas.put("异步加载AsyncListUtil", AsyncListUtilActivity.class);
        datas.put("GridLayoutManager的使用", GridLayoutManagerActivity.class);
        datas.put("LinearLayoutManager的使用", LinearLayoutManagerActivity.class);
        datas.put("StaggeredGridLayoutManager流水布局", StaggeredGridLayoutManagerActivity.class);
        datas.put("item的拖拽学习",DragAndDropActivity.class);
        datas.put("item的左右滑动学习",SwipeToDismissActivity.class);
        return datas;
    }
}
