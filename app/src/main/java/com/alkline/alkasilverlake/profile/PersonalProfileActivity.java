package com.alkline.alkasilverlake.profile;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
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
import com.alkline.alkasilverlake.roomdatabasemodel.UserInfoModel;
import com.alkline.alkasilverlake.utils.AddressLocationTask;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.MessageFormat;
import java.util.Calendar;
import java.util.Objects;
import java.util.regex.Pattern;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;

public class PersonalProfileActivity extends BaseActivity implements View.OnClickListener {
    private Handler handler = new Handler(Looper.myLooper());
    private UserInfoModel userInfoModel = new UserInfoModel();
    private LinearLayout llDelivery;
    private ImageView ivDelDown;
    private ImageView imgDevUp;
    private ImageView ivBillUp;
    private ImageView ivBillDown;
    private LinearLayout llBillInfo;
    private TextView txtName;
    private TextView txtBillName;
    private EditText edtBirthdate;
    private EditText edtName;
    private EditText edtPassword;
    private EditText edtEmail;
    private EditText edtMobile;
    private EditText edtLastName;
    private TextView txtDeliveryAddress;
    private TextView txtBillingAddress;
    private Pattern PASSWORD_PATTERN = Pattern.compile("[a-zA-Z0-9!@#$]{8,}");
    private PDialog pDialog = new PDialog();
    private String latitude = "";
    private String longitude = "";
    private String address = "";
    private String city = "";
    private String zipcode = "";
    private String state = "";
    private String json;
    private String jsonBilling;
    private String latitudeBilling = "";
    private String longitudeBilling = "";
    private String addressBilling = "";
    private String zipcodeBilling = "";
    private String stateBilling = "";
    private String name = "";
    private String email = "";
    private String password = "";
    private String mobile = "";
    private String bithdate = "";
    private String lastname = "";
    private Session session;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_profile);
        initView();
    }

    public void initView() {
        edtName = findViewById(R.id.edtName);
        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);
        edtMobile = findViewById(R.id.edtMobile);
        edtBirthdate = findViewById(R.id.edtBirthdate);
        edtLastName = findViewById(R.id.edtLastName);
        RelativeLayout rlDelivery = findViewById(R.id.rlDelivery);
        LinearLayout llBirth = findViewById(R.id.llBirth);
        ImageView imgBack = findViewById(R.id.imgBack);
        RelativeLayout rlBillingInfo = findViewById(R.id.rlBillingInfo);
        txtDeliveryAddress = findViewById(R.id.txtDeliveryAddress);
        txtBillingAddress = findViewById(R.id.txtBillingAddress);
        Button btnUpdateProfile = findViewById(R.id.btnUpdateProfile);
        llDelivery = findViewById(R.id.llDelivery);
        imgDevUp = findViewById(R.id.imgDevUp);
        ivBillUp = findViewById(R.id.ivBillUp);
        ivBillDown = findViewById(R.id.ivBillDown);
        ivDelDown = findViewById(R.id.ivDelDown);
        txtName = findViewById(R.id.txtName);
        txtBillName = findViewById(R.id.txtBillName);
        llBillInfo = findViewById(R.id.llBillInfo);
        rlDelivery.setOnClickListener(this);
        edtBirthdate.setOnClickListener(this);
        txtBillingAddress.setOnClickListener(this);
        rlBillingInfo.setOnClickListener(this);
        llBirth.setOnClickListener(this);
        txtDeliveryAddress.setOnClickListener(this);
        btnUpdateProfile.setOnClickListener(this);
        imgBack.setOnClickListener(this);
        session = new Session(PersonalProfileActivity.this);
        new Thread(() -> {
            userInfoModel = dataManager().userinfo(session.getRegistration().getId());
            name = userInfoModel.getFullName();
            email = userInfoModel.getEmail();
            password = userInfoModel.getPassword();
            latitude = userInfoModel.getDeliveryLatitude();
            longitude = userInfoModel.getDeliveryLongitude();
            mobile = userInfoModel.getContact();
            bithdate = userInfoModel.getBirthDate();
            lastname = userInfoModel.getLastName();
            address = userInfoModel.getDeliveryAddress();
            addressBilling = userInfoModel.getBillingAddress();
            handler.post(() -> {
                edtName.setText(name);
                edtEmail.setText(email);
                edtPassword.setText(password);
                edtMobile.setText(mobile);
                edtBirthdate.setText(bithdate);
                edtLastName.setText(lastname);
                if (address != null && !address.equals("")) {
                    txtDeliveryAddress.setText(address);
                }
                if (addressBilling != null && !addressBilling.equals("")) {
                    txtBillingAddress.setText(addressBilling);
                }


            });

        }).start();


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rlDelivery:
                if (llDelivery.getVisibility() == View.VISIBLE) {
                    llDelivery.setVisibility(View.GONE);
                    ivDelDown.setVisibility(View.GONE);
                    imgDevUp.setVisibility(View.VISIBLE);

                } else {
                    llDelivery.setVisibility(View.VISIBLE);
                    txtName.setText(userInfoModel.getFullName());
                    ivDelDown.setVisibility(View.VISIBLE);
                    imgDevUp.setVisibility(View.GONE);

                }

                break;

            case R.id.rlBillingInfo:
                if (llBillInfo.getVisibility() == View.VISIBLE) {
                    llBillInfo.setVisibility(View.GONE);
                    ivDelDown.setVisibility(View.GONE);
                    ivBillUp.setVisibility(View.VISIBLE);
                    ivBillDown.setVisibility(View.GONE);

                } else {
                    llBillInfo.setVisibility(View.VISIBLE);
                    txtBillName.setText(userInfoModel.getFullName());
                    ivBillDown.setVisibility(View.VISIBLE);
                    ivBillUp.setVisibility(View.GONE);

                }

                break;

            case R.id.llBirth:
                startDate();
                break;

            case R.id.txtDeliveryAddress:
                try {
                    Intent intent = new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN).build(PersonalProfileActivity.this);
                    startActivityForResult(intent, Constant.PLACE_AUTOCOMPLETE_REQUEST_CODE_DELIVERY);
                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }

                break;

            case R.id.txtBillingAddress:
                try {
                    Intent intent = new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN).build(PersonalProfileActivity.this);
                    startActivityForResult(intent, Constant.PLACE_AUTOCOMPLETE_REQUEST_BILLING);
                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }

                break;


            case R.id.btnUpdateProfile:
                if (pDialog.checkInternetConnection(PersonalProfileActivity.this)) {

                    if (checkValidation()) {
                        JSONArray jsonArray = new JSONArray();
                        JSONObject jsonObject = new JSONObject();
                        try {

                            jsonObject.put("d_address", address);
                            jsonObject.put("d_city", city);
                            jsonObject.put("d_state", state);
                            jsonObject.put("d_zip_code", "");
                            jsonObject.put("d_contact_name", edtName.getText().toString().trim());
                            jsonObject.put("d_contact_no", edtMobile.getText().toString().trim());
                            jsonObject.put("d_email", edtEmail.getText().toString());
                            jsonObject.put("d_lattitude", latitude);
                            jsonObject.put("d_longitude", longitude);
                            jsonArray.put(jsonObject);
                            json = jsonArray.toString();


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        JSONArray jsonArrayBilling = new JSONArray();
                        JSONObject jsonObjectBilling = new JSONObject();
                        try {

                            jsonObjectBilling.put("b_address", addressBilling);
                            jsonObjectBilling.put("b_city", city);
                            jsonObjectBilling.put("b_state", state);
                            jsonObjectBilling.put("b_zip_code", "");
                            jsonObjectBilling.put("b_lattitude", latitudeBilling);
                            jsonObjectBilling.put("b_longitude", longitudeBilling);
                            jsonObjectBilling.put("b_contact_no", edtMobile.getText().toString());
                            jsonObjectBilling.put("b_email", edtEmail.getText().toString());
                            jsonObjectBilling.put("b_contact_name", edtName.getText().toString());
                            jsonArrayBilling.put(jsonObjectBilling);
                            jsonBilling = jsonArrayBilling.toString();


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                        updateProfile();

                    }


                } else {
                    Toast.makeText(this, "Please check your internet", Toast.LENGTH_SHORT).show();
                }

                break;

            case R.id.imgBack:
                onBackPressed();
                break;


        }


    }

    public boolean checkValidation() {
        if (TextUtils.isEmpty(edtName.getText().toString().trim())) {
            Toast.makeText(this, "Enter Name", Toast.LENGTH_SHORT).show();
            return false;
        } else if (TextUtils.isEmpty(edtEmail.getText().toString().trim())) {
            Toast.makeText(this, "Enter Email", Toast.LENGTH_SHORT).show();
            return false;
        } else if (TextUtils.isEmpty(edtPassword.getText().toString().trim())) {
            Toast.makeText(this, "Enter Password", Toast.LENGTH_SHORT).show();
            return false;
        } else if (TextUtils.isEmpty(edtMobile.getText().toString().trim())) {
            Toast.makeText(this, "Enter Mobile number", Toast.LENGTH_SHORT).show();
            return false;
        } else if (TextUtils.isEmpty(edtBirthdate.getText().toString().trim())) {
            Toast.makeText(this, "Enter Birth date", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(edtEmail.getText().toString()).matches()) {
            Toast.makeText(this, "Enter valid Email", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!PASSWORD_PATTERN.matcher(edtPassword.getText().toString()).matches()) {
            Toast.makeText(this, "Enter valid Password", Toast.LENGTH_SHORT).show();
            return false;
        }


        return true;
    }

    public void startDate() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(PersonalProfileActivity.this,
                (datePicker, year1, month1, day1) -> {
                    String date = MessageFormat.format("{0}/{1}/{2}", String.valueOf(day1), month1 + 1, year1);
                    if (date.contains(",")) {
                        date = date.replace(",", "");
                        edtBirthdate.setText(date);


                    }
                }, year, month, day);
        //datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
        datePickerDialog.show();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Autocomplete Place Api
        if (requestCode == Constant.PLACE_AUTOCOMPLETE_REQUEST_CODE_DELIVERY) {

            if (resultCode == -1) {
                Place place = PlaceAutocomplete.getPlace(PersonalProfileActivity.this, data);
                getAddress(place);
            }
        } else if (requestCode == Constant.PLACE_AUTOCOMPLETE_REQUEST_BILLING) {
            Place place = PlaceAutocomplete.getPlace(PersonalProfileActivity.this, data);
            getAddressDelivery(place);
        }
    }


    private void getAddress(final Place place) {
        txtDeliveryAddress.setText(place.getAddress());
        if (pDialog.checkInternetConnection(PersonalProfileActivity.this)) {
            new AddressLocationTask(PersonalProfileActivity.this, place, (cty, st, cntry, locAddress) -> {
                city = cty;
                state = st;
                address = Objects.requireNonNull(place.getAddress()).toString();


                if (place.getLatLng() != null) {
                    latitude = "" + place.getLatLng().latitude;
                    longitude = "" + place.getLatLng().longitude;
                }


            }).execute();
        }
    }

    private void getAddressDelivery(final Place place) {
        txtBillingAddress.setText(place.getAddress());
        if (pDialog.checkInternetConnection(PersonalProfileActivity.this)) {
            new AddressLocationTask(PersonalProfileActivity.this, place, (cty, st, cntry, locAddress) -> {
                stateBilling = st;
                addressBilling = Objects.requireNonNull(place.getAddress()).toString();
                if (place.getLatLng() != null) {
                    latitudeBilling = "" + place.getLatLng().latitude;
                    longitudeBilling = "" + place.getLatLng().longitude;
                }


            }).execute();
        }
    }


    public void updateProfile() {
        pDialog.pdialog(this);
        name = edtName.getText().toString().trim();
        lastname = edtLastName.getText().toString().trim();
        email = edtEmail.getText().toString().trim();
        password = edtPassword.getText().toString().trim();
        bithdate = edtBirthdate.getText().toString().trim();
        mobile = edtMobile.getText().toString().trim();
        Session session = new Session(this);
        Call<ResponseBody> call = RetrofitClient.getInstance().getApi()
                .updateProfile(session.getAuthtoken(), name,
                        lastname, email,
                        json, jsonBilling,
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
                            updateProfileinLocalDb();
                            pDialog.alertDialog(msg, PersonalProfileActivity.this);

                        } else {
                            pDialog.alertDialog(msg, PersonalProfileActivity.this);
                        }


                    } else if (response.code() == 400) {

                        @SuppressLint({"NewApi", "LocalSuppress"}) String result = Objects.requireNonNull(response.errorBody()).string();

                        Log.d("response400", result);

                        JSONObject jsonObject = new JSONObject(result);
                        String statusCode = jsonObject.optString("status");
                        String msg = jsonObject.optString("message");
                        if (statusCode.equals("fail")) {
                            pDialog.alertDialog(msg, PersonalProfileActivity.this);

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

    void updateProfileinLocalDb() {
        new Thread(() -> {
            dataManager().updateuserInfo(name, lastname, email, bithdate,
                    mobile, address, city, "", state
                    , zipcode, latitude, longitude, addressBilling, ""
                    , stateBilling, latitudeBilling, zipcodeBilling
                    , longitudeBilling, Integer.parseInt(userInfoModel.getUserId()));
            String id = session.getRegistration().getId();
            UserInfoModel userInfoModeln = dataManager().userinfo(id);
            name = userInfoModeln.getFullName();
            email = userInfoModeln.getEmail();
            password = userInfoModeln.getPassword();
            mobile = userInfoModeln.getContact();
            bithdate = userInfoModeln.getBirthDate();
            lastname = userInfoModeln.getLastName();
            address = userInfoModeln.getDeliveryAddress();
            addressBilling = userInfoModeln.getBillingAddress();
            handler.post(() -> {
                edtName.setText(name);
                edtEmail.setText(email);
                edtPassword.setText(password);
                edtMobile.setText(mobile);
                edtBirthdate.setText(bithdate);
                edtLastName.setText(lastname);
                if (address != null && !address.equals("")) {
                    txtDeliveryAddress.setText(address);
                }
                if (addressBilling != null && !addressBilling.equals("")) {
                    txtBillingAddress.setText(addressBilling);
                }


            });
        }).start();
    }


}
