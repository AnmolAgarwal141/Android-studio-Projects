package com.example.tripplanner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tripplanner.Model.Register;
import com.example.tripplanner.data.DatabaseHandler;

public class Login extends AppCompatActivity {
    private EditText id,Pass;
    private Button Login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        id=findViewById(R.id.editText6);
        Pass=findViewById(R.id.editText7);
        Login=findViewById(R.id.button5);
        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseHandler db=new DatabaseHandler(Login.this);
                Register register=db.GetRegistered(id.getText().toString());
                String password=register.getPassword();
                if(password.equals(Pass.getText().toString()))
                {
                    Intent i= new Intent(Login.this,Tripplannermain.class);
                    startActivity(i);
                }
                else
                {
                    Toast.makeText(Login.this,"INVALID USERNAME OR PASSWORD",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
