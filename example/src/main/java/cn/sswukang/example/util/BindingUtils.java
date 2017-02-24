package cn.sswukang.example.util;

import android.databinding.BindingAdapter;
import android.support.v7.widget.RecyclerView;

/**
 * data binding 工具类
 *
 * @author sswukang on 2017/2/24 17:56
 * @version 1.0
 */
public class BindingUtils {

    @BindingAdapter("layoutManager")
    public static void setLayoutManager(RecyclerView view, RecyclerView.LayoutManager manager) {
        view.setLayoutManager(manager);
    }

    @BindingAdapter("adapter")
    public static void setAdapter(RecyclerView view, RecyclerView.Adapter adapter) {
        view.setAdapter(adapter);
    }

}
