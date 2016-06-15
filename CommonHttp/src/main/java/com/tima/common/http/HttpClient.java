package com.tima.common.http;




import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLSocketFactory;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Description: 网络请求工具类(支持默认的https).必须要跟sBaseUrl设置值后才能使用
 * Version:
 * Created by lll on 2016/4/12.
 * CopyRight lll
 */
public abstract class HttpClient {


    public static String sBaseUrl ="https://www.baidu.com/";

    private static OkHttpClient.Builder sHttpClient = new OkHttpClient.Builder();

    /**
     * 创建builder;
     */
    private static Retrofit.Builder sBuilder = new Retrofit.Builder()
            .baseUrl(sBaseUrl)
            .client(initHttpClient())
            .addConverterFactory(GsonConverterFactory.create());

    /**
     * 初始基础的请求
     *
     * @return
     */
    private static OkHttpClient initHttpClient() {
        OkHttpClient client = sHttpClient.build();
//
        if (sBaseUrl.contains("https")) {//https请求
            client.newBuilder().connectTimeout(10, TimeUnit.SECONDS)
                    .readTimeout(10, TimeUnit.SECONDS)
//                .addNetworkInterceptor()//拦截器
                    .socketFactory(SSLSocketFactory.getDefault())//设置默认证书的https
                    .build();
        } else {//http请求
            client.newBuilder().connectTimeout(10, TimeUnit.SECONDS)
                    .readTimeout(10, TimeUnit.SECONDS)
//                .addNetworkInterceptor()//拦截器
//                    .socketFactory(SSLSocketFactory.getDefault())//设置https
                    .build();
        }
        return client;
    }

    /**
     * 设置baseUrl
     * @param baseUrl
     */
    public static void setBaseUrl(String baseUrl){
        sBaseUrl = baseUrl;
        sBuilder = new Retrofit.Builder()
                .baseUrl(sBaseUrl)
                .client(initHttpClient())
                .addConverterFactory(GsonConverterFactory.create());

    }



    /**
     * 自定义
     * @param builder
     */
    public static void configBuilder(Retrofit.Builder builder){
        sBuilder = builder;
    }


    /**
     * 创建请求一个普通请求
     *
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T create(Class<T> clazz) {
        return sBuilder.build().create(clazz);
    }

    /**
     * 包含oAuth认证需要的请求
     * 参考网站：https://futurestud.io/blog/oauth-2-on-android-with-retrofit
     *
     * @param clazz
     * @param token
     * @param <T>
     * @return
     */
//    public static <T> T create(Class<T> clazz, final AccessToken token) {
//        if (token != null) {
//            sHttpClient.addInterceptor(new Interceptor() {
//                @Override
//                public Response intercept(Chain chain) throws IOException {
//                    Request original = chain.request();
//                    Request.Builder requestBuilder = original.newBuilder()
//                            .header("Accept", "application/json")
//                            .header("Authorization",
//                                    token.getTokenType() + " " + token.getAccessToken())
//                            .method(original.method(), original.body());
//                    return chain.proceed(original);
//                }
//            });
//        }
//        //没有指定oAuth采用默认的
//        return sBuilder.build().create(clazz);
//    }


}
