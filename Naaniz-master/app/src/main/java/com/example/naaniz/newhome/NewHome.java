package com.example.naaniz.newhome;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.naaniz.MenuActivity;
import com.example.naaniz.R;
import com.example.naaniz.RestaurantProfile;
import com.example.naaniz.fragments.UserRecipesFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

public class NewHome extends AppCompatActivity {
    private CardView userRecipeCard;
    private CardView vendorListCard;
    private CardView trendingRestaurantCard;
    private CardView trendingFindRecipes;
    private TextView cityText;
    private CardView trendingVideos;
    private BottomNavigationView botMenu;
    private LinearLayout topRestaurantLayout;
    private LinearLayout dishRestaurantLayout;
    private ProgressBar restaurantLoader;
    private ProgressBar recipeLoader;
    private String currentCity = "hyderabad";
    private String currentArea = "Himayath Nagar";
    private ArrayList<String> restaurantList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_home);
        userRecipeCard = findViewById(R.id.new_home_user_recipes);
        vendorListCard = findViewById(R.id.new_home_vendor_list);
        trendingRestaurantCard = findViewById(R.id.new_home_restaurants);
        trendingFindRecipes = findViewById(R.id.new_home_find_recipes);
        trendingVideos = findViewById(R.id.new_home_trending);
        cityText = findViewById(R.id.new_home_location_text);
        cityText.setText("Hyderabad, Himayath Nagar");
        dishRestaurantLayout = findViewById(R.id.new_home_dish_layout);
        topRestaurantLayout = findViewById(R.id.new_home_restaurant_layout);
        botMenu = findViewById(R.id.bot_menu);
        setListeners();
    }

    public void setListeners() {
        userRecipeCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserRecipesFragment fragment = new UserRecipesFragment();
                Bundle bundle = new Bundle();
                bundle.putString("name", getIntent().getStringExtra("name"));
                bundle.putString("phone", getIntent().getStringExtra("phone"));
                UserRecipesFragment userRecipesFragment = new UserRecipesFragment();
                userRecipesFragment.setArguments(bundle);
                Intent intent = new Intent(NewHome.this, NewFragmentContainerActivity.class);
                intent.putExtra("phone", getIntent().getStringExtra("phone"));
                intent.putExtra("fragment", "userRecipes");
                intent.putExtra("name",getIntent().getStringExtra("name"));
                startActivity(intent);
            }
        });
        vendorListCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NewHome.this, NewHomeVendorList.class);
                intent.putExtra("city", currentCity);
                startActivity(intent);
            }
        });
        cityText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(NewHome.this, NewHomeLocationSelector.class), 1);
            }
        });
        trendingRestaurantCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NewHome.this, NewFragmentContainerActivity.class);
                intent.putExtra("phone", getIntent().getStringExtra("phone"));
                intent.putExtra("fragment", "homeFragment");
                startActivity(intent);
            }
        });
        trendingFindRecipes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(NewHome.this, NewFragmentContainerActivity.class);             ///Find Recipe Activity
