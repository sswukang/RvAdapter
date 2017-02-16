package cn.sswukang.library.base;

import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * RecyclerView基础单Item Adapter。
 *
 * @author sswukang on 2016/2/15 14:30
 * @version 1.0
 */
public abstract class BaseSingleAdapter<T, H extends BaseViewHolder> extends RecyclerView.Adapter<H>
        implements BaseViewHolder.RecyclerClickListener {

    @LayoutRes
    private int layoutId;
    private List<T> data;

    /**
     * @param layoutId adapter需要的布局资源id
     * @param data     数据
     */
    public BaseSingleAdapter(@LayoutRes int layoutId, List<T> data) {
        this.layoutId = layoutId;
        this.data = data;
        setHasStableIds(true);
    }

    // 获得layoutId
    protected int getLayoutId() {
        return layoutId;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    /**
     * @param position item下标
     * @return 获得item数据封装
     */
    protected T getItem(int position) {
        List<T> data = getData();
        if (data != null && data.size() > position)
            return data.get(position);
        return null;
    }

    /**
     * @return 数据总数
     */
    @Override
    public int getItemCount() {
        List<T> data = getData();
        if (data != null)
            return data.size();
        return 0;
    }

    // 设置ID，保证item操作不错乱
    @Override
    public long getItemId(int position) {
        T t = getItem(position);
        if (t != null)
            return t.hashCode();
        else
            return super.getItemId(position);
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    // 创建hold
    @SuppressWarnings("unchecked")
    @Override
    public H onCreateViewHolder(ViewGroup parent, int viewType) {
        return (H) H.get(LayoutInflater.from(parent.getContext())
                .inflate(getLayoutId(), parent, false), getLayoutId(), viewType, this);
    }

    // 绑定hold
    @Override
    public void onBindViewHolder(H holder, int position) {
        convert(holder, getItem(position), position);
    }

    /**
     * 实现该抽象方法，完成数据的填充。
     *
     * @param holder   {@link H}
     * @param t        每个 position 对应的封装
     * @param position 当前行数，采用{@link H#getLayoutPosition()}
     */
    public abstract void convert(H holder, T t, int position);

    /**
     * 单击事件
     *
     * @param itemView 点击的item {@link H#itemView}
     * @param position 当前行数，采用{@link H#getLayoutPosition()}
     * @param viewType itemView的类型{@link #getItemViewType(int)}
     */
    @Override
    public void onItemClick(View itemView, int position, int viewType) {
    }

    /**
     * 长按事件
     *
     * @param itemView 点击的item {@link H#itemView}
     * @param position 当前行数，采用{@link H#getLayoutPosition()}
     * @param viewType itemView的类型{@link #getItemViewType(int)}
     * @return 是否消费事件
     */
    @Override
    public boolean onItemLongClick(View itemView, int position, int viewType) {
        return false;
    }
}
