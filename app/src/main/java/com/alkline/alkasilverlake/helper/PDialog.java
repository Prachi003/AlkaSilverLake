package com.alkline.alkasilverlake.helper;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import com.alkline.alkasilverlake.R;
import com.amitshekhar.DebugDB;

import java.util.Objects;

/**
 * Created by Ravi Birla on 26,December,2018
 */
public class PDialog {

    private ProgressDialog pDialog;

    @SuppressLint("NewApi")
    public void pdialog(Context context) {
        pDialog = new ProgressDialog(context);
        Objects.requireNonNull(pDialog.getWindow()).setBackgroundDrawable(new
                ColorDrawable(android.graphics.Color.TRANSPARENT));
        pDialog.setIndeterminate(true);
        pDialog.setCancelable(false);
        pDialog.show();
        pDialog.setContentView(R.layout.my_progress);
    }


    public void hideDialog() {

        if (pDialog != null && pDialog.isShowing())
            pDialog.dismiss();
    }


    public boolean checkInternetConnection(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        assert cm != null;
        @SuppressLint("MissingPermission") NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

    public static void showDebugDBAddressLogToast() {
        try {
            String value = DebugDB.getAddressLog();
            Log.d("database url", " " + value);

        } catch (Exception ignore) {

        }
    }

    public void alertDialog(String msg,Context context) {
        new AlertDialog.Builder(context)
                .setTitle("Alert")
                .setMessage(msg)

                // Specifying a listener allows you to take an action before dismissing the dialog.
                // The dialog is automatically dismissed when a dialog button is clicked.
                .setPositiveButton(android.R.string.ok, (dialog, which) -> {
                    // Continue with delete operation
                })

                // A null listener allows the button to dismiss the dialog and take no further action.

                .show();

    }
}
