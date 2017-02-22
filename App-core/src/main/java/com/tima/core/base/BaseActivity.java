package com.tima.core.base;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v4.view.LayoutInflaterCompat;
import android.support.v4.view.LayoutInflaterFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import com.afollestad.appthemeengine.ATEActivity;
import com.lll.common.ui.tint.ThemeDelegate;
import com.lll.common.util.SpUtils;

//import butterknife.ButterKnife;

public class BaseActivity extends ATEActivity {

    private ThemeDelegate mThemeDelegate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //必须在super.onCreate()之前调用
        getThemeDelegate().setLayoutFactory(getLayoutInflater());
        super.onCreate(savedInstanceState);
    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        //封装统一的转换动画
//        overridePendingTransition(R.anim.anim_in,R.anim.anim_out);
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        super.startActivityForResult(intent, requestCode);
//        overridePendingTransition(R.anim.anim_in,R.anim.anim_out);
    }

    public SpUtils getSpUtils() {
        return SpUtils.getInstance(this);
    }


    /**
     * 显示加载进度框
     */
    public void showLoadingDialog() {

    }

    /**
     * 因此进度框
     */
    public void dismissLoadingDialog() {

    }

    private ThemeDelegate getThemeDelegate() {
        if (mThemeDelegate == null) {
            mThemeDelegate = new ThemeDelegate();
        }
        return mThemeDelegate;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
