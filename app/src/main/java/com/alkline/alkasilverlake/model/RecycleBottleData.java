package com.alkline.alkasilverlake.model;

/**
 * Created by Ravi Birla on 09,February,2019
 */
public class RecycleBottleData {



    String recycleBottleId;
    String recycle_bottle_name;
    String recycle_bottle_image ;
    String unit_type;
    String price;
    String bottle_type;
    String status;
    String crd;
    String upd;
    String recycle_type;



    public RecycleBottleData(String recycleBottleId, String recycle_bottle_name, String recycle_bottle_image, String unit_type, String price, String bottle_type, String status, String crd, String upd, String recycle_type) {
        this.recycleBottleId = recycleBottleId;
        this.recycle_bottle_name = recycle_bottle_name;
        this.recycle_bottle_image = recycle_bottle_image;
        this.unit_type = unit_type;
        this.price = price;
        this.bottle_type = bottle_type;
        this.status = status;
        this.crd = crd;
        this.upd = upd;
        this.recycle_type = recycle_type;
    }

    public String getRecycle_type() {
        return recycle_type;
    }

    public void setRecycle_type(String recycle_type) {
        this.recycle_type = recycle_type;
    }

    public String getRecycleBottleId() {
        return recycleBottleId;
    }

    public void setRecycleBottleId(String recycleBottleId) {
        this.recycleBottleId = recycleBottleId;
    }

    public String getRecycle_bottle_name() {
        return recycle_bottle_name;
    }

    public void setRecycle_bottle_name(String recycle_bottle_name) {
        this.recycle_bottle_name = recycle_bottle_name;
    }

    public String getRecycle_bottle_image() {
        return recycle_bottle_image;
    }

    public void setRecycle_bottle_image(String recycle_bottle_image) {
        this.recycle_bottle_image = recycle_bottle_image;
    }

    public String getUnit_type() {
        return unit_type;
    }

    public void setUnit_type(String unit_type) {
        this.unit_type = unit_type;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
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

    @Override
    public String toString() {
        return recycle_type;
    }

    public String getUpd() {
        return upd;
    }

    public void setUpd(String upd) {
        this.upd = upd;
    }


}
