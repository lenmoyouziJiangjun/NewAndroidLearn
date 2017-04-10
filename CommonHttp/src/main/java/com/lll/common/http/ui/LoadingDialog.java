package com.lll.common.http.ui;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.lang.ref.WeakReference;

import okhttp3.Call;

/**
 * Version 1.0
 * Created by lll on 17/3/10.
 * Description 公共dialog
 * copyright generalray4239@gmail.com
 */
public class LoadingDialog {
    WeakReference<Call> mProcessCallReference;

    private Dialog mLoadingDialog;
    private LoadingView mLoadingView;

    public LoadingDialog(Context context) {
        mLoadingDialog = new Dialog(context);
        mLoadingDialog.setOnKeyListener(new MyKeyBackListener());
        mLoadingDialog.setOnDismissListener(new MyDismissListener());
        mLoadingView = new LoadingView(context);
        mLoadingDialog.setContentView(mLoadingView);
    }

    public void showLoadingDialog() {
        showLoadingDialog(-1, null);
    }

    public void showLoadingDialog(int loadStrId) {
        showLoadingDialog(loadStrId, null);
    }

    public void showLoadingDialog(int loadStrId, Call processCall) {
        if (null != processCall) {
            mProcessCallReference = new WeakReference<Call>(processCall);
        }
        if (loadStrId != -1) {
            mLoadingView.setLoadingTitle(loadStrId);
        }
        if (!mLoadingDialog.isShowing()) {
            mLoadingDialog.show();
        }
    }

    public void dismissDialog() {
        if (mLoadingDialog.isShowing()) {
            mLoadingDialog.dismiss();
        }
    }


    private class LoadingView extends LinearLayout {
        TextView mTitle;

        public LoadingView(Context context) {
            super(context);
        }

        public LoadingView(Context context, AttributeSet attrs) {
            super(context, attrs);
        }

        public void setLoadingTitle(int loadStrId) {
            mTitle.setText(loadStrId);
        }
    }

    private class MyDismissListener implements DialogInterface.OnDismissListener {

        @Override
        public void onDismiss(DialogInterface dialog) {
            dialog.dismiss();
            if (null != mProcessCallReference && null != mProcessCallReference.get()) {
                mProcessCallReference.clear();
            }
        }
    }

    /**
     * 支持取消请求
     */
    private class MyKeyBackListener implements DialogInterface.OnKeyListener {

        @Override
        public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                try {
                    if (null != mProcessCallReference && null != mProcessCallReference.get()) {
                        if (!mProcessCallReference.get().isCanceled()) {
                            mProcessCallReference.get().cancel();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                dialog.dismiss();
                return true;
            }
            return false;
        }
    }
}
