package com.example.nodo.util;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.room.RoomDatabase;

import com.example.nodo.data.NoDoDao;
import com.example.nodo.data.NodoRoomDatabase;
import com.example.nodo.model.Nodo;

import java.util.List;

public class NodoRepository {
    private NoDoDao noDoDao;
    private LiveData<List<Nodo>> allNodos;

    public NodoRepository(Application application) {
        NodoRoomDatabase db= NodoRoomDatabase.getDatabase(application);
        noDoDao =db.noDoDao();
        allNodos=noDoDao.getallnodo();
    }

    public LiveData<List<Nodo>> getAllNodos(){
        return allNodos;
    }
    public void insert(Nodo nodo) {
        new insertAsyncTask(noDoDao).execute(nodo);
    }

    private class insertAsyncTask  extends AsyncTask<Nodo,Void,Void> {
        private NoDoDao asyncTaskDao;
        public insertAsyncTask(NoDoDao dao) {
            asyncTaskDao=dao;
        }

        @Override
        protected Void doInBackground(Nodo... params) {
            asyncTaskDao.insert(params[0]);
            return null;
        }
    }
}
