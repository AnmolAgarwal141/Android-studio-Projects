package com.example.naaniz.customer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.example.naaniz.R;
import com.example.naaniz.adapters.OrdersAdapter;
import com.example.naaniz.rvmodels.OrdersRVModel;

import java.util.ArrayList;
import java.util.List;

public class CustomerOrdersActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    private List<OrdersRVModel> orders;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_orders);

        recyclerView = findViewById(R.id.recycler_view_cust);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        orders = new ArrayList<>();

        for (int i = 0; i < 10; ++i) {
            OrdersRVModel ordersRVModel = new OrdersRVModel("3:00 PM", "23", "Kadhai Paneer", "03", "Rs. 180", "Rs. 180");
            orders.add(ordersRVModel);
        }

        RecyclerView.Adapter adapter = new OrdersAdapter(orders, this, 2);

        recyclerView.setAdapter(adapter);

    }
}
