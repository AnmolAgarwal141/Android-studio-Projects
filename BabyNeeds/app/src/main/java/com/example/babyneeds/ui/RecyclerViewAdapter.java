package com.example.babyneeds.ui;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;

import com.example.babyneeds.R;
import com.example.babyneeds.data.DatabaseHandler;
import com.example.babyneeds.model.Item;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class RecyclerViewAdapter extends Adapter<RecyclerViewAdapter.ViewHolder> {

    private Context context;
    private List<Item> itemList;
    private AlertDialog.Builder builder;
    private AlertDialog alertDialog;
    private LayoutInflater inflater;
    private Button savebutton;
    private EditText babyitem,itemquantity,itemcolor,itemsize;
    public RecyclerViewAdapter(Context context,List<Item> itemList){
        this.context=context;
        this.itemList=itemList;
    }

    @NonNull
    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row,parent,false);

        return new ViewHolder(view,context);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.ViewHolder holder, int position) {

      Item item =itemList.get(position);
      holder.itemname.setText("Item: "+item.getItemname());
      holder.itemQuantity.setText("Qty: "+String.valueOf(item.getItemQuantity()));
      holder.itemcolor.setText("color: "+item.getItemvolor());
      holder.itemsize.setText("size:"+String.valueOf(item.getItemsize()));
      holder.itemdate.setText("Added On: "+item.getDateItemAdded());
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView itemname;
        public TextView itemQuantity;
        public TextView itemcolor;
        public TextView itemsize;
        public TextView itemdate;
        public Button editbutton,deletebutton;
        public int id;
        public ViewHolder(@NonNull View itemView,Context ctx) {
            super(itemView);
            context=ctx;

            itemname=itemView.findViewById(R.id.item_name);
            itemQuantity=itemView.findViewById(R.id.item_quantity);
            itemcolor=itemView.findViewById(R.id.item_color);
            itemsize=itemView.findViewById(R.id.item_size);
            itemdate=itemView.findViewById(R.id.item_date);
            editbutton=itemView.findViewById(R.id.editButton);
            deletebutton=itemView.findViewById(R.id.deleteButton);

            editbutton.setOnClickListener(this);
            deletebutton.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position;
            position=getAdapterPosition();
            Item item=itemList.get(position);
            switch(view.getId()){
                case R.id.editButton:

                    edititem(item);
                    break;
                case R.id.deleteButton:

                    deleteitem(item.getId());
                    break;
            }
        }
        private void deleteitem(final int id){

            builder=new AlertDialog.Builder(context);
            inflater=LayoutInflater.from(context);
            View view=inflater.inflate(R.layout.confirmationpop,null);
            Button nobutton =view.findViewById(R.id.conf_no_button);
            Button yesbutton=view.findViewById(R.id.conf_yes_button);
            builder.setView(view);
            alertDialog=builder.create();
            alertDialog.show();

            nobutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    alertDialog.dismiss();
                }
            });
            yesbutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DatabaseHandler db=new DatabaseHandler(context);
                    db.deleteitem(id);
                    itemList.remove(getAdapterPosition());
                    notifyItemRemoved(getAdapterPosition());
                    alertDialog.dismiss();
                }
            });

        }
        private void edititem(final Item newitem){

            builder =new AlertDialog.Builder(context);
            inflater=LayoutInflater.from(context);
            View view= inflater.inflate(R.layout.popup,null);
            babyitem=view.findViewById(R.id.babyItem);
            itemquantity=view.findViewById(R.id.ItemQuantity);
            itemcolor=view.findViewById(R.id.ItemColor);
            itemsize=view.findViewById(R.id.ItemSize);
            TextView Title = view.findViewById(R.id.title);
            Title.setText("Edit Item");
            savebutton=view.findViewById(R.id.SaveButton);
            savebutton.setText("Update");
            babyitem.setText((newitem.getItemname()));
            itemquantity.setText(String.valueOf(newitem.getItemQuantity()));
            itemcolor.setText(newitem.getItemvolor());
            itemsize.setText(String.valueOf(newitem.getItemsize()));
            builder.setView(view);
            alertDialog=builder.create();
            alertDialog.show();
            savebutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DatabaseHandler db=new DatabaseHandler(context);
                    newitem.setItemname(babyitem.getText().toString());
                    newitem.setItemQuantity(Integer.parseInt(itemquantity.getText().toString()));
                    newitem.setItemvolor(itemcolor.getText().toString());
                    newitem.setItemsize(Integer.parseInt(itemsize.getText().toString()));

                    if(!babyitem.getText().toString().isEmpty() && !itemcolor.getText().toString().isEmpty() && !itemquantity.getText().toString().isEmpty() && !itemsize.getText().toString().isEmpty() ){
                        db.updateitem(newitem);
                        notifyItemChanged(getAdapterPosition(),newitem);
                        alertDialog.dismiss();
                    }
                    else{
                        Snackbar.make(view,"Fields Empty",Snackbar.LENGTH_SHORT).show();
                    }
                }
            });

        }
    }
}
