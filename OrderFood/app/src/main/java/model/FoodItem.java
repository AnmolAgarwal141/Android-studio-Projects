package model;

public class FoodItem {
    private String fooditemname;
    private int foodprice;
    private String imageuri;
    private String userid;
    private String restaurantname;
    private String AccountType;

    public FoodItem(){}

    public String getFooditemname() {
        return fooditemname;
    }

    public String getRestaurantname() {
        return restaurantname;
    }

    public void setRestaurantname(String restaurantname) {
        this.restaurantname = restaurantname;
    }

    public String getAccountType() {
        return AccountType;
    }

    public void setAccountType(String accountType) {
        AccountType = accountType;
    }

    public void setFooditemname(String fooditemname) {
        this.fooditemname = fooditemname;
    }

    public int getFoodprice() {
        return foodprice;
    }

    public void setFoodprice(int foodprice) {
        this.foodprice = foodprice;
    }

    public String getImageuri() {
        return imageuri;
    }

    public void setImageuri(String imageuri) {
        this.imageuri = imageuri;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

}
