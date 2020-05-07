package com.example.babyneeds.model;

public class Item {
    private String itemname;
    private  String itemvolor;
    private  int itemQuantity;
    private int itemsize;
    private String DateItemAdded;
    private int id;

    public Item(String itemname, String itemvolor, int itemQuantity, int itemsize, String dateItemAdded, int id) {
        this.itemname = itemname;
        this.itemvolor = itemvolor;
        this.itemQuantity = itemQuantity;
        this.itemsize = itemsize;
        DateItemAdded = dateItemAdded;
        this.id = id;
    }
    public Item()
    {}

    public Item(String itemname, String itemvolor, int itemQuantity, int itemsize, String dateItemAdded) {
        this.itemname = itemname;
        this.itemvolor = itemvolor;
        this.itemQuantity = itemQuantity;
        this.itemsize = itemsize;
        DateItemAdded = dateItemAdded;
    }

    public String getItemname() {
        return itemname;
    }

    public void setItemname(String itemname) {
        this.itemname = itemname;
    }

    public String getItemvolor() {
        return itemvolor;
    }

    public void setItemvolor(String itemvolor) {
        this.itemvolor = itemvolor;
    }

    public int getItemQuantity() {
        return itemQuantity;
    }

    public void setItemQuantity(int itemQuantity) {
        this.itemQuantity = itemQuantity;
    }

    public int getItemsize() {
        return itemsize;
    }

    public void setItemsize(int itemsize) {
        this.itemsize = itemsize;
    }

    public String getDateItemAdded() {
        return DateItemAdded;
    }

    public void setDateItemAdded(String dateItemAdded) {
        DateItemAdded = dateItemAdded;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
