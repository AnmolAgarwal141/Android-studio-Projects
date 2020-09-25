package com.example.naaniz.models;

public class Vendor {
    private String phone_no,location,name;

    public Vendor(String phone_no, String location, String name) {
        this.phone_no = phone_no;
        this.location = location;
        this.name = name;
    }

    public String getPhone_no() {
        return phone_no;
    }

    public String getLocation() {
        return location;
    }

    public String getName() {
        return name;
    }
}
