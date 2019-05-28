package com.alkline.alkasilverlake.payment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.alkline.alkasilverlake.R;
import com.alkline.alkasilverlake.Session;
import com.alkline.alkasilverlake.helper.PDialog;
import com.alkline.alkasilverlake.payment.Model.StripeSaveCardResponce;
import com.alkline.alkasilverlake.payment.adapter.CardmanageAdapter;
import com.google.gson.Gson;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import com.stripe.model.ExternalAccountCollection;

import java.util.HashMap;
import java.util.Map;

public class PaymentMethodActivity extends AppCompatActivity implements View.OnClickListener {

    StripeSaveCardResponce cardResponce;
    private PDialog pDialog;
    private RecyclerView recycleCard;
    private CardmanageAdapter cardmanageAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_method);
        initView();
    }

    public void initView(){
        LinearLayout llCreditCard=findViewById(R.id.llCreditCard);
        ImageView ivBack=findViewById(R.id.ivBack);
        recycleCard=findViewById(R.id.recycleCard);
        llCreditCard.setOnClickListener(this);
        ivBack.setOnClickListener(this);
        pDialog=new PDialog();
        showCreditCardInfo();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.llCreditCard:
                Intent intent=new Intent(PaymentMethodActivity.this,AddCreditCardActivity.class);
                startActivityForResult(intent,101);
                break;

            case R.id.ivBack:
                onBackPressed();
                break;

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==101){
            showCreditCardInfo();
        }
    }

    ///""""""""" Saved credit card api """"""""//
    @SuppressLint("StaticFieldLeak")
    protected void showCreditCardInfo() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            pDialog.pdialog(this);
        }
        cardResponce = new StripeSaveCardResponce();
        new AsyncTask<Void, Void, ExternalAccountCollection>() {
            @Override
            protected ExternalAccountCollection doInBackground(Void... voids) {

                Stripe.apiKey = "sk_test_SBQwdtKJ1q8KNJD0nyOSvwgS";

                ExternalAccountCollection customer = null;
                try {
                    Session session=new Session(PaymentMethodActivity.this);
                    String customer_stripe_id = session.getRegistration().getCustomer_stripe_id();
                    Map<String, Object> cardParams = new HashMap<>();
                    cardParams.put("object", "card");
                    customer = Customer.retrieve(customer_stripe_id).getSources().all(cardParams);

                } catch (StripeException e) {
                    pDialog.hideDialog();
                }
                return customer;
            }

            @Override
            protected void onPostExecute(final ExternalAccountCollection externalAccountCollection) {
                super.onPostExecute(externalAccountCollection);
                pDialog.hideDialog();
                runOnUiThread(() -> {
                    if (externalAccountCollection != null) {

                        cardResponce = new Gson().fromJson(externalAccountCollection.toJson(), StripeSaveCardResponce.class);
                        Log.e("Size: ", "" + cardResponce.getData().size());

                        cardResponce.getData().size();
                        for (int i = 0; i < cardResponce.getData().size(); i++) {
                            cardResponce.getData().get(i).setMoreDetail(true);
                        }

                        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(PaymentMethodActivity.this);
                        cardmanageAdapter = new CardmanageAdapter(cardResponce.getData(), id -> removedSaveCardApi(id));
                        recycleCard.setHasFixedSize(true);
                        recycleCard.setLayoutManager(layoutManager);
                        recycleCard.setAdapter(cardmanageAdapter);






                    }

                });

            }

        }.execute();


    }


    //""""""""""  Remove Saved credit card """"""""""""//
    @SuppressLint("StaticFieldLeak")
    private void removedSaveCardApi(final String id) {
        if (pDialog.checkInternetConnection(PaymentMethodActivity.this)) {
            pDialog.pdialog(PaymentMethodActivity.this);
            new AsyncTask<Void, Void, Customer>() {
                @Override
                protected Customer doInBackground(Void... voids) {
                    Stripe.apiKey = "sk_test_SBQwdtKJ1q8KNJD0nyOSvwgS";

                    Customer customer = null;
                    try {

                        Session session=new Session(PaymentMethodActivity.this);
                        String customer_stripe_id = session.getRegistration().getCustomer_stripe_id();
                        customer = Customer.retrieve(customer_stripe_id);
                        customer.getSources().retrieve(id).delete();
                    } catch (StripeException e) {
                        e.printStackTrace();
                        pDialog.hideDialog();
                        Toast.makeText(PaymentMethodActivity.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }

                    return customer;
                }

                @Override
                protected void onPostExecute(Customer customer) {
                    super.onPostExecute(customer);
                    pDialog.hideDialog();
                    if (customer != null) {
                        showCreditCardInfo();
                    }
                }
            }.execute();
        }
    }
}
