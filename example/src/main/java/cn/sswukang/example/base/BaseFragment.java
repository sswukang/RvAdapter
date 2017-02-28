package cn.sswukang.example.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;

/**
 * Fragment基类
 *
 * @author sswukang on 2017/2/22 15:30
 * @version 1.0
 */
public abstract class BaseFragment<T extends BaseActivity> extends Fragment {
    private View view;

    /**
     * @return 设置视图id
     */
    protected abstract int getLayoutId();

    /**
     * 初始化视图
     */
    protected abstract void initView();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(getLayoutId(), container, false);
        }
        ViewGroup parent = (ViewGroup) view.getParent();
        if (parent != null) {
            parent.removeView(view);
        }
        return view;
    }

    @SuppressWarnings("unchecked")
    protected T getCreatorActivity() {
        return (T) getActivity();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        ButterKnife.bind(this, view);
        initView();
    }
}
