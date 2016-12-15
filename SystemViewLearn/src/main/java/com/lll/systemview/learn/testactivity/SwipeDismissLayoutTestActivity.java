package com.lll.systemview.learn.testactivity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;

import com.lll.systemview.learn.R;
import com.lll.systemview.learn.ui.SwipeDismissLayout;

import static android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN;
import static android.view.WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS;

public class SwipeDismissLayoutTestActivity extends AppCompatActivity {

    private SwipeDismissLayout mSwipeDismissLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swipe_dismiss_layout_test);
        initSwipeDismissLayout();
    }

    private void initSwipeDismissLayout() {
        mSwipeDismissLayout = (SwipeDismissLayout) findViewById(R.id.activity_swipe_dismiss_layout_test);
        mSwipeDismissLayout.setOnSwipeStateChangeListener(new SwipeDismissLayout.OnSwipeStateChangeListener() {
            private static final float ALPHA_DECREASE = 0.5f;
            private boolean mIsTranslucent = false;

            @Override
            public void onSwipeStart(SwipeDismissLayout layout) {
                Log.e("lll", "-----------+onSwipeStart");
            }

            @Override
            public void onSwipeProgressChanged(SwipeDismissLayout layout, float progress, float translate) {
                WindowManager.LayoutParams newParams = getWindow().getAttributes();
                newParams.x = (int) translate;
                newParams.alpha = 1 - (progress * ALPHA_DECREASE);
                getWindow().setAttributes(newParams);
                int flags = 0;
                if (newParams.x == 0) {
                    flags = FLAG_FULLSCREEN;
                } else {
                    flags = FLAG_LAYOUT_NO_LIMITS;
                }
                getWindow().setFlags(flags, FLAG_FULLSCREEN | FLAG_LAYOUT_NO_LIMITS);
            }

            @Override
            public void onSwipeFinished(SwipeDismissLayout layout) {
                finish();
            }

            @Override
            public void onSwipeCancelled(SwipeDismissLayout layout) {
                WindowManager.LayoutParams newParams = getWindow().getAttributes();
                newParams.x = 0;
                newParams.alpha = 1;
                getWindow().setAttributes(newParams);
                getWindow().setFlags(FLAG_FULLSCREEN, FLAG_FULLSCREEN | FLAG_LAYOUT_NO_LIMITS);
            }
        });
    }
}
