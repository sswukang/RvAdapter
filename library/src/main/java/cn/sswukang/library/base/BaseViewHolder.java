package cn.sswukang.library.base;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.StringRes;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * 自定义 RecyclerView 的 ViewHolder
 *
 * @author sswukang on 2016/2/15 16:01
 * @version 1.0
 */
public class BaseViewHolder extends RecyclerView.ViewHolder
        implements View.OnClickListener, View.OnLongClickListener {

    private final SparseArray<View> views;
    @LayoutRes
    private int layoutId;
    private int viewType;
    private RecyclerClickListener listener;

    protected BaseViewHolder(View root, @LayoutRes int layoutId, int viewType,
                             RecyclerClickListener listener) {
        super(root);
        this.views = new SparseArray<>();
        this.layoutId = layoutId;
        this.viewType = viewType;
        this.listener = listener;

        //添加监听事件
        itemView.setOnClickListener(this);
        itemView.setOnLongClickListener(this);
    }

    /**
     * 自定义ViewHolder创建方法
     *
     * @param root     每一个条目的根view。{@link RecyclerView.ViewHolder#itemView}
     * @param layoutId 该条目的layout id，常用于多条目的区分
     * @param viewType 该条目的类型，常用于多条目的区分
     * @param listener 条目的监听
     * @return {@link BaseViewHolder}
     */
    public static BaseViewHolder get(View root, @LayoutRes int layoutId, int viewType,
                                     RecyclerClickListener listener) {
        return new BaseViewHolder(root, layoutId, viewType, listener);
    }

    /**
     * 得到view
     *
     * @param viewId view在当前layout里设置的id
     * @param <T>    view的子类型
     * @return view的子类型实例
     */
    @SuppressWarnings("unchecked")
    public <T extends View> T getView(@IdRes int viewId) {
        View view = views.get(viewId);
        if (view == null) {
            view = itemView.findViewById(viewId);
            // 为TextView设置字体
//            if (view instanceof TextView) {
//                ((TextView) view).setTypeface(TTFUtil.tf_2nd);
//            }
            views.put(viewId, view);
        }
        return (T) view;
    }

    @Override
    public void onClick(View v) {
        listener.onItemClick(v, getLayoutPosition(), getViewType());
    }

    @Override
    public boolean onLongClick(View v) {
        return listener.onItemLongClick(v, getLayoutPosition(), getViewType());
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
     * 获得item类型（可用于multi adapter里区别不同item）
     *
     * @return item view type
     */
    public int getViewType() {
        return viewType;
    }

    /**
     * Set the visibility state of this view.
     *
     * @param visibility One of {@link View#VISIBLE}, {@link View#INVISIBLE}, or {@link View#GONE}.
     */
    public void setVisibility(@IdRes int viewId, int visibility) {
        View view = getView(viewId);
        view.setVisibility(visibility);
    }

    /**
     * Set the background to a given resource. The resource should refer to
     * a Drawable object or 0 to remove the background.
     *
     * @param resId The identifier of the resource.
     */
    public void setBackgroundResource(@IdRes int viewId, @DrawableRes int resId) {
        View view = getView(viewId);
        view.setBackgroundResource(resId);
    }

    /**
     * {@link TextView#setGravity(int)} (int)}
     */
    public void setTextGravity(@IdRes int viewId, int gravity) {
        TextView tv = getView(viewId);
        tv.setGravity(gravity);
    }

    /**
     * 设置TextViewImage，方向个数必须和res个数相同
     *
     * @param viewId  View ID
     * @param gravity {@link Gravity#START}|{@link Gravity#TOP}|{@link Gravity#END}|{@link Gravity#BOTTOM}
     * @param resId   资源ID
     */
    public void setTextImage(@IdRes int viewId, int gravity, @DrawableRes int... resId) {
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
        TextView tv = getView(viewId);
        tv.setCompoundDrawables(setDrawables[0], setDrawables[1], setDrawables[2], setDrawables[3]);
    }

    /**
     * {@link TextView#setText(int)}
     */
    public void setText(@IdRes int viewId, @StringRes int resId) {
        TextView tv = getView(viewId);
        tv.setText(resId);
    }

    /**
     * {@link TextView#setText(CharSequence)}
     */
    public void setText(@IdRes int viewId, CharSequence text) {
        TextView tv = getView(viewId);
        tv.setText(text);
    }

    /**
     * {@link TextView#setTextColor(int)}
     */
    public void setTextColor(@IdRes int viewId, @ColorInt int color) {
        TextView tv = getView(viewId);
        tv.setTextColor(color);
    }

    /**
     * {@link TextView#setTextColor(int)} and {@link ContextCompat#getColor(Context, int)}
     */
    public void setTextColorRes(@IdRes int viewId, @ColorRes int resId) {
        TextView tv = getView(viewId);
        tv.setTextColor(ContextCompat.getColor(getContext(), resId));
    }

    /**
     * {@link ImageView#setImageResource(int)}
     */
    public void setImageResource(@IdRes int viewId, @DrawableRes int resId) {
        ImageView view = getView(viewId);
        view.setImageResource(resId);
    }

    /**
     * RecyclerView Item 添加监听接口
     */
    protected interface RecyclerClickListener {
        /**
         * item 单击事件
         */
        void onItemClick(View v, int position, int viewType);

        /**
         * item 长按事件
         */
        boolean onItemLongClick(View v, int position, int viewType);
    }
}
