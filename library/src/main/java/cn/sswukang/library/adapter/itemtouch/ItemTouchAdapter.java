package cn.sswukang.library.adapter.itemtouch;

import android.graphics.Color;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Collections;
import java.util.List;

import cn.sswukang.library.adapter.base.BaseAdapter;


/**
 * 拖拽侧滑Adapter
 *
 * @author sswukang on 2017/2/20 15:36
 * @version 1.0
 */
public abstract class ItemTouchAdapter<T> extends BaseAdapter<T, ItemTouchViewHolder>
        implements ItemTouchCallBack.OnMoveSwipeListener, ItemTouchViewHolder.ItemViewStateChangeListener {
    /**
     * @param layoutId adapter需要的布局资源id
     * @param data     数据
     */
    protected ItemTouchAdapter(@LayoutRes int layoutId, List<T> data) {
        super(layoutId, data);
    }

    /**
     * 设置item总个数（不允许设置无限轮播）
     */
    @Override
    public final int getItemCount() {
        return super.getItemCount();
    }

    // 创建hold
    @NonNull
    @Override
    public final ItemTouchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return ItemTouchViewHolder.get(LayoutInflater.from(parent.getContext())
                .inflate(viewType, parent, false), viewType, this, this);
    }

    @Override
    public final void onItemClick(View itemView, int position, @LayoutRes int layoutId) {
        onItemClick(itemView, position, getDataItem(position));
    }

    @Override
    public final boolean onItemLongClick(View itemView, int position, @LayoutRes int layoutId) {
        return onItemLongClick(itemView, position, getDataItem(position));
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        //交换数据源位置
        Collections.swap(getData(), fromPosition, toPosition);
        //交换列表中数据位置
        notifyItemMoved(fromPosition, toPosition);
        return true;
    }

    @Override
    public void onItemSwipe(int position) {
        //删除数据源中对应数据
        getData().remove(position);
        //删除列表中对应位置
        notifyItemRemoved(position);
    }

    /**
     * item的单击事件
     *
     * @param itemView 点击的item {@link ItemTouchViewHolder#itemView}
     * @param position 当前item的position
     * @param t        position 对应的对象
     */
    public void onItemClick(View itemView, int position, @Nullable T t) {
        // do something...
    }

    /**
     * item的长按事件
     *
     * @param itemView 点击的item {@link ItemTouchViewHolder#itemView}
     * @param position 当前item的position
     * @param t        position 对应的对象
     * @return 长按事件是否被消费
     */
    public boolean onItemLongClick(View itemView, int position, @Nullable T t) {
        return false;
    }

    public void onItemPressed(View itemView) {
        itemView.setBackgroundColor(Color.GRAY);
    }

    public void onItemClear(View itemView) {
        itemView.setBackgroundColor(Color.TRANSPARENT);
    }
}
