package com.andrew.network.library;

/**
 * Created by   谷峰
 * Created on   2014/4/18
 * Created time 11:09
 * 网络工具类，缓存最近的网络状态及最近的网络类型
 */
public class NetworkConnectionCache {

    private NetworkConnectionCache() {

    }

    /**
     * 最后一次连接网络状态
     * @param object
     * @return
     */
    public static boolean getLastNetworkState(Object object) {
        if (isLastNetworkStateStored(object)) {
            return NetworkManagePool.getInstance().getConfiguration().getInMemoryCache().get(object.toString());
        }

        return true;
    }

    public static void setLastNetworkState(Object object, boolean isActive) {
        NetworkManagePool.getInstance().getConfiguration().getInMemoryCache().put(object.toString(), isActive);
    }

    public static boolean isLastNetworkStateStored(Object object) {
        return NetworkManagePool.getInstance().getConfiguration().getInMemoryCache().snapshot().containsKey(object.toString());
    }

    /**
     * 最后一次连接网络类型
     * @param object
     * @return
     */
    public static NetworkType getLastNetworkType(Object object) {
        return NetworkManagePool.getInstance().getConfiguration().getInMemoryCacheType().get(object.toString());
    }

    public static void setLastNetworkStateType(Object object, NetworkType type) {
        NetworkManagePool.getInstance().getConfiguration().getInMemoryCacheType().put(object.toString(), type);
    }

    public static boolean isLastNetworkTypeStored(Object object) {
        return NetworkManagePool.getInstance().getConfiguration().getInMemoryCacheType().snapshot().containsKey(object.toString());
    }


    public static void clearLastNetworkState(Object object) {
        NetworkManagePool.getInstance().getConfiguration().getInMemoryCache().remove(object.toString());
    }

    public static void clearLastNetworkType(Object object) {
        NetworkManagePool.getInstance().getConfiguration().getInMemoryCacheType().remove(object.toString());
    }
}
