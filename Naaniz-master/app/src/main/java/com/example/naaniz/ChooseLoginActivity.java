package com.example.naaniz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ChooseLoginActivity extends AppCompatActivity {

    private Button buttonCustomer, buttonRestaurant;
    private TextView textViewSkip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_login);

        buttonCustomer = findViewById(R.id.intro_customer);
        buttonRestaurant = findViewById(R.id.intro_Restaurant);
        textViewSkip = findViewById(R.id.intro_skip);

        buttonCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ChooseLoginActivity.this, NewIntroScreenActivity.class);
                intent.putExtra("user", "customer");
                startActivity(intent);
            }
        });

        buttonRestaurant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ChooseLoginActivity.this, NewIntroScreenActivity.class);
                intent.putExtra("user", "restaurant");
                startActivity(intent);
            }
        });

        textViewSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ChooseLoginActivity.this, "  Skip", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
