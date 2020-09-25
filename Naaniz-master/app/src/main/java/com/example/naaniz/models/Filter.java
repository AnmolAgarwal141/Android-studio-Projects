package com.example.naaniz.models;

import android.util.Log;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Filter implements Serializable {
    public List<String> prices = new ArrayList<>();
    public List<Boolean> choices_prices = new ArrayList<>();
    public List<String> tastes = new ArrayList<>();
    public List<Boolean> choices_tastes = new ArrayList<>();
    public String searchText;
    public Filter() {
        prices = new ArrayList<>();
        choices_prices = new ArrayList<>();
        tastes = new ArrayList<>();
        choices_tastes = new ArrayList<>();
        prices.add("0-100");
        prices.add("100-150");
        prices.add("150-200");
        prices.add("200-250");
        prices.add("250-300");
        prices.add("300-350");
        prices.add("350+");

        tastes.add("Veg");
        tastes.add("NonVeg");
        resetFilters();
    }

    public void resetFilters() {
        for (int i = 0; i < 7; i++)
            choices_prices.add(false);
        choices_tastes.add(false);
        choices_tastes.add(false);
        searchText = "";
    }

    public void setSearchText(String searchText){
        this.searchText = searchText;
    }
    public Boolean isAnyFilterApplied() {
        boolean anyFilterApplied = (searchText!=null) && (searchText.length()>0);
        anyFilterApplied |= isPriceFilterApplied() || isTasteFilterApplied();

        return anyFilterApplied;
    }

    public Boolean isPriceFilterApplied(){
        boolean priceFilterApplied = false;
        for(Boolean b : choices_prices)
            priceFilterApplied |= b;
        return priceFilterApplied;
    }

    public Boolean isTasteFilterApplied(){
        return choices_tastes.get(0) || choices_tastes.get(1);
    }

    public List<String> getPrices(){return prices;}
    public List<String> getTastes(){return tastes;}

    public Boolean isValidRestaurant(Restaurant restaurant){
        boolean validRestaurant = true;

        if(searchText.length() != 0) //checks if the searchedText (if any) is present in either name or category of restaurant
            validRestaurant &= (restaurant.getName().toLowerCase().contains(searchText.toLowerCase())) || (restaurant.getCategory().toLowerCase().contains(searchText.toLowerCase()));//tolower so as to ignore the case

        boolean temp = false, isfilterUsed=false;
        Integer priceperperson = restaurant.getPricePerPerson().intValue();
        temp |= (priceperperson<=100) && choices_prices.get(0);
        isfilterUsed |= choices_prices.get(0);

        int lower,upper;//lower and upper limits of prices in filter
        for(int i=1;i<6;i++) {
            lower = 50*(i+1);
            upper = 50*(i+2);
            temp |= (priceperperson>=lower) && (priceperperson<=upper) && (choices_prices.get(i));
            isfilterUsed |= choices_prices.get(i);
        }

        temp |= (priceperperson>=350) && choices_prices.get(6);
        isfilterUsed |= choices_prices.get(6);

        if(isfilterUsed)//if filter is not used then no need to check for the restaurant to lie in any price range
            validRestaurant &= temp;

        if(searchText.length()!=0 && restaurant.getName().toLowerCase().contains(searchText.toLowerCase()))
        Log.d("receiveFilter","search : "+searchText+" : name : "+restaurant.getName()+" : validRestaurant : "+validRestaurant);
       return validRestaurant;
    }

    public Boolean isValidDish(Dish dish, Restaurant restaurant){
        boolean validDish = true;

        if(searchText.length()>0)
            validDish &= dish.getName().toLowerCase().contains(searchText.toLowerCase()) || restaurant.getName().toLowerCase().contains(searchText.toLowerCase()) || restaurant.getCategory().toLowerCase().contains(searchText.toLowerCase());

        boolean temp = false, isfilterUsed=false;
        Integer price = Integer.parseInt(dish.getPrice());
        temp |= (price<=100) && choices_prices.get(0);
        isfilterUsed |= choices_prices.get(0);
        int lower,upper;//lower and upper limits of prices in filter
        for(int i=1;i<6;i++) {
            lower = 50*(i+1);
            upper = 50*(i+2);
            temp |= (price>=lower) && (price<=upper) && (choices_prices.get(i));
            isfilterUsed |= choices_prices.get(i);
        }

        temp |= (price>=350) && choices_prices.get(6);
        isfilterUsed |= choices_prices.get(6);

        if(isfilterUsed)//if filter is not used then no need to check for the restaurant to lie in any price range
            validDish &= temp;
        //choice_tastes
        if(choices_tastes.get(0))//Veg clicked in filter
            validDish &= dish.getTaste().equalsIgnoreCase("Veg");
        else if(choices_tastes.get(1))//Non Veg clicked in filter
            validDish &= dish.getTaste().equalsIgnoreCase("Non Veg");

        Log.d("receiveFilter","dish name : "+dish.getName()+" : "+dish.getTaste()+" : "+validDish);

        return validDish;
    }
}
