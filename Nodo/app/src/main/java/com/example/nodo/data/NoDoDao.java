package com.example.nodo.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.nodo.model.Nodo;

import java.util.List;

@Dao
public interface NoDoDao {

    @Insert
    void insert(Nodo nodo);

    @Query("Delete From nodo_table")
    void deleteAll();

    @Query("Delete from nodo_table where id= :id")
    int deleteanodo(int id);

    @Query("Update nodo_table Set nodo_col = :nodoText where id = :id")
    int updateNodoitem(int id,String nodoText);

    @Query("Select * from nodo_table Order by nodo_col desc")
    LiveData<List<Nodo>> getallnodo();
}
