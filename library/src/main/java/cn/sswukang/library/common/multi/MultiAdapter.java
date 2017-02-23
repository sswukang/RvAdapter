package cn.sswukang.library.common.multi;

import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import cn.sswukang.library.common.base.BaseAdapter;
import cn.sswukang.library.common.base.BaseViewHolder;

/**
 * multi Item Adapter。
 *
 * @author sswukang on 2017/2/17 11:05
 * @version 1.0
 */
public abstract class MultiAdapter<T, H extends BaseViewHolder> extends BaseAdapter<T, H> {

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

    // 创建hold
    @SuppressWarnings("unchecked")
    @Override
    public H onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View root = inflater.inflate(viewType, parent, false);
        return (H) H.get(root, viewType, this);
    }

    @Override
    public final void convert(H holder, T t) {
        convert(holder.getLayoutId(), holder, t);
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
     * @param layoutId 布局id
     * @param holder   {@link H}
     * @param t        每个 position 对应的封装
     */
    public abstract void convert(@LayoutRes int layoutId, H holder, T t);

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
