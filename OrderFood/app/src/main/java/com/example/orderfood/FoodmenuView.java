package com.example.orderfood;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import model.FoodItem;
import model.Restaurant;
import ui.FoodItemRecyclerViewAdapter;
import ui.RestaurantRecyclerViewAdapter;
import util.OrderFoodApi;

public class FoodmenuView extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private FirebaseUser user;
    private FirebaseFirestore db=FirebaseFirestore.getInstance();
    private StorageReference storageReference;
    private List<FoodItem> foodItemList;
    private RecyclerView recyclerView;
    private String name;
    public static String OrderBycust;
    private Button OrderButton;
    private FoodItemRecyclerViewAdapter foodItemRecyclerViewAdapter;
    private CollectionReference collectionReference=db.collection("FoodItem");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foodmenu_view);
        Objects.requireNonNull(getSupportActionBar()).setElevation(0);
        OrderButton=findViewById(R.id.Orderbutton);
       Bundle extras=  getIntent().getExtras();
       if(extras==null){
           return;
       }
            name=extras.getString("restaurantnameselected");

        firebaseAuth=FirebaseAuth.getInstance();
        user=firebaseAuth.getCurrentUser();
        foodItemList=new ArrayList<>();
        recyclerView=findViewById(R.id.recyclerviewfooditem);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        OrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OrderBycust="Order Placed: "+ui.FoodItemRecyclerViewAdapter.OrderPlaced+"Total Order Amount: "+ FoodItemRecyclerViewAdapter.TotalSum;
               // OrderBycust="cheesepasta";
                Toast.makeText(FoodmenuView.this, OrderBycust, Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_signout:
                if(user!=null && firebaseAuth!=null){
                    firebaseAuth.signOut();
                    startActivity(new Intent(FoodmenuView.this,MainActivity.class));
                    finish();
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onStart() {
        super.onStart();
        collectionReference.whereEqualTo("accountType", "Restaurant").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if(!queryDocumentSnapshots.isEmpty()){
                    for (QueryDocumentSnapshot fooditems:queryDocumentSnapshots){
                        FoodItem foodItem =fooditems.toObject(FoodItem.class);
                            foodItemList.add(foodItem);

                    }
                    foodItemRecyclerViewAdapter=new FoodItemRecyclerViewAdapter(FoodmenuView.this,foodItemList);
                    recyclerView.setAdapter(foodItemRecyclerViewAdapter);
                    foodItemRecyclerViewAdapter.notifyDataSetChanged();
                }
                else {
                }}
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }
}
