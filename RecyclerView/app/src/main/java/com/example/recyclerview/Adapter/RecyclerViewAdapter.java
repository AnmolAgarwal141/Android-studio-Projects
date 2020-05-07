package com.example.recyclerview.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recyclerview.Model.contact;
import com.example.recyclerview.R;

import org.w3c.dom.Text;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private Context context;
    private List<contact> contactList;

    public RecyclerViewAdapter(Context context, List<contact> contactList) {
        this.context = context;
        this.contactList = contactList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_row,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        contact contacts = contactList.get(position);
        holder.contactname.setText(contacts.getName());
        holder.phonenumber.setText(contacts.getPhonenumber());
    }


    @Override
    public int getItemCount() {
        return contactList.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView contactname;
        public TextView phonenumber;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            contactname=itemView.findViewById(R.id.name);
            phonenumber=itemView.findViewById(R.id.phonenumber);
        }
    }
}
