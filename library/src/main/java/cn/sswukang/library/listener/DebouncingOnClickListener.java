package cn.sswukang.library.listener;

import android.view.View;

/**
 * 单击防抖动
 *
 * @author wukang
 */
public abstract class DebouncingOnClickListener implements View.OnClickListener {
    private static boolean enabled = true;

    private static final Runnable ENABLE_AGAIN = () -> enabled = true;

    @Override
    public final void onClick(View v) {
        if (enabled) {
            enabled = false;
            v.post(ENABLE_AGAIN);
            doClick(v);
        }
    }

    public abstract void doClick(View v);
}
