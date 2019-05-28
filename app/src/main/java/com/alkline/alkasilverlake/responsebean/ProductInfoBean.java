package com.alkline.alkasilverlake.responsebean;

import java.util.List;

public class ProductInfoBean {


    /**
     * status : success
     * pickupAddress : {"address":"4327 Sunset Blvd. Los Angeles, CA 90029 ","lattitude":"34.095735","longitude":"-118.282898"}
     * delivery_rates : [{"officeChargesId":"1","min_distance":"5.00","min_charge":"5.00","extra_mile_charge":"1.00"}]
     * water : [{"waterId":"1","water_name":"Purified","water_price":"14.00","water_image":"","status":"1","crd":"2018-11-14 21:35:48","upd":"2019-02-19 04:24:30"},{"waterId":"2","water_name":"Alkaline","water_price":"13.00","water_image":"","status":"1","crd":"2018-11-16 05:44:34","upd":"2019-02-19 04:24:12"},{"waterId":"3","water_name":"NONE","water_price":"0.00","water_image":"","status":"1","crd":"2019-02-12 23:21:26","upd":"2019-02-12 23:21:41"}]
     * bottle : [{"bottleId":"11","bottle_name":"glass","bottle_image":"","unit_type":"5","new_bottle_price":"2.00","old_bottle_price":"1.00","bottle_type":"Glass","status":"1","crd":"2018-12-20 22:25:37","upd":"2019-02-10 21:43:09","bottles_type":"5 Gallon Glass"},{"bottleId":"12","bottle_name":"glass","bottle_image":"","unit_type":"3","new_bottle_price":"4.00","old_bottle_price":"3.00","bottle_type":"Glass","status":"1","crd":"2018-12-20 22:25:50","upd":"2019-02-10 21:43:16","bottles_type":"3 Gallon Glass"},{"bottleId":"13","bottle_name":"plastic","bottle_image":"","unit_type":"5","new_bottle_price":"6.00","old_bottle_price":"5.00","bottle_type":"Plastic","status":"1","crd":"2018-12-20 22:26:05","upd":"2019-02-10 21:43:25","bottles_type":"5 Gallon Plastic"},{"bottleId":"14","bottle_name":"plastic","bottle_image":"tu9G4wFr7yYa30kV.jpg","unit_type":"3","new_bottle_price":"8.00","old_bottle_price":"7.00","bottle_type":"Glass Bottle","status":"1","crd":"2018-12-20 22:26:22","upd":"2019-04-04 06:17:49","bottles_type":"3 Gallon Glass Bottle"}]
     * recycle_bottle : [{"recycleBottleId":"1","recycle_bottle_name":"plastic","recycle_bottle_image":"","unit_type":"5","price":"9.00","bottle_type":"Plastic","status":"1","crd":"2019-02-06 01:27:51","upd":"2019-02-10 22:07:11","recycle_type":"5 Gallon Plastic"},{"recycleBottleId":"4","recycle_bottle_name":"plastic","recycle_bottle_image":"","unit_type":"3","price":"10.00","bottle_type":"Plastic","status":"1","crd":"2019-02-06 21:05:00","upd":"2019-02-10 22:07:16","recycle_type":"3 Gallon Plastic"},{"recycleBottleId":"5","recycle_bottle_name":"glass","recycle_bottle_image":"","unit_type":"3","price":"11.00","bottle_type":"Glass","status":"1","crd":"2019-02-07 23:10:39","upd":"2019-02-10 22:07:20","recycle_type":"3 Gallon Glass"},{"recycleBottleId":"6","recycle_bottle_name":"glass","recycle_bottle_image":"","unit_type":"5","price":"12.00","bottle_type":"Glass","status":"1","crd":"2019-02-07 23:11:02","upd":"2019-02-13 01:53:13","recycle_type":"5 Gallon Glass"}]
     */

    private String status;
    private PickupAddressBean pickupAddress;
    private List<DeliveryRatesBean> delivery_rates;
    private List<WaterBean> water;
    private List<BottleBean> bottle;
    private List<RecycleBottleBean> recycle_bottle;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public PickupAddressBean getPickupAddress() {
        return pickupAddress;
    }

    public void setPickupAddress(PickupAddressBean pickupAddress) {
        this.pickupAddress = pickupAddress;
    }

    public List<DeliveryRatesBean> getDelivery_rates() {
        return delivery_rates;
    }

