package com.example.naaniz;

import com.google.gson.annotations.SerializedName;

public class SignUpResult {
    @SerializedName("formatted_address")
    String address;
    @SerializedName("place_id")
    String id;
    public SignUpResult(String address,String id)
    {
        this.address = address;
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public String getId() {
        return id;
    }
}
