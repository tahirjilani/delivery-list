package com.tj.deliverylist.repository;

import android.arch.paging.PagedList;
import android.support.annotation.NonNull;
import android.util.Log;

import com.tj.deliverylist.db.model.Delivery;

import static com.tj.deliverylist.repository.DeliveryRepository.PAGE_SIZE;


public class BoundaryCallback extends PagedList.BoundaryCallback<Delivery>{

    private final String TAG = this.getClass().getSimpleName();

    private final DeliveryRepository deliveryRepository;

    public BoundaryCallback(@NonNull DeliveryRepository deliveryRepository){

        this.deliveryRepository = deliveryRepository;
    }


    /**
     * Called when zero items are returned from an initial load of the PagedList's data source.
     *
     * Requests initial data from the network, replacing all content currently in the database.
     */
    @Override
    public void onZeroItemsLoaded() {

        Log.d(TAG, "Database is empty. Fetch first " + PAGE_SIZE + " items and add into database.");
        deliveryRepository.fetchDeliveries(0, PAGE_SIZE);
    }


    /**
     * Called when the item at the end of the PagedList has been loaded, and access has
     * occurred within {@link PagedList.Config#prefetchDistance} of it.
     *
     * Requests additional data from the network, appending the results to the end of the database's existing data.
     *
     * <p>
     * No more data will be appended to the PagedList after this item.
     *
     * @param itemAtEnd The first item of PagedList
     */
    @Override
    public void onItemAtEndLoaded(@NonNull Delivery itemAtEnd) {

        Log.d(TAG, "Fetching " + itemAtEnd.getId() + " to " + itemAtEnd.getId() + PAGE_SIZE);

        deliveryRepository.fetchDeliveries(itemAtEnd.getId() + 1, PAGE_SIZE);
    }

}
