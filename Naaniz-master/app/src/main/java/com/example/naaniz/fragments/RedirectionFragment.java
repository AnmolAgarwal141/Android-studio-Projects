package com.example.naaniz.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.naaniz.R;
import com.example.naaniz.adapters.RestaurantAdapter;
import com.example.naaniz.RestaurantsRedirect;

import java.util.ArrayList;

public class RedirectionFragment extends Fragment {

    private ListView listView ;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_redirection, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        listView = view.findViewById(R.id.redirection_list_view);

        RestaurantsRedirect res1 = new RestaurantsRedirect("Dominos", "Vadodara", "https://www.swiggy.com/restaurants/dominos-pizza-geeta-colony-mayur-vihar-delhi-24192", "https://www.zomato.com/vadodara/restaurants/dominos-pizza");
        RestaurantsRedirect res2 = new RestaurantsRedirect("Barbeque Nation", "Vadodara", "https://www.swiggy.com/restaurants/barbeque-nation-hotel-savoy-suites-sector-18-noida-1-30390", "https://www.zomato.com/vadodara/barbeque-nation-vadiwadi");
        RestaurantsRedirect res3 = new RestaurantsRedirect("Retro Bistro", "Vadodara", "https://www.swiggy.com/restaurants/retro-bistro-omkar-shoping-center-gidc-ankleshwar-205569", "https://www.zomato.com/vadodara/retro-bistro-vadiwadi");

        ArrayList<RestaurantsRedirect> restaurants = new ArrayList<>();
        restaurants.add(res1);
        restaurants.add(res2);
        restaurants.add(res3);

        RestaurantAdapter adapter = new RestaurantAdapter(getContext(), R.layout.redirection_adapter_view_layout, restaurants);
        listView.setAdapter(adapter);


    }
}
