package com.lll.common.animation.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewAnimationUtils;

import com.lll.common.animation.R;

import java.util.ArrayList;
import java.util.List;


public class PropertyAnimationTestActivity extends AnimationTestBaseActivity {


    @Override
    public List<String> getDatas() {
        List<String> datas = new ArrayList<>();
        datas.add("代码基本属性变化");
        datas.add("xml基本属性变化");
        datas.add("ViewAnimationUtils 圆形变换");
        datas.add("Activity转场动画");
        return datas;
    }

    @Override
    public void onItemClick(int position, String itemName) {
        switch (position) {
            case 0:
                testCodeAnimation();
                break;
            case 1:
                testXmlAnimation();
                break;
            case 2:
                testSimpleCircleAnimator();
                break;
            case 3:
                testActivityOptions();
                break;
        }
    }

    public void testCodeAnimation() {

    }

    public void testXmlAnimation() {

    }

    /**
     *
     */
    @TargetApi(21)
    private void testSimpleCircleAnimator() {
        ViewAnimationUtils.createCircularReveal(mTestImageView, (int) mTestImageView.getX(), (int) mTestImageView.getY(), 0, 360).setDuration(3000).start();
    }

    @TargetApi(16)
    private void testActivityOptions() {
        ActivityOptionsCompat compat = ActivityOptionsCompat.makeScaleUpAnimation(mTestImageView, (int) mTestImageView.getX()/2, (int) mTestImageView.getY()/2, 0, 0);
        Intent intent = new Intent(this, ViewAnimationTestActivity.class);
        startActivity(intent, compat.toBundle());
    }
}
