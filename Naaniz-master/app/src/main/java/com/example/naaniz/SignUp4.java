package com.example.naaniz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.naaniz.interfaces.AddressInterface;
import com.example.naaniz.models.User;
import com.example.naaniz.location.CurrentAddress;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SignUp4 extends AppCompatActivity {
    private String name;
    private String phone;
    private String add;
    private String foodPref;
    private TextView nameText;
    private TextView phoneText;
    private TextView addText;
    private TextView foodPrefText;
    private Double lat;
    private Button continueHome;
    private Double lng;
    private Uri picturePanCard;
    private Uri pictureAadhar;
    private Uri pictureCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up4);
        continueHome = findViewById(R.id.continueHome);
        User user = (User) getIntent().getSerializableExtra("user_details");
        name = user.getUsername();
        phone = user.getPhoneNumber();
        foodPref = user.getFood_choice();
        lat = user.getLat();
        lng = user.getLng();

        if (user.getPicturePanCard() != null)
            picturePanCard = Uri.parse(user.getPicturePanCard());
        if (user.getPictureAadhar() != null)
            pictureAadhar = Uri.parse(user.getPictureAadhar());
        if (user.getPictureCancel() != null)
            pictureCancel = Uri.parse(user.getPictureCancel());
        Log.d("Intentdetails","name : "+name);
        Log.d("Intentdetails","phone : "+phone);
        Log.d("Intentdetails","food : "+foodPref);
        nameText = findViewById(R.id.name_signup4);
        phoneText = findViewById(R.id.phone_signup4);
        foodPrefText = findViewById(R.id.food_choice_signup4);
        addText = findViewById(R.id.address_signup4);
        Toast.makeText(SignUp4.this, lat.toString() + "," + lng.toString(), Toast.LENGTH_SHORT).show();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://maps.googleapis.com/maps/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        AddressInterface addressInterface = retrofit.create(AddressInterface.class);
        Call<Results> callAddress = addressInterface.getAddress(lat.toString() + "," + lng.toString(), AddressInterface.API_KEY);
        setTexts();
        continueHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUp4.this, Home.class);
                intent.putExtra("name", name);
                intent.putExtra("phone", phone);
                SharedPreferences sharedPreferences = getSharedPreferences("NAANIZ_PREFERENCES", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("current_phone_number", phone);
                editor.putBoolean("current_log_in", Boolean.TRUE);
                editor.apply();
                startActivity(intent);
            }
        });
    }

    private void setTexts() {
        nameText.setText("NAME : " + name);
        phoneText.setText("PHONE : " + phone);
        add = Double.toString(lat)+" : "+Double.toString(lng);
        CurrentAddress currentAddress = new CurrentAddress(lat,lng);
        String address = currentAddress.getAddress(SignUp4.this);
        addText.setText("ADDRESS : "+address);
        foodPrefText.setText("FOOD CHOICE : " + foodPref);
        if(picturePanCard!=null)
            Picasso.get().load(picturePanCard).noPlaceholder().centerCrop().fit().into((ImageView) findViewById(R.id.photo1_signup4));
        if(pictureAadhar!=null)
            Picasso.get().load(pictureAadhar).noPlaceholder().centerCrop().fit().into((ImageView) findViewById(R.id.photo2_signup4));
        if(pictureCancel!=null)
            Picasso.get().load(pictureCancel).noPlaceholder().centerCrop().fit().into((ImageView) findViewById(R.id.photo3_signup4));
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference parentRef = firebaseDatabase.getReference("users");
        DatabaseReference ref = parentRef.child(phone).getRef();
        ref.child("name").setValue(name);
        ref.child("address").setValue(add);
        ref.child("food_choice").setValue(foodPref);
        if(picturePanCard==null)
        {
            ref.child("pan_uploaded").setValue(0);
        }
        else {
            Uri picture1 = picturePanCard;
            FirebaseStorage storageReference = FirebaseStorage.getInstance();
            StorageReference imgRef = storageReference.getReference();
            imgRef.child("users/"+phone+"/pic1").putFile(picture1).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(SignUp4.this,"File Uploaded",Toast.LENGTH_SHORT).show();
                }
            });
            ref.child("pan_uploaded").setValue(1);
        }
        if(pictureAadhar==null)
        {
            ref.child("aadhar_uploaded").setValue(0);
        }
        else {
            Uri picture1 = pictureAadhar;
            FirebaseStorage storageReference = FirebaseStorage.getInstance();
            StorageReference imgRef = storageReference.getReference();
            imgRef.child("users/"+phone+"/pic2").putFile(picture1).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(SignUp4.this,"File Uploaded",Toast.LENGTH_SHORT).show();
                }
            });
            ref.child("aadhar_uploaded").setValue(1);
        }
        if(pictureCancel==null)
        {
            ref.child("cancel_uploaded").setValue(0);
        }
        else {
            Uri picture1 = pictureAadhar;
            FirebaseStorage storageReference = FirebaseStorage.getInstance();
            StorageReference imgRef = storageReference.getReference();
            imgRef.child("users/"+phone+"/pic3").putFile(picture1).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(SignUp4.this,"File Uploaded",Toast.LENGTH_SHORT).show();
                }
            });
            ref.child("cancel_uploaded").setValue(1);
        }
        ref.child("city").setValue("Lucknow");
    }
}
