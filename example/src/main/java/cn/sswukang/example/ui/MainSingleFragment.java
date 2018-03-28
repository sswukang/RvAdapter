package cn.sswukang.example.ui;


import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import cn.sswukang.example.R;
import cn.sswukang.example.manager.CountryManager;
import cn.sswukang.example.model.Country;
import cn.sswukang.library.common.base.BaseViewHolder;
import cn.sswukang.library.common.single.SingleAdapter;

/**
 * Single Fragment
 *
 * @author sswukang on 2017/2/22 16:29
 * @version 1.0
 */
public class MainSingleFragment extends RvFragment {

    @BindView(R.id.common_rv)
    RecyclerView commonRv;

    private SingleAdapter<Country> adapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_main_rv;
    }

    @Override
    protected void initView() {
        adapter = new SingleAdapter<Country>(R.layout.rv_single_item,
                CountryManager.getInstance().getCountryList()) {
            @Override
            public void convert(int position, Country country, BaseViewHolder holder) {
                holder.setText(R.id.single_item_name, country.getCountryNameCn());
                holder.setText(R.id.single_item_code, "+" + country.getCountryCode());
            }

            @Override
            public void onItemClick(View itemView, int position, Country country) {
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
        commonRv.setAdapter(adapter);
    }

    @Override
    public void asc() {
        // Country 排序
        List<Country> sort = CountryManager.getInstance().getCountryList();
        Collections.sort(sort, CountryManager.getInstance().comparatorCodeAcs());
        // 刷新
        adapter.setData(sort);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void desc() {
        // Country 排序
        List<Country> sort = CountryManager.getInstance().getCountryList();
        Collections.sort(sort, CountryManager.getInstance().comparatorCodeAcs());
        Collections.reverse(sort);
        // 刷新
        adapter.setData(sort);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void shuffle() {
        // Country 乱序
        List<Country> shuffle = CountryManager.getInstance().getCountryList();
        Collections.shuffle(shuffle);
        // 刷新
        adapter.setData(shuffle);
        adapter.notifyDataSetChanged();
    }
}
