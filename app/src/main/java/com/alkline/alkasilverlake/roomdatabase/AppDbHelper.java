package com.alkline.alkasilverlake.roomdatabase;

import android.content.Context;

import com.alkline.alkasilverlake.roomdatabasemodel.AddOrder;
import com.alkline.alkasilverlake.roomdatabasemodel.RecycleOrder;
import com.alkline.alkasilverlake.roomdatabasemodel.UserInfoModel;

import java.util.List;

/**
 * Created by Ravi Birla on 11,February,2019
 */
public class AppDbHelper implements DbHelper {

    private static AppDbHelper instance;

    private final MyAppDatabase myAppDatabase;

    private AppDbHelper(Context context) {
        this.myAppDatabase = MyAppDatabase.getDatabaseInstance(context);
    }

    synchronized static DbHelper getDbInstance(Context context) {
        if (instance == null) {
            instance = new AppDbHelper(context);
        }
        return instance;
    }

    @Override
    public void productOrder(AddOrder addOrder) {
        myAppDatabase.myDao().productOrder(addOrder);
    }

    @Override
    public List<AddOrder> loadAllProductlist(String userId) {
        return myAppDatabase.myDao().loadAllProductlist(userId);
    }

    @Override
    public void RecycleproductOrder(RecycleOrder recycleOrder) {
        myAppDatabase.myRecDao().RecycleproductOrder(recycleOrder);
    }

    @Override
    public List<RecycleOrder> loadAllRecycleProductlist(String userId) {
        return myAppDatabase.myRecDao().loadAllrecycleProductlist(userId);
    }


    @Override
    public void updateOrderIdPayment(String watertype, String bottletype, String bottlecon, String qty, String price, String id, String bottlepricenew, String bottlepriceold, String waterid, String bottleid) {
        myAppDatabase.myDao().updateOrderIdPayment(watertype, bottletype, bottlecon, qty, price, id, bottlepricenew, bottlepriceold, waterid, bottleid);
    }

    @Override
    public void updateRecOrderIdPayment(String watertype, String productprice, String qty, String bottleprice, String id, String rbottleid) {
        myAppDatabase.myRecDao().updateRecOrderIdPayment(watertype, productprice, qty, bottleprice, id, rbottleid);

    }

    @Override
    public void DeleteOrderIdPayment(String id) {
        myAppDatabase.myDao().DeleteOrderIdPayment(id);

    }

    @Override
    public void DeleteRecOrderIdPayment(String id) {
        myAppDatabase.myRecDao().DeleteRecOrderIdPayment(id);

    }

    @Override
    public void updateOrderIdQty(String qty, String price, String id) {
        myAppDatabase.myDao().updateOrderIdQty(qty, price, id);

    }

    @Override
    public void updaterecOrderIdQty(String qty, String price, String id) {
        myAppDatabase.myRecDao().updaterecOrderIdQty(qty, price, id);

    }

    @Override
    public void deleteAllRecOrder() {
        myAppDatabase.myRecDao().deleteAllRecOrder();
    }

    @Override
    public void deleteAllOrder() {
        myAppDatabase.myDao().deleteAllOrder();

    }

    @Override
    public List<AddOrder> loadAllProductlistWithoutId() {
        return myAppDatabase.myDao().loadAllProductlistWithoutId();
    }

    @Override
    public List<RecycleOrder> loadAllrecycleProductlistWithoutId() {
        return myAppDatabase.myRecDao().loadAllrecycleProductlistWithoutId();
    }

    @Override
    public void deleteAddOrderItem(String userId) {
        myAppDatabase.myDao().deleteAddOrderItem(userId);
    }

    @Override
    public void deleterecycleByUserId(String Recycle_User_id) {
        myAppDatabase.myRecDao().deleterecycleByUserId(Recycle_User_id);
    }

    @Override
    public void insertUserInfo(UserInfoModel userInfoModel) {
        myAppDatabase.userInfoDao().insertUserInfo(userInfoModel);
    }

    @Override
    public UserInfoModel userinfo(String userId) {
        return myAppDatabase.userInfoDao().userinfo(userId);
    }

    @Override
    public void updateuserInfo(String fullName, String lastName, String email, String birthDate, String contact, String deliveryAddress, String deliveryCity, String deliveryCountry, String deliveryState, String deliveryZip, String deliveryLatitude, String deliveryLongitude, String billingAddress, String billingCountry, String billingState, String billingLatitude, String billingZip, String billingLongitude, int userId) {
        myAppDatabase.userInfoDao().updateuserInfo(fullName, lastName, email, birthDate, contact, deliveryAddress, deliveryCity, deliveryCountry, deliveryState, deliveryZip, deliveryLatitude, deliveryLongitude, billingAddress, billingCountry, billingState, billingLatitude, billingZip, billingLongitude, userId);
    }

    @Override
    public void deleteInfo() {
        myAppDatabase.userInfoDao().deleteInfo();
    }

    @Override
    public void updateDeliveryAddress(String billingAddress, String billingCountry, String billingState, String billingLatitude, String billingLongitude, int id) {
        myAppDatabase.userInfoDao().updateDeliveryAddress(billingAddress, billingCountry, billingState, billingLatitude, billingLongitude, id);
    }

    @Override
    public void updateBillingAddress(String billingAddress, String billingCountry, String billingCity, String billingLatitude, String billingLongitude, int userId) {
        myAppDatabase.userInfoDao().updateBillingAddress(billingAddress, billingCountry, billingCity, billingLatitude, billingLongitude, userId);
    }


}
