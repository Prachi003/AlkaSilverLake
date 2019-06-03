package com.alkline.alkasilverlake.activity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alkline.alkasilverlake.R;
import com.alkline.alkasilverlake.Session;
import com.alkline.alkasilverlake.base.Alkasilverlake;
import com.alkline.alkasilverlake.connection.RetrofitClient;
import com.alkline.alkasilverlake.helper.PDialog;
import com.alkline.alkasilverlake.roomdatabase.AppDataManager;
import com.stripe.Stripe;
import com.stripe.model.Token;

import org.json.JSONObject;

import java.text.MessageFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;

public class OneTimePayMentActivity extends AppCompatActivity implements View.OnClickListener {
    private int width;
    private EditText edt_card_no;
    private TextView edDateTime;
    private String error;
    private PDialog pDialog;
    private EditText edCardHolder;
    private EditText edCvv;
    private int year1, month1;
    private String address = "";
    private String auto_pay = "";
    private String grand_total = "";
    private String start_date = "";
    private String end_not_define = "";
    private String product = "";
    private String recycle = "";
    private String end_date = "";
    private String autherized = "";
    private String distance_charge = "";
    private String schedule_type = "";
    private String product_charge = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one_time_pay_ment);
        initView();
    }

    public void initView() {
        edt_card_no = findViewById(R.id.edt_card_no);
        edCvv = findViewById(R.id.edCvv);
        edDateTime = findViewById(R.id.edDateTime);
        ImageView ivClose = findViewById(R.id.ivClose);
        RelativeLayout llDate = findViewById(R.id.llDatePick);
        edCardHolder = findViewById(R.id.edCardHolder);
        Button btnAdd = findViewById(R.id.btnAdd);

        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        width = displaymetrics.widthPixels;
/*
        RelativeLayout rlView = findViewById(R.id.rlView);
        rlView.setVisibility(View.GONE);
*/
        edDateTime.setOnClickListener(this);

        edt_card_no.setOnClickListener(this);
        btnAdd.setOnClickListener(this);

        llDate.setOnClickListener(this);
        ivClose.setOnClickListener(this);
        if (getIntent() != null) {
            address = getIntent().getStringExtra("address");
            auto_pay = getIntent().getStringExtra("auto_pay");
            start_date = getIntent().getStringExtra("start_date");
            schedule_type = getIntent().getStringExtra("schedule_type");
            grand_total = getIntent().getStringExtra("grand_total");
            end_not_define = getIntent().getStringExtra("end_not_define");
            product = getIntent().getStringExtra("product");
            recycle = getIntent().getStringExtra("recycle");
            end_date = getIntent().getStringExtra("end_date");
            autherized = getIntent().getStringExtra("autherized");
            distance_charge = getIntent().getStringExtra("distance_charge");
            product_charge = getIntent().getStringExtra("product_charge");


        }

        pDialog = new PDialog();


    }


    private void showMonthYearDialog() {
        final int year = Calendar.getInstance().get(Calendar.YEAR);
        final int month = Calendar.getInstance().get(Calendar.MONTH);
        final Dialog yearDialog;
        yearDialog = new Dialog(this);
        yearDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        yearDialog.setContentView(R.layout.dialog_month_year);
        WindowManager.LayoutParams params = Objects.requireNonNull(yearDialog.getWindow()).getAttributes();
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        yearDialog.getWindow().setAttributes(params);
        WindowManager.LayoutParams wlp = yearDialog.getWindow().getAttributes();
        wlp.gravity = Gravity.BOTTOM;
        Objects.requireNonNull(yearDialog.getWindow()).setLayout((width * 10) / 11, WindowManager.LayoutParams.WRAP_CONTENT);
        Button set = yearDialog.findViewById(R.id.button1);
        Button cancel = yearDialog.findViewById(R.id.button2);
        final NumberPicker yearPicker = yearDialog.findViewById(R.id.numberPicker1);
        final NumberPicker monthPicker = yearDialog.findViewById(R.id.numberPicker2);
        monthPicker.setMinValue(1);
        monthPicker.setMaxValue(12);
        monthPicker.setWrapSelectorWheel(false);
        monthPicker.setValue(month);
        monthPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        yearPicker.setMaxValue(2050);
        yearPicker.setMinValue(year);
        yearPicker.setWrapSelectorWheel(false);
        yearPicker.setValue(year);
        yearPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);

        set.setOnClickListener(v -> {
            String year2 = String.valueOf(yearPicker.getValue());
            year1 = yearPicker.getValue();
            month1 = monthPicker.getValue();
            //  year = year.substring(2);
            edDateTime.setText(MessageFormat.format("{0}/{1}", String.valueOf(monthPicker.getValue()), year2));

            yearDialog.dismiss();
        });
        cancel.setOnClickListener(v -> yearDialog.dismiss());
        yearDialog.show();
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.llDatePick:
                showMonthYearDialog();
                break;

            case R.id.btnAdd:
                if (checkValidation()) {
                    if (pDialog.checkInternetConnection(OneTimePayMentActivity.this)) {
                        new AsyncTaskRunner().execute();
                    } else {
                        Toast.makeText(OneTimePayMentActivity.this, "Network not available", Toast.LENGTH_LONG).show();

                    }


                }

                break;

            case R.id.edDateTime:
                showMonthYearDialog();
                break;

            case R.id.ivClose:
                onBackPressed();

                break;
        }
    }

    public boolean checkValidation() {
        if (TextUtils.isEmpty(edt_card_no.getText().toString().trim())) {
            Toast.makeText(this, "Enter Card no.", Toast.LENGTH_SHORT).show();
            return false;
        } else if (TextUtils.isEmpty(edCvv.getText().toString().trim())) {
            Toast.makeText(this, "Enter Cvv", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(edDateTime.getText().toString().trim())) {
            Toast.makeText(this, "Enter Card no.", Toast.LENGTH_SHORT).show();
            return false;
        } else if (TextUtils.isEmpty(edCardHolder.getText().toString().trim())) {
            Toast.makeText(this, "Enter Cardholder name.", Toast.LENGTH_SHORT).show();
            return false;

        }

        return true;
    }


    @SuppressLint("StaticFieldLeak")
    public class AsyncTaskRunner extends AsyncTask<Void, Void, Token> {

        @Override
        protected Token doInBackground(Void... voids) {
            Stripe.apiKey = "sk_test_SBQwdtKJ1q8KNJD0nyOSvwgS";

            Token token = null;
            Map<String, Object> tokenParams = new HashMap<>();
            Map<String, Object> cardParams = new HashMap<>();
            cardParams.put("number", edt_card_no.getText().toString().trim());
            cardParams.put("exp_month", month1);
            cardParams.put("exp_year", year1);
            cardParams.put("cvc", edCvv.getText().toString().trim());
            cardParams.put("name", edCardHolder.getText().toString().trim());
            tokenParams.put("card", cardParams);

            try {
                token = Token.create(tokenParams);
            } catch (Exception e) {
                error = e.getLocalizedMessage();


                Log.e("error", e.getMessage());
            }
            return token;
        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected void onPostExecute(Token token) {
            super.onPostExecute(token);
            if (token != null) {
                payment(token.getId());

            } else {
                Toast.makeText(OneTimePayMentActivity.this, error, Toast.LENGTH_SHORT).show();
            }

        }
    }

    public void payment(String source_type) {
        Session session = new Session(OneTimePayMentActivity.this);
        String authToken = session.getAuthtoken();
        pDialog.pdialog(OneTimePayMentActivity.this);
        Call<ResponseBody> call = RetrofitClient.getInstance().getApi()
                .paydata(authToken, grand_total, "CARD", auto_pay, source_type, schedule_type
                        , start_date, end_not_define, address, product, recycle, end_date, autherized
                        , session.getMiles(), distance_charge, "token", product_charge);
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
                        String message = jsonObject.getString("message");
                        if (message.equals("Order placed successfully")) {

                            new Thread(() -> {
                                dataManager().deleteAllOrder();
                                dataManager().deleteAllRecOrder();
                                Handler handler=new Handler(Looper.myLooper());
                                handler.post(() -> onBackPressed());
                            }).start();
                        }


                    } else if (response.code() == 400) {

                        @SuppressLint({"NewApi", "LocalSuppress"})
                        String result = Objects.requireNonNull(response.errorBody()).string();
                        Log.d("response400", result);
                        JSONObject jsonObject = new JSONObject(result);
                        String statusCode = jsonObject.optString("status");
                        String msg = jsonObject.optString("message");
                        if (statusCode.equals("fail")) {
                            Toast.makeText(OneTimePayMentActivity.this, msg, Toast.LENGTH_SHORT).show();
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

    public AppDataManager dataManager() {
        return Alkasilverlake.getDataManager();
    }


}
