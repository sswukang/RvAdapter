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
        onItemClick(itemView, getItem(position));
    }

    @Override
    public final boolean onItemLongClick(View itemView, int position, @LayoutRes int layoutId) {
        return onItemLongClick(itemView, getItem(position));
    }

    /**
     * item的单击事件
     *
     * @param itemView 点击的item {@link BaseViewHolder#itemView}
     * @param t        每个 position 对应的封装
     */
    public void onItemClick(View itemView, T t) {
        // do something...
    }

    /**
     * item的长按事件
     *
     * @param itemView 点击的item {@link BaseViewHolder#itemView}
     * @param t        每个 position 对应的封装
     * @return 长按事件是否被消费
     */
    public boolean onItemLongClick(View itemView, T t) {
        return false;
    }
}
