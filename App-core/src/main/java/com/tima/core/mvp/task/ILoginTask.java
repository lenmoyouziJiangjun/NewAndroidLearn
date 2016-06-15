package com.tima.core.mvp.task;

import com.tima.core.mvp.BasePresenter;

import java.util.Map;

/**
 * Description:
 * Version:
 * Created by lll on 2016/5/4.
 * CopyRight lll
 */
public interface ILoginTask {
    interface  View  {
        /**
         * 关闭activity
         */
        void close();

        /**
         * 显示隐藏loading Dialog
         * @param active false 关闭loadingDialog,true 显示dialog
         */
        void switchLoadingDialog(boolean active);

        /**
         * 登录参数
         * @return
         */
        Map getLoginParams();
    }

    interface  Presenter extends BasePresenter {
        /**
         * 登录
         */
        void login();

        /**
         * 校验参数合法性
         * @return
         */
        boolean verifyParams();
    }

}
