package com.example.naaniz.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Restaurant implements Serializable {
    private String name;
    private String rating;
    private String price;
    private List<Dish> dishes = new ArrayList<>();
    private String category;
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void addDish(Dish dish){
        dishes.add(dish);
    }

    public List<Dish> getDishes(){
        return dishes;
    }

    public Float getPricePerPerson()
    {
        String price1 = price.trim();
        Float num = Float.parseFloat(price.substring(1,price1.indexOf(' ')));
        Float person = (price.substring(price1.indexOf("FOR")+4).equals("TWO"))? 2f : 1f ;
        return num/person;
    }

    public void setCategory(String category){ this.category = category; }

    public String getCategory(){ return category; }
}
