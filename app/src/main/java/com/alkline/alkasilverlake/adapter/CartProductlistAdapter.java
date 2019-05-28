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
import com.alkline.alkasilverlake.roomdatabasemodel.AddOrder;
import com.daimajia.swipe.SwipeLayout;

import java.util.List;

/**
 * Created by Ravi Birla on 09,February,2019
 */
public class CartProductlistAdapter extends RecyclerView.Adapter<CartProductlistAdapter.MyViewHolder> {

    private Context context;
    private List<AddOrder> addOrderList;
    private Deleteproduct deleteproduct;
    private NewAddproductqty addproductqty;
    private Subproductqty subproductqty;
    private editGetPostListener editGetPostListener;

    public interface Deleteproduct{
         void getdeleteposition(int pos);
    }
    public interface NewAddproductqty{
        void getaddposition(int pos);
    }
    public interface Subproductqty{
        void getsubposition(int pos);
    }

    public interface editGetPostListener{
        void getPosforEdit(int position);
    }

    public CartProductlistAdapter(Context context, List<AddOrder> addOrderList, Deleteproduct deleteproduct, NewAddproductqty addproductqty, Subproductqty subproductqty, editGetPostListener editGetPostListener)
    {
        this.context = context;
        this.addOrderList = addOrderList;
        this.editGetPostListener=editGetPostListener;
        this.deleteproduct = deleteproduct;
        this.addproductqty = addproductqty;
        this.subproductqty = subproductqty;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.cart_delivery_reqlist, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        AddOrder addOrder = addOrderList.get(position);
        holder.delivery_qty.setText(addOrder.getNo_bottle());
        holder.delivery_watertype.setText(addOrder.getWatertype());
        holder.delivery_product.setText(addOrder.getBottletype());
        holder.delivery_new_used.setText(addOrder.getBottlecon());
        holder.cart_tv_delivery_quntituy.setText(addOrder.getNo_bottle());

        if (position == 0) {
            holder.delivery_qty.setBackgroundResource(R.drawable.greenback);
            holder.delivery_watertype.setBackgroundResource(R.drawable.greenback);
            holder.delivery_product.setBackgroundResource(R.drawable.greenback);
            holder.delivery_new_used.setBackgroundResource(R.drawable.greenback);
            //  holder.cart_tv_delivery_quntituy.setBackgroundResource(R.drawable.greenback);

        } else {
            holder.delivery_qty.setBackgroundResource(R.drawable.button_blue_bag);
            holder.delivery_watertype.setBackgroundResource(R.drawable.button_blue_bag);
            holder.delivery_product.setBackgroundResource(R.drawable.button_blue_bag);
            holder.delivery_new_used.setBackgroundResource(R.drawable.button_blue_bag);
            // holder.cart_tv_delivery_quntituy.setBackgroundResource(R.drawable.button_blue_bag);
        }



    }

    //TOTAL SPACECRAFTS
    @Override
    public int getItemCount() {
        return addOrderList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {


        TextView delivery_qty, delivery_watertype, delivery_product, delivery_new_used, cart_tv_delivery_quntituy, cart_delivery_qty;
        ImageView cart_delivery_item_edit,cart_delivery_item_delete,cart_iv_delivery_sub,cart_iv_delivery_add;
        CardView cartdelete_post;
        SwipeLayout swipeLayout;
        boolean isSwiped = false;

        MyViewHolder(final View itemView) {
            super(itemView);
            delivery_qty = itemView.findViewById(R.id.cart_delivery_qty);

            delivery_watertype = itemView.findViewById(R.id.cart_delivery_watertype);
            delivery_product = itemView.findViewById(R.id.cart_delivery_product);
            delivery_new_used = itemView.findViewById(R.id.cart_delivery_new_used);
            cart_delivery_item_edit = itemView.findViewById(R.id.cart_delivery_item_edit);
            cart_delivery_item_delete = itemView.findViewById(R.id.cart_delivery_item_delete);
            cart_iv_delivery_sub = itemView.findViewById(R.id.cart_iv_delivery_sub);
            cart_tv_delivery_quntituy = itemView.findViewById(R.id.cart_tv_delivery_quntituy);
            cart_iv_delivery_add = itemView.findViewById(R.id.cart_iv_delivery_add);
            cartdelete_post = itemView.findViewById(R.id.cartdelete_post);
            swipeLayout = itemView.findViewById(R.id.selectedItem_view);
            swipeLayout.setSwipeEnabled(false);

            cart_iv_delivery_sub.setOnClickListener(v -> subproductqty.getsubposition(getAdapterPosition()));

            cart_iv_delivery_add.setOnClickListener(v -> addproductqty.getaddposition(getAdapterPosition()));

            cart_delivery_item_delete.setOnClickListener(v -> {
                //swipeLayout.addDrag(SwipeLayout.DragEdge.Left,cart_delivery_item_delete);
                //swipeLayout.addDrag(SwipeLayout.DragEdge.Right, swipeLayout.findViewById(R.id.cartdelete_post));

                if (isSwiped) {
                    isSwiped = false;
                    cart_delivery_item_delete.setImageResource(R.drawable.ic_delete);
                    cart_delivery_item_delete.setBackgroundResource(R.drawable.circlewhite);

                    swipeLayout.close();
                } else {
                    isSwiped = true;
                    cart_delivery_item_delete.setImageResource(R.drawable.ic_delete_red);
                    cart_delivery_item_delete.setBackgroundResource(R.drawable.circlered);
                    swipeLayout.open();
                }
                //  deleteproduct.getdeleteposition(getAdapterPosition());
                //notifyDataSetChanged();
            });


            cart_delivery_item_edit.setOnClickListener(v -> editGetPostListener.getPosforEdit(getAdapterPosition()));

            cartdelete_post.setOnClickListener(v -> {
                swipeLayout.close(true);
                deleteproduct.getdeleteposition(getAdapterPosition());
                notifyDataSetChanged();

            });
        }


    }

    public void setList(List<AddOrder>addOrderList){
        this.addOrderList=addOrderList;

    }


}
