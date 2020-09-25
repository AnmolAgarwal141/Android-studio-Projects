package com.example.naaniz.location;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.util.Log;
import android.widget.Toast;

import com.example.naaniz.SignUp2;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class CurrentAddress {
    private Double lat,longt;

    public CurrentAddress(Double lat, Double longt) {
        this.lat = lat;
        this.longt = longt;
    }

    public Double getLat() {
        return lat;
    }

    public Double getLongt() {
        return longt;
    }
    public String getAddress(Context context)
    {
        Geocoder geocoder = new Geocoder(context, Locale.getDefault());
        List<Address> addresses = null;
        try {
            addresses = geocoder.getFromLocation(lat, longt, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String completeAddress = addresses.get(0).getAddressLine(0);
        Toast.makeText(context,completeAddress+" ",Toast.LENGTH_SHORT).show();

        Log.d("city", completeAddress);
        return completeAddress;
    }
}
