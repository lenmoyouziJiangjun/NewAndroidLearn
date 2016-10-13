package com.lll.common.animation.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lll.common.animation.R;

import java.util.List;
import java.util.Random;

public abstract class AnimationTestActivity extends AppCompatActivity {

    public ImageView mTestImageView;

    public RecyclerView mRecyclerView;

    private List<String> mDatas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation_test);
        initView();
    }

    private void initView() {
        mTestImageView = (ImageView) findViewById(R.id.img_test);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
        mRecyclerView.setAdapter(new SimpleStringAdapter(mDatas, this));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
    }

    /**
     * 设置数据
     *
     * @return
     */
    public abstract List<String> getDatas();

    /**
     * 点击数据
     *
     * @param position 点击索引
     * @param itemName 对应的item的名称
     */
    public abstract void onItemClick(int position, String itemName);

    /**
     *
     */
    public class SimpleStringAdapter extends RecyclerView.Adapter<TextViewViewHolder> {
        List<String> mDatas;
        Context mContext;

        /**
         * @param datas 从外面传入的数据
         */
        public SimpleStringAdapter(List<String> datas, Context ctx) {
            this.mDatas = datas;
            this.mContext = ctx;

        }

        /**
         * 创建View
         *
         * @param parent
         * @param viewType
         * @return
         */
        @Override
        public TextViewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            TextViewViewHolder h = new TextViewViewHolder(new TextView(parent.getContext()));

            h.mTextView.setMinimumHeight(128);
            h.mTextView.setFocusable(true);
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
         *
         * @param holder
         * @param position
         */
        @Override
        public void onBindViewHolder(TextViewViewHolder holder, final int position) {
            holder.mTextView.setText(position + ":" + mDatas.get(position));
            holder.mTextView.setBackgroundColor(getBackgroundColor());
            holder.mTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClick(position, mDatas.get(position));
                }
            });
        }

        /**
         * 返回数据长度
         *
         * @return
         */
        @Override
        public int getItemCount() {
            return mDatas.size();
        }


        /**
         * 设置背景颜色
         *
         * @return
         */
        private int getBackgroundColor() {
            int color = new Random().nextInt(255);
            return Color.argb(1, color / 255, color / 255, color / 255);
        }
    }

    /**
     * ViewHolder
     */
    public static class TextViewViewHolder extends RecyclerView.ViewHolder {
        public TextView mTextView;

        public TextViewViewHolder(TextView itemView) {
            super(itemView);
            mTextView = itemView;
        }
    }

    /**
     * 分割线
     */
    private static class DividerItemDecoration extends RecyclerView.ItemDecoration {
        private static final int[] ATTRS = new int[]{
                android.R.attr.listDivider
        };

        public static final int HORIZONTAL_LIST = LinearLayoutManager.HORIZONTAL;

        public static final int VERTICAL_LIST = LinearLayoutManager.VERTICAL;

        private Drawable mDivider;

        private int mOrientation;

        public DividerItemDecoration(Context context, int orientation) {
            final TypedArray a = context.obtainStyledAttributes(ATTRS);
            mDivider = a.getDrawable(0);
            a.recycle();
            setOrientation(orientation);
        }

        public void setOrientation(int orientation) {
            if (orientation != HORIZONTAL_LIST && orientation != VERTICAL_LIST) {
                throw new IllegalArgumentException("invalid orientation");
            }
            mOrientation = orientation;
        }

        @Override
        public void onDraw(Canvas c, RecyclerView parent) {
            if (mOrientation == VERTICAL_LIST) {
                drawVertical(c, parent);
            } else {
                drawHorizontal(c, parent);
            }
        }

        public void drawVertical(Canvas c, RecyclerView parent) {
            final int left = parent.getPaddingLeft();
            final int right = parent.getWidth() - parent.getPaddingRight();

            final int childCount = parent.getChildCount();
            for (int i = 0; i < childCount; i++) {
                final View child = parent.getChildAt(i);
                final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                        .getLayoutParams();
                final int top = child.getBottom() + params.bottomMargin +
                        Math.round(ViewCompat.getTranslationY(child));
                final int bottom = top + mDivider.getIntrinsicHeight();
                mDivider.setBounds(left, top, right, bottom);
                mDivider.draw(c);
            }
        }

        public void drawHorizontal(Canvas c, RecyclerView parent) {
            final int top = parent.getPaddingTop();
            final int bottom = parent.getHeight() - parent.getPaddingBottom();

            final int childCount = parent.getChildCount();
            for (int i = 0; i < childCount; i++) {
                final View child = parent.getChildAt(i);
                final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                        .getLayoutParams();
                final int left = child.getRight() + params.rightMargin +
                        Math.round(ViewCompat.getTranslationX(child));
                final int right = left + mDivider.getIntrinsicHeight();
                mDivider.setBounds(left, top, right, bottom);
                mDivider.draw(c);
            }
        }

        @Override
        public void getItemOffsets(Rect outRect, int itemPosition, RecyclerView parent) {
            if (mOrientation == VERTICAL_LIST) {
                outRect.set(0, 0, 0, mDivider.getIntrinsicHeight());
            } else {
                outRect.set(0, 0, mDivider.getIntrinsicWidth(), 0);
            }
        }
    }


}
