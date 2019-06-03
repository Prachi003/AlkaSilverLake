package com.alkline.alkasilverlake.fragment;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alkline.alkasilverlake.R;
import com.alkline.alkasilverlake.Session;
import com.alkline.alkasilverlake.activity.CartDetailActivity;
import com.alkline.alkasilverlake.activity.PickupAddreessActivity;
import com.alkline.alkasilverlake.adapter.CartGeoRecycleProductlistAdapter;
import com.alkline.alkasilverlake.adapter.CartProductlistAdapter;
import com.alkline.alkasilverlake.base.Alkasilverlake;
import com.alkline.alkasilverlake.base.BaseFragment;
import com.alkline.alkasilverlake.connection.RetrofitClient;
import com.alkline.alkasilverlake.helper.PDialog;
import com.alkline.alkasilverlake.model.BottleData;
import com.alkline.alkasilverlake.model.RecycleBottleData;
import com.alkline.alkasilverlake.model.WaterNameData;
import com.alkline.alkasilverlake.pickerview.MyOptionsPickerView;
import com.alkline.alkasilverlake.pickerview.view.MyOptionPickerViewNew;
import com.alkline.alkasilverlake.profile.UserProfileActivity;
import com.alkline.alkasilverlake.roomdatabasemodel.AddOrder;
import com.alkline.alkasilverlake.roomdatabasemodel.RecycleOrder;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends BaseFragment implements View.OnClickListener, CartProductlistAdapter.editGetPostListener, CartGeoRecycleProductlistAdapter.RecyclegetPos, CartGeoRecycleProductlistAdapter.RecAddproductqty, CartGeoRecycleProductlistAdapter.RecDeleteproduct, CartGeoRecycleProductlistAdapter.RecSubproductqty, CartProductlistAdapter.NewAddproductqty,
        CartProductlistAdapter.Deleteproduct, CartProductlistAdapter.Subproductqty {


    private RecyclerView recyclerView;
    final ArrayList<String> arrayListCondition = new ArrayList<>();

    ProgressBar progress;
    private int test_count = 1;
    private int testCountRecycle = 1;
    private String bottleId = "0";
    private String priceRecycler = "0";
    private RecyclerView recyclerView1;
    private CardView cardNoData;
    private ArrayList<WaterNameData> waterlist = new ArrayList<>();
    private ArrayList<BottleData> bottlelist = new ArrayList<>();
    private ArrayList<RecycleBottleData> recycleBottlelist = new ArrayList<>();

    private CardView cardRecycled;

    private TextView product_add_text1, product_add_text2;
    private CartProductlistAdapter cartProductlistAdapter;
    private CartGeoRecycleProductlistAdapter cartGeoRecycleProductlistAdapter;
    private Handler handler = new Handler(Looper.myLooper());
    private List<AddOrder> addOrderList = new ArrayList<>();
    private List<RecycleOrder> recycleOrderList = new ArrayList<>();
    private Session session;
    private PDialog pDialog;
    private MyOptionsPickerView myOptionsPickerView;
    private String bname = "";
    private String bnameRecycler = "";
    String waterId;
    String waterName = "";
    String bottleUsednew = "";
    private LinearLayout llCheckOut;
    private RelativeLayout rlCount;
    private float totalPrice;
    private float totalPriceRecycler;
    private TextView txtCount;
    private String bottlePriceNew = "";

    private String bottlePriceOld;

    private float isproductWaterbottlePrice;

    private float bottlePrice = 0;
    private String waterPrice = "";


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
        if (!pDialog.checkInternetConnection(mContext)) {
            Toast.makeText(mContext, "Network not available", Toast.LENGTH_SHORT).show();
        }
        PDialog.showDebugDBAddressLogToast();


    }

    public void init(View view) {


        session = new Session(mContext);
        Button logout = view.findViewById(R.id.logout);
        RelativeLayout rlCart = view.findViewById(R.id.rlCart);
        rlCount = view.findViewById(R.id.rlCount);
        txtCount = view.findViewById(R.id.txtCount);
        pDialog = new PDialog();
        progress = view.findViewById(R.id.progress);
        llCheckOut = view.findViewById(R.id.llCheckOut);
        llCheckOut.setOnClickListener(this);
        logout.setOnClickListener(this);
        ImageView iv_del_Req_Add = view.findViewById(R.id.iv_del_Req_Add);
        ImageView iv_geo_Add = view.findViewById(R.id.iv_geo_Add);
        LinearLayout llBack = view.findViewById(R.id.llBack);
        ImageView ivProfile = view.findViewById(R.id.ivProfilenew);
        Button btnMenu = view.findViewById(R.id.btnMenu);
        cardNoData = view.findViewById(R.id.cardNoData);
        rlCart.setOnClickListener(this);
        cardNoData.setOnClickListener(this);
        cardRecycled = view.findViewById(R.id.cardRecycled);
        cardRecycled.setOnClickListener(this);
        recyclerView = view.findViewById(R.id.productlist_recycler_view);
        recyclerView1 = view.findViewById(R.id.geo_productlist_recycler_view);
        product_add_text1 = view.findViewById(R.id.product_add_text1);
        product_add_text2 = view.findViewById(R.id.product_add_text2);
        iv_del_Req_Add.setOnClickListener(this);
        iv_geo_Add.setOnClickListener(this);
        getProductData();
        ivProfile.setOnClickListener(this);
        iv_del_Req_Add.setOnClickListener(v -> {
            myOptionsPickerView = new MyOptionsPickerView(mContext);
            updateUi(waterlist);
            /*if (addOrderList.size()==0){
                updateUionFirst(waterlist);
            }else {
                updateUi(waterlist);
            }*/

        });
        ivProfile.setOnClickListener(v -> {
            Intent intent = new Intent(mContext, UserProfileActivity.class);
            startActivity(intent);
        });
        llBack.setOnClickListener(this);
        btnMenu.setOnClickListener(this);
        setList();


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_del_Req_Add:

                break;

            case R.id.logout:
                session.logout();
                break;

            case R.id.cardNoData:
                myOptionsPickerView = new MyOptionsPickerView(mContext);
                updateUi(waterlist);
                break;

            case R.id.iv_geo_Add:
                showRecyclerPopup();
                break;

            case R.id.cardRecycled:
                showRecyclerPopup();
                break;

            case R.id.btnMenu:
                Intent intentPicDel = new Intent(mContext, PickupAddreessActivity.class);
                startActivity(intentPicDel);
                break;

            case R.id.ivProfilenew:
                Intent intent = new Intent(mContext, UserProfileActivity.class);
                startActivity(intent);
                break;

            case R.id.rlCart:
                if (addOrderList.size() == 0) {
                    Toast.makeText(mContext, "Please Add Product", Toast.LENGTH_SHORT).show();
                } else {
                    Intent cart = new Intent(mContext, CartDetailActivity.class);
                    startActivityForResult(cart, 101);

                }
                break;


            case R.id.llCheckOut:
                if (addOrderList.size() == 0) {
                    Toast.makeText(mContext, "Please Add Order", Toast.LENGTH_SHORT).show();
                } else {
                    Intent checkOut = new Intent(mContext, CartDetailActivity.class);
                    startActivityForResult(checkOut, 101);

                }
                break;


        }
    }

    @SuppressLint("DefaultLocale")
    public void getDeliveryList() {


        new Thread(() -> {
            if (session.isLoggedIn()) {
                final List<AddOrder> getallOrder = dataManager().loadAllProductlist(session.getRegistration().getId());
                handler.post(() -> {
                    addOrderList.clear();
                    addOrderList.addAll(getallOrder);
                    if (getallOrder.size() == 0) {
                        //llCheckOut.setVisibility(View.GONE);
                        rlCount.setVisibility(View.GONE);
                        cardNoData.setVisibility(View.VISIBLE);
                        recyclerView.setVisibility(View.GONE);

                        product_add_text1.setVisibility(View.VISIBLE);
                    } else {

                        rlCount.setVisibility(View.VISIBLE);
                        txtCount.setText(String.format("%d", getallOrder.size()));
                        llCheckOut.setVisibility(View.VISIBLE);
                        cardNoData.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);

                        product_add_text1.setVisibility(View.GONE);
                    }

                    cartProductlistAdapter.notifyDataSetChanged();
                });
            } else {
                final List<AddOrder> getallOrder = dataManager().loadAllProductlist("000");


                handler.post(() -> {
                    addOrderList.clear();
                    addOrderList.addAll(getallOrder);
                    cartProductlistAdapter.notifyDataSetChanged();
                    if (getallOrder.size() == 0) {
                        rlCount.setVisibility(View.GONE);
                        llCheckOut.setVisibility(View.GONE);
                        cardNoData.setVisibility(View.VISIBLE);
                        recyclerView.setVisibility(View.GONE);

                        product_add_text1.setVisibility(View.VISIBLE);
                    } else {
                        txtCount.setText(String.format("%d", getallOrder.size()));
                        rlCount.setVisibility(View.VISIBLE);
                        llCheckOut.setVisibility(View.VISIBLE);
                        cardNoData.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);
                        product_add_text1.setVisibility(View.GONE);
                    }


                });
            }


            if (session.isLoggedIn()) {
                final List<RecycleOrder> getallrecOrder = dataManager().loadAllRecycleProductlist(session.getRegistration().getId());
                handler.post(() -> {
                    recycleOrderList.clear();
                    recycleOrderList.addAll(getallrecOrder);
                    cartGeoRecycleProductlistAdapter.notifyDataSetChanged();
                    if (getallrecOrder.size() == 0) {
                        // llCheckOut.setVisibility(View.GONE);
                        recyclerView1.setVisibility(View.GONE);
                        cardRecycled.setVisibility(View.VISIBLE);
                        product_add_text2.setVisibility(View.VISIBLE);
                    } else {
                        llCheckOut.setVisibility(View.VISIBLE);
                        recyclerView1.setVisibility(View.VISIBLE);
                        cardRecycled.setVisibility(View.GONE);
                        product_add_text2.setVisibility(View.GONE);

                    }

                });

            } else {
                final List<RecycleOrder> getallrecOrder = dataManager().loadAllRecycleProductlist("000");


                handler.post(() -> {
                    recycleOrderList.clear();
                    recycleOrderList.addAll(getallrecOrder);
                    cartGeoRecycleProductlistAdapter.notifyDataSetChanged();
                    if (getallrecOrder.size() == 0) {
                        recyclerView1.setVisibility(View.GONE);
                        cardRecycled.setVisibility(View.VISIBLE);
                        product_add_text2.setVisibility(View.VISIBLE);
                    } else {
                        llCheckOut.setVisibility(View.VISIBLE);
                        recyclerView1.setVisibility(View.VISIBLE);
                        cardRecycled.setVisibility(View.GONE);
                        product_add_text2.setVisibility(View.GONE);

                    }

                });
            }


        }).start();


    }

    public void getProductData() {
        pDialog.pdialog(mContext);
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
                        JSONObject jsonObjectpickupAddress = jsonObject.getJSONObject("pickupAddress");
                        String pickupAddress = jsonObjectpickupAddress.getString("address");
                        String lattitude = jsonObjectpickupAddress.getString("lattitude");
                        String longitude = jsonObjectpickupAddress.getString("longitude");
                        session.putAddress(pickupAddress);
                        session.putLatitude(lattitude);
                        session.putLongitude(longitude);
                        if (statusCode.equals("success")) {
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
                                if (waterdata.getWater_name().equals("NONE")) {
                                    waterdata.setWater_name("");
                                } else waterlist.add(waterdata);

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
                                String recycle_type = jsonObject32.getString("recycle_type");
                                String price = jsonObject32.getString("price");
                                String bottle_type = jsonObject32.getString("bottle_type");
                                String status = jsonObject32.getString("status");
                                String crd = jsonObject32.getString("crd");
                                String upd = jsonObject32.getString("upd");

                                recycleBottleData = new RecycleBottleData(recycleBottleId, recycle_bottle_name, recycle_bottle_image, unit_type, price, bottle_type, status, crd, upd, recycle_type);

                                recycleBottlelist.add(recycleBottleData);

                            }

                            //getDeliveryList();


                        }

                    } else if (response.code() == 400) {
                        String result = Objects.requireNonNull(response.errorBody()).string();
                        Log.d("response400", result);
                        JSONObject jsonObject = new JSONObject(result);
                        String statusCode = jsonObject.optString("responseCode");
                        if (statusCode.equals("300")) {
                            session.logout();
                            Toast.makeText(mContext, "Session expired, please login again.", Toast.LENGTH_SHORT).show();
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


    private void updateUi(final ArrayList<WaterNameData> waterlist) {
        arrayListCondition.clear();
        arrayListCondition.add("New");
        arrayListCondition.add("Used");
        myOptionsPickerView.show();
        Typeface typeface = ResourcesCompat.getFont(mContext, R.font.brownregular);
        myOptionsPickerView.setPicker(arrayListCondition, bottlelist, waterlist, false);
        myOptionsPickerView.setTitle("");
        assert typeface != null;
        myOptionsPickerView.setCustomFont(typeface);
        myOptionsPickerView.setCancelable(false);
        myOptionsPickerView.method("New", mContext, addOrderList);
        myOptionsPickerView.setSubmitButtonText("Add");
        myOptionsPickerView.setCyclic(false, false, false);
        myOptionsPickerView.setOnoptionsSelectListener((options1, option2, options3) -> {
            waterName = waterlist.get(options3).getWater_name();
            bname = bottlelist.get(option2).getBottles_type();
            bottleUsednew = arrayListCondition.get(options1);
            bottlePriceNew = bottlelist.get(option2).getNew_bottle_price();
            bottlePriceOld = bottlelist.get(option2).getOld_bottle_price();
            waterId = waterlist.get(options3).getWaterId();
            bottleId = bottlelist.get(option2).getBottleId();
            waterPrice = waterlist.get(options3).getWater_price();
            isproductWaterbottlePrice = bottlePrice + Float.parseFloat(waterPrice);
            if (arrayListCondition.get(options1).equals("New")) {
                float price = Float.valueOf(waterlist.get(options3).getWater_price()) + Float.valueOf(bottlelist.get(option2).getNew_bottle_price());
                totalPrice = price * test_count;
                bottlePrice = Float.parseFloat(bottlePriceNew);

            } else {
                bottlePrice = Float.parseFloat(bottlePriceOld);
                float price = Float.valueOf(waterlist.get(options3).getWater_price()) + Float.valueOf(bottlelist.get(option2).getOld_bottle_price());
                totalPrice = price * test_count;

            }

            alertAddProduct();


        });

    }

    private void alertAddProduct() {
        final Session session = Alkasilverlake.getInstance().getSessionManager();
        if (session.isLoggedIn()) {
            String pri = String.valueOf(totalPrice);
            final AddOrder addOrder = new AddOrder();
            addOrder.setId(0);
            addOrder.setBottletype(bname);
            addOrder.setWatertype(waterName);
            addOrder.setBottlecon(bottleUsednew);
            addOrder.setNo_bottle(String.valueOf(test_count));
            addOrder.setUser_id(session.getRegistration().getId());
            addOrder.setBottlepricenew(bottlePriceNew);
            addOrder.setBottlepriceold(bottlePriceOld);
            addOrder.setWater_id(waterId);
            addOrder.setBottle_id(bottleId);
            addOrder.setProduct_water_bottle_price(String.valueOf(isproductWaterbottlePrice));
            addOrder.setProduct_price(pri);
            new Thread(() -> {
                dataManager().productOrder(addOrder);
                if (session.isLoggedIn()) {
                    final List<AddOrder> getallOrder = dataManager().loadAllProductlist(session.getRegistration().getId());
                    addOrderList.clear();
                    addOrderList.addAll(getallOrder);
                    //cartProductlistAdapter.setList(getallOrder);

                    handler.post(() -> {
                        if (getallOrder.size() == 0) {
                            llCheckOut.setVisibility(View.GONE);
                            cardNoData.setVisibility(View.VISIBLE);
                            recyclerView.setVisibility(View.GONE);

                            product_add_text1.setVisibility(View.VISIBLE);
                        } else {
                            rlCount.setVisibility(View.VISIBLE);
                            txtCount.setText(MessageFormat.format("{0}", getallOrder.size()));

                            llCheckOut.setVisibility(View.VISIBLE);
                            cardNoData.setVisibility(View.GONE);
                            recyclerView.setVisibility(View.VISIBLE);

                            product_add_text1.setVisibility(View.GONE);
                        }
                        cartProductlistAdapter.notifyDataSetChanged();
                    });
                }

            }).start();

            //Toast.makeText(mContext, "Product data added successfully", Toast.LENGTH_LONG).show();

        } else {
            String pri = String.valueOf(totalPrice);
            final AddOrder addOrder = new AddOrder();
            addOrder.setId(0);
            addOrder.setBottletype(bname);
            addOrder.setWatertype(waterName);
            addOrder.setBottlecon(bottleUsednew);
            addOrder.setNo_bottle(String.valueOf(test_count));
            UUID uuid = UUID.randomUUID();
            Alkasilverlake.getInstance().setGuestIdAdd(uuid.toString());
            addOrder.setUser_id("000");
            addOrder.setBottlepricenew(bottlePriceNew);
            addOrder.setBottlepriceold(bottlePriceOld);
            addOrder.setWater_id(waterId);
            addOrder.setBottle_id(bottleId);
            addOrder.setProduct_water_bottle_price(String.valueOf(isproductWaterbottlePrice));
            addOrder.setProduct_price(pri);
            new Thread(() -> {
                dataManager().productOrder(addOrder);
                final List<AddOrder> getallOrder = dataManager().loadAllProductlist("000");
                addOrderList.clear();
                addOrderList.addAll(getallOrder);
                handler.post(() -> {
                    if (getallOrder.size() == 0) {
                        llCheckOut.setVisibility(View.GONE);
                        cardNoData.setVisibility(View.VISIBLE);
                        recyclerView.setVisibility(View.GONE);

                        product_add_text1.setVisibility(View.VISIBLE);
                    } else {
                        rlCount.setVisibility(View.VISIBLE);
                        txtCount.setText(MessageFormat.format("{0}", getallOrder.size()));
                        llCheckOut.setVisibility(View.VISIBLE);
                        cardNoData.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);

                        product_add_text1.setVisibility(View.GONE);
                    }
                    cartProductlistAdapter.notifyDataSetChanged();
                });


            }).start();





        }


    }


    void setList() {
        getDeliveryList();
        cartProductlistAdapter = new CartProductlistAdapter(mContext, addOrderList, this, this, this, this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mContext);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(cartProductlistAdapter);
        cartGeoRecycleProductlistAdapter = new CartGeoRecycleProductlistAdapter(mContext, recycleOrderList, this, this, this, this);
        RecyclerView.LayoutManager mLayoutManager1 = new LinearLayoutManager(mContext);
        recyclerView1.setLayoutManager(mLayoutManager1);
        recyclerView1.setItemAnimator(new DefaultItemAnimator());
        recyclerView1.setAdapter(cartGeoRecycleProductlistAdapter);

    }


    @Override
    public void getPosforEdit(final int position) {
        MyOptionsPickerView myOptionsPickerView = new MyOptionsPickerView(mContext);
        myOptionsPickerView.show();
        arrayListCondition.clear();
        arrayListCondition.add("New");
        arrayListCondition.add("Used");
        myOptionsPickerView.setPicker(arrayListCondition, bottlelist, waterlist, false);
        myOptionsPickerView.setTitle("");
        Typeface typeface = ResourcesCompat.getFont(mContext, R.font.brownregular);
        assert typeface != null;
        myOptionsPickerView.setCustomFont(typeface);
        myOptionsPickerView.setCancelable(false);
        myOptionsPickerView.method("New", mContext, addOrderList);
        int conditionPosition = 0;
        int bottleCondition = 0;
        int waterCondition = 0;
        myOptionsPickerView.setSubmitButtonText("Update");

        for (int i = 0; i < arrayListCondition.size(); i++) {
            if (arrayListCondition.get(i).equals(addOrderList.get(position).getBottlecon())) {
                conditionPosition = i;
                break;
            }
        }

        for (int i = 0; i < bottlelist.size(); i++) {
            if (bottlelist.get(i).getBottles_type().trim().equalsIgnoreCase(addOrderList.get(position).getBottletype().trim())) {
                bottleCondition = i;
                break;

            }
        }

        for (int i = 0; i < waterlist.size(); i++) {
            if (waterlist.get(i).getWater_name().equals(addOrderList.get(position).getWatertype())) {
                waterCondition = i;
                break;
            }
        }

        myOptionsPickerView.setSelectOptions(conditionPosition, bottleCondition, waterCondition);
        // myOptionsPickerView.setLabels(addOrderList.get(position).getBottlecon(),addOrderList.get(position).getBottletype(),addOrderList.get(position).getWatertype());
        myOptionsPickerView.setCyclic(false, false, false);
        test_count = Integer.parseInt(addOrderList.get(position).getNo_bottle());
        myOptionsPickerView.setOnoptionsSelectListener((options1, option2, options3) -> {
            waterName = waterlist.get(options3).getWater_name();
            bname = bottlelist.get(option2).getBottles_type();
            bottleUsednew = arrayListCondition.get(options1);
            bottlePriceNew = bottlelist.get(option2).getNew_bottle_price();
            bottlePriceOld = bottlelist.get(option2).getOld_bottle_price();
            waterId = waterlist.get(options3).getWaterId();
            bottleId = bottlelist.get(option2).getBottleId();
            waterPrice = waterlist.get(options3).getWater_price();
            isproductWaterbottlePrice = bottlePrice + Float.parseFloat(waterPrice);
            if (arrayListCondition.get(options1).equals("New")) {
                float price = Float.valueOf(waterlist.get(options3).getWater_price()) + Float.valueOf(bottlelist.get(option2).getNew_bottle_price());
                totalPrice = price * test_count;
                bottlePrice = Float.parseFloat(bottlePriceNew);

            } else {
                bottlePrice = Float.parseFloat(bottlePriceOld);
                float price = Float.valueOf(waterlist.get(options3).getWater_price()) + Float.valueOf(bottlelist.get(option2).getOld_bottle_price());
                totalPrice = price * test_count;

            }

            if (session.isLoggedIn()) {
                new Thread(() -> {
                    dataManager().updateOrderIdPayment(waterName, bname, bottleUsednew, String.valueOf(test_count), addOrderList.get(position).getProduct_price(), String.valueOf(addOrderList.get(position).getId()), bottlePriceNew, bottlePriceOld, waterId, bottleId);
                    final List<AddOrder> getallOrder = dataManager().loadAllProductlist(session.getRegistration().getId());
                    addOrderList.clear();
                    addOrderList.addAll(getallOrder);

                    handler.post(() -> {
                        //cartProductlistAdapter.setList(getallOrder);
                        if (getallOrder.size() == 0) {

                            cardNoData.setVisibility(View.VISIBLE);
                            recyclerView.setVisibility(View.GONE);
                            product_add_text1.setVisibility(View.VISIBLE);
                        } else {
                            cardNoData.setVisibility(View.GONE);
                            recyclerView.setVisibility(View.VISIBLE);
                            product_add_text1.setVisibility(View.GONE);
                        }
                        cartProductlistAdapter.notifyDataSetChanged();
                    });


                }).start();

            } else {
                new Thread(() -> {
                    dataManager().updateOrderIdPayment(waterName, bname, bottleUsednew, String.valueOf(test_count), addOrderList.get(position).getProduct_price(), String.valueOf(addOrderList.get(position).getId()), bottlePriceNew, bottlePriceOld, waterId, bottleId);
                    final List<AddOrder> getallOrder = dataManager().loadAllProductlist("000");
                    addOrderList.clear();
                    addOrderList.addAll(getallOrder);

                    handler.post(() -> {
                        //cartProductlistAdapter.setList(getallOrder);
                        if (getallOrder.size() == 0) {

                            cardNoData.setVisibility(View.VISIBLE);
                            recyclerView.setVisibility(View.GONE);
                            product_add_text1.setVisibility(View.VISIBLE);
                        } else {
                            cardNoData.setVisibility(View.GONE);
                            recyclerView.setVisibility(View.VISIBLE);
                            product_add_text1.setVisibility(View.GONE);
                        }
                        cartProductlistAdapter.notifyDataSetChanged();
                    });


                }).start();
            }



        });


    }


    public void showRecyclerPopup() {
        MyOptionPickerViewNew myOptionsPickerViewRecycler = new MyOptionPickerViewNew(mContext);
        myOptionsPickerViewRecycler.show();
        myOptionsPickerViewRecycler.method("Recycler", mContext);
        ArrayList<BottleData> bottlelist = new ArrayList<>();
        ArrayList<WaterNameData> waterlist = new ArrayList<>();
        myOptionsPickerViewRecycler.setPickerRe(bottlelist, recycleBottlelist, waterlist, false);
        myOptionsPickerViewRecycler.setTitle("");
        Typeface typeface = ResourcesCompat.getFont(mContext, R.font.brownregular);
        assert typeface != null;
        myOptionsPickerViewRecycler.setCustomFont(typeface);
        myOptionsPickerViewRecycler.setCyclic(false);
        myOptionsPickerViewRecycler.setSubmitButtonText("Add");
        myOptionsPickerViewRecycler.setOnoptionsSelectListener((options1, option2, options3) -> {
            bnameRecycler = recycleBottlelist.get(option2).getRecycle_type();
            bottleId = recycleBottlelist.get(option2).getRecycleBottleId();
            priceRecycler = recycleBottlelist.get(option2).getPrice();
            totalPriceRecycler = Float.parseFloat(recycleBottlelist.get(option2).getPrice()) * testCountRecycle;
            alertAddProductRecycler();

        });


    }

    private void alertAddProductRecycler() {
        if (session.isLoggedIn()) {
            final RecycleOrder recycleOrder = new RecycleOrder();
            recycleOrder.setId(0);
            recycleOrder.setRecycle_user_id(session.getRegistration().getId());
            recycleOrder.setRecycle_product_watertype(bnameRecycler);
            recycleOrder.setRecycle_product_price(String.valueOf(totalPriceRecycler));
            recycleOrder.setRecycle_product_qty(String.valueOf(testCountRecycle));
            recycleOrder.setBottleprice(String.valueOf(priceRecycler));
            recycleOrder.setRecycleBottleId(bottleId);
            new Thread(() -> {
                dataManager().RecycleproductOrder(recycleOrder);
                final List<RecycleOrder> getallrecOrder = dataManager().loadAllRecycleProductlist(session.getRegistration().getId());
                handler.post(() -> {
                    recycleOrderList.clear();
                    recycleOrderList.addAll(getallrecOrder);
                    cartGeoRecycleProductlistAdapter.notifyDataSetChanged();
                    if (getallrecOrder.size() == 0) {
                        llCheckOut.setVisibility(View.GONE);
                        recyclerView1.setVisibility(View.GONE);
                        cardRecycled.setVisibility(View.VISIBLE);
                        product_add_text2.setVisibility(View.VISIBLE);
                    } else {
                        llCheckOut.setVisibility(View.VISIBLE);
                        recyclerView1.setVisibility(View.VISIBLE);
                        cardRecycled.setVisibility(View.GONE);
                        product_add_text2.setVisibility(View.GONE);

                    }

                });

            }).start();


        } else {
            UUID uuid = UUID.randomUUID();
            Alkasilverlake.getInstance().setGuestRecycle(uuid.toString());
            final RecycleOrder recycleOrder = new RecycleOrder();
            recycleOrder.setId(0);
            recycleOrder.setRecycle_user_id("000");
            recycleOrder.setRecycle_product_watertype(bnameRecycler);
            recycleOrder.setRecycle_product_price(String.valueOf(totalPriceRecycler));
            recycleOrder.setRecycle_product_qty(String.valueOf(testCountRecycle));
            recycleOrder.setBottleprice(String.valueOf(priceRecycler));
            recycleOrder.setRecycleBottleId(bottleId);
            new Thread(() -> {
                dataManager().RecycleproductOrder(recycleOrder);
                if (session.isLoggedIn()) {
                    final List<RecycleOrder> getallrecOrder = dataManager().loadAllRecycleProductlist(session.getRegistration().getId());


                    handler.post(() -> {
                        recycleOrderList.clear();
                        recycleOrderList.addAll(getallrecOrder);
                        cartGeoRecycleProductlistAdapter.notifyDataSetChanged();
                        if (getallrecOrder.size() == 0) {
                            llCheckOut.setVisibility(View.GONE);
                            recyclerView1.setVisibility(View.GONE);
                            cardRecycled.setVisibility(View.VISIBLE);
                            product_add_text2.setVisibility(View.VISIBLE);
                        } else {
                            llCheckOut.setVisibility(View.VISIBLE);
                            recyclerView1.setVisibility(View.VISIBLE);
                            cardRecycled.setVisibility(View.GONE);
                            product_add_text2.setVisibility(View.GONE);

                        }

                    });

                } else {
                    final List<RecycleOrder> getallrecOrder = dataManager().loadAllRecycleProductlist("000");
                    //recycleOrderList.addAll(getallrecOrder);


                    handler.post(() -> {
                        //cartGeoRecycleProductlistAdapter.setListAdapter(getallrecOrder);
                        recycleOrderList.clear();
                        recycleOrderList.addAll(getallrecOrder);
                        cartGeoRecycleProductlistAdapter.notifyDataSetChanged();
                        if (getallrecOrder.size() == 0) {
                            llCheckOut.setVisibility(View.GONE);
                            recyclerView1.setVisibility(View.GONE);
                            cardRecycled.setVisibility(View.VISIBLE);
                            product_add_text2.setVisibility(View.VISIBLE);
                        } else {

                            llCheckOut.setVisibility(View.VISIBLE);
                            recyclerView1.setVisibility(View.VISIBLE);
                            cardRecycled.setVisibility(View.GONE);
                            product_add_text2.setVisibility(View.GONE);

                        }

                    });
                }


            }).start();



        }
    }


    @Override
    public void getRecyclePos(final int position) {
        MyOptionPickerViewNew myOptionsPickerViewRecycler = new MyOptionPickerViewNew(mContext);
        myOptionsPickerViewRecycler.show();
        myOptionsPickerViewRecycler.method("Recycler", mContext);
        ArrayList<BottleData> bottlelist = new ArrayList<>();
        ArrayList<WaterNameData> waterlist = new ArrayList<>();
        myOptionsPickerViewRecycler.setPickerRe(bottlelist, recycleBottlelist, waterlist, false);
        myOptionsPickerViewRecycler.setTitle("");
        Typeface typeface = ResourcesCompat.getFont(mContext, R.font.brownregular);
        assert typeface != null;
        myOptionsPickerViewRecycler.setCustomFont(typeface);

        myOptionsPickerViewRecycler.setCancelable(false);
        int positionRecycler = 0;
        myOptionsPickerViewRecycler.setCyclic(false);
        for (int i = 0; i < recycleBottlelist.size(); i++) {
            if (recycleBottlelist.get(i).getRecycle_type().equals(recycleOrderList.get(position).getRecycle_product_watertype())) {
                positionRecycler = i;
                break;
            }
        }

        myOptionsPickerViewRecycler.setSelectOptions(0, positionRecycler, 0);

        myOptionsPickerViewRecycler.setSubmitButtonText("Update");
        myOptionsPickerViewRecycler.setOnoptionsSelectListener((options1, option2, options3) -> {
            bnameRecycler = recycleBottlelist.get(option2).getRecycle_type();
            bottleId = recycleBottlelist.get(option2).getRecycleBottleId();
            priceRecycler = recycleBottlelist.get(option2).getPrice();
            totalPriceRecycler = Float.parseFloat(recycleBottlelist.get(option2).getPrice()) * testCountRecycle;
            new Thread(() -> {
                dataManager().updateRecOrderIdPayment(bnameRecycler, String.valueOf(totalPriceRecycler), String.valueOf(testCountRecycle), String.valueOf(priceRecycler), String.valueOf(recycleOrderList.get(position).getId()), bottleId);
                if (session.isLoggedIn()) {
                    final List<RecycleOrder> getallrecOrder = dataManager().loadAllRecycleProductlist(session.getRegistration().getId());
                    handler.post(() -> {
                        //cartGeoRecycleProductlistAdapter.setListAdapter(getallrecOrder);
                        recycleOrderList.clear();

                        recycleOrderList.addAll(getallrecOrder);
                        cartGeoRecycleProductlistAdapter.notifyDataSetChanged();
                        if (getallrecOrder.size() == 0) {
                            recyclerView1.setVisibility(View.GONE);
                            cardRecycled.setVisibility(View.VISIBLE);
                            product_add_text2.setVisibility(View.VISIBLE);
                        } else {
                            recyclerView1.setVisibility(View.VISIBLE);
                            cardRecycled.setVisibility(View.GONE);
                            product_add_text2.setVisibility(View.GONE);

                        }
                    });

                } else {

                    final List<RecycleOrder> getallrecOrderR = dataManager().loadAllRecycleProductlist("000");
                    handler.post(() -> {
                        recycleOrderList.clear();
                        recycleOrderList.addAll(getallrecOrderR);
                        cartGeoRecycleProductlistAdapter.notifyDataSetChanged();
                        if (getallrecOrderR.size() == 0) {
                            recyclerView1.setVisibility(View.GONE);
                            cardRecycled.setVisibility(View.VISIBLE);
                            product_add_text2.setVisibility(View.VISIBLE);
                        } else {
                            recyclerView1.setVisibility(View.VISIBLE);
                            cardRecycled.setVisibility(View.GONE);
                            product_add_text2.setVisibility(View.GONE);

                        }
                    });


                }


            }).start();

        });

    }

    @Override
    public void getaddpos(int pos) {
        final String id = String.valueOf(recycleOrderList.get(pos).getId());
        final String qty = String.valueOf(recycleOrderList.get(pos).getRecycle_product_qty());
        final String price = String.valueOf(recycleOrderList.get(pos).getBottleprice());
        float fprice = Float.parseFloat(price);

        float p = 0f;
        int fqty = Integer.parseInt(qty);
        if (fqty >= 1) {
            fqty++;
            p = fprice * fqty;
        }

        final int finalFqty = fqty;
        final float finalP = p;
        recycleOrderList.get(pos).setRecycle_product_qty(String.valueOf(fqty));
        recycleOrderList.get(pos).setRecycle_product_price(String.valueOf(finalP));

        new Thread(() -> {
            dataManager().updaterecOrderIdQty(String.valueOf(finalFqty), String.valueOf(finalP), id);
            handler.post(() -> cartGeoRecycleProductlistAdapter.notifyDataSetChanged());
        }).start();
    }

    @Override
    public void getrecdeletepos(final int pos) {
        final String id = String.valueOf(recycleOrderList.get(pos).getId());
        new Thread(() -> {
            dataManager().DeleteRecOrderIdPayment(id);


            handler.post(() -> {
                recycleOrderList.remove(pos);
                cartGeoRecycleProductlistAdapter.notifyItemRemoved(pos);
                if (recycleOrderList.size() == 0) {

                    llCheckOut.setVisibility(View.GONE);
                    recyclerView1.setVisibility(View.GONE);
                    cardRecycled.setVisibility(View.VISIBLE);
                    product_add_text2.setVisibility(View.VISIBLE);
                } else {
                    llCheckOut.setVisibility(View.GONE);
                    recyclerView1.setVisibility(View.VISIBLE);
                    cardRecycled.setVisibility(View.GONE);
                    product_add_text2.setVisibility(View.GONE);

                }
                if (addOrderList.size() > 0) {
                    llCheckOut.setVisibility(View.VISIBLE);
                }


            });


        }).start();

    }

    @Override
    public void getsubpos(int pos) {
        final String id = String.valueOf(recycleOrderList.get(pos).getId());
        final String qty = String.valueOf(recycleOrderList.get(pos).getRecycle_product_qty());
        final String price = String.valueOf(recycleOrderList.get(pos).getBottleprice());
        float fprice = Float.parseFloat(price);

        float p = Float.parseFloat(recycleOrderList.get(pos).getBottleprice());
        int fqty = Integer.parseInt(qty);
        if (fqty > 1) {
            fqty--;
            p = fprice * fqty;
        }

        final int finalFqty = fqty;
        recycleOrderList.get(pos).setRecycle_product_qty(String.valueOf(fqty));
        final float finalP = p;
        recycleOrderList.get(pos).setRecycle_product_price(String.valueOf(finalP));
        new Thread(() -> {
            dataManager().updaterecOrderIdQty(String.valueOf(finalFqty), String.valueOf(finalP), id);
            handler.post(() -> cartGeoRecycleProductlistAdapter.notifyDataSetChanged());
        }).start();
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void getdeleteposition(final int pos) {
        final String id = String.valueOf(addOrderList.get(pos).getId());

        new Thread(() -> {
            dataManager().DeleteOrderIdPayment(id);
            handler.post(() -> {
                addOrderList.remove(pos);
                cartProductlistAdapter.notifyDataSetChanged();
                if (addOrderList.size() == 0) {
                    rlCount.setVisibility(View.GONE);

                    llCheckOut.setVisibility(View.GONE);
                    cardNoData.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                    product_add_text1.setVisibility(View.VISIBLE);
                } else {
                    rlCount.setVisibility(View.VISIBLE);
                    txtCount.setText("" + addOrderList.size());
                    llCheckOut.setVisibility(View.VISIBLE);
                    cardNoData.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                    product_add_text1.setVisibility(View.GONE);
                }
                if (recycleOrderList.size() > 0) {
                    llCheckOut.setVisibility(View.VISIBLE);
                }


            });
        }).start();


    }

    @Override
    public void getaddposition(int pos) {
        final String id = String.valueOf(addOrderList.get(pos).getId());
        final String qty = String.valueOf(addOrderList.get(pos).getNo_bottle());
        final String price = String.valueOf(addOrderList.get(pos).getProduct_water_bottle_price());
        float fprice = Float.parseFloat(price);

        float p = 0f;
        int fqty = Integer.parseInt(qty);
        if (fqty >= 1) {
            fqty++;
            p = fprice * fqty;
        }

        final int finalFqty = fqty;
        addOrderList.get(pos).setNo_bottle(String.valueOf(finalFqty));
        final float finalP = p;
        addOrderList.get(pos).setProduct_price(String.valueOf(finalP));
        new Thread(() -> {
            dataManager().updateOrderIdQty(String.valueOf(finalFqty), String.valueOf(finalP), id);
            handler.post(() -> cartProductlistAdapter.notifyDataSetChanged());
        }).start();

    }

    @Override
    public void getsubposition(int pos) {
        final String id = String.valueOf(addOrderList.get(pos).getId());
        final String qty = String.valueOf(addOrderList.get(pos).getNo_bottle());
        final String price = String.valueOf(addOrderList.get(pos).getProduct_water_bottle_price());
        float fprice = Float.parseFloat(price);

        float p = Float.parseFloat(addOrderList.get(pos).getProduct_water_bottle_price());
        int fqty = Integer.parseInt(qty);
        if (fqty > 1) {
            fqty--;
            p = fprice * fqty;
        }

        final int finalFqty = fqty;
        final float finalP = p;
        addOrderList.get(pos).setProduct_price(String.valueOf(p));
        addOrderList.get(pos).setNo_bottle(String.valueOf(fqty));
        new Thread(() -> {
            dataManager().updateOrderIdQty(String.valueOf(finalFqty), String.valueOf(finalP), id);
            handler.post(() -> cartProductlistAdapter.notifyDataSetChanged());
        }).start();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 101) {
            getProductData();
            setList();

        }
    }
}
