package com.tima.core.base;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;
import android.view.View;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import com.lll.bitmaploader.cache.ImageCache;

/**
 * Description:
 * Version:
 * Created by lll on 2016/5/3.
 * CopyRight lll
 */
public class BaseApplication extends MultiDexApplication {

    @Override
    public void onCreate() {
//        MultiDex.install(this);
        super.onCreate();
        testViewTree();
    }

    /**
     * 测试公共打点
     */
    private void testViewTree() {
        /**
         * activity 生命周期的监控
         */
        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

            }

            @Override
            public void onActivityStarted(Activity activity) {

            }

            @Override
            public void onActivityResumed(Activity activity) {
                //遍历activity的所有View
                testViewAccess(activity.getWindow().getDecorView().getRootView());
            }

            @Override
            public void onActivityPaused(Activity activity) {

            }

            @Override
            public void onActivityStopped(Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {

            }
        });
    }


    private void testViewAccess(View view) {
        view.setAccessibilityDelegate(new View.AccessibilityDelegate() {
            @Override
            public void onInitializeAccessibilityEvent(View host, AccessibilityEvent event) {
                super.onInitializeAccessibilityEvent(host, event);
            }

            @Override
            public void onInitializeAccessibilityNodeInfo(View host, AccessibilityNodeInfo info) {
                super.onInitializeAccessibilityNodeInfo(host, info);
            }
        });
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }
}
