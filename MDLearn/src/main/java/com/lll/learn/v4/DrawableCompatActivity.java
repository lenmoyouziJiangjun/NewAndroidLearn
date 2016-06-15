package com.lll.learn.v4;

import android.graphics.Color;
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
                if (id == R.id.drawable_compat_no_tint){
                    clearTint();
                }else if(id == R.id.drawable_compat_color){
                    setColorTint();
                }else if(id == R.id.drawable_compat_state_list){
                    setColorStateListTint();
                }
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
