package com.lll.bitmaploader.utils;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;

import com.lll.bitmaploader.cache.ImageCache;

import java.io.FileDescriptor;

/**
 * Version 1.0
 * Created by lll on 16/7/14.
 * Description 图片尺寸的处理(图片压缩)
 * A simple subclass of {@link ImageWorker} that resizes images from resources given a target width
 * and height. Useful for when the input images might be too large to simply load directly into
 * memory.
 * copyright generalray4239@gmail.com
 */
public class ImageResizer extends ImageWorker {
    private static final String TAG = "ImageResizer";
    protected int mImageWidth;
    protected int mImageHeight;

    /**
     * Initialize providing a single target image size (used for both width and height);
     *
     * @param context
     * @param imageWidth
     * @param imageHeight
     */
    public ImageResizer(Context context, int imageWidth, int imageHeight) {
        super(context);
        setImageSize(imageWidth, imageHeight);
    }

    /**
     * Initialize providing a single target image size (used for both width and height);
     *
     * @param context
     * @param imageSize
     */
    public ImageResizer(Context context, int imageSize) {
        super(context);
        setImageSize(imageSize);
    }

    /**
     * Set the target image width and height.
     *
     * @param width
     * @param height
     */
    public void setImageSize(int width, int height) {
        mImageWidth = width;
        mImageHeight = height;
    }

    /**
     * Set the target image size (width and height will be the same).
     *
     * @param size
     */
    public void setImageSize(int size) {
        setImageSize(size, size);
    }


    /**
     * The main processing method. This happens in a background task. In this case we are just
     * sampling down the bitmap and returning it from a resource.
     *
     * @param resId
     * @return
     */
    private Bitmap processBitmap(int resId) {
        return decodeSampledBitmapFromResource(mResources, resId, mImageWidth,
                mImageHeight, getImageCache());
    }

    @Override
    protected Bitmap processBitmap(Object data) {
        return processBitmap(Integer.parseInt(String.valueOf(data)));
    }

    /**
     * 压缩resource目录的图片资源
     *
     * @param res
     * @param resId
     * @param reqWidth
     * @param reqHeight
     * @param cache
     * @return
     */
    public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId,
                                                         int reqWidth, int reqHeight, ImageCache cache) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        // END_INCLUDE (read_bitmap_dimensions)

        // If we're running on Honeycomb or newer, try to use inBitmap
        if (Utils.hasHoneycomb()) {
            addInBitmapOptions(options, cache);
        }

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }

    /**
     * 压缩本地图片
     * @param filename 图片全路径
     * @param reqWidth
     * @param reqHeight
     * @param cache
     * @return
     */
    public static Bitmap decodeSampledBitmapFromFile(String filename,
                                                     int reqWidth, int reqHeight, ImageCache cache) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filename,options);
        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // If we're running on Honeycomb or newer, try to use inBitmap
        if (Utils.hasHoneycomb()) {
            addInBitmapOptions(options, cache);
        }

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(filename, options);
    }


    /**
     * Decode and sample down a bitmap from a file input stream to the requested width and height.
     *
     * @param fileDescriptor
     * @param reqWidth
     * @param reqHeight
     * @param cache
     * @return
     */
    public static Bitmap decodeSampledBitmapFromDescriptor(FileDescriptor fileDescriptor, int reqWidth, int reqHeight, ImageCache cache) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFileDescriptor(fileDescriptor, null, options);
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        options.inJustDecodeBounds = false;
        if (Utils.hasHoneycomb()) {
            addInBitmapOptions(options, cache);
        }
        return BitmapFactory.decodeFileDescriptor(fileDescriptor, null, options);
    }


    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private static void addInBitmapOptions(BitmapFactory.Options options, ImageCache cache) {
        options.inMutable = true;
        if (cache != null) {
            Bitmap inBitmap = cache.getBitmapFromReusableSet(options);
            if (inBitmap != null) {
                options.inBitmap = inBitmap;
            }
        }
    }

    /**
     * 计算图片压缩比例
     *
     * @param options
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            final int halfHeight = height / 2;
            final int halfWidth = width / 2;
            while ((halfHeight / inSampleSize) > reqHeight && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
            //获取总像素
            long totallPixels = width * height / inSampleSize;
            final long totalReqPixelsCap = reqWidth * reqHeight * 2;
            while (totallPixels > totalReqPixelsCap) {
                inSampleSize *= 2;
                totallPixels /= 2;
            }
        }
        return inSampleSize;
    }
}
