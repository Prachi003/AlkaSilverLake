package com.alkline.alkasilverlake.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alkline.alkasilverlake.R;
import com.alkline.alkasilverlake.Session;
import com.alkline.alkasilverlake.adapter.CardDiscAdapater;
import com.alkline.alkasilverlake.base.BaseActivity;
import com.alkline.alkasilverlake.connection.RetrofitClient;
import com.alkline.alkasilverlake.helper.PDialog;
import com.alkline.alkasilverlake.payment.AddCreditCardActivity;
import com.alkline.alkasilverlake.payment.Model.StripeSaveCardResponce;
import com.alkline.alkasilverlake.utils.OnSwipeListener;
import com.google.gson.Gson;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import com.stripe.model.ExternalAccountCollection;
import com.yarolegovich.discretescrollview.DiscreteScrollView;
import com.yarolegovich.discretescrollview.transform.ScaleTransformer;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;

public class PaymentAndAddCardActivity extends BaseActivity implements CardDiscAdapater.ScrollListener,View.OnTouchListener,View.OnClickListener {
    private PDialog pDialog;
    StripeSaveCardResponce cardResponce;
    private CardDiscAdapater cardmanageAdapter;
    private DiscreteScrollView scCard;
    private String address = "";
    private String auto_pay = "";
    private String grand_total = "";
    private String start_date = "";
    private String end_not_define = "";
    private String product = "";
    private String recycle = "";
    Handler handler=new Handler(Looper.myLooper());
    private String end_date = "";
    private String autherized = "";
    private String distance_charge = "";
    private String schedule_type = "";

    private String product_charge = "";
    private String cardId = "";

    private GestureDetector gestureDetector;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_and_add_card_zctivity);
        initView();
    }


    private void initView() {
        scCard = findViewById(R.id.scCard);
        LinearLayout llOneTimePayment = findViewById(R.id.llOneTimePayment);
        LinearLayout llCreditCard = findViewById(R.id.llCreditCard);
        LinearLayout llPickDel = findViewById(R.id.llPickDel);
        TextView txtTotal = findViewById(R.id.txtTotal);
        ImageView ivClose = findViewById(R.id.ivClose);

        pDialog = new PDialog();
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

        txtTotal.setText(String.format("%s$%s", getString(R.string.total_am), grand_total));

        llOneTimePayment.setOnClickListener(this);
        llCreditCard.setOnClickListener(this);
        llPickDel.setOnClickListener(this);
        ivClose.setOnClickListener(this);
        if (auto_pay.equals("1")) {
            llOneTimePayment.setVisibility(View.GONE);
        } else {
            llOneTimePayment.setVisibility(View.VISIBLE);
        }
        showCreditCardInfo();
        findViewById(R.id.llCheckOut).setOnTouchListener(this);

        gestureDetector=new GestureDetector(this,new OnSwipeListener(){

            @Override
            public boolean onSwipe(Direction direction) {
                if (direction==Direction.up){
                   payment();

                }
                return true;
            }


        });

        scCard.addScrollListener((scrollPosition, currentPosition, newPosition, currentHolder, newCurrent) -> cardId = cardResponce.getData().get(currentPosition).getId());
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
                    Session session = new Session(PaymentAndAddCardActivity.this);
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
                        cardmanageAdapter = new CardDiscAdapater(cardResponce.getData(), PaymentAndAddCardActivity.this);
                        scCard.setAdapter(cardmanageAdapter);
                        scCard.setItemTransformer(new ScaleTransformer.Builder()
                                .setMinScale(0.8f)
                                .build());
                        if (cardResponce.getData().size()>0)
                            cardId=cardResponce.getData().get(0).getId();


                    }

                });

            }

        }.execute();


    }

    @Override
    public void onScroll(float scrollPosition, int currentIndex, int newIndex, @Nullable CardDiscAdapater.ViewHolder currentHolder, @Nullable CardDiscAdapater.ViewHolder newCurrentHolder) {
        cardId = cardResponce.getData().get(currentIndex).getId();
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        gestureDetector.onTouchEvent(motionEvent);
        return true;
    }

    public void payment() {
        Session session=new Session(PaymentAndAddCardActivity.this);
        String authToken=session.getAuthtoken();
        pDialog.pdialog(PaymentAndAddCardActivity.this);
        Call<ResponseBody> call = RetrofitClient.getInstance().getApi()
                .paydata(authToken,grand_total,"CARD",auto_pay,cardId,schedule_type
                        ,start_date,end_not_define,address,product,recycle,end_date,autherized
                        ,session.getMiles(),distance_charge,"card",product_charge);
        call.enqueue(new Callback<ResponseBody>() {
            @SuppressLint("NewApi")
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull retrofit2.Response<ResponseBody> response) {
                try {
                    pDialog.hideDialog();
                    if (response.code() == 200) {
                        @SuppressLint({"NewApi", "LocalSuppress"}) String stresult = Objects.requireNonNull(response.body()).string();Log.d("response", stresult);
                        JSONObject jsonObject = new JSONObject(stresult);
                        String message=jsonObject.getString("message");
                        Toast.makeText(PaymentAndAddCardActivity.this, ""+message, Toast.LENGTH_SHORT).show();
                        if (message.equals("Order placed successfully")){

                            new Thread(() -> {
                                dataManager().deleteAllOrder();
                                dataManager().deleteAllRecOrder();

                                handler.post(() -> {
                                    Intent intent=new Intent(PaymentAndAddCardActivity.this,TabActivity.class);
                                    intent.putExtra("from", "payment");
                                    startActivity(intent);

                                });
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
                            Toast.makeText(PaymentAndAddCardActivity.this, msg, Toast.LENGTH_SHORT).show();
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






    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.llOneTimePayment:
                Session session=new Session(PaymentAndAddCardActivity.this);
                Intent intent = new Intent(PaymentAndAddCardActivity.this, OneTimePayMentActivity.class);
                intent.putExtra("address",address);
                intent.putExtra("auto_pay",auto_pay);
                intent.putExtra("schedule_type",schedule_type);
                intent.putExtra("start_date",start_date);
                intent.putExtra("end_not_define",end_not_define);
                intent.putExtra("address",address);
                intent.putExtra("grand_total",grand_total);
                intent.putExtra("product",product);
                intent.putExtra("recycle",recycle);
                intent.putExtra("end_date",end_date);
                intent.putExtra("autherized","1");
                intent.putExtra("delivery_distance",session.getMiles());
                intent.putExtra("distance_charge",session.getDeliveryFee());
                intent.putExtra("product_charge",session.getMinCharge());
                startActivity(intent);

                break;

            case R.id.llCreditCard:
                Intent intentAddCredit=new Intent(PaymentAndAddCardActivity.this, AddCreditCardActivity.class);
                startActivityForResult(intentAddCredit,119);
                break;

            case R.id.ivClose:
                onBackPressed();
                break;

            case R.id.llPickDel:
                Intent intent1=new Intent(PaymentAndAddCardActivity.this,MapsActivity.class);
                startActivity(intent1);
                break;
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==119){
            showCreditCardInfo();
        }
    }
}
