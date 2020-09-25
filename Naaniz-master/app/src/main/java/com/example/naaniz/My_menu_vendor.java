package com.example.naaniz;

import android.os.Bundle;

import com.example.naaniz.ui.main.DishesInfo2;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.naaniz.ui.main.SectionsPagerAdapter;

import java.util.ArrayList;

public class My_menu_vendor extends AppCompatActivity {
    RecyclerView mRecyclerView;
    private ArrayList<DishesInfo2> mEa=new ArrayList<>();;
    private My_menu_vendor_adaptor my_menu_vendor_adaptor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_menu_vendor);

        //recycler view needed
//        mRecyclerView=findViewById(R.id.recycler_view_vendor);
//        mRecyclerView.setHasFixedSize(true);
//        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));


        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
        FloatingActionButton fab = findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for(int i=0;i<mEa.size();){
                    if(mEa.get(i).isSelected()){
                        mEa.remove(i);
                        my_menu_vendor_adaptor.notifyItemRemoved(i);
                        my_menu_vendor_adaptor.notifyItemRangeChanged(i,mEa.size());

                    }
                    else {
                        i++;
                    }
                }
            }
        });
    }
}