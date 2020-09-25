package com.example.naaniz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.naaniz.views.IngredientView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ShowRecipe2 extends AppCompatActivity {

    private TextView name;
    private TextView time;
    private TextView instructions_description;
    private TextView serving;
    public LinearLayout listOfIngredients;
    private List<Double> costOfIngredients = new ArrayList<>();
    private IngredientView ingredientView;
    private String dish;
    private String phone;
    private String choice; // whether the parent was home fragment OR user-recipes
    private TextView recipeCost;
    private Double cost = 0.0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show__recipe);
        name = findViewById(R.id.name);
        time = findViewById(R.id.time);
        instructions_description = findViewById(R.id.instructions_description);
        serving = findViewById(R.id.servings);
        listOfIngredients = findViewById(R.id.listOfIngredients);
        recipeCost = findViewById(R.id.recipeCost);
        dish = getIntent().getStringExtra("dish");
        choice = getIntent().getStringExtra("parentRef");
        phone = getIntent().getStringExtra("phone");
        Log.d("IntentDetails","Dish : "+dish);
        getRecipe();
    }

    private void getRecipe()
    {
        DatabaseReference parentRef;
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        if(getIntent().getStringExtra("parentRef")==null)
            return;
        else
            parentRef = database.getReference(choice);
        DatabaseReference ref2 = parentRef.child(phone).getRef();
        DatabaseReference ref = ref2.child(dish).getRef();
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.d("Intentdetails", dataSnapshot.toString());
                name.setText(dataSnapshot.getKey().toString());
                for(DataSnapshot d : dataSnapshot.getChildren()){
                    Log.d("Intentdetails","dish snapshot : "+d.getKey()+" "+d.getValue().toString());
                    switch (d.getKey())
                    {
                        case "instructions": {
                            instructions_description.setText(d.getValue().toString());
                            break;
                        }
                        case "ingredients": {
                            for(DataSnapshot dc : d.getChildren()) {
                                String ing_name="default",ing_qty="534",ing_qtySI="25454";
                                for(DataSnapshot dcm : dc.getChildren())
                                {
                                    if(dcm.getKey().equals("name"))
                                        ing_name = dcm.getValue().toString();

                                    if(dcm.getKey().equals("qty")){
                                        ing_qty = dcm.getValue().toString();
                                        ing_qtySI=ing_qty;
                                    }
                                    Log.d("!$#$#$%$%$%^%^%$^$^", ing_name+" "+ing_qty+" "+ing_qtySI);

                                    Log.d("Intentdetails","ingredient : "+dcm.getKey()+" : "+dcm.getValue().toString());
                                }
                                addIngredients(ing_name,ing_qtySI,ing_qty,null,null);
                            }
                            break;
                        }
                        case "readyInMinutes":{
                            String t = "Ready in "+d.getValue().toString()+" minutes";
                            time.setText(t);
                            break;
                        }
                        case "servings":{
                            String s = "Servings : "+d.getValue().toString();
                            serving.setText(s);
                            break;
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void addIngredients(String title, String quantitySI, String quantityUS,String category,List<String> vendorList)
    {
        Log.d("Intentdetails", "Inside addIngredients");
        LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        ingredientView = new IngredientView(this, title, quantitySI,category,vendorList);
        listOfIngredients.addView(ingredientView);
    }
}
