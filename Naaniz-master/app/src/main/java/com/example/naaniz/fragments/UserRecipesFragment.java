package com.example.naaniz.fragments;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.naaniz.R;
import com.example.naaniz.listofrecipes;
import com.example.naaniz.UserRecipeProfile;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;


public class UserRecipesFragment extends Fragment {

    LinearLayout listOfViews;
    TextView recipes;
    OnClickRecipe onClickRecipe;
    private String phone=null;
    public interface OnClickRecipe
    {
        void onRecipePress();
    }
    private BottomNavigationView.OnNavigationItemSelectedListener botm = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            FragmentManager fragmentManager = getChildFragmentManager();
            Fragment fragment = null;
            switch (menuItem.getItemId()) {
                case R.id.all_recipes_btm:
                    fragment = new listofrecipes();
                    Bundle bundle =new Bundle();
                    bundle.putString("phone",phone);
                    fragment.setArguments(bundle);
                    break;
                case R.id.user_recipes_btm:
                    fragment = new UserRecipeProfile();
                    Bundle bundle2 =new Bundle();
                    bundle2.putString("phone",phone);
                    fragment.setArguments(bundle2);
                    break;


            }
            fragmentManager.beginTransaction().replace(R.id.container, fragment).commit();
            return true;
        }
    };
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        onClickRecipe = (OnClickRecipe)context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_user_recipes, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        listOfViews = view.findViewById(R.id.List);

//        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new allUserRecipe()).commit();

        Bundle bundle =  getArguments();
          phone  = bundle.getString("phone");
        String name = bundle.getString("name");
        BottomNavigationView bottomNavigationView = view.findViewById(R.id.botm);
        bottomNavigationView.setOnNavigationItemSelectedListener(botm);


        FragmentManager fragmentManager = getChildFragmentManager();
        Fragment fragment = null;
        fragment = new listofrecipes();
        Bundle bundle2 =new Bundle();
        bundle2.putString("phone",phone);
        fragment.setArguments(bundle2);
        fragmentManager.beginTransaction().replace(R.id.container, fragment).commit();
        retrieveData();

        FloatingActionButton fab = (FloatingActionButton)view.findViewById(R.id.addButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickRecipe.onRecipePress();


            }
        });

    }


    private void addRecipes(String title)
    {
        LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        recipes.setLayoutParams(param);
        recipes = new TextView(getContext());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) { recipes.setForegroundGravity(Gravity.CENTER); }
        recipes.setLayoutParams(param);
        recipes.setText(title);
        listOfViews.addView(recipes);
    }

    private void retrieveData()
    {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference ref = firebaseDatabase.getReference("users-recipes");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String name="Default";
                for(DataSnapshot d : dataSnapshot.getChildren()) {
                    addRecipes((Objects.requireNonNull(d.getValue()).toString()));
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }


}
