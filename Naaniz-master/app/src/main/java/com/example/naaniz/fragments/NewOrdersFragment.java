package com.example.naaniz.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.naaniz.OrderPersons;
import com.example.naaniz.R;
import com.example.naaniz.adapters.OrderPersonAdapter;
import com.example.naaniz.adapters.OrdersAdapter;
import com.example.naaniz.rvmodels.OrdersRVModel;

import java.util.ArrayList;
import java.util.List;

public class NewOrdersFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private ListView listView;
    private RecyclerView recyclerView;

    private List<OrdersRVModel> orders;

    public NewOrdersFragment() {
        // Required empty public constructor
    }

    public static NewOrdersFragment newInstance(String param1, String param2) {
        NewOrdersFragment fragment = new NewOrdersFragment();
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
        return inflater.inflate(R.layout.fragment_new_orders, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        listView = view.findViewById(R.id.list_customers_new);

        listView.setVisibility(View.VISIBLE);

        OrderPersons abc = new OrderPersons("ABCDEFG", null);
        OrderPersons def = new OrderPersons("DEFGHIJ", null);
        OrderPersons a = new OrderPersons("ABCDE", null);
        OrderPersons b = new OrderPersons("DEFIJ", null);
        OrderPersons c = new OrderPersons("ABDEFG", null);
        OrderPersons d = new OrderPersons("FGHIJ", null);
        OrderPersons e = new OrderPersons("CDEFG", null);
        OrderPersons f = new OrderPersons("DEHIJ", null);

        ArrayList<OrderPersons> listItems = new ArrayList<>();

        listItems.add(abc);
        listItems.add(def);
        listItems.add(a);
        listItems.add(b);
        listItems.add(c);
        listItems.add(d);
        listItems.add(e);
        listItems.add(f);



        OrderPersonAdapter adapter = new OrderPersonAdapter(getContext(), R.layout.orders_adapter_view, listItems);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //Toast.makeText(getContext(), "Position Clicked: " + i, Toast.LENGTH_LONG).show();
                showRecyclerView();
            }
        });
    }

    public void showRecyclerView() {
        listView.setVisibility(View.GONE);

        recyclerView = getView().findViewById(R.id.recycler_view_new);
        recyclerView.setVisibility(View.VISIBLE);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        orders = new ArrayList<>();

        for (int i = 0; i < 10; ++i) {
            OrdersRVModel ordersRVModel = new OrdersRVModel("3:00 PM", "23", "Kadhai Paneer", "03", "Rs. 180", "Rs. 180");
            orders.add(ordersRVModel);
        }

        RecyclerView.Adapter adapter = new OrdersAdapter(orders, getContext(), 1);

        recyclerView.setAdapter(adapter);


    }
}
