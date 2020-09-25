package com.example.naaniz.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.naaniz.OrderPersons;
import com.example.naaniz.R;

import java.util.List;

public class OrderPersonAdapter extends ArrayAdapter<OrderPersons> {

    private Context context;
    int resource;

    public OrderPersonAdapter(@NonNull Context context, int resource, @NonNull List<OrderPersons> objects) {
        super(context, resource, objects);

        this.context = context;
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        String name = getItem(position).getName();
        String icon = getItem(position).getIcon();

        OrderPersons person = new OrderPersons(name, icon);

        LayoutInflater inflater = LayoutInflater.from(context);
        convertView = inflater.inflate(resource, parent, false);

        TextView textViewName = convertView.findViewById(R.id.username);
        ImageView imageViewIcon = convertView.findViewById(R.id.icon_pic);

        textViewName.setText(name);
        imageViewIcon.setImageResource(R.drawable.boarder_image_view);

        return convertView;
    }
}
