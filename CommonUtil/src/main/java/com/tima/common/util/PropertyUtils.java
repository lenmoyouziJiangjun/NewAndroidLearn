package com.tima.common.util;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;

import java.io.IOException;
import java.util.Properties;

/**
 * Description:
 * Version:
 * Created by lll on 2016/4/11.
 * CopyRight lll
 */
public class PropertyUtils {


    /**
     *  <meta-data
         android:name=""
         android:value="">
         </meta-data>
     * 获取AndroidManifest里面配置的MetaData
     * @param context
     * @param metaKey
     * @return
     */
    public static String getMetaValue(Context context, String metaKey) {
        Bundle metaData = null;
        String apiKey = null;
        if (context == null || metaKey == null) {
            return null;
        }
        try {
            ApplicationInfo ai = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
            if (null != ai) {
                metaData = ai.metaData;
            }
            if (null != metaData) {
                apiKey = metaData.getString(metaKey);
            }
        } catch (PackageManager.NameNotFoundException e) {

        }
        return apiKey;
    }

    /**
     * 获取 properties 配置文件属性
     * @param pFileName 文件名称
     * @param pName    属性名称
     * @return
     */
    public static String getPropertyData(String pFileName,String pName){
        Properties properties = new Properties();

        try {
            properties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream(pFileName));
            String value = properties.getProperty(pName);
            if (value == null) {
                throw new RuntimeException("the "+pName+" property is not found");
            }
            return value;
        } catch (IOException var4) {
            throw new RuntimeException("don`t find the "+pFileName);
        }
    }
}
