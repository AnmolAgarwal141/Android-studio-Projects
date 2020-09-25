package com.example.naaniz.checkout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.example.naaniz.R;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

public class Checkout_payment extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout_payment);
        TabLayout tabLayout = findViewById(R.id.tabLayout2);
        TabItem tabCart = findViewById(R.id.cart_tab);
//        TabItem tabAddress = findViewById(R.id.address_tab);
        TabItem tabPayment = findViewById(R.id.payment_tab);

        ViewPager viewPager = findViewById(R.id.viewPager);
        PaymentPagerAdaptor pagerAdaptor = new PaymentPagerAdaptor(
                getSupportFragmentManager(),tabLayout.getTabCount());
        viewPager.setAdapter(pagerAdaptor);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
}
