package com.alkline.alkasilverlake.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alkline.alkasilverlake.R;
import com.alkline.alkasilverlake.Session;
import com.alkline.alkasilverlake.base.BaseActivity;
import com.alkline.alkasilverlake.connection.RetrofitClient;
import com.alkline.alkasilverlake.helper.PDialog;
import com.alkline.alkasilverlake.responsebean.UserInfoBean;
import com.alkline.alkasilverlake.roomdatabasemodel.AddOrder;
import com.alkline.alkasilverlake.roomdatabasemodel.RecycleOrder;
import com.alkline.alkasilverlake.roomdatabasemodel.UserInfoModel;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.List;
import java.util.Objects;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;

public class SigninActivity extends BaseActivity implements View.OnClickListener {

    private EditText logemail, logpassword;
    private Button btnsign;
    private TextView tvaccount;
    private TextInputLayout passwordLayout, emailLayout;
    PDialog pDialog;
    private Handler handler=new Handler(Looper.myLooper());
    private String refreshedToken;

    private ImageView log_cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        //this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        pDialog = new PDialog();
        refreshedToken = FirebaseInstanceId.getInstance().getToken();
        init();
    }

    private TextWatcher textWatcher_email = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {

            if (editable == logemail.getEditableText() && editable.length() >= 1) {
                emailLayout.setHint(getResources().getString(R.string.email));
                emailLayout.setDefaultHintTextColor(ColorStateList.valueOf(getResources().getColor(R.color.gray_light_new)));
                if (!android.util.Patterns.EMAIL_ADDRESS.matcher(logemail.getText().toString()).matches()) {
                    emailLayout.setHint(getResources().getString(R.string.emailInvalidError));
                    emailLayout.setDefaultHintTextColor(ColorStateList.valueOf(getResources().getColor(R.color.red)));

                }
            } else {
                emailLayout.setHint(getResources().getString(R.string.emailerror));
                emailLayout.setDefaultHintTextColor(ColorStateList.valueOf(getResources().getColor(R.color.red)));

            }
        }
    };

    public void setclick() {
        btnsign.setOnClickListener(this);
        tvaccount.setOnClickListener(this);
        log_cancel.setOnClickListener(this);


    }

    public void init() {
        logemail = findViewById(R.id.signin_email);
        logpassword = findViewById(R.id.signin_password);
        logpassword.setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
        logpassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
        btnsign = findViewById(R.id.btsignin);
        TextView txtForgetPassword=findViewById(R.id.txtForgetPassword);
        txtForgetPassword.setOnClickListener(this);
        emailLayout = findViewById(R.id.signin_lay_email);
        passwordLayout = findViewById(R.id.signin_lay_Password);
        tvaccount = findViewById(R.id.signin_acc);
        log_cancel = findViewById(R.id.log_cancel);
        logemail.addTextChangedListener(textWatcher_email);
        logpassword.addTextChangedListener(textWatcher_pass);
        emailLayout.setDefaultHintTextColor(ColorStateList.valueOf(getResources().getColor(R.color.gray_light_new)));
        passwordLayout.setDefaultHintTextColor(ColorStateList.valueOf(getResources().getColor(R.color.gray_light_new)));


        setclick();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.signin_acc: {
                Intent intent = new Intent(SigninActivity.this, NewAccActivity.class);
                startActivity(intent);

            }
                break;
                case R.id.log_cancel: {
                   backPress();
                }
                break;

            case R.id.btsignin:

                boolean flag = true;
                if (pDialog.checkInternetConnection(SigninActivity.this)) {

                    if (logemail.getText().toString().length() == 0) {
                        flag = false;
                        emailLayout.setHint(getResources().getString(R.string.emailerror));
                        emailLayout.setDefaultHintTextColor(ColorStateList.valueOf(getResources().getColor(R.color.red)));

                    } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(logemail.getText().toString()).matches()) {
                        flag = false;
                        emailLayout.setHint(getResources().getString(R.string.emailInvalidError));
                        emailLayout.setDefaultHintTextColor(ColorStateList.valueOf(getResources().getColor(R.color.red)));
                    } else {
                        emailLayout.setHint(getResources().getString(R.string.email));
                        emailLayout.setDefaultHintTextColor(ColorStateList.valueOf(getResources().getColor(R.color.gray_light_new)));
                    }
                    if (logpassword.getText().toString().length() == 0) {
                        flag = false;
                        passwordLayout.setHint(getResources().getString(R.string.passworderror));
                        passwordLayout.setDefaultHintTextColor(ColorStateList.valueOf(getResources().getColor(R.color.red)));

                    }  else {
                        passwordLayout.setHint(getResources().getString(R.string.password));
                        passwordLayout.setDefaultHintTextColor(ColorStateList.valueOf(getResources().getColor(R.color.gray_light_new)));
                    }

                    if (flag)
                    {
                        LoginUser();
                    }
                } else {
                    Toast.makeText(SigninActivity.this, "Network not available", Toast.LENGTH_LONG).show();

                }
                break;

            case R.id.txtForgetPassword:
                Intent intent=new Intent(SigninActivity.this,ForgetPasswordActivity.class);
                startActivity(intent);
                break;


        }
    }

    public void LoginUser() {


        pDialog.pdialog(this);
        Call<ResponseBody> call = RetrofitClient.getInstance().getApi()
                .login(logemail.getText().toString(), logpassword.getText().toString(), refreshedToken);

        call.enqueue(new Callback<ResponseBody>() {


            @SuppressLint("NewApi")
            @Override

            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull retrofit2.Response<ResponseBody> response) {

                try {

                    pDialog.hideDialog();

                    if (response.code() == 200) {

                        @SuppressLint({"NewApi", "LocalSuppress"}) String stresult = Objects.requireNonNull(response.body()).string();
                        Log.i("eail4254", "" + stresult);
                        JSONObject jsonObject = new JSONObject(stresult);
                        String statusCode = jsonObject.optString("status");
                        String msg = jsonObject.optString("message");
                        if (statusCode.equals("success")) {
                            Session session = new Session(SigninActivity.this);
                            session.issetLoggedIn(true);
                            JSONObject jsonObject1 = jsonObject.getJSONObject("userDetail");
                            Gson gson = new Gson();
                            UserInfoBean.UserDetailBean registrationInfo = gson.fromJson(String.valueOf(jsonObject1), UserInfoBean.UserDetailBean.class);
                            registrationInfo.setPassword(logpassword.getText().toString().trim());
                            session.createRegistration(registrationInfo);
                            String id = jsonObject1.getString("id");
                            session.putuserid(id);
                            String authToken = jsonObject1.getString("authToken");
                            session.putAuthtoken(authToken);
                            insertGuestToUser(id, registrationInfo);



                        } else {
                            Toast.makeText(SigninActivity.this, msg, Toast.LENGTH_SHORT).show();

                        }

                    } else if (response.code() == 400) {

                        @SuppressLint({"NewApi", "LocalSuppress"})
                        String result = Objects.requireNonNull(response.errorBody()).string();

                        Log.d("response400", result);
                        JSONObject jsonObject = new JSONObject(result);
                        String statusCode = jsonObject.optString("status");
                        String msg = jsonObject.optString("message");
                        if (statusCode.equals("fail")) {
                            Toast.makeText(SigninActivity.this, msg, Toast.LENGTH_SHORT).show();
                            }

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

    private TextWatcher textWatcher_pass = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            if (editable == logpassword.getEditableText() && editable.length() >= 1) {
                passwordLayout.setHint(getResources().getString(R.string.password));
                passwordLayout.setDefaultHintTextColor(ColorStateList.valueOf(getResources().getColor(R.color.darkGrayColor)));
                }

             else {
                passwordLayout.setHint(getResources().getString(R.string.passworderror));
                passwordLayout.setDefaultHintTextColor(ColorStateList.valueOf(getResources().getColor(R.color.red)));

            }


    }

    };

    void insertGuestToUser(final String userId, final UserInfoBean.UserDetailBean userInfoBean) {
        new Thread(() -> {
            List<AddOrder> getallOrder = dataManager().loadAllProductlist("000");
            if (getallOrder.size() > 0) {
                for (AddOrder addorder : getallOrder) {
                    final AddOrder addOrder = new AddOrder();
                    addOrder.setId(0);
                    addOrder.setBottletype(addorder.getBottletype());
                    addOrder.setWatertype(addorder.getWatertype());
                    addOrder.setBottlecon(addorder.getBottlecon());
                    addOrder.setNo_bottle(addorder.getNo_bottle());
                    addOrder.setUser_id(userId);
                    addOrder.setBottlepricenew(addorder.getBottlepricenew());
                    addOrder.setBottlepriceold(addorder.getBottlepriceold());
                    addOrder.setWater_id(addorder.getWater_id());
                    addOrder.setBottle_id(addorder.getBottle_id());
                    addOrder.setProduct_water_bottle_price(addorder.getProduct_water_bottle_price());
                    addOrder.setProduct_price(addorder.getProduct_price());
                    dataManager().productOrder(addOrder);


                }

                dataManager().deleteAddOrderItem("000");
            }

            List<RecycleOrder> getallrecOrder = dataManager().loadAllRecycleProductlist("000");
            if (getallrecOrder.size() > 0) {
                for (RecycleOrder recycleOrder : getallrecOrder) {
                    final RecycleOrder recycleOrderI = new RecycleOrder();
                    recycleOrderI.setId(recycleOrder.getId());
                    recycleOrderI.setRecycle_user_id(userId);
                    recycleOrderI.setRecycle_product_watertype(recycleOrder.getRecycle_product_watertype());
                    recycleOrderI.setRecycle_product_price(recycleOrder.getRecycle_product_price());
                    recycleOrderI.setRecycle_product_qty(recycleOrder.getRecycle_product_qty());
                    recycleOrderI.setBottleprice(recycleOrder.getBottleprice());
                    recycleOrderI.setRecycleBottleId(recycleOrder.getRecycleBottleId());
                    dataManager().RecycleproductOrder(recycleOrderI);


                }
            }


            // dataManager().deleteInfo();

            UserInfoModel userInfoModel = new UserInfoModel();
            userInfoModel.setAddress(userInfoBean.getAddress());
            userInfoModel.setAuthToken(userInfoBean.getAuthToken());
            userInfoModel.setBirthDate(userInfoBean.getBirthDate());
            userInfoModel.setContact(userInfoBean.getContact());
            userInfoModel.setCrd(userInfoBean.getCrd());
            userInfoModel.setPassword(logpassword.getText().toString().trim());
            userInfoModel.setCustomer_stripe_id(userInfoBean.getCustomer_stripe_id());
            userInfoModel.setDeviceType(userInfoBean.getDeviceType());
            userInfoModel.setDeviceToken(userInfoBean.getDeviceToken());
            userInfoModel.setEmail(userInfoBean.getEmail());
            userInfoModel.setBirthDate(userInfoBean.getBirthDate());
            userInfoModel.setFullName(userInfoBean.getFullName());
            userInfoModel.setLastName(userInfoBean.getLastName());
            userInfoModel.setUserId(userInfoBean.getId());
            userInfoModel.setPassword_token(userInfoBean.getPassword_token());
            userInfoModel.setSocialId(userInfoBean.getSocialId());
            dataManager().insertUserInfo(userInfoModel);


            dataManager().deleterecycleByUserId("000");
            handler.post(() -> {
                Intent intent = new Intent(SigninActivity.this, PickupAddreessActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);

            });

        }).start();


    }


}