package com.tima.core.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.Window;
import android.view.WindowManager;

import com.lll.learn.v4.DrawableCompatActivity;
import com.tima.core.R;
import com.tima.core.base.BaseActivity;



/**
 * Description: 闪屏页面
 * Version:
 * Created by lll on 2016/5/3.
 * CopyRight lll
 */
public class FlashActivity extends BaseActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setWindowFlag();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flash_core);
        checkNewVersion();
    }

    /**
     * 细节1:闪屏界面一般是全屏界面
     */
    private void setWindowFlag(){
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setBackgroundDrawable(getResources().getDrawable(R.drawable.transparent_real));//
    }

    /**
     * 检查新版本
     */
    private void checkNewVersion(){
       if(true){//没有更新
           goBannerActivity();
       }
    }

    //进入广告(或新手引导界面)界面
    private void goBannerActivity(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(FlashActivity.this,BannerActivity.class);
                startActivity(intent);
                finish();//手动finish
            }
        },3000);
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
