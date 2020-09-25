package com.example.naaniz;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.naaniz.adapters.RecipeAdapter;
import com.example.naaniz.views.ListOfRecipes;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class listofrecipes extends Fragment {

    private ArrayList<ListOfRecipes> a= new ArrayList<>();
    private String food;
    public listofrecipes() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_listofrecipes, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle b = getArguments();
        String phone = b.getString("phone");

        FirebaseDatabase database = FirebaseDatabase.getInstance();

        DatabaseReference pr = database.getReference("users").child(phone).getRef();

        pr.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot p:dataSnapshot.getChildren())
                {
                     if(p.getKey().equals("food_choice"))
                     {
                         food =p.getValue().toString();
                     }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        DatabaseReference parentRef = database.getReference("user_recipes");

        parentRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot p : dataSnapshot.getChildren())
                {
                    for(DataSnapshot o : p.getChildren())
                    {
                        String po=null,qo=null,status=null,phone=null,choice=null,ready=null;
                        for(DataSnapshot q : o.getChildren())
                        {

                            if(q.getKey().equals("title"))
                            {
                                po= q.getValue().toString();
                            }
                            if(q.getKey().equals("name"))
                            {
                                qo=q.getValue().toString();
                            }
                            if(q.getKey().equals("status"))
                            {
                                status=q.getValue().toString();
                            }
                            if(q.getKey().equals("phone"))
                            {
                                phone=q.getValue().toString();
                            }
                            if(q.getKey().equals("choice"))
                            {
                                choice=q.getValue().toString();
                            }
                            if(q.getKey().equals("readyInMinutes"))
                            {
                                ready=q.getValue().toString();
                            }
                        }
                        if(po!=null && status.equals("verified") && choice.equals(food))
                        {
                            Log.i("6546546546", food);
                            Log.i("6added24434",choice);
                            a.add(new ListOfRecipes(po,qo,phone,ready));
                        }
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
        RecyclerView recyclerView = view.findViewById(R.id.list_recipes_all_user);
        RecipeAdapter adapter = new RecipeAdapter(a);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));

        recyclerView.setAdapter(adapter);
       adapter.setOnclickListen(new RecipeAdapter.OnItemClickListen() {
           @Override
           public void onClickDisplay(int position) {
               ListOfRecipes listOfRecipes = a.get(position);
               String phone =listOfRecipes.getPhone();
               String title =listOfRecipes.getRecipeTitle();

               FirebaseDatabase database = FirebaseDatabase.getInstance();
               DatabaseReference parentRef = database.getReference("user_recipes");
               DatabaseReference ref1 = parentRef.child(phone).getRef();
               DatabaseReference ref = ref1.child(title).getRef();
               ref.addListenerForSingleValueEvent(new ValueEventListener() {
                   @Override
                   public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                       for(DataSnapshot d :dataSnapshot.getChildren())
                       {
                           if(d.getKey().equals("views"))
                           {
                               int p =  Integer.parseInt(d.getValue().toString());
                               p++;
                               ref.child("views").setValue(p);
                           }
                       }
                   }

                   @Override
                   public void onCancelled(@NonNull DatabaseError databaseError) {

                   }
               });

               Intent intent = new Intent(getActivity(), ShowRecipe.class);
               intent.putExtra("dish",title);
               intent.putExtra("parentRef","user_recipes");
               intent.putExtra("phone",phone);
               startActivity(intent);
           }
       });
    }

}
