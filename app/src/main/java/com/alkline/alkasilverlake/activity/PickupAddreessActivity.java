package com.alkline.alkasilverlake.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.alkline.alkasilverlake.adapter.ProductAdapter;
import com.alkline.alkasilverlake.base.BaseActivity;
import com.alkline.alkasilverlake.connection.RetrofitClient;
import com.alkline.alkasilverlake.helper.PDialog;
import com.alkline.alkasilverlake.model.HistoryModel;
import com.alkline.alkasilverlake.profile.UserProfileActivity;
import com.alkline.alkasilverlake.roomdatabasemodel.AddOrder;
import com.alkline.alkasilverlake.roomdatabasemodel.RecycleOrder;
import com.alkline.alkasilverlake.utils.OnSwipeListener;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

public class PickupAddreessActivity extends BaseActivity implements View.OnClickListener, View.OnTouchListener {
    private TextView txtPickUpaddress;
    private PDialog pDialog;
    private Session session;
    private GestureDetector gestureDetector;
    private List<HistoryModel.DataBean> productsBeanList = new ArrayList<>();
    private ProductAdapter productAdapter;
    private TextView tvRecordMyLast;
    private TextView tvDate;
    private TextView tv_signin;
    private ImageView ivCart;
    private Handler handler = new Handler(Looper.myLooper());
    private List<HistoryModel.DataBean.ProductsBean> productsBeanArrayList = new ArrayList<>();
    private List<HistoryModel.DataBean.ProductsBean> temProductBeanList = new ArrayList<>();
    private List<HistoryModel.DataBean.RecycleBean> recycleBeanArrayList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pickup_addreess);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        initView();
    }

    public void initView() {
        txtPickUpaddress = findViewById(R.id.txtPickUpaddress);
        tvRecordMyLast = findViewById(R.id.tvRecordMyLast);
        tvDate = findViewById(R.id.tvDate);
        tv_signin = findViewById(R.id.tv_signin);
        Button btnMenu = findViewById(R.id.btnMenu);
        ImageView ivProfile = findViewById(R.id.ivProfile);
        RecyclerView recyclerLastOrder = findViewById(R.id.recyclerLastOrder);
        CardView setting_card = findViewById(R.id.setting_card);
        ivCart = findViewById(R.id.ivCart);
        TextView tvViewOrder = findViewById(R.id.tvViewOrder);
        RelativeLayout rlOrderClick = findViewById(R.id.rlOrderClick);
        RelativeLayout rlLastOrdersNew = findViewById(R.id.rlLastOrdersNew);
        RelativeLayout rlLastOrders = findViewById(R.id.rlLastOrders);
        btnMenu.setOnClickListener(this);
        ivProfile.setOnClickListener(this);
        rlLastOrders.setOnClickListener(this);
        ivCart.setOnClickListener(this);
        rlOrderClick.setOnClickListener(this);
        setting_card.setOnClickListener(this);
        tvViewOrder.setOnClickListener(this);
        rlLastOrdersNew.setOnClickListener(this);
        pDialog = new PDialog();
        session = new Session(PickupAddreessActivity.this);
        recyclerLastOrder.setLayoutManager(new LinearLayoutManager(PickupAddreessActivity.this));
        productAdapter = new ProductAdapter(temProductBeanList, PickupAddreessActivity.this, "address");
        recyclerLastOrder.setAdapter(productAdapter);

        getProductData();
        findViewById(R.id.rlParent).setOnTouchListener(this);
        gestureDetector = new GestureDetector(this, new OnSwipeListener() {

            @Override
            public boolean onSwipe(Direction direction) {
                if (direction == Direction.up) {
                    Intent intentOrderNow = new Intent(PickupAddreessActivity.this, TabActivity.class);
                    intentOrderNow.putExtra("from", "");
                    startActivity(intentOrderNow);

                    overridePendingTransition(R.anim.slide_up, R.anim.slide_down);


                }
                if (direction == Direction.right) {
                    Intent intentOrderNow = new Intent(PickupAddreessActivity.this, UserProfileActivity.class);
                    startActivity(intentOrderNow);
                    overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);

                }
                if (direction == Direction.left) {
                    Intent intentOrderNow = new Intent(PickupAddreessActivity.this, CartDetailActivity.class);
                    startActivity(intentOrderNow);
                    overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);

                }
                return true;
            }


        });

        if (productsBeanList.size() == 0) {
            tvRecordMyLast.setVisibility(View.GONE);
            tvDate.setVisibility(View.GONE);
            tv_signin.setVisibility(View.VISIBLE);

        } else {
            tvRecordMyLast.setVisibility(View.VISIBLE);
            tvDate.setVisibility(View.VISIBLE);
            tv_signin.setVisibility(View.GONE);

        }

    }

    public void getProductData() {
        pDialog.pdialog(PickupAddreessActivity.this);
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
                        String statusCode = jsonObject.optString("status");
                        if (statusCode.equals("success")) {
                            JSONObject jsonObjectpickupAddress = jsonObject.getJSONObject("pickupAddress");
                            String pickupAddress = jsonObjectpickupAddress.getString("address");
                            String lattitude = jsonObjectpickupAddress.getString("lattitude");
                            String longitude = jsonObjectpickupAddress.getString("longitude");
                            session.putAddress(pickupAddress);
                            session.putLatitude(lattitude);
                            session.putLongitude(longitude);

                            JSONArray jsonArray = jsonObject.getJSONArray("delivery_rates");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObjectdelivery_rates = jsonArray.getJSONObject(i);
                                String min_distance = jsonObjectdelivery_rates.getString("min_distance");
                                String min_charge = jsonObjectdelivery_rates.getString("min_charge");
                                String extra_mile_charge = jsonObjectdelivery_rates.getString("extra_mile_charge");
                                session.putExtraMileCharge(extra_mile_charge);
                                session.putMinDistance(min_distance);
                                session.putMinCharge(min_charge);


                            }
                            txtPickUpaddress.setText(pickupAddress);
                            getHistory();


                        }
                    } else if (response.code() == 400) {

                        String result = Objects.requireNonNull(response.errorBody()).string();
                        Log.d("response400", result);
                        JSONObject jsonObject = new JSONObject(result);
                        String statusCode = jsonObject.optString("responseCode");
                        if (statusCode.equals("300")) {
                            session.logout();
                            Toast.makeText(PickupAddreessActivity.this, "Session expired, please login again.", Toast.LENGTH_SHORT).show();
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnMenu:
                Intent intentOrderNow = new Intent(PickupAddreessActivity.this, TabActivity.class);
                intentOrderNow.putExtra("from", "");
                startActivity(intentOrderNow);
                break;

            case R.id.ivProfile:
                Intent profile = new Intent(PickupAddreessActivity.this, UserProfileActivity.class);
                startActivity(profile);
                break;

            case R.id.ivCart:
                Intent intent = new Intent(PickupAddreessActivity.this, CartDetailActivity.class);
                startActivity(intent);
                break;


            case R.id.rlLastOrdersNew:
                addCart();
                break;

            case R.id.tvViewOrder:
                Intent intent1 = new Intent(PickupAddreessActivity.this, TabActivity.class);
                intent1.putExtra("from", "addrees");
                startActivity(intent1);
                break;

        }
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        gestureDetector.onTouchEvent(motionEvent);
        return true;
    }

    public void getHistory() {
        pDialog.pdialog(PickupAddreessActivity.this);
        Call<ResponseBody> call = RetrofitClient.getInstance().getApi()
                .getOrders(session.getAuthtoken());
        call.enqueue(new retrofit2.Callback<ResponseBody>() {
            @SuppressLint("NewApi")
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull retrofit2.Response<ResponseBody> response) {

                try {
                    pDialog.hideDialog();
                    if (response.code() == 200) {
                        @SuppressLint({"NewApi", "LocalSuppress"})
                        String stresult = Objects.requireNonNull(response.body()).string();
                        Log.i("eail4254", "" + stresult);
                        JSONObject jsonObject = new JSONObject(stresult);
                        String statusCode = jsonObject.optString("status");
                        String msg = jsonObject.optString("message");
                        productsBeanList.clear();
                        productsBeanArrayList.clear();
                        recycleBeanArrayList.clear();
                        if (statusCode.equals("success")) {
                            Gson gson = new Gson();
                            JSONArray jsonArray = jsonObject.getJSONArray("data");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObjectData = jsonArray.getJSONObject(i);
                                HistoryModel.DataBean productsBean = gson.fromJson(jsonObjectData.toString(), HistoryModel.DataBean.class);
                                productsBeanList.add(productsBean);
                            }
                            if (productsBeanList.size() == 0) {
                                tvRecordMyLast.setVisibility(View.GONE);
                                tvDate.setVisibility(View.GONE);
                                tv_signin.setVisibility(View.VISIBLE);
                            } else {
                                tvRecordMyLast.setVisibility(View.VISIBLE);
                                tvDate.setVisibility(View.VISIBLE);
                                tv_signin.setVisibility(View.GONE);
                            }
                            for (int i = 0; i < productsBeanList.size(); i++) {
                                if (i == 0) {
                                    HistoryModel.DataBean productsBean = productsBeanList.get(i);
                                    productsBeanArrayList.addAll(productsBean.getProducts());
                                    recycleBeanArrayList.addAll(productsBean.getRecycle());
                                    String[] date = productsBean.getCrd().split(" ");
                                    String dateSplitt = date[0];

                                    @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");//yyyy-MM-dd'T'HH:mm:ss
                                    @SuppressLint("SimpleDateFormat") SimpleDateFormat output = new SimpleDateFormat("dd/MMM/yyyy");
                                    Date data;
                                    String formattedTime = "";
                                    try {
                                        data = sdf.parse(dateSplitt);
                                        formattedTime = output.format(data);
                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                    }
                                    String[] splittTime = formattedTime.split("/");
                                    String month = splittTime[1];
                                    String dateSp = splittTime[0] + "/" + splittTime[2];
                                    tvDate.setText(String.format("%s\n%s", month, dateSp));
                                    for (int j = 0; j < productsBean.getProducts().size(); j++) {
                                        HistoryModel.DataBean.ProductsBean productsBean1;
                                        if (j == 0) {
                                            productsBean1 = productsBean.getProducts().get(0);
                                            temProductBeanList.add(productsBean1);
                                        }
                                        if (j == 1) {
                                            productsBean1 = productsBean.getProducts().get(1);
                                            temProductBeanList.add(productsBean1);


                                        }
                                    }
                                }


                            }
                            productAdapter.notifyDataSetChanged();
                            //historyAdapter.notifyDataSetChanged();
                        } else {
                            Toast.makeText(PickupAddreessActivity.this, msg, Toast.LENGTH_SHORT).show();

                        }

                    } else if (response.code() == 400) {
                        @SuppressLint({"NewApi", "LocalSuppress"})
                        String result = Objects.requireNonNull(response.errorBody()).string();
                        Log.d("response400", result);
                        JSONObject jsonObject = new JSONObject(result);
                        String msg = jsonObject.optString("message");
                        Toast.makeText(PickupAddreessActivity.this, msg, Toast.LENGTH_SHORT).show();
                    } else if (response.code() == 401) try {
                        Log.d("ResponseInvalid", Objects.requireNonNull(response.errorBody()).string());


                    } catch (Exception e1) {
                        e1.printStackTrace();

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


    private void addCart() {
        if (session.isLoggedIn()) {
            new Thread(() -> {
                List<AddOrder> getallOrder = new ArrayList<>();
                dataManager().deleteAllOrder();
                dataManager().deleteAllRecOrder();

                for (int k = 0; k < productsBeanArrayList.size(); k++) {
                    HistoryModel.DataBean.ProductsBean productsBean = productsBeanArrayList.get(k);
                    final AddOrder addOrder = new AddOrder();
                    addOrder.setId(0);
                    addOrder.setBottletype(productsBean.getBottle_type());
                    addOrder.setWatertype(productsBean.getWater_name());
                    addOrder.setBottlecon(productsBean.getBottleCondition());
                    addOrder.setNo_bottle(String.valueOf(productsBean.getQuantity()));
                    addOrder.setUser_id(session.getRegistration().getId());
                    addOrder.setUnit_type(productsBean.getUnit_type());
                    addOrder.setBottlecon(productsBean.getBottleCondition());
                    addOrder.setBottlepricenew(productsBean.getNew_bottle_price());
                    addOrder.setBottlepriceold(productsBean.getOld_bottle_price());
                    addOrder.setWater_id(productsBean.getWater_id());
                    addOrder.setBottle_id(productsBean.getBottle_id());
                    addOrder.setProduct_water_bottle_price(productsBean.getWater_price());
                    addOrder.setProduct_price(productsBean.getProduct_price());
                    getallOrder.clear();
                    if (recycleBeanArrayList.size() > 0) {
                        for (int i = 0; i < recycleBeanArrayList.size(); i++) {
                            HistoryModel.DataBean.RecycleBean recycleBean = recycleBeanArrayList.get(i);
                            final RecycleOrder recycleOrder = new RecycleOrder();
                            recycleOrder.setId(Integer.parseInt(recycleBean.getRecycle_id()));
                            recycleOrder.setRecycle_user_id(session.getRegistration().getId());
                            recycleOrder.setRecycle_product_watertype(recycleBean.getBottle_type());
                            recycleOrder.setRecycle_product_price(recycleBean.getPrice());
                            recycleOrder.setUnit_type(recycleBean.getUnit_type());
                            recycleOrder.setRecycle_product_qty(recycleBean.getQuantity());
                            recycleOrder.setBottleprice(recycleBean.getPrice());
                            recycleOrder.setRecycleBottleId(recycleBean.getRecycleBottleId());
                            dataManager().RecycleproductOrder(recycleOrder);


                        }
                    }
                    getallOrder = dataManager().loadAllProductlist(session.getRegistration().getId());
                    dataManager().productOrder(addOrder);
                    if (session.isLoggedIn()) {
                        handler.post(() -> ivCart.callOnClick());
                    }


                }


            }).start();


        }

    }
}
