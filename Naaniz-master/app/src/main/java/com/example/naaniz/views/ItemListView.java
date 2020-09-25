package com.example.naaniz.views;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.naaniz.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;

public class ItemListView extends ConstraintLayout implements Serializable {
    private TextView heading;
    private LinearLayout itemLinear;
    private String headingText;
    private ArrayList<ItemListItemView> items = new ArrayList<>();

    public ItemListView(Context context) {
        super(context);
        init(null, 0);
    }

    public ItemListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public ItemListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle) {
        View.inflate(getContext(), R.layout.sample_item_list_view, this);
        onFinishInflate();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        itemLinear = findViewById(R.id.item_list_view_linear_lay);
        // heading = findViewById(R.id.item_list_view_heading);
//        heading.setText(headingText);
    }

    public void setItems(String name) {
        headingText = name;
        //heading.setText(headingText);
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        switch (headingText) {
            case "Dairy":
            case "Meat and Eggs":
            case "Vegetables": {
                Log.d("vendor_list", "in dairy switch");
                DatabaseReference ref = firebaseDatabase.getReference("ingredient_categories");
                ref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Log.d("vendor_list", headingText);
                        Log.d("vendor_list", "in datasnapshot");
                        if (dataSnapshot.hasChild(headingText)) {
                            Log.d("vendor_list", "in dairy child");
                            DatabaseReference rootRef = ref.child(headingText).getRef();
                            rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    if (dataSnapshot.getChildrenCount() > 0)
                                        for (DataSnapshot data : dataSnapshot.getChildren()) {
                                            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                                            ItemListItemView newItem = new ItemListItemView(getContext());
                                            CardView newCard = new CardView(getContext());
                                            newCard.setLayoutParams(params);
                                            newItem.setLayoutParams(params);
                                            Log.d("vendor_list","reading = "+data.getKey());
                                            newItem.setItemCheckHeading(data.getKey().toString());
                                            newItem.itemCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                                @Override
                                                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                                    if (isChecked) {
                                                        newItem.setItemPriceVisible();
                                                        items.add(newItem);
                                                    } else {
                                                        newItem.setItemPriceInvisible();
                                                        items.remove(newItem);
                                                    }
                                                }
                                            });
                                            newCard.addView(newItem);
                                            itemLinear.addView(newCard);
                                        }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                break;
            }
            case "Bakery": {
                DatabaseReference ref = firebaseDatabase.getReference("ingredient_categories");
                ref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.hasChild("Baking")) {
                            DatabaseReference rootRef = ref.child("Baking").getRef();
                            rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    if (dataSnapshot.getChildrenCount() > 0)
                                        for (DataSnapshot data : dataSnapshot.getChildren()) {
                                            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                                            ItemListItemView newItem = new ItemListItemView(getContext());
                                            CardView newCard = new CardView(getContext());
                                            newCard.setLayoutParams(params);
                                            newItem.setItemCheckHeading(data.getKey());
                                            newCard.addView(newItem);
                                            itemLinear.addView(newCard);
                                            newItem.itemCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                                @Override
                                                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                                    if (isChecked) {
                                                        newItem.setItemPriceVisible();
                                                        items.add(newItem);
                                                    } else {
                                                        newItem.setItemPriceInvisible();
                                                        items.remove(newItem);
                                                    }
                                                }
                                            });
                                        }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });
                        }
                        if (dataSnapshot.hasChild("Bread")) {
                            DatabaseReference rootRef = ref.child("Bread").getRef();
                            rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    if (dataSnapshot.getChildrenCount() > 0)
                                        for (DataSnapshot data : dataSnapshot.getChildren()) {
                                            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                                            ItemListItemView newItem = new ItemListItemView(getContext());
                                            CardView newCard = new CardView(getContext());
                                            newCard.setLayoutParams(params);
                                            newItem.setItemCheckHeading(data.getKey().toString());
                                            newCard.addView(newItem);
                                            itemLinear.addView(newCard);
                                            newItem.itemCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                                @Override
                                                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                                    if (isChecked) {
                                                        newItem.setItemPriceVisible();
                                                        items.add(newItem);
                                                    } else {
                                                        newItem.setItemPriceInvisible();
                                                        items.remove(newItem);
                                                    }
                                                }
                                            });
                                        }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });
                        }
                        if (dataSnapshot.hasChild("Bakery/Bread")) {
                            DatabaseReference rootRef = ref.child("Bakery/Bread").getRef();
                            rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    if (dataSnapshot.getChildrenCount() > 0)
                                        for (DataSnapshot data : dataSnapshot.getChildren()) {
                                            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                                            ItemListItemView newItem = new ItemListItemView(getContext());
                                            CardView newCard = new CardView(getContext());
                                            newCard.setLayoutParams(params);
                                            newItem.setItemCheckHeading(data.getKey().toString());
                                            newCard.addView(newItem);
                                            itemLinear.addView(newCard);
                                            newItem.itemCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                                @Override
                                                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                                    if (isChecked) {
                                                        newItem.setItemPriceVisible();
                                                        items.add(newItem);
                                                    } else {
                                                        newItem.setItemPriceInvisible();
                                                        items.remove(newItem);
                                                    }
                                                }
                                            });
                                        }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });
                        }
                        if (dataSnapshot.hasChild("Sweet Snacks")) {
                            DatabaseReference rootRef = ref.child("Sweet Snacks").getRef();
                            rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    if (dataSnapshot.getChildrenCount() > 0)
                                        for (DataSnapshot data : dataSnapshot.getChildren()) {
                                            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                                            ItemListItemView newItem = new ItemListItemView(getContext());
                                            CardView newCard = new CardView(getContext());
                                            newCard.setLayoutParams(params);
                                            newItem.setItemCheckHeading(data.getKey().toString());
                                            newCard.addView(newItem);
                                            itemLinear.addView(newCard);
                                            newItem.itemCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                                @Override
                                                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                                    if (isChecked) {
                                                        newItem.setItemPriceVisible();
                                                        items.add(newItem);
                                                    } else {
                                                        newItem.setItemPriceInvisible();
                                                        items.remove(newItem);
                                                    }
                                                }
                                            });
                                        }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                break;
            }
            case "Groceries": {
                DatabaseReference ref = firebaseDatabase.getReference("ingredient_categories");
                ref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.hasChild("Spices and Seasonings")) {
                            DatabaseReference rootRef = ref.child("Spices and Seasonings").getRef();
                            rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    if (dataSnapshot.getChildrenCount() > 0)
                                        for (DataSnapshot data : dataSnapshot.getChildren()) {
                                            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                                            ItemListItemView newItem = new ItemListItemView(getContext());
                                            CardView newCard = new CardView(getContext());
                                            newCard.setLayoutParams(params);
                                            newItem.setItemCheckHeading(data.getKey().toString());
                                            newCard.addView(newItem);
                                            itemLinear.addView(newCard);
                                            newItem.itemCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                                @Override
                                                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                                    if (isChecked) {
                                                        newItem.setItemPriceVisible();
                                                        items.add(newItem);
                                                    } else {
                                                        newItem.setItemPriceInvisible();
                                                        items.remove(newItem);
                                                    }
                                                }
                                            });
                                        }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });
                        }
                        if (dataSnapshot.hasChild("Pasta and Rice")) {
                            DatabaseReference rootRef = ref.child("Pasta and Rice").getRef();
                            rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    if (dataSnapshot.getChildrenCount() > 0)
                                        for (DataSnapshot data : dataSnapshot.getChildren()) {
                                            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                                            ItemListItemView newItem = new ItemListItemView(getContext());
                                            CardView newCard = new CardView(getContext());
                                            newCard.setLayoutParams(params);
                                            newItem.setItemCheckHeading(data.getKey().toString());
                                            newCard.addView(newItem);
                                            itemLinear.addView(newCard);
                                            newItem.itemCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                                @Override
                                                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                                    if (isChecked) {
                                                        newItem.setItemPriceVisible();
                                                        items.add(newItem);
                                                    } else {
                                                        newItem.setItemPriceInvisible();
                                                        items.remove(newItem);
                                                    }
                                                }
                                            });
                                        }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });
                        }
                        if (dataSnapshot.hasChild("Refrigerated")) {
                            DatabaseReference rootRef = ref.child("Refrigerated").getRef();
                            rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    if (dataSnapshot.getChildrenCount() > 0)
                                        for (DataSnapshot data : dataSnapshot.getChildren()) {
                                            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                                            ItemListItemView newItem = new ItemListItemView(getContext());
                                            CardView newCard = new CardView(getContext());
                                            newCard.setLayoutParams(params);
                                            newItem.setItemCheckHeading(data.getKey().toString());
                                            newCard.addView(newItem);
                                            itemLinear.addView(newCard);
                                            newItem.itemCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                                @Override
                                                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                                    if (isChecked) {
                                                        newItem.setItemPriceVisible();
                                                        items.add(newItem);
                                                    } else {
                                                        newItem.setItemPriceInvisible();
                                                        items.remove(newItem);
                                                    }
                                                }
                                            });
                                        }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });
                        }
                        if (dataSnapshot.hasChild("Canned and Jarred")) {
                            DatabaseReference rootRef = ref.child("Canned and Jarred").getRef();
                            rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    if (dataSnapshot.getChildrenCount() > 0)
                                        for (DataSnapshot data : dataSnapshot.getChildren()) {
                                            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                                            ItemListItemView newItem = new ItemListItemView(getContext());
                                            CardView newCard = new CardView(getContext());
                                            newCard.setLayoutParams(params);
                                            newItem.setItemCheckHeading(data.getKey().toString());
                                            newCard.addView(newItem);
                                            itemLinear.addView(newCard);
                                            newItem.itemCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                                @Override
                                                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                                    if (isChecked) {
                                                        newItem.setItemPriceVisible();
                                                        items.add(newItem);
                                                    } else {
                                                        newItem.setItemPriceInvisible();
                                                        items.remove(newItem);
                                                    }
                                                }
                                            });
                                        }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });
                        }
                        if (dataSnapshot.hasChild("Frozen")) {
                            DatabaseReference rootRef = ref.child("Frozen").getRef();
                            rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    if (dataSnapshot.getChildrenCount() > 0)
                                        for (DataSnapshot data : dataSnapshot.getChildren()) {

                                            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                                            ItemListItemView newItem = new ItemListItemView(getContext());
                                            CardView newCard = new CardView(getContext());
                                            newCard.setLayoutParams(params);
                                            newItem.setItemCheckHeading(data.getKey().toString());
                                            itemLinear.addView(newCard);
                                            newItem.itemCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                                @Override
                                                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                                    if (isChecked) {
                                                        newItem.setItemPriceVisible();
                                                        items.add(newItem);
                                                    } else {
                                                        newItem.setItemPriceInvisible();
                                                        items.remove(newItem);
                                                    }
                                                }
                                            });
                                        }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });
                        }
                        if (dataSnapshot.hasChild("Nut butters, Jams, and Honey")) {
                            DatabaseReference rootRef = ref.child("Nut butters, Jams, and Honey").getRef();
                            rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    if (dataSnapshot.getChildrenCount() > 0)
                                        for (DataSnapshot data : dataSnapshot.getChildren()) {
                                            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                                            ItemListItemView newItem = new ItemListItemView(getContext());
                                            CardView newCard = new CardView(getContext());
                                            newCard.setLayoutParams(params);
                                            newItem.setItemCheckHeading(data.getKey().toString());
                                            newCard.addView(newItem);
                                            itemLinear.addView(newCard);
                                            newItem.itemCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                                @Override
                                                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                                    if (isChecked) {
                                                        newItem.setItemPriceVisible();
                                                        items.add(newItem);
                                                    } else {
                                                        newItem.setItemPriceInvisible();
                                                        items.remove(newItem);
                                                    }
                                                }
                                            });
                                        }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });
                        }
                        if (dataSnapshot.hasChild("Oil, Vinegar, Salad Dressing")) {
                            DatabaseReference rootRef = ref.child("Oil, Vinegar, Salad Dressing").getRef();
                            rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    if (dataSnapshot.getChildrenCount() > 0)
                                        for (DataSnapshot data : dataSnapshot.getChildren()) {
                                            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                                            ItemListItemView newItem = new ItemListItemView(getContext());
                                            CardView newCard = new CardView(getContext());
                                            newCard.setLayoutParams(params);
                                            newItem.setItemCheckHeading(data.getKey().toString());
                                            newCard.addView(newItem);
                                            itemLinear.addView(newCard);
                                            newItem.itemCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                                @Override
                                                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                                    if (isChecked) {
                                                        newItem.setItemPriceVisible();
                                                        items.add(newItem);
                                                    } else {
                                                        newItem.setItemPriceInvisible();
                                                        items.remove(newItem);
                                                    }
                                                }
                                            });
                                        }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });
                        }
                        if (dataSnapshot.hasChild("Condiments")) {
                            DatabaseReference rootRef = ref.child("Condiments").getRef();
                            rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    if (dataSnapshot.getChildrenCount() > 0)
                                        for (DataSnapshot data : dataSnapshot.getChildren()) {
                                            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                                            ItemListItemView newItem = new ItemListItemView(getContext());
                                            CardView newCard = new CardView(getContext());
                                            newCard.setLayoutParams(params);
                                            newItem.setItemCheckHeading(data.getKey().toString());
                                            newCard.addView(newItem);
                                            itemLinear.addView(newCard);
                                            newItem.itemCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                                @Override
                                                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                                    if (isChecked) {
                                                        newItem.setItemPriceVisible();
                                                        items.add(newItem);
                                                    } else {
                                                        newItem.setItemPriceInvisible();
                                                        items.remove(newItem);
                                                    }
                                                }
                                            });
                                        }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });
                        }
                        if (dataSnapshot.hasChild("Savory Snacks")) {
                            DatabaseReference rootRef = ref.child("Savory Snacks").getRef();
                            rootRef.addListenerForSingleValueEvent(new ValueEventListener() {

                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    if (dataSnapshot.getChildrenCount() > 0)
                                        for (DataSnapshot data : dataSnapshot.getChildren()) {
                                            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                                            ItemListItemView newItem = new ItemListItemView(getContext());
                                            CardView newCard = new CardView(getContext());
                                            newCard.setLayoutParams(params);
                                            newItem.setItemCheckHeading(data.getKey().toString());
                                            newCard.addView(newItem);
                                            itemLinear.addView(newCard);
                                            newItem.itemCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                                @Override
                                                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                                    if (isChecked) {
                                                        newItem.setItemPriceVisible();
                                                        items.add(newItem);
                                                    } else {
                                                        newItem.setItemPriceInvisible();
                                                        items.remove(newItem);
                                                    }
                                                }
                                            });
                                        }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });
                        }
                        if (dataSnapshot.hasChild("Ethnic Foods")) {
                            DatabaseReference rootRef = ref.child("Ethnic Foods").getRef();
                            rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    if (dataSnapshot.getChildrenCount() > 0)
                                        for (DataSnapshot data : dataSnapshot.getChildren()) {
                                            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                                            ItemListItemView newItem = new ItemListItemView(getContext());
                                            CardView newCard = new CardView(getContext());
                                            newCard.setLayoutParams(params);
                                            newItem.setItemCheckHeading(data.getKey().toString());
                                            newCard.addView(newItem);
                                            itemLinear.addView(newCard);
                                            newItem.itemCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                                @Override
                                                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                                    if (isChecked) {
                                                        newItem.setItemPriceVisible();
                                                        items.add(newItem);
                                                    } else {
                                                        newItem.setItemPriceInvisible();
                                                        items.remove(newItem);
                                                    }
                                                }
                                            });
                                        }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });
                        }
                        if (dataSnapshot.hasChild("Tea and Coffee")) {
                            DatabaseReference rootRef = ref.child("Tea and Coffee").getRef();
                            rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    if (dataSnapshot.getChildrenCount() > 0)
                                        for (DataSnapshot data : dataSnapshot.getChildren()) {
                                            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                                            ItemListItemView newItem = new ItemListItemView(getContext());
                                            CardView newCard = new CardView(getContext());
                                            newCard.setLayoutParams(params);
                                            newItem.setItemCheckHeading(data.getKey().toString());
                                            newCard.addView(newItem);
                                            itemLinear.addView(newCard);
                                            newItem.itemCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                                @Override
                                                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                                    if (isChecked) {
                                                        newItem.setItemPriceVisible();
                                                        items.add(newItem);
                                                    } else {
                                                        newItem.setItemPriceInvisible();
                                                        items.remove(newItem);
                                                    }
                                                }
                                            });
                                        }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });
                        }
                        if (dataSnapshot.hasChild("Gourmet")) {
                            DatabaseReference rootRef = ref.child("Gourmet").getRef();
                            rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    if (dataSnapshot.getChildrenCount() > 0)
                                        for (DataSnapshot data : dataSnapshot.getChildren()) {
                                            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                                            ItemListItemView newItem = new ItemListItemView(getContext());
                                            CardView newCard = new CardView(getContext());
                                            newCard.setLayoutParams(params);
                                            newItem.setItemCheckHeading(data.getKey().toString());
                                            newCard.addView(newItem);
                                            itemLinear.addView(newCard);
                                            newItem.itemCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                                @Override
                                                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                                    if (isChecked) {
                                                        newItem.setItemPriceVisible();
                                                        items.add(newItem);
                                                    } else {
                                                        newItem.setItemPriceInvisible();
                                                        items.remove(newItem);
                                                    }
                                                }
                                            });
                                        }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });
                        }
                        if (dataSnapshot.hasChild("Gluten Free")) {
                            DatabaseReference rootRef = ref.child("Gluten Free").getRef();
                            rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    if (dataSnapshot.getChildrenCount() > 0)
                                        for (DataSnapshot data : dataSnapshot.getChildren()) {
                                            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                                            ItemListItemView newItem = new ItemListItemView(getContext());
                                            CardView newCard = new CardView(getContext());
                                            newCard.setLayoutParams(params);
                                            newItem.setItemCheckHeading(data.getKey().toString());
                                            newCard.addView(newItem);
                                            itemLinear.addView(newCard);
                                            newItem.itemCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                                @Override
                                                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                                    if (isChecked) {
                                                        newItem.setItemPriceVisible();
                                                        items.add(newItem);
                                                    } else {
                                                        newItem.setItemPriceInvisible();
                                                        items.remove(newItem);
                                                    }
                                                }
                                            });
                                        }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });
                        }
                        if (dataSnapshot.hasChild("Alcoholic Beverages")) {
                            DatabaseReference rootRef = ref.child("Alcoholic Beverages").getRef();
                            rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    if (dataSnapshot.getChildrenCount() > 0)
                                        for (DataSnapshot data : dataSnapshot.getChildren()) {
                                            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                                            ItemListItemView newItem = new ItemListItemView(getContext());
                                            CardView newCard = new CardView(getContext());
                                            newCard.setLayoutParams(params);
                                            newItem.setItemCheckHeading(data.getKey().toString());
                                            newCard.addView(newItem);
                                            itemLinear.addView(newCard);
                                            newItem.itemCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                                @Override
                                                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                                    if (isChecked) {
                                                        newItem.setItemPriceVisible();
                                                        items.add(newItem);
                                                    } else {
                                                        newItem.setItemPriceInvisible();
                                                        items.remove(newItem);
                                                    }
                                                }
                                            });
                                        }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });
                        }
                        if (dataSnapshot.hasChild("Cereal")) {
                            DatabaseReference rootRef = ref.child("Cereal").getRef();
                            rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    if (dataSnapshot.getChildrenCount() > 0)
                                        for (DataSnapshot data : dataSnapshot.getChildren()) {
                                            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                                            ItemListItemView newItem = new ItemListItemView(getContext());
                                            CardView newCard = new CardView(getContext());
                                            newCard.setLayoutParams(params);
                                            newItem.setItemCheckHeading(data.getKey().toString());
                                            newCard.addView(newItem);
                                            itemLinear.addView(newCard);
                                            newItem.itemCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                                @Override
                                                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                                    if (isChecked) {
                                                        newItem.setItemPriceVisible();
                                                        items.add(newItem);
                                                    } else {
                                                        newItem.setItemPriceInvisible();
                                                        items.remove(newItem);
                                                    }
                                                }
                                            });
                                        }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });
                        }
                        if (dataSnapshot.hasChild("Nuts")) {
                            DatabaseReference rootRef = ref.child("Nuts").getRef();
                            rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    if (dataSnapshot.getChildrenCount() > 0)
                                        for (DataSnapshot data : dataSnapshot.getChildren()) {
                                            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                                            ItemListItemView newItem = new ItemListItemView(getContext());
                                            CardView newCard = new CardView(getContext());
                                            newCard.setLayoutParams(params);
                                            newItem.setItemCheckHeading(data.getKey().toString());
                                            newCard.addView(newItem);
                                            itemLinear.addView(newCard);
                                            newItem.itemCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                                @Override
                                                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                                    if (isChecked) {
                                                        newItem.setItemPriceVisible();
                                                        items.add(newItem);
                                                    } else {
                                                        newItem.setItemPriceInvisible();
                                                        items.remove(newItem);
                                                    }
                                                }
                                            });
                                        }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });
                        }
                        if (dataSnapshot.hasChild("Beverages")) {
                            DatabaseReference rootRef = ref.child("Beverages").getRef();
                            rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    if (dataSnapshot.getChildrenCount() > 0)
                                        for (DataSnapshot data : dataSnapshot.getChildren()) {
                                            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                                            ItemListItemView newItem = new ItemListItemView(getContext());
                                            CardView newCard = new CardView(getContext());
                                            newCard.setLayoutParams(params);
                                            newItem.setItemCheckHeading(data.getKey().toString());
                                            newCard.addView(newItem);
                                            itemLinear.addView(newCard);
                                            newItem.itemCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                                @Override
                                                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                                    if (isChecked) {
                                                        newItem.setItemPriceVisible();
                                                        items.add(newItem);
                                                    } else {
                                                        newItem.setItemPriceInvisible();
                                                        items.remove(newItem);
                                                    }
                                                }
                                            });
                                        }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });
                        }
                        if (dataSnapshot.hasChild("Cheese")) {
                            DatabaseReference rootRef = ref.child("Cheese").getRef();
                            rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    if (dataSnapshot.getChildrenCount() > 0)
                                        for (DataSnapshot data : dataSnapshot.getChildren()) {
                                            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                                            ItemListItemView newItem = new ItemListItemView(getContext());
                                            CardView newCard = new CardView(getContext());
                                            newCard.setLayoutParams(params);
                                            newItem.setItemCheckHeading(data.getKey().toString());
                                            newCard.addView(newItem);
                                            itemLinear.addView(newCard);
                                            newItem.itemCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                                @Override
                                                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                                    if (isChecked) {
                                                        newItem.setItemPriceVisible();
                                                        items.add(newItem);
                                                    } else {
                                                        newItem.setItemPriceInvisible();
                                                        items.remove(newItem);
                                                    }
                                                }
                                            });
                                        }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });
                        }
                        if (dataSnapshot.hasChild("Dried Fruits")) {
                            DatabaseReference rootRef = ref.child("Dried Fruits").getRef();
                            rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    if (dataSnapshot.getChildrenCount() > 0)
                                        for (DataSnapshot data : dataSnapshot.getChildren()) {
                                            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                                            ItemListItemView newItem = new ItemListItemView(getContext());
                                            CardView newCard = new CardView(getContext());
                                            newCard.setLayoutParams(params);
                                            newItem.setItemCheckHeading(data.getKey().toString());
                                            newCard.addView(newItem);
                                            itemLinear.addView(newCard);
                                            newItem.itemCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                                @Override
                                                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                                    if (isChecked) {
                                                        newItem.setItemPriceVisible();
                                                        items.add(newItem);
                                                    } else {
                                                        newItem.setItemPriceInvisible();
                                                        items.remove(newItem);
                                                    }
                                                }
                                            });
                                        }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });
                        }
                        if (dataSnapshot.hasChild("Grilling Supplies")) {
                            DatabaseReference rootRef = ref.child("Grilling Supplies").getRef();
                            rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    if (dataSnapshot.getChildrenCount() > 0)
                                        for (DataSnapshot data : dataSnapshot.getChildren()) {
                                            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                                            ItemListItemView newItem = new ItemListItemView(getContext());
                                            CardView newCard = new CardView(getContext());
                                            newCard.setLayoutParams(params);
                                            newItem.setItemCheckHeading(data.getKey().toString());
                                            newCard.addView(newItem);
                                            itemLinear.addView(newCard);
                                            newItem.itemCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                                @Override
                                                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                                    if (isChecked) {
                                                        newItem.setItemPriceVisible();
                                                        items.add(newItem);
                                                    } else {
                                                        newItem.setItemPriceInvisible();
                                                        items.remove(newItem);
                                                    }
                                                }
                                            });
                                        }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                break;
            }
            default: {
                Log.d("vendor_list", "in default");
                Toast.makeText(getContext(), "Wrong", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public ArrayList<ItemListItemView> getItems() {
        return items;
    }
}
