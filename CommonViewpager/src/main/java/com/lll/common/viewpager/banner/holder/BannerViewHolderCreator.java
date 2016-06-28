package com.lll.common.viewpager.banner.holder;

/**
 * Version 1.0
 * Created by lll on 16/6/27.
 * Description 创建一个holderCreator用来创建pagerAdapter的view
 * copyright generalray4239@gmail.com
 */
public interface BannerViewHolderCreator<BannerHolder> {

    /**
     * 开放接口给子类实现,
     * @return
     */
    BannerHolder createHolder();
}
