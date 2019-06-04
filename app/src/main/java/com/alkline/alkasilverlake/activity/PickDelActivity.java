package com.alkline.alkasilverlake.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alkline.alkasilverlake.R;
import com.alkline.alkasilverlake.Session;
import com.alkline.alkasilverlake.base.BaseActivity;
import com.alkline.alkasilverlake.connection.RetrofitClient;
import com.alkline.alkasilverlake.helper.PDialog;
import com.alkline.alkasilverlake.profile.UserProfileActivity;
import com.alkline.alkasilverlake.utils.OnSwipeListener;

import org.json.JSONObject;

import java.util.Objects;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;


public class PickDelActivity extends BaseActivity implements View.OnClickListener, View.OnTouchListener {
    private GestureDetector gestureDetector;
    private PDialog pDialog;
    private Session session;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_del);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        init();
    }

    private void init() {
        TextView new_account = findViewById(R.id.pd_new_account);
        TextView signin = findViewById(R.id.pd_signin);
        RelativeLayout rlOrderClick = findViewById(R.id.rlOrderClick);
        Button btnOrderNow = findViewById(R.id.btnOrderNow);
        Button btnMenu = findViewById(R.id.btnMenu);
        ImageView ivProfile = findViewById(R.id.ivProfile);
        ImageView ivCart = findViewById(R.id.ivCart);
        pDialog = new PDialog();
        session = new Session(PickDelActivity.this);
        getProductData();
        new_account.setOnClickListener(this);
        ivCart.setOnClickListener(this);
        ivProfile.setOnClickListener(this);
        btnOrderNow.setOnClickListener(this);
        btnMenu.setOnClickListener(this);
        signin.setOnClickListener(this);
        rlOrderClick.setOnClickListener(this);
        findViewById(R.id.rlParent).setOnTouchListener(this);
        gestureDetector = new GestureDetector(this, new OnSwipeListener() {

            @Override
            public boolean onSwipe(Direction direction) {
                if (direction == Direction.up) {
                    Intent intentOrderNow = new Intent(PickDelActivity.this, TabActivity.class);
                    intentOrderNow.putExtra("from", "");
                    startActivity(intentOrderNow);
                    overridePendingTransition(R.anim.slide_up, R.anim.slide_down);


                }
                if (direction == Direction.right) {
                    Intent intentOrderNow = new Intent(PickDelActivity.this, UserProfileActivity.class);
                    startActivity(intentOrderNow);
                    overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);

                }
                if (direction == Direction.left) {
                    Intent intentOrderNow = new Intent(PickDelActivity.this, CartDetailActivity.class);
                    startActivity(intentOrderNow);
                    overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);


                }
                return true;
            }


        });


    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.pd_new_account:

                Intent intent = new Intent(PickDelActivity.this, NewAccActivity.class);
                startActivity(intent);

                break;

            case R.id.pd_signin:

                Intent picDel = new Intent(PickDelActivity.this, SigninActivity.class);
                startActivity(picDel);
                break;

            case R.id.btnOrderNow:
                Intent intentOrderNow = new Intent(PickDelActivity.this, TabActivity.class);

                intentOrderNow.putExtra("from", "");
                startActivity(intentOrderNow);
                overridePendingTransition(R.anim.slide_up, R.anim.slide_down);
                break;

            case R.id.btnMenu:
                Intent intentMenu = new Intent(PickDelActivity.this, TabActivity.class);
                intentMenu.putExtra("from", "");
                startActivity(intentMenu);
                overridePendingTransition(R.anim.slide_up, R.anim.slide_down);


                break;

            case R.id.ivProfile:
                Intent userprofileIntent = new Intent(PickDelActivity.this, UserProfileActivity.class);
                startActivity(userprofileIntent);
                break;

            case R.id.ivCart:
                Intent intent1 = new Intent(PickDelActivity.this, CartDetailActivity.class);
                startActivity(intent1);
             /*   new Thread(() -> {
                    if (session.isLoggedIn()) {
                        final List<AddOrder> getallOrder = dataManager().loadAllProductlist(session.getRegistration().getId());
                        //cartProductlistAdapter.setList(getallOrder);

                        handler.post(() -> {
                            if (getallOrder.size() == 0) {
                                Toast.makeText(this, "Please Add Product", Toast.LENGTH_SHORT).show();
                            } else {
                                Intent intent1 = new Intent(PickDelActivity.this, CartDetailActivity.class);
                                startActivity(intent1);

                            }
                        });
                    } else {
                        final List<AddOrder> getallOrder = dataManager().loadAllProductlist("000");
                        //cartProductlistAdapter.setList(getallOrder);

                        handler.post(() -> {
                            if (getallOrder.size() == 0) {
                                Toast.makeText(this, "Please Add Product", Toast.LENGTH_SHORT).show();
                            } else {
                                Intent intent1 = new Intent(PickDelActivity.this, CartDetailActivity.class);
                                startActivity(intent1);

                            }
                        });

                    }

                }).start();*/
                break;



         /*   case R.id.rlOrderClick:
                Intent orderIntent=new  Intent(PickDelActivity.this,TabActivity.class);
                startActivity(orderIntent);
                overridePendingTransition(R.anim.slide_up,R.anim.slide_down);


                break;*/


        }

    }


    public void getProductData() {


        //progress.setVisibility(View.VISIBLE);
        pDialog.pdialog(PickDelActivity.this);


        String authToken = session.getAuthtoken();

        Call<ResponseBody> call = RetrofitClient.getInstance().getApi().Productlistdata(authToken);

        call.enqueue(new retrofit2.Callback<ResponseBody>() {
            @SuppressLint("NewApi")
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                try {
                    pDialog.hideDialog();

                    if (response.code() == 200) {

                        String stresult = Objects.requireNonNull(response.body()).string();
                        Log.d("response", stresult);

                        JSONObject jsonObject = new JSONObject(stresult);

                        JSONObject jsonObjectpickupAddress = jsonObject.getJSONObject("pickupAddress");
                        String pickupAddress = jsonObjectpickupAddress.getString("address");
                        String lattitude = jsonObjectpickupAddress.getString("lattitude");
                        String longitude = jsonObjectpickupAddress.getString("longitude");
                        session.putAddress(pickupAddress);
                        session.putLatitude(lattitude);
                        session.putLongitude(longitude);


                    } else if (response.code() == 400) {
                        String result = Objects.requireNonNull(response.errorBody()).string();
                        Log.d("response400", result);
                        JSONObject jsonObject = new JSONObject(result);
                        String statusCode = jsonObject.optString("responseCode");
                        if (statusCode.equals("300")) {
                            session.logout();
                            Toast.makeText(PickDelActivity.this, "Session expired, please login again.", Toast.LENGTH_SHORT).show();

                        }


                    } else if (response.code() == 401) {

                        try {
                            Log.d("ResponseInvalid", Objects.requireNonNull(response.errorBody()).string());
                        } catch (Exception e1) {

                            e1.printStackTrace();

                        }

                    } else if (response.code() == 404) {

                        try {
                            Log.d("ResponseInvalid", Objects.requireNonNull(response.errorBody()).string());

                        } catch (Exception e1) {

                            e1.printStackTrace();

                        }

                    }


                } catch (Exception e) {

                    e.printStackTrace();

                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {

                pDialog.hideDialog();
            }
        });


    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        gestureDetector.onTouchEvent(event);

        return true;
    }


}
