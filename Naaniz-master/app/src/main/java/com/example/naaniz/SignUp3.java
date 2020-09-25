package com.example.naaniz;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.naaniz.models.User;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class SignUp3 extends AppCompatActivity {
    /*
    * Activity Details:
    *   Use Picasso to upload images from gallery into their respective ImageViews
    * TODO:
    *   Upload Images to Cloud Storage Firebase
    * */
    private ImageView panImage;
    private final int SELECT_PICTURE = 1;
    private ImageView aadharImage;
    private ImageView cancelChequeImage;
    private int currentID = 0;
    private int currentCounter =0;
    private ArrayList<Uri> pictureURI;
    private Button panButton;
    private Button aadharButton;
    private Button cancelButton;
    private Button nextButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up3);
        pictureURI = new ArrayList<>();
        currentCounter = 0;
        pictureURI.add(null);
        pictureURI.add(null);
        pictureURI.add(null);
        panImage = findViewById(R.id.pan_card_image);
        aadharImage = findViewById(R.id.aadhar_image);
        cancelChequeImage = findViewById(R.id.cancel_cheque_image);
        panButton = findViewById(R.id.pan_upload_btn);
        aadharButton = findViewById(R.id.aadhar_upload);
        cancelButton = findViewById(R.id.upload_cancel_cheque);
        nextButton = findViewById(R.id.doc_next_btn);
        panButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setImage(panImage);
            }
        });
        aadharButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setImage(aadharImage);
                nextButton.setVisibility(View.VISIBLE);
                nextButton.setEnabled(true);
            }
        });
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setImage(cancelChequeImage);
            }
        });
        nextButton.setOnClickListener(v -> {

            Toast.makeText(this, "working", Toast.LENGTH_SHORT).show();
            Intent intent1 = new Intent(SignUp3.this, SignUp4.class);
            User user = (User) getIntent().getSerializableExtra("user_details");
            if(pictureURI==null)
            {
                Log.d("Intentdetails","PictureUri NULL!!!");
                return;
            }

            if(pictureURI.get(0) != null)
            user.setPicturePanCard(pictureURI.get(0).toString());
            /*Error: java.lang.NullPointerException: Attempt to invoke virtual method
            'void com.example.naaniz.Models.User.setPictureFSSAI(java.lang.String)' on a null object reference
            AT LINE 55
             */
            if(pictureURI.get(1)!= null)
            user.setPictureAadhar(pictureURI.get(1).toString());
            if(pictureURI.get(2) != null)
            user.setPictureCancel(pictureURI.get(2).toString());
            intent1.putExtra("user_details", user);
            startActivity(intent1);
        });
    }
    private void setImage(ImageView imageView) {
        Intent intent = new Intent();
        currentID = imageView.getId();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select Photo"),SELECT_PICTURE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK) {
            if(requestCode==SELECT_PICTURE) {
                pictureURI.set(currentCounter,data.getData());
                Log.d("UploadImages","Uri : "+ data.getData().toString());
                Log.d("UploadImages","Uri : "+getContentResolver().getType(data.getData()));
                currentCounter++;
                Picasso.get().load(data.getData()).noPlaceholder().centerCrop().fit().into((ImageView) findViewById(currentID));
            }
        }
    }

}
