package com.lll.plugin.base;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.AssetManager;
import android.content.res.Resources;

import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

/**
 * Version 1.0
 * Created by lll on 16/11/24.
 * Description 实现自己的context。重写资源加载机制
 * copyright generalray4239@gmail.com
 */

public class BasePluginContextWrapper  extends ContextWrapper{

    private Map<String,SoftReference<AssetManager>> assetManagers;

    public BasePluginContextWrapper(Context base) {
        super(base);
    }


    private AssetManager getAssetManager(String resPath){
        if(assetManagers== null){
            assetManagers = new HashMap<>();
        }
        return assetManagers.get(resPath).get();
    }

    @Override
    public AssetManager getAssets() {
        AssetManager manager=getAssetManager("");
        if(null!=manager){
            return manager;
        }
        return super.getAssets();
    }

    @Override
    public Resources getResources() {
        return super.getResources();
    }



}
