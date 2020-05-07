package com.example.nodo.data;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.nodo.model.Nodo;

@Database(entities = {Nodo.class},version = 1)
public abstract class NodoRoomDatabase extends RoomDatabase {

    private static volatile NodoRoomDatabase Instance;
    public abstract NoDoDao noDoDao();

    public static NodoRoomDatabase getDatabase(final Context context){
        if(Instance == null){
            synchronized (NodoRoomDatabase.class){
                if(Instance == null){
                    Instance = Room.databaseBuilder(context.getApplicationContext(),NodoRoomDatabase.class,"nodo_database").addCallback(roomdatabasecallback).build();
                }
            }
        }
        return Instance;
    }
    private static RoomDatabase.Callback roomdatabasecallback =new RoomDatabase.Callback(){
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
            new PopulatedbAsyn(Instance).execute();
        }
    };

    private static class PopulatedbAsyn extends AsyncTask<Void,Void,Void> {
        private final NoDoDao noDoDao;
        public PopulatedbAsyn(NodoRoomDatabase db) {
            noDoDao=db.noDoDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
           // noDoDao.deleteAll();
        //    Nodo nodo = new Nodo("BUY A NEW FERRARI");
          //  noDoDao.insert(nodo);
           // nodo =new Nodo("BUY A BIG HOUSE");
            //noDoDao.insert(nodo);
            return null;
        }
    }
}
