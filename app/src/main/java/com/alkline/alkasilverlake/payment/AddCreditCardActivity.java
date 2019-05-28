package com.alkline.alkasilverlake.payment;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
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
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.alkline.alkasilverlake.R;
import com.alkline.alkasilverlake.Session;
import com.alkline.alkasilverlake.helper.PDialog;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import com.stripe.model.Token;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class AddCreditCardActivity extends AppCompatActivity implements View.OnClickListener {
    private int width;
    private EditText edt_card_no;
    private TextView edDateTime;
    private String error;
    private PDialog pDialog;
    private EditText edCardHolder;
    private EditText edCvv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_credit_card);
        initView();
    }


    public void initView() {
        edt_card_no = findViewById(R.id.edt_card_no);
        edCvv = findViewById(R.id.edCvv);
        edDateTime = findViewById(R.id.edDateTime);
        ImageView ivClose = findViewById(R.id.ivClose);
        ImageView ivRightarrow = findViewById(R.id.ivRightarrow);
        ImageView ivBackArrow = findViewById(R.id.ivBackArrow);
        LinearLayout llDate = findViewById(R.id.llDate);
        edCardHolder = findViewById(R.id.edCardHolder);
        Button btnAddCard = findViewById(R.id.btnAddCard);
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        width = displaymetrics.widthPixels;
        edt_card_no.setOnClickListener(this);
        btnAddCard.setOnClickListener(this);
        llDate.setOnClickListener(this);
        ivClose.setOnClickListener(this);
        ivRightarrow.setOnClickListener(this);
        ivBackArrow.setOnClickListener(this);

        pDialog = new PDialog();
        if (edCvv.hasFocus()&&edCardHolder.hasFocus()){
            ivBackArrow.setColorFilter(ContextCompat.getColor(AddCreditCardActivity.this, R.color.whiteColor));

        }else {
            ivBackArrow.setColorFilter(ContextCompat.getColor(AddCreditCardActivity.this, R.color.lightblue));

        }

        edCvv.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                Log.d("focus", "focus loosed");
                // Do whatever you want here
            } else {
                ivBackArrow.setColorFilter(ContextCompat.getColor(AddCreditCardActivity.this, R.color.whiteColor));

            }
        });
        edt_card_no.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus){
                ivBackArrow.setColorFilter(ContextCompat.getColor(AddCreditCardActivity.this, R.color.lightblue));

            }

        });
        edCardHolder.setOnFocusChangeListener((v, hasFocus) -> {
         if (hasFocus){
             ivBackArrow.setColorFilter(ContextCompat.getColor(AddCreditCardActivity.this, R.color.whiteColor));

         }
        });




    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnAddCard:
                if (pDialog.checkInternetConnection(this)) {
                    if (checkValidation()) {
                        new AsyncTaskRunner().execute();
                    }
                }
                break;


            case R.id.llDate:
                showMonthYearDialog();
                break;

            case R.id.ivClose:
                onBackPressed();
                break;

            case R.id.ivRightarrow:
                if (edt_card_no.hasFocus()){
                    edCvv.requestFocus();
                }else if (edCvv.hasFocus()){
                    edCardHolder.requestFocus();
                }
                break;

            case R.id.ivBackArrow:
                if (edCvv.hasFocus()){
                 edt_card_no.requestFocus();
                }else  if (edCardHolder.hasFocus()){
                 edCvv.requestFocus();
                }

                break;






        }
    }

    int year1;
    int month1;


    //*************show  MonthYear  Dialog *******************//
    @SuppressLint({"NewApi", "SetTextI18n"})
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
            edDateTime.setText(monthPicker.getValue() + "/" + year2);

            yearDialog.dismiss();
        });
        cancel.setOnClickListener(v -> yearDialog.dismiss());
        yearDialog.show();
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
                saveCreditCard(token.getId());

            } else {
                Toast.makeText(AddCreditCardActivity.this, error, Toast.LENGTH_SHORT).show();
            }

        }
    }

    @SuppressLint("StaticFieldLeak")
    private void saveCreditCard(final String id) {
        pDialog.pdialog(this);

        new AsyncTask<Void, Void, Customer>() {
            @Override
            protected Customer doInBackground(Void... voids) {
                Stripe.apiKey = "sk_test_SBQwdtKJ1q8KNJD0nyOSvwgS";

                Customer customer = null;
                try {
                    //SharedPreferences preferences = getSharedPreferences("logdata", Context.MODE_PRIVATE);
                    Session session = new Session(AddCreditCardActivity.this);

                    String customer_stripe_id = session.getRegistration().getCustomer_stripe_id();

                    customer = Customer.retrieve(customer_stripe_id);
                    Map<String, Object> params = new HashMap<>();
                    params.put("source", id);
                    customer.getSources().create(params);

                } catch (StripeException e) {
                    e.printStackTrace();
                }
                return customer;
            }

            @Override
            protected void onPostExecute(Customer customer) {
                super.onPostExecute(customer);
                pDialog.hideDialog();
                if (customer != null) {
                    Toast.makeText(AddCreditCardActivity.this, "Your card is saved successfully.", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent();
                    setResult(RESULT_OK, intent);
                    finish();
// customer.toJson();
                } else {
                    Toast.makeText(AddCreditCardActivity.this, "Stripe Error", Toast.LENGTH_SHORT).show();

//                    Constant.snackBar(binding.svCreditcardLayout, "Stripe Error");
                }
            }
        }.execute();

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



}
