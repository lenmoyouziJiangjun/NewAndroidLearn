package com.lll.learn.v4;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RadioGroup;

import com.lll.learn.R;

public class DrawableCompatActivity extends AppCompatActivity {
    private static final int IMAGE_RES = R.drawable.ic_favorite;

    private ImageView mImageView;
    private Drawable mDrawable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawable_compat);

        mImageView = (ImageView) findViewById(R.id.image);
        //加载资源
        Drawable d = ContextCompat.getDrawable(this, IMAGE_RES);
        //设置着色，必须调用这个
        mDrawable = DrawableCompat.wrap(d.mutate());

        mImageView.setImageDrawable(mDrawable);

        RadioGroup rg = (RadioGroup) findViewById(R.id.drawable_compat_options);
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int id) {
                if (id == R.id.drawable_compat_no_tint) {
                    clearTint();
                } else if (id == R.id.drawable_compat_color) {
                    setColorTint();
                } else if (id == R.id.drawable_compat_state_list) {
                    setColorStateListTint();
                }
            }
        });

        RadioGroup rgM = (RadioGroup) findViewById(R.id.drawable_compat_mode);
        rgM.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                PorterDuff.Mode mode = null;
                //测试不同Tint模式的效果
                if (checkedId == R.id.drawable_mode_1) {
                    mode = PorterDuff.Mode.CLEAR;
                } else if (checkedId == R.id.drawable_mode_2) {
                    mode = PorterDuff.Mode.SRC;
                } else if (checkedId == R.id.drawable_mode_3) {
                    mode = PorterDuff.Mode.DST;
                } else if (checkedId == R.id.drawable_mode_4) {
                    mode = PorterDuff.Mode.SRC_OVER;
                } else if (checkedId == R.id.drawable_mode_5) {
                    mode = PorterDuff.Mode.DST_OVER;
                } else if (checkedId == R.id.drawable_mode_6) {
                    mode = PorterDuff.Mode.SRC_IN;
                } else if (checkedId == R.id.drawable_mode_7) {
                    mode = PorterDuff.Mode.DST_IN;
                } else if (checkedId == R.id.drawable_mode_8) {
                    mode = PorterDuff.Mode.SRC_OUT;
                } else if (checkedId == R.id.drawable_mode_9) {
                    mode = PorterDuff.Mode.DST_OUT;
                } else if (checkedId == R.id.drawable_mode_10) {
                    mode = PorterDuff.Mode.SRC_ATOP;
                } else if (checkedId == R.id.drawable_mode_11) {
                    mode = PorterDuff.Mode.DST_ATOP;
                } else if (checkedId == R.id.drawable_mode_12) {
                    mode = PorterDuff.Mode.XOR;
                } else if (checkedId == R.id.drawable_mode_13) {
                    mode = PorterDuff.Mode.DARKEN;
                } else if (checkedId == R.id.drawable_mode_14) {
                    mode = PorterDuff.Mode.LIGHTEN;
                } else if (checkedId == R.id.drawable_mode_15) {
                    mode = PorterDuff.Mode.MULTIPLY;
                } else if (checkedId == R.id.drawable_mode_16) {
                    mode = PorterDuff.Mode.SCREEN;
                } else if (checkedId == R.id.drawable_mode_17) {
                    mode = PorterDuff.Mode.ADD;
                } else if (checkedId == R.id.drawable_mode_18) {
                    mode = PorterDuff.Mode.OVERLAY;
                }
                DrawableCompat.setTintMode(mDrawable, mode);//两中都显示
            }
        });
    }

    /**
     * 清楚颜色
     */
    private void clearTint() {
        DrawableCompat.setTintList(mDrawable, null);
    }

    /**
     * 设置颜色
     */
    private void setColorTint() {

        DrawableCompat.setTint(mDrawable, Color.MAGENTA);
    }

    /**
     * 设置其它颜色
     */
    private void setColorStateListTint() {
        DrawableCompat.setTintList(mDrawable,
                ContextCompat.getColorStateList(this, R.color.tint_state_list));
    }

}
