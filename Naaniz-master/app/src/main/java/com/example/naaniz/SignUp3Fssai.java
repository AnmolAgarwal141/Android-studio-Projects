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

import com.example.naaniz.models.User;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class SignUp3Fssai extends AppCompatActivity {

    private ImageView fssaiImage;
    private ArrayList<Uri> uri;
    private Button fssaiButton, nextButton;
    private int currentID = 0;
    private int currentCounter =0;
    private final int SELECT_PICTURE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up3_fssai);

        uri = new ArrayList<android.net.Uri>();
        fssaiImage = findViewById(R.id.fssai_doc_image);
        fssaiButton = findViewById(R.id.fssai_upload_btn);
        nextButton = findViewById(R.id.fssai_next_btn);
        uri.add(null);
        currentCounter = 0;

        fssaiButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setImage(fssaiImage);
                nextButton.setVisibility(View.VISIBLE);
                nextButton.setEnabled(true);
            }
        });

        nextButton.setOnClickListener(v -> {
            Intent intent1 = new Intent(SignUp3Fssai.this,SignUp4.class);
            User user = (User) getIntent().getSerializableExtra("user_details");
            if(uri==null)
            {
                Log.d("Intentdetails","PictureUri NULL!!!");
                return;
            }
            if(uri.get(0)!=null)
             user.setPictureFSSAI(uri.get(0).toString());
            /*Error: java.lang.NullPointerException: Attempt to invoke virtual method
            'void com.example.naaniz.Models.User.setPictureFSSAI(java.lang.String)' on a null object reference
            AT LINE 55
             */
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
                uri.set(currentCounter, data.getData());
                Log.d("UploadImages","Uri : "+ data.getData().toString());
                Log.d("UploadImages","Uri : "+getContentResolver().getType(data.getData()));
                currentCounter++;
                Picasso.get().load(data.getData()).noPlaceholder().centerCrop().fit().into((ImageView) findViewById(currentID));
            }
        }
    }
}
