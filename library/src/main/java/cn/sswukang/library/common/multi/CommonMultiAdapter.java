package cn.sswukang.library.common.multi;

import android.support.annotation.LayoutRes;
import android.view.View;

import java.util.List;

import cn.sswukang.library.common.single.BaseViewHolder;


/**
 * 多item通用adapter
 *
 * @author sswukang on 2016/11/19 17:40
 * @version 1.0
 */
public abstract class CommonMultiAdapter<T> extends BaseMultiAdapter<T, BaseViewHolder> {
    /**
     * @param data 数据
     */
    public CommonMultiAdapter(List<T> data) {
        super(data);
    }

    @Override
    public final void onItemClick(View itemView, int position, @LayoutRes int layoutId) {
        onItemClick(itemView, getItem(position), layoutId);
    }

    @Override
    public final boolean onItemLongClick(View itemView, int position, @LayoutRes int layoutId) {
        return onItemLongClick(itemView, getItem(position), layoutId);
    }

    /**
     * item的单击事件
     *
     * @param itemView 触发点击事件的View
     * @param t        每个 position 对应的封装
     * @param layoutId item布局id{@link BaseViewHolder#getLayoutId()}
     */
    public void onItemClick(View itemView, T t, @LayoutRes int layoutId) {
        // do something...
    }

    /**
     * item的长按事件
     *
     * @param itemView 触发点击事件的View
     * @param t        每个 position 对应的封装
     * @param layoutId item布局id{@link BaseViewHolder#getLayoutId()}
     * @return 长按事件是否被消费
     */
    public boolean onItemLongClick(View itemView, T t, @LayoutRes int layoutId) {
        return false;
    }
}
