package com.alkline.alkasilverlake.roomdatabasemodel;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by Ravi Birla on 12,February,2019
 */
@Entity(tableName = "Recycle_Product")
public class RecycleOrder {


    @PrimaryKey(autoGenerate = true)
    private  int  id;

    @ColumnInfo(name = "Recycle_User_id")
    private String recycle_user_id;

    @ColumnInfo(name = "recycleBottleId")
    private String recycleBottleId;

    @ColumnInfo(name = "Recycle_Product_Watertype")
    private String recycle_product_watertype;

    @ColumnInfo(name = "Recycle_Product_Price")
    private String recycle_product_price;


    @ColumnInfo(name = "Recycle_Product_qty")
    private String recycle_product_qty;

    @ColumnInfo(name = "unit_type")
    private String unit_type;

    public String getUnit_type() {
        return unit_type;
    }

    public void setUnit_type(String unit_type) {
        this.unit_type = unit_type;
    }



    @ColumnInfo(name = "Recycle_bottle_price")
    private String bottleprice;


    public String getRecycleBottleId() {
        return recycleBottleId;
    }

    public void setRecycleBottleId(String recycleBottleId) {
        this.recycleBottleId = recycleBottleId;
    }

    public String getBottleprice() {
        return bottleprice;
    }

    public void setBottleprice(String bottleprice) {
        this.bottleprice = bottleprice;
    }

    public String getRecycle_product_qty() {
        return recycle_product_qty;
    }

    public void setRecycle_product_qty(String recycle_product_qty) {
        this.recycle_product_qty = recycle_product_qty;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRecycle_user_id() {
        return recycle_user_id;
    }

    public void setRecycle_user_id(String recycle_user_id) {
        this.recycle_user_id = recycle_user_id;
    }

    public String getRecycle_product_watertype() {
        return recycle_product_watertype;
    }

    public void setRecycle_product_watertype(String recycle_product_watertype) {
        this.recycle_product_watertype = recycle_product_watertype;
    }

    public String getRecycle_product_price() {
        return recycle_product_price;
    }

    public void setRecycle_product_price(String recycle_product_price) {
        this.recycle_product_price = recycle_product_price;
    }
}
