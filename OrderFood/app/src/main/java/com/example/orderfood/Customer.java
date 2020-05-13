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
import android.widget.TextView;

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

import model.Restaurant;
import ui.RestaurantRecyclerViewAdapter;
import util.OrderFoodApi;

public class Customer extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private FirebaseUser user;
    private FirebaseFirestore db=FirebaseFirestore.getInstance();
    private StorageReference storageReference;
    private List<Restaurant> RestaurantList;
    private RecyclerView recyclerView;
    private RestaurantRecyclerViewAdapter restaurantRecyclerViewAdapter;
    private CollectionReference collectionReference=db.collection("Restaurant");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer);
        Objects.requireNonNull(getSupportActionBar()).setElevation(0);

        firebaseAuth=FirebaseAuth.getInstance();
        user=firebaseAuth.getCurrentUser();
        RestaurantList=new ArrayList<>();
        recyclerView=findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


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
                    startActivity(new Intent(Customer.this,MainActivity.class));
                    finish();
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();
        collectionReference.whereEqualTo("accountType","Restaurant" ).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if(!queryDocumentSnapshots.isEmpty()){
                    for (QueryDocumentSnapshot restaurants:queryDocumentSnapshots){
                        Restaurant restaurant =restaurants.toObject(Restaurant.class);
                        RestaurantList.add(restaurant);
                    }
                    restaurantRecyclerViewAdapter=new RestaurantRecyclerViewAdapter(Customer.this,RestaurantList);
                    recyclerView.setAdapter(restaurantRecyclerViewAdapter);
                    restaurantRecyclerViewAdapter.notifyDataSetChanged();
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
