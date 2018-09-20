package com.tj.deliverylist.db;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.tj.deliverylist.db.model.Delivery;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
public interface DeliveryDao {

    @Query("SELECT * from delivery_table ORDER BY id ASC")
    LiveData<List<Delivery>> getDeliveries();

    @Insert(onConflict = REPLACE)
    void insert(Delivery delivery);

    @Insert(onConflict = REPLACE)
    void insert(List<Delivery> deliveries);

    @Query("DELETE FROM delivery_table")
    void deleteAll();
}
