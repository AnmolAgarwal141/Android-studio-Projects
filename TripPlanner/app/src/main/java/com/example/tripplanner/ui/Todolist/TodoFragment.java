package com.example.tripplanner.ui.Todolist;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.tripplanner.R;
import com.example.tripplanner.ui.Proflie.ProfileViewModel;

public class TodoFragment extends Fragment {
    private TodoViewModel todoViewModel;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        todoViewModel = ViewModelProviders.of(this).get(TodoViewModel.class);
        View root = inflater.inflate(R.layout.fragment_todo, container, false);
        final TextView textView = root.findViewById(R.id.text_todo);
        todoViewModel.getText().observe((LifecycleOwner) this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}
