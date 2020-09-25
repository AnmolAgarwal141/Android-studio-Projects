package com.example.naaniz.fragments;

public class Grocery {

    private String latd,longtd,name,place_id;

    public Grocery(String latd, String longtd, String name,String place_id) {
        this.latd = latd;
        this.longtd = longtd;
        this.name = name;
        this.place_id=place_id;
    }

    public String getPlace_id() {
        return place_id;
    }

    public String getLatd() {
        return latd;
    }

    public String getLongtd() {
        return longtd;
    }

    public String getName() {
        return name;
    }
}
