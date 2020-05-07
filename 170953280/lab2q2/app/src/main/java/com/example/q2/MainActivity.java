package com.example.q2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import static android.provider.AlarmClock.EXTRA_MESSAGE;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
     Button add,subtract,Multiply,Divide;
     EditText num1,num2;
     Float a,b;
     String s;
    public static final String EXTRA_MESSAGE = "com.example.q2.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        add=findViewById(R.id.button);
        subtract=findViewById(R.id.button2);
        Multiply=findViewById(R.id.button3);
        Divide=findViewById(R.id.button4);
        num1=findViewById(R.id.editText);
        num2=findViewById(R.id.editText2);

        add.setOnClickListener(this);
        subtract.setOnClickListener(this);
        Multiply.setOnClickListener(this);
        Divide.setOnClickListener(this);



    }
     public void getnum()
     {
         a= Float.valueOf(num1.getText().toString());
         b= Float.valueOf(num2.getText().toString());
     }
    public void sendMessage(View view) {
        Intent intent = new Intent(this, Main2Activity.class);
        intent.putExtra(EXTRA_MESSAGE, s);
        startActivity(intent);
    }
    @Override
    public void onClick(View v) {
        switch(v.getId())
        {
            case R.id.button:
                 getnum();
                 s=a+" + "+b+" = "+(a+b);
                sendMessage(v);
                break;
            case R.id.button2:
                getnum();
                s=a+" - "+b+" = "+(a-b);
                sendMessage(v);
                break;
            case R.id.button3:
                getnum();
                s=a+" * "+b+" = "+(a*b);
                sendMessage(v);
                break;
            case R.id.button4:
                getnum();
                s=a+" / "+b+" = "+(a/b);
                sendMessage(v);
                break;
            default:
                break;
        }
    }


}
