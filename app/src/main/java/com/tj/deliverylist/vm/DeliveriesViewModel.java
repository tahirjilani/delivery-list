package com.tj.deliverylist.vm;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.tj.deliverylist.db.model.Delivery;
import com.tj.deliverylist.repository.DeliveryRepository;

import java.util.List;

import javax.inject.Inject;

public class DeliveriesViewModel extends ViewModel{

    private DeliveryRepository deliveryRepository;

    // Instructs Dagger 2 to provide the UserRepository parameter.
    @Inject
    DeliveriesViewModel(DeliveryRepository deliveryRepository){
        this.deliveryRepository = deliveryRepository;
    }

    public void init(){
        deliveryRepository.fetchDeliveries(1, 20);
    }

    public LiveData<List<Delivery>> getDeliveryData(){
        return deliveryRepository.getDeliveryData();
    }
}
