package ui;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.orderfood.FoodmenuView;
import com.example.orderfood.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import model.Restaurant;

public class RestaurantRecyclerViewAdapter extends RecyclerView.Adapter<RestaurantRecyclerViewAdapter.ViewHolder> {

    private Context context;
    private List<Restaurant> restaurantList;

    public  RestaurantRecyclerViewAdapter(Context context,List<Restaurant> restaurantList){
        this.context=context;
        this.restaurantList=restaurantList;
    }
    @NonNull
    @Override
    public RestaurantRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.restaurant_row,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RestaurantRecyclerViewAdapter.ViewHolder holder, int position) {
        Restaurant restaurant=restaurantList.get(position);
        String imageurl;
        holder.restaurantnamelist.setText(restaurant.getRestaurantname());
        holder.restaurantaddresslist.setText(restaurant.getRestaurantaddress());
        imageurl=restaurant.getResImageuri();

        Picasso.get().load(imageurl).placeholder(R.drawable.download).fit().into(holder.restaurantlistimage);


    }

    @Override
    public int getItemCount() {
        return restaurantList.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView restaurantnamelist,restaurantaddresslist;
        public ImageView restaurantlistimage;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            context=itemView.getContext();
            restaurantaddresslist=itemView.findViewById(R.id.restaurant_address_list);
            restaurantnamelist=itemView.findViewById(R.id.restaurant_name_list);
            restaurantlistimage=itemView.findViewById(R.id.restaurant_image_list);
            itemView.setClickable(true);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            Intent intent=new Intent(context,FoodmenuView.class);
            Restaurant restaurant=restaurantList.get(getAdapterPosition());
            intent.putExtra("restaurantnameselected", restaurant.getRestaurantname());
            context.startActivity(intent);
        }
    }
}
