package com.example.androiddrawing;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Drawing drawing=new Drawing(this);
        CustomTextVIew customTextVIew=new CustomTextVIew(this,null);
        //setContentView(drawing);
        setContentView(customTextVIew);
    }
}
