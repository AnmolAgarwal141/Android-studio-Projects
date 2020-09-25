package com.example.naaniz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SupportActivity extends AppCompatActivity {

    EditText description;
    Button send;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_support);

        description = findViewById(R.id.description);
        send = findViewById(R.id.send);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent eml = new Intent(Intent.ACTION_SEND);
                //eml.setData(Uri.parse("email"));
                eml.setDataAndType(Uri.parse("email"),"text/html");
                eml.putExtra(Intent.EXTRA_SUBJECT, "Support from User");      //have add text view later..
                eml.putExtra(Intent.EXTRA_EMAIL, new String[] {"coolguy.anshul@gmail.com"});     // email //whatsapp
                eml.putExtra(Intent.EXTRA_TEXT, description.getText().toString());
                //eml.setType("text/html");

                Intent chooser  = Intent.createChooser(eml, "Send Email");
                startActivity(chooser);
            }
        });
    }
}
