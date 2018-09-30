package com.tj.deliverylist.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.paging.LivePagedListBuilder;
import android.arch.paging.PagedList;
import android.util.Log;

import com.tj.deliverylist.db.AppExecutors;
import com.tj.deliverylist.db.DeliveryDao;
import com.tj.deliverylist.db.model.Delivery;
import com.tj.deliverylist.net.NetworkState;
import com.tj.deliverylist.net.Webservice;

import java.io.IOException;
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

    private MutableLiveData networkState;

    public static final int PAGE_SIZE = 20;
    private LiveData<PagedList<Delivery>> deliveryList;
    private BoundaryCallback boundaryCallback;

    @Inject
    public DeliveryRepository(Webservice webservice, DeliveryDao deliveryDao, AppExecutors appExecutors){

        this.webservice = webservice;
        this.deliveryDao = deliveryDao;
        this.appExecutors = appExecutors;

        initDataSource();
    }

    private void initDataSource() {

        PagedList.Config pagedListConfig =
                (new PagedList.Config.Builder())
                        .setEnablePlaceholders(true)
                        .setPageSize(PAGE_SIZE)
                        .build();
        boundaryCallback = new BoundaryCallback(this);

        deliveryList = (new LivePagedListBuilder(deliveryDao.deliveriesById(), pagedListConfig))
                    .setBoundaryCallback(boundaryCallback)
                .build();

        networkState = new MutableLiveData();

    }

    public void fetchDeliveries(Integer offset, Integer limit){

        networkState.postValue(NetworkState.LOADING);

        webservice.fetchDeliveries(offset, limit).enqueue(new Callback<List<Delivery>>() {
            @Override
            public void onResponse(Call<List<Delivery>> call, Response<List<Delivery>> response) {
                if (response.isSuccessful()){

                    final List<Delivery> deliveries = response.body();
                    saveDeliveries(deliveries);

                    networkState.postValue(NetworkState.DONE);
                }else{

                    String errorBody = "";
                    try {
                        errorBody = response.errorBody().string();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    networkState.postValue((new NetworkState(NetworkState.Status.STATE_FAILED, errorBody + "\n" + response.message())));
                    Log.e(TAG, "response not successful: " + errorBody + "\n" + response.message());
                }
            }

            @Override
            public void onFailure(Call<List<Delivery>> call, Throwable t) {
                networkState.postValue((new NetworkState(NetworkState.Status.STATE_FAILED, t.getMessage())));
                Log.e(TAG, "onFailure: " + t.toString());
            }
        });
    }

    private void saveDeliveries(final List<Delivery> deliveries){
        this.appExecutors.diskIO().execute(new Runnable() {
            @Override
            public void run() {
                deliveryDao.insert(deliveries);
            }
        });
    }

    public LiveData<PagedList<Delivery>> getDeliveryData() {
        return deliveryList;
    }

    public MutableLiveData getNetworkState() {
        return networkState;
    }

    public void refreshData(){
        this.appExecutors.networkIO().execute(new Runnable() {
            @Override
            public void run() {
                deliveryDao.deleteAll();
            }
        });
    }

    public void retryLastApiCall(Delivery d){
        boundaryCallback.onItemAtEndLoaded(d);
    }


    public MutableLiveData<Delivery> getDeliveryById(final Integer id) {

        MutableLiveData<Delivery> delivery = new MutableLiveData();
        this.appExecutors.diskIO().execute(new Runnable() {
            @Override
            public void run() {
                Delivery d = deliveryDao.getDeliveryById(id);
                delivery.postValue(d);
            }
        });
        return delivery;
    }
}
