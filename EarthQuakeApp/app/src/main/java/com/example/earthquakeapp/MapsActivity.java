package com.example.earthquakeapp;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.earthquakeapp.Activities.QuakelistActivity;
import com.example.earthquakeapp.Model.Earthquake;
import com.example.earthquakeapp.Ui.Custominfowindow;
import com.example.earthquakeapp.Util.Constants;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.util.Date;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnInfoWindowClickListener, GoogleMap.OnMarkerClickListener {

    private GoogleMap mMap;
    private LocationListener locationListener;
    private LocationManager locationManager;
    private RequestQueue queue;
    private Button showlistbutton;
private AlertDialog.Builder dialogbuilder;
private AlertDialog dialog;
private BitmapDescriptor[] itemcolor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        showlistbutton =findViewById(R.id.showlistbtn);
        showlistbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MapsActivity.this, QuakelistActivity.class));
            }
        });
        itemcolor=new BitmapDescriptor[]{BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE),BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW),BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN),BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN),BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA),BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)};
        queue = Volley.newRequestQueue(this);
        getEarthQuakes();
    }

    private void getEarthQuakes() {
        final Earthquake earthquake = new Earthquake();
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, Constants.url, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray features =response.getJSONArray("features");
                    for(int i=0 ;i<Constants.LIMIT;i++){
                        JSONObject properties =features.getJSONObject(i).getJSONObject("properties");
                        JSONObject geometry =features.getJSONObject(i).getJSONObject("geometry");
                        JSONArray coordinates = geometry.getJSONArray("coordinates");
                        double lon =coordinates.getDouble(0);
                        double lat = coordinates.getDouble(1);
                        earthquake.setPlace(properties.getString("place"));
                        earthquake.setType(properties.getString("type"));
                        earthquake.setTime(properties.getLong("time"));
                        earthquake.setLat(lat);
                        earthquake.setLon(lon);
                        earthquake.setMagnitude(properties.getDouble("mag"));
                        earthquake.setDetaillink(properties.getString("detail"));

                        java.text.DateFormat dateFormat = java.text.DateFormat.getDateInstance();
                        String formatteddate=dateFormat.format(new Date(Long.valueOf(properties.getLong("time"))).getTime());
                        MarkerOptions markerOptions=new MarkerOptions();
                        markerOptions.icon(itemcolor[Constants.randmint(0,itemcolor.length)]);
                        markerOptions.title(earthquake.getPlace());
                        markerOptions.position(new LatLng(lat,lon));
                        markerOptions.snippet("Magnitude: "+earthquake.getMagnitude()+"\n"+"Date: "+ formatteddate);

                       if(earthquake.getMagnitude()>=2.0){
                           CircleOptions circleOptions =new CircleOptions();
                           circleOptions.center(new LatLng(earthquake.getLat(),earthquake.getLon()));
                           circleOptions.radius(30000);
                           circleOptions.strokeWidth(3.6f);
                           circleOptions.fillColor(Color.RED);
                           markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
                           mMap.addCircle(circleOptions);
                       }


                        Marker marker = mMap.addMarker(markerOptions);
                        marker.setTag(earthquake.getDetaillink());
                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat,lon),3));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        queue.add(jsonObjectRequest);
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
        mMap.setInfoWindowAdapter(new Custominfowindow(getApplicationContext()));
        mMap.setOnInfoWindowClickListener(this);
        mMap.setOnMarkerClickListener(this);
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        locationManager = (LocationManager) this.getSystemService(LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                Log.d("location: ", location.toString());
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
        };

        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    Activity#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for Activity#requestPermissions for more details.
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},1);

        }
        else {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(grantResults.length>0 && grantResults[0] ==PackageManager.PERMISSION_GRANTED){
            if(ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION)==PackageManager.PERMISSION_GRANTED){
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
            }
        }
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        getQuakedetails(marker.getTag().toString());
    }

    private void getQuakedetails(String url) {
        JsonObjectRequest jsonObjectRequest =new JsonObjectRequest(Request.Method.GET, url, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                String detailsurl="";
                try {
                    JSONObject properties =response.getJSONObject("properties");
                    JSONObject products =properties.getJSONObject("products");
                    JSONArray geoserve =products.getJSONArray("geoserve");
                    for(int i=0;i<geoserve.length();i++){
                        JSONObject geoserveobj=geoserve.getJSONObject(i);
                        JSONObject contentobj=geoserveobj.getJSONObject("contents");
                        JSONObject geoJsonobj=contentobj.getJSONObject("geoserve.json");
                        detailsurl = geoJsonobj.getString("url");
                    }
                    getMoreDetails(detailsurl);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        queue.add(jsonObjectRequest);
    }
    public  void getMoreDetails(String url){
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                   dialogbuilder=new AlertDialog.Builder(MapsActivity.this);
                View view = getLayoutInflater().inflate(R.layout.popup,null);
            Button dismissButton = view.findViewById(R.id.popbutton);
                Button dismissButtontop=view.findViewById(R.id.dismisspoptop);
                TextView poplist =view.findViewById(R.id.poplist);
                WebView htmlpop = view.findViewById(R.id.htmlwebview);
                StringBuilder stringBuilder = new StringBuilder();
                try {

                    if(response.has("tectonicSummary")&& response.getString("tectonicSummary")!=null){
                        JSONObject tectonic = response.getJSONObject("tectonicSummary");
                       if(tectonic.has("text")&& tectonic.getString("text")!=null){
                            String text=tectonic.getString("text");
                           // htmlpop.loadDataWithBaseURL(null,text,"text/html","UTF-8",null);
                        }
                    }

                    JSONArray cities =response.getJSONArray("cities");
                    for(int i=0;i<cities.length();i++){
                        JSONObject citiesobj =cities.getJSONObject(i);
                        stringBuilder.append("City: "+citiesobj.getString("name")+"\n"+"Distance: "+citiesobj.getString("distance")+"\n"+"Population: "+citiesobj.getString("population"));
                    stringBuilder.append("\n\n");}
                    poplist.setText(stringBuilder);
                    dismissButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.dismiss();
                        }
                    });
                    dismissButtontop.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.dismiss();
                        }
                    });
                    dialogbuilder.setView(view);
                    dialog=dialogbuilder.create();
                    dialog.show();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        queue.add(jsonObjectRequest);
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }
}
