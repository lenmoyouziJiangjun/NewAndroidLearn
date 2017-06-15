package com.lll.common.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

/**
 * Version 1.0
 * Created by lll on 17/1/2.
 * Description  实现 design包的TabLayout功能，
 *              根据传递的tabs的长度自动决定是滚动还是规定tab。支持带图标的tab
 * copyright generalray4239@gmail.com
 */
public class TabLayout extends LinearLayout {
    public TabLayout(Context context) {
        super(context);
    }

    public TabLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TabLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
