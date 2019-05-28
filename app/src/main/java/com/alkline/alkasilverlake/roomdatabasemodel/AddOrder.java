package com.alkline.alkasilverlake.roomdatabasemodel;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by Ravi Birla on 08,February,2019
 */
@Entity(tableName = "Add_Product")
public class AddOrder {

    @PrimaryKey(autoGenerate = true)
    private  int  id;

    @ColumnInfo(name = "water_id")
    private String water_id;

    @ColumnInfo(name = "bottle_id")
    private String bottle_id;

    @ColumnInfo(name = "User_id")
    private String User_id;

    @ColumnInfo(name = "Product_Watertype")
    private String watertype;

    @ColumnInfo(name = "Product_Bottletype")
    private String bottletype;

    @ColumnInfo(name = "Product_bottlecon")
    private String bottlecon;

    @ColumnInfo(name = "Product_No_bottle")
    private String no_bottle;


    @ColumnInfo(name = "unit_type")
    private String unit_type;

    public String getUnit_type() {
        return unit_type;
    }

    public void setUnit_type(String unit_type) {
        this.unit_type = unit_type;
    }


    @ColumnInfo(name = "Product_price")
    private String product_price;

    @ColumnInfo(name = "Product_water_bottle_price")
    private String product_water_bottle_price;

    @ColumnInfo(name = "Product_Bottlepricenew")
    private String Bottlepricenew;

    @ColumnInfo(name = "Product_Bottlepriceold")
    private String Bottlepriceold;


    public String getWater_id() {
        return water_id;
    }

    public void setWater_id(String water_id) {
        this.water_id = water_id;
    }

    public String getBottle_id() {
        return bottle_id;
    }

    public void setBottle_id(String bottle_id) {
        this.bottle_id = bottle_id;
    }

    public String getProduct_water_bottle_price() {
        return product_water_bottle_price;
    }

    public void setProduct_water_bottle_price(String product_water_bottle_price) {
        this.product_water_bottle_price = product_water_bottle_price;
    }

    public String getBottlepriceold() {
        return Bottlepriceold;
    }

    public void setBottlepriceold(String bottlepriceold) {
        Bottlepriceold = bottlepriceold;
    }


    public String getBottlepricenew() {
        return Bottlepricenew;
    }

    public void setBottlepricenew(String bottlepricenew) {
        Bottlepricenew = bottlepricenew;
    }

    public String getProduct_price() {
        return product_price;
    }

    public void setProduct_price(String product_price) {
        this.product_price = product_price;
    }

    public String getUser_id() {
        return User_id;
    }

    public void setUser_id(String user_id) {
        User_id = user_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWatertype() {
        return watertype;
    }

    public void setWatertype(String watertype) {
        this.watertype = watertype;
    }

    public String getBottletype() {
        return bottletype;
    }

    public void setBottletype(String bottletype) {
        this.bottletype = bottletype;
    }

    public String getBottlecon() {
        return bottlecon;
    }

    public void setBottlecon(String bottlecon) {
        this.bottlecon = bottlecon;
    }

    public String getNo_bottle() {
        return no_bottle;
    }

    public void setNo_bottle(String no_bottle) {
        this.no_bottle = no_bottle;
    }
}
