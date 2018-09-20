package com.tj.deliverylist.di.moduels;

import com.tj.deliverylist.db.AppExecutors;
import com.tj.deliverylist.db.DeliveryDao;
import com.tj.deliverylist.net.Webservice;
import com.tj.deliverylist.repository.DeliveryRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class DeliveryRepositoryModule {

    @Provides
    @Singleton
    public DeliveryRepository provideDeliveryRepository(Webservice webservice, DeliveryDao deliveryDao, AppExecutors appExecutors){
        return new DeliveryRepository(webservice, deliveryDao, appExecutors);
    }
}
