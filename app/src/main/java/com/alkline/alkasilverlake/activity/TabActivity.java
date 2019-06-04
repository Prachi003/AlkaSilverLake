package com.alkline.alkasilverlake.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.alkline.alkasilverlake.R;
import com.alkline.alkasilverlake.Session;
import com.alkline.alkasilverlake.base.BaseActivity;
import com.alkline.alkasilverlake.fragment.FavoriteFragment;
import com.alkline.alkasilverlake.fragment.HistoryFragment;
import com.alkline.alkasilverlake.fragment.HomeFragment;
import com.alkline.alkasilverlake.fragment.NotificationFragment;
import com.alkline.alkasilverlake.utils.OnSwipeListener;
import com.alkline.alkasilverlake.utils.SimpleGestureFilter;

public class TabActivity extends BaseActivity implements View.OnClickListener,View.OnTouchListener,SimpleGestureFilter.SimpleGestureListener {


    ImageView history;
    ImageView notification;
    ImageView favorite;
    ImageView home;
    Fragment fragment = null;
    private SimpleGestureFilter detector;

    private GestureDetector gestureDetector;
    private boolean doubleBackToExitPressedOnce;
    Runnable runnable;
    private String from;
    private String type = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            View decor = getWindow().getDecorView();
            decor.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        setContentView(R.layout.activity_tab);
        detector = new SimpleGestureFilter(TabActivity.this, this);



        home = findViewById(R.id.home);
        history = findViewById(R.id.history);
        favorite = findViewById(R.id.favorite);
        notification = findViewById(R.id.notification);

        home.setOnClickListener(this);
        history.setOnClickListener(this);
        favorite.setOnClickListener(this);
        notification.setOnClickListener(this);

        if (getIntent() != null) {
            from = getIntent().getStringExtra("from");
            if (getIntent().getStringExtra("type") != null) {
                type = getIntent().getStringExtra("type");
                if (type.equals("Order")) {
                    fragment = new HistoryFragment();
                    displaySelectedFragment(fragment);
                    home.setImageResource(R.drawable.ic_inactive_home_ico);
                    history.setImageResource(R.drawable.ic_active_progress_ico);
                    favorite.setImageResource(R.drawable.ic_inactive_heart_ico);
                    notification.setImageResource(R.drawable.ic_inactive_notification_ico);

                }
            }


        }

        if (from.equals("addrees") || from.equals("payment")) {
            fragment = new HistoryFragment();
            displaySelectedFragment(fragment);
            home.setImageResource(R.drawable.ic_inactive_home_ico);
            history.setImageResource(R.drawable.ic_active_progress_ico);
            favorite.setImageResource(R.drawable.ic_inactive_heart_ico);
            notification.setImageResource(R.drawable.ic_inactive_notification_ico);


        } else if (type.equals("Order")) {
            fragment = new HistoryFragment();
            displaySelectedFragment(fragment);
            home.setImageResource(R.drawable.ic_inactive_home_ico);
            history.setImageResource(R.drawable.ic_active_progress_ico);
            favorite.setImageResource(R.drawable.ic_inactive_heart_ico);
            notification.setImageResource(R.drawable.ic_inactive_notification_ico);
        } else {
            fragment = new HomeFragment();
            displaySelectedFragment(fragment);
            home.setImageResource(R.drawable.ic_active_home_ico);
            history.setImageResource(R.drawable.ic_inactive_progress_ico);
            favorite.setImageResource(R.drawable.ic_inactive_heart_ico);
            notification.setImageResource(R.drawable.ic_inactive_notification_ico);


        }


