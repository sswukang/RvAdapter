package cn.sswukang.library.common.single;

import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import cn.sswukang.library.common.base.BaseViewHolder;


/**
 * 拖拽侧滑ViewHolder
 *
 * @author sswukang on 2016/11/24 11:54
 * @version 1.0
 */
public class ItemTouchViewHolder extends BaseViewHolder implements ItemTouchCallBack.OnStateChangedListener {

    private ItemViewStateChangeListener listener;

    private ItemTouchViewHolder(View root, @LayoutRes int layoutId, int viewType,
                                RecyclerClickListener clickListener, ItemViewStateChangeListener listener) {
        super(root, layoutId, viewType, clickListener);
        this.listener = listener;
    }

    /**
     * 创建 ItemTouchViewHolder 的方法
     *
     * @param root          每一个条目的根view。{@link RecyclerView.ViewHolder#itemView}
     * @param layoutId      该条目的layout id，常用于多条目的区分
     * @param viewType      该条目的类型，常用于多条目的区分
     * @param clickListener {@link RecyclerClickListener}
     * @param listener      {@link ItemViewStateChangeListener}
     * @return {@link ItemTouchViewHolder}
     */
    public static ItemTouchViewHolder get(View root, @LayoutRes int layoutId, int viewType,
                                          RecyclerClickListener clickListener,
                                          ItemViewStateChangeListener listener) {
        return new ItemTouchViewHolder(root, layoutId, viewType, clickListener, listener);
    }

    @Override
    public void onItemPressed() {
        if (listener != null) listener.onItemPressed(itemView);
    }

    @Override
    public void onItemClear() {
        if (listener != null) listener.onItemClear(itemView);
    }

    /**
     * 拖拽状态改变
     */
    protected interface ItemViewStateChangeListener {
        void onItemPressed(View itemView);

        void onItemClear(View itemView);
    }
}
