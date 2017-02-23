package cn.sswukang.example.base;

import android.databinding.ViewDataBinding;

/**
 * ViewModel Activity 基类
 *
 * @author sswukang on 2017/2/22 15:55
 * @version 1.0
 */
public class BaseViewModelActivity<B extends ViewDataBinding> {

    private B mDataBinding;

    @SuppressWarnings("unchecked")
    public void setView(BaseActivity activity) {
        mDataBinding = (B) activity.getDataBinding();
    }

    public B getDataBinding() {
        return mDataBinding;
    }

    public void onCreate() {
        // do something...
    }

    public void onRestart() {
        // do something...
    }

    public void onStart() {
        // do something...
    }

    public void onResume() {
        // do something...
    }

    public void onPause() {
        // do something...
    }

    public void onStop() {
        // do something...
    }

    public void onDestroy() {
        // do something...
    }

}
