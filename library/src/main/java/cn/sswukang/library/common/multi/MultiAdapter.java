package cn.sswukang.library.common.multi;

import android.support.annotation.LayoutRes;
import android.view.View;

import java.util.List;

import cn.sswukang.library.common.base.BaseAdapter;
import cn.sswukang.library.common.base.BaseViewHolder;

/**
 * multi Item Adapter。
 *
 * @author sswukang on 2017/2/17 11:05
 * @version 1.0
 */
public abstract class MultiAdapter<T> extends BaseAdapter<T, BaseViewHolder> {

    /**
     * @param data 数据
     */
    public MultiAdapter(List<T> data) {
        super(-1, data);
    }

    /**
     * 利用getItemViewType传递layout id
     *
     * @param position 当前行数
     * @return layout id
     */
    @LayoutRes
    @Override
    public int getItemViewType(int position) {
        return getItemLayoutId(getItem(position), position);
    }

    @Override
    public final void convert(T t, BaseViewHolder holder) {
        convert(t, holder, holder.getLayoutId());
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
     * 实现该抽象方法，得到单个item的layout id。
     *
     * @param t        每个 position 对应的封装
     * @param position 当前行数
     * @return layout id
     */
    public abstract int getItemLayoutId(T t, int position);

    /**
     * 实现该抽象方法，完成数据的填充。
     *
     * @param t        每个 position 对应的封装
     * @param holder   {@link BaseViewHolder}
     * @param layoutId 布局id (用于区别不同item)
     */
    public abstract void convert(T t, BaseViewHolder holder, @LayoutRes int layoutId);

    /**
     * item的单击事件
     *
     * @param itemView 触发点击事件的View
     * @param t        每个 position 对应的封装
     * @param layoutId 布局id (用于区别不同item)
     */
    public void onItemClick(View itemView, T t, @LayoutRes int layoutId) {
        // do something...
    }

    /**
     * item的长按事件
     *
     * @param itemView 触发点击事件的View
     * @param t        每个 position 对应的封装
     * @param layoutId 布局id (用于区别不同item)
     * @return 长按事件是否被消费
     */
    public boolean onItemLongClick(View itemView, T t, @LayoutRes int layoutId) {
        return false;
    }
}
