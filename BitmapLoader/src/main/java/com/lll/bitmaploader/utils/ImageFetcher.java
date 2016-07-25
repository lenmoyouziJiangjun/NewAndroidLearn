package com.lll.bitmaploader.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.widget.Toast;

import com.lll.bitmaploader.cache.DiskLruCache;
import com.lll.bitmaploader.cache.ImageCache;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Version 1.0
 * Created by lll on 16/7/25.
 * Description 图片下载工具类
 * copyright generalray4239@gmail.com
 */
public class ImageFetcher extends ImageResizer {
    private static final String TAG = "ImageFetcher";
    private static final int HTTP_CACHE_SIZE = 10 * 1024 * 1024; // 10MB
    private static final String HTTP_CACHE_DIR = "http";
    private static final int IO_BUFFER_SIZE = 8 * 1024;

    private DiskLruCache mHttpDiskCache;
    private File mHttpCacheDir;
    private boolean mHttpDiskCacheStarting = true;
    private final Object mHttpDiskCacheLock = new Object();
    private static final int DISK_CACHE_INDEX = 0;


    public ImageFetcher(Context context, int imageWidth, int imageHeight) {
        super(context, imageWidth, imageHeight);
        init(context);
    }

    public ImageFetcher(Context context, int imageSize) {
        super(context, imageSize);
        init(context);
    }

    private void init(Context context) {
        checkConnection(context);
        mHttpCacheDir = ImageCache.getDiskCacheDir(context, HTTP_CACHE_DIR);
    }

    protected void initDiskCacheInternal() {
        super.initDiskCacheInternal();
        initHttpDiskCache();
    }

    private void initHttpDiskCache() {
        if (!mHttpCacheDir.exists()) {
            mHttpCacheDir.mkdirs();
        }
        synchronized (mHttpDiskCacheLock) {
            if (ImageCache.getUsableSpace(mHttpCacheDir) > HTTP_CACHE_SIZE) {
                try {
                    mHttpDiskCache = DiskLruCache.open(mHttpCacheDir, 1, 1, HTTP_CACHE_SIZE);
                } catch (Exception e) {
                    mHttpDiskCache = null;
                }
            }
            mHttpDiskCacheStarting = false;
            mHttpDiskCacheLock.notifyAll();
        }
    }

    protected void clearCacheInternal() {
        super.clearCacheInternal();
        synchronized (mHttpDiskCacheLock) {
            if (mHttpDiskCache != null && !mHttpDiskCache.isClosed()) {
                try {
                    mHttpDiskCache.delete();
                } catch (Exception e) {

                }
            }
            mHttpDiskCache = null;
            mHttpDiskCacheStarting = true;
            initDiskCacheInternal();
        }
    }

    @Override
    protected void flushCacheInternal() {
        super.flushCacheInternal();
        synchronized (mHttpDiskCacheLock) {
            if (mHttpDiskCache != null) {
                try {
                    mHttpDiskCache.flush();
                } catch (IOException e) {
                }
            }
        }
    }

    @Override
    protected void closeCacheInternal() {
        super.closeCacheInternal();
        synchronized (mHttpDiskCacheLock) {
            if (mHttpDiskCache != null) {
                try {
                    if (!mHttpDiskCache.isClosed()) {
                        mHttpDiskCache.close();
                        mHttpDiskCache = null;
                    }
                } catch (IOException e) {
                }
            }
        }
    }

    /**
     * 检查网络状态
     *
     * @param context
     */
    private void checkConnection(Context context) {
        final ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        final NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if (networkInfo == null || !networkInfo.isConnectedOrConnecting()) {
            Toast.makeText(context, "no net work access", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * The main process method, which will be called by the ImageWorker in the AsyncTask background
     * thread.
     *
     * @param data
     */
    private Bitmap processBitmap(String data) {
        final String key = PathUtils.hashKeyForDisk(data);
        FileDescriptor fileDescriptor = null;
        FileInputStream fileInputStream = null;
        DiskLruCache.Snapshot snapshot;
        synchronized (mHttpDiskCacheLock) {
            while (mHttpDiskCacheStarting) {
                try {
                    mHttpDiskCacheLock.wait();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            if (mHttpDiskCache != null) {
                try {
                    snapshot = mHttpDiskCache.get(key);
                    if (snapshot == null) {
                        DiskLruCache.Editor editor = mHttpDiskCache.editor(key);
                        if (editor != null) {
                            //下载图片
                            if (downloadUrlToStream(data,
                                    editor.newOutputStream(DISK_CACHE_INDEX))) {
                                editor.commit();
                            } else {
                                editor.abort();
                            }
                        }
                        snapshot = mHttpDiskCache.get(key);
                    }
                    if (snapshot != null) {
                        fileInputStream =
                                (FileInputStream) snapshot.getInputStream(DISK_CACHE_INDEX);
                        fileDescriptor = fileInputStream.getFD();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (fileDescriptor == null) {
                        try {
                            fileInputStream.close();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }

        Bitmap bitmap = null;
        if (fileDescriptor != null) {
            //解析图片
            bitmap = decodeSampledBitmapFromDescriptor(fileDescriptor, mImageWidth,
                    mImageHeight, getImageCache());
        }
        if (fileInputStream != null) {
            try {
                fileInputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return bitmap;
    }

    @Override
    protected Bitmap processBitmap(Object data) {
        return processBitmap(String.valueOf(data));
    }

    /**
     * 下载图片
     *
     * @param urlString
     * @param outputStream
     * @return
     */
    public boolean downloadUrlToStream(String urlString, OutputStream outputStream) {
        disableConnectionReuseIfNecessary();
        HttpURLConnection connection = null;
        BufferedInputStream in =null;
        BufferedOutputStream out = null;
        try {
            final URL url = new URL(urlString);
            connection = (HttpURLConnection) url.openConnection();
            in = new BufferedInputStream(connection.getInputStream(),IO_BUFFER_SIZE);
            out = new BufferedOutputStream( outputStream,IO_BUFFER_SIZE);
            int b ;
            while ((b = in.read())!=-1){
                out.write(b);
            }
            return true;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(connection!=null){
                connection.disconnect();
            }
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return false;
    }


    public static void disableConnectionReuseIfNecessary() {
        // HTTP connection reuse which was buggy pre-froyo
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.FROYO) {
            System.setProperty("http.keepAlive", "false");
        }
    }

}
