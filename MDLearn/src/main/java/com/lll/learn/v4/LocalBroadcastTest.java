package com.lll.learn.v4;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;

/**
 * Version 1.0
 * Created by lll on 17/5/2.
 * Description 本地广播测试
 * copyright generalray4239@gmail.com
 */
public class LocalBroadcastTest extends AppCompatActivity {
    static final String ACTION_STARTED = "com.example.android.supportv4.STARTED";
    static final String ACTION_UPDATE = "com.example.android.supportv4.UPDATE";
    static final String ACTION_STOPPED = "com.example.android.supportv4.STOPPED";


    LocalBroadcastManager mLocalBroadcastManager;
    LocalReceiver mLocalReceiver;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void initBroadcastManager() {
        mLocalBroadcastManager = LocalBroadcastManager.getInstance(this);
    }

    private void registerLocalReceiver() {
        if (mLocalReceiver == null) {
            mLocalReceiver = new LocalReceiver();
        }
        IntentFilter filter = new IntentFilter();
        filter.addAction("");
        filter.addAction("");
        filter.addAction("");
        mLocalBroadcastManager.registerReceiver(mLocalReceiver, filter);
    }

    private void unRegisterLocalReceiver() {
        if (mLocalReceiver != null) {
            mLocalBroadcastManager.unregisterReceiver(mLocalReceiver);
            mLocalReceiver=null;
        }
    }

    private void sendBroadcastReceiver(){
        Intent intent = new Intent();
        intent.setAction("");
        mLocalBroadcastManager.sendBroadcast(intent);
    }

    /**
     * 定义一个广播
     */
    private class LocalReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(ACTION_STARTED)) {
            } else if (intent.getAction().equals(ACTION_UPDATE)) {
            } else if (intent.getAction().equals(ACTION_STOPPED)) {
            }
        }
    }
}
