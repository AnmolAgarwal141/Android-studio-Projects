package com.example.naaniz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.example.naaniz.models.User;
import com.example.naaniz.location.CurrentAddress;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.libraries.places.api.net.FetchPlaceRequest;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;


import java.util.Arrays;
import java.util.List;


public class SignUp2 extends AppCompatActivity implements OnMapReadyCallback, FssaiDialog.DialogListener {

    private Double lat = 0.0;
    private Double lng = 0.0;
    private GoogleMap gMap;
    private SupportMapFragment mapFragment;
    private Button finish_button;
    private User user;
    String apiKey;
    PlacesClient placesClient;
    private FusedLocationProviderClient fusedLocationClient;
    LatLng currentLatLng;
    String currentAddress = "";
    Marker marker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up2);

        initUI();
        callMap();
        initAutocompleteSearchFragment();
        initListener();
    }

    private void initUI() {
        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        finish_button = findViewById(R.id.sign_up_finish);

    }

    private void initListener() {
        if(finish_button!=null && !finish_button.hasOnClickListeners()) {
            finish_button.setOnClickListener(v -> {
                //for customer flag value = 0, for vendor flag value will be 1

                user = (User) getIntent().getSerializableExtra("user_details");
                if (marker == null) {//permissions not granted
                    checkPermissions();
                    return;
                }
                if (user != null) {
                    user.setLat(marker.getPosition().latitude);
                    user.setLng(marker.getPosition().longitude);
                }
                if (getIntent().getIntExtra("user_type", 0) == 0) {
                    openDialog();
                } else {
                    Intent intent = new Intent(SignUp2.this, SignUp3_Vendor.class);
                    intent.putExtra("user_details", user);
                    startActivity(intent);
                } });
        }
    }

    private void initMyLocationButtonListener() {
        if(gMap!=null ){
            gMap.setOnMyLocationButtonClickListener(new GoogleMap.OnMyLocationButtonClickListener() {
                @Override
                public boolean onMyLocationButtonClick() {
                    initFusedListener();
                    return true;
                }});
        }
    }

    private void callMap() { mapFragment.getMapAsync(this); }

    @Override
    public void onMapReady(GoogleMap map) {
        gMap = map;
        initMyLocationButtonListener();
        checkPermissions();
    }

    private void initFusedListener() {
        gMap.setMyLocationEnabled(true);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {
                            //callMap();
                            currentLatLng = new LatLng(location.getLatitude(), location.getLongitude());
                            addMarker(location.getLatitude(), location.getLongitude());
                        }
                    }
                });
    }

    private void checkPermissions() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) ==
                        PackageManager.PERMISSION_GRANTED) {
            initFusedListener();
            initOnDragListener();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION},
                    011020);
//            Toast.makeText(this,"permission not granted",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 011020) {
            if (grantResults.length == 0)
                return;
            if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(getApplicationContext(),
                        "Location is required for completion of SignUp", Toast.LENGTH_SHORT).show();
            } else {
                //Toast.makeText(SignUp2.this,"Map Ready",Toast.LENGTH_SHORT).show();
                initFusedListener();
                initOnDragListener();

            }
        }
    }

    private void addMarker(Double latitude, Double longitude) {
        if(marker!=null)marker.remove();

        LatLng loc = new LatLng(latitude, longitude);
        marker = gMap.addMarker(new MarkerOptions().position(loc).title("Cloud Kitchen").draggable(true));
        gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(loc, 12));
    }

    private void initOnDragListener() {
        gMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
            @Override
            public void onMarkerDragStart(Marker marker) {
                Toast.makeText(SignUp2.this, "Dragging", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onMarkerDrag(Marker marker) {
            }

            @Override
            public void onMarkerDragEnd(Marker marker) {
                currentLatLng = marker.getPosition();
                currentAddress = new CurrentAddress(currentLatLng.latitude, currentLatLng.longitude).getAddress(SignUp2.this);
                marker.setPosition(currentLatLng);
                //Toast.makeText(SignUp2.this, "Map Locatino : "+(currentLatLng==marker.getPosition()),Toast.LENGTH_SHORT).show();
                //marker.remove();
                Toast.makeText(SignUp2.this, "Map Location : " + currentAddress, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void openDialog() {
        FssaiDialog dialog = new FssaiDialog();
        dialog.show(getSupportFragmentManager(), "Fssai Dialog");
    }

    @Override
    public void sendCode(int code) {
        Log.d("user_details", "SignUp2 user : " + Boolean.toString(user == null));
        if (code == 2) {
            Intent intent = new Intent(SignUp2.this, SignUp3.class);
            intent.putExtra("user_details", user);
            startActivity(intent);
        }
        if (code == 1) {
            Intent intent = new Intent(SignUp2.this, SignUp3Fssai.class);
            intent.putExtra("user_details", user);
            startActivity(intent);
        }
    }

    private void initAutocompleteSearchFragment() {
        apiKey = getString(R.string.google_api_key);
        if (!Places.isInitialized()) {
            Places.initialize(getApplicationContext(), apiKey);
        }
        placesClient = Places.createClient(this);
        AutocompleteSupportFragment autocompleteFragment = (AutocompleteSupportFragment)
                getSupportFragmentManager().findFragmentById(R.id.autocomplete_fragment);

        if (autocompleteFragment == null)
            return;
        autocompleteFragment.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG));
        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                // TODO: Get info about the selected place.
                if (place == null)
                    Log.d("TAG", "place is null : " + true);
                else {
                    Log.d("TAG", "Place: " + place.getName() + ", " + place.getId() + " " + place.getLatLng());
                    List<Place.Field> placeFields = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG, Place.Field.ADDRESS);
                    FetchPlaceRequest request = FetchPlaceRequest.newInstance(place.getId(), placeFields);
                    placesClient.fetchPlace(request).addOnSuccessListener((response) -> {
                        Place placeFound = response.getPlace();
                        Log.i("TAG", "Place found: " + placeFound.getLatLng().latitude + " : " + placeFound.getLatLng().longitude);
                        addMarker(placeFound.getLatLng().latitude, placeFound.getLatLng().longitude);
                        currentAddress = new CurrentAddress(placeFound.getLatLng().latitude, placeFound.getLatLng().longitude).getAddress(SignUp2.this);

                    }).addOnFailureListener((exception) -> {
                        if (exception instanceof ApiException) {
                            Log.e("TAG", "Place not found: " + exception.getMessage());
                        }
                    });
                }
            }

            @Override
            public void onError(Status status) {
                // TODO: Handle the error.
                Log.d("TAG", "An error occurred: " + status.getStatusMessage()
                );
            }
        });
    }
}