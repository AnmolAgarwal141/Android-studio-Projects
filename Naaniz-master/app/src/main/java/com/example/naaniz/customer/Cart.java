package com.example.naaniz.customer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.naaniz.R;

import java.util.ArrayList;

public class Cart extends AppCompatActivity {
    ArrayList<DishesInfo> a =new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        RecyclerView recyclerView= findViewById(R.id.rec);

        a.add(new DishesInfo("asdasd","asdas12323d"));
        a.add(new DishesInfo("a123sdasd","asdasd123"));
        a.add(new DishesInfo("asda213sd","asdas213d"));
        a.add(new DishesInfo("asdasd213","asdasd213"));

        a.add(new DishesInfo("asdasd","asdas12323d"));
        a.add(new DishesInfo("a123sdasd","asdasd123"));
        a.add(new DishesInfo("asda213sd","asdas213d"));
        a.add(new DishesInfo("asdasd213","asdasd213"));
        RecyclerView.Adapter adapter = new DishesAdapter(a);
        LinearLayoutManager l =  new LinearLayoutManager(Cart.this);
        l.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(l);
        recyclerView.setAdapter(adapter);
    }
}
