package com.example.tripplanner.ui.MoneyManager;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.tripplanner.R;
import com.example.tripplanner.ui.BookingStatus.BookingStatusViewModel;

public class MoneyManagerFragment extends Fragment {
    private MoneyManagerViewModel moneyManagerViewModel;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        moneyManagerViewModel = ViewModelProviders.of(this).get(MoneyManagerViewModel.class);
        View root = inflater.inflate(R.layout.fragment_moneymanager, container, false);
        final TextView textView = root.findViewById(R.id.text_moneymanager);
        moneyManagerViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}
