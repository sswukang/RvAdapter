package cn.sswukang.library.base;

import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;

import java.util.List;

/**
 * 增量更新基础回调
 *
 * @author sswukang on 2016/10/28 15:20
 * @version 1.0
 */
public abstract class BaseDiffCallback<T> extends DiffUtil.Callback {
    private List<T> oldList, newList;

    public BaseDiffCallback(List<T> oldList, List<T> newList) {
        this.oldList = oldList;
        this.newList = newList;
    }

    @Override
    public final int getOldListSize() {
        return oldList == null ? 0 : oldList.size();
    }

    @Override
    public final int getNewListSize() {
        return newList == null ? 0 : newList.size();
    }

    @Override
    public final boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return areItemsTheSame(oldList.get(oldItemPosition), newList.get(newItemPosition));
    }

    @Override
    public final boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return areContentsTheSame(oldList.get(oldItemPosition), newList.get(newItemPosition));
    }

    @Nullable
    @Override
    public final Object getChangePayload(int oldItemPosition, int newItemPosition) {
        return getChangePayload(oldList.get(oldItemPosition), newList.get(newItemPosition));
    }

    public abstract boolean areItemsTheSame(T oldItem, T newItem);

    public abstract boolean areContentsTheSame(T oldItem, T newItem);

    @Nullable
    public abstract Object getChangePayload(T oldItem, T newItem);
}
