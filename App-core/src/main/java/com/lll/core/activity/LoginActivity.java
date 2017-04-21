package com.lll.core.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

import com.lll.learn.v4.NestedScrollActivity;
import com.tima.core.R;
import com.lll.core.base.BaseActivity;
import com.lll.core.mvp.task.ILoginTask;


import java.util.HashMap;
import java.util.Map;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
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
}
