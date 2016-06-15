package com.tima.core.base;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tima.common.util.CommonUtils;
import com.tima.core.R;
import com.tima.core.mvp.IBaseTask;

/**
 * A simple {@link Fragment} subclass.
 */
public class BaseFragment extends Fragment {

    BaseActivity mBaseActivity;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mBaseActivity = (BaseActivity) context;
    }


    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
//        getActivity().overridePendingTransition();
    }

    public void showDialog() {
        mBaseActivity.showLoadingDialog();
    }

    public void dismissDialog() {
        mBaseActivity.dismissLoadingDialog();
    }
}
