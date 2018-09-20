package com.tj.deliverylist;

import android.app.Application;

import com.tj.deliverylist.di.AppComponent;
import com.tj.deliverylist.di.DaggerAppComponent;
import com.tj.deliverylist.di.moduels.DeliveryRepositoryModule;
import com.tj.deliverylist.di.moduels.DeliveryRoomDatabaseModule;
import com.tj.deliverylist.di.moduels.NetModule;

public class App extends Application {

    private static App instance;
    public static App getInstance(){
        return instance;
    }

    private AppComponent appComponent;
    public AppComponent getAppComponent() {
        return appComponent;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        DeliveryRepositoryModule deliveryRepositoryModule = new DeliveryRepositoryModule();
        DeliveryRoomDatabaseModule databaseModule = new DeliveryRoomDatabaseModule();
        NetModule netModule = new NetModule();

        appComponent = DaggerAppComponent.builder()
                .deliveryRepositoryModule(deliveryRepositoryModule)
                .deliveryRoomDatabaseModule(databaseModule)
                .netModule(netModule)
                .build();
    }
}
