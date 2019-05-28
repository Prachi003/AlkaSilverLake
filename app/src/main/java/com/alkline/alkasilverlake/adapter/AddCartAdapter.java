package com.alkline.alkasilverlake.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alkline.alkasilverlake.R;
import com.alkline.alkasilverlake.roomdatabasemodel.AddOrder;
import java.util.List;

public class AddCartAdapter extends RecyclerView.Adapter<AddCartAdapter.ViewHolder> {
    private List<AddOrder>addOrderList;
    private Context context;

    public AddCartAdapter(List<AddOrder>addOrderList, Context context){
        this.addOrderList=addOrderList;
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
        AddOrder addOrder=addOrderList.get(i);
        viewHolder.txtQuantity.setText(addOrder.getNo_bottle());
        if (!addOrder.getBottletype().contains("Gallon")){
            viewHolder.txtProductName.setText(String.format("- %s Gallon %s Bottles of %s", addOrder.getUnit_type(), addOrder.getBottletype(), addOrder.getWatertype()));
        }else {
            viewHolder.txtProductName.setText(String.format("- %s Bottles of %s", addOrder.getBottletype(), addOrder.getWatertype()));

        }

    }

    @Override
    public int getItemCount() {
        return addOrderList.size();
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
