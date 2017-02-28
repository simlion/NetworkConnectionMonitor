package com.andrew.network.library;

import java.io.Serializable;

/**
 * Created by   谷峰
 * Created on   16/4/18
 * Created time 11:09
 * 网络工具类--回调实体类
 */
public class NetworkConnectionEvent implements Serializable {

    private NetworkState state;

    private NetworkType type;

    public NetworkConnectionEvent() {}

    public NetworkConnectionEvent(NetworkState state, NetworkType type) {
        this.state = state;
        this.type = type;
    }

    public NetworkState getState() {
        return state;
    }

    public void setState(NetworkState state) {
        this.state = state;
    }

    public NetworkType getType() {
        return type;
    }

    public void setType(NetworkType type) {
        this.type = type;
    }
}