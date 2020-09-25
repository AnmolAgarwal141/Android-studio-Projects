package com.example.naaniz;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.naaniz.newhome.NewFragmentContainerActivity;
import com.example.naaniz.newhome.NewHomeLocationSelector;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class RestaurantProfile extends AppCompatActivity {

    private BottomNavigationView botMenu;
    private CardView trendingVideos;
    private TextView cityText;
    private LinearLayout orders, nearMe, menu, recipes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurent_profile);

        menu = findViewById(R.id.menu);
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(RestaurantProfile.this, MenuActivity.class);
                intent.putExtra("phone", getIntent().getStringExtra("phone"));
                startActivity(intent);
            }
        });

        nearMe = findViewById(R.id.near_me);
        nearMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RestaurantProfile.this, NearMe.class));
            }
        });

        orders = findViewById(R.id.my_orders);
        orders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(RestaurantProfile.this, MyOrders.class));
            }
        });

        recipes = findViewById(R.id.my_recipies);
        recipes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RestaurantProfile.this, NewFragmentContainerActivity.class);
                intent.putExtra("phone", getIntent().getStringExtra("phone"));
                intent.putExtra("fragment", "userRecipes");
                startActivity(intent);
            }
        });


        cityText = findViewById(R.id.new_home_location_text);
        cityText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(RestaurantProfile.this, NewHomeLocationSelector.class), 1);
            }
        });

        trendingVideos = findViewById(R.id.youtube_tranding);
        trendingVideos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RestaurantProfile.this, NewFragmentContainerActivity.class);
                intent.putExtra("phone", getIntent().getStringExtra("phone"));
                intent.putExtra("fragment", "youtubeFragment");
                startActivity(intent);
            }
        });

        botMenu = findViewById(R.id.bot_menu);
        botMenu.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.newhome_home: {

                        break;
                    }
                    case R.id.newhome_custom: {

                        startActivity(new Intent(RestaurantProfile.this, Shopping.class));
                        break;
                    }
                    case R.id.newhome_profile: {
                        Intent intent = new Intent(RestaurantProfile.this, NewFragmentContainerActivity.class);
                        intent.putExtra("phone", getIntent().getStringExtra("phone"));
                        intent.putExtra("fragment", "profileFragment");
                        startActivity(intent);
                        break;
                    }
                    case R.id.newhome_logout: {

                        Intent intent = new Intent(RestaurantProfile.this, ProfileRestaurent.class);
                        intent.putExtra("phone", getIntent().getStringExtra("phone"));
                        startActivity(intent);
                        break;
                    }
                }
                return false;
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK && data != null) {
                cityText.setText(data.getStringExtra("select_city").substring(0, 1).toUpperCase() + data.getStringExtra("select_city").substring(1) + "," + data.getStringExtra("select_area"));
            }
        }
    }
}
