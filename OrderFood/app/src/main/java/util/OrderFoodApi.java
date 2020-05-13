package util;

import android.app.Application;

public class OrderFoodApi extends Application {
    public String Accountype;
    public String userid;
    private static OrderFoodApi instance;
    public static OrderFoodApi getInstance(){
        if(instance==null){
            instance=new OrderFoodApi();
        }
        return instance;
    }
    public OrderFoodApi(){}

    public String getAccountype() {
        return Accountype;
    }

    public void setAccountype(String accountype) {
        Accountype = accountype;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }
}