//                intent.putExtra("phone", getIntent().getStringExtra("phone"));
//                intent.putExtra("name", getIntent().getStringExtra("name"));
//                intent.putExtra("fragment", "searchFragment");
//                startActivity(intent);

                Intent intent = new Intent(NewHome.this, RestaurantProfile.class);
                intent.putExtra("phone", getIntent().getStringExtra("phone"));
                startActivity(intent);                                                                      ///Restaurent Home Activity
            }
        });
        trendingVideos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NewHome.this, NewFragmentContainerActivity.class);
                intent.putExtra("phone", getIntent().getStringExtra("phone"));
                intent.putExtra("fragment", "youtubeFragment");
                startActivity(intent);
            }
        });
        botMenu.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.newhome_home: {

                        break;
                    }
                    case R.id.newhome_custom: {
                        Intent intent = new Intent(NewHome.this, MenuActivity.class);
                        intent.putExtra("phone", getIntent().getStringExtra("phone"));
                        startActivity(intent);
                        break;
                    }
                    case R.id.newhome_profile: {
                        Intent intent = new Intent(NewHome.this, NewFragmentContainerActivity.class);
                        intent.putExtra("phone", getIntent().getStringExtra("phone"));
                        intent.putExtra("fragment", "profileFragment");
                        startActivity(intent);
                        break;
                    }
                    case R.id.newhome_logout: {
                        SharedPreferences sharedPreferences = getSharedPreferences("NAANIZ_PREFERENCES",MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putBoolean("current_log_in",Boolean.FALSE);
                        editor.putString("current_phone_number",null);
                        editor.apply();
                        Intent i = getBaseContext().getPackageManager().
                                getLaunchIntentForPackage(getBaseContext().getPackageName());
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        finish();
                        startActivity(i);
                        break;
                    }
                }
                return false;
            }
        });
        addLoadingViews();
        addRestaurantCards();
    }

    public void addLoadingViews() {
        restaurantLoader = new ProgressBar(this);
        restaurantLoader.setIndeterminate(true);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        restaurantLoader.setLayoutParams(params);
        topRestaurantLayout.addView(restaurantLoader);
        recipeLoader = new ProgressBar(this);
        recipeLoader.setIndeterminate(true);
        recipeLoader.setLayoutParams(params);
        dishRestaurantLayout.addView(recipeLoader);
    }

    public void addRestaurantCards() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("restaurants").child("hyderabad").child("Himayath Nagar").getRef();
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot names : dataSnapshot.getChildren()) {
                    Log.d("adding", "name: " + names.getKey().toString());
                    restaurantList.add(names.getKey());
                }
                Log.d("adding", "size : " + restaurantList.size());
                Collections.shuffle(restaurantList);
                topRestaurantLayout.removeView(restaurantLoader);
                for (int i = 0; i < restaurantList.size(); i++) {
                    if (i == 5)
                        break;
                    Log.d("listRes", restaurantList.get(i));
                    addCard(restaurantList.get(i));
                }
                addRecipeCards();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void addRecipeCards() {
//        Random random = new Random();
//        int randomChoice = random.nextInt(5);
//        String restaurant = restaurantList.get(randomChoice);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("restaurants").child("hyderabad").child("trending").getRef();
        ArrayList<String> dishes = new ArrayList<>();
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dish : dataSnapshot.getChildren()) {
                    dishes.add(dish.getKey());
                    Log.d("TAGAT", dish.getKey() + " : " + dish.getValue().toString());
                    addDishCard(dish.getKey(), dish.getValue().toString());
                }
                //Collections.shuffle(dishes);
                dishRestaurantLayout.removeView(recipeLoader);

//                      addDishCard("Biryani");
//                    addDishCard("Shawarma Wraps");
//                    addDishCard("Kebabs");
//                    addDishCard("Curries");
//                    addDishCard("Dosas");
//
//                    Log.d("listRes", dishes.get(i));
//                    addDishCard(dishes.get(i));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void addDishCard(String title, String query) {
        Log.d("adding", "adding card " + title);
        CardView newCard = new CardView(this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        TextView textView = new TextView(this);
        textView.setLayoutParams(params);
        textView.setPadding(40, 60, 40, 60);
        newCard.setLayoutParams(params);
        newCard.setElevation(10);
        newCard.setRadius(30);
        newCard.setCardElevation(10);
        newCard.setUseCompatPadding(true);
        textView.setTextSize(18);
        textView.setTextColor(Color.WHITE);
        textView.setGravity(Gravity.CENTER_VERTICAL);
        textView.setBackgroundColor(Color.parseColor("#FFFF8800"));
        textView.setText(title);
        newCard.setPadding(60, 10, 60, 10);
        newCard.addView(textView);
        newCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NewHome.this, NewFragmentContainerActivity.class);
                intent.putExtra("phone", getIntent().getStringExtra("phone"));
                intent.putExtra("query", query);
                intent.putExtra("fragment", "homeFragment");
                startActivity(intent);
            }
        });
        dishRestaurantLayout.addView(newCard);
    }

    public void addCard(String title) {
        Log.d("adding", "adding card " + title);
        CardView newCard = new CardView(this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
        TextView textView = new TextView(this);
        textView.setLayoutParams(params);
        textView.setPadding(40, 60, 40, 60);
        newCard.setLayoutParams(params);
        newCard.setCardElevation(10);
        newCard.setRadius(30);
        newCard.setUseCompatPadding(true);
        textView.setTextSize(18);
        textView.setTextColor(Color.WHITE);
        textView.setGravity(Gravity.CENTER_VERTICAL);
        textView.setBackgroundColor(Color.parseColor("#FFFF8800"));
        textView.setText(title);
        newCard.setPadding(60, 10, 60, 10);
        newCard.addView(textView);
        topRestaurantLayout.addView(newCard);
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
