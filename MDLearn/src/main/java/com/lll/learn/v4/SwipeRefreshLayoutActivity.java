package com.lll.learn.v4;

import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.lll.learn.R;

public class SwipeRefreshLayoutActivity extends AppCompatActivity {

    public static final String[] TITLES =
            {
                    "Henry IV (1)",
                    "Henry V",
                    "Henry VIII",
                    "Richard II",
                    "Richard III",
                    "Merchant of Venice",
                    "Othello",
                    "King Lear",
                    "Henry IV (1)",
                    "Henry V",
                    "Henry VIII",
                    "Richard II",
                    "Richard III",
                    "Merchant of Venice",
                    "Othello",
                    "King Lear",
                    "Henry IV (1)",
                    "Henry V",
                    "Henry VIII",
                    "Richard II",
                    "Richard III",
                    "Merchant of Venice",
                    "Othello",
                    "King Lear",
                    "Henry IV (1)",
                    "Henry V",
                    "Henry VIII",
                    "Richard II",
                    "Richard III",
                    "Merchant of Venice",
                    "Othello",
                    "King Lear"
            };


    private SwipeRefreshLayout mSwipeRefreshLayout;
    private ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swipe_refresh_layout);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.srl);
        mListView = (ListView) findViewById(R.id.content);

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                }, 3000);
            }
        });

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,
                android.R.id.text1, TITLES);
        mListView.setAdapter(adapter);

    }


}
