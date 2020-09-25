package com.example.naaniz.viewholders;

import android.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.naaniz.R;

public class OrdersViewHolder extends RecyclerView.ViewHolder {
    public TextView orderTime;
    public TextView orderNo;
    public TextView dishNames;
    public TextView dishQuantities;
    public TextView dishPrices;
    public TextView orderCost;
    public Button repeatOrder;

    public OrdersViewHolder(@NonNull View itemView) {
        super(itemView);

        orderTime = itemView.findViewById(R.id.time_of_order);
        orderNo = itemView.findViewById(R.id.order_no);
        dishNames = itemView.findViewById(R.id.items_list);
        dishQuantities = itemView.findViewById(R.id.quantities_list);
        dishPrices = itemView.findViewById(R.id.prices_list);
        orderCost = itemView.findViewById(R.id.total_amount);
        repeatOrder = itemView.findViewById(R.id.repeat_order);
    }
}
