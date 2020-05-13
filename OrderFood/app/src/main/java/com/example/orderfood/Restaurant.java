package com.example.orderfood;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.Objects;

import model.FoodItem;
import util.OrderFoodApi;

public class Restaurant extends AppCompatActivity implements View.OnClickListener{
    private ImageView foodimage;
    private EditText foodname,price;
    private Button restaurentdetail,additem,orders;
    private FirebaseAuth firebaseAuth;
    private String currentuserid,currentAccountType;
    private FirebaseAuth.AuthStateListener authStateListener;
    private FirebaseUser user;
    private FirebaseFirestore db=FirebaseFirestore.getInstance();
    private StorageReference storageReference;
    private CollectionReference collectionReference=db.collection("FoodItem");
    private CollectionReference collectionReferenceres=db.collection("Restaurant");
    private static  final int GALLERY_CODE=1;
    private Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant);
        foodimage=findViewById(R.id.imageView);
        foodname=findViewById(R.id.foodnameedittext);
        price=findViewById(R.id.fooditemprice);
        restaurentdetail=findViewById(R.id.Restaurantbuttondetail);
        additem=findViewById(R.id.addmenuitembutton);
        Objects.requireNonNull(getSupportActionBar()).setElevation(0);
        firebaseAuth=FirebaseAuth.getInstance();
        storageReference= FirebaseStorage.getInstance().getReference();

        orders=findViewById(R.id.Ordersbutton);
        orders.setOnClickListener(this);
        foodimage.setOnClickListener(this);
        restaurentdetail.setOnClickListener(this);
        additem.setOnClickListener(this);

        if(OrderFoodApi.getInstance()!=null){
            currentuserid=OrderFoodApi.getInstance().getUserid();
            currentAccountType = OrderFoodApi.getInstance().getAccountype();
        }



        authStateListener=new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                user=firebaseAuth.getCurrentUser();
                if(user!=null){

                }
                else {

                }
            }
        };
    }
    @Override
    protected void onStart() {
        super.onStart();
        user=firebaseAuth.getCurrentUser();
        firebaseAuth.addAuthStateListener(authStateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(firebaseAuth!=null){
            firebaseAuth.removeAuthStateListener(authStateListener);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.Restaurantbuttondetail:
                startActivity(new Intent(Restaurant.this,Restaurantdetail.class));
                break;
            case R.id.addmenuitembutton:
                savefooditem();
                break;
            case R.id.imageView:
                Intent galleryintent=new Intent(Intent.ACTION_GET_CONTENT);
                galleryintent.setType("image/*");
                startActivityForResult(galleryintent,GALLERY_CODE);
                break;
            case R.id.Ordersbutton:
                startActivity(new Intent(Restaurant.this,showorders.class));
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==GALLERY_CODE && resultCode==RESULT_OK){
            if(data!=null){
                imageUri=data.getData();
                foodimage.setImageURI(imageUri);
            }
        }

    }

    private void savefooditem(){
        final String fooditemname=foodname.getText().toString().trim();
        final int foodprice = Integer.parseInt(price.getText().toString().trim());
        if(!TextUtils.isEmpty(fooditemname)&& imageUri!=null){
            final StorageReference filepath=storageReference.child("food_image").child("image_"+ Timestamp.now().getSeconds());
            filepath.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    filepath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            String imageurl=uri.toString();
                            final FoodItem foodItem=new FoodItem();
                            model.Restaurant restaurant=new model.Restaurant();
                            foodItem.setImageuri(imageurl);
                            foodItem.setFooditemname(fooditemname);
                            foodItem.setFoodprice(foodprice);
                            foodItem.setUserid(currentuserid);
                            foodItem.setAccountType(currentAccountType);
                            collectionReferenceres.whereEqualTo("userid",currentuserid).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                @Override
                                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                    Toast.makeText(Restaurant.this, "INSIDE THE OTHER COLLECTION REFERENCE", Toast.LENGTH_SHORT).show();
                                    for (QueryDocumentSnapshot restaurants:queryDocumentSnapshots){
                                        model.Restaurant restaurant =restaurants.toObject(model.Restaurant.class);

                                        foodItem.setRestaurantname(restaurant.getRestaurantname());
                                        Toast.makeText(Restaurant.this, "name"+foodItem.getRestaurantname(), Toast.LENGTH_SHORT).show();
                                }
                            }});
                            collectionReference.add(foodItem).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    //intent to be made or toast may be
                                    Toast.makeText(Restaurant.this, "Food Item Added Successfully", Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {

                                }
                            });



                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                        }
                    });


                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });

        }


    }
}
