package com.lll.learn.v4;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.AppCompatActivity;
import android.widget.TabHost;

import com.lll.learn.R;

/**
 * Version 1.0
 * Created by lll on 16/9/18.
 * Description
 * copyright generalray4239@gmail.com
 */
public class FragmentTabHostActivity extends AppCompatActivity {

    FragmentTabHost mTabHost;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void testTabHost(){
        mTabHost = new FragmentTabHost(this);
        setContentView(mTabHost);
        mTabHost.setup(this, getSupportFragmentManager());

//        mTabHost.addTab(mTabHost.newTabSpec("menus").setIndicator("Menus"),
//                FragmentMenuFragmentSupport.class, null);
//        mTabHost.addTab(mTabHost.newTabSpec("contacts").setIndicator("Contacts"),
//                LoaderCursorSupport.CursorLoaderListFragment.class, null);
//        mTabHost.addTab(mTabHost.newTabSpec("stack").setIndicator("Stack"),
//                FragmentStackFragmentSupport.class, null);
//        mTabHost.addTab(mTabHost.newTabSpec("tabs").setIndicator("Tabs"),
//                FragmentTabsFragmentSupport.class, null);
//
//        mTabHost.newTabSpec("").setIndicator()
        mTabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {

            }
        });
    }
}
