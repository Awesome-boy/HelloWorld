package com.example.helloworld;

import android.app.Application;

import com.jeremyliao.liveeventbus.LiveEventBus;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        LiveEventBus
                .config()
                .lifecycleObserverAlwaysActive(true);
    }
}
