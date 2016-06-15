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
import com.lll.learn.v4.DrawableCompatActivity;
import com.lll.learn.v4.NestedScrollActivity;
import com.tima.core.base.BaseActivity;
import com.tima.core.learn.AnimatedRecyclerView;

/**
 * Description: 闪屏页面
 * Version:
 * Created by lll on 2016/5/3.
 * CopyRight lll
 */
public class FlashActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startLogin();
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

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int mask = event.getActionMasked();
        switch (mask) {
            case MotionEvent.ACTION_POINTER_DOWN:
                break;
            case MotionEvent.ACTION_POINTER_UP:
                break;
            case MotionEvent.ACTION_DOWN:
                break;
        }
        return super.onTouchEvent(event);
    }
}
