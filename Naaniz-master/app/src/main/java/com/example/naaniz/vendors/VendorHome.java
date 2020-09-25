package com.example.naaniz.vendors;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.naaniz.AddRecipe;
import com.example.naaniz.fragments.HomeFragment;
import com.example.naaniz.fragments.ProfileFragment;
import com.example.naaniz.R;
import com.example.naaniz.fragments.RedirectionFragment;
import com.example.naaniz.fragments.SearchFragment;
import com.example.naaniz.fragments.UserRecipesFragment;
import com.example.naaniz.fragments.YouTubeFragment;
import com.google.android.material.navigation.NavigationView;

public class VendorHome extends AppCompatActivity  implements UserRecipesFragment
        .OnClickRecipe {
    private ActionBarDrawerToggle toggle;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        drawerLayout = findViewById(R.id.drawer_layout);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("");
        toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        if(savedInstanceState==null) {
            Bundle bundle = new Bundle();
            toolbar.setTitle("Profile");
            bundle.putString("name",getIntent().getStringExtra("name"));
            bundle.putString("phone",getIntent().getStringExtra("phone"));
            ProfileFragment profileFragment = new ProfileFragment();
            profileFragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,profileFragment).commit();
            drawerLayout.closeDrawers();

        }
        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(item -> {
            switch(item.getItemId()) {
                case R.id.profile : {
                    Bundle bundle = new Bundle();
                    toolbar.setTitle("Profile");
                    bundle.putString("name",getIntent().getStringExtra("name"));
                    bundle.putString("phone",getIntent().getStringExtra("phone"));
                    VendorProfileFragment profileFragment = new VendorProfileFragment();
                    profileFragment.setArguments(bundle);
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,profileFragment).commit();
                    Toast.makeText(VendorHome.this,"Profile Selected",Toast.LENGTH_SHORT).show(); break;
                }
                case R.id.home : {
                    Bundle bundle = new Bundle();
                    toolbar.setTitle("Home");
                    bundle.putString("name",getIntent().getStringExtra("name"));
                    bundle.putString("phone",getIntent().getStringExtra("phone"));
                    HomeFragment homeFragment = new HomeFragment();
                    homeFragment.setArguments(bundle);
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,homeFragment).commit();
                    break;
                }
                case R.id.user_recipes : {
                    Bundle bundle = new Bundle();
                    toolbar.setTitle("User Recipies");
                    bundle.putString("name",getIntent().getStringExtra("name"));
                    bundle.putString("phone",getIntent().getStringExtra("phone"));
                    UserRecipesFragment userRecipesFragment = new UserRecipesFragment();
                    userRecipesFragment.setArguments(bundle);
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,userRecipesFragment).commit();
                    break;
                }
                case R.id.log_out : {
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
                }
                case R.id.redirect : {
                    RedirectionFragment redirectionFragment = new RedirectionFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,redirectionFragment).commit();
                    break;
                }
                case R.id.find_recipe : {
                    SearchFragment searchFragment = new SearchFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, searchFragment).commit();
                    break;
                }
                case R.id.youtube : {
                    YouTubeFragment youTubeFragment = new YouTubeFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, youTubeFragment).commit();
                }
            }
            return true;
        });
    }

    @Override
    public void onRecipePress() {
        Intent intent = new Intent(VendorHome.this, AddRecipe.class);
        intent.putExtra("name",getIntent().getStringExtra("name"));
        intent.putExtra("phone",getIntent().getStringExtra("phone"));
        Log.i("213123",getIntent().getStringExtra("name") );
        startActivity(intent);
    }
}

