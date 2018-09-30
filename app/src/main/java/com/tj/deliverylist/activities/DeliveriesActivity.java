package com.tj.deliverylist.activities;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;

import com.tj.deliverylist.App;
import com.tj.deliverylist.R;
import com.tj.deliverylist.adapter.DeliveryAdapter;
import com.tj.deliverylist.databinding.DeliveriesActivityBinding;
import com.tj.deliverylist.net.NetworkState;
import com.tj.deliverylist.utils.Utils;
import com.tj.deliverylist.vm.DeliveriesViewModel;
import com.tj.deliverylist.vm.ViewModelFactory;

import javax.inject.Inject;

public class DeliveriesActivity extends AppCompatActivity {

    private final String TAG = this.getClass().getSimpleName();

    @Inject
    ViewModelFactory viewModelFactory;

    private DeliveriesActivityBinding binding;
    private DeliveriesViewModel viewModel;
    private DeliveryAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        App.getInstance().getAppComponent().inject(this);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_deliveries);
        setSupportActionBar(binding.myToolbar);
        binding.toolbarTitle.setText(R.string.things_to_deliver);

        //ViewModelFactory to facilitate ViewModel injection
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(DeliveriesViewModel.class);

        //Erase data from db to force paging library to fetch latest
        binding.swipeRefreshLayout.setOnRefreshListener(() -> viewModel.refreshData());

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new DeliveryAdapter(this);

        //Submit new data pages to adapter
        viewModel.getDeliveryData().observe(this, deliveries -> adapter.submitList(deliveries));

        //We update adapter ui w.r.t network sate. Show error in case
        viewModel.getNetworkCallState().observe(this, o -> {
            handleNetworkState(o);
        });

        binding.recyclerView.setAdapter(adapter);

    }

    private void handleNetworkState(Object o){

        NetworkState networkState = (NetworkState)o;
        if(networkState == NetworkState.LOADING || networkState == NetworkState.DONE){
            adapter.setNetworkState(networkState);
        }else {
            Utils.retryAlert(this,
                    "Error",
                    networkState.getMessage(),
                    (dialog, which) -> viewModel.retry(adapter.getLastDelivery()));
        }
        if (networkState == NetworkState.DONE || networkState.getStatus() == NetworkState.Status.STATE_FAILED){
            binding.swipeRefreshLayout.setRefreshing(false);
        }
    }
}
