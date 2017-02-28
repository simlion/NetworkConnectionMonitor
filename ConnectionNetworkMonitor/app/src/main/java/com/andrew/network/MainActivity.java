package com.andrew.network;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.andrew.network.library.NetworkConnectionEvent;
import com.andrew.network.library.NetworkConnectivityListener;
import com.andrew.network.library.NetworkManagePool;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStart() {
        super.onStart();
        NetworkManagePool.getInstance().registerForConnectivityEvents(this, new NetworkConnectivityListener() {
            @Override
            public void onConnectionChange(NetworkConnectionEvent event) {

            }

            @Override
            public void onConnectionTypeChange(NetworkConnectionEvent event) {

            }
        },true);
    }

    @Override
    protected void onStop() {
        super.onStop();
        NetworkManagePool.getInstance().unregisterFromConnectivityEvents(this);
    }
}
