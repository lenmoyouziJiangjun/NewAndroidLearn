package com.lll.common.animation;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.ViewAnimationUtils;
import android.view.animation.Animation;


/**
 * Description: 打开activity的动画工具类
 * Version:
 * Created by lll on 2016/2/2.
 * CopyRight lll
 */
public class AnimationUtils {

    /**
     * 左右动画打开activity
     *
     * @param activity
     * @param intent
     */
    public static void startActivityWithAnimation(Activity activity, Intent intent) {
        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.anim_in, R.anim.anim_out);
    }

    /**
     * 指定动画打开Activity
     * @param activity
     * @param intent
     * @param inId 进入动画
     * @param outId 取消动画
     */
    public static void startActivityWithAnimation(Activity activity, Intent intent,int inId,int outId) {
        activity.startActivity(intent);
        activity.overridePendingTransition(inId, outId);
    }


    /**
     * 左右动画startActivityForResult
     *
     * @param activity
     * @param intent
     */
    public static void startActivityForResultWithAnimation(Activity activity, Intent intent, int resultCode) {
        activity.startActivityForResult(intent, resultCode);
        activity.overridePendingTransition(R.anim.anim_in, R.anim.anim_out);
    }


    /**
     * 加载ViewAnimation的xml资源
     * @param ctx
     * @param aId 定义的动画资源
     * @return
     */
    public static Animation loadViewAnimationXml(Context ctx,int aId){
        return android.view.animation.AnimationUtils.loadAnimation(ctx,aId);
    }
}
