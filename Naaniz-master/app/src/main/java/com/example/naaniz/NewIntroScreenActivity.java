package com.example.naaniz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.Objects;

public class NewIntroScreenActivity extends AppCompatActivity {

    private Button buttonSignUp, buttonLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_intro_screen);

        buttonLogin = findViewById(R.id.intro_login_button);
        buttonSignUp = findViewById(R.id.intro_signup_button);

        if (Objects.equals(getIntent().getStringExtra("user"), "customer")) {
            buttonLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(NewIntroScreenActivity.this, SignUpCust1.class);
                    startActivity(intent);
                }
            });

            buttonSignUp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(NewIntroScreenActivity.this, SignUpCust2.class);
                    startActivity(intent);
                }
            });
        }

        if (Objects.equals(getIntent().getStringExtra("user"), "restaurant")) {
            buttonLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(NewIntroScreenActivity.this, SignUpRest1.class);
                    startActivity(intent);
                }
            });

            buttonSignUp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(NewIntroScreenActivity.this, SignUpRest2.class);
                    startActivity(intent);
                }
            });
        }
    }
}
