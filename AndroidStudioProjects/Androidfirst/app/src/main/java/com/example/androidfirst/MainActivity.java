package com.example.androidfirst;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
     private Button Mybutton ;
     private EditText Mytext;
     private  final int REQUEST_CODE =2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Mybutton= findViewById(R.id.button);
        Mytext=findViewById(R.id.editText);
        Mybutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Enteredname= Mytext.getText().toString().trim();
                if(!Enteredname.isEmpty()) {
                    Intent intent = new Intent(MainActivity.this, Main2Activity.class);
                    intent.putExtra("names", Enteredname);
                    intent.putExtra("age",19);
                    startActivityForResult(intent,REQUEST_CODE);
                    //startActivity(intent);
                }
                else
                {
                    Toast.makeText(MainActivity.this,"ENTER ANY NAME",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQUEST_CODE)
        {
            if(resultCode==RESULT_OK)
            {
                assert data != null;
                String message = data.getStringExtra("messageback");
               Toast.makeText(MainActivity.this,message,Toast.LENGTH_SHORT).show();
            }
        }
    }
}
