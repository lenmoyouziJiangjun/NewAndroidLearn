package com.tima.common.encryption;

/**
 * Description:
 * Version:
 * Created by lll on 2016/5/4.
 * CopyRight lll
 */
public class EncryptionUtils {
    static{
        System.loadLibrary("secret");
    }

    /**
     * 获取sign签名（将生成sign的算法放到native代码里面）
     * @param param
     * @return
     */
    public static native String getSign(String param);

    /**
     * 字符串加密
     * @param param
     * @return
     */
    public static native String encryptionString(String param);

    /**
     * 字符串解密
     * @param param
     * @return
     */
    public static native String decryptionString(String param);
}