        findViewById(R.id.rlNewParent).setOnTouchListener(this);
        gestureDetector=new GestureDetector(this,new OnSwipeListener(){

            @Override
            public boolean onSwipe(Direction direction) {

                if (direction==Direction.down){
                    Session session = new Session(TabActivity.this);


                    if (!session.isLoggedIn()) {
                        Intent intentPicDel = new Intent(TabActivity.this, PickDelActivity.class);
                        overridePendingTransition(R.anim.slide_up, R.anim.slide_down);
                        startActivity(intentPicDel);

                    } else {
                        Intent intentPicDel = new Intent(TabActivity.this, PickupAddreessActivity.class);
                        overridePendingTransition(R.anim.slide_up, R.anim.slide_down);
                        startActivity(intentPicDel);
                    }

                }

                return true;
            }


        });


    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.home:
                // replaceFragment(ProfileFragment.newInstance(),true);
                fragment = new HomeFragment();
                displaySelectedFragment(fragment);
                updateTabView(R.id.home);
                break;
            case R.id.history:
                fragment = new HistoryFragment();
                displaySelectedFragment(fragment);
                updateTabView(R.id.history);
                break;
            case R.id.favorite:
                fragment = new FavoriteFragment();
                displaySelectedFragment(fragment);
                updateTabView(R.id.favorite);
                break;
            case R.id.notification:
                fragment = new NotificationFragment();
                displaySelectedFragment(fragment);
                updateTabView(R.id.notification);
                break;
            default:
                fragment = new HomeFragment();
                displaySelectedFragment(fragment);
                break;
        }
    }


    private void updateTabView(int flag) {
        switch (flag) {
            case R.id.home:
                home.setImageResource(R.drawable.ic_active_home_ico);
                history.setImageResource(R.drawable.ic_inactive_progress_ico);
                favorite.setImageResource(R.drawable.ic_inactive_heart_ico);
                notification.setImageResource(R.drawable.ic_inactive_notification_ico);


                break;

            case R.id.history:
                home.setImageResource(R.drawable.ic_inactive_home_ico);
                history.setImageResource(R.drawable.ic_active_progress_ico);
                favorite.setImageResource(R.drawable.ic_inactive_heart_ico);
                notification.setImageResource(R.drawable.ic_inactive_notification_ico);


                break;
            case R.id.favorite:
                home.setImageResource(R.drawable.ic_inactive_home_ico);
                history.setImageResource(R.drawable.ic_inactive_progress_ico);
                favorite.setImageResource(R.drawable.ic_active_heart_ico);
                notification.setImageResource(R.drawable.ic_inactive_notification_ico);


                break;

            case R.id.notification:
                home.setImageResource(R.drawable.ic_inactive_home_ico);
                history.setImageResource(R.drawable.ic_inactive_progress_ico);
                favorite.setImageResource(R.drawable.ic_inactive_heart_ico);
                notification.setImageResource(R.drawable.ic_active_notification_ico);


                break;

        }
    }

    private void displaySelectedFragment(Fragment fragment) {
        android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame, fragment);
        fragmentTransaction.commit();
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBackPressed() {
        /* Handle double click to finish activity*/

        Session session=new Session(TabActivity.this);
        if (session.isLoggedIn()){
            Handler handler = new Handler();
            FragmentManager fm = getSupportFragmentManager();
            int i = fm.getBackStackEntryCount();
            if (i > 0) {
                fm.popBackStack();
            } else if (!doubleBackToExitPressedOnce) {

                doubleBackToExitPressedOnce = true;
                Toast.makeText(this, "Click again to exit", Toast.LENGTH_SHORT).show();
//            MySnackBar.showSnackbar(this, findViewById(R.id.lyCoordinatorLayout), "Click again to exit");

                handler.postDelayed(() -> doubleBackToExitPressedOnce = false, 2000);

            } else {
                handler.removeCallbacks(runnable);
                finishAffinity();
            }


        }else {
            this.finish();
        }
    }


    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        gestureDetector.onTouchEvent(event);
        return true;
    }
    @Override
    public boolean dispatchTouchEvent(MotionEvent me) {
        // Call onTouchEvent of SimpleGestureFilter class
        this.detector.onTouchEvent(me);
        return super.dispatchTouchEvent(me);
    }

    @Override
    public void onSwipe(int direction) {
        //Detect the swipe gestures and display toast

        switch (direction) {

            case SimpleGestureFilter.SWIPE_RIGHT:
                //showToastMessage = "You have Swiped Right.";
                break;
            case SimpleGestureFilter.SWIPE_LEFT:
                //showToastMessage = "You have Swiped Left.";
                break;
            case SimpleGestureFilter.SWIPE_DOWN:
                Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.frame);
                if ((fragment instanceof HomeFragment)) {
                    Session session = new Session(TabActivity.this);


                    if (!session.isLoggedIn()) {
                        Intent intentPicDel = new Intent(TabActivity.this, PickDelActivity.class);
                        overridePendingTransition(R.anim.slide_up, R.anim.slide_down);
                        startActivity(intentPicDel);

                    } else {
                        Intent intentPicDel = new Intent(TabActivity.this, PickupAddreessActivity.class);
                        overridePendingTransition(R.anim.slide_up, R.anim.slide_down);
                        startActivity(intentPicDel);
                    }

                }
                break;
            case SimpleGestureFilter.SWIPE_UP:
                //showToastMessage = "You have Swiped Up.";
                break;

        }


    }

    @Override
    public void onDoubleTap() {

    }


}
