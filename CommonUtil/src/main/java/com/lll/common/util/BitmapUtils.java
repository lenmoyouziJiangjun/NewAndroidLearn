package com.lll.common.util;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.InputStream;

/**
 * Version 1.0
 * Created by lll on 16/7/7.
 * Description 图片处理工具类
 * copyright generalray4239@gmail.com
 */
public class BitmapUtils {

    /**
     * 压缩图片
     * @param res
     * @param resId
     * @param reqWidth  控件宽度
     * @param reqHeight 控件高度
     * @return
     */
    public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId,
                                                         int reqWidth, int reqHeight) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }

    /**
     * 计算图片压缩比例
     * @param options
     * @param reqWidth 控件宽度
     * @param reqHeight 控件高度
     * @return
     */
    private static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            final int halfHeight = height / 2;
            final int halfWidth = width / 2;
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }
        return inSampleSize;
    }

    /**
     * 将图片流存储到本地
     *     在异步线程中调用
     * @param ins
     * @param file
     */
    public static void saveBitmap(InputStream ins,String file){

    }


    /**
     * 将图片转为base64字节流
     * @param bit
     * @return
     */
    public static byte[] Bitmap2Base64(Bitmap bit){
          return null;
    }








}
