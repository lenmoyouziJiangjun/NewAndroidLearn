package com.tima.core.base;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

//import butterknife.ButterKnife;

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        ButterKnife.inject(this);
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

    /**
     * 显示加载进度框
     */
    public void showLoadingDialog(){

    }

    /**
     * 因此进度框
     */
    public void dismissLoadingDialog(){

    }
}
