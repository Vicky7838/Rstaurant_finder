package com.example.vicky.restaurantfinder;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps2);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    @Override
    public void onMapReady(GoogleMap googleMaps) {
        mMap = googleMaps;

        Bundle bundle = getIntent().getExtras();


       String la = bundle.getString("lat");
        double va = Double.parseDouble(la);
        String  lo= bundle.getString("lon");
        double vo = Double.parseDouble(lo);
        String  na= bundle.getString("name");
        String  ad= bundle.getString("addd");
       LatLng sydney = new LatLng(va,vo);
        mMap.addMarker(new MarkerOptions().position(sydney).title(na).snippet(ad));
             mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(sydney,14.0f));




    }
}
