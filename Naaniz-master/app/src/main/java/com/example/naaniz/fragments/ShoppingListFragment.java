package com.example.naaniz.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.naaniz.R;
import com.example.naaniz.UserRecipeIngredients;

import java.util.ArrayList;

public class ShoppingListFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private TextView show_ingredient_name;

    private ArrayList<UserRecipeIngredients> a=new ArrayList<>();
    private String all_ingredients;

    private EditText ingredient_name, ingredient_qnt;
    private Button buttonAdd;

    public ShoppingListFragment() {
        // Required empty public constructor
    }

    public static ShoppingListFragment newInstance(String param1, String param2) {
        ShoppingListFragment fragment = new ShoppingListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_shopping_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        buttonAdd = view.findViewById(R.id.add_list);
        ingredient_name = view.findViewById(R.id.ingredient_name);
        ingredient_qnt = view.findViewById(R.id.ingredient_qnt);

        show_ingredient_name = view.findViewById(R.id.show_ingredient_name);

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                add();
            }
        });
    }

    public void add(){

        if(show_ingredient_name.getText().toString().equals("Ingredient List") && !ingredient_name.getText().toString().equals("")){

            show_ingredient_name.setText("");
        }

        if(!ingredient_name.getText().toString().equals("") && !ingredient_qnt.getText().toString().equals("")){

            all_ingredients = ingredient_name.getText().toString();
            show_ingredient_name.append(all_ingredients + " : " + ingredient_qnt.getText().toString().trim() +"\n");
            a.add(new UserRecipeIngredients(ingredient_name.getText().toString().trim(),ingredient_qnt.getText().toString().trim()));
            ingredient_name.setText("");
            ingredient_qnt.setText("");
        }
        else {
            Toast.makeText(getContext(), "Enter ingredient name first", Toast.LENGTH_SHORT).show();
        }
    }
}
