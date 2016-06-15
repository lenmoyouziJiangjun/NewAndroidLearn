package com.tima.core.mvp.presenter;

import android.text.TextUtils;

import com.tima.app.http.TimaHttpClient;
import com.tima.app.http.domin.LoginRequest;
import com.tima.app.http.response.BaseResponse;
import com.tima.app.http.service.IBaseService;
import com.tima.common.util.LogUtils;
import com.tima.core.mvp.task.ILoginTask;

import org.bouncycastle.jcajce.provider.symmetric.ARC4;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Description:
 * Version:
 * Created by lll on 2016/5/4.
 * CopyRight lll
 */
public class LoginPresenter implements ILoginTask.Presenter {


    ILoginTask.View mView;

    public LoginPresenter(ILoginTask.View view){
        mView = view;
    }

    Map params;
    @Override
    public void login() {
        if (verifyParams()) {
            mView.switchLoadingDialog(true);
            IBaseService baseService = TimaHttpClient.getInstance().create(IBaseService.class);
            LoginRequest request = new LoginRequest("", mView.getLoginParams());
            Call<BaseResponse> responseCall = baseService.doFrom(request.getPath(), request.getForm());
            responseCall.enqueue(new Callback<BaseResponse>() {
                @Override
                public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                    mView.switchLoadingDialog(false);
                }

                @Override
                public void onFailure(Call<BaseResponse> call, Throwable t) {
                    mView.switchLoadingDialog(false);

                }
            });
        }
    }

    @Override
    public boolean verifyParams() {
        params = mView.getLoginParams();
        if (TextUtils.isEmpty((String)params.get("name"))){
            LogUtils.e("用户名不能为空");
            return false;
        }
        return true;
    }

    @Override
    public void onStart() {

    }
}
