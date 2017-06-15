package com.lll.core.navigation;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;

import java.util.Map;

/**
 * Version 1.0
 * Created by lll on 17/5/2.
 * Description activity之间的跳转工具类
 * <p>
 * copyright generalray4239@gmail.com
 */
public class MyRouter {

    private Context mContext;
    private String mAction;
    private String mUrl;
    private String mMapParams;
    private int mRequestCode = -1;

    public MyRouter(Context context) {
        this.mContext = context;
    }

    public MyRouter(Context mContext, String mAction, String mUrl, String mMapParams, int mRequestCode) {
        this.mContext = mContext;
        this.mAction = mAction;
        this.mUrl = mUrl;
        this.mMapParams = mMapParams;
        this.mRequestCode = mRequestCode;
    }

    /**
     * 跳转逻辑
     */
    public void routing() {
        final Intent intent = new Intent(mAction);
        if (mUrl != null) {//进入对应界面
            Uri uri = Uri.parse(mUrl);
            if (mMapParams != null) {
                uri = new Uri.Builder().path(mUrl).appendQueryParameter("param", mMapParams).build();
            }
            intent.setData(uri);
        } else {//没有传递默认进入主界面

        }
        if (mRequestCode == -1) {
            mContext.startActivity(intent);
        } else {
            if (mContext instanceof Activity) {
                ((Activity) mContext).startActivityForResult(intent, mRequestCode);
            } else {
                throw new RuntimeException("context is not activity");
            }
        }
    }

    public static class Router {
        private Context context;
        private String action;
        private String url;
        private String mapParams;
        private int requestCode;

        public Router from(Context context) {
            return this;
        }

        public Router action(String action) {
            return this;
        }

        /**
         * url 规范：协议名称://模块名称/activity名称
         * 示例 跳转到用户中心loginActivity:  lll://usercenter/login
         *
         * @param url
         * @return
         */
        public Router url(@NonNull String url) {
            return this;
        }

        public Router params(Map params) {
            return this;
        }

        public Router requestCode(int requestCode) {
            return this;
        }

        public MyRouter create() {
            MyRouter router = new MyRouter(context, action, url, mapParams, requestCode);
            if (null == context) {
                throw new RuntimeException("context is null");
            }
            return router;
        }
    }
}
