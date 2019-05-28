package com.alkline.alkasilverlake.activity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alkline.alkasilverlake.Constant;
import com.alkline.alkasilverlake.R;
import com.alkline.alkasilverlake.Session;
import com.alkline.alkasilverlake.base.BaseActivity;
import com.alkline.alkasilverlake.connection.RetrofitClient;
import com.alkline.alkasilverlake.helper.PDialog;
import com.alkline.alkasilverlake.roomdatabasemodel.AddOrder;
import com.alkline.alkasilverlake.roomdatabasemodel.RecycleOrder;
import com.alkline.alkasilverlake.roomdatabasemodel.UserInfoModel;
import com.alkline.alkasilverlake.utils.AddressLocationTask;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;

public class CheckOutActivity extends BaseActivity implements View.OnClickListener {
    private LinearLayout llDelinfo;
    private LinearLayout llBillInfo;
    private ImageView imgDevUp;
    private ImageView ivWeekly;
    private EditText edtBillingName;
    private EditText edName;
    private ImageView ivBiWeekly;
    private ImageView ivMonthly;
    private ImageView ivIndefinite;
    private ImageView ivBiMonthly;
    private ImageView ivAutoDelivery;
    private ImageView ivSame;
    private int dayCal = 0, monthCal = 0, yearCal = 0;
    int yearEnd, monthEnd, dayEnd;
    private TextView txtBillingAddress;
    private ImageView imgbillingUp;
    private Session session;
    private Handler handler = new Handler(Looper.myLooper());
    private String name = "";
    private String city = "";
    private String country = "";
    private String state = "";
    private String address = "";
    private String addressBilling = "";
    private String stateBilling = "";
    private String countryBilling = "";
    private LinearLayout llAutoDeliveryOptions;
    private boolean isSelect = true;
    private boolean isSelectSame = true;
    private boolean isByWeekly = true;
    private boolean isWeekly = true;
    private boolean isMonthly = true;
    private boolean isIndefinite = true;
    private boolean isByMonthly = true;
    private PDialog pDialog = new PDialog();
    private String latitude;
    private String longitude;
    String latitudeBilling;
    String longitudeBilling;
    private ImageView ivStartDate;
    private ImageView ivEndDate;
    private TextView txtAddress;
    private TextView txtStartDate;
    private TextView txtEndDate;
    String schedule_type = "1";
    String isAutodelivery = "0";
    String startDate = "";
    private String addressJson = "";
    String endDate = "";
    private String grand_total = "";
    private String isCheckedAutoDelivery = "0";
    private String isIndefiniteI = "0";
    private UserInfoModel userInfoModel;
    private JSONArray productArray, recycle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_out);
        initView();
    }

    private void initView() {
        imgDevUp = findViewById(R.id.imgDevUp);
        txtStartDate = findViewById(R.id.txtStartDate);
        txtEndDate = findViewById(R.id.txtEndDate);
        ivStartDate = findViewById(R.id.ivStartDate);
        ivIndefinite = findViewById(R.id.ivIndefinite);
        ivEndDate = findViewById(R.id.ivEndDate);
        ivSame = findViewById(R.id.ivSame);
        imgbillingUp = findViewById(R.id.imgbillingUp);
        edtBillingName = findViewById(R.id.edtBillingName);
        ivWeekly = findViewById(R.id.ivWeekly);
        ivMonthly = findViewById(R.id.ivMonthly);
        ivBiWeekly = findViewById(R.id.ivBiWeekly);
        ivBiMonthly = findViewById(R.id.ivBiMonthly);
        ivAutoDelivery = findViewById(R.id.ivAutoDelivery);
        llAutoDeliveryOptions = findViewById(R.id.llAutoDeliveryOptions);
        llBillInfo = findViewById(R.id.llBillInfo);
        edName = findViewById(R.id.edName);
        txtAddress = findViewById(R.id.txtAddress);
        txtBillingAddress = findViewById(R.id.txtBillingAddress);
        TextView tvBillingInfo = findViewById(R.id.tvBillingInfo);
        ImageView ivBack = findViewById(R.id.ivBack);
        LinearLayout llWeekly = findViewById(R.id.llWeekly);
        LinearLayout llCheckOut = findViewById(R.id.llCheckOut);
        LinearLayout llSameAs = findViewById(R.id.llSameAs);
        LinearLayout llBiWeekly = findViewById(R.id.llBiWeekly);
        LinearLayout llMonthly = findViewById(R.id.llMonthly);
        LinearLayout llBiMonthly = findViewById(R.id.llBiMonthlyy);
        RelativeLayout rlAutoDelivery = findViewById(R.id.rlAutoDelivery);
        llWeekly.setOnClickListener(this);
        llMonthly.setOnClickListener(this);
        llBiMonthly.setOnClickListener(this);
        llCheckOut.setOnClickListener(this);
        llSameAs.setOnClickListener(this);
        llBiWeekly.setOnClickListener(this);
        ivBack.setOnClickListener(this);
        rlAutoDelivery.setOnClickListener(this);
        ivAutoDelivery.setOnClickListener(this);
        ivIndefinite.setOnClickListener(this);
        txtAddress.setOnClickListener(this);
        txtBillingAddress.setOnClickListener(this);
        ivStartDate.setOnClickListener(this);
        ivEndDate.setOnClickListener(this);
        llDelinfo = findViewById(R.id.llDelinfo);
        imgDevUp.setOnClickListener(this);
        tvBillingInfo.setOnClickListener(this);
        ivSame.setOnClickListener(this);
        imgbillingUp.setOnClickListener(this);
        if (getIntent() != null) {
            grand_total = getIntent().getStringExtra("grand_total");
        }
        session = new Session(CheckOutActivity.this);
        new Thread(() -> {
            userInfoModel = dataManager().userinfo(session.getRegistration().getId());
            name = userInfoModel.getFullName();
            stateBilling = userInfoModel.getBillingState();
            countryBilling = userInfoModel.getBillingCountry();
            address = userInfoModel.getDeliveryAddress();
            city = userInfoModel.getDeliveryCity();
            state = userInfoModel.getDeliveryState();
            latitudeBilling = userInfoModel.getBillingLatitude();
            longitudeBilling = userInfoModel.getBillingLongitude();
            latitude = userInfoModel.getDeliveryLatitude();
            longitude = userInfoModel.getDeliveryLongitude();
            addressBilling = userInfoModel.getBillingAddress();
            handler.post(() -> {
                edName.setText(name);
                edtBillingName.setText(name);

                if (address != null && !address.equals("")) {
                    txtAddress.setText(address);

                } else {
                    txtAddress.setText("Delivery Address");
                }
                if (addressBilling != null && !addressBilling.equals("")) {
                    txtBillingAddress.setText(addressBilling);
                } else {
                    txtBillingAddress.setText("Billing Address");
                }


            });

        }).start();

    }


    @Override
    public void onClick(View v) {
        ivIndefinite.setImageResource(R.drawable.circlewhite);
        switch (v.getId()) {
            case R.id.imgDevUp:
                if (llDelinfo.getVisibility() == View.VISIBLE) {
                    imgDevUp.setImageResource(R.drawable.ic_up_arrow);
                    llDelinfo.setVisibility(View.GONE);

                } else {
                    imgDevUp.setImageResource(R.drawable.ic_down_arrow);
                    llDelinfo.setVisibility(View.VISIBLE);
                }


                break;

            case R.id.imgbillingUp:
                if (llBillInfo.getVisibility() == View.VISIBLE) {
                    imgbillingUp.setImageResource(R.drawable.ic_up_arrow);
                    llBillInfo.setVisibility(View.GONE);

                } else {
                    imgbillingUp.setImageResource(R.drawable.ic_down_arrow);
                    llBillInfo.setVisibility(View.VISIBLE);

                }

                break;

            case R.id.ivSame:
                if (isSelect) {
                    ivSame.setImageResource(R.drawable.active_check_ico);
                    edtBillingName.setText(edName.getText().toString().trim());
                    txtBillingAddress.setText(txtAddress.getText().toString());
                    latitudeBilling = latitude;
                    longitudeBilling = longitude;

                    isSelect = false;
                } else {
                    isSelect = true;
                    ivSame.setImageResource(R.drawable.circlewhite);
                }


                break;

            case R.id.rlAutoDelivery:
                showPopupBillingInfo();
                break;

            case R.id.ivAutoDelivery:
                showPopupBillingInfo();
              /*  if (isSelectAuto) {
                    ivAutoDelivery.setImageResource(R.drawable.active_check_ico);
                    llAutoDeliveryOptions.setVisibility(View.VISIBLE);
                    isAutodelivery = "1";
                    isSelectAuto = false;
                } else {
                    isSelectAuto = true;
                    ivAutoDelivery.setImageResource(R.drawable.circlewhite);
                    llAutoDeliveryOptions.setVisibility(View.GONE);
                }*/

                break;

            case R.id.llBiWeekly:
                if (isByWeekly) {
                    ivBiWeekly.setImageResource(R.drawable.active_check_ico);
                    ivMonthly.setImageResource(R.drawable.circlewhite);
                    Toast.makeText(this, "Under Development", Toast.LENGTH_SHORT).show();
                    ivWeekly.setImageResource(R.drawable.circlewhite);
                    isCheckedAutoDelivery = "1";
                    ivBiMonthly.setImageResource(R.drawable.circlewhite);
                    isByWeekly = false;
                } else {
                    isCheckedAutoDelivery = "0";
                    ivBiWeekly.setImageResource(R.drawable.circlewhite);
                    ivMonthly.setImageResource(R.drawable.circlewhite);
                    ivWeekly.setImageResource(R.drawable.circlewhite);
                    ivBiMonthly.setImageResource(R.drawable.circlewhite);
                    isByWeekly = true;
                }
                break;

            case R.id.llBiMonthlyy:
                if (isByMonthly) {

                    ivBiMonthly.setImageResource(R.drawable.circlewhite);
                    ivMonthly.setImageResource(R.drawable.circlewhite);
                    schedule_type = "3";
                    isCheckedAutoDelivery = "1";
                    ivWeekly.setImageResource(R.drawable.circlewhite);
                    ivBiMonthly.setImageResource(R.drawable.active_check_ico);
                    isByMonthly = false;
                } else {
                    isCheckedAutoDelivery = "0";
                    ivBiWeekly.setImageResource(R.drawable.circlewhite);
                    ivMonthly.setImageResource(R.drawable.circlewhite);
                    ivWeekly.setImageResource(R.drawable.circlewhite);
                    ivBiMonthly.setImageResource(R.drawable.circlewhite);
                    isByMonthly = true;

                }
                break;

            case R.id.llMonthly:
                if (isMonthly) {

                    ivBiWeekly.setImageResource(R.drawable.circlewhite);
                    ivMonthly.setImageResource(R.drawable.active_check_ico);
                    schedule_type = "4";
                    isCheckedAutoDelivery = "1";
                    ivWeekly.setImageResource(R.drawable.circlewhite);
                    ivBiMonthly.setImageResource(R.drawable.circlewhite);
                    isMonthly = false;
                } else {
                    isCheckedAutoDelivery = "0";
                    ivBiWeekly.setImageResource(R.drawable.circlewhite);
                    ivMonthly.setImageResource(R.drawable.circlewhite);
                    ivWeekly.setImageResource(R.drawable.circlewhite);
                    ivBiMonthly.setImageResource(R.drawable.circlewhite);
                    isMonthly = true;
                }
                break;

            case R.id.llWeekly:
                if (isWeekly) {

                    ivBiWeekly.setImageResource(R.drawable.circlewhite);
                    ivMonthly.setImageResource(R.drawable.circlewhite);
                    isCheckedAutoDelivery = "1";
                    ivWeekly.setImageResource(R.drawable.active_check_ico);
                    ivBiMonthly.setImageResource(R.drawable.circlewhite);
                    isWeekly = false;
                } else {
                    isCheckedAutoDelivery = "0";
                    ivBiWeekly.setImageResource(R.drawable.circlewhite);
                    ivMonthly.setImageResource(R.drawable.circlewhite);
                    ivWeekly.setImageResource(R.drawable.circlewhite);
                    ivBiMonthly.setImageResource(R.drawable.circlewhite);
                    isWeekly = true;
                }
                break;

            case R.id.txtBillingAddress:
                try {
                    Intent intent = new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN).build(CheckOutActivity.this);
                    startActivityForResult(intent, Constant.PLACE_AUTOCOMPLETE_REQUEST_BILLING);
                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }

                break;

            case R.id.txtAddress:

                try {
                    Intent intent = new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN).build(CheckOutActivity.this);
                    startActivityForResult(intent, Constant.PLACE_AUTOCOMPLETE_REQUEST_CODE_DELIVERY);
                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }

                break;

            case R.id.llSameAs:
                if (isSelectSame) {
                    ivSame.setImageResource(R.drawable.active_check_ico);
                    edtBillingName.setText(edName.getText().toString().trim());
                    txtBillingAddress.setText(txtAddress.getText().toString());
                    latitudeBilling = latitude;
                    longitudeBilling = longitude;

                    isSelectSame = false;
                } else {
                    isSelectSame = true;
                    ivSame.setImageResource(R.drawable.circlewhite);
                }

                break;

            case R.id.ivEndDate:
                if (dayCal != 0 && monthCal != 0 && yearCal != 0) {
                    endDatePicker();
                    //CheckDates(startDate,endDate);

                } else {
                    Toast.makeText(this, "Please Select Start Date", Toast.LENGTH_SHORT).show();

                }


                break;

            case R.id.ivStartDate:
                startDatePicker();
                ivStartDate.setImageResource(R.drawable.active_calender_ico);
                break;

            case R.id.llCheckOut:
                if (checkValidation()) {
                    new Thread(() -> {
                        List<AddOrder> getallOrder = dataManager().loadAllProductlist(session.getRegistration().getId());
                        List<RecycleOrder> getallrecOrder = dataManager().loadAllRecycleProductlist(session.getRegistration().getId());
                        dataManager().updateDeliveryAddress(address, country, state, latitude, longitude, Integer.parseInt(session.getRegistration().getId()));
                        dataManager().updateBillingAddress(addressBilling, countryBilling, stateBilling, latitudeBilling, longitudeBilling, Integer.parseInt(session.getRegistration().getId()));
                        productArray = new JSONArray();
                        for (AddOrder addOrder : getallOrder) {
                            JSONObject obj = new JSONObject();
                            try {
                                obj.put("water_id", addOrder.getWater_id());
                                obj.put("bottle_id", addOrder.getBottle_id());
                                obj.put("bottleCondition", addOrder.getBottlecon());
                                obj.put("product_price", addOrder.getProduct_price());
                                obj.put("quantity", addOrder.getNo_bottle());
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            productArray.put(obj);
                        }

                        recycle = new JSONArray();
                        for (RecycleOrder recycleOrder : getallrecOrder) {
                            JSONObject obj = new JSONObject();
                            try {
                                obj.put("recycle_id", recycleOrder.getRecycleBottleId());
                                obj.put("price", recycleOrder.getRecycle_product_price());
                                obj.put("quantity", recycleOrder.getRecycle_product_qty());

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            recycle.put(obj);
                        }

                        JSONArray jsonArray = new JSONArray();
                        JSONObject jsonObject = new JSONObject();

                        try {

                            jsonObject.put("d_address", address);
                            jsonObject.put("d_city", city);
                            jsonObject.put("d_state", state);
                            jsonObject.put("d_zip_code", "");
                            jsonObject.put("d_contact_name", edName.getText().toString().trim());
                            jsonObject.put("d_contact_no", "");
                            jsonObject.put("d_email", "");
                            jsonObject.put("d_lattitude", latitude);
                            jsonObject.put("d_longitude", longitude);
                            jsonArray.put(jsonObject);
                            addressJson = jsonArray.toString();


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        handler.post(this::updateProfile);


                    }).start();


                }

                break;

            case R.id.ivIndefinite:
                if (isIndefinite) {
                    ivIndefinite.setImageResource(R.drawable.active_check_ico);
                    isIndefinite = false;
                    isIndefiniteI = "1";
                } else {
                    isIndefiniteI = "0";
                    ivIndefinite.setImageResource(R.drawable.circlewhite);
                    isIndefinite = true;

                }

                break;

            case R.id.ivBack:
                onBackPressed();
                break;


        }
    }

    private boolean checkValidation() {

        if (edName.getText().toString().equals("Name")) {
            Toast.makeText(this, "Please add delivery person name", Toast.LENGTH_SHORT).show();
            return false;
        } else if (txtAddress.getText().equals("Delivery Address")) {
            Toast.makeText(this, "Please add delivery address", Toast.LENGTH_SHORT).show();
            return false;
        } else if (edtBillingName.getText().toString().equals("Name")) {
            Toast.makeText(this, "Please add billing person name", Toast.LENGTH_SHORT).show();
            return false;
        } else if ((txtBillingAddress.getText().equals("Billing Address"))) {
            Toast.makeText(this, "Please add billing address", Toast.LENGTH_SHORT).show();
            return false;
        } else if (ivAutoDelivery.getDrawable().getConstantState() == getResources().getDrawable(R.drawable.active_check_ico).getConstantState()) {

            if (ivWeekly.getDrawable().getConstantState() == getResources().getDrawable(R.drawable.circlewhite).getConstantState()
                    && ivBiWeekly.getDrawable().getConstantState() == getResources().getDrawable(R.drawable.circlewhite).getConstantState()
                    && ivMonthly.getDrawable().getConstantState() == getResources().getDrawable(R.drawable.circlewhite).getConstantState()
                    && ivBiMonthly.getDrawable().getConstantState() == getResources().getDrawable(R.drawable.circlewhite).getConstantState() && startDate.equals("") && endDate.equals("")) {

                Toast.makeText(this, "Please select delivery frequency. :- on auto delivery", Toast.LENGTH_SHORT).show();
                return false;
            } else if (startDate.isEmpty() && endDate.isEmpty()
                    && ivIndefinite.getDrawable().getConstantState() == getResources().getDrawable(R.drawable.circlewhite).getConstantState()) {
                Toast.makeText(this, "Please select end on OR indefinite", Toast.LENGTH_SHORT).show();
                return false;
            } else if (isCheckedAutoDelivery.equals("1")) {

                if (startDate.isEmpty() && endDate.isEmpty() && isIndefiniteI.equals("0")) {
                    Toast.makeText(this, "Please Select Start date and End date ", Toast.LENGTH_SHORT).show();
                    return false;
                }

            } else if (!startDate.equals("") && !endDate.equals("")
                    || ivIndefinite.getDrawable().getConstantState() == getResources().getDrawable(R.drawable.circlewhite)
                    .getConstantState() || ivBiWeekly.getDrawable().getConstantState() == getResources().getDrawable(R.drawable.circlewhite).getConstantState()
                    || ivMonthly.getDrawable().getConstantState() == getResources().getDrawable(R.drawable.circlewhite).getConstantState()
                    || ivBiMonthly.getDrawable() == getResources().getDrawable(R.drawable.circlewhite)
            ) {
                Toast.makeText(this, "Please select delivery frequency”. :- on auto delivery", Toast.LENGTH_SHORT).show();
                return false;


            }
        }

        return true;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Autocomplete Place Api
        if (requestCode == Constant.PLACE_AUTOCOMPLETE_REQUEST_CODE_DELIVERY) {

            if (resultCode == -1) {
                Place place = PlaceAutocomplete.getPlace(CheckOutActivity.this, data);
                getAddress(place);
            }
        } else if (requestCode == Constant.PLACE_AUTOCOMPLETE_REQUEST_BILLING) {
            Place place = PlaceAutocomplete.getPlace(CheckOutActivity.this, data);
            getAddressDelivery(place);
        }
    }

    private void getAddress(final Place place) {
        txtAddress.setText(place.getAddress());
        if (pDialog.checkInternetConnection(CheckOutActivity.this)) {
            new AddressLocationTask(CheckOutActivity.this, place, (cty, st, cntry, locAddress) -> {
               /*String city = cty;
               String state = st;*/
                address = Objects.requireNonNull(place.getAddress()).toString();
                country = cntry;
                city = cty;
                if (place.getLatLng() != null) {
                    latitude = "" + place.getLatLng().latitude;
                    longitude = "" + place.getLatLng().longitude;
                }


            }).execute();
        }
    }

    private void getAddressDelivery(final Place place) {
        txtBillingAddress.setText(place.getAddress());
        if (pDialog.checkInternetConnection(CheckOutActivity.this)) {
            new AddressLocationTask(CheckOutActivity.this, place, (cty, st, cntry, locAddress) -> {
                stateBilling = st;
                countryBilling = cntry;
                addressBilling = Objects.requireNonNull(place.getAddress()).toString();
                if (place.getLatLng() != null) {
                    latitudeBilling = "" + place.getLatLng().latitude;
                    longitudeBilling = "" + place.getLatLng().longitude;
                }


            }).execute();
        }
    }


    private void showPopupBillingInfo() {
        final Dialog dialog = new Dialog(CheckOutActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.dialog_billing_info);
        Window window = dialog.getWindow();
        Button btnOrderNow = dialog.findViewById(R.id.btnOrderNow);
        btnOrderNow.setOnClickListener(view -> {
            dialog.dismiss();
            ivAutoDelivery.setImageResource(R.drawable.active_check_ico);
            llAutoDeliveryOptions.setVisibility(View.VISIBLE);
            isAutodelivery = "1";
        });

        TextView txtNotNow = dialog.findViewById(R.id.txtNotNow);
        txtNotNow.setOnClickListener(view -> dialog.dismiss());

        assert window != null;
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.show();


    }


    public void startDatePicker() {
        Calendar calendar = Calendar.getInstance();
        yearCal = calendar.get(Calendar.YEAR);
        monthCal = calendar.get(Calendar.MONTH);
        dayCal = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(CheckOutActivity.this, (datePicker, yearCal, monthCal, dayCal) -> {
            txtStartDate.setTextColor(getResources().getColor(R.color.appColor));
            txtStartDate.setText(dayCal + "/" + (monthCal + 1) + "/" + yearCal);
            startDate = dayCal + "/" + monthCal + "/" + yearCal;
        }, yearCal, monthCal, dayCal);


        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
        datePickerDialog.show();
    }


    public void endDatePicker() {
        Calendar calendar = Calendar.getInstance();
        yearEnd = calendar.get(Calendar.YEAR);
        monthEnd = calendar.get(Calendar.MONTH);
        dayEnd = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(CheckOutActivity.this,
                (datePicker, yearEnd, monthEnd, dayEnd) -> {
                    endDate = dayEnd + "/" + monthEnd + "/" + yearEnd;
                    if (Date.parse(startDate) >= Date.parse(endDate)) {
                        txtEndDate.setText("End On");
                        txtEndDate.setTextColor(getResources().getColor(R.color.darkGrayColor));
                        ivEndDate.setImageResource(R.drawable.inactive_calender_ico);
                        Toast.makeText(this, "Start date and end date for a order must be equal or Start date must be smaller than the end date", Toast.LENGTH_SHORT).show();
                    } else {
                        txtEndDate.setTextColor(getResources().getColor(R.color.appColor));
                        ivEndDate.setImageResource(R.drawable.active_calender_ico);
                        txtEndDate.setText(dayEnd + "/" + (monthEnd + 1) + "/" + yearEnd);

                    }
                }, yearEnd, monthEnd, dayEnd);


        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
        datePickerDialog.show();
    }

    public void updateProfile() {
        pDialog.pdialog(this);
        name = edName.getText().toString().trim();
        String lastname = userInfoModel.getLastName();
        String email = userInfoModel.getEmail();
        String password = userInfoModel.getPassword();
        String bithdate = userInfoModel.getBirthDate();
        String mobile = userInfoModel.getContact();
        Session session = new Session(this);
        Call<ResponseBody> call = RetrofitClient.getInstance().getApi()
                .updateProfile(session.getAuthtoken(), name,
                        lastname, email,
                        addressJson, addressBilling,
                        mobile,
                        password,
                        bithdate);
        call.enqueue(new Callback<ResponseBody>() {
            @SuppressLint("NewApi")
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull retrofit2.Response<ResponseBody> response) {

                try {

                    pDialog.hideDialog();

                    if (response.code() == 200) {
                        String stresult = Objects.requireNonNull(response.body()).string();
                        Log.d("response", stresult);
                        JSONObject jsonObject = new JSONObject(stresult);
                        String statusCode = jsonObject.optString("status");
                        String msg = jsonObject.optString("message");
                        if (statusCode.equals("success")) {
                            if (session.isLoggedIn()) {
                                Intent intent = new Intent(CheckOutActivity.this, PaymentAndAddCardActivity.class);
                                intent.putExtra("auto_pay", isAutodelivery);
                                intent.putExtra("schedule_type", schedule_type);
                                intent.putExtra("start_date", startDate);
                                intent.putExtra("end_not_define", isIndefiniteI);
                                intent.putExtra("address", addressJson);
                                intent.putExtra("grand_total", grand_total);
                                intent.putExtra("product", productArray.toString());
                                intent.putExtra("recycle", recycle.toString());
                                intent.putExtra("end_date", endDate);
                                intent.putExtra("autherized", "1");
                                intent.putExtra("delivery_distance", session.getDistance());
                                intent.putExtra("distance_charge", session.getDeliveryFee());
                                intent.putExtra("product_charge", session.getMinCharge());
                                startActivity(intent);

                            } else {
                                Toast.makeText(CheckOutActivity.this, "Please Login first", Toast.LENGTH_SHORT).show();
                            }


                        } else {
                            pDialog.alertDialog(msg, CheckOutActivity.this);
                        }


                    } else if (response.code() == 400) {

                        @SuppressLint({"NewApi", "LocalSuppress"}) String result = Objects.requireNonNull(response.errorBody()).string();
                        Log.d("response400", result);
                        JSONObject jsonObject = new JSONObject(result);
                        int responseCode = jsonObject.optInt("responseCode");
                        String msg = jsonObject.optString("message");
                        if (responseCode == 300) {
                            pDialog.alertDialog(msg, CheckOutActivity.this);

                        }

                    } else if (response.code() == 401) {

                        try {
                            assert response.errorBody() != null;
                            pDialog.hideDialog();
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


}
