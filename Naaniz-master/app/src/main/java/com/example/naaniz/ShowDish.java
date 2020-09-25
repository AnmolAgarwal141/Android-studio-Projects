package com.example.naaniz;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.naaniz.models.Dish;
import com.example.naaniz.models.Restaurant;
import com.example.naaniz.views.RestaurantView;

import java.util.List;

public class ShowDish extends AppCompatActivity  {

    private TextView restaurant_name;
    private LinearLayout listOfDishes;
    private Restaurant restaurant;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show__dish);

        initViews();
    }

    private void initViews(){
        restaurant_name = findViewById(R.id.Restaurant);
        listOfDishes = findViewById(R.id.listOfDishes);

        restaurant = (Restaurant) getIntent().getSerializableExtra("restaurant");

        restaurant_name.setText(restaurant.getName());

        List<Dish> dishes = restaurant.getDishes();
        for(Dish d : dishes) {
            Log.d("Restaurant_details","Dish : "+d.getName()+" : "+d.getTaste());
            addCard(d);
        }
    }

    private void addCard(Dish dish) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        RestaurantView newRestaurant = new RestaurantView(this.getBaseContext());
        newRestaurant.setLayoutParams(params);
        newRestaurant.setName(dish.getName());
        newRestaurant.setPrice(dish.getTaste());
        newRestaurant.setRating(dish.getPrice());
        newRestaurant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openRecipe(dish.getName());
            }
        });
        listOfDishes.addView(newRestaurant);
    }

    private void openRecipe(String dish) {
        final String[] recipes = {
                "User Recipe", "YouTube Recipe"
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(ShowDish.this);
        builder.setTitle("View Recipe Options");
        builder.setItems(recipes, new DialogInterface.OnClickListener() {@
                Override
        public void onClick(DialogInterface dialog, int which) {
            if ("User Recipe".equals(recipes[which])) {
                Intent intent = new Intent(ShowDish.this, ShowRecipe.class);
                intent.putExtra("parentRef","recipes");
                Log.d("addingCard:","inside show dish : "+getIntent().getStringExtra("phone"));
                intent.putExtra("phone",getIntent().getStringExtra("phone"));
                intent.putExtra("dish", dish);
                startActivity(intent);
            } else if ("YouTube Recipe".equals(recipes[which])) {
                Intent intent = new Intent(ShowDish.this, YouTubeShowRecipe.class);
                intent.putExtra("query", dish);
                startActivity(intent);
            }
        }
        });
        builder.show();
        /*Intent intent = new Intent(this, Show_Recipe.class);
        intent.putExtra("parentRef","recipes");
        intent.putExtra("dish", dish);
        startActivity(intent);*/
    }
}
