package cn.sswukang.example.base;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Activity基类
 *
 * @author sswukang on 2017/2/21 10:36
 * @version 1.0
 */
public abstract class BaseActivity<B extends ViewDataBinding, M extends BaseViewModelActivity>
        extends AppCompatActivity {

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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 得到DataBinding
        mDataBinding = DataBindingUtil.setContentView(this, getLayoutId());
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
        mViewModel.onCreate();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        mViewModel.onRestart();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mViewModel.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mViewModel.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mViewModel.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mViewModel.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mViewModel.onDestroy();
    }

    public B getDataBinding() {
        return mDataBinding;
    }

    public M getViewModel() {
        return mViewModel;
    }

    public Context getContext() {
        return this;
    }

    public BaseActivity getActivity() {
        return this;
    }
}
