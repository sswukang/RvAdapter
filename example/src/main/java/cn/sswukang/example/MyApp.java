package cn.sswukang.example;

import android.app.Application;

import cn.sswukang.example.manager.CountryManager;

/**
 * My Application
 *
 * @author sswukang on 2017/2/22 10:57
 * @version 1.0
 */
public class MyApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        CountryManager.getInstance().init(this);
    }
}
