package model;

public class Restaurant {
    private String restaurantname;
    private String restaurantaddress;
    private String resImageuri;
    private String userid;
    private String AccountType;
    public Restaurant(){}

    public String getAccountType() {
        return AccountType;
    }

    public void setAccountType(String accountType) {
        AccountType = accountType;
    }

    public String getRestaurantname() {
        return restaurantname;
    }

    public void setRestaurantname(String restaurantname) {
        this.restaurantname = restaurantname;
    }

    public String getRestaurantaddress() {
        return restaurantaddress;
    }

    public void setRestaurantaddress(String restaurantaddress) {
        this.restaurantaddress = restaurantaddress;
    }

    public String getResImageuri() {
        return resImageuri;
    }

    public void setResImageuri(String resImageuri) {
        this.resImageuri = resImageuri;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }
}
