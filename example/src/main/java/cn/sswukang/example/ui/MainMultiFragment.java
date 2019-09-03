package cn.sswukang.example.ui;


import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import com.google.android.material.snackbar.Snackbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import cn.sswukang.example.R;
import cn.sswukang.example.manager.CountryManager;
import cn.sswukang.example.model.Country;
import cn.sswukang.library.adapter.base.BaseViewHolder;
import cn.sswukang.library.adapter.multi.MultiAdapter;

/**
 * Multi Fragment
 *
 * @author sswukang on 2017/2/28 15:41
 * @version 1.0
 */
public class MainMultiFragment extends MainFragment {

    @BindView(R.id.common_rv)
    RecyclerView commonRv;

    private Country header;
    private MultiAdapter<Country> adapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_main_rv;
    }

    @Override
    protected void initView() {
        header = new Country();
        header.setCountryNameEn("Recycler View Multi Adapter Item.");
        List<Country> list = new ArrayList<>();
        list.add(header);
        list.addAll(CountryManager.getInstance().getCountryList());
        adapter = new MultiAdapter<Country>(list) {
            @Override
            public int getItemLayoutId(int position, @Nullable Country country) {
                return position == 0 ? R.layout.rv_multi_title : R.layout.rv_multi_content;
            }

            @Override
            public void convert(int position, @Nullable Country country, BaseViewHolder holder, @LayoutRes int layoutId) {
                if (country != null) {
                    switch (layoutId) {
                        case R.layout.rv_multi_title:
                            holder.setText(R.id.multi_title_ab, country.getCountryNameEn());
                            break;
                        case R.layout.rv_multi_content:
                            holder.setText(R.id.multi_content_id, String.valueOf(country.getCountryId()));
                            holder.setText(R.id.multi_content_name, country.getCountryNameCn());
                            holder.setText(R.id.multi_content_code, "+" + country.getCountryCode());
                            break;
                    }
                }
            }

            @Override
            public void onItemClick(View itemView, int position, @Nullable Country country, @LayoutRes int layoutId) {
                if (country != null) {
                    Snackbar.make(itemView, country.toString(), Snackbar.LENGTH_LONG)
                            .addCallback(new Snackbar.Callback() {
                                @Override
                                public void onDismissed(Snackbar transientBottomBar, int event) {
                                    setToolbarContent(country.getCountryNameCn(), country.getCountryNameEn());
                                }
                            }).show();
                }
            }
        };
        commonRv.setLayoutManager(new LinearLayoutManager(getContext()));
        commonRv.setAdapter(adapter);
    }

    @Override
    public void asc() {
        // Country 正序
        List<Country> sort = CountryManager.getInstance().getCountryList();
        Collections.sort(sort, CountryManager.getInstance().comparatorIdAcs());
        // 多item数据
        List<Country> list = new ArrayList<>();
        list.add(header);
        list.addAll(sort);
        // 刷新
        adapter.setData(list);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void desc() {
        // Country 倒序
        List<Country> sort = CountryManager.getInstance().getCountryList();
        Collections.sort(sort, CountryManager.getInstance().comparatorIdAcs());
        Collections.reverse(sort);
        // 多item数据
        List<Country> list = new ArrayList<>();
        list.add(header);
        list.addAll(sort);
        // 刷新
        adapter.setData(list);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void shuffle() {
        // Country 乱序
        List<Country> shuffle = CountryManager.getInstance().getCountryList();
        Collections.shuffle(shuffle);
        // 多item数据
        List<Country> list = new ArrayList<>();
        list.add(header);
        list.addAll(shuffle);
        // 刷新
        adapter.setData(list);
        adapter.notifyDataSetChanged();
    }
}
