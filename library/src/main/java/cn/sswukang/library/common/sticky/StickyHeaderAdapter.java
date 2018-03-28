package cn.sswukang.library.common.sticky;

import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import cn.sswukang.library.common.base.BaseViewHolder;
import cn.sswukang.library.common.single.SingleAdapter;
import cn.sswukang.library.lib.sticky_header.sticky.StickyRecyclerHeadersAdapter;


/**
 * 粘性头部适配器。
 *
 * @author sswukang on 2017/2/21 11:03
 * @version 1.0
 */
public abstract class StickyHeaderAdapter<T> extends SingleAdapter<T>
        implements StickyRecyclerHeadersAdapter<BaseViewHolder> {

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
    public StickyHeaderAdapter(@LayoutRes int headerLayoutId, @LayoutRes int layoutId, List<T> data) {
        super(layoutId, data);
        this.headerLayoutId = headerLayoutId;
        this.headerHeight = setHeaderHeight();
    }

    /**
     * @return 设置item总个数（不允许设置无限轮播）
     */
    @Override
    public final int getItemCount() {
        return super.getItemCount();
    }

    @Override
    public final long getHeaderId(int position) {
        return getHeaderId(position, getDataItem(position));
    }

    @Override
    public final BaseViewHolder onCreateHeaderViewHolder(ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View root = inflater.inflate(headerLayoutId, parent, false);
        return BaseViewHolder.get(root, headerLayoutId, this);
    }

    @Override
    public final void onBindHeaderViewHolder(BaseViewHolder holder, int position) {
        convertHeader(position, getDataItem(position), holder);
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
     * 设置粘性头部高度，方便sticky header定位
     *
     * @return sticky header height
     */
    public abstract int setHeaderHeight();

    /**
     * 获得 header id 。如果某几个条目有相同的header，其id 需相同。
     * 如某条目不需要header，则return < 0 即可。
     * 例：字符串可以用 String.charAt(0)
     *
     * @param position 当前item的position
     * @param t        position 对应的对象
     * @return header id {@link StickyRecyclerHeadersAdapter#getHeaderId(int)}
     */
    public abstract long getHeaderId(int position, T t);

    /**
     * 填充粘性头部显示的内容
     *
     * @param position header 条目下标
     * @param t        header 对象数据封装
     * @param holder   {@link BaseViewHolder}
     */
    public abstract void convertHeader(int position, T t, BaseViewHolder holder);
}
