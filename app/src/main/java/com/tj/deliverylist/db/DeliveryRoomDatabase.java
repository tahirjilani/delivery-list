package com.tj.deliverylist.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.tj.deliverylist.db.model.Delivery;

@Database(entities = {Delivery.class}, version = 1, exportSchema = false)
public abstract class DeliveryRoomDatabase extends RoomDatabase{

    public static final String DATABASE_NAME = "delivery_database";

    public abstract DeliveryDao deliveryDao();

}
