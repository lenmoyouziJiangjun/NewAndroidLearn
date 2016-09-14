package com.tima.core.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tima.core.R;
import com.tima.core.base.BaseFragment;

/**
 * Version 1.0
 * Created by lll on 16/9/13.
 * Description
 * copyright generalray4239@gmail.com
 */
public class MessageTabFragment extends BaseFragment {

    TextView mtext;


    public static MessageTabFragment getInstance(String tabName,String tabId){
        MessageTabFragment f = new MessageTabFragment();
        Bundle b = new Bundle();
        b.putString("tabName",tabName);
        b.putString("tabId",tabId);
        f.setArguments(b);
        return f;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_msg_tab,container,false);
        mtext = (TextView) view.findViewById(R.id.text);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mtext.setText(getArguments().getString("tabName"));
    }
}
