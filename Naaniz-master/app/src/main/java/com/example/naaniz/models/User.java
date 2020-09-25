package com.example.naaniz.models;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class User implements Serializable {
    String username;
    String phoneNumber;
    String food_choice;
    Double lat;
    Double lng;
    String picturePanCard;
    String pictureAadhar;
    String pictureCancel;
    String pictureFSSAI;
    List<Dish> Menu = new ArrayList<>();

    public String getPictureFSSAI() {
        return pictureFSSAI;
    }

    public String getUsername() {
        return username;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getFood_choice() {
        return food_choice;
    }

    public Double getLat() {
        return lat;
    }

    public Double getLng() {
        return lng;
    }

    public String getPicturePanCard() {
        return picturePanCard;
    }

    public String getPictureAadhar() {
        return pictureAadhar;
    }

    public String getPictureCancel() {
        return pictureCancel;
    }

    public void setPictureFSSAI(String pictureFSSAI) {
        this.pictureFSSAI = pictureFSSAI;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setFood_choice(String food_choice) {
        this.food_choice = food_choice;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public void setPicturePanCard(String picturePanCard) {
        this.picturePanCard = picturePanCard;
    }

    public void setPictureAadhar(String pictureAadhar) {
        this.pictureAadhar = pictureAadhar;
    }

    public void setPictureCancel(String pictureCancel) {
        this.pictureCancel = pictureCancel;
    }

    public void addToMenu(Dish dish){
        Menu.add(dish);
    }
}
