package com.andrew.network.library;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by   谷峰
 * Created on   16/4/18
 * Created time 11:11
 * 网络工具类--网络状态监听广播
 */
public class NetworkChangeReceiver extends BroadcastReceiver {

    private Object object;

    private NetworkConnectivityListener mCallback;

    public NetworkChangeReceiver(Object object, NetworkConnectivityListener mCallback) {
        this.object = object;
        this.mCallback = mCallback;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        boolean hasConnectivity = NetworkManagePool.getInstance().hasNetworkConnection();
        if (hasConnectivity) {
            if (NetworkConnectionCache.getLastNetworkState(object) != hasConnectivity) {
                NetworkConnectionCache.setLastNetworkState(object, hasConnectivity);
                NetworkManagePool.getInstance().notifyConnectionChange(hasConnectivity, mCallback);
            }
            notifyConnectionTypeChange();
        } else if (!hasConnectivity && NetworkConnectionCache.getLastNetworkState(object) != hasConnectivity) {
            NetworkConnectionCache.setLastNetworkState(object, hasConnectivity);
            NetworkConnectionCache.clearLastNetworkType(object);
            NetworkManagePool.getInstance().notifyConnectionChange(hasConnectivity, mCallback);
        }
    }

    private void notifyConnectionTypeChange() {
        NetworkType lastType = NetworkConnectionCache.getLastNetworkType(object);
        NetworkType currentType = NetworkManagePool.getInstance().getNetworkType();
        if (null == lastType) {
            NetworkConnectionCache.setLastNetworkStateType(object, currentType);
            NetworkManagePool.getInstance().notifyConnectionTypeChange(mCallback);
        } else if (lastType != currentType) {
            NetworkConnectionCache.setLastNetworkStateType(object, currentType);
            NetworkManagePool.getInstance().notifyConnectionTypeChange(mCallback);
        }
    }
}
