package com.example.tripplanner;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class Tripplannermain extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tripplannermain);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_flight,R.id.navigation_Todo,R.id.navigation_moneymanager,R.id.navigation_Profile)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
    }

}
/*
<item
        android:id="@+id/navigation_moneymanager"
        android:icon="@drawable/ic_notifications_black_24dp"
        android:title="Money Manager" />
            <fragment
        android:id="@+id/navigation_moneymanager"
        android:name="com.example.tripplanner.ui.MoneyManager.MoneyManagerFragment"
        android:label="MoneyManager"
        tools:layout="@layout/fragment_moneymanager" />

 */