package com.example.naaniz.retrofit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AmountRetrofit {

    @SerializedName("us")
    @Expose
    private QuantityUSRetrofit quantityUS;

    public QuantityUSRetrofit getQuantityUS() {
        return quantityUS;
    }

    public void setQuantityUS(QuantityUSRetrofit quantityUS) {
        this.quantityUS = quantityUS;
    }
}
