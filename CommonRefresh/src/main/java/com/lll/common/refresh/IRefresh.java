package com.lll.common.refresh;

import android.view.View;

public interface IRefresh {

    View initHeader();

    View initFooter();


    void startRefresh();

    void endRefresh();

    void startLoadMore();

    void endLoadMore();
}
