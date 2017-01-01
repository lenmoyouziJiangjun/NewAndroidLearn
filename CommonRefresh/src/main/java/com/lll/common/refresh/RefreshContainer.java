package com.lll.common.refresh;

import android.view.View;
import android.widget.AbsListView;

public class RefreshContainer<T extends AbsListView> implements IRefresh{
    public T refreshContainer;

    public RefreshContainer(T t){
        refreshContainer = t;
    }

    @Override
    public View initHeader() {
        return null;
    }

    @Override
    public View initFooter() {
        return null;
    }

    @Override
    public void startRefresh() {

    }

    @Override
    public void endRefresh() {

    }

    @Override
    public void startLoadMore() {

    }

    @Override
    public void endLoadMore() {

    }
}
