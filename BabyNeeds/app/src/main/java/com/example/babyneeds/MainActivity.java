package com.example.babyneeds;

import android.content.Intent;
import android.os.Bundle;

import com.example.babyneeds.data.DatabaseHandler;
import com.example.babyneeds.model.Item;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private AlertDialog.Builder builder;
    private AlertDialog dialog;
    private Button savebutton;
    private EditText babyitem,itemquantity,itemcolor,itemsize;
    private DatabaseHandler databaseHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        databaseHandler = new DatabaseHandler(this);
        bypassActivity();

        List<Item> items=databaseHandler.getallitems();
        for(Item item : items){
          //  Toast.makeText(this,""+item.getItemname(),Toast.LENGTH_SHORT).show();
        }


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createpopupdialog();
               // Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                 //       .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public void createpopupdialog()
    {
        builder=new AlertDialog.Builder(this);
        View view=getLayoutInflater().inflate(R.layout.popup,null);
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
        dialog=builder.create();
        dialog.show();

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
                dialog.dismiss();
                startActivity(new Intent(MainActivity.this,ListActivity.class));
            }
        },1200);//1 sec


    }
    private  void  bypassActivity()
    {
        if(databaseHandler.getitemcount()>0){
            startActivity(new Intent(MainActivity.this,ListActivity.class));
            finish();
        }
    }
}
