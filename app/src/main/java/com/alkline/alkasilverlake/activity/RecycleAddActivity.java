package com.alkline.alkasilverlake.activity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
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
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alkline.alkasilverlake.R;
import com.alkline.alkasilverlake.Session;
import com.alkline.alkasilverlake.adapter.RecyclebottleAdapter;
import com.alkline.alkasilverlake.base.Alkasilverlake;
import com.alkline.alkasilverlake.base.BaseActivity;
import com.alkline.alkasilverlake.connection.RetrofitClient;
import com.alkline.alkasilverlake.helper.PDialog;
import com.alkline.alkasilverlake.model.BottleData;
import com.alkline.alkasilverlake.model.RecycleBottleData;
import com.alkline.alkasilverlake.model.WaterNameData;
import com.alkline.alkasilverlake.roomdatabasemodel.RecycleOrder;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;


public class RecycleAddActivity extends BaseActivity implements View.OnClickListener {


    EditText etrec_price;
    private PDialog pDialog;
    private Session session;
    private EditText etBottletype;
    private TextView tv_Bottle_quntituy, tv_rechead, plrec_change_add_update;
    private ImageView iv_rec_leftarrow;
    private ArrayList<BottleData> bottlelist = new ArrayList<>();
    private ArrayList<RecycleBottleData> recycleBottlelist = new ArrayList<>();
    private int test_count = 1;
    private Float price = 0.0f, price1;
    private String test,bottleusednew = "USED", bname, id, totalprice = "0", rec_updatehead, update, rbottleid;
    Handler handler = new Handler(Looper.myLooper());


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycle_add);
        session = new Session(this);
        init();
    }

    @SuppressLint("NewApi")
    public void init() {
        ArrayList<WaterNameData> waterlist = new ArrayList<>();
        etBottletype = findViewById(R.id.etrec_Bottletype);
        ImageView iv_bottle_add = findViewById(R.id.iv_rec_bottle_add);
        ImageView iv_bottle_sub = findViewById(R.id.iv_rec_bottle_sub);
        tv_Bottle_quntituy = findViewById(R.id.tv_rec_Bottle_quntituy);
//        iv_rec_leftarrow = findViewById(R.id.iv_rec_leftarrow);
        tv_rechead = findViewById(R.id.tv_rechead);
        etrec_price = findViewById(R.id.etrec_price);
        plrec_change_add_update = findViewById(R.id.plrec_change_add_update);
        etBottletype.setOnClickListener(this);
        RelativeLayout btn_Addproductlist = findViewById(R.id.btn_rec_Addproductlist);
        iv_bottle_sub.setOnClickListener(this);
        iv_bottle_add.setOnClickListener(this);
//        iv_rec_leftarrow.setOnClickListener(this);
        btn_Addproductlist.setOnClickListener(this);
        pDialog = new PDialog();
        session = new Session(this);

        if (pDialog.checkInternetConnection(this)) {
            ProductData();
        } else {
            Toast.makeText(this, "Network not available", Toast.LENGTH_SHORT).show();

        }
    }


    void updateUI() {
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("delivery_id")) {
            id = intent.getStringExtra("delivery_id");
            bname = intent.getStringExtra("delivery_product");
            test_count = Integer.parseInt(intent.getStringExtra("delivery_qty"));
            totalprice = intent.getStringExtra("deliverygeo_price");
            rbottleid = intent.getStringExtra("rbid");
            price = Float.valueOf(intent.getStringExtra("delivery_bottleprice"));
            rec_updatehead = intent.getStringExtra("rec_update");
            update = intent.getStringExtra("update");
            test = intent.getStringExtra("test");
            etBottletype.setText(bname);
            plrec_change_add_update.setText(update);
            etrec_price.setText(totalprice);
            tv_rechead.setText(rec_updatehead);
            tv_Bottle_quntituy.setText(String.valueOf(test_count));
        }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.etrec_Bottletype: {
                openAuthorizationDialog();
            }
            break;
           /* case R.id.iv_rec_leftarrow: {
                backPress();
            }
            break;*/

            case R.id.iv_rec_bottle_add: {
                if (test_count >= 1)
                    test_count++;
                tv_Bottle_quntituy.setText(String.valueOf(test_count));

                calculatePrice();
            /*  float f=  test_count*price;
                etrec_price.setText(String.valueOf(f));*/


            }
            break;
            case R.id.iv_rec_bottle_sub: {
                if (test_count > 1)
                    test_count--;
                tv_Bottle_quntituy.setText(String.valueOf(test_count));
               /* float f=  test_count*price;
                etrec_price.setText(String.valueOf(f));
*/
                calculatePrice();
            }
            break;

            case R.id.btn_rec_Addproductlist:


                if (etBottletype.getText().toString().isEmpty()) {
                   // Toast.makeText(this, "Please select bottle type", Toast.LENGTH_SHORT).show();
                    pDialog.alertDialog("Please select bottle type",RecycleAddActivity.this);

                } else if (update != null) {
                    final String pri = etrec_price.getText().toString();
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            dataManager().updateRecOrderIdPayment(bname, pri, String.valueOf(test_count), String.valueOf(price), id, rbottleid);

                        }
                    }).start();

                    if (test.equals("geoproduct")) {
                        Toast.makeText(this, "Product data update successfully", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(RecycleAddActivity.this, TabActivity.class);
                        startActivity(intent);
                    }
                    else {

                        Toast.makeText(this, "Product data update successfully", Toast.LENGTH_LONG).show();
//                        Intent intent = new Intent(RecycleAddActivity.this, CartnewActivity.class);
//                        startActivity(intent);

                    }
                } else {
                    if (session.isLoggedIn()){
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                final List<RecycleOrder> getallOrder = dataManager().loadAllRecycleProductlist(session.getRegistration().getId());
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {


                                        boolean isAdded = false;
                                        for (int i = 0; i < getallOrder.size(); i++) {
                                            if (getallOrder.get(i).getRecycle_product_watertype().equals(bname)) {
                                                isAdded = true;
                                            }
                                        }

                                        if (isAdded){
                                            alert12();
                                        }else{
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
                                final List<RecycleOrder> getallOrder = dataManager().loadAllRecycleProductlist("000");
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {


                                        boolean isAdded = false;
                                        for (int i = 0; i < getallOrder.size(); i++) {
                                            if (getallOrder.get(i).getRecycle_product_watertype().equals(bname)) {
                                                isAdded = true;
                                            }
                                        }

                                        if (isAdded){
                                            alert12();
                                        }else{
                                            alertAddProduct();
                                        }
                                    }








                                });

                            }
                        }).start();

                    }



                }
                break;

        }
    }

    private void alertAddProduct() {
        if (session.isLoggedIn()){
            totalprice = etrec_price.getText().toString();
            final RecycleOrder recycleOrder = new RecycleOrder();
            recycleOrder.setId(0);
            recycleOrder.setRecycle_user_id(session.getRegistration().getId());
            recycleOrder.setRecycle_product_watertype(bname);
            recycleOrder.setRecycle_product_price(totalprice);
            recycleOrder.setRecycle_product_qty(String.valueOf(test_count));
            recycleOrder.setBottleprice(String.valueOf(price));
            recycleOrder.setRecycleBottleId(rbottleid);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    dataManager().RecycleproductOrder(recycleOrder);

                }
            }).start();

            Toast.makeText(RecycleAddActivity.this, "Product data added successfully", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(RecycleAddActivity.this, TabActivity.class);
            startActivity(intent);
            finish();

        }else {
            UUID uuid = UUID.randomUUID();
            Alkasilverlake.getInstance().setGuestRecycle(uuid.toString());
            totalprice = etrec_price.getText().toString();
            final RecycleOrder recycleOrder = new RecycleOrder();
            recycleOrder.setId(0);
            recycleOrder.setRecycle_user_id("000");
            recycleOrder.setRecycle_product_watertype(bname);
            recycleOrder.setRecycle_product_price(totalprice);
            recycleOrder.setRecycle_product_qty(String.valueOf(test_count));
            recycleOrder.setBottleprice(String.valueOf(price));
            recycleOrder.setRecycleBottleId(rbottleid);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    dataManager().RecycleproductOrder(recycleOrder);

                }
            }).start();

            Toast.makeText(RecycleAddActivity.this, "Product data added successfully", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(RecycleAddActivity.this, TabActivity.class);
            startActivity(intent);
            finish();

        }
    }

    private void calculatePrice() {
        Float totalPrice = price * test_count;
        etrec_price.setText(String.valueOf(totalPrice));
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void ProductData() {


        pDialog.pdialog(RecycleAddActivity.this);


        String authToken = session.getAuthtoken();

        Call<ResponseBody> call = RetrofitClient.getInstance().getApi().Productlistdata(authToken);

        call.enqueue(new retrofit2.Callback<ResponseBody>() {
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
                                String recycle_type = jsonObject32.getString("recycle_type");

                                recycleBottleData = new RecycleBottleData(recycleBottleId, recycle_bottle_name, recycle_bottle_image, unit_type, price, bottle_type, status, crd, upd, recycle_type);
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

                            Toast.makeText(RecycleAddActivity.this, "Session expired, please login again.", Toast.LENGTH_SHORT).show();

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

    @SuppressLint("NewApi")
    private void openAuthorizationDialog() {
        final Dialog dialog = new Dialog(RecycleAddActivity.this);
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
        RecyclebottleAdapter recyclebottleAdapter = new RecyclebottleAdapter(this, recycleBottlelist, new RecyclebottleAdapter.RecBottlepos() {
            @Override
            public void getbottlepos(int pos) {

                bname = recycleBottlelist.get(pos).getRecycle_type();
                rbottleid = recycleBottlelist.get(pos).getRecycleBottleId();
                price = Float.parseFloat(recycleBottlelist.get(pos).getPrice());
                etBottletype.setText(bname);
                etrec_price.setText(String.valueOf(price));
                calculatePrice();
//                tv_Bottle_quntituy.setText(String.valueOf(1));
//                test_count = 1;
                dialog.dismiss();
            }
        });
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(RecycleAddActivity.this);
        check_recycler_view.setLayoutManager(mLayoutManager);
        check_recycler_view.setItemAnimator(new DefaultItemAnimator());
        check_recycler_view.setAdapter(recyclebottleAdapter);
        dialog.show();

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(RecycleAddActivity.this, TabActivity.class);
        startActivity(intent);
        finish();
    }

    public void alert12()
    {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(RecycleAddActivity.this);

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

                Intent intent = new Intent(RecycleAddActivity.this,TabActivity.class);
//                    intent.putExtra("paymentId",paymentId);
                startActivity(intent);
                finish();

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
