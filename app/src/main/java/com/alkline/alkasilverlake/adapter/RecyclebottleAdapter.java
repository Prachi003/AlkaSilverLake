package com.alkline.alkasilverlake.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alkline.alkasilverlake.R;
import com.alkline.alkasilverlake.model.RecycleBottleData;

import java.util.List;

/**
 * Created by Ravi Birla on 09,February,2019
 */
public class RecyclebottleAdapter extends RecyclerView.Adapter<RecyclebottleAdapter.MyViewHolder> {

    private Context context;
    private List<RecycleBottleData> recycleBottleDataList;
    private RecBottlepos recBottlepos;
    public interface RecBottlepos{
         void getbottlepos(int pos);
    }
    public RecyclebottleAdapter(Context context, List<RecycleBottleData> recycleBottleDataList, RecBottlepos recBottlepos)
    {
        this.context = context;
        this.recycleBottleDataList = recycleBottleDataList;
        this.recBottlepos = recBottlepos;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.checkdata, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        RecycleBottleData recycleBottleData = recycleBottleDataList.get(position);
        holder.tv_bottleName.setText(recycleBottleData.getRecycle_type());

    }

    //TOTAL SPACECRAFTS
    @Override
    public int getItemCount() {
        return recycleBottleDataList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {


        TextView tv_bottleName;

        MyViewHolder(final View itemView) {
            super(itemView);
            tv_bottleName = itemView.findViewById(R.id.tv_bottleName);


            itemView.setOnClickListener(v -> recBottlepos.getbottlepos(getAdapterPosition()));

        }
    }

}
