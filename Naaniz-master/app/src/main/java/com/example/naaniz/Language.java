package com.example.naaniz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;

import java.util.Locale;

public class Language extends AppCompatActivity {
    private Button englishButton;
    private Button hindiButton;
    private Button gujaratiButton;
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
            startActivity(new Intent(Language.this,IntroScreen.class));
            finish();
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language);
        englishButton = findViewById(R.id.english_button);
        hindiButton = findViewById(R.id.hindi_button);
        gujaratiButton = findViewById(R.id.gujarati_button);
        englishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setLocale("en");
                finish();
                startActivity(new Intent(Language.this, IntroScreen.class));
                finish();
            }
        });
        hindiButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setLocale("hi");
                finish();
                startActivity(new Intent(Language.this, IntroScreen.class));
                finish();
            }
        });
        gujaratiButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setLocale("gu");
                finish();
                startActivity(new Intent(Language.this, IntroScreen.class));
                finish();
            }
        });
    }
    public void setLocale(String language)
    {
        Resources res=this.getResources();
        DisplayMetrics dm=res.getDisplayMetrics();
        android.content.res.Configuration conf=res.getConfiguration();
        conf.setLocale(new Locale(language));
        res.updateConfiguration(conf,dm);
        SharedPreferences sharedPreferences = getSharedPreferences("NAANIZ_PREFERENCES",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("current_log_in",Boolean.FALSE);
        editor.putString("current_phone_number",null);
        editor.putString("language_pref",language);
        editor.putBoolean("lang_prev_started",Boolean.TRUE);
        editor.apply();
    }
}
