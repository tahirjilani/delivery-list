package com.tj.deliverylist.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.support.annotation.NonNull;

import com.tj.deliverylist.db.model.Delivery;

@Database(entities = {Delivery.class}, version = 1)
public abstract class DeliveryRoomDatabase extends RoomDatabase{

    public static final String DATABASE_NAME = "delivery_database";

    public abstract DeliveryDao deliveryDao();

}
