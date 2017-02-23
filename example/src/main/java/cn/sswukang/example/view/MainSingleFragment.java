package cn.sswukang.example.view;


import android.support.v7.widget.LinearLayoutManager;

import cn.sswukang.example.R;
import cn.sswukang.example.base.BaseFragment;
import cn.sswukang.example.databinding.MainSingleFragmentBinding;
import cn.sswukang.example.viewmodel.MainSingelViewModel;

/**
 * Single Fragment
 *
 * @author sswukang on 2017/2/22 16:29
 * @version 1.0
 */
public class MainSingleFragment extends BaseFragment<MainSingleFragmentBinding, MainSingelViewModel, MainActivity> {

    public MainSingleFragment() {
        // Required empty public constructor
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_main_single;
    }

    @Override
    public void initView() {
        // 数据绑定
        getDataBinding().setMainSingle(getViewModel());
        // 初始化列表数据
        getDataBinding().singleRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
//        getDataBinding().singleRecycler.setAdapter();
    }

}
