package com.tima.core.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tima.core.R;
import com.tima.core.base.BaseActivity;
import com.tima.core.base.BaseFragment;

/**
 * Description:  消息中心的Fragment
 * Version:
 * Created by lll on 2016/5/3.
 * CopyRight lll
 */
public class MessageFragment extends BaseFragment {

    public static MessageFragment newInstance() {
        MessageFragment fragment = new MessageFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       inflater.inflate(R.layout.activity_login,container,false);
        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
