package cn.sswukang.library.common.multi;

import android.support.annotation.LayoutRes;

/**
 * 多布局支持接口
 *
 * @author sswukang on 2016/2/15 14:51
 * @version 1.0
 */
public interface MultiItemTypeSupport<T> {
    @LayoutRes
    int getLayoutId(int viewType);

    int getItemViewType(int position, T t);
}
