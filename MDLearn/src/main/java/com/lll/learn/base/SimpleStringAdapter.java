package com.lll.learn.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

/**
 * Version 1.0
 * Created by lll on 16/6/28.
 * Description
 * copyright generalray4239@gmail.com
 */
public class SimpleStringAdapter extends RecyclerView.Adapter<SimpleStringAdapter.ViewHolder> {

    Map<String,Class> mDatas;

    List<String > mKeys;
    Context mContext;
    /**
     *
     * @param datas 从外面传入的数据
     */
    public SimpleStringAdapter(Map<String,Class> datas, Context ctx){
        this.mDatas = datas;
        this.mContext = ctx;
        mKeys = new ArrayList<>();
        mKeys .addAll(mDatas.keySet());
    }

    /**
     * 创建View
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder h = new ViewHolder(new TextView(parent.getContext()));

        h.mTextView.setMinimumHeight(128);
//        h.mTextView.setPadding(20, 0, 20, 0);
        h.mTextView.setFocusable(true);
//        h.mTextView.setBackgroundColor(getBackgroundColor(1));
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.leftMargin = 10;
        lp.rightMargin = 5;
        lp.topMargin = 20;
        lp.bottomMargin = 15;
        h.mTextView.setLayoutParams(lp);

        return h;
    }

    /**
     * 给View赋值
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.mTextView.setText(position + ":" + mKeys.get(position));
//        holder.mTextView.setMinHeight((200 + mKeys.get(position).length() * 10));
        holder.mTextView.setBackgroundColor(getBackgroundColor(position));
    }

    /**
     * 返回数据长度
     * @return
     */
    @Override
    public int getItemCount() {
        return mDatas.size();
    }


    /**
     * ViewHolder
     */
    public static class ViewHolder extends RecyclerView.ViewHolder{

        public TextView mTextView;

        public ViewHolder(TextView itemView) {
            super(itemView);
            mTextView = itemView;
        }
    }

    /**
     * 打开Activity
     * @param key
     */
    public void startActivity(String key){
        String skey = key.split(":")[1];
        Log.d("lll", "startActivity: key-==="+key+"-skey==-"+skey+"---value==="+mDatas.get(key));

        Intent intent = new Intent(mContext,mDatas.get(skey));
        mContext.startActivity(intent);
//        ((Activity)mContext).overridePendingTransition();
    }

    /**
     * 设置背景颜色
     * @param position
     * @return
     */
    private int getBackgroundColor(int position) {
        Random random  = new Random();
        int color=random.nextInt(255);
        return Color.argb(1,random.nextInt(255)/255,random.nextInt(255)/255,random.nextInt(255)/255);
    }


}
