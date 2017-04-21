package com.lll.core.base;

import android.app.Activity;
import android.content.ComponentCallbacks;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.multidex.MultiDexApplication;
import android.util.Log;
import android.view.View;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import com.afollestad.appthemeengine.ATE;
import com.lll.common.util.LogUtils;
import com.tima.core.R;

/**
 * Description:
 * Version:
 * Created by lll on 2016/5/3.
 * CopyRight lll
 */
public class BaseApplication extends MultiDexApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        testViewTree();
        setApplicationStyle();
        LogUtils.isDebug = true;
        LogUtils.e("----------do BaseApplication ----------");
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
                Log.e("lll", "---registerActivityLifecycleCallbacks--onCreate-----" + activity.getComponentName().getClassName());
                System.out.print("llllllllllllllllllllllllllllllllllllll");
            }

            @Override
            public void onActivityStarted(Activity activity) {
                Log.e("lll", "---registerActivityLifecycleCallbacks--onActivityStarted-----" + activity.getComponentName().getClassName());

            }

            @Override
            public void onActivityResumed(Activity activity) {
                Log.e("lll", "---registerActivityLifecycleCallbacks--onActivityResumed-----" + activity.getComponentName().getClassName());
//遍历activity的所有View
                testViewAccess(activity.getWindow().getDecorView().getRootView());
            }

            @Override
            public void onActivityPaused(Activity activity) {
                Log.e("lll", "---registerActivityLifecycleCallbacks--onActivityPaused-----" + activity.getComponentName().getClassName());

            }

            @Override
            public void onActivityStopped(Activity activity) {
                Log.e("lll", "---registerActivityLifecycleCallbacks--onActivityStopped-----" + activity.getComponentName().getClassName());

            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {
                Log.e("lll", "--registerActivityLifecycleCallbacks---onActivityDestroyed-----" + activity.getComponentName().getClassName());

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
        //清理缓存，例如图片库，例如自定义的缓存类
    }

    public void testComponentCallbacks() {
        registerComponentCallbacks(new ComponentCallbacks() {
            @Override
            public void onConfigurationChanged(Configuration newConfig) {

            }

            @Override
            public void onLowMemory() {

            }
        });
    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    public void test() {
        registerOnProvideAssistDataListener(new OnProvideAssistDataListener() {
            @Override
            public void onProvideAssistData(Activity activity, Bundle data) {

            }
        });
    }


    private void setApplicationStyle() {
        if (!ATE.config(this, "light_theme").isConfigured()) {
            ATE.config(this, "light_theme")
                    .activityTheme(R.style.AppThemeLight)
                    .primaryColorRes(R.color.colorPrimaryLightDefault)
                    .accentColorRes(R.color.colorAccentLightDefault)
                    .coloredNavigationBar(false)
                    .usingMaterialDialogs(true)
                    .commit();
        }
        if (!ATE.config(this, "dark_theme").isConfigured()) {
            ATE.config(this, "dark_theme")
                    .activityTheme(R.style.AppThemeDark)
                    .primaryColorRes(R.color.colorPrimaryDarkDefault)
                    .accentColorRes(R.color.colorAccentDarkDefault)
                    .coloredNavigationBar(false)
                    .usingMaterialDialogs(true)
                    .commit();
        }
        if (!ATE.config(this, "light_theme_notoolbar").isConfigured()) {
            ATE.config(this, "light_theme_notoolbar")
                    .activityTheme(R.style.AppThemeLight)
                    .coloredActionBar(false)
                    .primaryColorRes(R.color.colorPrimaryLightDefault)
                    .accentColorRes(R.color.colorAccentLightDefault)
                    .coloredNavigationBar(false)
                    .usingMaterialDialogs(true)
                    .commit();
        }
        if (!ATE.config(this, "dark_theme_notoolbar").isConfigured()) {
            ATE.config(this, "dark_theme_notoolbar")
                    .activityTheme(R.style.AppThemeDark)
                    .coloredActionBar(false)
                    .primaryColorRes(R.color.colorPrimaryDarkDefault)
                    .accentColorRes(R.color.colorAccentDarkDefault)
                    .coloredNavigationBar(true)
                    .usingMaterialDialogs(true)
                    .commit();
        }
    }


}
