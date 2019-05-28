package com.alkline.alkasilverlake;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Base64;

import com.alkline.alkasilverlake.activity.PickDelActivity;
import com.alkline.alkasilverlake.responsebean.UserInfoBean;
import com.google.gson.Gson;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;

public class Session {

    private static final String PREF_NAME = "imLink";
    private static final String PREF_NAME2 = "appSession";
    private static final String PREF_NAME3 = "newSession";
    private static final String IS_LOGGEDIN = "isLoggedIn";
    private static final String IS_FIrebaseLogin = "isFirebaseLogin";
    private static final String IS_UPDATE_UID = "isUpdateUid";
    private static final String RegisterInfo = "preRegistrationInfo";
    private static final String AuthToken = "authToken";

    private Context _context;
    private SharedPreferences mypref;
    private SharedPreferences mypref2;
    private SharedPreferences.Editor editor;
    private SharedPreferences.Editor editor2;

    public Session(Context context) {
        this._context = context;
        mypref = _context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        mypref2 = _context.getSharedPreferences(PREF_NAME2, Context.MODE_PRIVATE);
        SharedPreferences mypref3 = _context.getSharedPreferences(PREF_NAME3, Context.MODE_PRIVATE);
        editor = mypref.edit();
        editor2 = mypref2.edit();
        SharedPreferences.Editor editor3 = mypref3.edit();
        editor.apply();
        editor2.apply();
        editor3.apply();
    }





    public void putusertype(String type){
        editor2.putString("type",type);

        editor2.apply();
    }
    public String getusertype(){
        return mypref2.getString("type", "");
    }



    public HashMap<String, String> getHeader() {
        return new HashMap<>();
    }

    public void putuserid(String userid){
        editor2.putString("userid",userid);
        editor2.apply();
    }


    public String getuserid(){
        return mypref2.getString("userid", "");
    }



    public boolean isUpdateUid() {
        return mypref.getBoolean(IS_UPDATE_UID, false);
    }

    public void setUpdateUid(boolean isUpdate) {
        editor.putBoolean(IS_UPDATE_UID, isUpdate);
        editor.commit();
    }

    public void createSession() {
        createSession(false);
    }

    private void createSession(boolean isFirebaseLogin) {
//        Gson gson = new Gson();
//        String json = gson.toJson(user); // myObject - instance of MyObject
//        editor.putString("user", json);

        editor.putBoolean(IS_LOGGEDIN, true);
        editor.putBoolean(IS_FIrebaseLogin, isFirebaseLogin);
        // editor.putString("authToken", user.authToken);
        editor.apply();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public String getPassword(){
        // Receiving side
        byte[] data = Base64.decode(mypref.getString("pwd", null), Base64.DEFAULT);
        return new String(data, StandardCharsets.UTF_8);
    }

    public void setPassword(String pwd) {
        byte[] data = new byte[0];
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            data = pwd.getBytes(StandardCharsets.UTF_8);
        }
        String base64 = Base64.encodeToString(data, Base64.DEFAULT);
        editor.putString("pwd", base64);
        editor.commit();
    }



    public void putAuthtoken(String token){
        editor2.putString("token",token);
        editor2.apply();
    }

    public void putAddress(String adress) {
        editor2.putString("adress", adress);
        editor2.apply();
    }

    public void putLatitude(String latitude) {
        editor2.putString("latitude", latitude);
        editor2.apply();
    }

    public void putLongitude(String longitude) {
        editor2.putString("longitude", longitude);
        editor2.apply();
    }


    public void putMinDistance(String min_distance) {
        editor2.putString("min_distance", min_distance);
        editor2.apply();
    }

    public void putMinCharge(String min_charge) {
        editor2.putString("min_charge", min_charge);
        editor2.apply();
    }


    public void putExtraMileCharge(String extra_mile_charge) {
        editor2.putString("extra_mile_charge", extra_mile_charge);
        editor2.apply();
    }

    public void putDistance(String distance) {
        editor2.putString("distance", distance);
        editor2.apply();
    }

    public void putMiles(String miles) {
        editor2.putString("miles", miles);
        editor2.apply();
    }

    public void putDeLiveryFee(String deliveryfee) {
        editor2.putString("deliveryfee", deliveryfee);
        editor2.apply();
    }


    public String getMinDistance() {
        return mypref2.getString("min_distance", "");
    }


    public String getMiles() {
        return mypref2.getString("miles", "");
    }

    public String getDistance() {
        return mypref2.getString("distance", "");
    }

    public String getDeliveryFee() {
        return mypref2.getString("deliveryfee", "");
    }


    public String getMinCharge() {
        return mypref2.getString("min_charge", "");
    }

    public String getExtraMileCharge() {
        return mypref2.getString("extra_mile_charge", "");
    }


    public String getAuthtoken() {
        return mypref2.getString("token", "");
    }


    public String getAddress() {
        return mypref2.getString("adress", "");
    }

    public String getLatitude() {
        return mypref2.getString("latitude", "");
    }

    public String getLongitude() {
        return mypref2.getString("longitude", "");
    }



    public void createRegistration(UserInfoBean.UserDetailBean registrationInfo) {
        Gson gson = new Gson();
        String json = gson.toJson(registrationInfo);
        editor.putString(RegisterInfo, json);
        editor.putString(AuthToken, registrationInfo.getAuthToken());
        editor.commit();
    }
    public UserInfoBean.UserDetailBean getRegistration() {
        Gson gson = new Gson();
        String string = mypref.getString(RegisterInfo, "");
        if (!string.isEmpty())
            return gson.fromJson(string, UserInfoBean.UserDetailBean.class);
        else return null;
    }




    public String getUpdatedinDb(){
        return mypref.getString("IsUpdated", "");
    }

    public boolean getIsOutCallFilter() {
        return mypref.getBoolean("outcall",false);
    }

    public void setIsOutCallFilter(boolean value){
        editor.putBoolean("outcall", value);
        this.editor.commit();
    }

    public String getUserChangedLocLat() {
        return mypref.getString("lat", "");
    }


    public void setUserChangedLocLat(String lat){
        editor.putString("lat", lat);
        this.editor.commit();
    }

    public String getUserChangedLocLng() {
        return mypref.getString("lng", "");
    }


    public void setUserChangedLocLng(String lng){
        editor.putString("lng", lng);
        this.editor.commit();
    }

    public String getUserChangedLocName() {
        return mypref.getString("locName", "");
    }

    public void setUserChangedLocName(String locName){
        editor.putString("locName", locName);
        this.editor.commit();
    }

    public boolean getIsFirebaseLogin() {
        return mypref.getBoolean(IS_FIrebaseLogin, false);
    }



    public void logout() {
        editor.clear();
        editor.apply();

        Intent showLogin = new Intent(_context, PickDelActivity.class);
        showLogin.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        showLogin.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        _context.startActivity(showLogin);
    }

    public boolean isLoggedIn() {
        return mypref.getBoolean(IS_LOGGEDIN, false);
    }

    public void issetLoggedIn(boolean value)
    {
        editor.putBoolean(IS_LOGGEDIN, true);
        this.editor.apply();
    }

}
