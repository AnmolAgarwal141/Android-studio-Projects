package com.example.orderfood;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class showorders extends AppCompatActivity {
    private TextView Ordered;
    private Button Deliverorder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showorders);
        Ordered=findViewById(R.id.textView2);
        Deliverorder=findViewById(R.id.DelieverOrder);

        String ordervalue=FoodmenuView.OrderBycust;
        String finalvalue="Customer Name :  Anmol\n"+ordervalue;
        Ordered.setText(finalvalue);
        Deliverorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(showorders.this, "Order is sent for Delivery", Toast.LENGTH_SHORT).show();
            }
        });



    }
}
