package com.tima.core.activity;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Bundle;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.JsResult;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Map;

/**
 * Version 1.0
 * Created by lll on 16/8/22.
 * Description 公共的WebView界面
 * copyright generalray4239@gmail.com
 */
public class PublicWebViewActivity extends Activity {
    private WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mWebView = new WebView(this);
        setContentView(mWebView);
        initWebView();
    }

    /**
     * webView的基本设置
     */
    private void initWebView() {
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);//自适应屏幕
        webSettings.setGeolocationEnabled(true);//允许获取GPS地址
        mWebView.requestFocus();

        /*设置本地缓存*/
        webSettings.setAppCacheEnabled(true);
        final String cachePath = getApplicationContext().getDir("cache", Context.MODE_PRIVATE).getPath();
        webSettings.setAppCachePath(cachePath);
        webSettings.setAppCacheMaxSize(5*1024*1024);

        mWebView.setWebChromeClient(new WebChromeClient() {

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
            }

            /**
             * 自定义处理alert弹框
             * @param view
             * @param url
             * @param message
             * @param result
             * @return
             */
            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                return super.onJsAlert(view, url, message, result);
            }

            /**
             * 自定义Confirm弹框
             * @param view
             * @param url
             * @param message
             * @param result
             * @return
             */
            @Override
            public boolean onJsConfirm(WebView view, String url, String message, JsResult result) {
                return super.onJsConfirm(view, url, message, result);
            }

            /**
             * 多媒体播放全屏的时候会调用该方法
             */

            @Override
            public void onShowCustomView(View view, CustomViewCallback callback) {
                super.onShowCustomView(view, callback);
            }
        });

        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }

            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
                byte[] bytes = new byte[1024];
                InputStream in = new ByteArrayInputStream(bytes);
                return new WebResourceResponse("", "", in);
            }

            /**
             * 是否拦截请求,自定义处理,5.0版本处理
             * @param view
             * @param request
             * @return
             */
            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
//                Uri url=request.getUrl();
//                Map<String,String> headers=request.getRequestHeaders();
//                String method=request.getMethod();
//                if(method.equals("get")){//拦截请求
//                    byte[] bytes = new byte[1024];
//                    InputStream in = new ByteArrayInputStream(bytes);
//                    return new WebResourceResponse("", "UTF-8", in);
//                }else{//不拦截请求
                    return null;
//                }

            }

            /**
             * 处理超链接
             * @param view
             * @param url
             * @return
             */
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return super.shouldOverrideUrlLoading(view, url);
            }

            /**
             * https报错
             * @param view
             * @param handler
             * @param error
             */
            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                super.onReceivedSslError(view, handler, error);
            }

            /**
             * 网络状态不佳等问题,提示
             * @param view
             * @param request
             * @param error
             */
            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                String data = "当前网络不怎么好哦，请恢复网络后刷新再试";
                view.loadUrl("javascript:document.body.innerHTML=\"" + data
                        + "\"");
            }
        });
    }

    /**
     * java 调用js方法
     *    只有一种方式:通过调用url方式调用js中定义好的方法
     */
    private void callJS(String data){
        mWebView.loadUrl(String.format("javascript:WebViewJavascriptBridge._handleMessageFromNative(%s)", data));
    }

    /**
     * js调用Java方法
     *
     */
    private void callJava(){
       mWebView.addJavascriptInterface(new JavascriptInterface(),"JavascriptInterface");
    }

    /**
     * 定义一个调用类
     */
    private static class JavascriptInterface{

        @android.webkit.JavascriptInterface
        public static void showToast(String toast){

        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        mWebView.onResume();
    }


    @Override
    protected void onPause() {
        super.onPause();
        mWebView.onPause();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mWebView.destroy();
        System.exit(0);//退出webView界面单独的进程
    }
}
