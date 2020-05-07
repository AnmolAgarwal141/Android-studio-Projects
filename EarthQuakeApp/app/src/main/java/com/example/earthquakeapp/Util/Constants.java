package com.example.earthquakeapp.Util;

import java.util.Random;

public class Constants {
    public static  final  String url ="https://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_week.geojson";
    public static final int LIMIT=30;
    public static int randmint(int min , int max){
        return new Random().nextInt(max-min)+min;
    }
}
