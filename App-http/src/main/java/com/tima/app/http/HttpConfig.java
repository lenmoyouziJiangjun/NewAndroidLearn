package com.tima.app.http;

import com.lll.common.encryption.NativeEncryptionUtils;

import java.net.URLEncoder;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;

/**
 * Description: 网络请求常量，数据必须加密存储
 * Version:
 * Created by lll on 2016/5/4.
 * CopyRight lll
 */
public final class HttpConfig {
    private static String sAppKey="";
    private static String sSecretKey="";
    private static String sToken;

    public static String getAppKey() {
        return NativeEncryptionUtils.decryptionString(sAppKey);
    }

    public static String getSecretKey() {
        return NativeEncryptionUtils.decryptionString(sSecretKey);
    }
    public static String getToken() {
        return NativeEncryptionUtils.decryptionString(sToken);
    }

    public static void setToken(String sToken) {
        HttpConfig.sToken = NativeEncryptionUtils.encryptionString(sToken);
    }

    /**
     * 生成签名
     * @param appendUrl
     * @param params
     * @return
     */
    public static String getSign(String appendUrl, Map params){
        LinkedList parameters = new LinkedList(params.entrySet());
        Collections.sort(parameters, new Comparator<Map.Entry<String, String>>() {
            public int compare(Map.Entry<String, String> o1, Map.Entry<String, String> o2) {
                return o1.getKey().compareTo(o2.getKey());
            }
        });
        StringBuilder sb = new StringBuilder();
        sb.append(appendUrl).append("_");
        Iterator baseString = parameters.iterator();

        while (baseString.hasNext()) {
            Map.Entry param = (Map.Entry) baseString.next();
            sb.append((String) param.getKey()).append("=").append((String) param.getValue()).append("_");
        }

        sb.append(getSecretKey());
        String baseString1=null;
        try {
            baseString1 = URLEncoder.encode(sb.toString(), "UTF-8");
        }catch (Exception e){
//            baseString1 = sb.toString();
            throw  new RuntimeException("not support utf-8 encode");
        }
        return NativeEncryptionUtils.getSign(baseString1);
    }
}
