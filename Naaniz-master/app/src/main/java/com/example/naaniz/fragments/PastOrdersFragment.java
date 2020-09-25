package com.example.naaniz.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.naaniz.OrderPersons;
import com.example.naaniz.R;
import com.example.naaniz.adapters.OrderPersonAdapter;
import com.example.naaniz.adapters.OrdersAdapter;
import com.example.naaniz.rvmodels.OrdersRVModel;

import java.util.ArrayList;
import java.util.List;

public class PastOrdersFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    private ListView listView;
    private CardView cardView;

    private TextView orderTime;
    private TextView orderNo;
    private TextView dishNames;
    private TextView dishQuantities;
    private TextView dishPrices;
    private TextView orderCost;

    private Button button;

    public PastOrdersFragment() {
    }

    public static PastOrdersFragment newInstance(String param1, String param2) {
        PastOrdersFragment fragment = new PastOrdersFragment();
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
        return inflater.inflate(R.layout.fragment_past_orders, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        listView = view.findViewById(R.id.list_customers_past);
        listView.setVisibility(View.VISIBLE);

        OrderPersons abc = new OrderPersons("ABCDEFG", null);
        OrderPersons def = new OrderPersons("DEFGHIJ", null);
        OrderPersons a = new OrderPersons("ABCDEFG", null);
        OrderPersons b = new OrderPersons("DEFGHIJ", null);
        OrderPersons c = new OrderPersons("ABCDEFG", null);
        OrderPersons d = new OrderPersons("DEFGHIJ", null);
        OrderPersons e = new OrderPersons("ABCDEFG", null);
        OrderPersons f = new OrderPersons("DEFGHIJ", null);

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

        cardView = getView().findViewById(R.id.card_view_past);
        cardView.setVisibility(View.VISIBLE);

        orderTime = getView().findViewById(R.id.time_of_order);
        orderNo = getView().findViewById(R.id.order_no);
        dishNames = getView().findViewById(R.id.items_list);
        dishQuantities = getView().findViewById(R.id.quantities_list);
        dishPrices = getView().findViewById(R.id.prices_list);
        orderCost = getView().findViewById(R.id.total_amount);
        button = getView().findViewById(R.id.mark_as_done);

        button.setVisibility(View.VISIBLE);

        orderTime.setText("3:00 PM");
        orderNo.setText("Order 05");
        dishNames.setText("Paneer Butter Masala");
        dishQuantities.setText("10");
        dishPrices.setText("Rs.290");
        orderCost.setText("Rs.1090");

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Done", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
