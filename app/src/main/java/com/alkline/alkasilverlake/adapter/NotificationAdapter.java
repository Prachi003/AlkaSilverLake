package com.alkline.alkasilverlake.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alkline.alkasilverlake.R;
import com.alkline.alkasilverlake.model.NotificationModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {
    private Context context;
    private List<NotificationModel.DataBean>dataBeans;

    public NotificationAdapter(Context context, List<NotificationModel.DataBean> dataBeans){
        this.context=context;
        this.dataBeans=dataBeans;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(context).inflate(R.layout.notification_adapter, viewGroup, false);
        return new ViewHolder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        NotificationModel.DataBean dataBean=dataBeans.get(i);
        @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat =
                new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        switch (dataBean.getType()) {
            case "Order":
                viewHolder.ivStatusIcon.setImageResource(R.drawable.round_delivered_btn);
                viewHolder.txtStatus.setText(R.string.order_created);
                viewHolder.txtStatus.setTextColor(ContextCompat.getColor(context, R.color.appColor));

                break;
            case "Payment":
                viewHolder.ivStatusIcon.setImageResource(R.drawable.ic_round_done_button);
                viewHolder.txtStatus.setText(R.string.payment_successful);
                viewHolder.txtStatus.setTextColor(ContextCompat.getColor(context, R.color.colorGreen));


                break;
            case "Delivery":
                viewHolder.ivStatusIcon.setImageResource(R.drawable.ic_yellow_arrow_ico);
                viewHolder.txtStatus.setText(R.string.delivery_in_transir);
                viewHolder.txtStatus.setTextColor(ContextCompat.getColor(context, R.color.orangecolor));


                break;
            default:
                viewHolder.ivStatusIcon.setImageResource(R.drawable.round_delivered_btn);
                viewHolder.txtStatus.setText(R.string.order_delivered);
                viewHolder.txtStatus.setTextColor(ContextCompat.getColor(context, R.color.appColor));
                break;


        }





        try {
            Date date1 = simpleDateFormat.parse(dataBean.getCrd());
            Date date2 = simpleDateFormat.parse(dataBean.getCurrent_time());
            printDifference(date1, date2, viewHolder.txtDateTime);

        } catch (ParseException e) {
            e.printStackTrace();
        }


        if (dataBean.getMessage().contains(dataBean.getInvoice_id())) {
            int startingPosition = dataBean.getMessage().indexOf(dataBean.getInvoice_id());
            int endingPosition = startingPosition + dataBean.getInvoice_id().length();
            Spannable WordtoSpan = new SpannableString(dataBean.getMessage());
            WordtoSpan.setSpan(new ForegroundColorSpan(Color.BLACK), startingPosition, endingPosition, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            WordtoSpan.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), startingPosition, endingPosition, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            viewHolder.txtMessage.setText(WordtoSpan);
        } else {
            viewHolder.txtMessage.setText(dataBean.getMessage());
        }


        viewHolder.txtStatus.setText(dataBean.getTitle());
        // viewHolder.txtMessage.setText(dataBean.getMessage());

    }

    /*public static Spanned getBoldString(String textNotBoldFirst, String textToBold, String textNotBoldLast) {
        String resultant = null;

        resultant = textNotBoldFirst + " " + "<b>" + textToBold + "</b>" + " " + textNotBoldLast;

        return Html.fromHtml(resultant);

    }*/


    @Override
    public int getItemCount() {
        return dataBeans.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtStatus;
        TextView txtMessage;
        TextView txtDateTime;
        ImageView ivStatusIcon;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtStatus=itemView.findViewById(R.id.txtStatus);
            txtMessage=itemView.findViewById(R.id.txtMessage);
            txtDateTime=itemView.findViewById(R.id.txtDateTime);
            ivStatusIcon = itemView.findViewById(R.id.ivStatusIcon);
        }
    }

    @SuppressLint("SetTextI18n")
    private void printDifference(Date startDate, Date endDate, TextView textView) {
        //milliseconds
        long different = endDate.getTime() - startDate.getTime();

        System.out.println("startDate : " + startDate);
        System.out.println("endDate : " + endDate);
        System.out.println("different : " + different);

        long secondsInMilli = 1000;
        long minutesInMilli = secondsInMilli * 60;
        long hoursInMilli = minutesInMilli * 60;
        long daysInMilli = hoursInMilli * 24;

        long elapsedDays = different / daysInMilli;
        different = different % daysInMilli;

        long elapsedHours = different / hoursInMilli;
        different = different % hoursInMilli;

        long elapsedMinutes = different / minutesInMilli;
        different = different % minutesInMilli;

        long elapsedSeconds = different / secondsInMilli;

        System.out.printf("%d days, %d hours, %d minutes, %d seconds%n", elapsedDays, elapsedHours, elapsedMinutes, elapsedSeconds);
        if (elapsedDays != 0) {
            textView.setText(elapsedDays + " " + context.getString(R.string.days_ago));


        } else if (elapsedHours != 0) {
            textView.setText(elapsedHours + " " + context.getString(R.string.hours_ago));


        } else if (elapsedMinutes != 0) {
            textView.setText(elapsedMinutes + " " + context.getString(R.string.minute_ago));


        } else if (elapsedSeconds != 0) {
            textView.setText(elapsedSeconds + " " + context.getString(R.string.second_ago));


        }
    }
}
