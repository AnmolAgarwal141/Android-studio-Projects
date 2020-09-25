package com.example.naaniz.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.naaniz.My_menu_vendor_adaptor;
import com.example.naaniz.R;

import java.util.ArrayList;


public class Frag1 extends Fragment {
    RecyclerView mRecyclerView;
    ArrayList<DishesInfo2> a =new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag1,container,false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycleView);
        a.add(new DishesInfo2("asdasd","asdas12323d"));
        a.add(new DishesInfo2("a123sdasd","asdasd123"));
        a.add(new DishesInfo2("asda213sd","asdas213d"));
        a.add(new DishesInfo2("asdasd213","asdasd213"));

        a.add(new DishesInfo2("asdasd","asdas12323d"));
        a.add(new DishesInfo2("a123sdasd","asdasd123"));
        a.add(new DishesInfo2("asda213sd","asdas213d"));
        a.add(new DishesInfo2("asdasd213","asdasd213"));
        RecyclerView.Adapter adapter = new My_menu_vendor_adaptor(a);
        LinearLayoutManager l =  new LinearLayoutManager(getActivity());
        l.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(l);
        mRecyclerView.setAdapter(adapter);
        return view;
    }
}
