package com.alkline.alkasilverlake.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.view.WindowManager;

import com.alkline.alkasilverlake.R;
import com.alkline.alkasilverlake.Session;
import com.alkline.alkasilverlake.base.Alkasilverlake;
import com.alkline.alkasilverlake.base.BaseActivity;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;


public class SplashActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        updateLocation();
        new Handler().postDelayed(() -> {
            Session session = Alkasilverlake.getInstance().getSessionManager();

            if (session.isLoggedIn()) {
                startActivity(new Intent(SplashActivity.this, PickupAddreessActivity.class));
            } else {
                startActivity(new Intent(SplashActivity.this, PickDelActivity.class));
            }
            finish();
        }, 2000);


    }

    public void updateLocation() {
        FusedLocationProviderClient mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        mFusedLocationClient.getLastLocation().addOnSuccessListener(SplashActivity.this, location -> {
            if (location != null) {
                // Logic to handle location object
                Alkasilverlake.LATITUDE = location.getLatitude();
                Alkasilverlake.LONGITUDE = location.getLongitude();
                // AppLogger.e("Location", String.valueOf(Agrinvest.LATITUDE));
            }  //Location not available
            //AppLogger.e("Splash", "Location not available");

        });

    }
}
