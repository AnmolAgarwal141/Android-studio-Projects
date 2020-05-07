package com.example.recyclerview.Data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.recyclerview.R;
import com.example.recyclerview.Util.Util;
import com.example.recyclerview.Model.contact;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {
    public DatabaseHandler(Context context) {
        super(context, Util.DATABASE_NAME, null, Util.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_CONTACT_TABLE = "CREATE TABLE " + Util.TABLE_NAME + "(" + Util.KEY_ID + " INTEGER PRIMARY KEY," + Util.KEY_NAME + " TEXT," + Util.KEY_PHONE_NUMBER + " TEXT" + ")";
        sqLiteDatabase.execSQL(CREATE_CONTACT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String DROP_TABLE = String.valueOf(R.string.db_drop);
        sqLiteDatabase.execSQL(DROP_TABLE, new String[]{Util.DATABASE_NAME});
        onCreate(sqLiteDatabase);
    }

    public void AddContacts(contact contacts) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Util.KEY_NAME, contacts.getName());
        values.put(Util.KEY_PHONE_NUMBER, contacts.getPhonenumber());
        db.insert(Util.TABLE_NAME, null, values);
        Log.d("DBHANDLER", "ITEM_ADDED");
        db.close();
    }

    public contact GetContacts(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(Util.TABLE_NAME, new String[]{Util.KEY_ID, Util.KEY_NAME, Util.KEY_PHONE_NUMBER}, Util.KEY_ID + "=?", new String[]{String.valueOf(id)}, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
        contact contacts = new contact();
        contacts.setId(Integer.parseInt(cursor.getString(0)));
        contacts.setName(cursor.getString(1));
        contacts.setPhonenumber(cursor.getString(2));
        return contacts;
    }

    public List<contact> GetAllContacts() {
        SQLiteDatabase db = this.getReadableDatabase();
        List<contact> contactList = new ArrayList<>();
        String SelectALL = "SELECT * FROM " + Util.TABLE_NAME;
        Cursor cursor = db.rawQuery(SelectALL, null);
        if (cursor.moveToFirst()) {
            do {
                contact contacts = new contact();
                contacts.setId(Integer.parseInt(cursor.getString(0)));
                contacts.setName(cursor.getString(1));
                contacts.setPhonenumber(cursor.getString(2));
                contactList.add(contacts);
            } while (cursor.moveToNext());
        }
        return contactList;
    }

    public int UpdateContact(contact contacts) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Util.KEY_NAME, contacts.getName());
        values.put(Util.KEY_PHONE_NUMBER, contacts.getPhonenumber());
        return db.update(Util.TABLE_NAME, values, Util.KEY_ID + "=?", new String[]{String.valueOf(contacts.getId())});
    }

    public void DeleteContact(contact contacts) {
        SQLiteDatabase db =this.getWritableDatabase();
        db.delete(Util.TABLE_NAME,Util.KEY_ID+"=?",new String[]{String.valueOf(contacts.getId())});
        db.close();
    }
    public int GetCount(){
        String QUERY_COUNT ="SELECT * FROM "+Util.TABLE_NAME;
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor =db.rawQuery(QUERY_COUNT,null);
        return cursor.getCount();
    }
}