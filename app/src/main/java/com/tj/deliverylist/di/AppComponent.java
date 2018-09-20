package com.tj.deliverylist.di;

import com.tj.deliverylist.activities.DeliveriesActivity;
import com.tj.deliverylist.di.moduels.AppModule;
import com.tj.deliverylist.di.moduels.DeliveryRepositoryModule;
import com.tj.deliverylist.di.moduels.DeliveryRoomDatabaseModule;
import com.tj.deliverylist.di.moduels.NetModule;
import com.tj.deliverylist.di.moduels.ViewModelModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class, DeliveryRepositoryModule.class, DeliveryRoomDatabaseModule.class, ViewModelModule.class, NetModule.class})
public interface AppComponent {

    void inject(DeliveriesActivity activity);

}
