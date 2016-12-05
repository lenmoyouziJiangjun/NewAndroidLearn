package com.lll.common.util;

import android.app.PendingIntent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.view.View;

/**
 * Version 1.0
 * Created by lll on 16/11/28.
 * Description 获取缩略图的工具类
 * copyright generalray4239@gmail.com
 */

public class CaptureUtils {


    public static void getThumbnails(Bitmap mCapture, View mMainView) {
        Canvas canvas = new Canvas(mCapture);
        final int left = mMainView.getScrollX();
        final int top = mMainView.getScrollY() + mMainView.getHeight();
        int state = canvas.save();
        canvas.translate(-left, -top);
        float scale = 240 / (float) mMainView.getWidth();
        canvas.scale(scale, scale, left, top);
        mMainView.draw(canvas);

        canvas.restore();
        Paint alphaPaint = getAlphaPaint();
        canvas.drawRect(0, 0, 1, mCapture.getHeight(), alphaPaint);
        canvas.drawRect(mCapture.getWidth() - 1, 0, mCapture.getWidth(), mCapture.getHeight(), alphaPaint);
        canvas.drawRect(0, 0, mCapture.getWidth(), 1, alphaPaint);
        canvas.drawRect(0, mCapture.getHeight() - 1, mCapture.getWidth(), mCapture.getHeight(), alphaPaint);
        canvas.setBitmap(null);
    }

    private static Paint getAlphaPaint() {
        Paint sAlphaPaint = new Paint();
        sAlphaPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        sAlphaPaint.setColor(Color.TRANSPARENT);
        sAlphaPaint.setAntiAlias(true);
        return sAlphaPaint;
    }


}
