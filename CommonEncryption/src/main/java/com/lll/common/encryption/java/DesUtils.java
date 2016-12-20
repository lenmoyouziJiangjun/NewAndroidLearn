package com.lll.common.encryption.java;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;

public class DESUtils extends EncryptionUtils {


    public DESUtils() throws Exception {
        mCipher = Cipher.getInstance("AES");
        //生成秘钥，注意这么写秘钥不安全，最好放到JNI中保存秘钥
        mkey = KeyGenerator.getInstance("AES").generateKey();
    }

}
