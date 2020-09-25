package com.example.naaniz.views;

public class ListOfRecipes {
    private String recipeTitle,recipeAuthor,phone,ready;


    public ListOfRecipes(String recipeTitle, String recipeAuthor, String phone,String ready) {
        this.recipeTitle = recipeTitle;
        this.recipeAuthor = recipeAuthor;
        this.phone = phone;
        this.ready=ready;
    }

    public String getReady() {
        return ready;
    }

    public String getPhone() {
        return phone;
    }

    public String getRecipeTitle() {
        return recipeTitle;
    }

    public String getRecipeAuthor() {
        return recipeAuthor;
    }
}
