package com.tima.core.mvp;

/**
 * Description:
 * Version:
 * Created by lll on 2016/4/12.
 * CopyRight lll
 */
public interface IpnMvpInterface {
    /**
     * 具体View的接口
     */
    interface View extends BaseView<Presenter>{

    }

    /**
     * 具体Presenter的接口
     */
    interface Presenter extends BasePresenter{

    }
}
