package com.lll.h5conntainer;

import android.content.Context;
import android.util.AttributeSet;
import android.webkit.WebView;

/**
 * Version 1.0
 * Created by lll on 17/2/22.
 * Description 自定义一个WebView.封装里面常用dialog和toast.以及h5界面
 * copyright generalray4239@gmail.com
 */
public class HyWebView extends WebView {
    public HyWebView(Context context) {
        super(context);
    }

    public HyWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public HyWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
