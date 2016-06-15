package com.tima.app.http.response;

import java.io.Serializable;

/**
 * Description:
 * Version:
 * Created by lll on 2016/5/4.
 * CopyRight lll
 */
public class BaseResponse implements Serializable {
    public String status;
    public String errorCode;
    public String errorMessage;
    public String extMessage;

}
