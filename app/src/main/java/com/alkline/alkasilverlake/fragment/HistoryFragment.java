package com.alkline.alkasilverlake.fragment;


import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alkline.alkasilverlake.R;
import com.alkline.alkasilverlake.Session;
import com.alkline.alkasilverlake.activity.CartDetailActivity;
import com.alkline.alkasilverlake.activity.TabActivity;
import com.alkline.alkasilverlake.adapter.HistoryAdapter;
import com.alkline.alkasilverlake.base.BaseFragment;
import com.alkline.alkasilverlake.connection.RetrofitClient;
import com.alkline.alkasilverlake.helper.PDialog;
import com.alkline.alkasilverlake.model.HistoryModel;
import com.alkline.alkasilverlake.profile.UserProfileActivity;
import com.alkline.alkasilverlake.roomdatabasemodel.AddOrder;
import com.alkline.alkasilverlake.roomdatabasemodel.RecycleOrder;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import okhttp3.ResponseBody;
import retrofit2.Call;

/**
 * A simple {@link Fragment} subclass.
 */
public class HistoryFragment extends BaseFragment implements HistoryAdapter.onclickListener, View.OnClickListener {
    PDialog pDialog;
    private Session session;
    private LinearLayout llCheckOut;
    private List<HistoryModel.DataBean> productsBeanList = new ArrayList<>();

    private HistoryAdapter historyAdapter;
    private RelativeLayout rlCount;
    private TextView txtCount;
    private TextView txtNoHistory;
    private Dialog dialog;

    private Handler handler = new Handler(Looper.myLooper());


