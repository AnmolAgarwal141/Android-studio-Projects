package com.example.naaniz.ui.main;

public class DishesInfo2 {
    private String name,price;
    private boolean isSelected=false;
    public boolean showcheckbox=false;

    public DishesInfo2(String name, String price) {
        this.name = name;
        this.price = price;
    }
    public String getName() {
        return name;
    }
    public String getPrice() {
        return price;
    }
    public void setSelected(boolean selected){
        isSelected=selected;
    }
    public Boolean isSelected(){
        return isSelected;
    }
}
