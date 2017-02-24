package cn.sswukang.library.bind.itemtouch;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.graphics.Color;
import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Collections;
import java.util.List;

import cn.sswukang.library.bind.base.BaseBindAdapter;
import cn.sswukang.library.common.itemtouch.ItemTouchCallBack;


/**
 * ItemTouch Adapter Adapter (DataBinding模式)
 *
 * @author sswukang on 2017/2/24 14:16
 * @version 1.0
 */
public abstract class ItemTouchBindAdapter<T, B extends ViewDataBinding>
        extends BaseBindAdapter<T, B, ItemTouchBindViewHolder<B>>
        implements ItemTouchCallBack.OnMoveSwipeListener, ItemTouchBindViewHolder.ItemViewStateChangeListener {
    /**
     * @param layoutId adapter需要的布局资源id
     * @param data     数据
     */
    protected ItemTouchBindAdapter(@LayoutRes int layoutId, List<T> data) {
        super(layoutId, data);
    }

    @SuppressWarnings("unchecked")
    @Override
    public final ItemTouchBindViewHolder<B> onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        B binding = DataBindingUtil.inflate(inflater, viewType, parent, false);
        return ItemTouchBindViewHolder.get(binding, viewType, this, this);
    }

    @Override
    public final void onItemClick(View itemView, int position, @LayoutRes int layoutId) {
        onItemClick(itemView, getItem(position));
    }

    @Override
    public final boolean onItemLongClick(View itemView, int position, @LayoutRes int layoutId) {
        return onItemLongClick(itemView, getItem(position));
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
     * @param itemView 点击的item {@link ItemTouchBindViewHolder#itemView}
     * @param t        每个 position 对应的封装
     */
    public void onItemClick(View itemView, T t) {
        // do something...
    }

    /**
     * item的长按事件
     *
     * @param itemView 点击的item {@link ItemTouchBindViewHolder#itemView}
     * @param t        每个 position 对应的封装
     * @return 长按事件是否被消费
     */
    public boolean onItemLongClick(View itemView, T t) {
        return false;
    }

    public void onItemPressed(View itemView) {
        itemView.setBackgroundColor(Color.GRAY);
    }


    public void onItemClear(View itemView) {
        itemView.setBackgroundColor(Color.TRANSPARENT);
    }
}
