package com.andrew.network.library;

import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.util.HashMap;

/**
 * Created by   谷峰
 * Created on   2014/4/18
 * Created time 11:09
 * 网络工具类
 */
public class NetworkManagePool {

    private static final String ACTION_CONNECTIVITY_CHANGE = "android.net.conn.CONNECTIVITY_CHANGE";

    private static final String ACTION_WIFI_STATE_CHANGE = "android.net.wifi.WIFI_STATE_CHANGED";

    private static HashMap<String, NetworkChangeReceiver> receiversHashMap = new HashMap<>();

    private static volatile NetworkManagePool instance;

    private NetworkManageConfiguration configuration;

    protected NetworkManagePool() {
    }

    public static NetworkManagePool getInstance() {
        if (instance == null) {
            synchronized (NetworkManagePool.class) {
                if (instance == null) {
                    instance = new NetworkManagePool();
                }
            }
        }
        return instance;
    }

    public synchronized void init(NetworkManageConfiguration configuration) {
        if (configuration == null) {
            throw new IllegalArgumentException();
        }
        if (this.configuration == null) {
            this.configuration = configuration;
        }
    }

    public NetworkManageConfiguration getConfiguration() {
        return configuration;
    }

    /**
     * 注册网络回调
     * @param object
     * @param listener
     * @param notifyImmediately 是否立即获取网络状态
     */
    public void registerForConnectivityEvents(Object object, NetworkConnectivityListener listener, boolean notifyImmediately) {
        boolean hasConnection = hasNetworkConnection();
        NetworkConnectionCache.setLastNetworkState(object, hasConnection);
        if (notifyImmediately)
            notifyConnectionChange(hasConnection,listener);
        IntentFilter filter = new IntentFilter();
        filter.addAction(ACTION_CONNECTIVITY_CHANGE);
        filter.addAction(ACTION_WIFI_STATE_CHANGE);

        NetworkChangeReceiver receiver = new NetworkChangeReceiver(object, listener);
        if (!receiversHashMap.containsKey(object.toString())) {
            receiversHashMap.put(object.toString(), receiver);
        }

        configuration.getContext().registerReceiver(receiver, filter);
    }

    /**
     * 取消注册监听网络切换
     * @param object
     */
    public void unregisterFromConnectivityEvents(Object object) {
        NetworkChangeReceiver receiver = receiversHashMap.get(object.toString());
        if (null != receiver) {
            configuration.getContext().unregisterReceiver(receiver);
        }
        receiversHashMap.remove(object.toString());
    }

    /**
     *  网络连接类型变化
     * @param listener
     */
    public void notifyConnectionTypeChange(final NetworkConnectivityListener listener) {
        final NetworkConnectionEvent event = new NetworkConnectionEvent(NetworkState.CONNECTED, getNetworkType());
        if (event.getType() == NetworkType.BOTH && configuration.isRegisteredForMobileNetworkChanges()
                && configuration.isRegisteredForWiFiChanges()) {
            listener.onConnectionTypeChange(event);
        } else if (event.getType() == NetworkType.MOBILE && configuration.isRegisteredForMobileNetworkChanges()) {
            listener.onConnectionTypeChange(event);
        } else if (event.getType() == NetworkType.WIFI && configuration.isRegisteredForWiFiChanges()) {
            listener.onConnectionTypeChange(event);
        }
    }

    /**
     * 网络连接状态变化
     * @param hasConnection
     * @param listener
     */
    public void notifyConnectionChange(boolean hasConnection, final NetworkConnectivityListener listener) {
        if (hasConnection) {
            final NetworkConnectionEvent event = new NetworkConnectionEvent(NetworkState.CONNECTED, getNetworkType());
            handleActiveInternetConnection(event, listener);
        } else {
            listener.onConnectionChange(new NetworkConnectionEvent(NetworkState.DISCONNECTED, NetworkType.NONE));
        }
    }

    private void handleActiveInternetConnection(NetworkConnectionEvent event, NetworkConnectivityListener listener) {
        if (event.getType() == NetworkType.BOTH && configuration.isRegisteredForMobileNetworkChanges()
                && configuration.isRegisteredForWiFiChanges()) {
            listener.onConnectionChange(event);
        } else if (event.getType() == NetworkType.MOBILE && configuration.isRegisteredForMobileNetworkChanges()) {
            listener.onConnectionChange(event);
        } else if (event.getType() == NetworkType.WIFI && configuration.isRegisteredForWiFiChanges()) {
            listener.onConnectionChange(event);
        }
    }

    /**
     * 当前是否有网环境
     * @return
     */
    public boolean hasNetworkConnection() {
        if (configuration.getConnectivityManager() == null) {
            throw new IllegalStateException("Connectivity manager is null, library was not properly initialized!");
        }

        NetworkInfo networkInfoMobile = configuration.getConnectivityManager().getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        NetworkInfo networkInfoWiFi = configuration.getConnectivityManager().getNetworkInfo(ConnectivityManager.TYPE_WIFI);

        return networkInfoMobile != null && networkInfoMobile.isConnected() || networkInfoWiFi.isConnected();
    }

    /**
     * 网络类型
     * @return
     */
    public NetworkType getNetworkType() {
        if (configuration.getConnectivityManager() == null) {
            throw new IllegalStateException("Connectivity manager is null, library was not properly initialized!");
        }

        NetworkInfo networkInfoMobile = configuration.getConnectivityManager().getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        NetworkInfo networkInfoWiFi = configuration.getConnectivityManager().getNetworkInfo(ConnectivityManager.TYPE_WIFI);

        if (networkInfoMobile != null && networkInfoMobile.isConnected() && networkInfoWiFi.isConnected()) {
            return NetworkType.BOTH;
        } else if (networkInfoMobile != null && networkInfoMobile.isConnected()) {
            return NetworkType.MOBILE;
        } else if (networkInfoWiFi.isConnected()) {
            return NetworkType.WIFI;
        } else {
            return NetworkType.NONE;
        }
    }
}
