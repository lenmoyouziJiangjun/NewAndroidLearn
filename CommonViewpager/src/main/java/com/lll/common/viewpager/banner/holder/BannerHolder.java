package com.lll.common.viewpager.banner.holder;

import android.content.Context;
import android.view.View;

/**
 * Version 1.0
 * Created by lll on 16/6/27.
 * Description viewPager的item
 * copyright generalray4239@gmail.com
 */
public interface BannerHolder<T> {
    /**
     * 创建Item
     * @param ctx
     * @return
     */
    View createView(Context ctx);

    /**
     * 更新Item界面
     * @param ctx
     * @param position
     * @param data
     */
    void updateItem(Context ctx,int position,T data);

}
