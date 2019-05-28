package com.alkline.alkasilverlake.adapter;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alkline.alkasilverlake.R;
import com.alkline.alkasilverlake.payment.Model.StripeSaveCardResponce;

import java.util.List;

public class CardDiscAdapater extends RecyclerView.Adapter<CardDiscAdapater.ViewHolder> {

    private List<StripeSaveCardResponce.DataBean> cardList;
    private ScrollListener scrollListener;


    public CardDiscAdapater(List<StripeSaveCardResponce.DataBean> cardList,ScrollListener scrollListener) {
        this.cardList = cardList;
        this.scrollListener=scrollListener;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_disc_adapter, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        StripeSaveCardResponce.DataBean dataBean = cardList.get(position);
        holder.txtNumber.setText(dataBean.getLast4());
        holder.txtUsername.setText(String.valueOf(dataBean.getName()));
        String s = String.valueOf(dataBean.getExp_year()).substring(2);
        holder.txtDate.setText(String.format("%d/%s", dataBean.getExp_month(), s));
        if (dataBean.getBrand().equals("Visa")) {
            holder.ivCardType.setImageResource(R.drawable.visa_logo_ico);
        } else {
            holder.ivCardType.setImageResource(R.drawable.master_card_logo_ico);
        }


    }

    @Override
    public int getItemCount() {
        return cardList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView txtNumber;
        private TextView txtUsername;
        private TextView txtDate;
        private ImageView ivCardType;

        //private ImageView ivDelCard;

        ViewHolder(View itemView) {
            super(itemView);
            txtNumber = itemView.findViewById(R.id.txtNumber);
            txtUsername = itemView.findViewById(R.id.txtUsername);
            txtDate = itemView.findViewById(R.id.txtDate);
            ivCardType = itemView.findViewById(R.id.ivCardType);
           // ivDelCard = itemView.findViewById(R.id.ivDelCard);

            ///ivDelCard.setOnClickListener(this);


        }

        @Override
        public void onClick(View v) {
           /* if (v.getId() == R.id.ivDelCard) {
                StripeSaveCardResponce.DataBean dataBean = cardList.get(getAdapterPosition());
                deleteSaveCard.ondeleteSaveCard(dataBean.getId());

            }*/
        }
    }

    public interface ScrollListener<T extends ViewHolder> {
        //The same as ScrollStateChangeListener, but for the cases when you are interested only in onScroll()
        void onScroll(float scrollPosition, int currentIndex, int newIndex, @Nullable T currentHolder, @Nullable T newCurrentHolder);
    }


}
