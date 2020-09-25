package com.example.naaniz.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.naaniz.fragments.BusinessAreasFragment;
import com.example.naaniz.fragments.BusinessesFragment;

public class NearMeAdapter extends FragmentStatePagerAdapter {

    int mNoOfTabs;

    public NearMeAdapter(FragmentManager fm, int NoOfTabs){

        super(fm);
        this.mNoOfTabs = NoOfTabs;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {

        switch (position){

            case 0:
                BusinessesFragment businessesFragment = new BusinessesFragment();
                return businessesFragment;
            case 1:
                BusinessAreasFragment businessAreasFragment = new BusinessAreasFragment();
                return businessAreasFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNoOfTabs;
    }
}
