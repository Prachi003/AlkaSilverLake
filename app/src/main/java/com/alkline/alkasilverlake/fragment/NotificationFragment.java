package com.alkline.alkasilverlake.fragment;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alkline.alkasilverlake.R;
import com.alkline.alkasilverlake.Session;
import com.alkline.alkasilverlake.activity.CartDetailActivity;
import com.alkline.alkasilverlake.activity.TabActivity;
import com.alkline.alkasilverlake.adapter.NotificationAdapter;
import com.alkline.alkasilverlake.base.BaseFragment;
import com.alkline.alkasilverlake.connection.RetrofitClient;
import com.alkline.alkasilverlake.helper.PDialog;
import com.alkline.alkasilverlake.model.NotificationModel;
import com.alkline.alkasilverlake.profile.UserProfileActivity;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import okhttp3.ResponseBody;
import retrofit2.Call;

/**
 * A simple {@link Fragment} subclass.
 */
public class NotificationFragment extends BaseFragment implements View.OnClickListener {
    private PDialog pDialog;
    private List<NotificationModel.DataBean> dataBeanList = new ArrayList<>();
    private NotificationAdapter notificationAdapter;
    private TextView txtNoNotification;
    private Session session;


    public NotificationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notification, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        pDialog = new PDialog();
        session = new Session(mContext);
        RecyclerView recyclerNotification = view.findViewById(R.id.recyclerNotification);
        RelativeLayout rlCart = view.findViewById(R.id.rlCart);
        ImageView ivProfilenew = view.findViewById(R.id.ivProfilenew);
        Button btnMenu = view.findViewById(R.id.btnMenu);
        txtNoNotification = view.findViewById(R.id.txtNoNotification);
        recyclerNotification.setLayoutManager(new LinearLayoutManager(mContext));
        notificationAdapter = new NotificationAdapter(mContext, dataBeanList);
        recyclerNotification.setAdapter(notificationAdapter);
        rlCart.setOnClickListener(this);
        ivProfilenew.setOnClickListener(this);
        btnMenu.setOnClickListener(this);
        getNotification();


    }

    public void getNotification() {
        pDialog.pdialog(mContext);
        Call<ResponseBody> call = RetrofitClient.getInstance().getApi()
                .getNotification(session.getAuthtoken());

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
                        if (statusCode.equals("success")) {
                            Gson gson = new Gson();
                            JSONArray jsonArray = jsonObject.getJSONArray("data");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObjectDate = jsonArray.getJSONObject(i);
                                NotificationModel.DataBean dataBean = gson.fromJson(jsonObjectDate.toString(), NotificationModel.DataBean.class);
                                dataBeanList.add(dataBean);
                            }
                        }

                        if (dataBeanList.size() == 0) {
                            txtNoNotification.setVisibility(View.VISIBLE);
                        } else {
                            txtNoNotification.setVisibility(View.GONE);
                        }
                        notificationAdapter.notifyDataSetChanged();

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
            case R.id.rlCart:
                Intent intent = new Intent(mContext, CartDetailActivity.class);
                startActivity(intent);
                break;

            case R.id.ivProfilenew:
                Intent intent1 = new Intent(mContext, UserProfileActivity.class);
                startActivity(intent1);
                break;

            case R.id.btnMenu:
                Intent intent2 = new Intent(mContext, TabActivity.class);
                intent2.putExtra("from", "");
                startActivity(intent2);
                break;


        }
    }
}
