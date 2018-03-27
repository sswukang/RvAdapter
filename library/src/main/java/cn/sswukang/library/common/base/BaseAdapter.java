package cn.sswukang.library.common.base;

import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * RecyclerView基础Adapter。
 *
 * @author sswukang on 2017/2/17 9:30
 * @version 1.0
 */
public abstract class BaseAdapter<T, H extends BaseViewHolder> extends RecyclerView.Adapter<H>
        implements BaseViewHolder.RecyclerClickListener {

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
    @NonNull
    @SuppressWarnings("unchecked")
    @Override
    public H onCreateViewHolder(@NonNull ViewGroup parent, @LayoutRes int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View root = inflater.inflate(viewType, parent, false);
        return (H) H.get(root, viewType, this);
    }

    // 绑定hold
    @Override
    public void onBindViewHolder(@NonNull H holder, int position) {
        convert(getItem(position), holder);
    }

    /**
     * 实现该抽象方法，完成数据的填充。
     *
     * @param t      每个 position 对应的封装
     * @param holder {@link H}
     */
    public abstract void convert(T t, H holder);

    /**
     * 单击事件
     *
     * @param itemView 点击的item {@link H#itemView}
     * @param position 当前行数，采用{@link H#getLayoutPosition()}
     * @param layoutId item布局id{@link H#getLayoutId()}
     */
    @Override
    public void onItemClick(View itemView, int position, @LayoutRes int layoutId) {
    }

    /**
     * 长按事件
     *
     * @param itemView 点击的item {@link H#itemView}
     * @param position 当前行数，采用{@link H#getLayoutPosition()}
     * @param layoutId item布局id{@link H#getLayoutId()}
     * @return 是否消费事件
     */
    @Override
    public boolean onItemLongClick(View itemView, int position, @LayoutRes int layoutId) {
        return false;
    }
}
