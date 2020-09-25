package com.example.naaniz.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.naaniz.R;
import com.example.naaniz.rvmodels.OrdersRVModel;
import com.example.naaniz.viewholders.OrdersViewHolder;

import java.util.List;

public class OrdersAdapter extends RecyclerView.Adapter<OrdersViewHolder> {

    private List<OrdersRVModel> listItems;
    private Context context;
    private int check;

    public OrdersAdapter(List<OrdersRVModel> listItems, Context context, int check) {
        this.listItems = listItems;
        this.context = context;
        this.check = check;
    }

    @NonNull
    @Override
    public OrdersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v =LayoutInflater.from(parent.getContext())
                .inflate(R.layout.order_item, parent, false);

        return new OrdersViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull OrdersViewHolder holder, int position) {
        OrdersRVModel listItem = listItems.get(position);

        holder.orderTime.setText(listItem.getTime());
        holder.orderNo.setText(listItem.getOrder_no());
        holder.dishNames.setText(listItem.getDish_name());
        holder.dishQuantities.setText(listItem.getDish_quantity());
        holder.dishPrices.setText(listItem.getDish_price());
        holder.orderCost.setText(listItem.getTotal_cost());

        if (check == 2) {
            holder.repeatOrder.setVisibility(View.VISIBLE);
            holder.repeatOrder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(context, "Position: " + position,  Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }
}
