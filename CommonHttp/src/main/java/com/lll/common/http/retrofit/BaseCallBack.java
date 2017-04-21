package com.lll.common.http.retrofit;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Version 1.0
 * Created by lll on 17/3/10.
 * Description 统一处理 返回数据
 * copyright generalray4239@gmail.com
 */
public class BaseCallBack implements Callback {
    @Override
    public void onFailure(Call call, IOException e) {

    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {
           response.body();
    }
}
