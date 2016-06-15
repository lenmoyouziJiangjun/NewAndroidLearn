package com.tima.core.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.EditText;

import com.lll.learn.v4.NestedScrollActivity;
import com.tima.core.R;
import com.tima.core.base.BaseActivity;
import com.tima.core.learn.AnimatedRecyclerView;
import com.tima.core.mvp.presenter.LoginPresenter;
import com.tima.core.mvp.task.ILoginTask;


import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

//import butterknife.InjectView;
//import butterknife.OnClick;
//
/**
 * Description:
 * Version:
 * Created by lll on 2016/5/3.
 * CopyRight lll
 */
public class LoginActivity extends BaseActivity implements ILoginTask.View {

    //    @InjectView(R.id.et_name)
    EditText mName;
    //    @InjectView(R.id.et_pwd)
    EditText mPassword;

    //    @Inject
    LoginPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mPresenter = new LoginPresenter(this);
    }

    //    @OnClick()
    void login() {
        mPresenter.login();

    }


    @Override
    public void close() {
        Intent intent = new Intent(this, NestedScrollActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void switchLoadingDialog(boolean active) {
        if (active) {
            showLoadingDialog();
        } else {
            dismissLoadingDialog();
        }
    }

    @Override
    public Map getLoginParams() {
        Map params = new HashMap();
        params.put("name", mName.getText());
        params.put("pwd", mPassword.getText());
        return params;
    }


    void test() {
        RecyclerView view = new RecyclerView(this);
//        view
    }


}
