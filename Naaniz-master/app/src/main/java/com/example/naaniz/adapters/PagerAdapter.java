package com.example.naaniz.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.naaniz.fragments.NewOrdersFragment;
import com.example.naaniz.fragments.PastOrdersFragment;

public class PagerAdapter extends FragmentStatePagerAdapter {

    int mNoOfTabs;

    public PagerAdapter(FragmentManager fm, int NoOfTabs){

        super(fm);
        this.mNoOfTabs = NoOfTabs;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {

        switch (position){

            case 0:
                NewOrdersFragment newOrdersFragment = new NewOrdersFragment();
                return newOrdersFragment;
            case 1:
                PastOrdersFragment pastOrdersFragment = new PastOrdersFragment();
                return pastOrdersFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNoOfTabs;
    }
}
