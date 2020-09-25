package com.example.naaniz.views;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.naaniz.R;

import java.util.ArrayList;
import java.util.List;

public class IngredientView extends LinearLayout {
    public TextView ingredient_name;
    public TextView ingredient_qty;
    public Button show_prices;
    public TextView vendorList;
    private String name,qty,category;
    private Double ingredient_cost;
    private boolean isVisible = false;
    private List<String> listOfVendors = new ArrayList<>();
    public IngredientView(Context context,String name, String qty,String category,List<String> listOfVendors) {
        super(context);
        this.name = name;
        this.qty = qty;
        this.category = category;
        this.listOfVendors = listOfVendors;
        init();
    }

    public IngredientView(Context context, AttributeSet attrs,String name, String qty,String category,List<String> listOfVendors) {
        super(context, attrs);
        this.name = name;
        this.qty = qty;
        this.category = category;
        this.listOfVendors = listOfVendors;
        init();
    }

    public IngredientView(Context context, AttributeSet attrs, int defStyle,String name, String qty,String category,List<String> listOfVendors) {
        super(context, attrs, defStyle);
        this.name = name;
        this.qty = qty;
        this.listOfVendors = listOfVendors;
        init();
    }

    private void init() {
        View.inflate(getContext(),R.layout.sample_ingredient_view,this);
        onFinishInflate();
    }

    @Override
    protected void onFinishInflate()
    {
        super.onFinishInflate();
        Log.d("Intentdetails","Inside onFinishInflate : "+name+" :: "+qty);
        ingredient_name = findViewById(R.id.ingredient_name);
        ingredient_qty = findViewById(R.id.ingredient_qty);
        show_prices = findViewById(R.id.btn_vendor);
        vendorList = findViewById(R.id.vendor_list);
        if(ingredient_name==null || ingredient_qty==null|| name==null || qty == null)
            return;
        ingredient_name.setText(name);
        ingredient_qty.setText(qty.replace(';',' '));
        vendorList.setVisibility(View.GONE);
        isVisible = false;
        if(listOfVendors!=null)
        setVendorList();
        initListener();
    }
    private void initListener()
    {
        if(show_prices==null)
            return;
        show_prices.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isVisible) {
                    vendorList.setVisibility(View.GONE);
                }
                else {
                    vendorList.setVisibility(View.VISIBLE);
                }
                isVisible = !isVisible;
            }
        });
    }
    public String getName() {
        return name;
    }
    
    public void setVendorList()
    {
        for(String vendor : listOfVendors)
            vendorList.append("\n"+vendor);
    }

    private Double getIngredientCost() {

        return ingredient_cost;
    }
}