package com.example.q2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Main2Activity extends AppCompatActivity {
     TextView text;
     Button back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Intent intent = getIntent();
        String value = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
        text = findViewById(R.id.textView2);
        text.setText(value);
        back=findViewById(R.id.button6);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              //  Intent intent1=new Intent(this,MainActivity.class);
            }
        });
    }
}
