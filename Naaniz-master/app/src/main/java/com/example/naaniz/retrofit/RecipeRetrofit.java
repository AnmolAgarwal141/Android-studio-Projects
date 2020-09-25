package com.example.naaniz.retrofit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RecipeRetrofit {

    @SerializedName("results")
    @Expose
    private List<ResultRetrofit> results = null;

    public List<ResultRetrofit> getResults() {
        return results;
    }

    public void setResults(List<ResultRetrofit> results) {
        this.results = results;
    }
}
