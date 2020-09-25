package com.example.naaniz.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.naaniz.R;

import java.io.Serializable;

/**
 * TODO: document your custom view class.
 */
public class ItemListItemView extends ConstraintLayout implements Serializable {

    private EditText itemPrice;
    public CheckBox itemCheck;

    public ItemListItemView(Context context) {
        super(context);
        init(null, 0);
    }

    public ItemListItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public ItemListItemView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle) {
        View.inflate(getContext(),R.layout.sample_item_list_item_view,this);
        onFinishInflate();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        this.itemCheck = findViewById(R.id.item_list_item_view_checkbox);
        this.itemPrice = findViewById(R.id.item_list_item_view_price);
        this.itemPrice.setVisibility(INVISIBLE);
        this.itemCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    itemPrice.setVisibility(VISIBLE);
                }
                else {
                    itemPrice.setVisibility(INVISIBLE);
                }
            }
        });
    }
    public void setItemPriceVisible() {
        this.itemPrice.setVisibility(VISIBLE);
    }
    public void setItemPriceInvisible() {
        this.itemPrice.setVisibility(INVISIBLE);
    }
    public void setItemCheckHeading(String heading) {
        this.itemCheck.setText(heading);
    }
    public String getItemCheckHeading() {
        return this.itemCheck.getText().toString();
    }
    public Float getItemPrice() {
        if(!this.itemPrice.getText().toString().isEmpty())
            return Float.parseFloat(this.itemPrice.getText().toString());
        return 0.0f;
    }
    public boolean getChecked() {
        return this.itemCheck.isChecked();
    }
}