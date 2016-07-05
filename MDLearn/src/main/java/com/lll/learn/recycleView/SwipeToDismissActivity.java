package com.lll.learn.recycleView;

import android.annotation.TargetApi;
import android.graphics.Canvas;
import android.os.Build;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.lll.learn.ConfigToggle;
import com.lll.learn.R;

/**
 * RecycleView的item左右滑动效果
 */
public class SwipeToDismissActivity extends ItemTouchHelperActivity {

    boolean mSwipeStartEnabled = true;
    boolean mSwipeEndEnabled = true;
    boolean mPointerSwipeEnabled = true;
    boolean mCustomSwipeEnabled = false;

    @Override
    ConfigToggle[] createConfigToggles() {
        ConfigToggle[] configToggles = {
                new ConfigToggle(this, R.string.swipe_start) {
                    @Override
                    public boolean isChecked() {
                        return mSwipeStartEnabled;
                    }

                    @Override
                    public void onChange(boolean newValue) {
                        mSwipeStartEnabled = newValue;
                    }
                },
                new ConfigToggle(this, R.string.swipe_end) {
                    @Override
                    public boolean isChecked() {
                        return mSwipeEndEnabled;
                    }

                    @Override
                    public void onChange(boolean newValue) {
                        mSwipeEndEnabled = newValue;
                    }
                },
                new ConfigToggle(this, R.string.pointer_swipe_enabled) {
                    @Override
                    public boolean isChecked() {
                        return mPointerSwipeEnabled;
                    }

                    @Override
                    public void onChange(boolean newValue) {
                        mPointerSwipeEnabled = newValue;
                        mAdapter.notifyDataSetChanged();
                    }
                }
        };


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            ConfigToggle[] copy = new ConfigToggle[configToggles.length + 1];
            System.arraycopy(configToggles, 0, copy, 0, configToggles.length);
            copy[copy.length - 1] = new ConfigToggle(this, R.string.custom_swipe_enabled) {
                @Override
                public boolean isChecked() {
                    return mCustomSwipeEnabled;
                }

                @Override
                public void onChange(boolean newValue) {
                    mCustomSwipeEnabled = newValue;
                }
            };
            return copy;
        } else {
            return configToggles;
        }
    }


    @Override
    public void onBind(ItemTouchViewHolder viewHolder) {
        super.onBind(viewHolder);
        viewHolder.actionButton.setVisibility(mPointerSwipeEnabled ? View.GONE : View.VISIBLE);
    }

    @Override
    public void clearView(RecyclerView.ViewHolder viewHolder) {
        super.clearView(viewHolder);
        ItemTouchViewHolder touchVH = (ItemTouchViewHolder) viewHolder;
        touchVH.cardView.setCardBackgroundColor(getResources().getColor(android.R.color.white));
        touchVH.overlay.setVisibility(View.GONE);
    }

    /**
     * 选中效果
     *
     * @param viewHolder
     * @param actionState
     */
    @Override
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
        ItemTouchViewHolder holder = (ItemTouchViewHolder) viewHolder;
        if (actionState != ItemTouchHelper.ACTION_STATE_IDLE) {
            holder.cardView.setCardBackgroundColor(getResources().getColor(R.color.card_aquatic));
            if (mCustomSwipeEnabled) {
                // hide it
                holder.overlay.setTranslationX(viewHolder.itemView.getWidth());
                holder.overlay.setVisibility(View.VISIBLE);
            }
        }
        super.onSelectedChanged(viewHolder, actionState);
    }

    /**
     * 滑动中动画
     * @param c
     * @param recyclerView
     * @param viewHolder
     * @param dX
     * @param dY
     * @param actionState
     * @param isCurrentlyActive
     * @return
     */
    @Override
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public boolean onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        if (!mCustomSwipeEnabled) {
            return false;
        }
        ItemTouchViewHolder touchVH = (ItemTouchViewHolder) viewHolder;
        final  float dir = Math.signum(dX);
        if(dir==0){//位移动画
            touchVH.overlay.setTranslationX(-touchVH.overlay.getWidth());
        }else{
            final float overlayOffset = dX - dir * viewHolder.itemView.getWidth();
            //滑动删除都是对X的移动
            touchVH.overlay.setTranslationX(overlayOffset);
        }
        float alpha = (float)(.2+.8*Math.abs(dX)/viewHolder.itemView.getWidth());
        touchVH.overlay.setAlpha(alpha);
        return true;
    }


    @Override
    public ItemTouchViewHolder onCreateViewHolder(ViewGroup parent) {
        final ItemTouchViewHolder holder = super.onCreateViewHolder(parent);;
        holder.actionButton.setText(R.string.swipe);
        holder.actionButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (MotionEventCompat.getActionMasked(event) == MotionEvent.ACTION_DOWN) {
                    mItemTouchHelper.startSwipe(holder);
                }
                return false;
            }
        });
        return holder;
    }


    @Override
    public boolean isPointerSwipeEnabled() {
        return mPointerSwipeEnabled;
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        return mCallback.makeMovementFlags(0,
                (mSwipeStartEnabled ? ItemTouchHelper.START : 0) |
                        (mSwipeEndEnabled ? ItemTouchHelper.END : 0));
    }
}
