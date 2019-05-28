package com.alkline.alkasilverlake.roomdatabasedao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.alkline.alkasilverlake.roomdatabasemodel.UserInfoModel;

@Dao
public interface UserInfoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertUserInfo(UserInfoModel userInfoModel);


    @Query("SELECT * FROM userInfo WHERE userId=:userId")
    UserInfoModel userinfo(String userId);

    @Query("DELETE  FROM userInfo")
    void deleteInfo();



    @Query("UPDATE userInfo  SET  fullName=:fullName , lastName =:lastName , email =:email ," +
            " birthDate =:birthDate , contact =:contact , " +
            "deliveryAddress =:deliveryAddress ," +
            " deliveryCity=:deliveryCity ,deliveryCountry=:deliveryCountry," +
            "deliveryState=:deliveryState,deliveryZip=:deliveryZip," +
            "deliveryLatitude=:deliveryLatitude,deliveryLongitude=:deliveryLongitude," +
            "billingAddress =:billingAddress , billingCountry=:billingCountry ," +
            "billingState=:billingState,billingLatitude=:billingLatitude,billingZip=:billingZip," +
            "billingLongitude=:billingLongitude WHERE userId=:userId")
    void updateuserInfo(String fullName, String lastName, String email, String birthDate,
                              String contact, String deliveryAddress, String deliveryCity
            , String deliveryCountry, String deliveryState
            ,String deliveryZip,String deliveryLatitude
            ,String deliveryLongitude,String billingAddress,String billingCountry,String billingState,String billingLatitude,String billingZip,String billingLongitude,int userId);


    @Query("UPDATE userInfo SET deliveryAddress=:deliveryAddress,deliveryCountry=:deliveryCountry,deliveryState=:deliveryState,deliveryLatitude=:deliveryLatitude,deliveryLongitude=:deliveryLongitude WHERE userId=:userId")
    void updateDeliveryAddress(String deliveryAddress,String deliveryCountry,String deliveryState,String deliveryLatitude,String deliveryLongitude,int userId);

    @Query("UPDATE userInfo SET billingAddress=:billingAddress,billingCountry=:billingCountry,billingCity=:billingCity,billingLatitude=:billingLatitude,billingLongitude=:billingLongitude WHERE userId=:userId")
    void updateBillingAddress(String billingAddress,String billingCountry,String billingCity,String billingLatitude,String billingLongitude,int userId);


}
