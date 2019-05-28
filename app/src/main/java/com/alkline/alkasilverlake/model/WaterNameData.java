package com.alkline.alkasilverlake.model;

import android.support.annotation.NonNull;

/**
 * Created by Ravi Birla on 09,February,2019
 */
public class WaterNameData {



    String waterId;
    String water_name;
    String water_price;
    String water_image;
    String status;
    String crd;
    String upd;

    public WaterNameData() {
    }

    public WaterNameData(String waterId, String water_name, String water_price, String water_image, String status, String crd, String upd) {
        this.waterId = waterId;
        this.water_name = water_name;
        this.water_price = water_price;
        this.water_image = water_image;
        this.status = status;
        this.crd = crd;
        this.upd = upd;
    }

    public String getWaterId() {
        return waterId;
    }

    public void setWaterId(String waterId) {
        this.waterId = waterId;
    }

    public String getWater_name() {
        return water_name;
    }

    @NonNull
    @Override
    public String toString() {
        return water_name;
    }

    public void setWater_name(String water_name) {
        this.water_name = water_name;
    }

    public String getWater_price() {
        return water_price;
    }

    public void setWater_price(String water_price) {
        this.water_price = water_price;
    }

    public String getWater_image() {
        return water_image;
    }

    public void setWater_image(String water_image) {
        this.water_image = water_image;
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

    public String getUpd() {
        return upd;
    }

    public void setUpd(String upd) {
        this.upd = upd;
    }


}
