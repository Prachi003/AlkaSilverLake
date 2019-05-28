package com.alkline.alkasilverlake.profile;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alkline.alkasilverlake.R;
import com.alkline.alkasilverlake.Session;
import com.alkline.alkasilverlake.activity.MapsActivity;
import com.alkline.alkasilverlake.activity.NewAccActivity;
import com.alkline.alkasilverlake.activity.PickupAddreessActivity;
import com.alkline.alkasilverlake.activity.SigninActivity;
import com.alkline.alkasilverlake.payment.PaymentMethodActivity;
import com.alkline.alkasilverlake.utils.OnSwipeListener;

public class UserProfileActivity extends AppCompatActivity implements View.OnClickListener, View.OnTouchListener {

    private GestureDetector gestureDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        initView();

    }

    private void initView() {
        Session session = new Session(UserProfileActivity.this);
        LinearLayout llRedeem = findViewById(R.id.llRedeem);
        LinearLayout llPickDel = findViewById(R.id.llPickDel);
        LinearLayout llLogOut = findViewById(R.id.llLogOut);
        ImageView imgBack = findViewById(R.id.imgBack);
        ImageView ivProfile = findViewById(R.id.ivProfile);
        TextView txtSignIn = findViewById(R.id.txtSignIn);
        TextView txtSignUp = findViewById(R.id.txtSignUp);
        RelativeLayout rlOrderClick = findViewById(R.id.rlOrderClick);
        LinearLayout llPaymethods = findViewById(R.id.llPaymethods);
        LinearLayout llPersonal = findViewById(R.id.llPersonal);
        imgBack.setOnClickListener(this);
        llPickDel.setOnClickListener(this);
        txtSignIn.setOnClickListener(this);
        txtSignUp.setOnClickListener(this);
        ivProfile.setOnClickListener(this);
        llPaymethods.setOnClickListener(this);
        llLogOut.setOnClickListener(this);
        llPersonal.setOnClickListener(this);
        if (session.isLoggedIn()) {
            llRedeem.setVisibility(View.VISIBLE);
            llPaymethods.setVisibility(View.VISIBLE);
            llPersonal.setVisibility(View.VISIBLE);

        } else {
            llLogOut.setVisibility(View.GONE);
            rlOrderClick.setVisibility(View.VISIBLE);
        }

        findViewById(R.id.rlParentnew).setOnTouchListener(this);
        gestureDetector = new GestureDetector(this, new OnSwipeListener() {

            @Override
            public boolean onSwipe(Direction direction) {

                if (direction == Direction.left) {
                    Intent intentOrderNow = new Intent(UserProfileActivity.this, PickupAddreessActivity.class);
                    startActivity(intentOrderNow);
                    overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);

                }
                return true;
            }


        });




    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgBack:
                onBackPressed();
                break;

            case R.id.llPersonal:
                Intent intent = new Intent(UserProfileActivity.this, PersonalProfileActivity.class);
                startActivity(intent);
                break;

            case R.id.ivProfile:
                Intent userProfileIntent = new Intent(UserProfileActivity.this, UserProfileActivity.class);
                startActivity(userProfileIntent);
                break;

            case R.id.txtSignIn:
                Intent loginIntent = new Intent(UserProfileActivity.this, SigninActivity.class);
                startActivity(loginIntent);
                break;

            case R.id.txtSignUp:
                Intent signUpIntent = new Intent(UserProfileActivity.this, NewAccActivity.class);
                startActivity(signUpIntent);
                break;

            case R.id.llPickDel:

                Intent pickUp = new Intent(UserProfileActivity.this, MapsActivity.class);
                startActivity(pickUp);
                break;

            case R.id.llPaymethods:
                Intent intentPay = new Intent(UserProfileActivity.this, PaymentMethodActivity.class);
                startActivity(intentPay);
                break;

            case R.id.llLogOut:
                Session session = new Session(UserProfileActivity.this);
                session.logout();
                break;


        }
    }


    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        gestureDetector.onTouchEvent(motionEvent);
        return true;

    }
}
