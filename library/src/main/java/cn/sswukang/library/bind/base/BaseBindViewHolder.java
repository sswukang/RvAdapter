package cn.sswukang.library.bind.base;

import android.content.Context;
import android.databinding.ViewDataBinding;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.LayoutRes;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;


/**
 * 自定义 RecyclerView 的 ViewHolder (DataBinding模式)
 *
 * @author sswukang on 2017/2/23 18:08
 * @version 1.0
 */
public class BaseBindViewHolder<B extends ViewDataBinding> extends RecyclerView.ViewHolder
        implements View.OnClickListener, View.OnLongClickListener {

    private B binding;
    @LayoutRes
    private int layoutId;
    private RecyclerClickListener listener;

    protected BaseBindViewHolder(B binding, @LayoutRes int layoutId, RecyclerClickListener listener) {
        super(binding.getRoot());
        this.binding = binding;
        this.layoutId = layoutId;
        this.listener = listener;

        //添加监听事件
        itemView.setOnClickListener(this);
        itemView.setOnLongClickListener(this);
    }

    /**
     * 自定义ViewHolder创建方法
     *
     * @param binding  {@link B}
     * @param layoutId 该条目的layout id，可用于多条目的区分
     * @param listener 条目的监听
     * @return {@link BaseBindViewHolder}
     */
    @SuppressWarnings("unchecked")
    public static <B extends ViewDataBinding> BaseBindViewHolder get(
            B binding, @LayoutRes int layoutId, RecyclerClickListener listener) {
        return new BaseBindViewHolder(binding, layoutId, listener);
    }

    /**
     * @return {@link B}
     */
    public B getBinding() {
        return binding;
    }

    @Override
    public void onClick(View v) {
        listener.onItemClick(v, getLayoutPosition(), getLayoutId());
    }

    @Override
    public boolean onLongClick(View v) {
        return listener.onItemLongClick(v, getLayoutPosition(), getLayoutId());
    }

    /**
     * 获得context，建议布局里使用用此方法得到context。
     *
     * @return {@link Context}
     */
    public Context getContext() {
        return itemView.getContext();
    }

    /**
     * 获得item布局资源id（可用于multi adapter里区别不同item）
     *
     * @return item view res id
     */
    @LayoutRes
    public int getLayoutId() {
        return layoutId;
    }

    /**
     * 设置TextViewImage，方向个数必须和res个数相同
     *
     * @param tv      {@link TextView}
     * @param gravity {@link Gravity#START}|{@link Gravity#TOP}|{@link Gravity#END}|{@link Gravity#BOTTOM}
     * @param resId   资源ID
     */
    public void setTextImage(TextView tv, int gravity, @DrawableRes int... resId) {
        int index = 0;

        // 初始化Drawable
        int length = resId.length;
        Drawable[] initDrawables = new Drawable[length];
        for (int i = 0; i < length; i++) {
            Drawable drawable = ContextCompat.getDrawable(getContext(), resId[i]);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            initDrawables[i] = drawable;
        }

        // 设置Drawable
        Drawable[] setDrawables = new Drawable[4];
        if ((gravity & Gravity.START) == Gravity.START) {
            setDrawables[0] = initDrawables[index++];
        }
        if ((gravity & Gravity.TOP) == Gravity.TOP) {
            setDrawables[1] = initDrawables[index++];
        }
        if ((gravity & Gravity.END) == Gravity.END) {
            setDrawables[2] = initDrawables[index++];
        }
        if ((gravity & Gravity.BOTTOM) == Gravity.BOTTOM) {
            setDrawables[3] = initDrawables[index];
        }
        tv.setCompoundDrawables(setDrawables[0], setDrawables[1], setDrawables[2], setDrawables[3]);
    }

    /**
     * RecyclerView Item 添加监听接口
     */
    protected interface RecyclerClickListener {
        /**
         * item 单击事件
         */
        void onItemClick(View v, int position, @LayoutRes int layoutId);

        /**
         * item 长按事件
         */
        boolean onItemLongClick(View v, int position, @LayoutRes int layoutId);
    }
}
