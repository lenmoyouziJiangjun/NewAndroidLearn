package com.tima.app.http.request;

import com.tima.app.http.HttpConfig;

import java.util.HashMap;

/**
 * Description:
 * Version:
 * Created by lll on 2016/5/4.
 * CopyRight lll
 */
public class BaseRequest {
    protected HashMap<String, String> query = new HashMap<>();
    protected HashMap<String, String> form = new HashMap<>();
    private String apiUrl = "";


    /**
     * @param apiUrl 此URL必须和请求路径一致，如： 登录的请求路径为 /tm/user/user/loginByMobile
     *               那apiUrl也应该为/tm/user/user/loginByMobile，否则签名验证不通过
     */
    public BaseRequest(String apiUrl) {
        query.put("appkey", HttpConfig.getAppKey());
        query.put("token", HttpConfig.getToken());
        this.apiUrl = apiUrl;
    }


    public HashMap<String, String> getQuery() {
        query.put("sign", HttpConfig.getSign(apiUrl, query));
        return query;
    }

    public HashMap<String, String> getForm() {
        return form;
    }


    public String getPath() {
        return apiUrl;
    }


}
