package cn.sswukang.library.adapter.base;

import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import cn.sswukang.library.listener.RecyclerClickListener;

/**
 * RecyclerView基础Adapter
 *
 * @author sswukang on 2017/2/17 9:30
 * @version 1.0
 */
public abstract class BaseAdapter<T, H extends BaseViewHolder> extends RecyclerView.Adapter<H>
        implements RecyclerClickListener {

    @LayoutRes
    private int layoutId;
    private List<T> data;

    /**
     * @param layoutId adapter需要的布局资源id
     * @param data     数据
     */
    public BaseAdapter(@LayoutRes int layoutId, List<T> data) {
        this.layoutId = layoutId;
        this.data = data;
        setHasStableIds(true);
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    /**
     * @return 获得item数据总个数
     */
    public final int getDataSize() {
        List<T> data = getData();
        if (data != null)
            return data.size();
        return 0;
    }

    /**
     * @param position item下标
     * @return 获得item数据封装
     */
    @Nullable
    protected final T getDataItem(int position) {
        List<T> data = getData();
        if (data != null && data.size() > 0) {
            if (position >= data.size()) {
                position = position % data.size();
            }
            return data.get(position);
        }
        return null;
    }

    // 设置ID，保证item操作不错乱
    @Override
    public long getItemId(int position) {
        T t = getDataItem(position);
        if (t != null)
            return t.hashCode();
        else
            return super.getItemId(position);
    }

    /**
     * @return 设置item总个数（一般为数据总个数，设置成{@link Integer#MAX_VALUE}可实现无限轮播）
     */
    @Override
    public int getItemCount() {
        return getDataSize();
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
        return layoutId;
    }

    // 创建hold
    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public H onCreateViewHolder(@NonNull ViewGroup parent, @LayoutRes int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View root = inflater.inflate(viewType, parent, false);
        return (H) H.get(root, viewType, this);
    }

    // 绑定hold
    @Override
    public void onBindViewHolder(@NonNull H holder, int position) {
        convert(position, getDataItem(position), holder);
    }

    /**
     * 实现该抽象方法，完成数据的填充。
     *
     * @param position 当前item的position（无限轮播时会超过数据总个数）
     * @param t        position 对应的对象（无限轮播时为对数据总个数取余后对应的对象）
     * @param holder   {@link H}
     */
    public abstract void convert(int position, @Nullable T t, @NonNull H holder);

    /**
     * 单击事件
     *
     * @param itemView 点击的item {@link H#itemView}
     * @param position 当前点击的position，采用{@link H#getLayoutPosition()}（无限轮播时会超过数据总个数）
     * @param layoutId item布局id{@link H#getLayoutId()}
     */
    @Override
    public void onItemClick(View itemView, int position, @LayoutRes int layoutId) {
    }

    /**
     * 长按事件
     *
     * @param itemView 点击的item {@link H#itemView}
     * @param position 当前点击的position，采用{@link H#getLayoutPosition()}（无限轮播时会超过数据总个数）
     * @param layoutId item布局id{@link H#getLayoutId()}
     * @return 是否消费事件
     */
    @Override
    public boolean onItemLongClick(View itemView, int position, @LayoutRes int layoutId) {
        return false;
    }
}
