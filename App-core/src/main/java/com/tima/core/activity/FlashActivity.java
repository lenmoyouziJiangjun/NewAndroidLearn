package com.tima.core.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.MotionEvent;
import android.widget.ListView;

import com.lll.learn.AsyncListUtilActivity;
import com.lll.learn.DrawerLayoutActivity;
import com.lll.learn.GridLayoutManagerActivity;
import com.lll.learn.LinearLayoutManagerActivity;
import com.lll.learn.StaggeredGridLayoutManagerActivity;
import com.lll.learn.base.BaseListStringActivity;
import com.lll.learn.md.MDLearnActivity;
import com.lll.learn.recycleView.RecycleViewLearnActivity;
import com.lll.learn.v4.DrawableCompatActivity;
import com.lll.learn.v4.NestedScrollActivity;
import com.lll.learn.v7.V7LearnActivity;
import com.tima.core.base.BaseActivity;
import com.tima.core.learn.AnimatedRecyclerView;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Description: 闪屏页面
 * Version:
 * Created by lll on 2016/5/3.
 * CopyRight lll
 */
public class FlashActivity extends BaseListStringActivity {



    @Override
    public Map<String, Class> getDatas() {
        Map<String,Class> map = new LinkedHashMap<>(10);
        map.put("图片tint",DrawableCompatActivity.class);
        map.put("状态栏", MDLearnActivity.class);
        map.put("support v7 包学习", V7LearnActivity.class);
        map.put("RecycleView的学习", RecycleViewLearnActivity.class);
        map.put("图片加载", BitmapLoaderActivity.class);


        return map;
    }

    private void startLogin() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
//                Intent intent = new Intent(FlashActivity.this,AsyncListUtilActivity.class);
//                Intent intent = new Intent(FlashActivity.this, DrawerLayoutActivity.class);
//                Intent intent = new Intent(FlashActivity.this, LinearLayoutManagerActivity.class);
//                Intent intent = new Intent(FlashActivity.this, GridLayoutManagerActivity.class);
//                Intent intent = new Intent(FlashActivity.this, NestedScrollActivity.class);

                Intent intent = new Intent(FlashActivity.this, DrawableCompatActivity.class);
                startActivity(intent);
//                ListViewiew
//                RecycleV

            }
        }, 1000);
    }


}
