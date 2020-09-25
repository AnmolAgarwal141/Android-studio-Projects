package com.example.naaniz.retrofit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RecipePreparationRetrofit {

    @SerializedName("steps")
    @Expose
    private List<RecipeStepsRetrofit> steps = null;

    public List<RecipeStepsRetrofit> getSteps() {
        return steps;
    }

    public void setSteps(List<RecipeStepsRetrofit> steps) {
        this.steps = steps;
    }
}
