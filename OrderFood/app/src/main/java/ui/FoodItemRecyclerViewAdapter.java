package ui;

import android.app.SearchManager;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.orderfood.FoodmenuView;
import com.example.orderfood.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import model.FoodItem;


public class FoodItemRecyclerViewAdapter extends RecyclerView.Adapter<FoodItemRecyclerViewAdapter.ViewHolder> {
    private Context context;
    List<FoodItem> foodItemList;
    public static int TotalSum=0;
    public static String OrderPlaced="";
;

    public FoodItemRecyclerViewAdapter(Context context, List<FoodItem> foodItemList) {
        this.context = context;
        this.foodItemList = foodItemList;
    }

    @NonNull
    @Override
    public FoodItemRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.fooditem_row,parent,false);


        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodItemRecyclerViewAdapter.ViewHolder holder, int position) {

        FoodItem foodItem=foodItemList.get(position);
        //foodItem.getRestaurantname()
        String imageurl;
        holder.fooditempricelist.setText(String.valueOf(foodItem.getFoodprice()));
        holder.fooditemnamelist.setText(foodItem.getFooditemname());
        imageurl=foodItem.getImageuri();

        Picasso.get().load(imageurl).placeholder(R.drawable.download).fit().into(holder.fooditemlistimage);



    }

    @Override
    public int getItemCount() {
        return foodItemList.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView fooditemnamelist,fooditempricelist;
        public ImageView fooditemlistimage;



        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            context=itemView.getContext();
            fooditemnamelist=itemView.findViewById(R.id.fooditem_name_list);
            fooditempricelist=itemView.findViewById(R.id.fooditem_price_list);
            fooditemlistimage=itemView.findViewById(R.id.fooditem_image_list);
            itemView.setClickable(true);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {

            TotalSum+=Integer.parseInt(fooditempricelist.getText().toString());
            OrderPlaced+=""+fooditemnamelist.getText()+" amount:"+ fooditempricelist.getText().toString()+"\n";


        }
    }

}
