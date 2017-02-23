package cn.sswukang.library.common.multi;

import android.view.View;

import java.util.List;

import cn.sswukang.library.common.base.BaseMultiAdapter;
import cn.sswukang.library.common.base.BaseViewHolder;


/**
 * 多item通用adapter
 *
 * @author sswukang on 2016/11/19 17:40
 * @version 1.0
 */
public abstract class CommonMultiAdapter<T> extends BaseMultiAdapter<T, BaseViewHolder> {
    /**
     * @param data                 数据
     * @param multiItemTypeSupport 多布局支持接口
     */
    public CommonMultiAdapter(List<T> data, MultiItemTypeSupport<T> multiItemTypeSupport) {
        super(data, multiItemTypeSupport);
    }

    @Override
    public final void onItemClick(View itemView, int position, int viewType) {
        onItemClick(itemView, getItem(position), viewType);
    }

    @Override
    public final boolean onItemLongClick(View itemView, int position, int viewType) {
        return onItemLongClick(itemView, getItem(position), viewType);
    }

    /**
     * item的单击事件
     *
     * @param itemView 触发点击事件的View
     * @param t        每个 position 对应的封装
     * @param viewType item类型{@link BaseViewHolder#getViewType()}
     */
    public void onItemClick(View itemView, T t, int viewType) {
        // do something...
    }

    /**
     * item的长按事件
     *
     * @param itemView 触发点击事件的View
     * @param t        每个 position 对应的封装
     * @param viewType item类型{@link BaseViewHolder#getViewType()}
     * @return 长按事件是否被消费
     */
    public boolean onItemLongClick(View itemView, T t, int viewType) {
        return false;
    }
}
