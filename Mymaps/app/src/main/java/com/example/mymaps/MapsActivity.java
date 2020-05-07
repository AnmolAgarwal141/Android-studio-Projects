package com.example.mymaps;

import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private LatLng mountEverest =new LatLng(28.001377,86.928129);
    LatLng mountKilimanjaro = new LatLng(-3.075558,37.344363);
    LatLng theAlps = new LatLng(47.368955,9.702579);
    LatLng binga =new LatLng(-19.7766658,33.0444344);
    LatLng sydney = new LatLng(-34, 151);
    private Marker everestmarker;
    private Marker kilimanjaromarker;
    private Marker thealpsmarker;
    private ArrayList<Marker> markerArrayList= new ArrayList<>();

    private MarkerOptions everestoption;
    private MarkerOptions kilimanjarooption;
    private MarkerOptions thealpsoption;

    private ArrayList<MarkerOptions> markerOptionsArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
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
        mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);

        everestoption=new MarkerOptions().position(mountEverest).title("Mt.Everest").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE));
        markerOptionsArrayList.add(everestoption);
        kilimanjarooption=new MarkerOptions().position(mountKilimanjaro).title("Mt.Kelinmanjaro").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
        markerOptionsArrayList.add(kilimanjarooption);
        thealpsoption=new MarkerOptions().position(theAlps).title("The Alps").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
        markerOptionsArrayList.add(thealpsoption);

        for ( MarkerOptions options:markerOptionsArrayList){
            LatLng latLng = new LatLng(options.getPosition().latitude,options.getPosition().longitude);
            mMap.addMarker(options);
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,8));//1-20
           // Log.d("Marker","onmapready"+marker.getTitle());
        }
        // Add a marker in Sydney and move the camera


       // mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)).alpha(0.7f));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney,8));//1-20
    }
}
