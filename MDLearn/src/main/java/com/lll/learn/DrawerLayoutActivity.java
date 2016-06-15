package com.lll.learn;

import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Description: 侧滑菜单示例
 * Version:
 * Created by lll on 2016/5/26.
 * CopyRight lll
 */
public class DrawerLayoutActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private ListView mStartDrawer;
    private FrameLayout mEndDrawer;
    private TextView mContent;

    private ActionBarDrawerToggle mDrawerToggle;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawer_layout);
        initView();
    }

    private void initView() {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mStartDrawer = (ListView) findViewById(R.id.start_drawer);
        mEndDrawer = (FrameLayout) findViewById(R.id.end_drawer);
        mContent = (TextView) findViewById(R.id.content_text);

        //左边侧滑栏
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        //右边侧滑栏
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow_end, GravityCompat.END);

//        mDrawerLayout.
        // The drawer title must be set in order to announce state changes when
        // accessibility is turned on. This is typically a simple description,
        // e.g. "Navigation".
        mDrawerLayout.setDrawerTitle(GravityCompat.START, "开始");

        mStartDrawer.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,
                Shakespeare.TITLES));
        mStartDrawer.setOnItemClickListener(new DrawerItemClickListener());

        // Find the toolbar in our layout and set it as the support action bar on the activity.
        // This is required to have the drawer slide "over" the toolbar.
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle("测试");

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(false);

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close) {

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                Log.e("lll", "---------------open");
            }


            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                Log.e("lll", "---------------close");
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                Log.e("lll", "---------------Slide" + slideOffset);
            }

            @Override
            public void onDrawerStateChanged(int newState) {
                super.onDrawerStateChanged(newState);
            }
        };
        //添加拖动状态监控
        mDrawerLayout.addDrawerListener(mDrawerToggle);

        mDrawerLayout.setStatusBarBackgroundColor(Color.BLUE);

        mDrawerLayout.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                Log.e("lll", "onPreDraw: ");
                // What is the width of the entire DrawerLayout?
                final int drawerLayoutWidth = mDrawerLayout.getWidth();

                // What is the action bar size?
                final Resources.Theme theme = mDrawerLayout.getContext().getTheme();
                final TypedArray a = theme.obtainStyledAttributes(
                        new int[]{android.support.v7.appcompat.R.attr.actionBarSize});
                final int actionBarSize = a.getDimensionPixelSize(0, 0);
                if (a != null) {
                    a.recycle();
                }

                // Compute the width of the drawer and set it on the layout params.
                final int idealDrawerWidth = 5 * actionBarSize;
                final int maxDrawerWidth = Math.max(0, drawerLayoutWidth - actionBarSize);
                final int drawerWidth = Math.min(idealDrawerWidth, maxDrawerWidth);

                final DrawerLayout.LayoutParams startDrawerLp =
                        (DrawerLayout.LayoutParams) mStartDrawer.getLayoutParams();
                startDrawerLp.width = drawerWidth;
                mStartDrawer.setLayoutParams(startDrawerLp);

                final DrawerLayout.LayoutParams endDrawerLp =
                        (DrawerLayout.LayoutParams) mEndDrawer.getLayoutParams();
                endDrawerLp.width = drawerWidth;
                mEndDrawer.setLayoutParams(endDrawerLp);

                // Remove ourselves as the pre-draw listener since this is a one-time
                // configuration.
                mDrawerLayout.getViewTreeObserver().removeOnPreDrawListener(this);
                return true;
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        /*
         * The action bar home/up action should open or close the drawer.
         * The drawer toggle will take care of this.
         */
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        boolean hadOpenDrawer = false;
        // Is the start drawer open?
        if (mDrawerLayout.isDrawerOpen(mStartDrawer)) {
            // Close it
            mDrawerLayout.closeDrawer(mStartDrawer);
            hadOpenDrawer = true;
            mDrawerLayout.openDrawer(mEndDrawer);//打开右边的Drawer
        }
        // Is the end drawer open?
        if (mDrawerLayout.isDrawerOpen(mEndDrawer)) {
            // Close it
            mDrawerLayout.closeDrawer(mEndDrawer);
            hadOpenDrawer = true;
        }

        if (hadOpenDrawer) {
            // If we had one or both drawers open, now that we've closed it / them, return.
            return;
        }

        super.onBackPressed();
    }


    /**
     * This list item click listener implements very simple view switching by changing
     * the primary content text. The drawer is closed when a selection is made.
     */
    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            mContent.setText(Shakespeare.DIALOGUE[position]);
            mToolbar.setTitle(Shakespeare.TITLES[position]);
            mDrawerLayout.closeDrawer(mStartDrawer);
        }
    }
}
