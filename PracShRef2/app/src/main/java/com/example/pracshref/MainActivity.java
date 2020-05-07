package com.example.pracshref;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private static final String MESSAGE_ID = "message_pref";
    private EditText Message;
    private TextView View;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Message =findViewById(R.id.editText);
        button=findViewById(R.id.button);
        View=findViewById(R.id.textView);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                String mssg=Message.getText().toString().trim();
                SharedPreferences sharedPreferences=getSharedPreferences(MESSAGE_ID,MODE_PRIVATE);
                SharedPreferences.Editor editor=sharedPreferences.edit();
                editor.putString("message",mssg);
                editor.apply();
            }
        });

        SharedPreferences getSharedData=getSharedPreferences(MESSAGE_ID,MODE_PRIVATE);
        String value=getSharedData.getString("message","Nothing Yet");
        View.setText(value);
    }
}
