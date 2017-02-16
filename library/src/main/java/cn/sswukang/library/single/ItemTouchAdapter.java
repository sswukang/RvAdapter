package cn.sswukang.library.single;

import android.graphics.Color;
import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Collections;
import java.util.List;

import cn.sswukang.library.base.BaseSingleAdapter;


/**
 * 单item {@link android.support.v7.widget.helper.ItemTouchHelper}实现Adapter
 * example:{
 * ☞ ItemTouchHelper touchHelper = new ItemTouchHelper(new ItemTouchCallBack(adapter));
 * ☞ touchHelper.attachToRecyclerView(recyclerView);
 * }
 *
 * @author sswukang on 2016/11/24 11:36
 * @version 1.0
 */
public abstract class ItemTouchAdapter<T> extends BaseSingleAdapter<T, ItemTouchViewHolder>
        implements ItemTouchCallBack.OnMoveSwipeListener, ItemTouchViewHolder.ItemViewStateChangeListener {
    /**
     * @param layoutId adapter需要的布局资源id
     * @param data     数据
     */
    protected ItemTouchAdapter(@LayoutRes int layoutId, List<T> data) {
        super(layoutId, data);
    }

    @Override
    public final ItemTouchViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return ItemTouchViewHolder.get(LayoutInflater.from(parent.getContext())
                .inflate(getLayoutId(), parent, false), getLayoutId(), viewType, this, this);
    }

    @Override
    public final void onItemClick(View itemView, int position, int viewType) {
        onItemClick(itemView, getItem(position), position);
    }

    @Override
    public final boolean onItemLongClick(View itemView, int position, int viewType) {
        return onItemLongClick(itemView, getItem(position), position);
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
     * @param t        每个 position 对应的封装
     * @param position 当前行数，采用{@link ItemTouchViewHolder#getLayoutPosition()}
     */
    public void onItemClick(View itemView, T t, int position) {
        // do something...
    }

    /**
     * item的长按事件
     *
     * @param itemView 点击的item {@link ItemTouchViewHolder#itemView}
     * @param t        每个 position 对应的封装
     * @param position 当前行数，采用{@link ItemTouchViewHolder#getLayoutPosition()}
     * @return 长按事件是否被消费
     */
    public boolean onItemLongClick(View itemView, T t, int position) {
        return false;
    }

    public void onItemPressed(View itemView) {
        itemView.setBackgroundColor(Color.GRAY);
    }


    public void onItemClear(View itemView) {
        itemView.setBackgroundColor(Color.TRANSPARENT);
    }
}
