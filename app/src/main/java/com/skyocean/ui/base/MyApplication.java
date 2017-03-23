package com.skyocean.ui.base;

import android.app.Application;

import org.xutils.x;
/**
 * Created by DY on 2016/11/24.
 */

public class MyApplication extends Application {
    private static MyApplication INSTANCE = null;

    public static MyApplication getInstance() {
        return INSTANCE;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        INSTANCE = this;
        //初始化Xutils 3.0
        x.Ext.init(this);
        x.Ext.setDebug(true); //是否输出debug日志，开启debug会影响性能。
    }

}
