package com.lll.core.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;

import com.lll.core.base.BaseActivity;
import com.lll.core.fragment.ImageGridFragment;

/**
 * Version 1.0
 * Created by lll on 16/7/25.
 * Description
 * copyright generalray4239@gmail.com
 */
public class BitmapLoaderActivity extends BaseActivity {
    private static final String TAG = "BitmapLoaderActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportFragmentManager().findFragmentByTag(TAG) == null) {
            final FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.add(android.R.id.content, new ImageGridFragment(), TAG);
            ft.commit();
        }
    }
}
