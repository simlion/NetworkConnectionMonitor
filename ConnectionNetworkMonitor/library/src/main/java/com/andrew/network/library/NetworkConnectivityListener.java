package com.andrew.network.library;

/**
 * Created by   谷峰
 * Created on   2014/4/18
 * Created time 11:09
 * 网络连接状态回调
 */
public interface NetworkConnectivityListener {

    void onConnectionChange(NetworkConnectionEvent event);

    void onConnectionTypeChange(NetworkConnectionEvent event);
}
