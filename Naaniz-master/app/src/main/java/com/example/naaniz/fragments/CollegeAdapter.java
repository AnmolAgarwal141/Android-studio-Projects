package com.example.naaniz.fragments;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.naaniz.R;

import java.util.ArrayList;

public class CollegeAdapter extends ArrayAdapter<Grocery> {
    public CollegeAdapter(@NonNull Context context, ArrayList<Grocery> groceries) {
        super(context,0, groceries);
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View list = convertView;
        if(list==null)
        {
            list =  LayoutInflater.from(getContext()).inflate(R.layout.listview,parent,false);
        }
        Grocery a = getItem(position);
        TextView name = (TextView) list.findViewById(R.id.text);
        name.setText(a.getName());
        return list;
    }
}
