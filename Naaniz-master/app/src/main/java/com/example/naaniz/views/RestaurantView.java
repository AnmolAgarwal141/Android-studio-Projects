package com.example.naaniz.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.naaniz.R;

/**
 * TODO: document your custom view class.
 */
public class RestaurantView extends ConstraintLayout {
    private TextView res_name;
    private TextView res_rating;
    private TextView res_price;

    public RestaurantView(Context context) {
        super(context);
        init(null, 0);
    }

    public RestaurantView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public RestaurantView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle) {
        View.inflate(getContext(),R.layout.sample_restaurant_view,this);
        onFinishInflate();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        res_name = findViewById(R.id.res_view_name);
        res_rating = findViewById(R.id.res_view_rating);
        res_price = findViewById(R.id.res_view_price);
    }

   public void setName(String name) {
        this.res_name.setText(name);
   }
   public void setRating(String rating) {
        this.res_rating.setText(rating);
   }
   public void setPrice(String price){
        this.res_price.setText(price);
   }
   public String getName(){return this.res_name.getText().toString();}
}

