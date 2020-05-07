package com.example.nodo.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nodo.R;
import com.example.nodo.model.Nodo;

import java.net.ContentHandler;
import java.util.List;

public class NodoListAdapter extends RecyclerView.Adapter <NodoListAdapter.NoDoViewHolder>{

    private final LayoutInflater nodoInflater;
    private List<Nodo> nodoList;
    public NodoListAdapter(Context context){
        nodoInflater = LayoutInflater.from(context);
    }
    @NonNull
    @Override
    public NoDoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view =nodoInflater.inflate(R.layout.recyclerview_item,parent,false);
        return new NoDoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoDoViewHolder holder, int position) {
        if(nodoList != null){
            Nodo current =nodoList.get(position);
            holder.nodotextview.setText(current.getNoDo());
            }
        else {
            holder.nodotextview.setText("No no todo");

        }
    }
    public void setNodo(List<Nodo> nodos){
        nodoList=nodos;
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        if(nodoList!=null)
        return nodoList.size();
        else return 0;
    }

    public class NoDoViewHolder extends RecyclerView.ViewHolder {
        public TextView nodotextview;
        public NoDoViewHolder(@NonNull View itemView) {
            super(itemView);
            nodotextview = itemView.findViewById(R.id.textview);
        }
    }
}
