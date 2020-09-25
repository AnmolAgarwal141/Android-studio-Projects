package com.example.naaniz.interfaces;

import com.example.naaniz.retrofit.YouTubeRetrofit;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface YouTubeApi {

    @GET("search")
    Call<YouTubeRetrofit> getVideos(
            @Query("part") String part,
            @Query("q") String q,
            @Query("key") String key,
            @Query("type") String type
    );
}
