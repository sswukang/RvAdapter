package cn.sswukang.library.common.single;

import android.support.annotation.LayoutRes;
import android.view.View;

import java.util.List;

import cn.sswukang.library.common.base.BaseAdapter;
import cn.sswukang.library.common.base.BaseViewHolder;

/**
 * single item Adapter
 *
 * @author sswukang on 2017/2/17 10:33
 * @version 1.0
 */
public abstract class SingleAdapter<T> extends BaseAdapter<T, BaseViewHolder> {
    /**
     * @param layoutId adapter需要的布局资源id
     * @param data     数据
     */
    protected SingleAdapter(@LayoutRes int layoutId, List<T> data) {
        super(layoutId, data);
    }

    @Override
    public final void onItemClick(View itemView, int position, @LayoutRes int layoutId) {
        onItemClick(itemView, position, getDataItem(position));
    }

    @Override
    public final boolean onItemLongClick(View itemView, int position, @LayoutRes int layoutId) {
        return onItemLongClick(itemView, position, getDataItem(position));
    }

    /**
     * item的单击事件
     *
     * @param itemView 点击的item {@link BaseViewHolder#itemView}
     * @param position 当前点击的position，采用{@link BaseViewHolder#getLayoutPosition()}（无限轮播时会超过数据总个数）
     * @param t        position 对应的对象（无限轮播时为对数据总个数取余后对应的对象）
     */
    public void onItemClick(View itemView, int position, T t) {
        // do something...
    }

    /**
     * item的长按事件
     *
     * @param itemView 点击的item {@link BaseViewHolder#itemView}
     * @param position 当前点击的position，采用{@link BaseViewHolder#getLayoutPosition()}（无限轮播时会超过数据总个数）
     * @param t        position 对应的对象（无限轮播时为对数据总个数取余后对应的对象）
     * @return 长按事件是否被消费
     */
    public boolean onItemLongClick(View itemView, int position, T t) {
        return false;
    }
}
