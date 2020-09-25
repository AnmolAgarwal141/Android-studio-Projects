package com.example.naaniz.adapters;

import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.naaniz.R;
import com.example.naaniz.views.ListOfRecipes;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecycleHolder> {

    private ArrayList<ListOfRecipes> mEa;
    private OnItemClickListen mListen;

    public interface OnItemClickListen
    {
         void onClickDisplay(int position);
    }

    public void setOnclickListen(OnItemClickListen mListen)
    {
        this.mListen=mListen;
    }

    public class RecycleHolder extends RecyclerView.ViewHolder
    {
        private TextView t1,t2,t3;
        private ImageView i1;
        public RecycleHolder(@NonNull View itemView,OnItemClickListen onItemClickListen) {
            super(itemView);
            t1=  (TextView)itemView.findViewById(R.id.recipe_name);
            t2 = (TextView)itemView.findViewById(R.id.recipe_author);
            i1  = (ImageView)itemView.findViewById(R.id.image_recipe);
            t3 = (TextView)itemView.findViewById(R.id.recipe_time);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                     if(onItemClickListen!=null)
                     {
                         int position = getAdapterPosition();
                         if(position!=RecyclerView.NO_POSITION)
                         {
                             onItemClickListen.onClickDisplay(position);
                         }
                     }
                }
            });
        }
    }
    public RecipeAdapter(ArrayList<ListOfRecipes> a)
    {
        mEa=a;
    }
    @NonNull
    @Override
    public RecycleHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_of_recipes_for_all,parent,false);
        RecycleHolder recycleHolder = new RecycleHolder(view,mListen);
        return recycleHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecycleHolder holder, int position) {
        ListOfRecipes b = mEa.get(position);
        holder.t1.setText(b.getRecipeTitle());
        holder.t2.setText(b.getRecipeAuthor());
        holder.t3.setText(b.getReady()+" min");
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference refer = storage.getReference();
        refer.child("users/"+b.getPhone()+"/"+b.getRecipeTitle()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).fit().into(holder.i1);
                Log.i("!#413424343434", uri.toString());
            }
        });
    }

    @Override
    public int getItemCount() {
        return mEa.size();
    }
}