package cn.sswukang.example.base;

import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

/**
 * ViewModel Fragment 基类
 *
 * @author sswukang on 2017/2/22 16:05
 * @version 1.0
 */
public class BaseViewModelFragment<B extends ViewDataBinding> {

    private B mDataBinding;

    @SuppressWarnings("unchecked")
    public void setView(BaseFragment fragment) {
        mDataBinding = (B) fragment.getDataBinding();
    }

    public B getDataBinding() {
        return mDataBinding;
    }

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        // do something...
    }

    public void onDestroyView() {
        // do something...
    }

}
