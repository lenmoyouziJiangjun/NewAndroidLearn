package com.lll.common.ui.setting;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Switch;

/**
 * Version 1.0
 * Created by lll on 16/8/19.
 * Description 带有开关设置的item
 * copyright generalray4239@gmail.com
 */
public class SettingSwitchItem  extends SettingDefaultItem{

    private Switch mSwitchButton;


    public SettingSwitchItem(Context context) {
        super(context);
    }

    public SettingSwitchItem(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SettingSwitchItem(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    void inflateView() {
        mSwitchViewStub.inflate();
    }


}
