package cn.sswukang.example.base;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;

/**
 * Activity基类
 *
 * @author sswukang on 2017/2/21 10:36
 * @version 1.0
 */
public abstract class BaseActivity extends AppCompatActivity {

    /**
     * @return 设置视图id
     */
    protected abstract int getLayoutId();

    /**
     * 初始化视图
     */
    protected abstract void initView();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        ButterKnife.bind(this);
        initView();
    }


    public Context getContext() {
        return this;
    }

    public BaseActivity getActivity() {
        return this;
    }
}
