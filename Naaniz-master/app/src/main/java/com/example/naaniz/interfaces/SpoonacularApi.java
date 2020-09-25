package com.example.naaniz.interfaces;

import com.example.naaniz.retrofit.IngredientListRetrofit;
import com.example.naaniz.retrofit.RecipePreparationRetrofit;
import com.example.naaniz.retrofit.RecipeRetrofit;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface SpoonacularApi {

    @GET("search")
    Call<RecipeRetrofit> getRecipe(
            @Query("query") String name,
            @Query("number") String number,
            @Query("apiKey") String key
    );

    @GET("{id}/ingredientWidget.json")
    Call<IngredientListRetrofit> getIngredients(
            @Path("id") String id,
            @Query("apiKey") String key
    );

    @GET("{id}/analyzedInstructions")
    Call<List<RecipePreparationRetrofit>> getMethod(
            @Path("id") String id,
            @Query("apiKey") String key
    );
}
