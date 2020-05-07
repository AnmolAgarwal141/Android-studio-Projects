package com.example.tripplanner.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.tripplanner.Model.Handlingid;
import com.example.tripplanner.Model.Register;
import com.example.tripplanner.Model.Registerdetails;
import com.example.tripplanner.R;
import com.example.tripplanner.Util.Util;
// com.example.tripplanner.model.contact;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {
    public DatabaseHandler(Context context) {
        super(context, Util.DATABASE_NAME, null, Util.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_TripPlanner_TABLE = "CREATE TABLE " + Util.TABLE_NAME + "("+ Util.Register_ID + " TEXT," + Util.Register_Pass+ " TEXT" + ")";
        sqLiteDatabase.execSQL(CREATE_TripPlanner_TABLE);
        String CREATE_RegisterDetails_TABLE = "CREATE TABLE " + Util.TABLE_NAME2 + "("+ Util.Registerdetails_id + " TEXT," + Util.Registerdetails_email+ " TEXT" + Util.Registerdetails_name+"TEXT"+Util.Registerdetails_Phone+"TEXT"+Util.Registerdetails_bdate+"TEXT"+")";
        sqLiteDatabase.execSQL(CREATE_RegisterDetails_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String DROP_TABLE = String.valueOf(R.string.db_drop);
        sqLiteDatabase.execSQL(DROP_TABLE, new String[]{Util.DATABASE_NAME});
        onCreate(sqLiteDatabase);
    }

    public void Registered( Register register) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Util.Register_ID, register.getUserid());
        values.put(Util.Register_Pass, register.getPassword());
        db.insert(Util.TABLE_NAME, null, values);
        Log.d("DBHANDLER", "ITEM_ADDED");
        db.close();
    }
    public void addRegisterDetails( Registerdetails registerdetails) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Util.Registerdetails_id, registerdetails.getId());
        values.put(Util.Registerdetails_email, registerdetails.getEmail());
        values.put(Util.Registerdetails_name, registerdetails.getName());
        values.put(Util.Registerdetails_Phone, registerdetails.getPhone());
        values.put(Util.Registerdetails_bdate, registerdetails.getBdate());
        db.insert(Util.TABLE_NAME2, null, values);
        Log.d("DBHANDLER", "ITEM_ADDED");
        db.close();
    }

    public Register GetRegistered(String id) {
       SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(Util.TABLE_NAME, new String[]{Util.Register_ID, Util.Register_Pass}, Util.Register_ID + "=?", new String[]{id}, null, null, null);
        if (cursor != null)
           cursor.moveToFirst();
        Register register = new Register();
        register.setUserid(cursor.getString(0));
        register.setPassword(cursor.getString(1));
        return register;
    }

    //public List<contact> GetAllContacts() {
      //  SQLiteDatabase db = this.getReadableDatabase();
        //List<contact> contactList = new ArrayList<>();
        //String SelectALL = "SELECT * FROM " + Util.TABLE_NAME;
        //Cursor cursor = db.rawQuery(SelectALL, null);
        //if (cursor.moveToFirst()) {
          //  do {
            //    contact contacts = new contact();
              //  contacts.setId(Integer.parseInt(cursor.getString(0)));
                //contacts.setName(cursor.getString(1));
                //contacts.setPhonenumber(cursor.getString(2));
                //contactList.add(contacts);
            //} while (cursor.moveToNext());
        //}
      //  return contactList;
    //}

    //public int UpdateContact(contact contacts) {
      //  SQLiteDatabase db = this.getWritableDatabase();
       // ContentValues values = new ContentValues();
        //values.put(Util.KEY_NAME, contacts.getName());
        //values.put(Util.KEY_PHONE_NUMBER, contacts.getPhonenumber());
        //return db.update(Util.TABLE_NAME, values, Util.KEY_ID + "=?", new String[]{String.valueOf(contacts.getId())});
    //}

    //public void DeleteContact(contact contacts) {
      //  SQLiteDatabase db =this.getWritableDatabase();
       // db.delete(Util.TABLE_NAME,Util.KEY_ID+"=?",new String[]{String.valueOf(contacts.getId())});
        //db.close();
    //}
    public int GetCount(){
        String QUERY_COUNT ="SELECT * FROM "+Util.TABLE_NAME;
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor =db.rawQuery(QUERY_COUNT,null);
        return cursor.getCount();
    }
}