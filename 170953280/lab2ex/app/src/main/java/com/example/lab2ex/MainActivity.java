package com.example.lab2ex;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
     Button button;
     EditText text;
    String s;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button=findViewById(R.id.button);
        text=findViewById(R.id.editText2);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("NAME","HI ANMOL");
                s = text.getText().toString();
                Toast.makeText(getApplicationContext(),"HI "+s,Toast.LENGTH_LONG).show();
           }
        });
    }
    public void clickfunction(View view)
    {
      Log.i("NAME1", "clickfunction: ");
       //Toast.makeText(this,"hello",Toast.LENGTH_LONG);
    }
}
