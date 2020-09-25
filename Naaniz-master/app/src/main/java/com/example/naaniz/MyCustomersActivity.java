package com.example.naaniz;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.example.naaniz.adapters.OrderPersonAdapter;

import java.util.ArrayList;

public class MyCustomersActivity extends AppCompatActivity {

    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_customers);

        Button button = findViewById(R.id.backButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        listView = findViewById(R.id.list_my_customers);

        OrderPersons abc = new OrderPersons("Mary ", null);
        OrderPersons def = new OrderPersons("Helen", null);
        OrderPersons a = new OrderPersons("John", null);
        OrderPersons b = new OrderPersons("Rambo", null);
        OrderPersons c = new OrderPersons("Venessa", null);
        OrderPersons d = new OrderPersons("Tom", null);
        OrderPersons e = new OrderPersons("Ralewood", null);
        OrderPersons f = new OrderPersons("Hanston", null);

        ArrayList<OrderPersons> listItems = new ArrayList<>();

        listItems.add(abc);
        listItems.add(def);
        listItems.add(a);
        listItems.add(b);
        listItems.add(c);
        listItems.add(d);
        listItems.add(e);
        listItems.add(f);

        OrderPersonAdapter adapter = new OrderPersonAdapter(MyCustomersActivity.this, R.layout.orders_adapter_view, listItems);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

            }
        });
    }
}
