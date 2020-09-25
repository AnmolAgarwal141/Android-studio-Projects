package com.example.naaniz.newhome;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.naaniz.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class NewHomeVendorList extends AppCompatActivity {
    private String currentCity;
    private LinearLayout vendorList;
    Button backButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_home_vendor_list);

        backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        Intent intent = getIntent();
        currentCity = intent.getStringExtra("city");
        vendorList = findViewById(R.id.new_vendor_list_linear);
        addVendors();
    }
    public void addVendors() {
        DatabaseReference database = FirebaseDatabase.getInstance().getReference("vendors");
        database.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot data : dataSnapshot.getChildren()) {
                    boolean flag = false;
                    if(data.child("city").getValue().toString().equals(currentCity)) {
                        addCard(data.child("name").getValue().toString());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    public void addCard(String s)  {
        TextView textView = new TextView(this);
        CardView card = new CardView(this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT);
        textView.setText(s);
        textView.setTextSize(24);
        textView.setPadding(20,20,20,20);
        textView.setLayoutParams(params);
        card.setLayoutParams(params);
        card.setBackground(Drawable.createFromPath("?android:attr/windowBackground"));
        card.addView(textView);
        vendorList.addView(card);
    }
}
