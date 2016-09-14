package com.tima.core.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lll.common.viewpager.tab.ViewPagerTabStrip;
import com.tima.core.R;
import com.tima.core.base.BaseActivity;
import com.tima.core.base.BaseFragment;
import com.tima.core.pojo.MessageTab;

import java.util.ArrayList;
import java.util.List;

/**
 * Description:  消息中心的Fragment
 * Version:
 * Created by lll on 2016/5/3.
 * CopyRight lll
 */
public class MessageFragment extends BaseFragment {

    private ViewPagerTabStrip mPagerTab;
    private ViewPager mViewPager;

    public static MessageFragment newInstance() {
        MessageFragment fragment = new MessageFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_message, container, false);
        mPagerTab = (ViewPagerTabStrip) v.findViewById(R.id.vpt);
        mViewPager = (ViewPager) v.findViewById(R.id.vp);
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    private List<MessageTab> getTabs(){
        List<MessageTab> tabs = new ArrayList<>(10);
        tabs.add(new MessageTab("微博",1000));
        tabs.add(new MessageTab("朋友圈",1001));
        tabs.add(new MessageTab("人人网",1002));
        tabs.add(new MessageTab("知乎",1003));
        tabs.add(new MessageTab("人人网3",1004));
        tabs.add(new MessageTab("人人网4",1005));
        return tabs;
    }

    private void initView() {
        MyViewPagerAdapter adapter=new MyViewPagerAdapter(getFragmentManager());
        adapter.setTitles(getTabs());
        mViewPager.setAdapter(adapter);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mPagerTab.setViewPager(mViewPager);
    }

    private class MyViewPagerAdapter extends FragmentPagerAdapter {

        private List<MessageTab> mTitles;

        public MyViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        public void setTitles(List<MessageTab> titles) {
            mTitles = titles;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles.get(position).title;
        }

        @Override
        public Fragment getItem(int position) {
            MessageTab tab = mTitles.get(position);

            return MessageTabFragment.getInstance(tab.title, tab.id + "");
        }

        @Override
        public int getCount() {
            return mTitles == null ? 0 : mTitles.size();
        }
    }
}
