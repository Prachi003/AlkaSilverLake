package com.alkline.alkasilverlake.activity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.design.widget.CheckableImageButton;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.text.Editable;
import android.text.InputType;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

import java.util.Calendar;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;

public class NewAccActivity extends BaseActivity implements View.OnClickListener {

    //Pattern PASSWORD_PATTERN = Pattern.compile("^(?=.*[A-Z])(?=.*[!@#$&*])(?=.*[0-9])(?=.*[a-z]).{8,100}$");
    Pattern PASSWORD_PATTERN = Pattern.compile("^(?=.*[A-Z])(?=.*[!@#$.,%^&*])(?=.*[0-9])(?=.*[a-z]).{8,100}");

    private EditText firstname, lastname, email, mobnumber, password, birth;
    private TextInputLayout firstnameLayout;
    private TextInputLayout lastnameLayout;
    private TextInputLayout emailLayout;
    private TextInputLayout mobnumberLayout;
    private TextInputLayout passwordLayout;
    private CheckableImageButton passwordToggleView;
    private Boolean toggleFlag = false;
    private Handler handler=new Handler(Looper.myLooper());
    private PDialog pDialog;
    private LinearLayout ll_fullpassword;
    private String refreshedToken = "";
    private TextWatcher watcherClass_fname = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        @Override
        public void afterTextChanged(Editable editable) {

            ll_fullpassword.setVisibility(View.GONE);
            if (editable == firstname.getEditableText() && editable.length() >= 1) {
                firstnameLayout.setHint(getResources().getString(R.string.fname));
                firstnameLayout.setDefaultHintTextColor(ColorStateList.valueOf(getResources().getColor(R.color.colorgray)));

            } else {
                firstnameLayout.setHint(getResources().getString(R.string.fnameerror));
                firstnameLayout.setDefaultHintTextColor(ColorStateList.valueOf(getResources().getColor(R.color.red)));
//                tvfnameerror.setVisibility(View.VISIBLE);
            }
        }
    };
    private TextWatcher textWatcher_lname = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {

            ll_fullpassword.setVisibility(View.GONE);
            if (editable == lastname.getEditableText() && editable.length() >= 1) {
                lastnameLayout.setHint(getResources().getString(R.string.lname));
                lastnameLayout.setDefaultHintTextColor(ColorStateList.valueOf(getResources().getColor(R.color.darkGrayColor)));

//                tvlnameerror.setVisibility(View.GONE);
//            firstnameLayout.setDefaultHintTextColor(ColorStateList.valueOf(getResources().getColor(R.color.Darkgraycolor)));
            } else {
                lastnameLayout.setHint(getResources().getString(R.string.lnameerror));
                lastnameLayout.setDefaultHintTextColor(ColorStateList.valueOf(getResources().getColor(R.color.red)));
//                tvlnameerror.setVisibility(View.VISIBLE);
            }

        }
    };
    private TextWatcher textWatcher_email = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {

            ll_fullpassword.setVisibility(View.GONE);
            if (editable == email.getEditableText() && editable.length() >= 1) {

                emailLayout.setHint(getResources().getString(R.string.email));
                emailLayout.setDefaultHintTextColor(ColorStateList.valueOf(getResources().getColor(R.color.darkGrayColor)));

//                tvemailerror.setVisibility(View.GONE);
//                invalid_emailerror.setVisibility(View.GONE);
                if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches()) {
                    emailLayout.setHint(getResources().getString(R.string.emailInvalidError));
                    emailLayout.setDefaultHintTextColor(ColorStateList.valueOf(getResources().getColor(R.color.red)));

//                    invalid_emailerror.setVisibility(View.VISIBLE);
//                    tvemailerror.setVisibility(View.GONE);
                }
            } else {
                emailLayout.setHint(getResources().getString(R.string.emailerror));
                emailLayout.setDefaultHintTextColor(ColorStateList.valueOf(getResources().getColor(R.color.red)));

//                tvemailerror.setVisibility(View.VISIBLE);
//                invalid_emailerror.setVisibility(View.GONE);
            }
        }
    };
    private TextWatcher textWatcher_mob = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            ll_fullpassword.setVisibility(View.GONE);

            if (editable == mobnumber.getEditableText() && editable.length() >= 1) {
                mobnumberLayout.setHint(getResources().getString(R.string.mob));
                mobnumberLayout.setDefaultHintTextColor(ColorStateList.valueOf(getResources().getColor(R.color.darkGrayColor)));

//                tvmoberror.setVisibility(View.GONE);
//            firstnameLayout.setDefaultHintTextColor(ColorStateList.valueOf(getResources().getColor(R.color.Darkgraycolor)));
            } else {
                mobnumberLayout.setHint(getResources().getString(R.string.moberror));
                mobnumberLayout.setDefaultHintTextColor(ColorStateList.valueOf(getResources().getColor(R.color.red)));

//                tvmoberror.setVisibility(View.VISIBLE);
            }

        }
    };
    private TextWatcher textWatcher_pass = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            if (editable == password.getEditableText() && editable.length() >= 1) {
                passwordLayout.setHint(getResources().getString(R.string.passguide));
                passwordLayout.setDefaultHintTextColor(ColorStateList.valueOf(getResources().getColor(R.color.red)));
                if (!toggleFlag) {
                    toggleFlag = true;
                    passwordToggleView.setColorFilter(ContextCompat.getColor(NewAccActivity.this,R.color.red),PorterDuff.Mode.SRC_IN);
                }
                if (!PASSWORD_PATTERN.matcher(password.getText().toString()).matches()) {
                    if (!toggleFlag) {
                        toggleFlag = true;
                        passwordToggleView.setColorFilter(ContextCompat.getColor(NewAccActivity.this,R.color.red),PorterDuff.Mode.SRC_IN);
                    }
                } else {
                    passwordLayout.setHint(getResources().getString(R.string.password));
                    passwordLayout.setDefaultHintTextColor(ColorStateList.valueOf(getResources().getColor(R.color.darkGrayColor)));
                    if (toggleFlag) {
                        toggleFlag = false;
                        passwordToggleView.setColorFilter(ContextCompat.getColor(NewAccActivity.this,R.color.darkGrayColor),PorterDuff.Mode.SRC_IN);
                    }
                    ll_fullpassword.setVisibility(View.GONE);
                }

            } else {
                passwordLayout.setHint(getResources().getString(R.string.passworderror));
                passwordLayout.setDefaultHintTextColor(ColorStateList.valueOf(getResources().getColor(R.color.red)));
                if (toggleFlag) {
                    toggleFlag = false;
                    passwordToggleView.setColorFilter(ContextCompat.getColor(NewAccActivity.this,R.color.red),PorterDuff.Mode.SRC_IN);
                }
            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_acc);
        //this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        pDialog = new PDialog();
        refreshedToken = FirebaseInstanceId.getInstance().getToken();
        init();
    }

    @SuppressLint("NewApi")
    private void init() {
        TextView tvSpannanbleContents = findViewById(R.id.tvSpannanbleContents);
        String mContents = getString(R.string.accotext);
        SpannableString mHints = new SpannableString(getString(R.string.accotext));
        String pp = getString(R.string.privacy_and_terms);
        int ppStartIndex = mContents.indexOf(pp);
        mHints.setSpan(new StyleSpan(android.graphics.Typeface.BOLD) {
            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setTextSize(19);
                ds.setColor(Color.BLACK);
            }
        },ppStartIndex,ppStartIndex+pp.length(),0);
        tvSpannanbleContents.setText(mHints);


        ImageView ivacc_cancel = findViewById(R.id.acc_cancel);
        TextView tvacc_signin = findViewById(R.id.tvacc_signin);

        firstname = findViewById(R.id.newacc_firstname);
        lastname = findViewById(R.id.newacc_lastname);
        email = findViewById(R.id.newacc_email);
        ll_fullpassword = findViewById(R.id.fullpassword);
        mobnumber = findViewById(R.id.newacc_mobile);
        password = findViewById(R.id.newacc_password);
        password.setTypeface(firstname.getTypeface());
        password.setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
        password.setTransformationMethod(PasswordTransformationMethod.getInstance());
        birth = findViewById(R.id.newacc_birth);
        firstnameLayout = findViewById(R.id.newacc_lay_firstname);
        lastnameLayout = findViewById(R.id.newacc_lay_lastname);
        emailLayout = findViewById(R.id.newacc_lay_email);
        mobnumberLayout = findViewById(R.id.newacc_lay_mobile);
        passwordLayout = findViewById(R.id.newacc_lay_Password);
        passwordLayout.post(() -> passwordToggleView = passwordLayout.findViewById(R.id.text_input_password_toggle));


        TextInputLayout birthLayout = findViewById(R.id.newacc_lay_birth);
        Button btnacc = findViewById(R.id.btnacc);

        btnacc.setOnClickListener(this);
        firstname.setOnClickListener(this);
        birth.setOnClickListener(this);
        ivacc_cancel.setOnClickListener(this);
        tvacc_signin.setOnClickListener(this);


        firstname.addTextChangedListener(watcherClass_fname);
        lastname.addTextChangedListener(textWatcher_lname);
        email.addTextChangedListener(textWatcher_email);
        mobnumber.addTextChangedListener(textWatcher_mob);
        password.addTextChangedListener(textWatcher_pass);

        firstname.setOnClickListener(this);
        lastname.setOnClickListener(this);
        email.setOnClickListener(this);
        mobnumber.setOnClickListener(this);



        firstnameLayout.setHint(getResources().getString(R.string.fname));
        lastnameLayout.setHint(getResources().getString(R.string.lname));
        emailLayout.setHint(getResources().getString(R.string.email));
        mobnumberLayout.setHint(getResources().getString(R.string.mob));
        passwordLayout.setHint(getResources().getString(R.string.password));
        birthLayout.setHint(getResources().getString(R.string.birth));


        firstnameLayout.setDefaultHintTextColor(ColorStateList.valueOf(getResources().getColor(R.color.colorgray)));
        lastnameLayout.setDefaultHintTextColor(ColorStateList.valueOf(getResources().getColor(R.color.colorgray)));
        emailLayout.setDefaultHintTextColor(ColorStateList.valueOf(getResources().getColor(R.color.colorgray)));
        passwordLayout.setDefaultHintTextColor(ColorStateList.valueOf(getResources().getColor(R.color.colorgray)));
        mobnumberLayout.setDefaultHintTextColor(ColorStateList.valueOf(getResources().getColor(R.color.colorgray)));
        birthLayout.setDefaultHintTextColor(ColorStateList.valueOf(getResources().getColor(R.color.colorgray)));

        Typeface typeface = ResourcesCompat.getFont(this, R.font.brownlight);

       /* Typeface font_yekan= Typeface.createFromAsset(getAssets(), "brownlight.otf");
        passwordLayout.setTypeface(font_yekan);*/
        password.setTypeface(typeface);

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnacc: {
                boolean flag = true;
                String stfname = firstname.getText().toString();
                String stlname = lastname.getText().toString();
                String stemail = email.getText().toString();
                String stpass = password.getText().toString();
                String stmob = mobnumber.getText().toString();

                if (pDialog.checkInternetConnection(this)) {
                    if (stfname.length() == 0) {
                        flag = false;
                        firstnameLayout.setHint(getResources().getString(R.string.fnameerror));
                        firstnameLayout.setDefaultHintTextColor(ColorStateList.valueOf(getResources().getColor(R.color.red)));
                    } else {
                        lastnameLayout.setHint(getResources().getString(R.string.fname));
                        lastnameLayout.setDefaultHintTextColor(ColorStateList.valueOf(getResources().getColor(R.color.darkGrayColor)));
                    }

                    if (stlname.length() == 0) {
                        flag = false;
                        lastnameLayout.setHint(getResources().getString(R.string.lnameerror));
                        lastnameLayout.setDefaultHintTextColor(ColorStateList.valueOf(getResources().getColor(R.color.red)));

                    } else {
                        lastnameLayout.setHint(getResources().getString(R.string.lname));
                        lastnameLayout.setDefaultHintTextColor(ColorStateList.valueOf(getResources().getColor(R.color.darkGrayColor)));

                    }

                    if (stemail.length() == 0) {
                        flag = false;
                        emailLayout.setHint(getResources().getString(R.string.emailerror));
                        emailLayout.setDefaultHintTextColor(ColorStateList.valueOf(getResources().getColor(R.color.red)));

                    } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches()) {
                        flag = false;
                        emailLayout.setHint(getResources().getString(R.string.emailInvalidError));
                        emailLayout.setDefaultHintTextColor(ColorStateList.valueOf(getResources().getColor(R.color.red)));
                    } else {
                        emailLayout.setHint(getResources().getString(R.string.email));
                        emailLayout.setDefaultHintTextColor(ColorStateList.valueOf(getResources().getColor(R.color.darkGrayColor)));
                    }

                    if (stmob.length() == 0) {
                        flag = false;
                        mobnumberLayout.setHint(getResources().getString(R.string.moberror));
                        mobnumberLayout.setDefaultHintTextColor(ColorStateList.valueOf(getResources().getColor(R.color.red)));
                    } else {
                        mobnumberLayout.setHint(getResources().getString(R.string.mob));
                        mobnumberLayout.setDefaultHintTextColor(ColorStateList.valueOf(getResources().getColor(R.color.darkGrayColor)));
                    }

                    if (stpass.length() == 0) {
                        flag = false;
                        passwordToggleView.setColorFilter(ContextCompat.getColor(NewAccActivity.this,R.color.red),PorterDuff.Mode.SRC_IN);
                        passwordLayout.setHint(getResources().getString(R.string.passworderror));
                        passwordLayout.setDefaultHintTextColor(ColorStateList.valueOf(getResources().getColor(R.color.red)));

                    } else if (!PASSWORD_PATTERN.matcher(password.getText().toString()).matches()) {
                        flag = false;
                        ll_fullpassword.setVisibility(View.VISIBLE);
                        passwordToggleView.setColorFilter(ContextCompat.getColor(NewAccActivity.this,R.color.red),PorterDuff.Mode.SRC_IN);
                        passwordLayout.setHint(getResources().getString(R.string.passguide));
                        passwordLayout.setDefaultHintTextColor(ColorStateList.valueOf(getResources().getColor(R.color.red)));
                    } else {
                        ll_fullpassword.setVisibility(View.GONE);
                        passwordToggleView.setColorFilter(ContextCompat.getColor(NewAccActivity.this,R.color.darkGrayColor),PorterDuff.Mode.SRC_IN);
                        passwordLayout.setHint(getResources().getString(R.string.password));
                        passwordLayout.setDefaultHintTextColor(ColorStateList.valueOf(getResources().getColor(R.color.darkGrayColor)));
                    }

                    if (flag) {
                        //ToDo: Call API here
                        RegisterUser();

                    }
                }else {
                    Toast.makeText(NewAccActivity.this, "Network not available", Toast.LENGTH_LONG).show();

                }



            }
            break;
            case R.id.newacc_firstname: {
                ll_fullpassword.setVisibility(View.GONE);
            }
            break;
            case R.id.newacc_lastname: {
                ll_fullpassword.setVisibility(View.GONE);
            }
            break;
            case R.id.newacc_email: {
                ll_fullpassword.setVisibility(View.GONE);
            }
            case R.id.newacc_mobile: {
                ll_fullpassword.setVisibility(View.GONE);
            }

            break;
            case R.id.newacc_birth:
            {
                ll_fullpassword.setVisibility(View.GONE);

                startdatemathod();
            }
            break;
            case R.id.acc_cancel:
            {

                backPress();
            }
            break;
            case R.id.tvacc_signin:
            {

                Intent intent = new Intent(NewAccActivity.this,SigninActivity.class);
                startActivity(intent);
            }
            break;
        }
    }
    public void startdatemathod() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        @SuppressLint("DefaultLocale") DatePickerDialog datePickerDialog = new DatePickerDialog(NewAccActivity.this,
                (datePicker, year1, month1, day1) ->
                        birth.setText(String.format("%d/%d/%d", day1, month1 + 1, year1)), year, month, day);
        //datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
        datePickerDialog.show();
    }


    public void RegisterUser() {
        String uname = firstname.getText().toString().trim();
        String lastnamev = lastname.getText().toString().trim();
        String uemail = email.getText().toString().trim();
        final String upass = password.getText().toString().trim();
        pDialog.pdialog(NewAccActivity.this);
        Call<ResponseBody> call = RetrofitClient.getInstance().getApi()
                .createuser(uname, uemail, lastnamev, upass, refreshedToken);
        call.enqueue(new Callback<ResponseBody>() {
            @SuppressLint("NewApi")
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull retrofit2.Response<ResponseBody> response) {
                try {
                    pDialog.hideDialog();
                    if (response.code() == 200) {
                        @SuppressLint({"NewApi", "LocalSuppress"}) String stresult = Objects.requireNonNull(response.body()).string();
                        Log.d("response", stresult);
                        JSONObject jsonObject = new JSONObject(stresult);
                        String statusCode = jsonObject.optString("status");
                        String msg = jsonObject.optString("message");
                        if (statusCode.equals("success")) {
                            Session session = new Session(NewAccActivity.this);
                            session.issetLoggedIn(true);
                            JSONObject jsonObject1 = jsonObject.getJSONObject("userDetail");
                            Gson gson = new Gson();
                            UserInfoBean.UserDetailBean registrationInfo = gson.fromJson(String.valueOf(jsonObject1), UserInfoBean.UserDetailBean.class);
                            registrationInfo.setPassword(upass);
                            session.createRegistration(registrationInfo);
                            String id = jsonObject1.getString("id");
                            session.putuserid(id);
                            String authToken = jsonObject1.getString("authToken");
                            insertGuestToUser(id, registrationInfo);
                            session.putAuthtoken(authToken);
                            Toast.makeText(NewAccActivity.this, msg, Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(NewAccActivity.this, TabActivity.class);
                            intent.putExtra("from", "");
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(NewAccActivity.this, msg, Toast.LENGTH_SHORT).show();

                        }


                    } else if (response.code() == 400) {

                        @SuppressLint({"NewApi", "LocalSuppress"}) String result = Objects.requireNonNull(response.errorBody()).string();

                        Log.d("response400", result);

                        JSONObject jsonObject = new JSONObject(result);
                        String statusCode = jsonObject.optString("status");
                        String msg = jsonObject.optString("message");
                        if (statusCode.equals("fail")) {
                            Toast.makeText(NewAccActivity.this, msg, Toast.LENGTH_SHORT).show();

                        }
                    } else if (response.code() == 401) {

                        try {
                            assert response.errorBody() != null;
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

            UserInfoModel userInfoModel = new UserInfoModel();
            userInfoModel.setAddress(userInfoBean.getAddress());
            userInfoModel.setAuthToken(userInfoBean.getAuthToken());
            userInfoModel.setBirthDate(userInfoBean.getBirthDate());
            userInfoModel.setContact(userInfoBean.getContact());
            userInfoModel.setCrd(userInfoBean.getCrd());
            userInfoModel.setPassword(password.getText().toString().trim());
            userInfoModel.setCustomer_stripe_id(userInfoBean.getCustomer_stripe_id());
            userInfoModel.setDeviceType(userInfoBean.getDeviceType());
            userInfoModel.setDeviceToken(userInfoBean.getDeviceToken());
            userInfoModel.setEmail(userInfoBean.getEmail());
            userInfoModel.setLastName(userInfoBean.getLastName());
            userInfoModel.setFullName(userInfoBean.getFullName());
            userInfoModel.setUserId(userInfoBean.getId());
            userInfoModel.setPassword_token(userInfoBean.getPassword_token());
            userInfoModel.setSocialId(userInfoBean.getSocialId());
            dataManager().insertUserInfo(userInfoModel);


            dataManager().deleterecycleByUserId("000");
            handler.post(() -> {
                Intent intent = new Intent(NewAccActivity.this, PickupAddreessActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);

            });

        }).start();


    }

}
