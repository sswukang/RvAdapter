package cn.sswukang.example.viewmodel;

import android.databinding.ObservableField;

import cn.sswukang.example.base.BaseViewModelActivity;
import cn.sswukang.example.databinding.MainActivityBinding;

/**
 * Main Activity ViewModel
 *
 * @author sswukang on 2017/2/21 18:01
 * @version 1.0
 */
public class MainViewModel extends BaseViewModelActivity<MainActivityBinding> {
    // 中文名
    public ObservableField<String> nameCn = new ObservableField<>();
    // 英文名
    public ObservableField<String> nameEn = new ObservableField<>();
}
