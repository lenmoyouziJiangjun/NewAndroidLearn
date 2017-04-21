package com.lll.common.http.retrofit;

import java.io.Serializable;

public class BaseResponse implements Serializable {
    public int errCode;//为了提高网络安全，建议API不返回message,统一成code。同时所有的网络请求参数以a,b,c等替代
    public String errMsg;
    public boolean success;
}
