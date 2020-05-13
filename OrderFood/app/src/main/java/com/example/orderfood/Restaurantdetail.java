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

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.Objects;

import model.Restaurant;
import util.OrderFoodApi;

public class Restaurantdetail extends AppCompatActivity implements View.OnClickListener {
    private ImageView restaurantimage;
    private EditText restaurantname,restaurantaddress;
    private Button savebutton;
    private String currentuserid,currentAccounttype;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private FirebaseUser user;
    private FirebaseFirestore db=FirebaseFirestore.getInstance();
    private StorageReference storageReference;
    private CollectionReference collectionReference=db.collection("Restaurant");
    private static  final int GALLERY_CODE=1;
    private Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurantdetail);
        Objects.requireNonNull(getSupportActionBar()).setElevation(0);

        firebaseAuth=FirebaseAuth.getInstance();
        restaurantaddress =findViewById(R.id.restaurantaddress);
        restaurantname=findViewById(R.id.restaurantname);
        restaurantimage=findViewById(R.id.restaurantimageview);
        savebutton=findViewById(R.id.savebutton);
        storageReference= FirebaseStorage.getInstance().getReference();

        savebutton.setOnClickListener(this);
        restaurantimage.setOnClickListener(this);

        if(OrderFoodApi.getInstance()!=null){
            currentuserid=OrderFoodApi.getInstance().getUserid();
            currentAccounttype=OrderFoodApi.getInstance().getAccountype();
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
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.restaurantimageview:
                Intent galleryintent=new Intent(Intent.ACTION_GET_CONTENT);
                galleryintent.setType("image/*");
                startActivityForResult(galleryintent,GALLERY_CODE);
                break;
            case R.id.savebutton:
                saverestaurant();
                break;
        }

    }

    private void saverestaurant() {
        final String restaurantnm=restaurantname.getText().toString().trim();
        final String restaurantadd=restaurantaddress.getText().toString().trim();
        if(!TextUtils.isEmpty(restaurantnm)&&!TextUtils.isEmpty(restaurantadd)&&imageUri!=null){
            final StorageReference filepath=storageReference.child("Restaurant_image").child("image_"+ Timestamp.now().getSeconds());
            filepath.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    filepath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            String imageurl=uri.toString();
                            Restaurant restaurant=new Restaurant();
                            restaurant.setResImageuri(imageurl);
                            restaurant.setRestaurantaddress(restaurantadd);
                            restaurant.setRestaurantname(restaurantnm);
                            restaurant.setUserid(currentuserid);
                            restaurant.setAccountType(currentAccounttype);

                            collectionReference.add(restaurant).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    //startActivity(new Intent(Restaurantdetail.this, Restaurant.class));
                                    finish();

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
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==GALLERY_CODE && resultCode==RESULT_OK){
            if(data!=null){
                imageUri=data.getData();
                restaurantimage.setImageURI(imageUri);
            }
        }
    }
}