    public HistoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_history, container, false);
        initView(view);
        return view;

    }

    private void initView(View view) {
        session = new Session(mContext);
        pDialog = new PDialog();
        dialog = new Dialog(mContext);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerProduct);
        Button btnMenu = view.findViewById(R.id.btnMenu);
        ImageView ivProfilenew = view.findViewById(R.id.ivProfilenew);
        RelativeLayout rlCart = view.findViewById(R.id.rlCart);
        rlCount = view.findViewById(R.id.rlCount);
        txtCount = view.findViewById(R.id.txtCount);
        txtNoHistory = view.findViewById(R.id.txtNoHistory);
        llCheckOut = view.findViewById(R.id.llCheckOut);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        historyAdapter = new HistoryAdapter(productsBeanList, mContext, this);
        recyclerView.setAdapter(historyAdapter);
        llCheckOut.setOnClickListener(this);
        btnMenu.setOnClickListener(this);
        ivProfilenew.setOnClickListener(this);
        rlCart.setOnClickListener(this);
        getHistory();
        if (productsBeanList.size() == 0) {
            txtNoHistory.setVisibility(View.VISIBLE);
        }

    }


    public void getHistory() {


        pDialog.pdialog(mContext);
        Call<ResponseBody> call = RetrofitClient.getInstance().getApi()
                .getOrders(session.getAuthtoken());

        call.enqueue(new retrofit2.Callback<ResponseBody>() {
            @SuppressLint("NewApi")
            @Override

            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull retrofit2.Response<ResponseBody> response) {

                try {

                    pDialog.hideDialog();

                    if (response.code() == 200) {

                        @SuppressLint({"NewApi", "LocalSuppress"})
                        String stresult = Objects.requireNonNull(response.body()).string();
                        Log.i("eail4254", "" + stresult);
                        JSONObject jsonObject = new JSONObject(stresult);
                        String statusCode = jsonObject.optString("status");
                        String msg = jsonObject.optString("message");
                        productsBeanList.clear();

                        if (statusCode.equals("success")) {
                            Gson gson = new Gson();
                            JSONArray jsonArray = jsonObject.getJSONArray("data");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObjectData = jsonArray.getJSONObject(i);
                                HistoryModel.DataBean productsBean = gson.fromJson(jsonObjectData.toString(), HistoryModel.DataBean.class);
                                productsBeanList.add(productsBean);
                            }
                            if (productsBeanList.size() == 0) {
                                txtNoHistory.setVisibility(View.VISIBLE);
                            } else {
                                txtNoHistory.setVisibility(View.GONE);
                            }
                            historyAdapter.notifyDataSetChanged();
                        } else {
                            Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();

                        }

                    } else if (response.code() == 400) {
                        @SuppressLint({"NewApi", "LocalSuppress"})
                        String result = Objects.requireNonNull(response.errorBody()).string();
                        Log.d("response400", result);
                        JSONObject jsonObject = new JSONObject(result);
                        String msg = jsonObject.optString("message");
                        Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
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


    @Override
    public void getPos(int position) {
        if (productsBeanList.get(position).getFavourite().equals("0")) {
            showAddNameDialog(productsBeanList.get(position).getOrderId());
        } else {
            addNameApi("name", productsBeanList.get(position).getOrderId(), dialog);
        }


    }

    @Override
    public void getAddCartPos(int position, boolean isSelect) {
        if (productsBeanList.get(position).isSelect()) {
            llCheckOut.setVisibility(View.VISIBLE);
        } else {
            for (int i = 0; i < productsBeanList.size(); i++) {
                if (productsBeanList.get(i).isSelect()) {
                    llCheckOut.setVisibility(View.VISIBLE);
                    break;
                } else {
                    llCheckOut.setVisibility(View.GONE);

                }
            }


        }

    }

    private void showAddNameDialog(String orderId) {
        dialog = new Dialog(mContext);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.dialog_add_name);
        Window window = dialog.getWindow();
        assert window != null;
        EditText edtName = dialog.findViewById(R.id.edtName);
        TextView txtCancel = dialog.findViewById(R.id.txtCancel);
        CardView btnAdd = dialog.findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(view -> {
            if (TextUtils.isEmpty(edtName.getText().toString().trim())) {
                Toast.makeText(mContext, "Please Enter Name", Toast.LENGTH_SHORT).show();
            } else {
                addNameApi(edtName.getText().toString().trim(), orderId, dialog);
            }
        });

        txtCancel.setOnClickListener(view -> dialog.dismiss());

        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.show();


    }


    public void addNameApi(String name, String orderId, Dialog dialog) {
        pDialog.pdialog(mContext);
        Call<ResponseBody> call = RetrofitClient.getInstance().getApi()
                .addName(session.getAuthtoken(), name, orderId);
        call.enqueue(new retrofit2.Callback<ResponseBody>() {
            @SuppressLint("NewApi")
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull retrofit2.Response<ResponseBody> response) {

                try {

                    pDialog.hideDialog();
                    if (response.code() == 200) {
                        @SuppressLint({"NewApi", "LocalSuppress"})
                        String stresult = Objects.requireNonNull(response.body()).string();
                        Log.i("eail4254", "" + stresult);
                        JSONObject jsonObject = new JSONObject(stresult);
                        String statusCode = jsonObject.optString("status");
                        String msg = jsonObject.optString("message");
                        if (statusCode.equals("success")) {
                            dialog.dismiss();
                            getHistory();
                        } else {
                            Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();

                        }

                    } else if (response.code() == 400) {
                        @SuppressLint({"NewApi", "LocalSuppress"})
                        String result = Objects.requireNonNull(response.errorBody()).string();
                        Log.d("response400", result);
                        JSONObject jsonObject = new JSONObject(result);
                        String msg = jsonObject.optString("message");
                        Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();


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

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.llCheckOut:
                addCart();
                break;

            case R.id.btnMenu:
                Intent intent = new Intent(mContext, TabActivity.class);
                intent.putExtra("from", "");
                startActivity(intent);
                break;

            case R.id.ivProfilenew:
                Intent intentProfile = new Intent(mContext, UserProfileActivity.class);
                startActivity(intentProfile);
                break;

            case R.id.rlCart:
                Intent intentCart = new Intent(mContext, CartDetailActivity.class);
                startActivity(intentCart);
                break;
        }

    }

    private void addCart() {
        if (session.isLoggedIn()) {
            new Thread(() -> {
                List<AddOrder> getallOrder = new ArrayList<>();
                dataManager().deleteAllOrder();
                dataManager().deleteAllRecOrder();

                for (int j = 0; j < productsBeanList.size(); j++) {
                    HistoryModel.DataBean dataBean = productsBeanList.get(j);
                    for (int k = 0; k < dataBean.getProducts().size(); k++) {
                        HistoryModel.DataBean.ProductsBean productsBean = dataBean.getProducts().get(k);
                        if (productsBeanList.get(j).isSelect()) {
                            final AddOrder addOrder = new AddOrder();
                            addOrder.setId(0);
                            addOrder.setBottletype(productsBean.getBottle_type());
                            addOrder.setWatertype(productsBean.getWater_name());
                            addOrder.setBottlecon(productsBean.getBottleCondition());
                            addOrder.setNo_bottle(String.valueOf(productsBean.getQuantity()));
                            addOrder.setUser_id(session.getRegistration().getId());
                            addOrder.setUnit_type(productsBean.getUnit_type());
                            addOrder.setBottlecon(productsBean.getBottleCondition());
                            addOrder.setBottlepricenew(productsBean.getNew_bottle_price());
                            addOrder.setBottlepriceold(productsBean.getOld_bottle_price());
                            addOrder.setWater_id(productsBean.getWater_id());
                            addOrder.setBottle_id(productsBean.getBottle_id());
                            addOrder.setProduct_water_bottle_price(productsBean.getWater_price());
                            addOrder.setProduct_price(productsBean.getProduct_price());
                            getallOrder.clear();
                            if (dataBean.getRecycle().size() > 0) {
                                for (int i = 0; i < dataBean.getRecycle().size(); i++) {
                                    HistoryModel.DataBean.RecycleBean recycleBean = dataBean.getRecycle().get(i);
                                    final RecycleOrder recycleOrder = new RecycleOrder();
                                    recycleOrder.setId(Integer.parseInt(recycleBean.getRecycle_id()));
                                    recycleOrder.setRecycle_user_id(session.getRegistration().getId());
                                    recycleOrder.setRecycle_product_watertype(recycleBean.getBottle_type());
                                    recycleOrder.setRecycle_product_price(recycleBean.getPrice());
                                    recycleOrder.setUnit_type(recycleBean.getUnit_type());
                                    recycleOrder.setRecycle_product_qty(recycleBean.getQuantity());
                                    recycleOrder.setBottleprice(recycleBean.getPrice());
                                    recycleOrder.setRecycleBottleId(recycleBean.getRecycleBottleId());
                                    //final List<RecycleOrder> getallrecOrder = dataManager().loadAllRecycleProductlist(session.getRegistration().getId());
                                    dataManager().RecycleproductOrder(recycleOrder);
                                    /*if (getallrecOrder.size()>0){
                                        for (int l = 0; l <getallrecOrder.size() ; l++) {
                                            RecycleOrder recycleOrderLocal=getallrecOrder.get(l);
                                            if (!recycleOrderLocal.getRecycleBottleId().equals(recycleBean.getRecycle_id())&&dataBean.isSelect()){
                                                dataManager().RecycleproductOrder(recycleOrder);
                                            }
                                        }

                                    }else {
                                        dataManager().RecycleproductOrder(recycleOrder);
                                    }*/


                                }
                            }
                            getallOrder = dataManager().loadAllProductlist(session.getRegistration().getId());
                            dataManager().productOrder(addOrder);
                        /*    if (getallOrder.size() > 0) {
                                for (int i = 0; i < getallOrder.size(); i++) {
                                    if (!getallOrder.get(i).getBottle_id().equals(productsBean.getBottle_id()) && dataBean.isSelect()) {
                                        dataManager().productOrder(addOrder);

                                    }
                                }
                            } else {
                                dataManager().productOrder(addOrder);
                            }
*/

                            if (session.isLoggedIn()) {
                                getallOrder = dataManager().loadAllProductlist(session.getRegistration().getId());
                                List<AddOrder> finalGetallOrder = getallOrder;
                                handler.post(() -> {
                                    if (finalGetallOrder.size() > 0) {
                                        txtCount.setText(MessageFormat.format("{0}", finalGetallOrder.size()));

                                        rlCount.setVisibility(View.VISIBLE);
                                    }

                                });
                            }


                        }
                    }

                }

            }).start();


        }

    }
}






