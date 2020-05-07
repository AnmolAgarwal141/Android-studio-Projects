package com.example.tripplanner;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tripplanner.Model.Handlingid;
import com.example.tripplanner.Model.Registerdetails;
import com.example.tripplanner.data.DatabaseHandler;

import java.util.Calendar;

public class Register2 extends AppCompatActivity {
    private TextView id,email,bdate;
    private EditText Name,phone;
    private Button confirm;
    private int i=0;
    private DatePickerDialog.OnDateSetListener dateSetListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register2);
        id=findViewById(R.id.textView9);
        email=findViewById(R.id.textView10);
        bdate=findViewById(R.id.textView12);
        Name=findViewById(R.id.editText4);
        phone=findViewById(R.id.editText5);
        confirm=findViewById(R.id.button4);
        Bundle bundle =getIntent().getExtras();
        String e=bundle.getString("email");
        email.setText(e);
        i=i+1;
        id.setText("TP"+i);
        bdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal =Calendar.getInstance();
                int year=cal.get(Calendar.YEAR);
                int month=cal.get(Calendar.MONTH);
                int day=cal.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dialog =new DatePickerDialog(Register2.this,android.R.style.Theme_Holo_Light_Dialog,dateSetListener,year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();

            }
        });
        dateSetListener=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                i1=i1+1;
                String Date=i2+"/"+i1+"/"+i;
                bdate.setText(Date);
            }
        };
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Registerdetails registerdetails = new Registerdetails(id.getText().toString(), email.getText().toString(), Name.getText().toString(), phone.getText().toString(), bdate.getText().toString());
                DatabaseHandler db = new DatabaseHandler(Register2.this);
                db.addRegisterDetails(registerdetails);
                Toast.makeText(Register2.this, "REGISTERED SUCCESSFULLY", Toast.LENGTH_SHORT).show();
                Intent i= new Intent(Register2.this,Login.class);
                startActivity(i);

            }
        });
    }
}