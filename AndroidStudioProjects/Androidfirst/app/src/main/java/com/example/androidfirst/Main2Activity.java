package com.example.androidfirst;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class Main2Activity extends AppCompatActivity {
    private TextView name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        name=findViewById(R.id.textView2);
        Bundle extra =getIntent().getExtras();
        if(extra !=null)
        {
            String value=extra.getString("names");
            Log.d("extra int","onCreate:"+extra.getInt("age"));
            name.setText(value);
        }
         name.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Intent intent =getIntent();
                 intent.putExtra("messageback","from Activity 2 to MAin Activity");
                 setResult(RESULT_OK,intent);
                 finish();
             }
         });
       // if(getIntent().getStringExtra("names")!=null)
        //{
         //   String value=getIntent().getStringExtra("names");
          //  name.setText(value);
        //}
    }
}
