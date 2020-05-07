package com.example.contactmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.contactmanager.data.DatabaseHandler;
import com.example.contactmanager.model.contact;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ListView listView;
    private ArrayList<String> contactstring;
    private ArrayAdapter<String> arrayAdapter ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView =findViewById(R.id.listview);
        contactstring = new ArrayList<>();
        DatabaseHandler db =new DatabaseHandler(MainActivity.this);
        contact Anmol =new contact();
        Anmol.setName("Anmol");
        Anmol.setPhonenumber("9741387092");
        contact Keshav =new contact();
        Keshav.setName("Keshav");
        Keshav.setPhonenumber("9584509522");
       // db.AddContacts(new contact("James","8523571"));
        //db.AddContacts(new contact("Joy","85242671"));
        //db.AddContacts(new contact("Adam","24723571"));
        //db.AddContacts(new contact("Steve","827223571"));
        //db.AddContacts(new contact("Erica","8427571"));
        //db.AddContacts(new contact("Stuart","85743571"));
        //db.AddContacts(new contact("Jen","852358"));
        //db.AddContacts(new contact("Jake","85297971"));
        //db.AddContacts(new contact("Sim","8805390"));
        //db.AddContacts(new contact("Taylor","049292571"));


       /// db.AddContacts(Keshav);


        //contacts.setName("Anmol");
        //contacts.setPhonenumber("9893457364");
        //int updatedRow =db.UpdateContact(contacts);
        //Log.d("UPDATE",String.valueOf(updatedRow));

        //contact contacts =db.GetContacts(5);
       // db.DeleteContact(contacts);

         int count=db.GetCount();

         Log.d("COUNT_DB",String.valueOf(count));
        List<contact> contactList=db.GetAllContacts();
        for(contact c : contactList)
        {
            Log.d("MainActivity", c.getName());
            contactstring.add(c.getName());
        }
        arrayAdapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,contactstring);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d("list", "onItemClick: "+contactstring.get(i));
            }
        });
    }
}
