package com.lll.common.encryption.java;

public interface EncryptionInteface {

    String getSign(String param) throws Exception;

    /**
     * 字符串加密默认采用aes加密
     *
     * @param param
     * @return 加密后的字符串
     */
    String encryptionString(String param) throws Exception;


    /**
     * 字符串解密(默认为aes解密)
     *
     * @param param
     * @return 解密后的字符串
     */
    String decryptionString(String param) throws Exception;


}
