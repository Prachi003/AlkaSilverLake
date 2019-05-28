package com.alkline.alkasilverlake.roomdatabasedao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.alkline.alkasilverlake.roomdatabasemodel.AddOrder;

import java.util.List;

/**
 * Created by Ravi Birla on 08,February,2019
 */
@Dao
public interface MyDao {

     @Insert(onConflict = OnConflictStrategy.REPLACE)
      void productOrder(AddOrder addOrder);



    @Query("SELECT * FROM Add_Product WHERE User_id =:User_id")
    List<AddOrder> loadAllProductlist(String User_id);

    @Query("SELECT * FROM Add_Product")
    List<AddOrder> loadAllProductlistWithoutId();




    @Query("UPDATE Add_Product SET Product_Watertype =:watertype , Product_Bottletype =:bottletype , Product_bottlecon =:bottlecon , Product_No_bottle =:qty , Product_price =:price , Product_Bottlepricenew =:bottlepricenew , Product_Bottlepriceold=:bottlepriceold ,water_id=:waterid,bottle_id=:bottleid WHERE id=:id")
    void updateOrderIdPayment(String watertype, String bottletype, String bottlecon, String qty, String price, String id, String bottlepricenew, String bottlepriceold, String waterid, String bottleid);


    @Query("DELETE FROM Add_Product WHERE id=:id")
    void DeleteOrderIdPayment(String id);


    @Query("DELETE FROM Add_Product WHERE User_id=:User_id")
    void deleteAddOrderItem(String User_id);






    @Query("UPDATE Add_Product SET  Product_No_bottle =:qty, Product_price =:price   WHERE id=:id")
    void updateOrderIdQty(String qty, String price, String id);

    @Query("DELETE FROM Add_Product")
    void deleteAllOrder();


}
