package com.alkline.alkasilverlake.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alkline.alkasilverlake.R;
import com.alkline.alkasilverlake.model.HistoryModel;

import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {

    private List<HistoryModel.DataBean> productsBeanList;
    private List<HistoryModel.DataBean.ProductsBean> productsBeans = new ArrayList<>();
    private Context context;
    private onclickListener onclickListener;


    public HistoryAdapter(List<HistoryModel.DataBean> productsBeanList, Context context, onclickListener onclickListener) {
        this.productsBeanList = productsBeanList;
        this.context = context;
        this.onclickListener = onclickListener;

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        HistoryModel.DataBean productsBean = productsBeanList.get(i);
        String[] date = productsBean.getCrd().split(" ");
        String dateSplitt = date[0];
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");//yyyy-MM-dd'T'HH:mm:ss
        @SuppressLint("SimpleDateFormat") SimpleDateFormat output = new SimpleDateFormat("dd/MM/yyyy");
        Date data;
        String formattedTime = "";
        try {
            data = sdf.parse(dateSplitt);
            formattedTime = output.format(data);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (productsBean.getFavourite().equals("1")) {
            viewHolder.ivFav.setImageResource(R.drawable.ic_like);
            viewHolder.txtDate.setText(MessageFormat.format("{0} - {1}", formattedTime, productsBean.getComment()));


        } else {
            viewHolder.ivFav.setImageResource(R.drawable.ic_heart);
            viewHolder.txtDate.setText(formattedTime);


        }


        if (productsBean.isSelect()) {

            viewHolder.ivAddCart.setImageResource(R.drawable.active_check_ico);
        } else {
            //else remove selected item color.
            viewHolder.ivAddCart.setImageResource(R.drawable.bold_circlewhite);

        }
        productsBeans.clear();
        for (int j = 0; j < productsBean.getProducts().size(); j++) {
            HistoryModel.DataBean.ProductsBean productsBean1;
            if (j == 0) {
                productsBean1 = productsBean.getProducts().get(0);
                productsBeans.add(productsBean1);

            }
            if (j == 1) {
                productsBean1 = productsBean.getProducts().get(1);
                productsBeans.add(productsBean1);
            }


        }

        viewHolder.recyclerData.setLayoutManager(new LinearLayoutManager(context));
        viewHolder.recyclerData.setAdapter(new ProductAdapter(productsBeans, context, "history"));
        //viewHolder.txtProduct.setText(MessageFormat.format("-{0} Gallon {1} Bottles of {2}", productsBean.getUnit_type(), productsBean.getBottle_type(), productsBean.getWater_name()));


        //viewHolder.txtCount.setText(productsBean.getProducts().get());

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(context).inflate(R.layout.adapter_history, viewGroup, false);
        return new ViewHolder(v);

    }

    public interface onclickListener {
        void getPos(int position);

        void getAddCartPos(int position, boolean isSelected);
    }

    @Override
    public int getItemCount() {
        return productsBeanList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView txtCount;
        TextView txtDate;
        TextView txtProduct;
        ImageView ivFav;
        ImageView ivAddCart;
        RecyclerView recyclerData;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtCount = itemView.findViewById(R.id.txtCount);
            txtDate = itemView.findViewById(R.id.txtDate);
            txtProduct = itemView.findViewById(R.id.txtProduct);
            ivFav = itemView.findViewById(R.id.ivFav);
            ivAddCart = itemView.findViewById(R.id.ivAddCart);
            recyclerData = itemView.findViewById(R.id.recyclerData);
            ivFav.setOnClickListener(this);
            ivAddCart.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.ivFav:
                    onclickListener.getPos(getAdapterPosition());
                    break;

                case R.id.ivAddCart:
                    HistoryModel.DataBean dataBean = productsBeanList.get(getAdapterPosition());
                    dataBean.setSelect(!dataBean.isSelect());
                    onclickListener.getAddCartPos(getAdapterPosition(), false);
                    notifyItemChanged(getAdapterPosition());


                    break;
            }

        }
    }


}
