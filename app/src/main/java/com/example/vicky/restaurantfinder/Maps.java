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

public class Maps extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps2);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
      /*
    }*/
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
    public void onMapReady(GoogleMap googleMaps) {
        mMap = googleMaps;


        LatLng dck = new LatLng(28.468398,77.015591);
        LatLng cake = new LatLng(28.467399,77.017436);
        LatLng chow = new LatLng(28.468597,77.015173);
        LatLng dial = new LatLng(28.468776,77.014036);
        LatLng biryani = new LatLng(28.510867,77.047616);
        LatLng om = new LatLng(28.5107311,77.0482768);
        LatLng eato = new LatLng(28.511352,77.048707);
        LatLng chicken = new LatLng(28.510854,77.049385);

        mMap.addMarker(new MarkerOptions().position(dck).title("DCK Dana Choga").snippet("Sector-7"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(dck));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(dck, 14.0f));
        mMap.addMarker(new MarkerOptions().position(cake).title("Cake Innovation").snippet("Sector-7"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(cake));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(cake, 14.0f));
        mMap.addMarker(new MarkerOptions().position(chow).title("Chowringhee").snippet("Sector-7"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(chow));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(chow, 14.0f));
        mMap.addMarker(new MarkerOptions().position(dial).title("Dial a Chicken").snippet("Sector-7"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(dial));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(dial, 14.0f));
        mMap.addMarker(new MarkerOptions().position(biryani).title("Biryani Paradise").snippet("Sector-23"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(biryani));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(biryani, 14.0f));
        mMap.addMarker(new MarkerOptions().position(om).title("OmSweets&Paradise").snippet("Sector-23"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(om));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(om, 14.0f));
        mMap.addMarker(new MarkerOptions().position(eato).title("Eato").snippet("Sector-23"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(eato));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(eato, 14.0f));
        mMap.addMarker(new MarkerOptions().position(chicken).title("Chicken Factory").snippet("Sector-23"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(chicken));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(chicken,14.0f));





        // Updating the camera position to the user input latitude and longitude


    }
}
