package com.example.naaniz.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.naaniz.fragments.ShoppingListFragment;
import com.example.naaniz.fragments.VendorNearbyFragment;

public class ShoppingAdapter extends FragmentStatePagerAdapter {

    int mNoOfTabs;

    public ShoppingAdapter(FragmentManager fm, int NoOfTabs){

        super(fm);
        this.mNoOfTabs = NoOfTabs;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {

        switch (position){

            case 0:
                ShoppingListFragment shoppingListFragment = new ShoppingListFragment();
                return shoppingListFragment;
            case 1:
                VendorNearbyFragment vendorNearbyFragment = new VendorNearbyFragment();
                return vendorNearbyFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNoOfTabs;
    }
}
