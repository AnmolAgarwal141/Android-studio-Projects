package com.example.naaniz.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.naaniz.R;
import com.example.naaniz.RestaurantsRedirect;

import java.util.List;
import java.util.Objects;
import java.util.zip.Inflater;

public class RestaurantAdapter extends ArrayAdapter<RestaurantsRedirect> {

    private Context context;
    int resource;

    public RestaurantAdapter(@NonNull Context context, int resource, @NonNull List<RestaurantsRedirect> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
    }

    @SuppressLint("ViewHolder")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        String name = Objects.requireNonNull(getItem(position)).getName();
        String location = Objects.requireNonNull(getItem(position)).getLocation();
        String swiggyUrl = Objects.requireNonNull(getItem(position)).getSwiggyUrl();
        String zomatoUrl = Objects.requireNonNull(getItem(position)).getZomatoUrl();



        RestaurantsRedirect restaurants = new RestaurantsRedirect(name, location, swiggyUrl, zomatoUrl);

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        convertView = layoutInflater.inflate(resource, parent, false);

        TextView textViewName = (TextView) convertView.findViewById(R.id.restaurant_name);
        textViewName.setText(name);
        TextView textViewLocation = (TextView) convertView.findViewById(R.id.restaurant_location);
        textViewLocation.setText(location);
        Button swiggyButton = (Button) convertView.findViewById(R.id.swiggy_redirect);
        swiggyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(swiggyUrl));
                context.startActivity(intent);
            }
        });
        Button zomatoButton = (Button) convertView.findViewById(R.id.zomato_redirect);
        zomatoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(zomatoUrl));
                context.startActivity(intent);
            }
        });

        return convertView;
    }
}
