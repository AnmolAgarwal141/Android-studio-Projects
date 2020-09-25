package com.example.naaniz.newhome;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.naaniz.AddRecipe;
import com.example.naaniz.fragments.HomeFragment;
import com.example.naaniz.fragments.ProfileFragment;
import com.example.naaniz.R;
import com.example.naaniz.fragments.SearchFragment;
import com.example.naaniz.fragments.UserRecipesFragment;
import com.example.naaniz.fragments.YouTubeFragment;

public class NewFragmentContainerActivity extends AppCompatActivity
        implements UserRecipesFragment.OnClickRecipe{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_fragment_container);
        Intent intent = getIntent();
        String type = intent.getStringExtra("fragment");
        switch(type) {
            case "userRecipes" : {
                Bundle bundle = new Bundle();
                bundle.putString("name",getIntent().getStringExtra("name"));
                bundle.putString("phone",getIntent().getStringExtra("phone"));
                UserRecipesFragment userRecipesFragment = new UserRecipesFragment();
                userRecipesFragment.setArguments(bundle);
                getSupportFragmentManager().beginTransaction().replace(R.id.new_activity_fragment_container,userRecipesFragment).commit();
                break;
            }
            case "profileFragment" : {
                Bundle bundle = new Bundle();
                bundle.putString("name",getIntent().getStringExtra("name"));
                bundle.putString("phone",getIntent().getStringExtra("phone"));
                ProfileFragment profileFragment = new ProfileFragment();
                profileFragment.setArguments(bundle);
                getSupportFragmentManager().beginTransaction().replace(R.id.new_activity_fragment_container,profileFragment).commit();
                break;
            }
            case "homeFragment" : {
                Bundle bundle = new Bundle();
                bundle.putString("name",getIntent().getStringExtra("name"));
                bundle.putString("phone",getIntent().getStringExtra("phone"));
                bundle.putString("query",((getIntent().getStringExtra("query")==null)) ? "" : getIntent().getStringExtra("query"));
                HomeFragment homeFragment = new HomeFragment();
                homeFragment.setArguments(bundle);
                getSupportFragmentManager().beginTransaction().replace(R.id.new_activity_fragment_container, homeFragment).commit();
                break;
            }
            case "youtubeFragment" : {
                YouTubeFragment youTubeFragment = new YouTubeFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.new_activity_fragment_container, youTubeFragment).commit();
                break;
            }
            case "searchFragment" : {
                SearchFragment searchFragment = new SearchFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.new_activity_fragment_container, searchFragment).commit();
                break;
            }
            default: {
                Toast.makeText(this, "Error occurred", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onRecipePress() {
        Intent intent = new Intent(NewFragmentContainerActivity.this, AddRecipe.class);
        intent.putExtra("name",getIntent().getStringExtra("name"));
        intent.putExtra("phone",getIntent().getStringExtra("phone"));
        Log.i("phone_no",getIntent().getStringExtra("phone") );
        startActivity(intent);
    }
}
