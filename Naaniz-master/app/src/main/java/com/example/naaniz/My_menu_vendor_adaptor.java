package com.example.naaniz;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.naaniz.ui.main.DishesInfo2;

import java.util.ArrayList;

public class My_menu_vendor_adaptor extends RecyclerView.Adapter<My_menu_vendor_adaptor.RecycleHolder> {
    private ArrayList<DishesInfo2> mEa;
    public class RecycleHolder extends RecyclerView.ViewHolder
    {
        private TextView t1,t2;
        private Button selectbutton;
        public RecycleHolder(@NonNull View itemView) {
            super(itemView);
            t1=  (TextView)itemView.findViewById(R.id.dish_name);
            t2 = (TextView)itemView.findViewById(R.id.dish_price);
            selectbutton=itemView.findViewById(R.id.selectbutton);
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    showallboxes();
                    return false;
                }
            });
        }
    }
    public My_menu_vendor_adaptor(ArrayList<DishesInfo2> a)
    {
        mEa=a;
    }
    @NonNull
    @Override
    public RecycleHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_my_menu_row,parent,false);
        RecycleHolder recycleHolder = new RecycleHolder(view);
        return recycleHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecycleHolder holder, int position) {
        DishesInfo2 b = mEa.get(position);
        holder.t1.setText(b.getName());
        holder.t2.setText(b.getPrice());
        holder.selectbutton.setVisibility(b.showcheckbox ?View.VISIBLE:View.GONE);
        holder.selectbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                b.setSelected(!b.isSelected());
                notifyItemChanged(holder.getAdapterPosition());
            }
        });


    }



    @Override
    public int getItemCount() {
        return mEa.size();
    }
    private void showallboxes(){
        for(DishesInfo2 item:mEa){
            item.showcheckbox=true;
        }
        notifyDataSetChanged();
    }
    private void hideallboxes(){
        for(DishesInfo2 item:mEa){
            item.showcheckbox=false;
        }
        notifyDataSetChanged();
    }
}
