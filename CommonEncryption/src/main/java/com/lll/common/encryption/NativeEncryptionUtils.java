package com.lll.common.encryption;

/**
 * Description: 加解密工具类 so库实现
 * Version: 1.0
 * Created by lll on 2016/5/4.
 * CopyRight lll
 */
public class NativeEncryptionUtils {
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
     * 字符串加密默认采用aes加密
     * @param param
     * @return 加密后的字符串
     */
    public static native String encryptionString(String param);

    /**
     * 字符串串假面
     * @param param
     * @param encryptionType 加密算法，
     * @return
     */
    public static native String encryptionString (String param,String encryptionType);



    /**
     * 字符串解密(默认为aes解密)
     * @param param
     * @return 解密后的字符串
     */
    public static native String decryptionString(String param);

    /**
     *
     * @param param
     * @return
     */
    public static native String decryptionString(String param,String decryptionType);




}
