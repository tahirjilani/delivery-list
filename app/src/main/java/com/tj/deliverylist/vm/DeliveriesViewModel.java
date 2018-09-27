package com.tj.deliverylist.vm;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.paging.PagedList;

import com.tj.deliverylist.db.model.Delivery;
import com.tj.deliverylist.repository.DeliveryRepository;

import javax.inject.Inject;

public class DeliveriesViewModel extends ViewModel{

    private DeliveryRepository deliveryRepository;

    @Inject
    public DeliveriesViewModel(DeliveryRepository deliveryRepository){
        this.deliveryRepository = deliveryRepository;
    }

    public LiveData<PagedList<Delivery>> getDeliveryData(){
        return deliveryRepository.getDeliveryData();
    }

    public void refreshData(){
        deliveryRepository.refreshData();
    }

    public MutableLiveData getNetworkCallState(){
        return deliveryRepository.getNetworkState();
    }

    public void retry(Delivery d){
        if (d != null) {
            deliveryRepository.retryLastApiCall(d);
        }
    }
}
