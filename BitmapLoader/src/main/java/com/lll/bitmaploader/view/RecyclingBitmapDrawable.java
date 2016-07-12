package com.lll.bitmaploader.view;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;

/**
 * Version 1.0
 * Created by lll on 16/7/7.
 * Description 用完自动回收bitmap的BitmapDrawable
 * copyright generalray4239@gmail.com
 */
public class RecyclingBitmapDrawable extends BitmapDrawable {

    private int mCacheRefCount =0;
    private int mDisplayRefCount =0;

    private boolean mHasBeenDisplayed;

    /**
     *
     * @param res
     * @param bitmap
     */
    public RecyclingBitmapDrawable(Resources res, Bitmap bitmap){
        super(res,bitmap);
    }

    /**
     * 图片是否存在且没有被回收
     * @return
     */
    private synchronized  boolean hasValidBitmap(){
        Bitmap bitmap = getBitmap();
        return bitmap != null &&  !bitmap.isRecycled();
    }

    /**
     * 检查状态,如果没有引用了,回收图片
     */
    private synchronized void checkState(){
        if (mCacheRefCount <=0 && mDisplayRefCount<=0 &&mHasBeenDisplayed && hasValidBitmap()){
               getBitmap().recycle();
        }
    }

    /**
     *  drawable的状态发生改变
     * @param isDisplayed 是否被引用,true,引用次数加1;
     */
    public void setIsDisplayed(boolean isDisplayed){
        synchronized (this){
            if (isDisplayed){
                mDisplayRefCount++;
                mHasBeenDisplayed = true;
            }else{
                mDisplayRefCount--;
            }
        }
        checkState();
    }

    /**
     * 是否添加到缓存中
     * @param isCached true 添加到缓存
     */
    public void setIsCached(boolean isCached){
        synchronized (this){
            if (isCached){
                mCacheRefCount++;
            }else {
                mCacheRefCount--;
            }
        }
        checkState();
    }

}
