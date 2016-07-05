package com.lll.learn.recycleView;

import android.graphics.Canvas;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.lll.learn.Cheeses;
import com.lll.learn.ConfigToggle;
import com.lll.learn.ConfigViewHolder;
import com.lll.learn.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Version 1.0
 * Created by lll on 16/7/4.
 * Description RecycleView的item操作
 * copyright generalray4239@gmail.com
 */
public abstract class ItemTouchHelperActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;

    /*item触摸工具类*/
    public ItemTouchHelper mItemTouchHelper;

    /*触摸回调*/
    public ItemTouchHelper.Callback mCallback;


    public ItemTouchAdapter mAdapter;

    /**/
    private ConfigToggle[] mConfigToggles;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_touch);
        initView();
//        mAdapter.
    }

    private void initView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mAdapter = createAdapter();
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        //item操作,
        mItemTouchHelper = createItemTouchHelper();
        mItemTouchHelper.attachToRecyclerView(mRecyclerView);
        initToggles();
    }

    /*创建adapter*/
    private ItemTouchAdapter createAdapter() {
        return new ItemTouchAdapter();
    }

    /*创建touchHelper*/
    public ItemTouchHelper createItemTouchHelper() {
        mCallback = createCallBack();
        return new ItemTouchHelper(mCallback);
    }

    /**
     * 测试按钮
     */
    private void initToggles(){
        mConfigToggles = createConfigToggles();
        RecyclerView configView = (RecyclerView) findViewById(R.id.config_recycler_view);

        configView.setAdapter(new RecyclerView.Adapter<ConfigViewHolder>() {

            @Override
            public ConfigViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                return new ConfigViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.config_view_toggle,null));
            }

            @Override
            public void onBindViewHolder(ConfigViewHolder holder, int position) {
                ConfigToggle toggle = mConfigToggles[position];
                holder.bind(toggle);
            }

            @Override
            public int getItemCount() {
                return mConfigToggles.length;
            }
        });
        configView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        configView.setHasFixedSize(true);

    }

    /**
     * 创建控制器
     * @return
     */
    abstract ConfigToggle[] createConfigToggles();

    /**
     * 创建callBack
     * 核心思想就是将方法封装暴露出去,给子类实现
     *
     * @return
     */
    private ItemTouchHelper.Callback createCallBack() {
        return new ItemTouchHelper.Callback() {
            /**
             * 设置上下拖动或者左右滑动方向:
             *     ({@link LEFT}, {@link RIGHT},
             *     {@link #START}, {@link #END},
             *     {@link #UP}, {@link #DOWN})
             * @param recyclerView
             * @param viewHolder
             * @return
             */
            @Override
            public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                return ItemTouchHelperActivity.this.getMovementFlags(recyclerView, viewHolder);
            }


            /**
             * 拖动的逻辑,底层调用adapter的notifyItemMoved(from, to);方法实现拖动
             *
             * @param recyclerView
             * @param viewHolder  拖动的holder:viewHolder.getAdapterPosition()获取在adapter的实际位置
             * @param target      目标的holder
             * @return
             */
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                //viewHolder.getAdapterPosition() 获取holder的实际位置
                mAdapter.move(viewHolder.getAdapterPosition(), target.getAdapterPosition());
                return true;
            }

            /**
             * 滑动操作,这里我们在滑动里实现删除逻辑
             * @param viewHolder
             * @param direction
             */
            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                mAdapter.delete(viewHolder.getAdapterPosition());
            }

            /**
             * item选中的逻辑。可以实现选中的时候动画操作,例如放大,例如更改背景颜色等等
             * @param viewHolder
             * @param actionState
             */
            @Override
            public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
                super.onSelectedChanged(viewHolder, actionState);
                ItemTouchHelperActivity.this.onSelectedChanged(viewHolder,actionState);
            }

            /**
             * 绘制child,实现滑动或者拖动的时候的动画效果
             * @param c
             * @param recyclerView
             * @param viewHolder
             * @param dX
             * @param dY
             * @param actionState
             * @param isCurrentlyActive
             */
            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                if(ItemTouchHelperActivity.this.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)){
                    return;
                }
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }

            @Override
            public void onChildDrawOver(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                if(ItemTouchHelperActivity.this.onChildDrawOver(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)){
                    return;
                }
                super.onChildDrawOver(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }

            @Override
            public boolean isLongPressDragEnabled() {
                return ItemTouchHelperActivity.this.isLongPressDragEnabled();
            }

            @Override
            public boolean isItemViewSwipeEnabled() {
                return ItemTouchHelperActivity.this.isPointerSwipeEnabled();
            }

            @Override
            public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                super.clearView(recyclerView, viewHolder);
                ItemTouchHelperActivity.this.clearView(viewHolder);
            }
        };
    }

    /**
     * 交给子类实现
     *
     * @param recyclerView
     * @param viewHolder
     * @return
     */
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        return 0;
    }

    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {

    }

    public boolean isLongPressDragEnabled(){
        return false;
    }

    public boolean isPointerSwipeEnabled() {
        return false;
    }

    public void clearView(RecyclerView.ViewHolder viewHolder){

    }

    /**
     * 绘制孩子
     * @param c
     * @param recyclerView
     * @param viewHolder
     * @param dX
     * @param dY
     * @param actionState
     * @param isCurrentlyActive
     */
    public boolean onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
         return false;
    }

    /**
     *
     * @param c
     * @param recyclerView
     * @param viewHolder
     * @param dX
     * @param dY
     * @param actionState
     * @param isCurrentlyActive
     * @return
     */
    public boolean onChildDrawOver(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        return false;
    }


    /**
     * touch holder
     */
    public class ItemTouchViewHolder extends RecyclerView.ViewHolder {
        public final TextView textView;

        public final Button actionButton;

        public final CardView cardView;

        public final View overlay;

        public ItemTouchViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView;
            textView = (TextView) itemView.findViewById(R.id.text_view);
            actionButton = (Button) itemView.findViewById(R.id.action_button);
            overlay = itemView.findViewById(R.id.overlay);
        }
    }

    /**
     * adapter
     */
    public class ItemTouchAdapter extends RecyclerView.Adapter<ItemTouchViewHolder> {

        private List<String> datas = new ArrayList<>();

        public ItemTouchAdapter() {
            datas.addAll(Arrays.asList(Cheeses.sCheeseStrings));
        }


        @Override
        public ItemTouchViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
           return ItemTouchHelperActivity.this.onCreateViewHolder(parent);
        }

        @Override
        public void onBindViewHolder(ItemTouchViewHolder holder, int position) {
            holder.textView.setText(datas.get(position));
            onBind(holder);
        }

        @Override
        public int getItemCount() {
            return datas.size();
        }

        /**
         * 定义一个删除方法
         */
        public void delete(int position) {
            datas.remove(position);
            //提示item删除
            notifyItemRemoved(position);
        }

        /**
         * 调用adapter的移动数据方法
         *
         * @param from
         * @param to
         */
        public void move(int from, int to) {
            String pre = datas.remove(from);
            //添加数据
            datas.add(to > from ? to - 1 : to, pre);
            notifyItemMoved(from, to);
        }
    }

    /**
     * 留给子类处理
     * @param viewHolder
     */
    public void onBind(ItemTouchViewHolder viewHolder) {

    }

    public ItemTouchViewHolder onCreateViewHolder(ViewGroup parent) {
        ItemTouchViewHolder itemTouchViewHolder = new ItemTouchViewHolder(
                LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.touch_item, parent, false));
        return itemTouchViewHolder;
    }
}
