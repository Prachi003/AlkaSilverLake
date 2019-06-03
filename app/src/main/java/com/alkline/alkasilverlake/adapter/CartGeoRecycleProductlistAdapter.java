package com.alkline.alkasilverlake.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alkline.alkasilverlake.R;
import com.alkline.alkasilverlake.roomdatabasemodel.RecycleOrder;
import com.daimajia.swipe.SwipeLayout;

import java.util.List;

/**
 * Created by Ravi Birla on 09,February,2019
 */
public class CartGeoRecycleProductlistAdapter extends RecyclerView.Adapter<CartGeoRecycleProductlistAdapter.MyViewHolder> {

    private Context context;
    private List<RecycleOrder> recycleOrderslist;
    private RecDeleteproduct recDeleteproduct;
    private RecyclegetPos recyclegetPos;
    private RecAddproductqty recAddproductqty;
    private RecSubproductqty recSubproductqty;

    public CartGeoRecycleProductlistAdapter(Context context, List<RecycleOrder> recycleOrderslist, RecDeleteproduct recDeleteproduct, RecAddproductqty recAddproductqty, RecSubproductqty recSubproductqty, RecyclegetPos recyclegetPos)
    {
        this.context = context;
        this.recycleOrderslist = recycleOrderslist;
        this.recDeleteproduct = recDeleteproduct;
        this.recyclegetPos=recyclegetPos;
        this.recAddproductqty = recAddproductqty;
        this.recSubproductqty =recSubproductqty;
    }

    public interface RecSubproductqty {
        void getsubpos(int pos);
    }

    public interface RecyclegetPos {
        void getRecyclePos(int position);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.cart_geo_list, parent, false);
        return new MyViewHolder(v);
    }
    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        RecycleOrder recycleOrder = recycleOrderslist.get(position);
        holder.delivery_qty.setText(recycleOrder.getRecycle_product_qty());
        holder.cart_geodelivery_new_used.setText(R.string.used);
        holder.delivery_product.setText(recycleOrder.getRecycle_product_watertype());
        holder.deliverygeo_price.setText(recycleOrder.getRecycle_product_price());
        holder.cart_tv_delivery_quntituy.setText(recycleOrder.getRecycle_product_qty());
        holder.swipeLayout.addDrag(SwipeLayout.DragEdge.Right, holder.swipeLayout.findViewById(R.id.cartRecdelete_post));


    }

    public interface RecAddproductqty {
        void getaddpos(int pos);
    }

    public interface RecDeleteproduct {
        void getrecdeletepos(int pos);
    }

    //TOTAL SPACECRAFTS
    @Override
    public int getItemCount() {
        return recycleOrderslist.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {


        TextView delivery_qty;
        TextView delivery_product;
        TextView deliverygeo_price;
        TextView cart_tv_delivery_quntituy;
        TextView cart_geodelivery_new_used;
        ImageView cart_geo_item_delete,cart_geo_item_edit,cart_iv_delivery_sub,cart_iv_delivery_add;
        SwipeLayout swipeLayout;
        CardView cardView;
        boolean isSwiped = false;

        MyViewHolder(final View itemView) {
            super(itemView);
            delivery_qty = itemView.findViewById(R.id.cart_geo_qty);
            cart_geodelivery_new_used = itemView.findViewById(R.id.cart_geodelivery_new_used);
            delivery_product = itemView.findViewById(R.id.cart_geodelivery_product);
            deliverygeo_price = itemView.findViewById(R.id.cart_deliverygeo_price);
            cart_geo_item_delete = itemView.findViewById(R.id.cart_geo_item_delete);
            cart_geo_item_edit = itemView.findViewById(R.id.cart_geo_item_edit);
            cart_iv_delivery_sub = itemView.findViewById(R.id.cart_iv_geo_sub);
            cart_tv_delivery_quntituy = itemView.findViewById(R.id.cart_tv_geo_quntituy);
            cart_iv_delivery_add = itemView.findViewById(R.id.cart_iv_geo_add);
            swipeLayout = itemView.findViewById(R.id.RecselectedItem_view);
            cardView = itemView.findViewById(R.id.cartRecdelete_post);
            // swipeLayout.setSwipeEnabled(false);

            cart_iv_delivery_sub.setOnClickListener(v -> recSubproductqty.getsubpos(getAdapterPosition()));

            cart_iv_delivery_add.setOnClickListener(v -> recAddproductqty.getaddpos(getAdapterPosition()));

            cart_geo_item_delete.setOnClickListener(v -> {
                //recDeleteproduct.getrecdeletepos(getAdapterPosition());
                if (isSwiped) {
                    isSwiped = false;
                    cart_geo_item_delete.setImageResource(R.drawable.ic_delete);
                    cart_geo_item_delete.setBackgroundResource(R.drawable.circlewhite);

                    swipeLayout.close();
                } else {
                    isSwiped = true;
                    cart_geo_item_delete.setImageResource(R.drawable.ic_delete_red);
                    cart_geo_item_delete.setBackgroundResource(R.drawable.circlered);
                    swipeLayout.open();
                }

            });


            cart_geo_item_edit.setOnClickListener(v -> recyclegetPos.getRecyclePos(getAdapterPosition()));

            cardView.setOnClickListener(v -> {

                swipeLayout.close(true);
                recDeleteproduct.getrecdeletepos(getAdapterPosition());
                //notifyDataSetChanged();

            });
        }
    }

   /* public void setListAdapter(List<RecycleOrder>recycleOrderslist){
        this.recycleOrderslist=recycleOrderslist;
    }*/

}
