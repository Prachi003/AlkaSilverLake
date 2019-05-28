package com.alkline.alkasilverlake.connection;

import com.alkline.alkasilverlake.Interface.Reginterface;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {


    private static final String Base_Url = "http://www.app.alkasilverlake.com/apiv1/";
    private static RetrofitClient minstance;
    private Retrofit retrofit;

    private RetrofitClient() {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30,TimeUnit.SECONDS).build();

        retrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl(Base_Url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static synchronized RetrofitClient getInstance() {
        if (minstance == null) {
            minstance = new RetrofitClient();
        }
        return minstance;
    }

    public Reginterface getApi() {
        return retrofit.create(Reginterface.class);
    }

}
