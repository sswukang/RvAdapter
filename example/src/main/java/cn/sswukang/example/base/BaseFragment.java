package cn.sswukang.example.base;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Fragment基类
 *
 * @author sswukang on 2017/2/22 15:30
 * @version 1.0
 */
public abstract class BaseFragment<B extends ViewDataBinding, M extends BaseViewModelFragment, T extends BaseActivity>
        extends Fragment {

    // 视图绑定对象
    private B mDataBinding;
    // ViewModel模型对象
    private M mViewModel;

    /**
     * @return 设置视图id
     */
    public abstract int getLayoutId();

    /**
     * 初始化视图
     */
    public abstract void initView();

    @SuppressWarnings("unchecked")
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // 得到DataBinding
        mDataBinding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false);
        // 得到ViewModel
        Type genType = getClass().getGenericSuperclass();
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
        Class<M> bizClass = (Class) params[1];
        try {
            mViewModel = bizClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 初始化视图
        initView();
        // 初始化ViewModel
        mViewModel.setView(this);
        return mDataBinding.getRoot();
    }

    public B getDataBinding() {
        return mDataBinding;
    }

    public M getViewModel() {
        return mViewModel;
    }

    @SuppressWarnings("unchecked")
    protected T getCreatorActivity() {
        return (T) getActivity();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mViewModel.onDestroyView();
    }
}
