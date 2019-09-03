package cn.sswukang.library.listener;

import androidx.annotation.LayoutRes;
import android.view.View;

/**
 * RecyclerView Item 添加监听接口
 *
 * @author wukang
 */
public interface RecyclerClickListener {
    /**
     * item 单击事件
     */
    void onItemClick(View v, int position, @LayoutRes int layoutId);

    /**
     * item 长按事件
     */
    boolean onItemLongClick(View v, int position, @LayoutRes int layoutId);
}
