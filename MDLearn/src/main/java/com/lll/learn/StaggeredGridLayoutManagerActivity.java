package com.lll.learn;

import android.support.v7.widget.StaggeredGridLayoutManager;

/**
 * Description: 流水布局
 * Version:
 * Created by lll on 2016/5/26.
 * CopyRight lll
 */
public class StaggeredGridLayoutManagerActivity extends BaseLayoutManagerActivity<StaggeredGridLayoutManager> {

    private boolean mVertical = true;

    @Override
    protected StaggeredGridLayoutManager createLayoutManager() {
        if (mVertical) {
            return new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
        } else {
            return new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.HORIZONTAL);
        }
    }

    @Override
    ConfigToggle[] createConfigToggles() {
        return new ConfigToggle[]{
                new ConfigToggle(this, R.string.vertical) {
                    @Override
                    public boolean isChecked() {
                        return mVertical;
                    }

                    @Override
                    public void onChange(boolean newValue) {
                        if (mVertical == newValue) {
                            return;
                        }
                        mVertical = newValue;
                        mRecyclerView.setLayoutManager(createLayoutManager());
                    }
                }
        };
    }
}
