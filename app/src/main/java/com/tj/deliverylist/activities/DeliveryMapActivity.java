package com.tj.deliverylist.activities;

import android.arch.lifecycle.ViewModelProviders;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.picasso.Picasso;
import com.tj.deliverylist.App;
import com.tj.deliverylist.R;
import com.tj.deliverylist.db.model.Delivery;
import com.tj.deliverylist.vm.DeliveryMapViewModel;
import com.tj.deliverylist.vm.ViewModelFactory;

import javax.inject.Inject;

public class DeliveryMapActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    @Inject
    ViewModelFactory viewModelFactory;

    private DeliveryMapViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_map);
        App.getInstance().getAppComponent().inject(this);

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(DeliveryMapViewModel.class);


        Toolbar toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        ((TextView)findViewById(R.id.toolbar_title)).setText(R.string.delivery_details);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            Integer id = (Integer) bundle.getSerializable("id");
            viewModel.getDeliveryById(id).observe(this, delivery -> {
                if (delivery != null) {
                    LatLng address = new LatLng(delivery.getLocation().getLat(), delivery.getLocation().getLng());
                    mMap.addMarker(new MarkerOptions().position(address));
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(address));

                    showDescription(delivery);
                }
            });

        }
    }

    private void showDescription(final Delivery delivery){

        ImageView imageView = findViewById(R.id.image_view);
        TextView descriptionTv = findViewById(R.id.description_tv);

        Picasso.get()
                .load(delivery.getImageUrl())
                .placeholder(R.drawable.ic_image_black_24dp)
                .error(R.drawable.ic_image_black_24dp)
                .into(imageView);

        descriptionTv.setText(delivery.getDescription() + " at " + delivery.getLocation().getAddress());

    }
}
