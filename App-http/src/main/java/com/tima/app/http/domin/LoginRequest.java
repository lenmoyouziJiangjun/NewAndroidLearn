package com.tima.app.http.domin;

import com.tima.app.http.request.BaseRequest;

import java.util.Map;

/**
 * Description:
 * Version:
 * Created by lll on 2016/5/5.
 * CopyRight lll
 */
public class LoginRequest extends BaseRequest {

    /**
     * @param apiUrl
     */
    public LoginRequest(String apiUrl, Map formParams) {
        super(apiUrl);
        form.putAll(formParams);
    }
}
