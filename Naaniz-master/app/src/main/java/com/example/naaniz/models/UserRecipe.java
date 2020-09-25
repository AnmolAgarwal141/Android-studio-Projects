package com.example.naaniz.models;

import com.example.naaniz.UserRecipeIngredients;

import java.util.ArrayList;

public class UserRecipe {

    private String instructions,title;
    private int readyInMinutes,servings;
    private ArrayList<UserRecipeIngredients> ingredients;

    public UserRecipe(String instructions, String title, int readyInMintutes, int servings, ArrayList<UserRecipeIngredients> ingredients) {
        this.instructions = instructions;
        this.title = title;
        this.readyInMinutes = readyInMintutes;
        this.servings = servings;
        this.ingredients = ingredients;
    }

    public String getInstructions() {
        return instructions;
    }

    public int getReadyInMintutes() {
        return readyInMinutes;
    }

    public int getServings() {
        return servings;
    }

    public String getTitle() {
        return title;
    }

    public ArrayList<UserRecipeIngredients> getIngredients() {
        return ingredients;
    }
}
