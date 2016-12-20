package com.lll.common.encryption.java;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;

import javax.crypto.Cipher;

public class RSAUtils implements EncryptionInteface {

    private Cipher mCipher;
    private PublicKey mPublicKey;
    private PrivateKey mPrivateKey;

    public RSAUtils() throws Exception{
        mCipher = Cipher.getInstance("RSA");
        KeyPair keyPair = KeyPairGenerator.getInstance("RSA").generateKeyPair();
        mPublicKey = keyPair.getPublic();
        mPrivateKey = keyPair.getPrivate();
    }


    @Override
    public String getSign(String param) throws Exception{
        return encryptionString(param);
    }

    @Override
    public String encryptionString(String param) throws Exception{
        mCipher.init(Cipher.ENCRYPT_MODE,mPublicKey);
        return mCipher.doFinal(param.getBytes()).toString();
    }

    @Override
    public String decryptionString(String param) throws Exception{
        mCipher.init(Cipher.DECRYPT_MODE,mPrivateKey);
        return mCipher.doFinal(param.getBytes()).toString();
    }
}
