package com.tj.deliverylist.di.moduels;

import com.tj.deliverylist.db.AppExecutors;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

    public static final String TAG = AppModule.class.getSimpleName();

    @Provides
    @Singleton
    public AppExecutors provideAppExecutors() {
        return new AppExecutors();
    }
}
