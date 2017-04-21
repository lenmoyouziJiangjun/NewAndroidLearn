package com.lll.core.mvp;

/**
 * Description: view公共接口
 * Version:
 * Created by lll on 2016/4/12.
 * CopyRight lll
 */
public interface BaseView<T extends BasePresenter> {
    /**
     *
     * @param t
     */
    void setPresenter(T t);

}
