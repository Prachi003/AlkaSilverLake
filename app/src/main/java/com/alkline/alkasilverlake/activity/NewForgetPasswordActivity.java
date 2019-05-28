package com.alkline.alkasilverlake.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alkline.alkasilverlake.R;
import com.alkline.alkasilverlake.connection.RetrofitClient;
import com.alkline.alkasilverlake.helper.PDialog;

import org.json.JSONObject;

import java.util.Objects;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;

public class NewForgetPasswordActivity extends AppCompatActivity implements View.OnClickListener {
    private String email = "";
    private PDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_forget_password);
       // this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        if (getIntent() != null) {
            email = getIntent().getStringExtra("email");
        }
        pDialog = new PDialog();
        initView();


    }




    void initView() {
        TextView txtEmail = findViewById(R.id.txtEmail);
        RelativeLayout btnForgetPassword = findViewById(R.id.btnForgetPassword);
        TextView txtRetry = findViewById(R.id.txtRetry);
        ImageView log_cancel = findViewById(R.id.log_cancel);
        btnForgetPassword.setOnClickListener(this);
        log_cancel.setOnClickListener(this);
        txtRetry.setOnClickListener(this);
        txtEmail.setText(email);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnForgetPassword:
                if (pDialog.checkInternetConnection(NewForgetPasswordActivity.this)) {
                    forgetPassword();

                } else {
                    Toast.makeText(NewForgetPasswordActivity.this, "Network not available", Toast.LENGTH_LONG).show();

                }
                break;


            case R.id.log_cancel:
                onBackPressed();
                break;


            case R.id.txtRetry:
                onBackPressed();
                break;
        }
    }


    public void forgetPassword() {


        pDialog.pdialog(this);
        Call<ResponseBody> call = RetrofitClient.getInstance().getApi()
                .Forgotuser(email);
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
                            pDialog.alertDialog(msg,NewForgetPasswordActivity.this);
                        } else {
                            pDialog.alertDialog(msg,NewForgetPasswordActivity.this);
                        }


                    } else if (response.code() == 400) {

                        @SuppressLint({"NewApi", "LocalSuppress"}) String result = Objects.requireNonNull(response.errorBody()).string();

                        Log.d("response400", result);

                        JSONObject jsonObject = new JSONObject(result);
                        String statusCode = jsonObject.optString("status");
                        String msg = jsonObject.optString("message");
                        if (statusCode.equals("fail")) {
                            pDialog.alertDialog(msg,NewForgetPasswordActivity.this);

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




}
