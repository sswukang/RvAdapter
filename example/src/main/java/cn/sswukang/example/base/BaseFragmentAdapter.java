package cn.sswukang.example.base;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * FragmentPager 通用适配器
 *
 * @author sswukang on 2016/8/30 11:28
 * @version 1.0
 */
public class BaseFragmentAdapter<T extends BaseFragment> extends FragmentPagerAdapter {
    private List<T> fragList; // 碎片集合
    private List<CharSequence> fragTags; // 碎片tag集合

    public BaseFragmentAdapter(FragmentManager fm, List<T> fragList) {
        super(fm);
        this.fragList = fragList;
    }

    public BaseFragmentAdapter(FragmentManager fm, List<T> fragList, List<CharSequence> fragTags) {
        super(fm);
        this.fragList = fragList;
        this.fragTags = fragTags;

        if (fragList.size() != fragTags.size())
            throw new IllegalArgumentException("Fragment list size must be the same with tag list size.");
    }

    @Override
    public T getItem(int position) {
        return fragList.get(position);
    }

    @Override
    public int getCount() {
        return fragList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (fragTags == null)
            return "";
        return fragTags.get(position);
    }

}
