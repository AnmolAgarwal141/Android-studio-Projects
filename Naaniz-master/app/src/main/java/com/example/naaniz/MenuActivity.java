package com.example.naaniz;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;

import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.naaniz.views.RestaurantView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MenuActivity extends AppCompatActivity {
    private float prevX,prevY;
    LinearLayout listOfDishes;
    List<String> dishes = new ArrayList<>();
    Boolean crossedScrollingLimit = false;
    DatabaseReference ref;
    Button backButton;
    FloatingActionButton share;
    TextInputEditText ed;


    private int SELECT_PICTURE=1;
    private Uri uri=null;
    private FloatingActionButton d;
    private int STORAGE_CODE=10;
    private StringBuilder savedPDF=new StringBuilder();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        Log.i("carry was here", "meeeeeeemmmmmmmmmmeelklklkklkl");

        d = (FloatingActionButton)findViewById(R.id.downld2_Button);

        d.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
                    if (MenuActivity.this.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                        String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
                        requestPermissions(permissions, STORAGE_CODE);
                    } else {
                        Toast.makeText(MenuActivity.this, "Kyaa yaar", Toast.LENGTH_SHORT).show();
                        savePdf();
                    }
                }
            }
        });



        Button button = (Button)findViewById(R.id.recipe_upload_btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("application/pdf");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"Select pdf"),SELECT_PICTURE);
            }
        });

        share = findViewById(R.id.share);
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, savedPDF.toString());
                sendIntent.setType("text/plain");
                startActivity(sendIntent);

                /*Intent sharingIntent = new Intent(Intent.ACTION_SEND);

                sharingIntent.setType("application/pdf");
                sharingIntent.putExtra(Intent.EXTRA_STREAM, uri);
                startActivity(Intent.createChooser(sharingIntent, "Share using :"));*/
            }
        });

        backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        listOfDishes = findViewById(R.id.listOfDishes);
        initListOfDishes();
    }
    private void initListOfDishes(){

        String phone = getIntent().getStringExtra("phone");
        Log.d("addingCard","phone : "+phone);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        ref = database.getReference("users").child(phone).getRef();
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.d("addingCard","dataSnapshot : "+dataSnapshot.getKey());
                Log.d("addingCard",dataSnapshot.getValue().toString());
                for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                    Log.d("addingCard","dataSnapshot1 : "+dataSnapshot1.getKey());
                    Log.d("addingCard",dataSnapshot1.getValue().toString());
                    if(dataSnapshot1.getKey().equals("menu")){
                        for(DataSnapshot dataSnapshot2 : dataSnapshot1.getChildren()){
                            Log.d("addingCard",dataSnapshot2.getKey());
                            dishes.add(dataSnapshot2.getKey());
                            addCard(dataSnapshot2.getKey());
                        }
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });
    }
    @SuppressLint("ClickableViewAccessibility")
    private void addCard(String dish){
        Log.d("addingCard",dish);
        RestaurantView restaurantView = new RestaurantView(MenuActivity.this);
        restaurantView.setName(dish);
        restaurantView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:{
                        prevX = event.getX();
                        prevY = event.getY();
                        crossedScrollingLimit = false;
                        break;
                    }
                    case MotionEvent.ACTION_UP:{
                        double dist = Math.sqrt(Math.pow(event.getX()-prevX,2)+Math.pow(event.getY()-prevY,2));
                        if(event.getX() > prevX+100) {
                            Log.d("addingCard", "SCROLLED RIGHT : " + event.getPointerCount());
                            listOfDishes.removeView(v);
                            RestaurantView res = (RestaurantView) v;
                            ref.child("menu").child(res.getName()).removeValue(null);
                            dishes.remove(dishes.indexOf(res.getName()));
                            Toast.makeText(MenuActivity.this, "dish removed", Toast.LENGTH_SHORT).show();
                        }else if(dist<50 && !crossedScrollingLimit) {
                            Log.d("addingCard", "TAP");
                            Intent intent = new Intent(MenuActivity.this, ShowRecipe.class);
                            intent.putExtra("parentRef","recipes");
                            intent.putExtra("dish",((RestaurantView)v).getName());
                            startActivity(intent);
                        }
                        else{
                            v.setX(0);
                        }
                        break;
                    }
                    case MotionEvent.ACTION_MOVE:{
                        if(event.getX() > prevX+100 ) {
                            Log.d("motionEvent",""+(100.0*((int)(event.getX()-prevX)/100)));
                            v.setX(100.0f*((int)(event.getX()-prevX)/100));
                            crossedScrollingLimit = true;
                        }
                    }
                }
                return true;
            }
        });
        listOfDishes.addView(restaurantView);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK) {
            if(requestCode==SELECT_PICTURE) {
                uri=data.getData();
            }
        }
    }
    void savePdf()
    {
        ed= (TextInputEditText)findViewById(R.id.edit_share);
        String pp = ed.getText().toString().trim();
        savedPDF.append("Restaurant Name:   "+"\t\t" + pp+"\n\n");



        String phone = getIntent().getStringExtra("phone");
        savedPDF.append("Phone No : " +"\t\t" + phone+   "\n\n");


        Log.d("addingCard","phone : "+phone);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        ref = database.getReference("users").child(phone).getRef();
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.d("addingCard","dataSnapshot : "+dataSnapshot.getKey());
                Log.d("addingCard",dataSnapshot.getValue().toString());
                for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                    Log.d("addingCard","dataSnapshot1 : "+dataSnapshot1.getKey());
                    Log.d("addingCard",dataSnapshot1.getValue().toString());
                    int count=1;
                    if(dataSnapshot1.getKey().equals("menu")){
                        for(DataSnapshot dataSnapshot2 : dataSnapshot1.getChildren()){
                            Log.d("addingCard","fidfisfisdfisdf    " +dataSnapshot2.getKey());
                            savedPDF.append(count +".   "+dataSnapshot2.getKey()+"\n");
                            count++;
                        }
                    }
                }
                pdfPrinted();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==STORAGE_CODE)
        {
            if(grantResults.length>0 && grantResults[0]== PackageManager.PERMISSION_GRANTED)
            {
                savePdf();
            }
            else
            {
                Toast.makeText(MenuActivity.this,"permission denied",Toast.LENGTH_SHORT).show();
            }
        }
    }
    public void pdfPrinted()
    {
        String po = savedPDF.toString();

        Document document = new Document();
        String file = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(System.currentTimeMillis());
        String filePath = Environment.getExternalStorageDirectory()+"/"+file+".pdf";

        try {
            PdfWriter.getInstance(document,new FileOutputStream(filePath));
            document.open();
            document.add(new Paragraph(po));
            document.close();

        } catch (DocumentException | FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
