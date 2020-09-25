package com.example.naaniz;

public class UserRecipeIngredients {
    private String name;
    private String qty;

    public UserRecipeIngredients(String name, String qty) {
        this.name = name;
        this.qty = qty;
    }

    public String getNameUser() {
        return name;
    }

    public String getQnt() {
        return qty;
    }
}
