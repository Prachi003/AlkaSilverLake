package com.alkline.alkasilverlake.activity;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.alkline.alkasilverlake.R;
import com.alkline.alkasilverlake.base.BaseActivity;
import com.alkline.alkasilverlake.helper.PDialog;

public class ForgetPasswordActivity extends BaseActivity implements View.OnClickListener {
    private EditText etEmail;
    private TextInputLayout forgetTextInput;
    private PDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot);
       // this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        initView();
    }


    void initView(){
     etEmail=findViewById(R.id.etEmail);
        Button btnForgetPassword = findViewById(R.id.btnForgetPassword);
        ImageView log_cancel = findViewById(R.id.log_cancel);
        btnForgetPassword.setOnClickListener(this);
        log_cancel.setOnClickListener(this);

        forgetTextInput=findViewById(R.id.forgetTextInput);
        pDialog = new PDialog();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnForgetPassword:
                boolean flag = true;
                if (pDialog.checkInternetConnection(ForgetPasswordActivity.this)) {

                    if (TextUtils.isEmpty(etEmail.getText().toString().trim())) {
                        flag = false;
                        forgetTextInput.setHint(getResources().getString(R.string.emailerror));
                        forgetTextInput.setDefaultHintTextColor(ColorStateList.valueOf(getResources().getColor(R.color.red)));

                    } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(etEmail.getText().toString()).matches()) {
                        flag = false;
                        forgetTextInput.setHint(getResources().getString(R.string.emailInvalidError));
                        forgetTextInput.setDefaultHintTextColor(ColorStateList.valueOf(getResources().getColor(R.color.red)));
                    } else {
                        forgetTextInput.setHint(getResources().getString(R.string.email));
                        forgetTextInput.setDefaultHintTextColor(ColorStateList.valueOf(getResources().getColor(R.color.gray_light_new)));
                    }


                    if (flag)
                    {

                        Intent intent=new Intent(ForgetPasswordActivity.this,NewForgetPasswordActivity.class);
                        intent.putExtra("email",etEmail.getText().toString().trim());
                        startActivity(intent);
                        //forgetPassword();
                    }
                } else {
                    Toast.makeText(ForgetPasswordActivity.this, "Network not available", Toast.LENGTH_LONG).show();

                }


                break;

            case R.id.log_cancel:
                onBackPressed();
                break;
        }

    }


}
