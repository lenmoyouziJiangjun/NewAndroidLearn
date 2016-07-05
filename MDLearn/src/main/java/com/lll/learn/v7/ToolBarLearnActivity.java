package com.lll.learn.v7;

import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.ViewUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.lll.common.ui.MiddleLineTextView;
import com.lll.common.util.StatusBarUtil;
import com.lll.common.util.ViewUtil;
import com.lll.learn.R;

public class ToolBarLearnActivity extends AppCompatActivity {

    private Toolbar mToolBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_v7_learn);
        initToolBar();
        StatusBarUtil.setStatusBarColor(this,R.color.colorPrimary);
        ((MiddleLineTextView)findViewById(R.id.test)).setText("ahkjfahdkjhakfjajdkdahk");
    }

    private void initToolBar(){
        mToolBar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolBar);
        mToolBar.setLogo(R.drawable.ic_favorite);
        //设置title
        mToolBar.setTitle("toolBar");
        mToolBar.setTitleTextColor(Color.WHITE);
        mToolBar.setSubtitle("测试toolBar");
        mToolBar.setSubtitleTextColor(Color.WHITE);
        //设置返回icon
        mToolBar.setNavigationIcon(R.drawable.ic_menu_send);
        mToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v,"返回点击",Snackbar.LENGTH_SHORT).show();
            }
        });


        mToolBar.inflateMenu(R.menu.menu_statyle_pic);

        mToolBar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                return false;
            }
        });
//        mToolBar.
//        mToolBar.

    }

    /**
     * 更改模式
     * @param view
     */
    public void change(View view){
        startSupportActionMode(new ActionMode.Callback() {
            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                mode.getMenuInflater().inflate(R.menu.actions,menu);
                return true;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                return false;
            }

            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                return false;
            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {

            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return false;
    }
}
