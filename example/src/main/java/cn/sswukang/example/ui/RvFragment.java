package cn.sswukang.example.ui;

import cn.sswukang.example.base.BaseFragment;

/**
 * show RecyclerView Fragment
 *
 * @author sswukang on 2017/2/22 15:16
 * @version 1.0
 */
public abstract class RvFragment extends BaseFragment<MainActivity> {
    public abstract void asc();

    public abstract void desc();

    public abstract void shuffle();

    protected void setToolbarContent(String title, String subtitle) {
        getCreatorActivity().setTopToolbarText(title, subtitle);
    }
}
