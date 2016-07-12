package com.lll.common.viewpager.banner;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.lll.common.viewpager.R;
import com.lll.common.viewpager.banner.listener.BannerPageChangeListener;

import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Handler;

/**
 * Version 1.0
 * Created by lll on 16/7/5.
 * Description 广告banner的界面
 * copyright generalray4239@gmail.com
 */
public class BannerView<T> extends LinearLayout {

    private List<T> mDatas;
    private int[] page_indicatorID;
    /*pagerIndex,后期优化成一个单独的View处理*/
    private ArrayList<ImageView> mPointViews = new ArrayList<>();

    private BannerPageChangeListener mBannerPageChangeListener;

    private ViewPager.OnPageChangeListener mViewPageChangeListener;

    private BannerPagerAdapter mBannerPageAdapter;

    private BannerViewPager mBannerViewPager;

    private BannerScroller mBannerScroller;

    private ViewGroup loPageTurningPoint;

    private PagerSwitchTask mPagerSwitchTask;

    private long autoTurningTime;
    private boolean turning;
    private boolean canTurn ;
    private boolean manualPageable = true;
    private boolean canLoop;


    public enum PageIndicatorAlign{
        ALIGN_PARENT_LEFT,ALIGN_PARENT_RIGHT,CENTER_HORIZONTAL
    }





    /**
     * pager切换的task
     */
    private static class PagerSwitchTask implements Runnable{

        private final WeakReference<BannerView> weakBanner;

        public PagerSwitchTask(BannerView view){
            weakBanner = new WeakReference<BannerView>(view);
        }

        @Override
        public void run() {
            BannerView banner = weakBanner.get();
            if(banner != null){
                if(banner.mBannerViewPager !=null){
                    int page = banner.mBannerViewPager.getCurrentItem() + 1;
                    banner.mBannerViewPager.setCurrentItem(page);
                    //调用View的postDelayed
                    banner.postDelayed(banner.mPagerSwitchTask, banner.autoTurningTime);
                }
            }
        }
    }

    public BannerView(Context context) {
        super(context);
        initView(context);
    }

    public BannerView(Context context, AttributeSet attrs) {
        super(context, attrs,0);
    }

    public BannerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.obtainStyledAttributes(attrs,R.styleable.bannerView);
        canLoop = a.getBoolean(R.styleable.bannerView_canLoop,true);
        a.recycle();
        initView(context);
    }

    private void initView(Context ctx){
        View hView = LayoutInflater.from(ctx).inflate(R.layout.layout_banner_view,null);
        mBannerViewPager = (BannerViewPager) hView.findViewById(R.id.cbLoopViewPager);
        loPageTurningPoint = (ViewGroup) hView.findViewById(R.id.loPageTurningPoint);
        initViewPagerScroll();
        mPagerSwitchTask = new PagerSwitchTask(this);
    }

    /**
     * 通过反射设置ViewPager的滑动速度
     */
    private void initViewPagerScroll(){
        try {
            Field mScroller = null;
            mScroller = ViewPager.class.getDeclaredField("mScroller");
            mScroller.setAccessible(true);
            mBannerScroller = new BannerScroller(
                    mBannerViewPager.getContext());
            mScroller.set(mBannerPageAdapter, mBannerScroller);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public boolean isManualPageable() {
        return mBannerViewPager.isCanScroll();
    }

    public void setManualPageable(boolean manualPageable) {
        mBannerViewPager.setCanScroll(manualPageable);
    }


}
