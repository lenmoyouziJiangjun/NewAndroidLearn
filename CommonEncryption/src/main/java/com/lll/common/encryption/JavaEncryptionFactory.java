package com.lll.common.encryption;

import android.util.Base64;
import android.webkit.JavascriptInterface;

import com.lll.common.encryption.java.AESUtils;
import com.lll.common.encryption.java.DESUtils;
import com.lll.common.encryption.java.EncryptionInteface;
import com.lll.common.encryption.java.MD5Utils;
import com.lll.common.encryption.java.RSAUtils;

/**
 * Version 1.0
 * Created by lll on 16/9/2.
 * Description 加解密工具类
 * copyright generalray4239@gmail.com
 */
public class JavaEncryptionFactory implements EncryptionInteface {

    private EncryptionInteface mImpl;


    public JavaEncryptionFactory(String type) throws Exception {
        type = type.toUpperCase();
        switch (type) {
            case "MD5":
                mImpl = new MD5Utils();
                break;
            case "DES":
                mImpl = new DESUtils();
                break;
            case "AES":
                mImpl = new AESUtils();
                break;
            case "RSA":
                mImpl = new RSAUtils();
                break;
            default:
                mImpl = new AESUtils();
                break;

        }
    }


    @Override
    public String getSign(String param) {
        param = Base64.encode(param.getBytes(), Base64.DEFAULT).toString();
        try {
            return mImpl.getSign(param);
        } catch (Exception e) {
            e.printStackTrace();
            return param;
        }

    }

    @Override
    public String encryptionString(String param) {
        param = Base64.encode(param.getBytes(), Base64.DEFAULT).toString();
        try {
            return mImpl.encryptionString(param);
        } catch (Exception e) {
            e.printStackTrace();
            return param;
        }
    }

    @Override
    public String decryptionString(String param) {
        try {
            return Base64.encode(mImpl.decryptionString(param).getBytes(), Base64.DEFAULT).toString();
        } catch (Exception e) {
            e.printStackTrace();
            return param;
        }

    }
}
