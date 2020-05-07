package com.example.nodo.model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.nodo.util.NodoRepository;

import java.util.List;

public class NodoViewModel extends AndroidViewModel {
    private NodoRepository nodoRepository;
    private LiveData<List<Nodo>> allNodos;
    public NodoViewModel(@NonNull Application application) {
        super(application);
        nodoRepository=new NodoRepository(application);
        allNodos =nodoRepository.getAllNodos();
    }
    public LiveData<List<Nodo>> getAllNodos(){
        return allNodos;
    }
    public void insert(Nodo nodo){
        nodoRepository.insert(nodo);
    }
}
