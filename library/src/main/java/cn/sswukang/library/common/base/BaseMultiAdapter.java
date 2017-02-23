package cn.sswukang.library.common.base;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

import cn.sswukang.library.common.multi.MultiItemTypeSupport;


/**
 * RecyclerView基础多Item Adapter。
 *
 * @author sswukang on 2016/2/15 14:50
 * @version 1.0
 */
public abstract class BaseMultiAdapter<T, H extends BaseViewHolder> extends BaseSingleAdapter<T, H> {

    private MultiItemTypeSupport<T> multiItemTypeSupport;

    /**
     * @param data                 数据
     * @param multiItemTypeSupport 多布局支持接口
     */
    public BaseMultiAdapter(List<T> data, MultiItemTypeSupport<T> multiItemTypeSupport) {
        super(-1, data);
        this.multiItemTypeSupport = multiItemTypeSupport;

        if (multiItemTypeSupport == null)
            throw new IllegalArgumentException("the MultiItemTypeSupport<T> can not be null.");
    }

    // 根据item类型分配布局类型
    @Override
    public int getItemViewType(int position) {
        return multiItemTypeSupport.getItemViewType(position, getItem(position));
    }

    // 创建hold
    @SuppressWarnings("unchecked")
    @Override
    public H onCreateViewHolder(ViewGroup parent, int viewType) {
        int layoutId = multiItemTypeSupport.getLayoutId(viewType);
        return (H) H.get(LayoutInflater.from(parent.getContext()).inflate(layoutId, parent, false),
                layoutId, viewType, this);
    }

    @Override
    public final void convert(H holder, T t, int position) {
        convert(holder.getViewType(), holder, t, position);
    }

    /**
     * 实现该抽象方法，完成数据的填充。
     *
     * @param viewType itemView的类型
     * @param holder   {@link H}
     * @param t        每个 position 对应的封装
     * @param position 当前行数，采用{@link H#getLayoutPosition()}
     */
    public abstract void convert(int viewType, H holder, T t, int position);
}
