package cn.sswukang.library.bind.sticky;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

import cn.sswukang.library.bind.base.BaseBindViewHolder;
import cn.sswukang.library.bind.single.SingleBindAdapter;
import cn.sswukang.library.lib.sticky_header.sticky.StickyRecyclerHeadersAdapter;


/**
 * 粘性头部适配器。(DataBinding模式)
 *
 * @author sswukang on 2017/2/23 18:43
 * @version 1.0
 */
public abstract class StickyHeaderBindAdapter<T, B extends ViewDataBinding> extends SingleBindAdapter<T, B>
        implements StickyRecyclerHeadersAdapter<BaseBindViewHolder<B>> {

    // sticky header res id;
    @LayoutRes
    private int headerLayoutId;
    // sticky header layout height;
    private int headerHeight;

    /**
     * @param headerLayoutId header需要的布局资源id
     * @param layoutId       content需要的布局资源id
     * @param data           数据
     */
    public StickyHeaderBindAdapter(@LayoutRes int headerLayoutId, @LayoutRes int layoutId, List<T> data) {
        super(layoutId, data);
        this.headerLayoutId = headerLayoutId;
    }

    @Override
    public final long getHeaderId(int position) {
        return getHeaderId(getItem(position), position);
    }

    @SuppressWarnings("unchecked")
    @Override
    public final BaseBindViewHolder<B> onCreateHeaderViewHolder(ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        B binding = DataBindingUtil.inflate(inflater, headerLayoutId, parent, false);
        this.headerHeight = binding.getRoot().getLayoutParams().height;
        return BaseBindViewHolder.get(binding, headerLayoutId, this);
    }

    @Override
    public final void onBindHeaderViewHolder(BaseBindViewHolder<B> holder, int position) {
        convertHeader(holder, getItem(position), position);
    }

    /**
     * 开放粘性头部高度，方便 recycler view 滚动。
     *
     * @return sticky header height
     */
    public int getHeaderHeight() {
        return headerHeight;
    }

    /**
     * 获得 header id 。如果某几个条目有相同的header，其id 需相同。
     * 如某条目不需要header，则return < 0 即可。
     * 例：字符串可以用 String.charAt(0)
     *
     * @param t        每个 position 对应的封装
     * @param position 当前行数，采用{@link BaseBindViewHolder<B>#getLayoutPosition()}
     * @return header id {@link StickyRecyclerHeadersAdapter#getHeaderId(int)}
     */
    public abstract long getHeaderId(T t, int position);

    /**
     * 填充粘性头部显示的内容
     *
     * @param holder   {@link BaseBindViewHolder<B>}
     * @param t        相同header id 的第一个条目封装 {@link #getHeaderId(Object, int)}
     * @param position 相同header id 的第一个条目的position  {@link #getHeaderId(Object, int)}
     */
    public abstract void convertHeader(BaseBindViewHolder<B> holder, T t, int position);
}
