package com.example.naaniz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;

import com.example.naaniz.adapters.SwipeAdapter;
import com.tbuonomo.viewpagerdotsindicator.SpringDotsIndicator;

import java.util.Locale;

public class Onboarding extends AppCompatActivity {

    ViewPager viewPager;
    SwipeAdapter adapter;
    SpringDotsIndicator springDotsIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences prefs = getSharedPreferences("NAANIZ_PREFERENCES",MODE_PRIVATE);
        boolean prevStarted = prefs.getBoolean("lang_prev_started",false);
        if(prevStarted)
        {
            String language = prefs.getString("language_pref","en");
            Resources res = this.getResources();
            DisplayMetrics displayMetrics = res.getDisplayMetrics();
            Configuration configuration = res.getConfiguration();
            configuration.setLocale(new Locale(language));
            res.updateConfiguration(configuration,displayMetrics);
            startActivity(new Intent(Onboarding.this,IntroScreen.class));
            finish();
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding);

        viewPager = (ViewPager) findViewById(R.id.view_pager);
        adapter = new SwipeAdapter(this);

        viewPager.setAdapter(adapter);

        springDotsIndicator = (SpringDotsIndicator) findViewById(R.id.spring_dots_indicator);
        springDotsIndicator.setViewPager(viewPager);

        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Onboarding.this, Language.class);
                startActivity(intent);
            }
        });
    }


}
