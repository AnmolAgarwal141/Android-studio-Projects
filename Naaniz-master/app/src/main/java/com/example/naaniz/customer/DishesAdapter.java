package com.example.naaniz.customer;



import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.naaniz.R;

import java.util.ArrayList;


public class DishesAdapter extends RecyclerView.Adapter<DishesAdapter.RecycleHolder> {

    private ArrayList<DishesInfo> mEa;
    public class RecycleHolder extends RecyclerView.ViewHolder
    {
        private TextView t1,t2;
        public RecycleHolder(@NonNull View itemView) {
            super(itemView);
            t1=  (TextView)itemView.findViewById(R.id.dish_name);
            t2 = (TextView)itemView.findViewById(R.id.dish_price);
        }
    }
    public DishesAdapter(ArrayList<DishesInfo> a)
    {
        mEa=a;
    }
    @NonNull
    @Override
    public RecycleHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_selected_cart,parent,false);
        RecycleHolder recycleHolder = new RecycleHolder(view);
        return recycleHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecycleHolder holder, int position) {
        DishesInfo b = mEa.get(position);
        holder.t1.setText(b.getName());
        holder.t2.setText(b.getPrice());
    }

    @Override
    public int getItemCount() {
        return mEa.size();
    }
}
