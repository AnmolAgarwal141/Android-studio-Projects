package com.example.babyneeds.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.strictmode.SqliteObjectLeakedViolation;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.babyneeds.model.Item;
import com.example.babyneeds.util.Constants;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {
    private final Context context;
    public DatabaseHandler(@Nullable Context context) {
        super(context, Constants.DATABASE_NAME,null,Constants.DATABASE_VERSION);
        this.context=context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String Create_baby_table="CREATE TABLE "+Constants.TABLE_NAME+"("+Constants.KEY_ID+" INTEGER PRIMARY KEY,"+Constants.KEY_ITEM+" TEXT,"+Constants.KEY_QTY+" INTEGER,"+Constants.KEY_Color+" TEXT,"+Constants.KEY_SIZE+" INTEGER,"+Constants.KEY_DATE_NAME+" LONG"+ ")";
        sqLiteDatabase.execSQL(Create_baby_table);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+Constants.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
    public void additem(Item item)
    {
        SQLiteDatabase db= this.getWritableDatabase();
        ContentValues values =new ContentValues();
        values.put(Constants.KEY_ITEM,item.getItemname());
        values.put(Constants.KEY_QTY,item.getItemQuantity());
        values.put(Constants.KEY_Color,item.getItemvolor());
        values.put(Constants.KEY_SIZE,item.getItemsize());
        values.put(Constants.KEY_DATE_NAME,java.lang.System.currentTimeMillis());
        db.insert(Constants.TABLE_NAME,null,values);
        Log.d("DBHANDLER","ITEM ADDED");

    }
    public Item getitem(int id){
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.query(Constants.TABLE_NAME,new String[]{Constants.KEY_ID,Constants.KEY_ITEM,Constants.KEY_QTY,Constants.KEY_Color,Constants.KEY_SIZE,Constants.KEY_DATE_NAME},Constants.KEY_ID+ "=?",new String[]{String.valueOf(id)},null,null,null,null);
        if(cursor != null)
            cursor.moveToFirst();
        Item item =new Item();
        item.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Constants.KEY_ID))));
        item.setItemname(cursor.getString(cursor.getColumnIndex(Constants.KEY_ITEM)));
        item.setItemQuantity(cursor.getInt(cursor.getColumnIndex(Constants.KEY_QTY)));
        item.setItemvolor(cursor.getString(cursor.getColumnIndex(Constants.KEY_Color)));
        item.setItemsize(cursor.getInt(cursor.getColumnIndex(Constants.KEY_SIZE)));
        DateFormat dateFormat = DateFormat.getDateInstance();
        String formatteddate =dateFormat.format(new Date(cursor.getLong(cursor.getColumnIndex(Constants.KEY_DATE_NAME))).getTime());
        item.setDateItemAdded(formatteddate);

        return item;
    }
    public List<Item> getallitems(){
        SQLiteDatabase db =this.getReadableDatabase();
        List<Item> itemList = new ArrayList<>();

        Cursor cursor=db.query(Constants.TABLE_NAME,new String[]{Constants.KEY_ID,Constants.KEY_ITEM,Constants.KEY_QTY,Constants.KEY_Color,Constants.KEY_SIZE,Constants.KEY_DATE_NAME},null,null,null,null,Constants.KEY_DATE_NAME+ " DESC",null);
        if(cursor.moveToFirst())
        {
            do{
                Item item =new Item();
                item.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Constants.KEY_ID))));
                item.setItemname(cursor.getString(cursor.getColumnIndex(Constants.KEY_ITEM)));
                item.setItemQuantity(cursor.getInt(cursor.getColumnIndex(Constants.KEY_QTY)));
                item.setItemvolor(cursor.getString(cursor.getColumnIndex(Constants.KEY_Color)));
                item.setItemsize(cursor.getInt(cursor.getColumnIndex(Constants.KEY_SIZE)));
                DateFormat dateFormat = DateFormat.getDateInstance();
                String formatteddate =dateFormat.format(new Date(cursor.getLong(cursor.getColumnIndex(Constants.KEY_DATE_NAME))).getTime());
                item.setDateItemAdded(formatteddate);

                itemList.add(item);
            }while (cursor.moveToNext());
        }
        return itemList;
    }
    public int updateitem(Item item){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values =new ContentValues();
        values.put(Constants.KEY_ITEM,item.getItemname());
        values.put(Constants.KEY_QTY,item.getItemQuantity());
        values.put(Constants.KEY_Color,item.getItemvolor());
        values.put(Constants.KEY_SIZE,item.getItemsize());
        values.put(Constants.KEY_DATE_NAME,java.lang.System.currentTimeMillis());

        return db.update(Constants.TABLE_NAME,values,Constants.KEY_ID+"=?",new String[]{String.valueOf(item.getId())});

    }
    public void deleteitem(int id){
        SQLiteDatabase db =this.getWritableDatabase();
        db.delete(Constants.TABLE_NAME,Constants.KEY_ID+"=?",new String[]{String.valueOf(id)});
        db.close();
    }
    public int getitemcount(){
        String countquery = "SELECT * FROM "+Constants.TABLE_NAME;
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor =db.rawQuery(countquery,null);
        return cursor.getCount();
    }
}
