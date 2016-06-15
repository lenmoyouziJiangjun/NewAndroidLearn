package com.lll.learn;

import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * Description: 线性布局
 * Version:
 * Created by lll on 2016/5/26.
 * CopyRight lll
 */
public class LinearLayoutManagerActivity extends BaseLayoutManagerActivity<LinearLayoutManager> {

    private DividerItemDecoration mDividerItemDecoration;

    @Override
    protected LinearLayoutManager createLayoutManager() {
        return new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
    }

    @Override
    protected void onRecyclerViewInit(RecyclerView recyclerView) {
        mDividerItemDecoration = new DividerItemDecoration(this, mLayoutManager.getOrientation());
        recyclerView.addItemDecoration(mDividerItemDecoration);
    }

    @Override
    ConfigToggle[] createConfigToggles() {
        return new ConfigToggle[]{
                new ConfigToggle(this, R.string.checkbox_orientation) {
                    @Override
                    public boolean isChecked() {
                        return mLayoutManager.getOrientation() == LinearLayoutManager.HORIZONTAL;
                    }

                    @Override
                    public void onChange(boolean newValue) {
                        mLayoutManager.setOrientation(newValue ? LinearLayoutManager.HORIZONTAL
                                : LinearLayoutManager.VERTICAL);
                        if (mDividerItemDecoration != null) {
                            mDividerItemDecoration.setOrientation(mLayoutManager.getOrientation());
                        }

                    }
                },
                new ConfigToggle(this, R.string.checkbox_reverse) {
                    @Override
                    public boolean isChecked() {
                        return mLayoutManager.getReverseLayout();
                    }

                    @Override
                    public void onChange(boolean newValue) {
                        mLayoutManager.setReverseLayout(newValue);
                    }
                },
                new ConfigToggle(this, R.string.checkbox_layout_dir) {
                    @Override
                    public boolean isChecked() {
                        return ViewCompat.getLayoutDirection(mRecyclerView) ==
                                ViewCompat.LAYOUT_DIRECTION_RTL;
                    }

                    @Override
                    public void onChange(boolean newValue) {
                        ViewCompat.setLayoutDirection(mRecyclerView, newValue ?
                                ViewCompat.LAYOUT_DIRECTION_RTL : ViewCompat.LAYOUT_DIRECTION_LTR);
                    }
                },
                new ConfigToggle(this, R.string.checkbox_stack_from_end) {
                    @Override
                    public boolean isChecked() {
                        return mLayoutManager.getStackFromEnd();
                    }

                    @Override
                    public void onChange(boolean newValue) {
                        mLayoutManager.setStackFromEnd(newValue);
                    }
                }
        };
    }
}
