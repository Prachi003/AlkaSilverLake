package com.alkline.alkasilverlake.model;

import android.support.annotation.NonNull;

/**
 * Created by Ravi Birla on 09,February,2019
 */
public class BottleData {

    String bottleId;
    String bottle_name;
    String bottle_image;
    String unit_type;
    String new_bottle_price;
    String old_bottle_price;
    String bottle_type;
    String status;
    String crd;
    String upd;
    String bottles_type;


    public BottleData(String bottleId, String bottle_name, String bottle_image, String unit_type, String new_bottle_price, String old_bottle_price, String bottle_type, String status, String crd, String upd, String bottles_type) {
        this.bottleId = bottleId;
        this.bottle_name = bottle_name;
        this.bottle_image = bottle_image;
        this.unit_type = unit_type;
        this.new_bottle_price = new_bottle_price;
        this.old_bottle_price = old_bottle_price;
        this.bottle_type = bottle_type;
        this.status = status;
        this.crd = crd;
        this.upd = upd;
        this.bottles_type = bottles_type;
    }

    public String getBottleId() {
        return bottleId;
    }

    public void setBottleId(String bottleId) {
        this.bottleId = bottleId;
    }

    public String getBottle_name() {
        return bottle_name;
    }

    public void setBottle_name(String bottle_name) {
        this.bottle_name = bottle_name;
    }

    public String getBottle_image() {
        return bottle_image;
    }

    public void setBottle_image(String bottle_image) {
        this.bottle_image = bottle_image;
    }

    public String getUnit_type() {
        return unit_type;
    }

    public void setUnit_type(String unit_type) {
        this.unit_type = unit_type;
    }

    public String getNew_bottle_price() {
        return new_bottle_price;
    }

    public void setNew_bottle_price(String new_bottle_price) {
        this.new_bottle_price = new_bottle_price;
    }

    public String getOld_bottle_price() {
        return old_bottle_price;
    }

    public void setOld_bottle_price(String old_bottle_price) {
        this.old_bottle_price = old_bottle_price;
    }

    public String getBottle_type() {
        return bottle_type;
    }

    public void setBottle_type(String bottle_type) {
        this.bottle_type = bottle_type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCrd() {
        return crd;
    }

    public void setCrd(String crd) {
        this.crd = crd;
    }

    @NonNull
    @Override
    public String toString() {
        return bottles_type;
    }

    public String getUpd() {
        return upd;
    }

    public void setUpd(String upd) {
        this.upd = upd;
    }

    public String getBottles_type() {
        return bottles_type;
    }

    public void setBottles_type(String bottles_type) {
        this.bottles_type = bottles_type;
    }
}
