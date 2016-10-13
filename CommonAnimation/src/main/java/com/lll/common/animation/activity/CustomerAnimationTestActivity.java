package com.lll.common.animation.activity;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.lll.common.animation.R;
import com.lll.common.animation.timely.TimelyView;

import java.security.InvalidParameterException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.LogRecord;

public class CustomerAnimationTestActivity extends AppCompatActivity {

    TimelyView timelyView11, timelyView12, timelyView13, timelyView14, timelyView15;

    TextView hourColon;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            updateView();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_animation_test);
        findView();
    }

    private void findView() {
        timelyView11 = (TimelyView) findViewById(R.id.timelyView11);
        timelyView12 = (TimelyView) findViewById(R.id.timelyView12);
        timelyView13 = (TimelyView) findViewById(R.id.timelyView13);
        timelyView14 = (TimelyView) findViewById(R.id.timelyView14);
        timelyView15 = (TimelyView) findViewById(R.id.timelyView15);
        hourColon = (TextView) findViewById(R.id.hour_colon);
        updateTimer();
    }


    private void updateTimer() {
        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                mHandler.sendEmptyMessage(1);
            }
        }, 300, 300);
    }

    private void updateView() {
        String time = makeShortTimeString(this, System.currentTimeMillis() / 100000);
        if (time.length() < 5) {
            timelyView11.setVisibility(View.GONE);
            timelyView12.setVisibility(View.GONE);
            hourColon.setVisibility(View.GONE);

            changeDigit(timelyView13, time.charAt(0) - '0');
            changeDigit(timelyView14, time.charAt(2) - '0');
            changeDigit(timelyView15, time.charAt(3) - '0');

        } else if (time.length() == 5) {
            timelyView12.setVisibility(View.VISIBLE);
            changeDigit(timelyView12, time.charAt(0) - '0');
            changeDigit(timelyView13, time.charAt(1) - '0');
            changeDigit(timelyView14, time.charAt(3) - '0');
            changeDigit(timelyView15, time.charAt(4) - '0');
        } else {
            timelyView11.setVisibility(View.VISIBLE);
            hourColon.setVisibility(View.VISIBLE);
            changeDigit(timelyView11, time.charAt(0) - '0');
            changeDigit(timelyView12, time.charAt(2) - '0');
            changeDigit(timelyView13, time.charAt(3) - '0');
            changeDigit(timelyView14, time.charAt(5) - '0');
            changeDigit(timelyView15, time.charAt(6) - '0');
        }
    }


    public void changeDigit(TimelyView tv, int end) {
        ObjectAnimator obja = tv.animator(end);
        obja.setDuration(400);
        obja.start();
    }

    public void changeDigit(TimelyView tv, int start, int end) {
        try {
            ObjectAnimator obja = tv.animator(start, end);
            obja.setDuration(400);
            obja.start();
        } catch (InvalidParameterException e) {
            e.printStackTrace();
        }
    }

    /**
     * 格式化时间
     *
     * @param context
     * @param secs
     * @return
     */
    public static final String makeShortTimeString(final Context context, long secs) {
        long hours, mins;

        hours = secs / 3600;
        secs %= 3600;
        mins = secs / 60;
        secs %= 60;

        final String durationFormat = context.getResources().getString(
                hours == 0 ? R.string.durationformatshort : R.string.durationformatlong);
        return String.format(durationFormat, hours, mins, secs);
    }
}
