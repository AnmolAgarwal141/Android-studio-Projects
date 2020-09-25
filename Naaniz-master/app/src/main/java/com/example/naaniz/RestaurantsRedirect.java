package com.example.naaniz;

public class RestaurantsRedirect {
    private String name;
    private String location;
    private String swiggyUrl;
    private String zomatoUrl;

    public RestaurantsRedirect(String name, String location, String swiggyUrl, String zomatoUrl) {
        this.name = name;
        this.location = location;
        this.swiggyUrl = swiggyUrl;
        this.zomatoUrl = zomatoUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getSwiggyUrl() {
        return swiggyUrl;
    }

    public void setSwiggyUrl(String swiggyUrl) {
        this.swiggyUrl = swiggyUrl;
    }

    public String getZomatoUrl() {
        return zomatoUrl;
    }

    public void setZomatoUrl(String zomatoUrl) {
        this.zomatoUrl = zomatoUrl;
    }
}
