package com.alkline.alkasilverlake.activity;

import android.app.ActivityManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.alkline.alkasilverlake.R;
import com.alkline.alkasilverlake.Session;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONObject;

import java.util.List;

public class MyFirebaseMessagingServices extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMsgService";
    //    String title = "Notification!";

    String CHANNEL_ID = "com.alkasilverlake";// The id of the channel.
    String title = "";
    String body = "";
    String type = "";
    String order_id = "";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        Log.e(TAG, "Body: " + remoteMessage.getData().toString());
        Log.e(TAG, "Body1: " + remoteMessage);


        if (remoteMessage.getData().containsKey("body")) {
            title = remoteMessage.getData().get("title");
            body = remoteMessage.getData().get("body");
            type = remoteMessage.getData().get("type");
            order_id = remoteMessage.getData().get("order_id");

            try {
                JSONObject obj = new JSONObject(type);

            } catch (Exception e) {

                e.printStackTrace();
            }
        }

        Intent intent = null;
        Session sessionManager = new Session(this);
        sendNotification(remoteMessage.getTtl(), title, body, order_id);

        /*switch (type) {
            case "Bill":
                if (sessionManager.isLoggedIn()) {
                    *//*intent = new Intent(this,Order_Summery.class);
                    intent.putExtra("reference_id", order_id);*//*
                } else {
                    new Intent(this, SigninActivity.class);
                }
                sendNotification(remoteMessage.getTtl(), title, intent,body);

                break;

            case "Payment":
                if (sessionManager.isLoggedIn()) {
                    *//*intent = new Intent(this,Order_Summery.class);
                    intent.putExtra("reference_id", order_id);*//*
                } else {
                    new Intent(this, SigninActivity.class);
                }


                break;

            case "Delivery":
                if (sessionManager.isLoggedIn()) {
                 *//*   intent = new Intent(this,Order_Summery.class);
                    intent.putExtra("reference_id", order_id);*//*
                } else {
                    new Intent(this, SigninActivity.class);
                }
                sendNotification(remoteMessage.getTtl(), title, intent,body);

                break;

            case "Order":
                if (sessionManager.isLoggedIn()) {
                   *//* intent = new Intent(this,Order_Summery.class);
                    intent.putExtra("reference_id", order_id);*//*
                } else {
                    new Intent(this, SigninActivity.class);
                }
                sendNotification(remoteMessage.getTtl(), title, intent,body);

                break;

        }*/
    }

    //    27/dec/2018 Changes
 /*   private void sendNotification(int id, String title, Intent intent, String messageBody) {
        PendingIntent pendingIntent = null;
        NotificationCompat.Builder notificationBuilder;
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_ONE_SHOT);
        CharSequence name = "MyChannal";// The user-visible name of the channel.
        int importance = NotificationManager.IMPORTANCE_HIGH;
        NotificationChannel mChannel;

        if (true) {

            notificationBuilder = new NotificationCompat.Builder(this, CHANNEL_ID)
                    .setSmallIcon(R.drawable.icon)
                    .setContentTitle(title)
                    .setContentText(messageBody)
                    .setPriority(Notification.PRIORITY_HIGH)
                    .setAutoCancel(true)
                    .setContentIntent(pendingIntent);

        } else {
            notificationBuilder = new NotificationCompat.Builder(this, CHANNEL_ID)
                    // .setSmallIcon(R.drawable.icon_app_192_white)
                    .setColor(getResources().getColor(R.color.appColor))
                    .setContentTitle(title)
                    .setContentText(messageBody)
                    .setSmallIcon(R.drawable.icon)
                    .setStyle(new NotificationCompat.BigTextStyle().bigText(messageBody))
                    .setAutoCancel(true)
                    .setSound(defaultSoundUri)
                    .setContentIntent(pendingIntent);
        }

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            notificationBuilder.setSmallIcon(R.drawable.icon);
            notificationBuilder.setColor(getResources().getColor(R.color.appColor));
        } else {
            notificationBuilder.setSmallIcon(R.drawable.icon);
        }

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            mChannel = new NotificationChannel(CHANNEL_ID, name, importance);
            assert notificationManager != null;
            notificationManager.createNotificationChannel(mChannel);
        }
        assert notificationManager != null;
        notificationManager.notify(1, notificationBuilder.build());
    }*/


    private boolean isAppOnForeground(Context context) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();
        if (appProcesses == null) {
            return false;
        }
        final String packageName = context.getPackageName();
        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
            if (appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND && appProcess.processName.equals(packageName)) {
                return true;
            }
        }
        return false;
    }

    private void sendNotification(int title, String title1, String body, String order_id) {
        Intent intent = new Intent(this, TabActivity.class);

        intent.putExtra("order_id", order_id);
        intent.putExtra("type", type);
        intent.putExtra("from", "");
        intent.putExtra("title", title);
        intent.putExtra("body", body);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        int iUniqueId = (int) (System.currentTimeMillis() & 0xfffffff);
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), iUniqueId, intent, PendingIntent.FLAG_ONE_SHOT);
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        String CHANNEL_ID = "my_channel_01";// The id of the channel.
        CharSequence name = "Abc";// The user-visible name of the channel.
        int importance = NotificationManager.IMPORTANCE_HIGH;
        NotificationChannel mChannel = null;

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle(this.title)
                .setContentText(this.body)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            notificationBuilder.setSmallIcon(R.drawable.icon);
            notificationBuilder.setColor(ContextCompat.getColor(getApplicationContext(), R.color.darkGrayColor));
        } else {
            notificationBuilder.setSmallIcon(R.drawable.icon);
        }

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            mChannel = new NotificationChannel(CHANNEL_ID, name, importance);
            assert notificationManager != null;
            notificationManager.createNotificationChannel(mChannel);

        }
        assert notificationManager != null;
        notificationManager.notify(iUniqueId, notificationBuilder.build());
    }
}
