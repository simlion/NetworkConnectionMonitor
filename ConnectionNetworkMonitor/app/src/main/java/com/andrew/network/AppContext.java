package com.andrew.network;

import android.app.Application;

import com.andrew.network.library.NetworkManageConfiguration;
import com.andrew.network.library.NetworkManagePool;

public class AppContext extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        //初始化网络切换连接配置
        NetworkManageConfiguration configuration = new NetworkManageConfiguration.Builder(this).build();
        NetworkManagePool.getInstance().init(configuration);
    }
}
