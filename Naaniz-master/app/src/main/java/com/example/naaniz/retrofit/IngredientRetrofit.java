package com.example.naaniz.retrofit;

import com.example.naaniz.retrofit.AmountRetrofit;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class IngredientRetrofit {

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("amount")
    @Expose
    private AmountRetrofit amount;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public AmountRetrofit getAmount() {
        return amount;
    }

    public void setAmount(AmountRetrofit amount) {
        this.amount = amount;
    }
}
