package com.example.naaniz;

import android.content.res.Resources;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Results {
    @SerializedName("results")
    ArrayList<SignUpResult> results;
    @SerializedName("status")
    String status;
    public Results(ArrayList<SignUpResult> results,String status) {
        this.results = results;
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public ArrayList<SignUpResult> getResults() {
        return results;
    }
}
