package cn.sswukang.example.ui;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.snackbar.Snackbar;
import androidx.recyclerview.widget.DividerItemDecoration;
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
import cn.sswukang.library.adapter.sticky.StickyHeaderAdapter;
import cn.sswukang.library.lib.sticky_header.sticky.StickyRecyclerHeadersDecoration;

/**
 * Sticky Fragment
 *
 * @author sswukang on 2017/2/28 16:03
 * @version 1.0
 */
public class MainStickyFragment extends MainFragment {

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
            public int setHeaderHeight() {
                return getResources().getDimensionPixelSize(R.dimen.main_sticky_header_height);
            }

            @Override
            public long getHeaderId(int position, @Nullable Country country) {
                if (country != null) {
                    return country.getCountryNameEn().charAt(0);
                } else {
                    return getItemId(position);
                }
            }

            @Override
            public void convertHeader(int position, @Nullable Country country, BaseViewHolder holder) {
                if (country != null) {
                    holder.setText(R.id.sticky_title_initials, country.getCountryNameEn().substring(0, 1));
                }
            }

            @Override
            public void convert(int position, @Nullable Country country, @NonNull BaseViewHolder holder) {
                if (country != null) {
                    holder.setText(R.id.sticky_content_name,
                            country.getCountryNameCn() + "(" + country.getCountryNameEn() + ")");
                    holder.setText(R.id.sticky_content_code, "+" + country.getCountryCode());
                }
            }

            @Override
            public void onItemClick(View itemView, int position, @Nullable Country country) {
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
        commonRv.setLayoutManager(new LinearLayoutManager(getCreatorActivity()));
        commonRv.addItemDecoration(new DividerItemDecoration(getCreatorActivity(), DividerItemDecoration.VERTICAL));
        commonRv.addItemDecoration(new StickyRecyclerHeadersDecoration<>(adapter)); // 必须添加
        commonRv.setAdapter(adapter);
    }

    @Override
    public void asc() {
        // Country 正序
        List<Country> sort = CountryManager.getInstance().getCountryList();
        Collections.sort(sort, CountryManager.getInstance().comparatorNameEnAcs());
        // 刷新
        adapter.setData(sort);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void desc() {
        // Country 倒序
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
        // 循环乱序首字母
        for (String initials : initialsShuffle) {
            List<Country> initialsList = new ArrayList<>();
            for (Country country : CountryManager.getInstance().getCountryList()) {
                if (country.getCountryNameEn().startsWith(initials)) {
                    // 找到首字母与该字母相同的元素，并添加
                    initialsList.add(country);
                }
            }
            // 该字母所有元素排序
            Collections.sort(initialsList, CountryManager.getInstance().comparatorNameEnAcs());
            shuffle.addAll(initialsList);
        }
        // 刷新
        adapter.setData(shuffle);
        adapter.notifyDataSetChanged();
    }
}
