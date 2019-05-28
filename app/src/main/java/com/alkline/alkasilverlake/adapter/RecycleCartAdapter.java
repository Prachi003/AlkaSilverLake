package com.alkline.alkasilverlake.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alkline.alkasilverlake.R;
import com.alkline.alkasilverlake.roomdatabasemodel.RecycleOrder;

import java.util.List;

public class RecycleCartAdapter extends RecyclerView.Adapter<RecycleCartAdapter.ViewHolder> {
    private List<RecycleOrder>recycleOrderList;
    private Context context;

    public RecycleCartAdapter(List<RecycleOrder>recycleOrderList,Context context){
        this.recycleOrderList=recycleOrderList;
        this.context=context;

    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(context).inflate(R.layout.add_cart_layout, viewGroup, false);
        return new ViewHolder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        RecycleOrder recycleOrder=recycleOrderList.get(i);
        viewHolder.txtQuantity.setText(recycleOrder.getRecycle_product_qty());
        if (!recycleOrder.getRecycle_product_watertype().contains("Gallon")){
            viewHolder.txtProductName.setText(String.format("- %s Gallon %s", recycleOrder.getUnit_type(), recycleOrder.getRecycle_product_watertype()));

        }else {
            viewHolder.txtProductName.setText(String.format("- %s", recycleOrder.getRecycle_product_watertype()));

        }
    }

    @Override
    public int getItemCount() {
        return recycleOrderList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtQuantity,txtProductName;
        ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtQuantity=itemView.findViewById(R.id.txtQuantity);
            txtProductName=itemView.findViewById(R.id.txtProductName);
        }
    }
}
