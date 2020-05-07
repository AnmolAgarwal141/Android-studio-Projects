package com.example.babyneeds;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.babyneeds.data.DatabaseHandler;
import com.example.babyneeds.model.Item;
import com.example.babyneeds.ui.RecyclerViewAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;
    private List<Item> itemList;
    private DatabaseHandler databaseHandler;
    private FloatingActionButton fab;
    private AlertDialog.Builder builder;
    private AlertDialog alertDialog;
    private Button savebutton;
    private EditText babyitem,itemquantity,itemcolor,itemsize;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        recyclerView=findViewById(R.id.Recyclerview);
        fab=findViewById(R.id.fab);
        databaseHandler=new DatabaseHandler(this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        itemList = new ArrayList<>();
        itemList=databaseHandler.getallitems();
        recyclerViewAdapter=new RecyclerViewAdapter(this,itemList);
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerViewAdapter.notifyDataSetChanged();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createpopdialog();
            }
        });
    }
    private void createpopdialog(){
        builder =new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.popup,null);
        babyitem=view.findViewById(R.id.babyItem);
        itemquantity=view.findViewById(R.id.ItemQuantity);
        itemcolor=view.findViewById(R.id.ItemColor);
        itemsize=view.findViewById(R.id.ItemSize);

        savebutton=view.findViewById(R.id.SaveButton);
        savebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!babyitem.getText().toString().isEmpty() && !itemcolor.getText().toString().isEmpty() && !itemquantity.getText().toString().isEmpty() && !itemsize.getText().toString().isEmpty() ){
                    saveItem(view);
                }
                else{
                    Snackbar.make(view,"EMPTY FIELDS NOT ALLOWED",Snackbar.LENGTH_SHORT).show();
                }

            }
        });


        builder.setView(view);
        alertDialog =builder.create();
        alertDialog.show();
    }
    private void saveItem(View view)
    {
        Item item=new Item();
        String newItem = babyitem.getText().toString().trim();
        int newQuantity =Integer.parseInt(itemquantity.getText().toString());
        String newcolor=itemcolor.getText().toString();
        int newsize =Integer.parseInt(itemsize.getText().toString());
        item.setItemname(newItem);
        item.setItemvolor(newcolor);
        item.setItemQuantity(newQuantity);
        item.setItemsize(newsize);
        databaseHandler.additem(item);
        Snackbar.make(view,"ITEM SAVED",Snackbar.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                alertDialog.dismiss();
                startActivity(new Intent(ListActivity.this,ListActivity.class));
                finish();
            }
        },1200);//1 sec


    }
}
