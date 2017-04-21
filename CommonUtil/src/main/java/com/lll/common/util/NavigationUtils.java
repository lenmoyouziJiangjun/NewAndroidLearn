package com.lll.common.util;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

/**
 * Version 1.0
 * Created by lll on 17/4/19.
 * Description Fragment、activity跳转工具类。用于项目解耦
 * copyright generalray4239@gmail.com
 */
public class NavigationUtils {
    /**
     * 显示fragment
     *
     * @param activity
     * @param fragmentContainerId
     * @param fragment
     * @param transAnimas         切换动画
     */
    public static void showFragment(AppCompatActivity activity, int fragmentContainerId, Fragment fragment, int[] transAnimas) {
        FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
        if (transAnimas != null) {
            if (transAnimas.length == 2) {
                transaction.setCustomAnimations(transAnimas[0], transAnimas[1]);
            } else if (transAnimas.length == 4) {
                transaction.setCustomAnimations(transAnimas[0], transAnimas[1], transAnimas[2], transAnimas[3]);
            }
        }
        transaction.hide(activity.getSupportFragmentManager().findFragmentById(fragmentContainerId));
        transaction.add(fragmentContainerId, fragment);
        transaction.addToBackStack(null).commit();
    }

}
