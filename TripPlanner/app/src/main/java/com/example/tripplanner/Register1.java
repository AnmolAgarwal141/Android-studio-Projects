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

public class Register1 extends AppCompatActivity {
    private EditText userid,pass,confpass;
    private Button next;
    DatabaseHandler db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register1);
        userid=findViewById(R.id.editText);
        pass=findViewById(R.id.editText2);
        confpass=findViewById(R.id.editText3);
        next=findViewById(R.id.button3);
        db =new DatabaseHandler(Register1.this);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if((pass.getText().toString()).equals(confpass.getText().toString())){

                    Register register =new Register(userid.getText().toString(),pass.getText().toString());
                     db.Registered(register);
                     Bundle bundle =new Bundle();
                     bundle.putString("email",userid.getText().toString());
                     Intent i= new Intent(Register1.this,Register2.class);
                     i.putExtras(bundle);
                     startActivity(i);
                }
                else
                {
                    Toast.makeText(Register1.this,"PASSWORD DOES NOT MATCH",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
