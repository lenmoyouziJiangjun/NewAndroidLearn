package com.lll.learn.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.lll.common.util.LogUtils;
import com.lll.learn.DividerItemDecoration;
import com.lll.learn.R;

import java.util.List;
import java.util.Map;

/**
 * Version 1.0
 * Created by lll on 16/6/28.
 * Description
 * copyright generalray4239@gmail.com
 */
public abstract class BaseListStringActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;

    private Map<String,Class> mDatas;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //左滑出现anr了。不晓得什么原因，
//        getWindow().addFlags(Window.FEATURE_SWIPE_TO_DISMISS);
        setContentView(R.layout.activity_mdlearn);
        initView();

    }


    private void initView(){
        mRecyclerView = (RecyclerView) findViewById(R.id.ryv);
        LinearLayoutManager manager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);

        mRecyclerView.setLayoutManager(manager);
        mDatas = getDatas();
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL_LIST));
        mRecyclerView.setAdapter(new SimpleStringAdapter(mDatas,this){
            @Override
            public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                final ViewHolder holder = super.onCreateViewHolder(parent,viewType);
                holder.mTextView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(holder.mTextView.getText().toString());
                    }
                });
                return  holder;
            }
        });
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
    }

    public abstract Map<String,Class> getDatas();

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        LogUtils.e("----------------------");
    }
}
