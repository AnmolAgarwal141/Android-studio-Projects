package com.example.naaniz.retrofit;

import com.example.naaniz.retrofit.ItemRetrofit;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class YouTubeRetrofit {
    @SerializedName("items")
    @Expose
    private List<ItemRetrofit> items = null;

    public List<ItemRetrofit> getItems() {
        return items;
    }

    public void setItems(List<ItemRetrofit> items) {
        this.items = items;
    }
}
