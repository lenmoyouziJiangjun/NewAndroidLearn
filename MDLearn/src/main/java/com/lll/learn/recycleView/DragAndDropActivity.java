package com.lll.learn.recycleView;

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
 *
 */
public class DragAndDropActivity extends ItemTouchHelperActivity {

    /*定义三个状态*/
    boolean mDragUpEnabled = true;
    boolean mDragDownEnabled = true;
    boolean mLongPressDragEnabled = true;

    @Override
    ConfigToggle[] createConfigToggles() {
        return new ConfigToggle[]{
                new ConfigToggle(this,R.string.drag_up) {
                    @Override
                    public boolean isChecked() {
                        return mDragUpEnabled;
                    }

                    @Override
                    public void onChange(boolean newValue) {
                        mDragUpEnabled = newValue;
                    }
                },
                new ConfigToggle(this,R.string.drag_down) {
                    @Override
                    public boolean isChecked() {
                        return mDragDownEnabled;
                    }

                    @Override
                    public void onChange(boolean newValue) {
                       mDragDownEnabled = newValue;
                    }
                },
                new ConfigToggle(this,R.string.long_press_drag) {
                    @Override
                    public boolean isChecked() {
                        return mLongPressDragEnabled;
                    }

                    @Override
                    public void onChange(boolean newValue) {
                        mLongPressDragEnabled = newValue;
                        mAdapter.notifyDataSetChanged();
                    }
                }
        };
    }

    @Override
    public boolean isLongPressDragEnabled() {
        return mLongPressDragEnabled;
    }

    @Override
    public void onBind(ItemTouchHelperActivity.ItemTouchViewHolder viewHolder) {
        super.onBind(viewHolder);
        viewHolder.actionButton.setVisibility(mLongPressDragEnabled ? View.GONE : View.VISIBLE);
    }

    @Override
    public void clearView(RecyclerView.ViewHolder viewHolder) {
        super.clearView(viewHolder);
        ItemTouchViewHolder touchVH = (ItemTouchViewHolder) viewHolder;
        touchVH.cardView.setCardBackgroundColor(getResources().getColor(android.R.color.white));
        touchVH.overlay.setVisibility(View.GONE);
    }

    @Override
    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
        ItemTouchViewHolder holder = (ItemTouchViewHolder)viewHolder;
        if (actionState != ItemTouchHelper.ACTION_STATE_IDLE) {
            holder.cardView.setCardBackgroundColor(getResources().getColor(R.color.card_aquatic));
        }
        super.onSelectedChanged(viewHolder, actionState);
    }

    @Override
    public ItemTouchViewHolder onCreateViewHolder(ViewGroup parent) {
        final ItemTouchViewHolder vh = super.onCreateViewHolder(parent);
        vh.actionButton.setText("drag me");
        vh.actionButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (MotionEventCompat.getActionMasked(event) == MotionEvent.ACTION_DOWN) {
                    mItemTouchHelper.startDrag(vh);
                }
                return false;
            }
        });
        return vh;
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        return mCallback.makeMovementFlags(
                (mDragUpEnabled ? ItemTouchHelper.UP : 0) |
                        (mDragDownEnabled ? ItemTouchHelper.DOWN : 0), 0);
    }
}
