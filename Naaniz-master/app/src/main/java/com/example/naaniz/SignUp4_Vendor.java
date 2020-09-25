package com.example.naaniz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.naaniz.models.User;
import com.example.naaniz.models.Vendor;
import com.example.naaniz.vendors.VendorHome;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SignUp4_Vendor extends AppCompatActivity {

    private String name, phone, foodPref;
    private Double lat, lng;
    private ArrayList<String> meatList;
    private ArrayList<String> bakeryList;
    private ArrayList<String> groceryList;
    private ArrayList<String> vegetablesList;
    private ArrayList<String> dairyList;
    private int a = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_sign_up4__vendor);
        Intent intent = getIntent();
        ArrayList<String> category_list = intent.getStringArrayListExtra("category_list");
        TextView textView = (TextView) findViewById(R.id.txt2);
        Bundle args = intent.getBundleExtra("BUNDLE");
        meatList = intent.getStringArrayListExtra("meat_list");
        bakeryList = intent.getStringArrayListExtra("bakery_list");
        groceryList =intent.getStringArrayListExtra("grocery_list");
        vegetablesList = intent.getStringArrayListExtra("vegetables_list");
        dairyList = intent.getStringArrayListExtra("dairy_list");

        User user = (User) getIntent().getSerializableExtra("user_details");
        name = user.getUsername();
        phone = user.getPhoneNumber();
        lat = user.getLat();
        lng = user.getLng();
        int i;
        textView.setText("\n");
        textView.append("Name : " + name + "\n");
        textView.append("Address : " + "lat" + lat.toString() + " lng " + lng.toString() + "\n");
        textView.append("Mobile : " + phone + "\n\n" + "Item Selected :\n");
        textView.setMovementMethod(new ScrollingMovementMethod());
        for (i = 0; i < category_list.size(); i++) {
            textView.append(category_list.get(i) + "\n");
        }
        Button bt = (Button) findViewById(R.id.btton_push23);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                DatabaseReference parentRef = firebaseDatabase.getReference().child("vendors").child(phone).getRef();
                Vendor vendor = new Vendor(phone, "lat" + lat.toString() + " lng " + lng.toString(), name);
                parentRef.setValue(vendor);
                DatabaseReference catRef = firebaseDatabase.getReference("ingredient_categories");
                catRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot cat : dataSnapshot.getChildren()) {
                            switch (cat.getKey()) {
                                case "Milk and Other Dairy": {
                                    if (category_list.contains("Dairy")) {
                                        if (dairyList.size() > 0) {
                                            for (String item : dairyList) {
                                                cat.child(item.substring(0,item.indexOf(';'))).child(phone).getRef().setValue(name+";"+item.substring(item.indexOf(';')+1,item.length()));
                                            }
                                        }
                                    }
                                    break;
                                }
                                case "Meat and Eggs": {
                                    if (category_list.contains("Meat and Eggs")) {
                                        if (meatList.size() > 0) {
                                            for (String item : meatList) {
                                                cat.child(item.substring(0,item.indexOf(';'))).child(phone).getRef().setValue(name+";"+item.substring(item.indexOf(';')+1,item.length()));
                                            }
                                        }
                                    }
                                    break;
                                }
                                case "Vegetables": {
                                    if (category_list.contains("Vegetables")) {
                                        if (vegetablesList.size() > 0) {
                                            for (String item : vegetablesList) {
                                                cat.child(item.substring(0,item.indexOf(';'))).child(phone).getRef().setValue(name+";"+item.substring(item.indexOf(';')+1,item.length()));
                                            }
                                        }
                                    }
                                    break;
                                }
                                case "Baking":
                                case "Bread":
                                case "Sweet Snacks": {
                                    if (category_list.contains("Bakery")) {
                                        cat.getRef().addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                if(bakeryList.size()>0)
                                                for(String item : bakeryList) {
                                                    if(dataSnapshot.hasChild(item.substring(0,item.indexOf(';')))) {
                                                        cat.child(item.substring(0,item.indexOf(';'))).child(phone).getRef().setValue(name+";"+item.substring(item.indexOf(';')+1,item.length()));
                                                    }
                                                }
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError databaseError) {

                                            }
                                        });
                                    }
                                    break;
                                }
                                default: {
                                    if (category_list.contains("Groceries")) {
                                        cat.getRef().addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                if(groceryList.size()>0)
                                                for(String item : groceryList) {
                                                    if(dataSnapshot.hasChild(item.substring(0,item.indexOf(';')))) {
                                                        cat.child(item.substring(0,item.indexOf(';'))).child(phone).getRef().setValue(name+";"+item.substring(item.indexOf(';')+1,item.length()));
                                                    }
                                                }
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError databaseError) {

                                            }
                                        });
                                    }
                                }
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                Toast.makeText(SignUp4_Vendor.this, "added", Toast.LENGTH_SHORT).show();
                Intent intent1 = new Intent(SignUp4_Vendor.this, VendorHome.class);
                startActivity(intent1);
            }
        });
    }
}
