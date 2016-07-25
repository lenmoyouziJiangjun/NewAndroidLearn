package com.lll.bitmaploader.utils;

import java.security.MessageDigest;

/**
 * Version 1.0
 * Created by lll on 16/7/14.
 * Description hash文件路径
 * copyright generalray4239@gmail.com
 */
public class PathUtils {

    /**
     * A hashing method that changes a string (like a URL) into a hash suitable for using as a
     * disk filename.
     *
     * @param key 文件路径
     * @return
     */
    public static String hashKeyForDisk(String key) {
        String cacheKey;
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update(key.getBytes());
            cacheKey=bytesToHexString(digest.digest());
        }catch (Exception e){
            cacheKey = String.valueOf(key.hashCode());
        }
        return cacheKey;
    }

    /**
     * 将bytes数组转为16进制的字符串
     *
     * @param bytes
     * @return
     */
    private static String bytesToHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (int i=0;i<bytes.length;i++){
            String hex = Integer.toHexString(0xFF & bytes[i]);
            if (hex.length() == 1) {
                sb.append('0');
            }
            sb.append(hex);
        }
        return sb.toString();
    }

}
