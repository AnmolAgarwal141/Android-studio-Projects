package com.example.naaniz.checkout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.naaniz.ui.main.Frag1;
import com.example.naaniz.ui.main.Frag2;

public class PaymentPagerAdaptor extends FragmentPagerAdapter {

    private int numOfTabs;
    public PaymentPagerAdaptor(FragmentManager fm, int numOfTabs)
    {
        super(fm);
        this.numOfTabs = numOfTabs;
    }
    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                // sample fragment needs to be changed
                return new com.example.naaniz.Checkout.Cart();
            case 1:
                // sample fragment needs to be changed
                return new Payment();
            default:
                return null;

        }
    }

    @Override
    public int getCount() {
        return numOfTabs;
    }
}
