package cn.sswukang.library.bind.itemtouch;

import android.databinding.ViewDataBinding;
import android.support.annotation.LayoutRes;
import android.view.View;

import cn.sswukang.library.bind.base.BaseBindViewHolder;
import cn.sswukang.library.common.itemtouch.ItemTouchCallBack;


/**
 * 拖拽侧滑ViewHolder。(DataBinding模式)
 *
 * @author sswukang on 2017/2/24 10:03
 * @version 1.0
 */
public class ItemTouchBindViewHolder<B extends ViewDataBinding>
        extends BaseBindViewHolder<B> implements ItemTouchCallBack.OnStateChangedListener {

    private ItemViewStateChangeListener listener;

    private ItemTouchBindViewHolder(B binding, @LayoutRes int layoutId, RecyclerClickListener clickListener,
                                    ItemViewStateChangeListener listener) {
        super(binding, layoutId, clickListener);
        this.listener = listener;
    }

    /**
     * 创建 ItemTouchViewHolder 的方法
     *
     * @param binding       {@link B}
     * @param layoutId      该条目的layout id，常用于多条目的区分
     * @param clickListener {@link RecyclerClickListener}
     * @param listener      {@link ItemViewStateChangeListener}
     * @return {@link ItemTouchBindViewHolder}
     */
    @SuppressWarnings("unchecked")
    public static <B extends ViewDataBinding> ItemTouchBindViewHolder get(
            B binding, @LayoutRes int layoutId, RecyclerClickListener clickListener,
            ItemViewStateChangeListener listener) {
        return new ItemTouchBindViewHolder(binding, layoutId, clickListener, listener);
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
