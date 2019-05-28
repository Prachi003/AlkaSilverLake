package com.alkline.alkasilverlake.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alkline.alkasilverlake.R;
import com.alkline.alkasilverlake.model.BottleData;

import java.util.List;

/**
 * Created by Ravi Birla on 09,February,2019
 */
public class BottleAdapter extends RecyclerView.Adapter<BottleAdapter.MyViewHolder> {

    Context context;
    List<BottleData> bottleDataList;
    Bottlepos bottlepos;
    public interface Bottlepos{
         void getbottlepos(int pos);
    }
    public BottleAdapter(Context context, List<BottleData> bottleDataList, Bottlepos bottlepos)
    {
        this.context = context;
        this.bottleDataList = bottleDataList;
        this.bottlepos = bottlepos;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.checkdata, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        BottleData bottleData = bottleDataList.get(position);
        holder.tv_bottleName.setText(bottleData.getBottles_type());

    }

    //TOTAL SPACECRAFTS
    @Override
    public int getItemCount() {
        return bottleDataList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {


        TextView tv_bottleName;
        public MyViewHolder(final View itemView) {
            super(itemView);
            tv_bottleName = itemView.findViewById(R.id.tv_bottleName);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    bottlepos.getbottlepos(getAdapterPosition());

                }
            });

        }
    }

}
