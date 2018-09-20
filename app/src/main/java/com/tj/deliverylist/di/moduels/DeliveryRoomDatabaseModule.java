package com.tj.deliverylist.di.moduels;

import android.arch.persistence.room.Room;

import com.tj.deliverylist.App;
import com.tj.deliverylist.db.DeliveryDao;
import com.tj.deliverylist.db.DeliveryRoomDatabase;

import dagger.Module;
import dagger.Provides;

import static com.tj.deliverylist.db.DeliveryRoomDatabase.DATABASE_NAME;

@Module
public class DeliveryRoomDatabaseModule {

    @Provides
    public DeliveryRoomDatabase provideDatabase(){
        return Room.databaseBuilder(App.getInstance(), DeliveryRoomDatabase.class,
                DATABASE_NAME)
                .build();
    }

    @Provides
    public DeliveryDao provideDeliveryDao(DeliveryRoomDatabase database){
        return database.deliveryDao();
    }


}
