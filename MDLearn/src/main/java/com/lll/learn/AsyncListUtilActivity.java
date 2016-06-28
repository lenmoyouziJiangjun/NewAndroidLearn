package com.lll.learn;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.util.AsyncListUtil;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class AsyncListUtilActivity extends AppCompatActivity {

    private RecyclerView mRecycleView;

    private LinearLayoutManager mLinearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createRecycleView();
        setContentView(mRecycleView);

    }


    private void createRecycleView() {
        mRecycleView = new RecyclerView(this);

        //第一步：设置layoutManager
        mLinearLayoutManager = new LinearLayoutManager(this);
//        mLinearLayoutManager.canScrollHorizontally();
        mLinearLayoutManager.canScrollVertically();
        mRecycleView.setLayoutManager(mLinearLayoutManager);

        mRecycleView.setHasFixedSize(true);

        //第二步：设置params
        final ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mRecycleView.setLayoutParams(layoutParams);
        //第三步：设置adapter
        mRecycleView.setAdapter(new AsyncAdapter());
    }

    private static class TextViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        public TextViewHolder(Context context) {
            super(new TextView(context));
            textView = (TextView) itemView;
        }
    }


    private class AsyncAdapter extends RecyclerView.Adapter<TextViewHolder> {

        private AsyncListUtil<String> mAsyncListUtil;

        AsyncAdapter() {
            mAsyncListUtil = new AsyncStringListUtil();
        }

        @Override
        public TextViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new TextViewHolder(parent.getContext());
        }

        @Override
        public void onBindViewHolder(TextViewHolder holder, int position) {
            final String itemString = mAsyncListUtil.getItem(position);
            if (itemString == null) {
                holder.textView.setText("loading...");
            } else {
                holder.textView.setText(itemString);
            }
        }

        @Override
        public int getItemCount() {
            return mAsyncListUtil.getItemCount();
        }
    }

    private class AsyncStringListUtil extends AsyncListUtil<String> {

        private static final int TILE_SIZE = 5;

        private static final long DELAY_MS = 500;

        public AsyncStringListUtil() {
            super(String.class, TILE_SIZE,
                    new AsyncListUtil.DataCallback<String>() {
                        @Override
                        public int refreshData() {
                            return com.lll.learn.Cheeses.sCheeseStrings.length;
                        }

                        @Override
                        public void fillData(String[] data, int startPosition, int itemCount) {
                            sleep();
                            for (int i = 0; i < itemCount; i++) {
                                data[i] = com.lll.learn.Cheeses.sCheeseStrings[startPosition + i];
                            }
                        }

                        private void sleep() {
                            try {
                                Thread.sleep(DELAY_MS);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    },
                    new AsyncListUtil.ViewCallback() {
                        @Override
                        public void getItemRangeInto(int[] outRange) {
                            outRange[0] = mLinearLayoutManager.findFirstVisibleItemPosition();
                            outRange[1] = mLinearLayoutManager.findLastVisibleItemPosition();
                        }

                        @Override
                        public void onDataRefresh() {
                            mRecycleView.getAdapter().notifyDataSetChanged();
                        }

                        @Override
                        public void onItemLoaded(int position) {
                            mRecycleView.getAdapter().notifyItemChanged(position);
                        }
                    });

            mRecycleView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    onRangeChanged();
                }
            });
        }
    }


    private class AsyncContactListUtil extends AsyncListUtil<Contact>{

        /**
         * Creates an AsyncListUtil.
         *
         * @param klass        Class of the data item.
         * @param tileSize     Number of item per chunk loaded at once.
         * @param dataCallback Data access callback.
         * @param viewCallback Callback for querying visible item range and update notifications.
         */
        public AsyncContactListUtil(Class<Contact> klass, int tileSize, DataCallback<Contact> dataCallback, ViewCallback viewCallback) {
            super(klass, tileSize, dataCallback, viewCallback);
        }
    }

}
