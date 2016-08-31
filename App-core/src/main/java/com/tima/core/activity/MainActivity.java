package com.tima.core.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.widget.FrameLayout;
import android.widget.RadioGroup;

import com.tima.core.R;
import com.tima.core.base.BaseActivity;
import com.tima.core.base.BaseFragment;
import com.tima.core.fragment.MessageFragment;
import com.tima.core.fragment.PersonFragment;

/**
 * Description: 主界面
 * Version:
 * Created by lll on 2016/5/3.
 * CopyRight lll
 */
public class MainActivity extends BaseActivity {


    private RadioGroup mRadioGroup;

    private BaseFragment mCurrentFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        mRadioGroup = (RadioGroup) findViewById(R.id.rg_bottom);
        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switchFragment(checkedId);
            }
        });
    }

    /**
     * 切换fragment
     *
     * @param id
     */
    private void switchFragment(int id) {
        if (mCurrentFragment != null) {
            getSupportFragmentManager().beginTransaction().hide(mCurrentFragment).commit();
        }
        switch (id) {
            case 0:
                mCurrentFragment = MessageFragment.newInstance();
                break;
            case 1:
                mCurrentFragment = PersonFragment.newInstance();
                break;
            case 2:
                mCurrentFragment = PersonFragment.newInstance();
                break;
            case 3:
                mCurrentFragment = PersonFragment.newInstance();
                break;
        }
        getSupportFragmentManager().beginTransaction().show(mCurrentFragment).commit();
    }


    @Override
    public void onBackPressed() {
        //再按一次退出应用
    }
}
