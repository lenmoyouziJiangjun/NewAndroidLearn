package com.lll.core.mvp;

/**
 * Description:
 * Version:
 * Created by lll on 2016/5/4.
 * CopyRight lll
 */
public interface IBaseTask {

    interface View extends BaseView<Presenter> {
        /**
         * 显示隐藏loading Dialog
         * @param active
         */
        void switchLoadingDialog(boolean active);

    }

    interface Presenter extends BasePresenter {

    }
}
