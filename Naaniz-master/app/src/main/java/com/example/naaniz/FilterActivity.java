package com.example.naaniz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.naaniz.fragments.HomeFragment;
import com.example.naaniz.models.Filter;

public class FilterActivity extends AppCompatActivity {
    private TextView prices;
    private TextView taste;
    private LinearLayout choices;
    private Filter filter;
    private TextView clear;
    private TextView close;
    private Button apply;
    private int isPriceClicked = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        prices = findViewById(R.id.prices);
        taste = findViewById(R.id.taste);
        choices = findViewById(R.id.choices);
        filter = (Filter) getIntent().getSerializableExtra("filter");
        clear = findViewById(R.id.clear);
        apply = findViewById(R.id.apply);

        initListeners();
    }

    private void initListeners() {

        View.OnClickListener pricesChoicesClick = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button b = (Button) v;
                if (filter.choices_prices.get(filter.prices.indexOf(b.getText().toString()))) {
                    b.setBackgroundColor(Color.parseColor("#aaaaaa"));
                } else {
                    b.setBackgroundColor(Color.parseColor("#ffffff"));
                }
                filter.choices_prices.set(filter.prices.indexOf(b.getText().toString()), !filter.choices_prices.get(filter.prices.indexOf(b.getText().toString())));
            }
        };

        prices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isPriceClicked!=1) {
                    choices.removeAllViews();
                    for (String price : filter.getPrices()) {
                        Button b = new Button(getApplicationContext());
                        if(filter.choices_prices.get(filter.prices.indexOf(price)))
                            b.setBackgroundColor(Color.parseColor("#ffffff"));
                        else
                            b.setBackgroundColor(Color.parseColor("#aaaaaa"));

                        b.setText(price);
                        b.setOnClickListener(pricesChoicesClick);
                        choices.addView(b);
                    }
                }
                isPriceClicked = 1;
            }
        });

        View.OnClickListener tasteChoicesClick = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button b = (Button) v;
                if (filter.choices_tastes.get(filter.tastes.indexOf(b.getText().toString()))) {
                    b.setBackgroundColor(Color.parseColor("#aaaaaa"));
                } else {
                    b.setBackgroundColor(Color.parseColor("#ffffff"));
                }
                filter.choices_tastes.set(filter.tastes.indexOf(b.getText().toString()), !filter.choices_tastes.get(filter.tastes.indexOf(b.getText().toString())));
            }
        };

        taste.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isPriceClicked!=2) {
                    choices.removeAllViews();
                    for (String taste : filter.getTastes()) {
                        Button b = new Button(getBaseContext());
                        if(filter.choices_tastes.get(filter.tastes.indexOf(taste)))
                            b.setBackgroundColor(Color.parseColor("#ffffff"));
                        else
                            b.setBackgroundColor(Color.parseColor("#aaaaaa"));
                        b.setText(taste);
                        b.setOnClickListener(tasteChoicesClick);
                        choices.addView(b);
                    }
                }
                isPriceClicked = 2;
            }
        });

        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //resultCode = 1;
                Intent intent = new Intent(FilterActivity.this, HomeFragment.class);
                intent.putExtra("filter",filter);
                setResult(1,intent);
                finish();
            }
        });

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filter.resetFilters();
                choices.removeAllViews();
                isPriceClicked = 0;
            }
        });


    }

    @Override
    public void onBackPressed(){
        Intent intent = new Intent(FilterActivity.this, HomeFragment.class);
        intent.putExtra("filter",filter);
        setResult(0,intent);
        finish();
    }
}