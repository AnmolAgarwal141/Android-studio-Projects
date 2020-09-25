package com.example.naaniz.Checkout;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.naaniz.R;
import com.example.naaniz.customer.DishesAdapter;
import com.example.naaniz.customer.DishesInfo;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class Cart extends Fragment {

    public Cart() {
        // Required empty public constructor
    }

    ArrayList<DishesInfo> a =new ArrayList<>();
    RecyclerView recyclerView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view  = inflater.inflate(R.layout.fragment_cart, container, false);
        recyclerView= (RecyclerView) view.findViewById(R.id.rec);

        a.add(new DishesInfo("asdasd","asdas12323d"));
        a.add(new DishesInfo("a123sdasd","asdasd123"));
        a.add(new DishesInfo("asda213sd","asdas213d"));
        a.add(new DishesInfo("asdasd213","asdasd213"));

        a.add(new DishesInfo("asdasd","asdas12323d"));
        a.add(new DishesInfo("a123sdasd","asdasd123"));
        a.add(new DishesInfo("asda213sd","asdas213d"));
        a.add(new DishesInfo("asdasd213","asdasd213"));
        RecyclerView.Adapter adapter = new DishesAdapter(a);
        LinearLayoutManager l =  new LinearLayoutManager(getActivity());
        l.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(l);
        recyclerView.setAdapter(adapter);
        return view;

    }
}
