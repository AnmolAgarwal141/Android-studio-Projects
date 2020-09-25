package com.example.naaniz.interfaces;

import com.example.naaniz.Results;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface AddressInterface {
    String API_KEY = "AIzaSyAMGRCo9SEKR_OB6cvM56UANRj-qz4jNQc";
    Boolean sensor = true;
    @GET("api/geocode/json")
    Call<Results> getAddress(@Query("latlng") String location, @Query("key") String key);
}
