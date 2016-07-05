package com.lll.learn.v7;

import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.lll.common.util.StatusBarUtil;
import com.lll.learn.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * toolbar 显示ViewPager
 */
public class ToolViewPagerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tool_view_pager);
        StatusBarUtil.setTranslucent(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ViewPager vp = (ViewPager) findViewById(R.id.viewpager);
        PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager(),
                new MenuFragment(), new Menu2Fragment());
        vp.setAdapter(new PageAdapter());
    }


    private class PageAdapter extends android.support.v4.view.PagerAdapter{

        Map<Integer,View> imgs = new HashMap();
        @Override
        public int getCount() {
            return 5;
        }


        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            if(imgs.get(position)!=null){
                return imgs.get(position);
            }
            ImageView img = new ImageView(container.getContext());
            switch (position){
                case 0:
                    img.setBackgroundResource(R.drawable.p1);
                    break;
                case 1:
                    img.setBackgroundResource(R.drawable.p2);
                    break;
                case 2:
                    img.setBackgroundResource(R.drawable.p3);
                    break;
                default:
                    img.setBackgroundResource(R.drawable.p4);
                    break;
            }
            container.addView(img);
            imgs.put(position,img);
            img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Snackbar.make(v,"点老子"+position,Snackbar.LENGTH_SHORT).show();
                }
            });
            return img;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(imgs.get(position));
            imgs.remove(position);
//            super.destroyItem(container, position, object);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.actions,menu);
        return true;
    }

    private static class PagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragments;

        public PagerAdapter(FragmentManager fm, Fragment... fragments) {
            super(fm);

            mFragments = new ArrayList<Fragment>();
            for (Fragment fragment : fragments) {
                mFragments.add(fragment);
            }
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }
    }

    /**
     * A fragment that displays a menu.  This fragment happens to not
     * have a UI (it does not implement onCreateView), but it could also
     * have one if it wanted.
     */
    public static class MenuFragment extends Fragment {
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setHasOptionsMenu(true);
        }

        @Override
        public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
            MenuItemCompat.setShowAsAction(menu.add("Menu 1a"), MenuItemCompat.SHOW_AS_ACTION_IF_ROOM);
            MenuItemCompat.setShowAsAction(menu.add("Menu 1b"), MenuItemCompat.SHOW_AS_ACTION_NEVER);
            super.onCreateOptionsMenu(menu, inflater);
        }

        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                                 @Nullable Bundle savedInstanceState) {
            TextView textView = new TextView(container.getContext());

            textView.setText(getClass().getSimpleName());
            textView.setGravity(Gravity.CENTER);
            textView.setLayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

            return textView;
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            if (item.getTitle().equals("Menu 1a")) {
                Toast.makeText(getActivity(), "Selected Menu 1a.", Toast.LENGTH_SHORT).show();
                return true;
            }
            if (item.getTitle().equals("Menu 1b")) {
                Toast.makeText(getActivity(), "Selected Menu 1b.", Toast.LENGTH_SHORT).show();
                return true;
            }
            return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Second fragment with a menu.
     */
    public static class Menu2Fragment extends Fragment {
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setHasOptionsMenu(true);
        }

        @Override
        public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
            MenuItemCompat.setShowAsAction(menu.add("Menu 2"), MenuItemCompat.SHOW_AS_ACTION_IF_ROOM);
        }

        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                                 @Nullable Bundle savedInstanceState) {
            TextView textView = new TextView(container.getContext());

            textView.setText(getClass().getSimpleName());
            textView.setGravity(Gravity.CENTER);
            textView.setLayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

            return textView;
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            if (item.getTitle().equals("Menu 2")) {
                Toast.makeText(getActivity(), "Selected Menu 2.", Toast.LENGTH_SHORT).show();
                return true;
            }
            return false;
        }
    }
}
