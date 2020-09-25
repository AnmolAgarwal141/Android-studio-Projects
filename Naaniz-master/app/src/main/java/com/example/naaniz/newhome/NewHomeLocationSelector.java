package com.example.naaniz.newhome;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.naaniz.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class NewHomeLocationSelector extends AppCompatActivity {
    private TextView locationHeading;
    private LinearLayout locationList;
    private String city;
    private String area;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_home_location_selector);
        locationHeading = findViewById(R.id.newLocationSelector_heading);
        locationHeading.setText("Please Choose City: ");
        locationList = findViewById(R.id.newLocationList);
        addCityCards();
    }
    private void addCityCards() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference cityRef =  database.getReference("restaurants");
        ArrayList<String> cities = new ArrayList<>();
        cityRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot data : dataSnapshot.getChildren()) {
                    cities.add(data.getKey());
                }
                int[] favicon = {R.drawable.delhi_favicon, R.drawable.hyderabad_favicon, R.drawable.lucknow_favicon, R.drawable.bangalore_favicon};
                int i = 0;
                for(String s : cities) {
                    CardView cardView = new CardView(getBaseContext());
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                    TextView textView = new TextView(getBaseContext());
                    ImageView imageView = new ImageView(getBaseContext());
                    imageView.setImageResource(favicon[i]);
                    ++i;
                    imageView.setPadding(100, 200, 100, 50);
                    cardView.setPadding(60, 50, 60, 10);
                    cardView.setRadius(20);
                    textView.setLayoutParams(params);
                    textView.setPadding(0, 50, 0, 50);
                    textView.setGravity(Gravity.CENTER_HORIZONTAL);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        textView.setTextAppearance(R.style.cities);
                    }
                    textView.setText(s.toUpperCase());
                    cardView.setLayoutParams(params);
                    cardView.setUseCompatPadding(true);
                    cardView.addView(imageView);
                    cardView.addView(textView);
                    cardView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            city = s;
                            setCityAreas(s);
                        }
                    });
                    locationList.addView(cardView);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    private void setCityAreas(String s) {
        locationList.removeAllViews();
        locationList.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
        locationHeading.setText(s.toUpperCase());
        locationList.setPadding(50, 0, 50, 0);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
        ImageView imageView = new ImageView(getBaseContext());
        int para = R.drawable.delhi_favicon;
        switch (s.toLowerCase()) {
            case "delhi" :
                para = R.drawable.delhi_favicon;
                break;
            case "lucknow" :
                para = R.drawable.lucknow_favicon;
                break;
            case "hyderabad" :
                para = R.drawable.hyderabad_favicon;
                break;
            case "bangalore" :
                para = R.drawable.bangalore_favicon;
                break;
        }
        imageView.setImageResource(para);
        imageView.setLayoutParams(params);
        imageView.setPadding(0, 0, 0, 100);
        locationList.addView(imageView);
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("restaurants").child(s).getRef();
        ArrayList<String> areas = new ArrayList<>();
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot area : dataSnapshot.getChildren()) {
                        areas.add(area.getKey());
                }
                for(String a : areas) {
                    CardView cardView = new CardView(getBaseContext());
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                    TextView textView = new TextView(getBaseContext());
                    textView.setLayoutParams(params);
                    cardView.setPadding(100, 20, 100, 20);
                    textView.setPadding(50, 50, 50, 50);
                    textView.setText(a);
                    textView.setTextSize(28);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        textView.setTextAppearance(R.style.areas);
                    }
                    cardView.setRadius(10);
                    cardView.setLayoutParams(params);
                    cardView.setUseCompatPadding(true);
                    cardView.addView(textView);
                    cardView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            area=a;
                            returnValues();
                        }
                    });
                    locationList.addView(cardView);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    private void returnValues() {
        Intent result = new Intent();
        result.putExtra("select_city", city);
        result.putExtra("select_area",area);
        setResult(Activity.RESULT_OK,result);
        finish();
    }
}
