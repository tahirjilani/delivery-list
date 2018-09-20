package com.tj.deliverylist.net;

import com.tj.deliverylist.db.model.Delivery;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Webservice {

    @GET("deliveries")
    Call<List<Delivery>> fetchDeliveries( @Query("offset") Integer offset, @Query("limit") Integer limit);

}
