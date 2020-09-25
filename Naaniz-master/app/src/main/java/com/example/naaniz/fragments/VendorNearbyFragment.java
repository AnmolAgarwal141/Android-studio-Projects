package com.example.naaniz.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.naaniz.OrderPersons;
import com.example.naaniz.R;
import com.example.naaniz.adapters.OrderPersonAdapter;

import java.util.ArrayList;

public class VendorNearbyFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private ListView listView;

    public VendorNearbyFragment() {
        // Required empty public constructor
    }

    public static VendorNearbyFragment newInstance(String param1, String param2) {
        VendorNearbyFragment fragment = new VendorNearbyFragment();
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
        return inflater.inflate(R.layout.fragment_vendor_nearby, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        listView = view.findViewById(R.id.list_vendors);
        //listView.setVisibility(View.VISIBLE);

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
    }
}
