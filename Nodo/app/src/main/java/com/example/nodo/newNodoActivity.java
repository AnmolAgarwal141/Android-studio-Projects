package com.example.nodo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class newNodoActivity extends AppCompatActivity {
    private EditText editText;
    private Button button;
    public static final String Extra_reply = "Extranodo";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_nodo);
        editText=findViewById(R.id.edit_nodo);
        button=findViewById(R.id.button_save);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent replyIntent = new Intent();
                if(TextUtils.isEmpty(editText.getText())){
                    setResult(RESULT_CANCELED,replyIntent);
                }
                else {
                    String nodoString =editText.getText().toString();
                    replyIntent.putExtra(Extra_reply,nodoString);
                    setResult(RESULT_OK,replyIntent);
                }
                finish();
            }
        });
    }
}
