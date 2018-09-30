package com.tj.deliverylist.vm;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.tj.deliverylist.db.model.Delivery;
import com.tj.deliverylist.repository.DeliveryRepository;

import javax.inject.Inject;

public class DeliveryMapViewModel extends ViewModel{

    private DeliveryRepository deliveryRepository;

    @Inject
    public DeliveryMapViewModel(DeliveryRepository deliveryRepository){
        this.deliveryRepository = deliveryRepository;
    }

    public MutableLiveData<Delivery> getDeliveryById(Integer id){
        return deliveryRepository.getDeliveryById(id);
    }
}
