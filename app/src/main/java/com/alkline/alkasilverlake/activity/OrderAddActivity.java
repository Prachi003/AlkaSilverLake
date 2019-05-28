package com.alkline.alkasilverlake.activity;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alkline.alkasilverlake.R;
import com.alkline.alkasilverlake.Session;
import com.alkline.alkasilverlake.adapter.BottleAdapter;
import com.alkline.alkasilverlake.base.Alkasilverlake;
import com.alkline.alkasilverlake.base.BaseActivity;
import com.alkline.alkasilverlake.connection.RetrofitClient;
import com.alkline.alkasilverlake.helper.PDialog;
import com.alkline.alkasilverlake.model.BottleData;
import com.alkline.alkasilverlake.model.RecycleBottleData;
import com.alkline.alkasilverlake.model.WaterNameData;
import com.alkline.alkasilverlake.roomdatabase.MyAppDatabase;
import com.alkline.alkasilverlake.roomdatabasemodel.AddOrder;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

public class OrderAddActivity extends BaseActivity implements View.OnClickListener {


    public MyAppDatabase myAppDatabase;
    Handler handler = new Handler(Looper.myLooper());
    private PDialog pDialog;

    private Session session;
    private EditText etBottletype;
    private TextView tv_Bottle_quntituy, tv_addorder, pl_change_add_update;
    private ImageView iv_bottle_add, iv_bottle_sub, iv_product_leftarrow;
    private RadioGroup radioGroup, radioGroup1;
    private RadioButton rbAlklinewatertype, rbPurifiedwatertype, rb_new_bottle_con, rb_used_bottle_con;
    private ArrayList<WaterNameData> waterlist;
    private ArrayList<BottleData> bottlelist = new ArrayList<>();
    private ArrayList<RecycleBottleData> recycleBottlelist = new ArrayList<>();
    private int test_count = 1;
    private float bottleprice = 0, product_water_bottle_price = 0;
    private EditText etorder_price;
    private RelativeLayout btn_Addproductlist;
    private String test = "", watername = "", bottleusednew = "", bname = "", waterprice = "0", bottlepricenew = "0", bottlepriceold = "0", updatedata, autoid, waterid, bottleid;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order__add_);
        session = new Session(this);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void init() {
        waterlist = new ArrayList<>();
        etBottletype = findViewById(R.id.etBottletype);
        iv_bottle_add = findViewById(R.id.iv_bottle_add);
        iv_bottle_sub = findViewById(R.id.iv_bottle_sub);
        iv_product_leftarrow = findViewById(R.id.iv_product_leftarrow);
        tv_Bottle_quntituy = findViewById(R.id.tv_Bottle_quntituy);
//        iv_product_leftarrow = findViewById(R.id.iv_product_leftarrow);
        pl_change_add_update = findViewById(R.id.pl_change_add_update);
        etorder_price = findViewById(R.id.etorder_price);
        tv_addorder = findViewById(R.id.tv_addorder);
        etBottletype.setOnClickListener(this);
        radioGroup = findViewById(R.id.radiogroupwatertype);
        radioGroup1 = findViewById(R.id.radiogroup_bottle_con);
        rbAlklinewatertype = findViewById(R.id.rbAlklinewatertype);
        rbPurifiedwatertype = findViewById(R.id.rbPurifiedwatertype);
        rb_new_bottle_con = findViewById(R.id.rb_new_bottle_con);
        rb_used_bottle_con = findViewById(R.id.rb_used_bottle_con);
        btn_Addproductlist = findViewById(R.id.btn_Addproductlist);
        iv_bottle_sub.setOnClickListener(this);
        iv_bottle_add.setOnClickListener(this);
//        iv_product_leftarrow.setOnClickListener(this);
        btn_Addproductlist.setOnClickListener(this);
        iv_product_leftarrow.setOnClickListener(this);
        pDialog = new PDialog();
        session = new Session(this);

        if (pDialog.checkInternetConnection(this)) {
            ProductData();
        } else {
            Toast.makeText(this, "Network not available", Toast.LENGTH_SHORT).show();

        }

        setRadioGroup();

    }

    void updateUI() {
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("id")) {
            test_count = Integer.parseInt(intent.getStringExtra("qty"));
            tv_Bottle_quntituy.setText(String.valueOf(test_count));
            bottlepricenew = intent.getStringExtra("bottlepricenew");
            bottlepriceold = intent.getStringExtra("bottlepriceold");
            updatedata = intent.getStringExtra("updatedata");
            bottleid = intent.getStringExtra("bid");
            waterid = intent.getStringExtra("wid");
            autoid = String.valueOf(intent.getStringExtra("id"));
            bname = intent.getStringExtra("btype");
            test = intent.getStringExtra("test");
            pl_change_add_update.setText(updatedata);
            String wtype = intent.getStringExtra("wtype");
            if (wtype.equals("Alkaline")) {
                rbAlklinewatertype.setChecked(true);
            } else {
                rbPurifiedwatertype.setChecked(true);
            }
            String bcondition = intent.getStringExtra("bcondi");
            if (bcondition.equals("NEW")) {

                    rb_new_bottle_con.setChecked(true);


            } else {
                rb_used_bottle_con.setChecked(true);
            }
            etBottletype.setText(intent.getStringExtra("btype"));
            etorder_price.setText(intent.getStringExtra("price"));
            tv_addorder.setText(intent.getStringExtra("uorder"));

        }
    }

    public void setRadioGroup() {
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (rbAlklinewatertype.isChecked()) {
                    watername = waterlist.get(1).getWater_name();
                    waterprice = waterlist.get(1).getWater_price();
                    waterid = waterlist.get(1).getWaterId();
                    //etorder_price.setText(waterprice);
                    rbAlklinewatertype.setButtonTintList(ColorStateList.valueOf(ContextCompat.getColor(OrderAddActivity.this, R.color.appColor)));
                    rbAlklinewatertype.setTextColor(getResources().getColor(R.color.appColor));
                    rbPurifiedwatertype.setButtonTintList(ColorStateList.valueOf(ContextCompat.getColor(OrderAddActivity.this, R.color.darkGrayColor)));
                    rbPurifiedwatertype.setTextColor(getResources().getColor(R.color.darkGrayColor));

                    //calculatePrice();
                } else if (rbPurifiedwatertype.isChecked()) {
                    watername = waterlist.get(0).getWater_name();
                    waterprice = waterlist.get(0).getWater_price();
                    waterid = waterlist.get(0).getWaterId();
                    //etorder_price.setText(waterprice);
                    rbAlklinewatertype.setButtonTintList(ColorStateList.valueOf(ContextCompat.getColor(OrderAddActivity.this, R.color.darkGrayColor)));
                    rbAlklinewatertype.setTextColor(getResources().getColor(R.color.darkGrayColor));
                    rbPurifiedwatertype.setButtonTintList(ColorStateList.valueOf(ContextCompat.getColor(OrderAddActivity.this, R.color.appColor)));
                    rbPurifiedwatertype.setTextColor(getResources().getColor(R.color.appColor));

                  //  calculatePrice();
                }
            }
        });


        radioGroup1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (rb_new_bottle_con.isChecked()) {
                    if (etBottletype.getText().toString().isEmpty()){
                        pDialog.alertDialog("Please Select bottle type",OrderAddActivity.this);
                        rb_new_bottle_con.setChecked(false);
                    }else {
                        bottleusednew = "NEW";

                        bottleprice = Float.parseFloat(bottlepricenew);
                        product_water_bottle_price = bottleprice + Float.parseFloat(waterprice);
                        //etorder_price.setText(String.valueOf(bottleprice));
                        rb_new_bottle_con.setButtonTintList(ColorStateList.valueOf(ContextCompat.getColor(OrderAddActivity.this, R.color.appColor)));
                        rb_new_bottle_con.setTextColor(getResources().getColor(R.color.appColor));
                        rb_used_bottle_con.setButtonTintList(ColorStateList.valueOf(ContextCompat.getColor(OrderAddActivity.this, R.color.darkGrayColor)));
                        rb_used_bottle_con.setTextColor(getResources().getColor(R.color.darkGrayColor));

                        calculatePrice();
                    }

                } else if (rb_used_bottle_con.isChecked()) {
                    if (etBottletype.getText().toString().isEmpty()){
                        pDialog.alertDialog("Please Select bottle type",OrderAddActivity.this);
                        rb_used_bottle_con.setChecked(false);

                    }else {
                        bottleusednew = "USED";

                        bottleprice = Float.parseFloat(bottlepriceold);
                        product_water_bottle_price = bottleprice + Float.parseFloat(waterprice);
                        //etorder_price.setText(String.valueOf(bottleprice));
                        rb_new_bottle_con.setButtonTintList(ColorStateList.valueOf(ContextCompat.getColor(OrderAddActivity.this, R.color.darkGrayColor)));
                        rb_new_bottle_con.setTextColor(getResources().getColor(R.color.darkGrayColor));
                        rb_used_bottle_con.setButtonTintList(ColorStateList.valueOf(ContextCompat.getColor(OrderAddActivity.this, R.color.appColor)));
                        rb_used_bottle_con.setTextColor(getResources().getColor(R.color.appColor));

                        calculatePrice();
                    }

                }
            }
        });
    }

    private void calculatePrice() {
        Float price = Float.valueOf(waterprice) + bottleprice;
        Float totalPrice = price * test_count;
        etorder_price.setText(String.valueOf(totalPrice));
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.etBottletype: {
                openAuthorizationDialog();
            }
            break;

//            case R.id.iv_product_leftarrow: {
//
//                Intent intent = new Intent(OrderAddActivity.this, TabActivity.class);
//                startActivity(intent);
//                finish();
//            }
//            break;

            case R.id.iv_bottle_add: {
                if (test_count >= 1)
                    test_count++;
                tv_Bottle_quntituy.setText(String.valueOf(test_count));
/*
                float f=  test_count*bottleprice;
                etorder_price.setText(String.valueOf(f));*/

                calculatePrice();
            }
            break;
            case R.id.iv_bottle_sub: {
                if (test_count > 1)
                    test_count--;
                tv_Bottle_quntituy.setText(String.valueOf(test_count));

                /*float f=  test_count*bottleprice;
                etorder_price.setText(String.valueOf(f));*/

                calculatePrice();

            }
            break;

            case R.id.btn_Addproductlist:


                if (watername.isEmpty()) {
                    pDialog.alertDialog("Please select Water type",OrderAddActivity.this);
                    //Toast.makeText(this, "Please select Water type", Toast.LENGTH_SHORT).show();
                } else if (etBottletype.getText().toString().isEmpty()) {
                    pDialog.alertDialog("Please select bottle type",OrderAddActivity.this);

                   // Toast.makeText(this, "Please select bottle type", Toast.LENGTH_SHORT).show();
                } else if (bottleusednew.isEmpty()) {
                    pDialog.alertDialog("Please select bottle Condition",OrderAddActivity.this);
                } else if (updatedata != null) {
                    final String pri = etorder_price.getText().toString();
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            dataManager().updateOrderIdPayment(watername, bname, bottleusednew, String.valueOf(test_count), pri, autoid, bottlepricenew, bottlepriceold, waterid, bottleid);

                        }
                    }).start();

                    if (test.equals("producttest")) {
                        Toast.makeText(this, "Product data update successfully", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(OrderAddActivity.this, TabActivity.class);
                        intent.putExtra("from", "");
                        startActivity(intent);
                    } else {

                        Toast.makeText(this, "Product data update successfully", Toast.LENGTH_LONG).show();
//                        Intent intent = new Intent(OrderAddActivity.this, CartnewActivity.class);
//                        startActivity(intent);

                    }
                } else {
                    if (session.isLoggedIn()){
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                final List<AddOrder> getallOrder = dataManager().loadAllProductlist(session.getRegistration().getId());
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {


                                        boolean isAdded = false;
                                        for (int i = 0; i < getallOrder.size(); i++) {
                                            if (getallOrder.get(i).getBottletype().equals(bname) && getallOrder.get(i).getBottlecon().equals(bottleusednew) && getallOrder.get(i).getWatertype().equals(watername)) {
                                                isAdded = true;
                                            } else {

                                            }
                                        }

                                        if (isAdded) {
                                            alert12();
                                        } else {
                                            alertAddProduct();
                                        }
                                    }


                                });

                            }
                        }).start();
                    }else {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                final List<AddOrder> getallOrder = dataManager().loadAllProductlist("000");
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {


                                        boolean isAdded = false;
                                        for (int i = 0; i < getallOrder.size(); i++) {
                                            if (getallOrder.get(i).getBottletype().equals(bname) && getallOrder.get(i).getBottlecon().equals(bottleusednew) && getallOrder.get(i).getWatertype().equals(watername)) {
                                                isAdded = true;
                                            } else {

                                            }
                                        }

                                        if (isAdded) {
                                            alert12();
                                        } else {
                                            alertAddProduct();
                                        }
                                    }


                                });

                            }
                        }).start();
                    }




                }
                break;


            case R.id.iv_product_leftarrow:
                onBackPressed();
                break;

        }


    }

    private void alertAddProduct() {
        Session session = Alkasilverlake.getInstance().getSessionManager();
        if(session.isLoggedIn()){
            String pri = etorder_price.getText().toString();
            final AddOrder addOrder = new AddOrder();
            addOrder.setId(0);
            addOrder.setBottletype(bname);
            addOrder.setWatertype(watername);

            addOrder.setBottlecon(bottleusednew);
            addOrder.setNo_bottle(String.valueOf(test_count));
            addOrder.setUser_id(session.getRegistration().getId());
            addOrder.setBottlepricenew(bottlepricenew);
            addOrder.setBottlepriceold(bottlepriceold);
            addOrder.setWater_id(waterid);
            addOrder.setBottle_id(bottleid);
            addOrder.setProduct_water_bottle_price(String.valueOf(product_water_bottle_price));
            addOrder.setProduct_price(pri);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    dataManager().productOrder(addOrder);

                }
            }).start();

            Toast.makeText(OrderAddActivity.this, "Product data added successfully", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(OrderAddActivity.this, TabActivity.class);
            intent.putExtra("from", "");
            startActivity(intent);

        }else {
            String pri = etorder_price.getText().toString();
            final AddOrder addOrder = new AddOrder();
            addOrder.setId(0);
            addOrder.setBottletype(bname);
            addOrder.setWatertype(watername);
            addOrder.setBottlecon(bottleusednew);
            addOrder.setNo_bottle(String.valueOf(test_count));
            UUID uuid = UUID.randomUUID();
            Alkasilverlake.getInstance().setGuestIdAdd(uuid.toString());
            addOrder.setUser_id("000");
            addOrder.setBottlepricenew(bottlepricenew);
            addOrder.setBottlepriceold(bottlepriceold);
            addOrder.setWater_id(waterid);
            addOrder.setBottle_id(bottleid);
            addOrder.setProduct_water_bottle_price(String.valueOf(product_water_bottle_price));
            addOrder.setProduct_price(pri);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    dataManager().productOrder(addOrder);

                }
            }).start();

            Toast.makeText(OrderAddActivity.this, "Product data added successfully", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(OrderAddActivity.this, TabActivity.class);
            startActivity(intent);
        }
    }

    public void ProductData() {


        pDialog.pdialog(OrderAddActivity.this);



        String authToken = session.getAuthtoken();

        Call<ResponseBody> call = RetrofitClient.getInstance().getApi().Productlistdata(authToken);

        call.enqueue(new retrofit2.Callback<ResponseBody>() {
            @SuppressLint("NewApi")
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                try {

                    pDialog.hideDialog();

                    http://www.app.alkasilverlake.com/apiv1/

                    if (response.code() == 200) {

                        String stresult = Objects.requireNonNull(response.body()).string();
                        Log.d("response", stresult);

                        JSONObject jsonObject = new JSONObject(stresult);

                        String statusCode = jsonObject.optString("status");


                        if (statusCode.equals("success")) {
                            JSONArray jsonArray = jsonObject.getJSONArray("delivery_rates");
                            JSONObject jsonObject2 = jsonArray.getJSONObject(0);
                            String officeChargesId = jsonObject2.getString("officeChargesId");
                            String min_distance = jsonObject2.getString("min_distance");
                            String min_charge = jsonObject2.getString("min_charge");
                            String extra_mile_charge = jsonObject2.getString("extra_mile_charge");


                            JSONArray jsonArray1 = jsonObject.getJSONArray("water");
                            for (int i = 0; i < jsonArray1.length(); i++) {

                                WaterNameData waterdata;
                                JSONObject jsonObject21 = jsonArray1.getJSONObject(i);
                                String waterId = jsonObject21.getString("waterId");
                                String water_name = jsonObject21.getString("water_name");
                                String water_price = jsonObject21.getString("water_price");
                                String water_image = jsonObject21.getString("water_image");
                                String status = jsonObject21.getString("status");
                                String crd = jsonObject21.getString("crd");
                                String upd = jsonObject21.getString("upd");

                                waterdata = new WaterNameData(waterId, water_name, water_price, water_image, status, crd, upd);
                                waterlist.add(waterdata);
                               /* if (waterlist.size() != 0) {
                                    watername = waterlist.get(0).getWater_name();
                                    waterprice = waterlist.get(0).getWater_price();

                                }*/

                            }

                            JSONArray jsonArray2 = jsonObject.getJSONArray("bottle");
                            for (int i = 0; i < jsonArray2.length(); i++) {

                                BottleData bottleData;
                                JSONObject jsonObject22 = jsonArray2.getJSONObject(i);
                                String bottleId = jsonObject22.getString("bottleId");
                                String bottle_name = jsonObject22.getString("bottle_name");
                                String bottle_image = jsonObject22.getString("bottle_image");
                                String unit_type = jsonObject22.getString("unit_type");
                                String new_bottle_price = jsonObject22.getString("new_bottle_price");
                                String old_bottle_price = jsonObject22.getString("old_bottle_price");
                                String bottle_type = jsonObject22.getString("bottle_type");
                                String status = jsonObject22.getString("status");
                                String crd = jsonObject22.getString("crd");
                                String upd = jsonObject22.getString("upd");
                                String bottles_type = jsonObject22.getString("bottles_type");

                                bottleData = new BottleData(bottleId, bottle_name, bottle_image, unit_type, new_bottle_price, old_bottle_price, bottle_type, status, crd, upd, bottles_type);
                                bottlelist.add(bottleData);

                            }

                            JSONArray jsonArray3 = jsonObject.getJSONArray("recycle_bottle");
                            for (int i = 0; i < jsonArray3.length(); i++) {

                                RecycleBottleData recycleBottleData;
                                JSONObject jsonObject32 = jsonArray3.getJSONObject(i);
                                String recycleBottleId = jsonObject32.getString("recycleBottleId");
                                String recycle_bottle_name = jsonObject32.getString("recycle_bottle_name");
                                String recycle_bottle_image = jsonObject32.getString("recycle_bottle_image");
                                String unit_type = jsonObject32.getString("unit_type");
                                String price = jsonObject32.getString("price");
                                String bottle_type = jsonObject32.getString("bottle_type");
                                String status = jsonObject32.getString("status");
                                String crd = jsonObject32.getString("crd");
                                String upd = jsonObject32.getString("upd");

                                recycleBottleData = new RecycleBottleData(recycleBottleId, recycle_bottle_name, recycle_bottle_image, unit_type, price, bottle_type, status, crd, upd, "");
                                recycleBottlelist.add(recycleBottleData);

                            }

                            updateUI();
                        }

                    } else if (response.code() == 400) {

                        String result = Objects.requireNonNull(response.errorBody()).string();

                        Log.d("response400", result);

                        JSONObject jsonObject = new JSONObject(result);

                        String statusCode = jsonObject.optString("responseCode");


                        if (statusCode.equals("300")) {
                            session.logout();

                            Toast.makeText(OrderAddActivity.this, "Session expired, please login again.", Toast.LENGTH_SHORT).show();

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

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void openAuthorizationDialog() {
        final Dialog dialog = new Dialog(OrderAddActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.layout_custom_dialog);

        ImageView clereg_logo = dialog.findViewById(R.id.clereg_logo);
        clereg_logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

            }
        });
        RecyclerView check_recycler_view = dialog.findViewById(R.id.check_recycler_view);
        BottleAdapter bottleAdapter = new BottleAdapter(this, bottlelist, new BottleAdapter.Bottlepos() {
            @Override
            public void getbottlepos(int pos) {
                bname = bottlelist.get(pos).getBottles_type();
                bottleid = bottlelist.get(pos).getBottleId();
                bottlepricenew = bottlelist.get(pos).getNew_bottle_price();
                bottlepriceold = bottlelist.get(pos).getOld_bottle_price();
                etBottletype.setText(bname);

                if (rb_new_bottle_con.isChecked()) {
                    bottleprice = Float.valueOf(bottlepricenew);

                } else if (rb_used_bottle_con.isChecked()) {
                    bottleprice = Float.valueOf(bottlepriceold);

                }

                calculatePrice();
                dialog.dismiss();
            }
        });
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(OrderAddActivity.this);
        check_recycler_view.setLayoutManager(mLayoutManager);
        check_recycler_view.setItemAnimator(new DefaultItemAnimator());
        check_recycler_view.setAdapter(bottleAdapter);
        dialog.show();

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(OrderAddActivity.this, TabActivity.class);
        intent.putExtra("from", "");
        startActivity(intent);
        finish();
    }

    public void alert12() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(OrderAddActivity.this);

        // Setting Dialog Title
        alertDialog.setTitle("Alert");

        // Setting Dialog Message
        alertDialog.setMessage("This item is already in cart so if you want to add same product,increase or decrease the quantity from home page.");

        // Setting Icon to Dialog
        alertDialog.setIcon(R.drawable.ic_logo);

        // Setting Positive "Yes" Button
        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            public void onClick(DialogInterface dialog, int which) {

                Intent intent = new Intent(OrderAddActivity.this, TabActivity.class);
//                    intent.putExtra("paymentId",paymentId);
                startActivity(intent);

            }
        });

//        // Setting Negative "NO" Button
        alertDialog.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // Write your code here to invoke NO event

                dialog.cancel();
            }
        });

        // Showing Alert Message
        alertDialog.show();
    }
}

