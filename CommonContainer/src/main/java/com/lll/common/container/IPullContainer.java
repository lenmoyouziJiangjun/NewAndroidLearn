package com.lll.common.container;

import android.view.View;


public interface IPullContainer {

    void addHeader(View header);

    void addFooter(View footer);


    void startRefresh();

    void endRefresh();

    void startLoadMore();

    void endLoadMore();
}
