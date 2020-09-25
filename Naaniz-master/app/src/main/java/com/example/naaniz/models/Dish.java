package com.example.naaniz.models;

import java.io.Serializable;

public class Dish implements Serializable {
    private String name;
    private String taste;
    private String price;

    public Dish() { }

    public void setName(String name) {
        this.name = name;
    }

    public void setTaste(String taste) {
        this.taste = taste;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public String getTaste() {
        return taste;
    }

    public String getPrice() {
        return price;
    }
}
