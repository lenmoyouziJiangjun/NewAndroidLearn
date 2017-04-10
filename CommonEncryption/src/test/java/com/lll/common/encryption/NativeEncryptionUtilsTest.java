package com.lll.common.encryption;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class NativeEncryptionUtilsTest {

    @Test
    public void getSign() throws Exception {
       assertEquals("HelloFrom JNI!",NativeEncryptionUtils.getSign("sdfhjkfd"));
    }

    @Test
    public void encryptionString() throws Exception {

    }

    @Test
    public void encryptionString1() throws Exception {

    }

    @Test
    public void decryptionString() throws Exception {

    }

    @Test
    public void decryptionString1() throws Exception {

    }

}