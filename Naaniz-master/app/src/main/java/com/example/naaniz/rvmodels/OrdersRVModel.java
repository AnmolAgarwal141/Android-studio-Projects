package com.example.naaniz.rvmodels;

public class OrdersRVModel {

    private String time;
    private String order_no;
    private String dish_name;
    private String dish_quantity;
    private String dish_price;
    private String total_cost;

    public OrdersRVModel(String time, String order_no, String dish_name, String dish_quantity, String dish_price, String total_cost) {
        this.time = time;
        this.order_no = order_no;
        this.dish_name = dish_name;
        this.dish_quantity = dish_quantity;
        this.dish_price = dish_price;
        this.total_cost = total_cost;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getOrder_no() {
        return order_no;
    }

    public void setOrder_no(String order_no) {
        this.order_no = order_no;
    }

    public String getDish_name() {
        return dish_name;
    }

    public void setDish_name(String dish_name) {
        this.dish_name = dish_name;
    }

    public String getDish_quantity() {
        return dish_quantity;
    }

    public void setDish_quantity(String dish_quantity) {
        this.dish_quantity = dish_quantity;
    }

    public String getDish_price() {
        return dish_price;
    }

    public void setDish_price(String dish_price) {
        this.dish_price = dish_price;
    }

    public String getTotal_cost() {
        return total_cost;
    }

    public void setTotal_cost(String total_cost) {
        this.total_cost = total_cost;
    }
}
