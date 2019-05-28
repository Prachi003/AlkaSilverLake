package com.alkline.alkasilverlake.roomdatabase;

import android.content.Context;

import com.alkline.alkasilverlake.roomdatabasemodel.AddOrder;
import com.alkline.alkasilverlake.roomdatabasemodel.RecycleOrder;
import com.alkline.alkasilverlake.roomdatabasemodel.UserInfoModel;

import java.util.List;

/**
 * Created by Ravi Birla on 11,February,2019
 */
public class AppDataManager implements DataManager {
    private static AppDataManager instance;
    private final DbHelper mDbHelper;

    private AppDataManager(Context context) {
        mDbHelper = AppDbHelper.getDbInstance(context);
        //Gson mGson = new GsonBuilder().create();
    }

    public synchronized static AppDataManager getInstance(Context context) {
        if (instance == null) {
            instance = new AppDataManager(context);
        }
        return instance;
    }

    @Override
    public void productOrder(AddOrder addOrder) {
        mDbHelper.productOrder(addOrder);
    }

    @Override
    public List<AddOrder> loadAllProductlist(String userId) {
        return mDbHelper.loadAllProductlist(userId);
    }

    @Override
    public void RecycleproductOrder(RecycleOrder recycleOrder) {
        mDbHelper.RecycleproductOrder(recycleOrder);

    }

    @Override
    public List<RecycleOrder> loadAllRecycleProductlist(String userId) {
        return mDbHelper.loadAllRecycleProductlist(userId);
    }


    @Override
    public void updateOrderIdPayment(String watertype, String bottletype, String bottlecon, String qty, String price, String id, String bottlepricenew, String bottlepriceold, String waterid, String bottleid) {
        mDbHelper.updateOrderIdPayment(watertype, bottletype, bottlecon, qty, price, id, bottlepricenew, bottlepriceold, waterid, bottleid);

    }

    @Override
    public void updateRecOrderIdPayment(String watertype, String productprice, String qty, String bottleprice, String id, String rbottleid) {
        mDbHelper.updateRecOrderIdPayment(watertype, productprice, qty, bottleprice, id, rbottleid);
    }

    @Override
    public void DeleteOrderIdPayment(String id) {
        mDbHelper.DeleteOrderIdPayment(id);
    }

    @Override
    public void DeleteRecOrderIdPayment(String id) {
        mDbHelper.DeleteRecOrderIdPayment(id);

    }

    @Override
    public void updateOrderIdQty(String qty, String price, String id) {
        mDbHelper.updateOrderIdQty(qty, price, id);
    }

    @Override
    public void updaterecOrderIdQty(String qty, String price, String id) {
        mDbHelper.updaterecOrderIdQty(qty, price, id);

    }

    @Override
    public void deleteAllRecOrder() {
        mDbHelper.deleteAllRecOrder();

    }

    @Override
    public void deleteAllOrder() {
        mDbHelper.deleteAllOrder();
    }

    @Override
    public List<AddOrder> loadAllProductlistWithoutId() {
        return mDbHelper.loadAllProductlistWithoutId();
    }

    @Override
    public List<RecycleOrder> loadAllrecycleProductlistWithoutId() {
        return mDbHelper.loadAllrecycleProductlistWithoutId();
    }

    @Override
    public void deleteAddOrderItem(String userId) {
        mDbHelper.deleteAddOrderItem(userId);
    }

    @Override
    public void deleterecycleByUserId(String Recycle_User_id) {
        mDbHelper.deleterecycleByUserId(Recycle_User_id);
    }

    @Override
    public void insertUserInfo(UserInfoModel userInfoModel) {
        mDbHelper.insertUserInfo(userInfoModel);
    }

    @Override
    public UserInfoModel userinfo(String userId) {
        return mDbHelper.userinfo(userId);
    }

    @Override
    public void updateuserInfo(String fullName, String lastName, String email, String birthDate, String contact, String deliveryAddress, String deliveryCity, String deliveryCountry, String deliveryState, String deliveryZip, String deliveryLatitude, String deliveryLongitude, String billingAddress, String billingCountry, String billingState, String billingLatitude, String billingZip, String billingLongitude, int userId) {
        mDbHelper.updateuserInfo(fullName, lastName, email, birthDate, contact, deliveryAddress, deliveryCity, deliveryCountry, deliveryState, deliveryZip, deliveryLatitude, deliveryLongitude, billingAddress, billingCountry, billingState, billingLatitude, billingZip, billingLongitude, userId);
    }

    @Override
    public void deleteInfo() {
        mDbHelper.deleteInfo();
    }

    @Override
    public void updateDeliveryAddress(String billingAddress, String billingCountry, String billingState, String billingLatitude, String billingLongitude, int id) {
        mDbHelper.updateDeliveryAddress(billingAddress, billingCountry, billingState, billingLatitude, billingLongitude, id);
    }

    @Override
    public void updateBillingAddress(String billingAddress, String billingCountry, String billingCity, String billingLatitude, String billingLongitude, int userId) {
        mDbHelper.updateBillingAddress(billingAddress, billingCountry, billingCity, billingLatitude, billingLongitude, userId);
    }
}
