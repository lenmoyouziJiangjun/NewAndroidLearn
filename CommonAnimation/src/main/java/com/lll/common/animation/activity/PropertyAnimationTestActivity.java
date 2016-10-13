package com.lll.common.animation.activity;

import android.annotation.TargetApi;
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
}
