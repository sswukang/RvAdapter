package cn.sswukang.library.common.single;

import android.support.annotation.LayoutRes;
import android.view.View;

import java.util.List;

import cn.sswukang.library.common.base.BaseSingleAdapter;
import cn.sswukang.library.common.base.BaseViewHolder;


/**
 * 单item通用Adapter
 *
 * @author sswukang on 2016/10/10 9:33
 * @version 1.0
 */
public abstract class CommonSingleAdapter<T> extends BaseSingleAdapter<T, BaseViewHolder> {
    /**
     * @param layoutId adapter需要的布局资源id
     * @param data     数据
     */
    protected CommonSingleAdapter(@LayoutRes int layoutId, List<T> data) {
        super(layoutId, data);
    }

    @Override
    public final void onItemClick(View itemView, int position, int viewType) {
        onItemClick(itemView, getItem(position), position);
    }

    @Override
    public final boolean onItemLongClick(View itemView, int position, int viewType) {
        return onItemLongClick(itemView, getItem(position), position);
    }

    /**
     * item的单击事件
     *
     * @param itemView 点击的item {@link BaseViewHolder#itemView}
     * @param t        每个 position 对应的封装
     * @param position 当前行数，采用{@link BaseViewHolder#getLayoutPosition()}
     */
    public void onItemClick(View itemView, T t, int position) {
        // do something...
    }

    /**
     * item的长按事件
     *
     * @param itemView 点击的item {@link BaseViewHolder#itemView}
     * @param t        每个 position 对应的封装
     * @param position 当前行数，采用{@link BaseViewHolder#getLayoutPosition()}
     * @return 长按事件是否被消费
     */
    public boolean onItemLongClick(View itemView, T t, int position) {
        return false;
    }
}
