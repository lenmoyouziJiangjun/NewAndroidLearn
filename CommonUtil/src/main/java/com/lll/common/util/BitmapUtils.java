package com.lll.common.util;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.text.TextUtils;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
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
     *
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
     *
     * @param options
     * @param reqWidth  控件宽度
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
     * 在异步线程中调用
     *
     * @param ins
     * @param file
     */
    public static void saveBitmap(InputStream ins, String file) {

    }


    /**
     * 将图片转为base64字节流
     *
     * @param bitmap
     * @return
     */
    public static String Bitmap2Base64(Bitmap bitmap) {
        String result = null;
        ByteArrayOutputStream baos = null;
        try {
            if (bitmap != null) {
                baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                baos.flush();
                baos.close();
                byte[] bitmapBytes = baos.toByteArray();
                result = Base64.encodeToString(bitmapBytes, Base64.DEFAULT);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (baos != null) {
                    baos.flush();
                    baos.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }


    /**
     * 将图片保存到本地
     *
     * @param bitmap
     * @param filePath   文件dir
     * @param fileName   文件名称
     * @param quality    质量，0到100，100表示高清
     * @param suffixName 后缀名称
     * @return
     */
    private static String saveBitmap2Local(Bitmap bitmap, String filePath, String fileName, int quality, String suffixName) {
        File dir = new File(filePath);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        String filePullPath;
        if (TextUtils.isEmpty(fileName)) {
            filePullPath = dir.getPath() + "/" + System.currentTimeMillis() + suffixName;
        } else {
            filePullPath = dir.getPath() + "/" + fileName + suffixName;
        }

        File file = new File(filePullPath);
        FileOutputStream fOut = null;
        try {
            fOut = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        bitmap.compress(Bitmap.CompressFormat.JPEG, quality, fOut);
        try {
            fOut.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            fOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return filePullPath;
    }

    /**
     * 图片尺寸缩放，最大宽度不超过targetWidth
     *
     * @param bitmap
     * @param targetWidth
     * @return
     */
    public static Bitmap resizeImage(Bitmap bitmap, int targetWidth) {
        Bitmap originBitmap = bitmap;
        int width = originBitmap.getWidth();
        int height = originBitmap.getHeight();

        if (width > targetWidth) {
            float scale = ((float) targetWidth) / width;
            Matrix matrix = new Matrix();
            matrix.postScale(scale, scale);

            Bitmap resizedBitmap = originBitmap.createBitmap(originBitmap, 0, 0, width, height, matrix, true);
            return resizedBitmap;
        } else {
            return originBitmap;
        }
    }

    /**
     * 图片旋转，根据照片Exif信息
     *
     * @param src 图片路径
     * @return
     */
    public static Bitmap rotateImage(String src) {
        Bitmap bitmap = BitmapFactory.decodeFile(src);
        try {
            ExifInterface exif = new ExifInterface(src);
            int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);

            Matrix matrix = new Matrix();
            switch (orientation) {
                case ExifInterface.ORIENTATION_FLIP_HORIZONTAL:
                    matrix.setScale(-1, 1);
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    matrix.setRotate(180);
                    break;
                case ExifInterface.ORIENTATION_FLIP_VERTICAL:
                    matrix.setRotate(180);
                    matrix.postScale(-1, 1);
                    break;
                case ExifInterface.ORIENTATION_TRANSPOSE:
                    matrix.setRotate(90);
                    matrix.postScale(-1, 1);
                    break;
                case ExifInterface.ORIENTATION_ROTATE_90:
                    matrix.setRotate(90);
                    break;
                case ExifInterface.ORIENTATION_TRANSVERSE:
                    matrix.setRotate(-90);
                    matrix.postScale(-1, 1);
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    matrix.setRotate(-90);
                    break;
                case ExifInterface.ORIENTATION_NORMAL:
                case ExifInterface.ORIENTATION_UNDEFINED:
                default:
                    return bitmap;
            }

            try {
                Bitmap oriented = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
                bitmap.recycle();
                return oriented;
            } catch (OutOfMemoryError e) {
                e.printStackTrace();
                return bitmap;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    /**
     * 创建一张空白图片
     *
     * @param width
     * @param height
     * @param color
     * @return
     */
    public static Bitmap createBlankBitmap(int width, int height, int color) {
        int[] colors = new int[width * height];
        for (int i = 0; i < colors.length; i++) {
            colors[i] = color;
        }
        return Bitmap.createBitmap(colors, width, height, Bitmap.Config.ARGB_8888);
    }


}