    public void setDelivery_rates(List<DeliveryRatesBean> delivery_rates) {
        this.delivery_rates = delivery_rates;
    }

    public List<WaterBean> getWater() {
        return water;
    }

    public void setWater(List<WaterBean> water) {
        this.water = water;
    }

    public List<BottleBean> getBottle() {
        return bottle;
    }

    public void setBottle(List<BottleBean> bottle) {
        this.bottle = bottle;
    }

    public List<RecycleBottleBean> getRecycle_bottle() {
        return recycle_bottle;
    }

    public void setRecycle_bottle(List<RecycleBottleBean> recycle_bottle) {
        this.recycle_bottle = recycle_bottle;
    }

    public static class PickupAddressBean {
        /**
         * address : 4327 Sunset Blvd. Los Angeles, CA 90029
         * lattitude : 34.095735
         * longitude : -118.282898
         */

        private String address;
        private String lattitude;
        private String longitude;

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getLattitude() {
            return lattitude;
        }

        public void setLattitude(String lattitude) {
            this.lattitude = lattitude;
        }

        public String getLongitude() {
            return longitude;
        }

        public void setLongitude(String longitude) {
            this.longitude = longitude;
        }
    }

    public static class DeliveryRatesBean {
        /**
         * officeChargesId : 1
         * min_distance : 5.00
         * min_charge : 5.00
         * extra_mile_charge : 1.00
         */

        private String officeChargesId;
        private String min_distance;
        private String min_charge;
        private String extra_mile_charge;

        public String getOfficeChargesId() {
            return officeChargesId;
        }

        public void setOfficeChargesId(String officeChargesId) {
            this.officeChargesId = officeChargesId;
        }

        public String getMin_distance() {
            return min_distance;
        }

        public void setMin_distance(String min_distance) {
            this.min_distance = min_distance;
        }

        public String getMin_charge() {
            return min_charge;
        }

        public void setMin_charge(String min_charge) {
            this.min_charge = min_charge;
        }

        public String getExtra_mile_charge() {
            return extra_mile_charge;
        }

        public void setExtra_mile_charge(String extra_mile_charge) {
            this.extra_mile_charge = extra_mile_charge;
        }
    }

    public static class WaterBean {
        /**
         * waterId : 1
         * water_name : Purified
         * water_price : 14.00
         * water_image :
         * status : 1
         * crd : 2018-11-14 21:35:48
         * upd : 2019-02-19 04:24:30
         */

        private String waterId;
        private String water_name;
        private String water_price;
        private String water_image;
        private String status;
        private String crd;
        private String upd;

        public String getWaterId() {
            return waterId;
        }

        public void setWaterId(String waterId) {
            this.waterId = waterId;
        }

        public String getWater_name() {
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

    public static class BottleBean {
        /**
         * bottleId : 11
         * bottle_name : glass
         * bottle_image :
         * unit_type : 5
         * new_bottle_price : 2.00
         * old_bottle_price : 1.00
         * bottle_type : Glass
         * status : 1
         * crd : 2018-12-20 22:25:37
         * upd : 2019-02-10 21:43:09
         * bottles_type : 5 Gallon Glass
         */

        private String bottleId;
        private String bottle_name;
        private String bottle_image;
        private String unit_type;
        private String new_bottle_price;
        private String old_bottle_price;
        private String bottle_type;
        private String status;
        private String crd;
        private String upd;
        private String bottles_type;

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

    public static class RecycleBottleBean {
        /**
         * recycleBottleId : 1
         * recycle_bottle_name : plastic
         * recycle_bottle_image :
         * unit_type : 5
         * price : 9.00
         * bottle_type : Plastic
         * status : 1
         * crd : 2019-02-06 01:27:51
         * upd : 2019-02-10 22:07:11
         * recycle_type : 5 Gallon Plastic
         */

        private String recycleBottleId;
        private String recycle_bottle_name;
        private String recycle_bottle_image;
        private String unit_type;
        private String price;
        private String bottle_type;
        private String status;
        private String crd;
        private String upd;
        private String recycle_type;

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

        public String getUpd() {
            return upd;
        }

        public void setUpd(String upd) {
            this.upd = upd;
        }

        public String getRecycle_type() {
            return recycle_type;
        }

        public void setRecycle_type(String recycle_type) {
            this.recycle_type = recycle_type;
        }
    }
}
