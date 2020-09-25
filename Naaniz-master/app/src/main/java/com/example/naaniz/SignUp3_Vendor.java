package com.example.naaniz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;

import com.example.naaniz.models.User;
import com.example.naaniz.views.ItemListItemView;
import com.example.naaniz.views.ItemListView;

import java.util.ArrayList;

public class SignUp3_Vendor extends AppCompatActivity {
    private CheckBox dairy;
    private CheckBox meat;
    private CheckBox grocery;
    private CheckBox vegetables;
    private CheckBox bakery;
    private Context context;
    private Button continueButton;
    private LinearLayout item_layout;
    private ArrayList<ItemListView> dairy_checkList = new ArrayList<>();
    private ArrayList<ItemListView> meat_checkList = new ArrayList<>();
    private ArrayList<ItemListView> grocery_checkList = new ArrayList<>();
    private ArrayList<ItemListView> vegetables_checkList = new ArrayList<>();
    private ArrayList<ItemListView> bakery_checkList = new ArrayList<>();
    private ArrayList<String> category_list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up3__vendor);
        context = this;
        dairy = findViewById(R.id.checkBox2);
        continueButton = findViewById(R.id.button3);
        item_layout = findViewById(R.id.item_list_layout);
        meat = findViewById(R.id.checkBox3);
        grocery = findViewById(R.id.checkBox4);
        vegetables = findViewById(R.id.checkBox5);
        bakery = findViewById(R.id.checkBox6);
        dairy.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                ItemListView newList = new ItemListView(getApplicationContext());
                if (isChecked) {
                    dairy.setChecked(true);
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    newList.setLayoutParams(params);
                    newList.setItems(dairy.getText().toString());
                    item_layout.addView(newList);
                    category_list.add(dairy.getText().toString());
                    dairy_checkList.add(newList);
                } else {
                    item_layout.removeView(newList);
                    category_list.remove(dairy.getText().toString());
                    dairy.setChecked(false);
                    dairy_checkList.remove(newList);
                }
            }
        });
        bakery.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                ItemListView newList = new ItemListView(getApplicationContext());
                if (isChecked) {
                    bakery.setChecked(true);
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    newList.setLayoutParams(params);
                    newList.setItems(bakery.getText().toString());
                    category_list.add(bakery.getText().toString());
                    item_layout.addView(newList);
                    bakery_checkList.add(newList);
                } else {
                    item_layout.removeView(newList);
                    category_list.remove(bakery.getText().toString());
                    bakery.setChecked(false);
                    bakery_checkList.remove(newList);
                }
            }
        });
        meat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                ItemListView newList = new ItemListView(context);
                if (isChecked) {
                    meat.setChecked(true);
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    newList.setLayoutParams(params);
                    item_layout.addView(newList);
                    category_list.add(meat.getText().toString());
                    newList.setItems(meat.getText().toString());
                    meat_checkList.add(newList);
                } else {
                    item_layout.removeView(newList);
                    category_list.remove(meat.getText().toString());
                    meat.setChecked(false);
                    meat_checkList.remove(newList);
                }
            }
        });
        grocery.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                ItemListView newList = new ItemListView(getBaseContext());
                if (isChecked) {
                    grocery.setChecked(true);
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    newList.setLayoutParams(params);
                    newList.setItems(grocery.getText().toString());
                    category_list.add(grocery.getText().toString());
                    item_layout.addView(newList);
                    grocery_checkList.add(newList);
                } else {
                    item_layout.removeView(newList);
                    category_list.remove(grocery.getText().toString());
                    grocery.setChecked(false);
                    grocery_checkList.remove(newList);
                }
            }
        });
        vegetables.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                ItemListView newList = new ItemListView(getBaseContext());
                if (isChecked) {
                    vegetables.setChecked(true);
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    newList.setLayoutParams(params);
                    newList.setItems(vegetables.getText().toString());
                    category_list.add(vegetables.getText().toString());
                    item_layout.addView(newList);
                    vegetables_checkList.add(newList);
                } else {
                    item_layout.removeView(newList);
                    category_list.remove(vegetables.getText().toString());
                    vegetables.setChecked(false);
                    vegetables_checkList.remove(newList);
                }
            }
        });
        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<String> dairyList = new ArrayList<>();
                ArrayList<String> groceryList = new ArrayList<>();
                ArrayList<String> bakeryList = new ArrayList<>();
                ArrayList<String> vegetablesList = new ArrayList<>();
                if(dairy_checkList.size()>0)
                for(ItemListItemView item : dairy_checkList.get(0).getItems()) {
                    dairyList.add(item.getItemCheckHeading()+";"+item.getItemPrice().toString());
                    Log.d("vendor_items",item.getItemCheckHeading()+";"+item.getItemPrice().toString());
                }
                if(grocery_checkList.size()>0) {
                    for(ItemListItemView item: grocery_checkList.get(0).getItems()) {
                        groceryList.add(item.getItemCheckHeading()+";"+item.getItemPrice().toString());
                    }
                }
                if(bakery_checkList.size()>0) {
                    for(ItemListItemView item : bakery_checkList.get(0).getItems()) {
                        bakeryList.add(item.getItemCheckHeading()+";"+item.getItemPrice().toString());
                    }
                }
                if(vegetables_checkList.size()>0) {
                    for(ItemListItemView item : vegetables_checkList.get(0).getItems()) {
                        vegetablesList.add(item.getItemCheckHeading()+";"+item.getItemPrice().toString());
                    }
                }
                ArrayList<String> meatList = new ArrayList<>();
                if(meat_checkList.size()>0)
                for(ItemListItemView item : meat_checkList.get(0).getItems()) {
                    meatList.add(item.getItemCheckHeading()+";"+item.getItemPrice().toString());
                    Log.d("vendor_items",item.getItemCheckHeading()+";"+item.getItemPrice().toString());
                }
                Intent intent = new Intent(SignUp3_Vendor.this, SignUp4_Vendor.class);
                intent.putExtra("user_details", (User) getIntent().getSerializableExtra("user_details"));
                intent.putExtra("category_list",category_list);
                intent.putExtra("meat_list",meatList);
                intent.putExtra("vegetables_list",vegetablesList);
                intent.putExtra("grocery_list",groceryList);
                intent.putExtra("dairy_list",dairyList);
                intent.putExtra("bakery_list",bakeryList);
                startActivity(intent);
            }
        });
    }
}
