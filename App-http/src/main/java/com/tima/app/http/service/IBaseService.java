package com.tima.app.http.service;

import com.tima.app.http.response.LoginResponse;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

/**
 * Description: 网络请求公共类
 * Version:
 * Created by lll on 2016/5/4.
 * CopyRight lll
 */
public interface IBaseService<T> {

    @FormUrlEncoded
    @Headers("Content-Type: application/x-www-from-urlencoded")
    @POST("{path}")
    Call<T> doFromAndQuery(@Path("path") String appendPath, @QueryMap Map queryParams, @FieldMap Map formParams);

    @FormUrlEncoded
    @Headers("Content-Type: application/x-www-from-urlencoded")
    @POST("{path}")
    Call<T> doFrom(@Path("path") String appendPath, @FieldMap Map formParams);


    @FormUrlEncoded
    @Headers("Content-Type: application/x-www-from-urlencoded")
    @POST("{path}")
    Call<T> doQuery(@Path("path") String appendPath, @QueryMap Map queryParams);
}
