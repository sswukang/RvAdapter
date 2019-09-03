package cn.sswukang.library.adapter.base;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.IdRes;
import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import cn.sswukang.library.listener.DebouncingOnClickListener;
import cn.sswukang.library.listener.RecyclerClickListener;


/**
 * 自定义 RecyclerView 的 ViewHolder
 *
 * @author wukang on 2017/2/17 9:01
 */
public class BaseViewHolder extends RecyclerView.ViewHolder {
    private final SparseArray<View> views;
    @LayoutRes
    private int layoutId;

    protected BaseViewHolder(View root, @LayoutRes int layoutId, RecyclerClickListener listener) {
        super(root);
        this.views = new SparseArray<>();
        this.layoutId = layoutId;

        //添加监听事件
        itemView.setOnClickListener(new DebouncingOnClickListener() {
            @Override
            public void doClick(View v) {
                listener.onItemClick(v, getLayoutPosition(), getLayoutId());
            }
        });
        itemView.setOnLongClickListener(v -> listener.onItemLongClick(v, getLayoutPosition(), getLayoutId()));
    }

    /**
     * 自定义ViewHolder创建方法
     *
     * @param root     每一个条目的根view。{@link RecyclerView.ViewHolder#itemView}
     * @param layoutId 该条目的layout id，可用于多条目的区分
     * @param listener 条目的监听
     * @return {@link BaseViewHolder}
     */
    public static BaseViewHolder get(View root, @LayoutRes int layoutId, RecyclerClickListener listener) {
        return new BaseViewHolder(root, layoutId, listener);
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
            views.put(viewId, view);
        }
        return (T) view;
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
     * 获得context，建议布局里使用用此方法得到context。
     *
     * @return {@link Context}
     */
    public Context getContext() {
        return itemView.getContext();
    }

    /**
     * @return {@link ContextCompat#getColor(Context, int)}
     */
    @ColorInt
    public int getColor(@ColorRes int resId) {
        return ContextCompat.getColor(getContext(), resId);
    }

    /**
     * @return {@link ContextCompat#getDrawable(Context, int)}
     */
    public Drawable getDrawable(@DrawableRes int resId) {
        return ContextCompat.getDrawable(getContext(), resId);
    }

    /**
     * @return {@link Context#getString(int)}
     */
    public String getString(@StringRes int resId) {
        return getContext().getString(resId);
    }

    /**
     * @return {@link Context#getString(int, Object...)}
     */
    public String getString(@StringRes int resId, Object... formatArgs) {
        return getContext().getString(resId, formatArgs);
    }

    /**
     * {@link View#setOnClickListener(View.OnClickListener)}
     */
    public void setOnClickListener(@IdRes int viewId, View.OnClickListener listener) {
        View view = getView(viewId);
        view.setOnClickListener(new DebouncingOnClickListener() {
            @Override
            public void doClick(View v) {
                listener.onClick(v);
            }
        });
    }

    /**
     * {@link View#setOnLongClickListener(View.OnLongClickListener)}
     */
    public void setOnLongClickListener(@IdRes int viewId, View.OnLongClickListener listener) {
        View view = getView(viewId);
        view.setOnLongClickListener(listener);
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
     * @param gravity 多个方向可组合使用 {@link Gravity#START}|{@link Gravity#TOP}|{@link Gravity#END}|{@link Gravity#BOTTOM}
     * @param resId   资源ID
     */
    public void setTextImage(@IdRes int viewId, int gravity, @DrawableRes int... resId) {
        if (resId == null)
            return;

        // 初始化Drawable
        int length = resId.length;
        Drawable[] initDrawables = new Drawable[length];
        for (int i = 0; i < length; i++) {
            Drawable drawable = ContextCompat.getDrawable(getContext(), resId[i]);
            if (drawable != null)
                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            initDrawables[i] = drawable;
        }

        // 设置Drawable
        Drawable[] setDrawables = new Drawable[4];
        try {
            if ((gravity & Gravity.BOTTOM) == Gravity.BOTTOM) {
                setDrawables[3] = initDrawables[--length];
            }
            if ((gravity & Gravity.END) == Gravity.END) {
                setDrawables[2] = initDrawables[--length];
            }
            if ((gravity & Gravity.TOP) == Gravity.TOP) {
                setDrawables[1] = initDrawables[--length];
            }
            if ((gravity & Gravity.START) == Gravity.START) {
                setDrawables[0] = initDrawables[--length];
            }
        } catch (Exception e) {
            throw new IllegalArgumentException("方向设置个数必须与资源id个数一致.");
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
     * {@link ImageView#setImageDrawable(Drawable)}
     */
    public void setImageDrawable(@IdRes int viewId, @Nullable Drawable drawable) {
        ImageView view = getView(viewId);
        view.setImageDrawable(drawable);
    }

    /**
     * {@link ImageView#setImageDrawable(Drawable)}
     */
    public void setImageColor(@IdRes int viewId, @ColorRes int resId) {
        ImageView view = getView(viewId);
        view.setImageDrawable(new ColorDrawable(ContextCompat.getColor(getContext(), resId)));
    }


    /* 可自行扩展View及其子类的方法... */
}
