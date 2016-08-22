package com.lll.common.ui.setting;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewStub;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tima.common.ui.R;

/**
 * Version 1.0
 * Created by lll on 16/8/19.
 * Description 公共的设置item:
 *            <p> 从左到右依次为logo,title,小红点,描述,开关或者下一页 <p/>
 * copyright generalray4239@gmail.com
 */
public abstract class SettingDefaultItem extends LinearLayout{
    private ImageView mLogo;
    private TextView mTitle;
    private TextView mRedPoint;
    private TextView mDiscription;



    public ViewStub mNextViewStub;
    public ViewStub mSwitchViewStub;
    public ViewStub mSeekBarViewStub;


    public Context mContext;
    public SettingDefaultItem(Context context) {
        super(context);
    }

    public SettingDefaultItem(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SettingDefaultItem(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context){
        mContext  = context;
        LayoutInflater.from(mContext).inflate(R.layout.setting_cell,this);
        inflateView();
    }

    /**
     * 加载对应的ViewStab
     */
   abstract void inflateView();

}
