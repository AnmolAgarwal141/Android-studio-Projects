package com.example.naaniz.fragments;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.naaniz.DialogForDownload;
import com.example.naaniz.FilterActivity;
import com.example.naaniz.R;
import com.example.naaniz.ShowDish;
import com.example.naaniz.ShowRecipe;
import com.example.naaniz.models.Dish;
import com.example.naaniz.models.Filter;
import com.example.naaniz.models.Restaurant;
import com.example.naaniz.views.RestaurantView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;

public class HomeFragment extends Fragment {
    private LinearLayout recipeLayout;
    private TextView restText;
    private SearchView searchText;
    private ArrayList<Restaurant> listRestaurant = new ArrayList<>();
    private Spinner sortSpinner;
    private String city;
    private String locality;
    private Button toRecipe;
    private Button filterButton;
    private FloatingActionButton d;
    private int STORAGE_CODE=10;
    private StringBuilder savedPDF=new StringBuilder();

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference parentRef = database.getReference("restaurants");
    private DatabaseReference ref;

    Filter filter;
    String textSearched;
    List<Restaurant> filteredList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recipeLayout = view.findViewById(R.id.recipe_list_layout);
        sortSpinner = view.findViewById(R.id.sort_spinner);
        searchText = view.findViewById(R.id.home_search_bar);
        toRecipe = view.findViewById(R.id.to_recipe);
        d = (FloatingActionButton)view.findViewById(R.id.downld_Button);
        filterButton = view.findViewById(R.id.filter);
        filter = new Filter();
        if(getArguments().getString("query")!=null) {
            filter.setSearchText(getArguments().getString("query"));
            textSearched = getArguments().getString("query");
            searchText.setQuery(getArguments().getString("query"),true);
        }
        city = "hyderabad"; // city = getArguments().getString("city");
        locality = "Himayath Nagar"; // locality = getArguements.getString("locality");
        ref = parentRef.child(city).child(locality).getRef();
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.d("data_list",dataSnapshot.getValue().toString());
                setRestaurants(dataSnapshot);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });
    }

    private void setRestaurants(DataSnapshot dataSnapshot) {

        Log.d("data_list", "settings");
        Log.d("data_list", "dataSnapshot key : " + dataSnapshot.getKey());
        for(DataSnapshot r : dataSnapshot.getChildren()){
            Restaurant restaurant = new Restaurant();
            restaurant.setName(r.getKey());
            Log.d("data_details","Restaurant : "+r.getKey());
            for(DataSnapshot d : r.getChildren()) {
                if (d.getKey().equals("rating"))
                    restaurant.setRating(d.getValue().toString());
                else if (d.getKey().equals("price"))
                    restaurant.setPrice(d.getValue().toString());
                else if (d.getKey().equals("category"))
                    restaurant.setCategory(d.getValue().toString());
                else if(!d.getKey().equals("null")){
                    Dish dish = d.getValue(Dish.class);
                    dish.setName(d.getKey());
                    restaurant.addDish(dish);
                }
            }
            if(restaurant.getRating()==null)
                restaurant.setRating("NA");
            listRestaurant.add(restaurant);

            LinkedHashSet<Restaurant> hashSet = new LinkedHashSet<>(listRestaurant);
            ArrayList<Restaurant> listNoDuplicate = new ArrayList<>(hashSet);
            listRestaurant = listNoDuplicate;

            //addCard(restaurant);
        }

        addToSavedPDF();
        filteredList.addAll(listRestaurant);
        setFilter();
        initListeners();
    }

    private void setRatingIncrease() {
        Collections.sort(filteredList, (o1, o2) -> Float.compare(Float.parseFloat((o1.getRating().equals("NA"))?"5.0":o1.getRating()), Float.parseFloat((o2.getRating().equals("NA"))?"5.0":o2.getRating())));
        resetRecipeLayout();
    }

    private void setRatingDecrease() {
        Collections.sort(filteredList, (o1, o2) -> Float.compare(Float.parseFloat((o1.getRating().equals("NA"))?"0.0":o1.getRating()), Float.parseFloat((o2.getRating().equals("NA"))?"0.0":o2.getRating())));
        Collections.reverse(filteredList);
        resetRecipeLayout();
    }

    private void setPriceIncreasing() {
        Collections.sort(filteredList, (o1, o2) -> Float.compare(o1.getPricePerPerson(), o2.getPricePerPerson()));
        resetRecipeLayout();
    }

    private void setPriceDecreasing() {
        Collections.sort(filteredList, (o1, o2) -> Float.compare(o1.getPricePerPerson(), o2.getPricePerPerson()));
        Collections.reverse(filteredList);
        resetRecipeLayout();
    }

    private void resetRecipeLayout()
    {
        recipeLayout.removeAllViews();
        for (Restaurant res : filteredList) {
            addCard(res);
        }
    }

    private void addCard(Restaurant restaurant) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        RestaurantView newRestaurant = new RestaurantView(getActivity().getBaseContext());
        newRestaurant.setLayoutParams(params);
        newRestaurant.setName(restaurant.getName());
        newRestaurant.setRating(restaurant.getRating());
        newRestaurant.setPrice(restaurant.getPrice());
        newRestaurant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ShowDish.class);
                intent.putExtra("phone",getArguments().getString("phone"));
                intent.putExtra("restaurant",restaurant);
                startActivity(intent);
            }
        });
    recipeLayout.addView(newRestaurant);
    }

    private void initListeners() {
        toRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openRecipe();
            }
        });

        d.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
                    if (getActivity().checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                        String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
                        requestPermissions(permissions, STORAGE_CODE);
                    } else {
                        savePdf();
                    }
                }
            }
        });

        sortSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch ((String) parent.getItemAtPosition(position)) {
                    case "Ratings Increasing" : {
                        setRatingIncrease();
                        break;
                    }
                    case "Ratings Decreasing" : {
                        setRatingDecrease();
                        break;
                    }
                    case "Price Low to High": {
                        setPriceIncreasing();
                        break;
                    }
                    case "Price High to Low" : {
                        setPriceDecreasing();
                        break;
                    }
                    default: {
                        Toast.makeText(getActivity().getBaseContext(), "Invalid", Toast.LENGTH_SHORT).show();
                    }
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });

        searchText.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if(query.isEmpty()) {
                    Log.d("search_bar","empty");
                    textSearched = "";
                    setFilter();
                    setRatingDecrease();
                }
                else {
                    String search = query;
                    filter.setSearchText(query);

                    Log.d("receiveFilter","Inside SearchText Listener, filter.choices_taste : "+filter.searchText);
                    setFilter();
//                    ArrayList<Restaurant> searchList = new ArrayList<>();
//                    for (Restaurant res : listRestaurant) {
//                        if (res.getName().toLowerCase().contains(search.toLowerCase())) {
//                            searchList.add(res);
//                        } else {
//                            for (Dish dish : res.getDishes()) {
//                                if (dish.getName().toLowerCase().contains(search.toLowerCase())) {
//                                    searchList.add(res);
//                                } } } }
//                    //resetRecipeLayout(searchList);
                }
                return true;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                if(newText.equals(""))
                    this.onQueryTextSubmit("");
                return true;
            }});

        filterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), FilterActivity.class);
                filter.searchText = searchText.getQuery().toString();
                Log.d("receiveFilter","Before sending to Filter Activity");
                Log.d("receiveFIlter", "filter choice_prices : "+filter.choices_prices.size());
                Log.d("receiveFIlter", "filter choice_tastes : "+filter.choices_tastes.size());
                intent.putExtra("filter",filter);
                startActivityForResult(intent,011020);
            }
        });
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if(requestCode == 011020 ){
            filter = (Filter)data.getSerializableExtra("filter");
            Log.d("receivedFilter","recieved filter");
            Log.d("receiveFilter","After returning from Filter Activity");
            Log.d("receiveFIlter", "filter choice_prices : "+filter.choices_prices.size());
            Log.d("receiveFIlter", "filter choice_tastes : "+filter.choices_tastes.size());

            for(Boolean b : filter.choices_prices)
                Log.d("receiveFilter","filter choice prices : "+b);
            filter.searchText = searchText.getQuery().toString();
            Log.d("receiveFilter","searchText.getQuery : "+searchText.getQuery().toString());
            for(Boolean b : filter.choices_tastes)
                Log.d("receiveFilter","filter taste prices : "+b);

            if(resultCode==1) // apply clicked in filter activity
            setFilter();
            else //close clicked in filter activity
            {
                filteredList = new ArrayList<>();
                filteredList.addAll(listRestaurant);
            }
        }
    }

    private void setFilter(){
        Log.d("TAGAT","textSearched : "+textSearched);
        if(textSearched!=null)
            filter.setSearchText(textSearched);
        filteredList = new ArrayList<>();

        Log.d("receivedFilter","filter.isAnyFilterApplied() : "+filter.isAnyFilterApplied());
        recipeLayout.removeAllViews();
        if(!filter.isAnyFilterApplied()) {
            filteredList = listRestaurant;
        }
        else {
            for (Restaurant restaurant : listRestaurant) {
                if (!filter.isTasteFilterApplied() && filter.isValidRestaurant(restaurant)) { // when no taste filter applied, whole restaurant can be considered
                    filteredList.add(restaurant);
                    addCard(restaurant);
                } else {
                    Restaurant filteredRestaurant = new Restaurant();
                    filteredRestaurant.setName(restaurant.getName());
                    filteredRestaurant.setRating(restaurant.getRating());
                    filteredRestaurant.setCategory(restaurant.getCategory());
                    filteredRestaurant.setPrice(restaurant.getPrice());

                    for(Dish dish : restaurant.getDishes()) {
                        if(filter.isValidDish(dish,restaurant)){ filteredRestaurant.addDish(dish); }
                    }

                    if(filteredRestaurant.getDishes().size()>0){
                        filteredList.add(filteredRestaurant);
                        addCard(filteredRestaurant);
                    }
                }
            }
        }
        resetRecipeLayout();
    }
    private void openRecipe() {
        Intent intent = new Intent(getActivity(), ShowRecipe.class);
        intent.putExtra("parentRef","recipes");
        intent.putExtra("dish", "Vegetable Chow Mein");
        startActivity(intent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==STORAGE_CODE)
        {
            if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED)
            {
                savePdf();
            }
            else
            {
                Toast.makeText(getActivity(),"permission denied",Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void addToSavedPDF() {
        for(Restaurant restaurant : listRestaurant){
            savedPDF.append(restaurant.getName()+"\n");
            int count = 1;
            for(Dish dish : restaurant.getDishes()){
                savedPDF.append(count++ +".\t"+dish.getName()+"\n");
            }
        }
    }
    public void savePdf() {

        DialogForDownload dialogForDownload = new DialogForDownload();
        dialogForDownload.show(getChildFragmentManager(),"dfdf");
    }

    @Override
    public void onResume(){
        super.onResume();
        setFilter();
    }
}
