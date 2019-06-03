package com.alkline.alkasilverlake.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alkline.alkasilverlake.R;
import com.alkline.alkasilverlake.Session;
import com.alkline.alkasilverlake.adapter.AddCartAdapter;
import com.alkline.alkasilverlake.adapter.RecycleCartAdapter;
import com.alkline.alkasilverlake.base.BaseActivity;
import com.alkline.alkasilverlake.roomdatabasemodel.AddOrder;
import com.alkline.alkasilverlake.roomdatabasemodel.RecycleOrder;
import com.alkline.alkasilverlake.roomdatabasemodel.UserInfoModel;
import com.alkline.alkasilverlake.utils.OnSwipeListener;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

public class CartDetailActivity extends BaseActivity implements View.OnClickListener, View.OnTouchListener {
    private Handler handler = new Handler(Looper.myLooper());
    AddCartAdapter addCartAdapter;
    private Session session;
    RecycleCartAdapter recycleCartAdapter;
    RecyclerView recyclerAdd, recycleProduct;
    LinearLayout llNewproduct, llrecycler;
    TextView txtUsername;
    List<AddOrder> getallOrder = new ArrayList<>();
    List<AddOrder> addOrderArrayList = new ArrayList<>();
    List<RecycleOrder> recycleOrderArrayList = new ArrayList<>();
    List<RecycleOrder> getallrecOrder = new ArrayList<>();
    float totalAdd = 0;
    float totalAddProduct = 0;
    float totalRecycler = 0;
    int miles = 0;
    String deilevryfee = "";
    private TextView tvDelivery;
    double taxPrice = 0;
    float finalPrice = 0;
    float distance = 0;
    float total = 0;
    TextView txtOrderTotalPrice;
    TextView txtTaxPrice;
    RelativeLayout rlCount;
    TextView txtFinalPrice, txtCount, tvSubTotal;
    private UserInfoModel userInfoModel;
    private GestureDetector gestureDetector;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_detail);
        initView();
    }

    private void initView() {
        txtUsername = findViewById(R.id.txtUsername);
        tvSubTotal = findViewById(R.id.tvSubTotal);
        tvDelivery = findViewById(R.id.tvDelivery);
        TextView txtRemoveRecycler = findViewById(R.id.txtRemoveRecycler);
        ImageView ivBack = findViewById(R.id.ivBack);
        recyclerAdd = findViewById(R.id.recyclerAdd);
        txtTaxPrice = findViewById(R.id.txtTaxPrice);
        rlCount = findViewById(R.id.rlCount);
        txtCount = findViewById(R.id.txtCount);
        recycleProduct = findViewById(R.id.recycleProduct);
        llNewproduct = findViewById(R.id.llNewproduct);
        llrecycler = findViewById(R.id.llrecycler);
        txtOrderTotalPrice = findViewById(R.id.txtOrderTotalPrice);
        txtFinalPrice = findViewById(R.id.txtFinalPrice);
        TextView txtRemove = findViewById(R.id.txtRemove);
        TextView txtEditAdd = findViewById(R.id.txtEditAdd);
        TextView txtEditRecycler = findViewById(R.id.txtEditRecycler);
        LinearLayout llCheckOut = findViewById(R.id.llCheckOut);
        txtRemove.setOnClickListener(this);
        ivBack.setOnClickListener(this);
        txtRemoveRecycler.setOnClickListener(this);
        llCheckOut.setOnClickListener(this);
        txtEditRecycler.setOnClickListener(this);
        txtEditAdd.setOnClickListener(this);
        session = new Session(CartDetailActivity.this);
        getDistance();
        updateUi();
        findViewById(R.id.rlParentActivity).setOnTouchListener(this);
        findViewById(R.id.llView).setOnTouchListener(this);
        gestureDetector = new GestureDetector(this, new OnSwipeListener() {

            @Override
            public boolean onSwipe(Direction direction) {

                if (direction == Direction.right) {
                    Intent intentOrderNow = new Intent(CartDetailActivity.this, PickupAddreessActivity.class);
                    startActivity(intentOrderNow);
                    overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);

                }
                if (direction == Direction.left) {
                    Intent intentOrderNow = new Intent(CartDetailActivity.this, PickupAddreessActivity.class);
                    startActivity(intentOrderNow);
                    overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);

                }
                return true;
            }


        });


    }


    private void updateUi() {

        new Thread(() -> {

            if (session.isLoggedIn()) {
                userInfoModel = dataManager().userinfo(session.getRegistration().getId());
                getallOrder = dataManager().loadAllProductlist(session.getRegistration().getId());
                addOrderArrayList.addAll(getallOrder);

                handler.post(() -> {
                    if (getallOrder.size() == 0) {
                        llNewproduct.setVisibility(View.GONE);
                    } else {
                        llNewproduct.setVisibility(View.VISIBLE);
                        addCartAdapter = new AddCartAdapter(addOrderArrayList, CartDetailActivity.this);
                        recyclerAdd.setLayoutManager(new LinearLayoutManager(CartDetailActivity.this));
                        recyclerAdd.setAdapter(addCartAdapter);

                    }

                    txtUsername.setText(userInfoModel.getFullName());


                });
            } else {
                //UserInfoModel userInfoModel = dataManager().userinfo("000");
                getallOrder = dataManager().loadAllProductlist("000");
                addOrderArrayList.addAll(getallOrder);
                handler.post(() -> {
                    if (getallOrder.size() == 0) {
                        txtUsername.setText(R.string.guest);
                        llNewproduct.setVisibility(View.GONE);
                    } else {
                        txtUsername.setText(R.string.guest);
                        llNewproduct.setVisibility(View.VISIBLE);
                        addCartAdapter = new AddCartAdapter(addOrderArrayList, CartDetailActivity.this);
                        recyclerAdd.setLayoutManager(new LinearLayoutManager(CartDetailActivity.this));
                        recyclerAdd.setAdapter(addCartAdapter);

                    }
                });


            }


            for (int i = 0; i < getallOrder.size(); i++) {
                taxPrice = (Double.valueOf(getallOrder.get(i).getProduct_price()) / 100.0f) * 9.5;
                totalAdd += Float.parseFloat(getallOrder.get(i).getProduct_price()) + taxPrice;
                totalAddProduct += Float.parseFloat(getallOrder.get(i).getProduct_price());

            }


            if (session.isLoggedIn()) {
                getallrecOrder = dataManager().loadAllRecycleProductlist(session.getRegistration().getId());
                recycleOrderArrayList.addAll(getallrecOrder);
                handler.post(() -> {
                    if (getallrecOrder.size() == 0) {
                        llrecycler.setVisibility(View.GONE);
                    } else {
                        llrecycler.setVisibility(View.VISIBLE);
                        recycleCartAdapter = new RecycleCartAdapter(recycleOrderArrayList, this);
                        recycleProduct.setLayoutManager(new LinearLayoutManager(CartDetailActivity.this));
                        recycleProduct.setAdapter(recycleCartAdapter);
                    }
                });


            } else {
                getallrecOrder = dataManager().loadAllRecycleProductlist("000");
                recycleOrderArrayList.addAll(getallrecOrder);
                handler.post(() -> {
                    if (getallrecOrder.size() == 0) {
                        llrecycler.setVisibility(View.GONE);
                    } else {
                        recycleCartAdapter = new RecycleCartAdapter(getallrecOrder, this);
                        recycleProduct.setLayoutManager(new LinearLayoutManager(CartDetailActivity.this));
                        recycleProduct.setAdapter(recycleCartAdapter);
                    }
                });
            }

            for (int i = 0; i < getallrecOrder.size(); i++) {
                totalRecycler += Float.parseFloat(getallrecOrder.get(i).getRecycle_product_price());
            }

            for (int i = 0; i < getallOrder.size(); i++) {
                taxPrice = (Double.valueOf(getallOrder.get(i).getProduct_price()) / 100.0f) * 9.5;
                totalAdd += Float.parseFloat(getallOrder.get(i).getProduct_price()) + taxPrice;

            }

            finalPrice = totalAdd - totalRecycler;
            if (!session.getDeliveryFee().equals("")) {
                total = Float.parseFloat(session.getDeliveryFee()) + finalPrice + Float.parseFloat(String.valueOf(taxPrice));

            }

            totalAddProduct = totalAddProduct - totalRecycler;

            handler.post(() -> {
                txtOrderTotalPrice.setText(String.format("$    %s", totalAddProduct));
                tvDelivery.setText(String.format("$   %s", session.getDeliveryFee()));
                txtFinalPrice.setText(String.format("$    %s", total));
                tvSubTotal.setText(String.format("$  %s", total));
                txtTaxPrice.setText(String.format("$    %s", taxPrice));
                if (getallOrder.size() != 0) {
                    rlCount.setVisibility(View.VISIBLE);
                    txtCount.setText(MessageFormat.format("{0}", getallOrder.size()));
                }
            });


        }).start();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txtRemove:
                new Thread(() -> {
                    dataManager().deleteAllOrder();
                    if (!session.isLoggedIn()) {
                        getallOrder = dataManager().loadAllProductlist("000");
                        addOrderArrayList.clear();

                        handler.post(() -> {
                            rlCount.setVisibility(View.GONE);
                            addCartAdapter.notifyDataSetChanged();
                            finalPrice = 0;
                            llNewproduct.setVisibility(View.GONE);
                            for (int i = 0; i < getallrecOrder.size(); i++) {
                                totalRecycler += Float.parseFloat(getallrecOrder.get(i).getRecycle_product_price());
                            }

                            for (int i = 0; i < getallrecOrder.size(); i++) {
                                totalRecycler += Float.parseFloat(getallrecOrder.get(i).getRecycle_product_price());
                            }

                            for (int i = 0; i < getallOrder.size(); i++) {
                                taxPrice = (Double.valueOf(getallOrder.get(i).getProduct_price()) / 100.0f) * 9.5;
                                totalAdd += Float.parseFloat(getallOrder.get(i).getProduct_price()) + taxPrice;
                            }
                            if (recycleOrderArrayList.size() == 0) {
                                totalRecycler = 0;
                            }
                            txtOrderTotalPrice.setText(String.format("$    %s", finalPrice));
                            txtFinalPrice.setText(String.format("$    %s", finalPrice));
                            txtTaxPrice.setText(String.format("$    %s", taxPrice));
                            txtTaxPrice.setText(String.format("$    %s", "0"));
                            tvSubTotal.setText(String.format("$  %s", "0"));


                        });
                    } else {
                        getallOrder = dataManager().loadAllProductlist(session.getRegistration().getId());
                        handler.post(() -> {
                            rlCount.setVisibility(View.GONE);
                            llNewproduct.setVisibility(View.GONE);
                            finalPrice = 0;
                            totalAdd = 0;
                            addCartAdapter.notifyDataSetChanged();
                            for (int i = 0; i < getallrecOrder.size(); i++) {
                                totalRecycler += Float.parseFloat(getallrecOrder.get(i).getRecycle_product_price());
                            }

                            for (int i = 0; i < getallOrder.size(); i++) {
                                taxPrice = (Double.valueOf(getallOrder.get(i).getProduct_price()) / 100.0f) * 9.5;
                                totalAdd += Float.parseFloat(getallOrder.get(i).getProduct_price()) + taxPrice;
                            }

                            if (recycleOrderArrayList.size() == 0) {
                                totalRecycler = 0;
                            }

                            finalPrice = totalAdd - totalRecycler;


                            //finalPrice=0-totalRecycler;
                            tvDelivery.setText("$ 0");
                            txtOrderTotalPrice.setText(String.format("$    %s", finalPrice));
                            txtFinalPrice.setText(String.format("$    %s", finalPrice));
                            txtTaxPrice.setText(String.format("$    %s", taxPrice));
                            txtTaxPrice.setText(String.format("$    %s", "0"));
                            tvSubTotal.setText(String.format("$  %s", "0"));


                        });
                    }

                }).start();

                break;

            case R.id.txtRemoveRecycler:
                new Thread(() -> {
                    if (!session.isLoggedIn()) {
                        dataManager().deleteAllRecOrder();
                        getallrecOrder = dataManager().loadAllRecycleProductlist("000");

                        recycleOrderArrayList.clear();
                        handler.post(() -> {
                            llrecycler.setVisibility(View.GONE);
                            totalAdd = 0;
                            recycleCartAdapter.notifyDataSetChanged();
                            for (int i = 0; i < getallrecOrder.size(); i++) {
                                totalRecycler += Float.parseFloat(getallrecOrder.get(i).getRecycle_product_price());
                            }

                            for (int i = 0; i < getallOrder.size(); i++) {
                                taxPrice = (Double.valueOf(getallOrder.get(i).getProduct_price()) / 100.0f) * 9.5;
                                totalAdd += Float.parseFloat(getallOrder.get(i).getProduct_price()) + taxPrice;
                            }

                            if (getallOrder.size() == 0) {
                                totalAdd = 0;
                            }

                            totalRecycler = 0;

                            finalPrice = totalAdd - totalRecycler;
                            txtOrderTotalPrice.setText(String.format("$    %s", finalPrice));
                            txtFinalPrice.setText(String.format("$    %s", finalPrice));
                            txtTaxPrice.setText(String.format("$    %s", taxPrice));
                            tvSubTotal.setText(String.format("$  %s", "0"));


                        });

                    } else {
                        dataManager().deleteAllRecOrder();
                        getallrecOrder = dataManager().loadAllRecycleProductlist(session.getRegistration().getId());
                        recycleOrderArrayList.clear();
                        handler.post(() -> {
                            llrecycler.setVisibility(View.GONE);
                            totalAdd = 0;
                            recycleCartAdapter.notifyDataSetChanged();
                            for (int i = 0; i < getallrecOrder.size(); i++) {
                                totalRecycler += Float.parseFloat(getallrecOrder.get(i).getRecycle_product_price());
                            }

                            for (int i = 0; i < getallOrder.size(); i++) {
                                taxPrice = (Double.valueOf(getallOrder.get(i).getProduct_price()) / 100.0f) * 9.5;
                                totalAdd += Float.parseFloat(getallOrder.get(i).getProduct_price()) + taxPrice;
                            }

                            if (getallOrder.size() == 0) {
                                totalAdd = 0;
                            }

                            totalRecycler = 0;
                            taxPrice = 0;

                            finalPrice = totalAdd - totalRecycler;
                            txtOrderTotalPrice.setText(String.format("$    %s", finalPrice));
                            txtFinalPrice.setText(String.format("$    %s", finalPrice));
                            txtTaxPrice.setText(String.format("$    %s", "0"));
                            tvSubTotal.setText(String.format("$  %s", "0"));


                        });
                    }

                }).start();

                break;

            case R.id.ivBack:
                onBackPressed();
                break;

            case R.id.llCheckOut:
                if (addOrderArrayList.size() == 0) {
                    Toast.makeText(this, "Please Add Product", Toast.LENGTH_SHORT).show();
                }

                if (session.isLoggedIn() && addOrderArrayList.size() != 0) {
                    Intent intent = new Intent(CartDetailActivity.this, CheckOutActivity.class);
                    intent.putExtra("grand_total", String.valueOf(total));
                    startActivity(intent);
                } else if (!session.isLoggedIn() && addOrderArrayList.size() > 0) {
                    Toast.makeText(this, "Please Login First", Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.txtEditAdd:
                Intent intent = new Intent(CartDetailActivity.this, TabActivity.class);
                intent.putExtra("from", "");
                startActivity(intent);
                break;

            case R.id.txtEditRecycler:

                Intent intent1 = new Intent(CartDetailActivity.this, TabActivity.class);
                intent1.putExtra("from", "");
                startActivity(intent1);
                break;

        }
    }

    public void getDistance() {
        Session session = new Session(CartDetailActivity.this);

        if (session.isLoggedIn()) {
            new Thread(() -> {
                UserInfoModel userInfoModel = dataManager().userinfo(session.getRegistration().getId());
                if (userInfoModel.getDeliveryLatitude() != null || userInfoModel.getBillingLatitude() != null) {
                    Location startPoint = new Location("locationA");
                    startPoint.setLatitude(Double.valueOf(userInfoModel.getDeliveryLatitude()));            //(34.095735);
                    startPoint.setLongitude(Double.valueOf(userInfoModel.getDeliveryLongitude()));          //(-118.282898);

                    Location endPoint = new Location("locationA");
                    endPoint.setLatitude(Double.parseDouble(session.getLatitude()));
                    endPoint.setLongitude(Double.parseDouble(session.getLongitude()));
                    distance = startPoint.distanceTo(endPoint);
                    float m = (float) (distance * 0.000621);
                    miles = Math.round(m);
                    session.putMiles(String.valueOf(miles));
                    if (m > Float.parseFloat(session.getMinCharge())) {
                        float deliveydiff = m - Float.parseFloat(session.getMinDistance());
                        float addconfirmamount = Float.parseFloat(session.getMinCharge()) + deliveydiff * Float.parseFloat(session.getExtraMileCharge());
                        deilevryfee = String.valueOf(addconfirmamount);
                    } else {
                        deilevryfee = String.valueOf(session.getMinCharge());
                    }
                    session.putDeLiveryFee(deilevryfee);


                } else {
                    handler.post(() -> Toast.makeText(CartDetailActivity.this, "Please Add delivery address and Billing Address", Toast.LENGTH_SHORT).show());
                }


            }).start();

        }
        // m= Math.round(distance);


    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        gestureDetector.onTouchEvent(motionEvent);
        return true;


    }


}


