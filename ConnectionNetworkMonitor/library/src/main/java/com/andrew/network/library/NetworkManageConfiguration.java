package com.andrew.network.library;

import android.content.Context;
import android.net.ConnectivityManager;
import android.util.LruCache;

/**
 * Created by   谷峰
 * Created on   2014/4/18
 * Created time 11:09
 * 网络工具类--配置类，按需所配置
 */
public class NetworkManageConfiguration {

    private Context context;

    //网络配置缓存对象大小
    private int cacheSize;

    //是否监听wifi网络
    private boolean registeredForWiFiChanges;

    //是否监听mobile网络
    private boolean registeredForMobileNetworkChanges;

    //网络连接状态缓存
    private LruCache<String, Boolean> inMemoryCache;

    //网络连接状态类型缓存
    private LruCache<String, NetworkType> inMemoryCacheType;

    private ConnectivityManager connectivityManager;

    private NetworkManageConfiguration(Builder builder) {
        this.context = builder.context;
        this.registeredForMobileNetworkChanges = builder.registerForMobileNetworkChanges;
        this.registeredForWiFiChanges = builder.registerForWiFiChanges;
        this.cacheSize = builder.cacheSize;
        this.inMemoryCache = new LruCache<>(cacheSize);
        this.inMemoryCacheType = new LruCache<>(cacheSize);
        this.connectivityManager =  (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
    }

    public Context getContext() {
        return context;
    }

    public boolean isRegisteredForWiFiChanges() {
        return registeredForWiFiChanges;
    }

    public boolean isRegisteredForMobileNetworkChanges() {
        return registeredForMobileNetworkChanges;
    }

    public int getCacheSize() {
        return cacheSize;
    }

    public LruCache<String, Boolean> getInMemoryCache() {
        return inMemoryCache;
    }

    public LruCache<String, NetworkType> getInMemoryCacheType() {
        return inMemoryCacheType;
    }

    public ConnectivityManager getConnectivityManager() {
        return connectivityManager;
    }

    public static class Builder {

        private Context context;

        private final int kbSize = 1024;

        private final int memoryPart = 10;

        private final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / kbSize);

        private boolean registerForWiFiChanges = true;

        private boolean registerForMobileNetworkChanges = true;

        private int cacheSize = maxMemory / memoryPart;

        public Builder(Context context) {
            this.context = context.getApplicationContext();
        }

        public Builder registerForWiFiChanges(boolean shouldRegister) {
            this.registerForWiFiChanges = shouldRegister;
            return this;
        }

        public Builder registerForMobileNetworkChanges(boolean shouldRegister) {
            this.registerForMobileNetworkChanges = shouldRegister;
            return this;
        }

        public Builder setCacheSize(int size) {
            this.cacheSize = size;
            return this;
        }


        public NetworkManageConfiguration build() {
            return new NetworkManageConfiguration(this);
        }
    }
}
