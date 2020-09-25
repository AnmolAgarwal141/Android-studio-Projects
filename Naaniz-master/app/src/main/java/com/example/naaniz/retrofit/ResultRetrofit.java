package com.example.naaniz.retrofit;

import android.content.Intent;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResultRetrofit {

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("title")
    @Expose
    private String name;

    @SerializedName("readyInMinutes")
    @Expose
    private String cookingTime;

    @SerializedName("servings")
    @Expose
    private String servings;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCookingTime() {
        return cookingTime;
    }

    public String getServings() {
        return servings;
    }
}
