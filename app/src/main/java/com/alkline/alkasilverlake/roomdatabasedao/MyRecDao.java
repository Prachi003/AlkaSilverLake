package com.alkline.alkasilverlake.roomdatabasedao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.alkline.alkasilverlake.roomdatabasemodel.RecycleOrder;

import java.util.List;

/**
 * Created by Ravi Birla on 08,February,2019
 */
@Dao
public interface MyRecDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void RecycleproductOrder(RecycleOrder recycleOrder);

    @Query("SELECT * FROM Recycle_Product WHERE Recycle_User_id=:Recycle_User_id")
    List<RecycleOrder> loadAllrecycleProductlist(String Recycle_User_id);

    @Query("SELECT * FROM Recycle_Product")
    List<RecycleOrder> loadAllrecycleProductlistWithoutId();


    @Query("UPDATE Recycle_Product SET Recycle_Product_Watertype =:watertype , Recycle_Product_Price =:productprice , Recycle_Product_qty =:qty , Recycle_bottle_price =:bottleprice ,recycleBottleId=:rbottleid WHERE id=:id")
    void updateRecOrderIdPayment(String watertype, String productprice, String qty, String bottleprice, String id, String rbottleid);


    @Query("DELETE FROM Recycle_Product WHERE id=:id")
    void DeleteRecOrderIdPayment(String id);

    @Query("DELETE FROM Recycle_Product WHERE Recycle_User_id=:Recycle_User_id")
    void deleterecycleByUserId(String Recycle_User_id);


    @Query("UPDATE Recycle_Product SET  Recycle_Product_qty =:qty, Recycle_Product_Price =:price   WHERE id=:id")
    void updaterecOrderIdQty(String qty, String price, String id);


    @Query("DELETE FROM Recycle_Product")
    void deleteAllRecOrder();
}
