package com.tima.core.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.widget.FrameLayout;
import android.widget.RadioButton;
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
        ((RadioButton)findViewById(R.id.rb_msg)).setChecked(true);
    }

    /**
     * 切换fragment
     *
     * @param id
     */
    private void switchFragment(int id) {
        if(id == R.id.rb_msg){
            mCurrentFragment = MessageFragment.newInstance();
        }else if(id == R.id.rb_book){
            mCurrentFragment = PersonFragment.newInstance();
        }else if(id == R.id.rb_person){
            mCurrentFragment = PersonFragment.newInstance();
        }else if(id == R.id.rb_send){
            mCurrentFragment = PersonFragment.newInstance();
        }

        getSupportFragmentManager().beginTransaction().replace(R.id.fl_main_container,mCurrentFragment).commitAllowingStateLoss();
//        getSupportFragmentManager().beginTransaction().show(mCurrentFragment).commit();
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //再按一次退出应用
    }
}
