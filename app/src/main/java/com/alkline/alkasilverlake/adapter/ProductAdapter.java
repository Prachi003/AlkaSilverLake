package com.alkline.alkasilverlake.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alkline.alkasilverlake.R;
import com.alkline.alkasilverlake.model.HistoryModel;

import java.text.MessageFormat;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {
    private List<HistoryModel.DataBean.ProductsBean>productsBeanList;
    private Context context;
    private String from;

    public ProductAdapter(List<HistoryModel.DataBean.ProductsBean> productsBeanList, Context context,String from){
        this.productsBeanList=productsBeanList;
        this.context=context;
        this.from=from;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(context).inflate(R.layout.product_adapter, viewGroup, false);
        return new ViewHolder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        HistoryModel.DataBean.ProductsBean productsBean=productsBeanList.get(i);
        viewHolder.txtProduct.setText(MessageFormat.format("-{0} Gallon {1} Bottles of {2}", productsBean.getUnit_type(), productsBean.getBottle_type(), productsBean.getWater_name()));
        viewHolder.txtCount.setText(productsBean.getQuantity());
        if (from.equals("address")){
          viewHolder.llItem.setGravity(Gravity.CENTER);
        }

    }

    @Override
    public int getItemCount() {
        return productsBeanList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtProduct;
        TextView txtCount;
        LinearLayout llItem;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtProduct=itemView.findViewById(R.id.txtProduct);
            txtCount=itemView.findViewById(R.id.txtCount);
            llItem=itemView.findViewById(R.id.llItem);

        }
    }
}
