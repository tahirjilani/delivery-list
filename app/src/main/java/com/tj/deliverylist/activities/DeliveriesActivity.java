package com.tj.deliverylist.activities;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.tj.deliverylist.App;
import com.tj.deliverylist.R;
import com.tj.deliverylist.db.model.Delivery;
import com.tj.deliverylist.vm.DeliveriesViewModel;
import com.tj.deliverylist.vm.ViewModelFactory;

import java.util.List;

import javax.inject.Inject;

public class DeliveriesActivity extends AppCompatActivity {

    private DeliveriesViewModel viewModel;
    @Inject
    ViewModelFactory viewModelFactory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deliveries);
        App.getInstance().getAppComponent().inject(this);

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(DeliveriesViewModel.class);
        viewModel.init();

        viewModel.getDeliveryData().observe(this, new Observer<List<Delivery>>() {
            @Override
            public void onChanged(@Nullable List<Delivery> deliveries) {
                Toast.makeText(DeliveriesActivity.this, "Data: " + deliveries.size(), Toast.LENGTH_SHORT).show(); deliveries.size();
            }
        });
    }
}
