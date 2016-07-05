package com.lll.learn.md;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;

import com.lll.common.util.StatusBarUtil;
import com.lll.learn.R;

/**
 *  MD风格的详情界面
 */
public class DetailTemActivity extends AppCompatActivity {

    private ImageView mBg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_tem);
        StatusBarUtil.setTranslucent(this);
        initView();
    }
    private void initView(){
        mBg = (ImageView) findViewById(R.id.backdrop);
        mBg.setBackgroundResource(R.drawable.p1);
//        mBg.setBackground(getResources().getDrawable(R.drawable.p1));
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.actions, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

}
