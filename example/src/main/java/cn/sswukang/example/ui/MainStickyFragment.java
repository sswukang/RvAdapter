package cn.sswukang.example.ui;


import android.support.design.widget.Snackbar;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import cn.sswukang.example.R;
import cn.sswukang.example.manager.CountryManager;
import cn.sswukang.example.model.Country;
import cn.sswukang.library.common.base.BaseViewHolder;
import cn.sswukang.library.common.sticky.StickyHeaderAdapter;
import cn.sswukang.library.lib.sticky_header.sticky.StickyRecyclerHeadersDecoration;

/**
 * Sticky Fragment
 *
 * @author sswukang on 2017/2/28 16:03
 * @version 1.0
 */
public class MainStickyFragment extends RvFragment {

    @BindView(R.id.common_rv)
    RecyclerView commonRv;

    private StickyHeaderAdapter<Country> adapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_main_rv;
    }

    @Override
    protected void initView() {
        adapter = new StickyHeaderAdapter<Country>(R.layout.rv_sticky_title, R.layout.rv_sticky_content,
                CountryManager.getInstance().getCountryList()) {
            @Override
            public long getHeaderId(Country country, int position) {
                return country.getCountryNameEn().charAt(0);
            }

            @Override
            public void convertHeader(Country country, BaseViewHolder holder, int position) {
                holder.setText(R.id.sticky_title_initials, country.getCountryNameEn().substring(0, 1));
            }

            @Override
            public void convert(Country country, BaseViewHolder holder) {
                holder.setText(R.id.sticky_content_name,
                        country.getCountryNameCn() + "(" + country.getCountryNameEn() + ")");
                holder.setText(R.id.sticky_content_code, "+" + country.getCountryCode());
            }

            @Override
            public void onItemClick(View itemView, Country country) {
                Snackbar.make(itemView, country.toString(), Snackbar.LENGTH_LONG)
                        .addCallback(new Snackbar.Callback() {
                            @Override
                            public void onDismissed(Snackbar transientBottomBar, int event) {
                                setToolbarContent(country.getCountryNameCn(), country.getCountryNameEn());
                            }
                        }).show();
            }
        };
        commonRv.setLayoutManager(new LinearLayoutManager(getContext()));
        commonRv.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        commonRv.addItemDecoration(new StickyRecyclerHeadersDecoration(adapter)); // 必须添加
        commonRv.setAdapter(adapter);
    }

    @Override
    public void asc() {
        // Country 排序
        List<Country> sort = CountryManager.getInstance().getCountryList();
        Collections.sort(sort, CountryManager.getInstance().comparatorNameEnAcs());
        // 刷新
        adapter.setData(sort);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void desc() {
        // Country 排序
        List<Country> sort = CountryManager.getInstance().getCountryList();
        Collections.sort(sort, CountryManager.getInstance().comparatorNameEnAcs());
        Collections.reverse(sort);
        // 刷新
        adapter.setData(sort);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void shuffle() {
        // 首字母乱序
        List<String> initialsShuffle = CountryManager.getInstance().getInitialsList();
        Collections.shuffle(initialsShuffle);
        // Country 乱序
        List<Country> shuffle = new ArrayList<>();
        for (String initials : initialsShuffle) {
            List<Country> initialsList = new ArrayList<>();
            for (Country country : CountryManager.getInstance().getCountryList()) {
                if (country.getCountryNameEn().startsWith(initials)) {
                    initialsList.add(country);
                }
            }
            Collections.sort(initialsList, CountryManager.getInstance().comparatorNameEnAcs());
            shuffle.addAll(initialsList);
        }
        // 刷新
        adapter.setData(shuffle);
        adapter.notifyDataSetChanged();
    }
}
