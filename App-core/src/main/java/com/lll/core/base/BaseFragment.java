package com.lll.core.base;


import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

//import com.tima.common.util.CommonUtils;
import com.lll.core.mvp.BasePresenter;

/**
 * A simple {@link Fragment} subclass.
 */
public class BaseFragment<T extends BasePresenter> extends Fragment {

    BaseActivity mBaseActivity;

    private T mPresenter;


    public T getPresenter(){
        return mPresenter;
    }

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
