package com.example.earthquakeapp.Ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.earthquakeapp.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

public class Custominfowindow implements GoogleMap.InfoWindowAdapter {
    private View view;
    private Context context;
    private LayoutInflater layoutInflater;
    public Custominfowindow(Context context){
        this.context=context;
        layoutInflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view=layoutInflater.inflate(R.layout.custom_info_window,null);

    }
    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        TextView title =view.findViewById(R.id.wintitle);
        title.setText(marker.getTitle());
        TextView magnitude = view.findViewById(R.id.magnitude);
        magnitude.setText(marker.getSnippet());
        return view;
    }
}
