package cn.sswukang.example.model;

import android.databinding.ObservableField;

/**
 * 展示的国家类，用于数据绑定。
 *
 * @author sswukang on 2017/2/21 12:05
 * @version 1.0
 */
public class ShowCountry {
    // 中文名
    public ObservableField<String> nameCn = new ObservableField<>();
    // 英文名
    public ObservableField<String> nameEn = new ObservableField<>();
}
