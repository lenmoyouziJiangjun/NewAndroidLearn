package com.tima.core.activity;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.PersistableBundle;

import com.lll.common.util.CaptureUtils;
import com.lll.common.util.logger.Log;
import com.lll.common.util.logger.LogUtils;
import com.lll.learn.base.BaseListStringActivity;
import com.lll.learn.md.MDLearnActivity;
import com.lll.learn.recycleView.RecycleViewLearnActivity;
import com.lll.learn.v4.DrawableCompatActivity;
import com.lll.learn.v4.V4LearnActivity;
import com.lll.learn.v7.V7LearnActivity;
import com.tima.core.activity.test.AnimationTestActivity;
import com.tima.core.activity.test.CustomViewActivity;
import com.tima.core.activity.test.FrameworkLearnActivity;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Version 1.0
 * Created by lll on 16/9/2.
 * Description
 * copyright generalray4239@gmail.com
 */
public class BannerActivity extends BaseListStringActivity {


    Handler mHandler;


    private void testHandler() {
        mHandler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                return false;
            }
        });
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtils.e("------onCreate---------");

        initThum();
    }


    private void initThum() {
        if (mThum == null) {
            mThum = Bitmap.createBitmap(240, 240,
                    Bitmap.Config.RGB_565);
            mThum.eraseColor(Color.WHITE);
        }
        CaptureUtils.getThumbnails(mThum, getWindow().getDecorView());
    }

    @Override
    protected void onStart() {
        super.onStart();
        LogUtils.e("------onStart----注册广播-----");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        LogUtils.e("------onRestart---------");
    }

    @Override
    protected void onResume() {
        super.onResume();
        LogUtils.e("------onResume----加入UI相关的listener-----");
    }

    @Override
    protected void onPause() {
        super.onPause();
        LogUtils.e("------onPause---------");
    }

    @Override
    protected void onStop() {
        super.onStop();
        LogUtils.e("------onStop-----移除广播，移除listener----");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LogUtils.e("------onDestroy---------");
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        LogUtils.e("");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        LogUtils.e("");
    }

    @Override
    public Map<String, Class> getDatas() {
        Map<String, Class> map = new LinkedHashMap<>(10);
        map.put("MD学习", MDLearnActivity.class);
        map.put("support v7 包学习", V7LearnActivity.class);
        map.put("support v4 包学习", V4LearnActivity.class);
        map.put("RecycleView的学习", RecycleViewLearnActivity.class);
        map.put("图片加载", BitmapLoaderActivity.class);
        map.put("进入主界面", MainActivity.class);
        map.put("View效果合集", CustomViewActivity.class);
        map.put("动画效果", AnimationTestActivity.class);
        map.put("FrameWork学习", FrameworkLearnActivity.class);

        return map;
    }


    public static Bitmap mThum;


}
