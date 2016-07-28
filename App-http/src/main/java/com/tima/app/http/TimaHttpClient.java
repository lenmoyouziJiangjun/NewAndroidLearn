package com.tima.app.http;

import com.tima.common.http.HttpClient;
import com.lll.common.util.logger.LogUtils;
import com.lll.common.util.PropertyUtils;

/**
 * Description:
 * Version:
 * Created by lll on 2016/5/4.
 * CopyRight lll
 */
public class TimaHttpClient extends HttpClient {


    public static TimaHttpClient mInstance = new TimaHttpClient();

    private TimaHttpClient() {
        setBaseUrl(getBaseUrl());
    }

    public static TimaHttpClient getInstance() {
        return mInstance;
    }

    /**
     * 获取URL前缀
     * @return
     */
    private String getBaseUrl() {
        String base_url = PropertyUtils.getPropertyData("config","base_url");
        LogUtils.e("base_url================"+base_url);
        return base_url;
    }



}
