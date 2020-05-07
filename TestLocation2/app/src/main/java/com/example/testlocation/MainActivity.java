package com.example.testlocation;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.os.Build;
import android.os.Bundle;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationAvailability;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks,GoogleApiClient.OnConnectionFailedListener, LocationListener {
    private static final int ALL_PERMISSION_RESULT = 1111;
    private GoogleApiClient client;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private ArrayList<String> permissiontorequest;
    private ArrayList<String> permissions = new ArrayList<>();
    private ArrayList<String> permissionsrejected =new ArrayList<>();
    private TextView locationtextview;
    private LocationRequest locationrequest;
    private static final long update_interval =5000;
    private static final long fastest_interval =5000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        locationtextview =findViewById(R.id.location_text_view);
        fusedLocationProviderClient =LocationServices.getFusedLocationProviderClient(MainActivity.this);

        permissions.add(Manifest.permission.ACCESS_FINE_LOCATION);
        permissions.add(Manifest.permission.ACCESS_COARSE_LOCATION);
        permissiontorequest=permissiontorequest(permissions);
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
            if(permissiontorequest.size()>0){
                requestPermissions(permissiontorequest.toArray(new String[permissiontorequest.size()]),ALL_PERMISSION_RESULT);
            }
        }

        client=new GoogleApiClient.Builder(this).addApi(LocationServices.API).addConnectionCallbacks(this).addOnConnectionFailedListener(this).build();

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    private ArrayList<String> permissiontorequest(ArrayList<String> wantedpermissions) {
        ArrayList<String> result = new ArrayList<>();

        for(String perm: wantedpermissions){
            if(!hasPermission(perm)){
                result.add(perm);
            }
        }
        return result;
    }

    private boolean hasPermission(String perm) {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            return  checkSelfPermission(perm) == PackageManager.PERMISSION_GRANTED;
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        int errorcode = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(this);
        if(errorcode != ConnectionResult.SUCCESS){
            Dialog errordialog =GoogleApiAvailability.getInstance().getErrorDialog(this, errorcode, errorcode, new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialogInterface) {
                    Toast.makeText(MainActivity.this,"NO SERVICES",Toast.LENGTH_SHORT).show();
                }
            }) ;
            errordialog.show();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(client != null){
            client.connect();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        client.disconnect();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(client !=null && client.isConnected()){
            LocationServices.getFusedLocationProviderClient(this).removeLocationUpdates(new LocationCallback(){});
            client.disconnect();
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        if(location !=null){
            locationtextview.setText("Lat: "+location.getLatitude()+"lon: "+ location.getLongitude());
        }
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if(ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_COARSE_LOCATION)!=PackageManager.PERMISSION_GRANTED){
            return;
        }
        fusedLocationProviderClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if(location != null){
                    locationtextview.setText("Lat: "+location.getLatitude()+" Lon: "+ location.getLongitude());
                }
            }
        });
        startlocationUpdates();
    }

    private void startlocationUpdates() {
        locationrequest = new LocationRequest();
        locationrequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationrequest.setInterval(update_interval);
        locationrequest.setFastestInterval(fastest_interval);
        if(ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_COARSE_LOCATION)!=PackageManager.PERMISSION_GRANTED){
            Toast.makeText(MainActivity.this,"YOU NEED TO ENABLE PERMISSIONS TO DISPLAY LOCATION",Toast.LENGTH_LONG).show();
        }
        LocationServices.getFusedLocationProviderClient(MainActivity.this).requestLocationUpdates(locationrequest,new LocationCallback(){
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);
                if(locationResult !=null){
                    Location location =locationResult.getLastLocation();
                    locationtextview.setText("Lat: "+location.getLatitude()+"Lon: "+location.getLongitude());
                }
            }

            @Override
            public void onLocationAvailability(LocationAvailability locationAvailability) {
                super.onLocationAvailability(locationAvailability);
            }
        },null);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case ALL_PERMISSION_RESULT:
                for(String perm : permissiontorequest){
                    if(!hasPermission(perm)){
                        permissionsrejected.add(perm);
                    }
                }
                if(permissionsrejected.size()>0){
                    if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
                        if(shouldShowRequestPermissionRationale(permissionsrejected.get(0))){
                            new AlertDialog.Builder(MainActivity.this).setMessage("THESE PERMISSIONS ARE MANDATORY TO GET LOCATION").setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
                                        requestPermissions(permissionsrejected.toArray(new String[permissionsrejected.size()]),ALL_PERMISSION_RESULT);
                                    }
                                }
                            }).setNegativeButton("CANCEL",null).create().show();
                        }
                    }
                }else{
                    if(client !=null){
                        client.connect();
                    }
                }
                break;
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
