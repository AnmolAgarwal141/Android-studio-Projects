package com.example.naaniz.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.naaniz.retrofit.AmountRetrofit;
import com.example.naaniz.retrofit.IngredientListRetrofit;
import com.example.naaniz.retrofit.IngredientRetrofit;
import com.example.naaniz.retrofit.QuantityUSRetrofit;
import com.example.naaniz.R;
import com.example.naaniz.retrofit.RecipePreparationRetrofit;
import com.example.naaniz.retrofit.RecipeRetrofit;
import com.example.naaniz.retrofit.RecipeStepsRetrofit;
import com.example.naaniz.retrofit.ResultRetrofit;
import com.example.naaniz.ShowRecipe;
import com.example.naaniz.UserRecipeIngredients;
import com.example.naaniz.interfaces.SpoonacularApi;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SearchFragment extends Fragment {

    private SearchView searchView;
    private TextView textView1, textView2, textView3;
    private SpoonacularApi api;
    private String id;
    private String title,des,ckng_time,serv;
    private Button button;

    private ArrayList<UserRecipeIngredients> a = new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        searchView = (SearchView) view.findViewById(R.id.search_view);
        textView1 = (TextView) view.findViewById(R.id.recipe);
        textView2 = (TextView) view.findViewById(R.id.recipe_ingredients);
        textView3 = (TextView) view.findViewById(R.id.recipe_steps);
       button = (Button)view.findViewById(R.id.detail_view);


       button.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent = new Intent(getActivity(), ShowRecipe.class);
               intent.putExtra("dish",searchView.getQuery().toString());
               intent.putExtra("parentRef","recipes");
               startActivity(intent);
           }
       });
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.spoonacular.com/recipes/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();

        api = retrofit.create(SpoonacularApi.class);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                getRecipe();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
    }

    public void getRecipe() {

        String name = searchView.getQuery().toString();
        button.setVisibility(View.VISIBLE);
        Call<RecipeRetrofit> call = api.getRecipe(name , "1", "156c2fb356c448d6bda0f10a8ec3d2d2");

        call.enqueue(new Callback<RecipeRetrofit>() {
            @Override
            public void onResponse(Call<RecipeRetrofit> call, Response<RecipeRetrofit> response) {
                if (!response.isSuccessful()) {
                    textView1.setText("Code: " + response.code());
                    return;
                }

                RecipeRetrofit recipe = response.body();
                List<ResultRetrofit> results = recipe.getResults();
                textView1.setText("");
                for (ResultRetrofit result : results) {
                    String content = "";
                    id = result.getId();

                    content += "Name: " + result.getName() + "\n";
                    content += "Servings: " + result.getServings() + " persons\n";
                    content += "Cooking Time: " + result.getCookingTime() + " min" + "\n";

                    title = result.getName();
                    serv = result.getServings();
                    ckng_time = result.getCookingTime();

                    getIngredients();

                    textView1.append(content);
                }

            }

            @Override
            public void onFailure(Call<RecipeRetrofit> call, Throwable t) {
                textView1.setText(t.getMessage());
            }
        });
        return;
    }


    public void setDatabase()
    {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference parentRef = database.getReference("recipes");
        DatabaseReference ref = parentRef.child(searchView.getQuery().toString()).getRef();

        ref.child("title").setValue(searchView.getQuery().toString());
        ref.child("servings").setValue(serv);
        ref.child("readyInMinutes").setValue(ckng_time);
        ref.child("instructions").setValue(des);
    }


    public void getIngredients() {

        Call<IngredientListRetrofit> call = api.getIngredients(id, "156c2fb356c448d6bda0f10a8ec3d2d2");
        call.enqueue(new Callback<IngredientListRetrofit>() {
            @Override
            public void onResponse(Call<IngredientListRetrofit> call, Response<IngredientListRetrofit> response) {
                if (!response.isSuccessful()) {
                    textView2.setText("Code: " + response.code());
                    return;
                }

                IngredientListRetrofit ingredientList = response.body();
                List<IngredientRetrofit> ingredients = ingredientList.getIngredients();

                textView2.setText("");
                int i=0;
                for (IngredientRetrofit ingredient : ingredients) {
                    AmountRetrofit amount = ingredient.getAmount();
                    QuantityUSRetrofit quantity = amount.getQuantityUS();

                    getMethod();
                    String content = "";
                    content += ingredient.getName() + " - " + quantity.getValue() + " " + quantity.getUnit() + "\n";

                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference parentRef = database.getReference("recipes");
                    DatabaseReference ref = parentRef.child(searchView.getQuery().toString()).getRef();
                    DatabaseReference ref2 = ref.child("ingredients").getRef();
                    DatabaseReference ref3 = ref2.child(String.valueOf(i)).getRef();
                    ref3.child("name").setValue(ingredient.getName());
                    ref3.child("qty").setValue(quantity.getValue());
                    i++;
                    textView2.append(content);

                }
            }

            @Override
            public void onFailure(Call<IngredientListRetrofit> call, Throwable t) {
                textView2.setText(t.getMessage());
            }
        });
        return;
    }

    public void getMethod() {

        Call<List<RecipePreparationRetrofit>> call = api.getMethod(id, "156c2fb356c448d6bda0f10a8ec3d2d2");

        call.enqueue(new Callback<List<RecipePreparationRetrofit>>() {
            @Override
            public void onResponse(Call<List<RecipePreparationRetrofit>> call, Response<List<RecipePreparationRetrofit>> response) {
                if (!response.isSuccessful()) {
                    textView3.setText("Code: " + response.code());
                    return;
                }

                List<RecipePreparationRetrofit> list = new ArrayList<>();
                list = response.body();

                textView3.setText("");
                for (RecipePreparationRetrofit recipe : list) {
                    List<RecipeStepsRetrofit> steps = recipe.getSteps();
                    for (RecipeStepsRetrofit step : steps) {
                        String content = "";
                        content += step.getStep() + "\n";
                        textView3.append(content);
                    }
                }
                des = textView3.getText().toString();
                setDatabase();
            }

            @Override
            public void onFailure(Call<List<RecipePreparationRetrofit>> call, Throwable t) {

            }
        });
        return;
    }
}
