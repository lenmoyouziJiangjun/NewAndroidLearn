package com.lll.h5conntainer;

import android.annotation.TargetApi;
import android.content.ComponentName;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.lll.h5conntainer.bean.CacheInfoBean;
import com.lll.h5conntainer.manager.StateManager;

import java.util.HashMap;
import java.util.Map;

/**
 * Version 1.0
 * Created by lll on 17/2/22.
 * Description 自定义一个WebView.封装里面常用dialog和toast.以及h5界面
 * copyright generalray4239@gmail.com
 */
public class HyWebView extends WebView {
    private Context mApplicationContext;

    public HyWebView(Context context) {
        super(context);
        initWebView(context);
    }

    public HyWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initWebView(context);
    }

    public HyWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initWebView(context);
    }

    private void initWebView(Context context) {
        this.mApplicationContext = context.getApplicationContext();
        WebSettings settings = getSettings();
        settings.setJavaScriptEnabled(true);
        if (Build.VERSION.SDK_INT >= 16) {
            //不允许JavaScript执行本地文件，有安全漏洞
            //http://www.droidsec.cn/webview-file%E5%9F%9F%E5%90%8C%E6%BA%90%E7%AD%96%E7%95%A5%E7%BB%95%E8%BF%87%E6%BC%8F%E6%B4%9E%E6%B5%85%E6%9E%90/
            //http://blogs.360.cn/360mobile/2014/09/22/webview%E8%B7%A8%E6%BA%90%E6%94%BB%E5%87%BB%E5%88%86%E6%9E%90/
            settings.setAllowFileAccessFromFileURLs(false);
        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }

    /**
     * 需求：拦截h5界面的请求，本地加载后添加进来
     */
    public static class MyWebViewClient extends WebViewClient {
        private Context mContext;
        private CacheInfoBean dataBean = null;

        public MyWebViewClient(Context context) {
            this.mContext = context;
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        @TargetApi(21)
        @Override
        public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
            return handlerInterceptRequest(request.getUrl().toString());
        }

        @Override
        public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
            return handlerInterceptRequest(url);
        }

        /**
         * 处理请求拦截操作
         * @param url
         * @return
         */
        private WebResourceResponse handlerInterceptRequest(String url){
            WebResourceResponse resourceResponse=null;
            if(!StateManager.getInstance().isCloseAllHybrid()){
                Uri uri = Uri.parse(url);
                String scheme = uri.getScheme();
                boolean hybridState = StateManager.getInstance().isUsedHybrid();
                if(!TextUtils.isEmpty(scheme) && scheme.contains("http") && hybridState){
                    Log.i("lll","url ==="+uri);
                    resourceResponse = getWebResourceFEModel(url);
                }
            }
            if(resourceResponse!=null){
                setWebResourceResponseHeader(resourceResponse);
            }
            return resourceResponse;
        }

        private WebResourceResponse getWebResourceFEModel(String requestUrl){
            if(this.dataBean==null){
                return null;
            }else{

            }
            return null;
        }

        /**
         * 添加ajax跨域访问请求头
         * @param resourceResponse
         */
        private void setWebResourceResponseHeader(WebResourceResponse resourceResponse){
            if(resourceResponse!=null && Build.VERSION.SDK_INT>=21){
                Map headers=resourceResponse.getResponseHeaders();
                if(headers==null){
                    headers  = new HashMap();
                }
                headers.put("Access-Control-Allow-Origin","*");//
                resourceResponse.setResponseHeaders(headers);
            }
        }
    }




}
