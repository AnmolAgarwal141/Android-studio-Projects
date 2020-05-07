package com.example.nodo;

import android.content.Intent;
import android.os.Bundle;

import com.example.nodo.model.Nodo;
import com.example.nodo.model.NodoViewModel;
import com.example.nodo.ui.NodoListAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private NodoListAdapter nodoListAdapter;
    private NodoViewModel nodoViewModel;
    private static  final int New_Nodo_request_code=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        nodoViewModel = ViewModelProviders.of(this).get(NodoViewModel.class);
        RecyclerView recyclerView=findViewById(R.id.recyclerview);
        nodoListAdapter=new NodoListAdapter(this);
        recyclerView.setAdapter(nodoListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(MainActivity.this,newNodoActivity.class);
                startActivityForResult(intent,New_Nodo_request_code);

            }
        });
        nodoViewModel.getAllNodos().observe(this, new Observer<List<Nodo>>() {
            @Override
            public void onChanged(List<Nodo> nodos) {
                nodoListAdapter.setNodo(nodos);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==New_Nodo_request_code&& resultCode ==RESULT_OK){
            assert data != null;
            Nodo nodo=new Nodo(data.getStringExtra(newNodoActivity.Extra_reply));
            nodoViewModel.insert(nodo);
        }
        else {
            Toast.makeText(this, "NOT SAVED", Toast.LENGTH_SHORT).show();
        }
    }
}
