package com.lll.common.encryption.java;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

public class EncryptionUtils implements EncryptionInteface {
    Cipher mCipher;
    SecretKey mkey;

    public EncryptionUtils() throws Exception {

    }


    @Override
    public String getSign(String param) throws Exception {
        mCipher.init(Cipher.ENCRYPT_MODE, mkey);//设置模式为加密密码
        return mCipher.doFinal(param.getBytes()).toString();
    }

    @Override
    public String encryptionString(String param) throws Exception {
        mCipher.init(Cipher.ENCRYPT_MODE, mkey);
        return mCipher.doFinal(param.getBytes()).toString();
    }

    @Override
    public String decryptionString(String param) throws Exception {
        mCipher.init(Cipher.DECRYPT_MODE, mkey);
        return mCipher.doFinal(param.getBytes()).toString();
    }
}
