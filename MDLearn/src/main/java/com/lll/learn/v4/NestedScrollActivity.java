package com.lll.learn.v4;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.lll.learn.R;

/**
 * 测试NestedScrollView
 */
public class NestedScrollActivity extends AppCompatActivity {

    private ListView mListView;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nested_scroll);
        findView();
    }

    private void findView() {
        mListView = (ListView) findViewById(R.id.lv);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, TITLES);
        mListView.setAdapter(arrayAdapter);
    }
}
