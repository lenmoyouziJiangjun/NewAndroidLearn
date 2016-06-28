package com.lll.learn.v4;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.lll.learn.R;

/**
 * NestedScroll 里面嵌套RecyclerView和ViewPager
 */
public class NestedScroll2Activity extends AppCompatActivity {


    private ViewPager mBanner;
    private RecyclerView mTabs;
    private RecyclerView mContent;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nested_scroll2);
        initView();
    }


    private void initView(){
        mBanner = (ViewPager) findViewById(R.id.vp_banner);
        mTabs = (RecyclerView) findViewById(R.id.taps);
        mContent  = (RecyclerView) findViewById(R.id.content);
    }


    private void initViewPager(){
         mBanner.setAdapter(new PagerAdapter() {
             @Override
             public int getCount() {
                 return 0;
             }

             @Override
             public boolean isViewFromObject(View view, Object object) {
                 return false;
             }
         });

    }

    private void initTabs(){
        LinearLayoutManager manager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        mTabs.setLayoutManager(manager);

    }

    private void initContent(){

    }


}
