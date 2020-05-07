package com.example.makeitrainapp;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity {
   // private Button ShowMoney;
  //  private Button Showtag;
    private int MoneyCounter =0;
    private TextView MoneyText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MoneyText=findViewById(R.id.textView2);
       // ShowMoney=findViewById(R.id.button);
      //  Showtag=findViewById(R.id.ShowTag);
      //  ShowMoney.setOnClickListener(new View.OnClickListener() {
       //     @Override
       //     public void onClick(View v) {
        //        Log.d("Mytag", "onClick: Show Money ");
         //   }
        //});
    }
    public  void showTag(View v)
    {
        Toast.makeText(getApplicationContext(),R.string.app_name,Toast.LENGTH_LONG)
                .show();
      //  Log.d("Mytag1", "onClick: Show Tag ");
    }
    public  void showMoney(View v1)
    {
        NumberFormat numberFormat=NumberFormat.getCurrencyInstance();
        MoneyCounter += 1000;
        int v= MoneyCounter;
        MoneyText.setText(String.valueOf(numberFormat.format(MoneyCounter)));
        if(v< 20000 )
        {
            MoneyText.setTextColor(getResources().getColor(R.color.MyColor));
        }
        else if (v>20000 &&v<40000)
        {
            MoneyText.setTextColor(Color.RED);
        }
        else if (v>40000)
        {
            MoneyText.setTextColor(Color.BLUE);
        }
       // Log.d("Mytag", "onClick: Show Money "+ MoneyCounter);
    }
}
