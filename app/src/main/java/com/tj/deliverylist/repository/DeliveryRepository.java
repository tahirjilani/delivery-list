package com.tj.deliverylist.repository;

import android.arch.lifecycle.LiveData;
import android.util.Log;

import com.tj.deliverylist.db.AppExecutors;
import com.tj.deliverylist.db.DeliveryDao;
import com.tj.deliverylist.db.DeliveryRoomDatabase;
import com.tj.deliverylist.db.model.Delivery;
import com.tj.deliverylist.net.Webservice;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@Singleton
public class DeliveryRepository {

    private final String TAG = DeliveryRepository.class.getSimpleName();

    private final Webservice webservice;
    private final DeliveryDao deliveryDao;
    final AppExecutors appExecutors;

    @Inject
    public DeliveryRepository(Webservice webservice, DeliveryDao deliveryDao, AppExecutors appExecutors){
        this.webservice = webservice;
        this.deliveryDao = deliveryDao;
        this.appExecutors = appExecutors;
    }

    public void fetchDeliveries(Integer offset, Integer limit){

        webservice.fetchDeliveries(offset, limit).enqueue(new Callback<List<Delivery>>() {
            @Override
            public void onResponse(Call<List<Delivery>> call, Response<List<Delivery>> response) {
                if (response.isSuccessful()){
                    final List<Delivery> deliveries = response.body();
                    saveDeliveries(deliveries);
                }
            }

            @Override
            public void onFailure(Call<List<Delivery>> call, Throwable t) {

            }
        });
    }

    private void saveDeliveries(final List<Delivery> deliveries){
        this.appExecutors.networkIO().execute(new Runnable() {
            @Override
            public void run() {
                deliveryDao.insert(deliveries);
            }
        });
    }

    public LiveData<List<Delivery>> getDeliveryData() {
        return deliveryDao.getDeliveries();
    }
}
