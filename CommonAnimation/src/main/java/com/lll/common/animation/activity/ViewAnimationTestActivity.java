package com.lll.common.animation.activity;

import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.BounceInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

import com.lll.common.animation.AnimationUtils;
import com.lll.common.animation.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Description: View动画
 * Version:
 * Created by lll on 2016/2/2.
 * CopyRight lll
 */
public class ViewAnimationTestActivity extends AnimationTestBaseActivity {
    @Override
    public List<String> getDatas() {
        List<String> datas = new ArrayList<>();
        datas.add("代码基本属性变化");
        datas.add("xml基本属性变化");
        return datas;
    }

    @Override
    public void onItemClick(int position, String itemName) {
        switch (position) {
            case 0:
                testTranslateAnimation();
                break;
            case 1:
                loadViewAnimationXML();
                break;
        }
    }

    private void testTranslateAnimation() {
        /**
         *  定义唯一动画
         *  前面两个坐标:
         */
        TranslateAnimation animation = new TranslateAnimation(10, 0, -200, 500);
//        animation.setDuration(3000);//设置延时
//        animation.setRepeatCount(2);//设置重复次数
//        animation.setRepeatMode(Animation.REVERSE);
//        animation.setFillAfter(true);//动画结束后,移到动画结束的位置
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
//                imageView.setClickable(false);//清除原来区域的点击事件
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        ScaleAnimation s = new ScaleAnimation(1.0f, 1.5f, 1.0f, 1.5f, 50.0f, 50.0f);
//        s.setInterpolator(new BounceInterpolator());
//        s.setDuration(3000);
        AnimationSet set = new AnimationSet(false);
        set.addAnimation(s);
        set.addAnimation(animation);
        set.setDuration(3000);
        set.setFillAfter(true);
        set.setInterpolator(new BounceInterpolator());
        RotateAnimation r = new RotateAnimation(0, 360, 50, 50);
//        r.setRepeatMode(Animation.R);
        set.setRepeatCount(Animation.INFINITE);//-1表示一直循环
        set.addAnimation(r);
        mTestImageView.startAnimation(set);
    }


    private void loadViewAnimationXML() {
//        AnimationSet set = new AnimationSet(true);
//        set.addAnimation(AnimationUtils.loadViewAnimationXml(this,R.anim.anim_in));
//        set.addAnimation(AnimationUtils.loadViewAnimationXml(this,R.anim.anim_out));
//        set.addAnimation(AnimationUtils.loadViewAnimationXml(this,R.anim.anim_left));
//        set.addAnimation(AnimationUtils.loadViewAnimationXml(this,R.anim.anim_right));
        mTestImageView.startAnimation(AnimationUtils.loadViewAnimationXml(this, R.anim.hyperspace_jump));
    }

}
