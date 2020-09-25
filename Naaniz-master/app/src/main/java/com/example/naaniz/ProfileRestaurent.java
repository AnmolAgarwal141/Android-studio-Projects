package com.example.naaniz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;

import com.example.naaniz.newhome.NewFragmentContainerActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ProfileRestaurent extends AppCompatActivity {

    private BottomNavigationView botMenu;
    RelativeLayout signout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_restaurent);

        signout = findViewById(R.id.sign_out);
        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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
        });

        botMenu = findViewById(R.id.bot_menu);
        botMenu.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.newhome_home: {

                        finish();
                        break;
                    }
                    case R.id.newhome_custom: {

                        startActivity(new Intent(ProfileRestaurent.this, Shopping.class));
                        break;
                    }
                    case R.id.newhome_profile: {
                        Intent intent = new Intent(ProfileRestaurent.this, NewFragmentContainerActivity.class);
                        intent.putExtra("phone", getIntent().getStringExtra("phone"));
                        intent.putExtra("fragment", "profileFragment");
                        startActivity(intent);
                        break;
                    }
                    case R.id.newhome_logout: {

                        break;
                    }
                }
                return false;
            }
        });
    }
}
