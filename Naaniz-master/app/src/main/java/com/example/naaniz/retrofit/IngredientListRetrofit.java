package com.example.naaniz.retrofit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class IngredientListRetrofit {

    @SerializedName("ingredients")
    @Expose
    private List<IngredientRetrofit> ingredients = null;

    public List<IngredientRetrofit> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<IngredientRetrofit> ingredients) {
        this.ingredients = ingredients;
    }
}
