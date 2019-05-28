package com.alkline.alkasilverlake.roomdatabase;

import com.alkline.alkasilverlake.roomdatabasemodel.AddOrder;
import com.alkline.alkasilverlake.roomdatabasemodel.RecycleOrder;
import com.alkline.alkasilverlake.roomdatabasemodel.UserInfoModel;

import java.util.List;

/**
 * Created by Ravi Birla on 11,February,2019
 */
public interface DbHelper {


    //Delivery request Database
    void productOrder(AddOrder addOrder);

    List<AddOrder> loadAllProductlist(String userId);




    //Recycle Database

    void RecycleproductOrder(RecycleOrder recycleOrder);
    List<RecycleOrder> loadAllRecycleProductlist(String userId);
    void updateOrderIdPayment(String watertype, String bottletype, String bottlecon, String qty, String price, String id, String bottlepricenew, String bottlepriceold, String waterid, String bottleid);
    void updateRecOrderIdPayment(String watertype, String productprice, String qty, String bottleprice, String id, String rbottleid);
    void DeleteOrderIdPayment(String id);
    void DeleteRecOrderIdPayment(String id);
    void updateOrderIdQty(String qty, String price, String id);
    void updaterecOrderIdQty(String qty, String price, String id);
    void deleteAllRecOrder();



    void deleteAllOrder();


    List<AddOrder> loadAllProductlistWithoutId();

    List<RecycleOrder> loadAllrecycleProductlistWithoutId();

    void deleteAddOrderItem(String User_id);

    void deleterecycleByUserId(String Recycle_User_id);

    /*Insert userInfo*/

    void insertUserInfo(UserInfoModel userInfoModel);

    /*Get user Info*/

    UserInfoModel userinfo(String userId);


    /*Update UserInfo*/

    void updateuserInfo(String fullName, String lastName, String email, String birthDate,
                        String contact, String deliveryAddress, String deliveryCity
            , String deliveryCountry, String deliveryState
            , String deliveryZip, String deliveryLatitude
            , String deliveryLongitude, String billingAddress, String billingCountry, String billingState, String billingLatitude, String billingZip, String billingLongitude, int userId);


    void deleteInfo();


    /*Update lat long*/
    void updateDeliveryAddress(String billingAddress, String billingCountry, String billingState, String billingLatitude, String billingLongitude, int id);

    void updateBillingAddress(String billingAddress, String billingCountry, String billingCity, String billingLatitude, String billingLongitude, int userId);





}
