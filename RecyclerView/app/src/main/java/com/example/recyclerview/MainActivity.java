package com.example.recyclerview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.recyclerview.Adapter.RecyclerViewAdapter;
import com.example.recyclerview.Data.DatabaseHandler;
import com.example.recyclerview.Model.contact;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;

    private ArrayList<contact> contactArraylist;
    private RecyclerViewAdapter recyclerViewAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView=findViewById(R.id.recyclerview_id);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        DatabaseHandler db =new DatabaseHandler(MainActivity.this);
        contactArraylist=new ArrayList<>();
        List<contact> Contactlist =db.GetAllContacts();
        for(contact c : Contactlist)
        {
            contactArraylist.add(c);
        }
        recyclerViewAdapter =new RecyclerViewAdapter(this,contactArraylist);
        recyclerView.setAdapter(recyclerViewAdapter);

    }
}
