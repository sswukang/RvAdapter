package cn.sswukang.example.ui;


import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import butterknife.BindView;
import cn.sswukang.example.R;
import cn.sswukang.example.base.BaseFragment;
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
public class MainSingleFragment extends BaseFragment<MainActivity> {

    @BindView(R.id.single_recycler)
    RecyclerView singleRecycler;

    private SingleAdapter<Country> singleAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_main_single;
    }

    @Override
    public void initView() {
        singleAdapter = new SingleAdapter<Country>(R.layout.rv_single_item,
                CountryManager.getInstance().getCountryList()) {
            @Override
            public void convert(Country country, BaseViewHolder holder) {
                holder.setText(R.id.single_item_name, country.getCountryNameCn());
                holder.setText(R.id.single_item_code, "+" + country.getCountryCode());
            }

            @Override
            public void onItemClick(View itemView, Country country) {
                Snackbar.make(itemView, country.toString(), Snackbar.LENGTH_LONG)
                        .addCallback(new Snackbar.Callback() {
                            @Override
                            public void onDismissed(Snackbar transientBottomBar, int event) {
                                getCreatorActivity().setTopToolbarText(country.getCountryNameCn(),
                                        country.getCountryNameEn());
                            }
                        }).show();
            }
        };
        singleRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        singleRecycler.setAdapter(singleAdapter);
    }
}
