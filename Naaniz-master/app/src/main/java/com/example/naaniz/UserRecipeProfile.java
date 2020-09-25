package com.example.naaniz;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.naaniz.adapters.RecipeAdapter;
import com.example.naaniz.views.ListOfRecipes;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class UserRecipeProfile extends Fragment {
    private ArrayList<ListOfRecipes> a= new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.user_recipes_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle b = getArguments();
        String phone = b.getString("phone");
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference parentRef = database.getReference("user_recipes");
        DatabaseReference ref = parentRef.child(phone).getRef();
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot p : dataSnapshot.getChildren())
                {

                    String po=null,qo=null,status=null,ready=null;
                    for(DataSnapshot o : p.getChildren())
                    {

                        Log.i("dsad", o.toString());

                        if(o.getKey().equals("title"))
                        {
                            po= o.getValue().toString();
                            Log.i("111111", po);
                        }
                        if(o.getKey().equals("status"))
                        {
                            status=o.getValue().toString();
                        }

                        if(o.getKey().equals("readyInMinutes"))
                        {
                            ready=o.getValue().toString();
                        }
                    }
                    if(po!=null )
                    {
                        a.add(new ListOfRecipes(po,status,phone,ready));
                    }
                }
                data(view);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
    public void data(View view)
    {
        RecyclerView recyclerView = view.findViewById(R.id.current_user_status);
        RecyclerView.Adapter adapter = new RecipeAdapter(a);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
        recyclerView.setAdapter(adapter);
    }
}
