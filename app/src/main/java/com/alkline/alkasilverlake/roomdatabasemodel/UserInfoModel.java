package com.alkline.alkasilverlake.roomdatabasemodel;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "userInfo")
public class UserInfoModel {

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @PrimaryKey(autoGenerate = true)
    private  int  id;


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
    @ColumnInfo(name = "userId")
    private  String  userId;

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getDeviceToken() {
        return deviceToken;
    }

    public void setDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public String getSocialId() {
        return socialId;
    }

    public void setSocialId(String socialId) {
        this.socialId = socialId;
    }

    public String getSocialType() {
        return socialType;
    }

    public void setSocialType(String socialType) {
        this.socialType = socialType;
    }

    public String getCustomer_stripe_id() {
        return customer_stripe_id;
    }

    public void setCustomer_stripe_id(String customer_stripe_id) {
        this.customer_stripe_id = customer_stripe_id;
    }

    public String getPassword_token() {
        return password_token;
    }

    public void setPassword_token(String password_token) {
        this.password_token = password_token;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getThumbImage() {
        return thumbImage;
    }

    public void setThumbImage(String thumbImage) {
        this.thumbImage = thumbImage;
    }

    @ColumnInfo(name = "fullName")
    private  String  fullName;

    @ColumnInfo(name = "lastName")
    private  String  lastName;

    @ColumnInfo(name = "email")
    private  String  email;

    @ColumnInfo(name = "birthDate")
    private  String  birthDate;

    @ColumnInfo(name = "password")
    private  String  password;

    @ColumnInfo(name = "contact")
    private  String  contact;

    @ColumnInfo(name = "userType")
    private  String  userType;

    @ColumnInfo(name = "address")
    private  String  address;

    @ColumnInfo(name = "profileImage")
    private  String  profileImage;

    @ColumnInfo(name = "deviceType")
    private  String  deviceType;

    @ColumnInfo(name = "deviceToken")
    private  String  deviceToken;

    @ColumnInfo(name = "authToken")
    private  String  authToken;

    @ColumnInfo(name = "socialId")
    private  String  socialId;

    @ColumnInfo(name = "socialType")
    private  String  socialType;

    @ColumnInfo(name = "customer_stripe_id")
    private  String  customer_stripe_id;

    @ColumnInfo(name = "password_token")
    private  String  password_token;

    @ColumnInfo(name = "crd")
    private  String  crd;

    @ColumnInfo(name = "upd")
    private  String  upd;

    @ColumnInfo(name = "status")
    private  String  status;

    @ColumnInfo(name = "thumbImage")
    private  String  thumbImage;

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public String getDeliveryCity() {
        return deliveryCity;
    }

    public void setDeliveryCity(String deliveryCity) {
        this.deliveryCity = deliveryCity;
    }

    public String getDeliveryState() {
        return deliveryState;
    }

    public void setDeliveryState(String deliveryState) {
        this.deliveryState = deliveryState;
    }

    public String getDeliveryCountry() {
        return deliveryCountry;
    }

    public void setDeliveryCountry(String deliveryCountry) {
        this.deliveryCountry = deliveryCountry;
    }

    public String getDeliveryZip() {
        return deliveryZip;
    }

    public void setDeliveryZip(String deliveryZip) {
        this.deliveryZip = deliveryZip;
    }

    public String getDeliveryLatitude() {
        return deliveryLatitude;
    }

    public void setDeliveryLatitude(String deliveryLatitude) {
        this.deliveryLatitude = deliveryLatitude;
    }

    public String getDeliveryLongitude() {
        return deliveryLongitude;
    }

    public void setDeliveryLongitude(String deliveryLongitude) {
        this.deliveryLongitude = deliveryLongitude;
    }

    public String getBillingAddress() {
        return billingAddress;
    }

    public void setBillingAddress(String billingAddress) {
        this.billingAddress = billingAddress;
    }

    public String getBillingCity() {
        return billingCity;
    }

    public void setBillingCity(String billingCity) {
        this.billingCity = billingCity;
    }

    public String getBillingState() {
        return billingState;
    }

    public void setBillingState(String billingState) {
        this.billingState = billingState;
    }

    public String getBillingCountry() {
        return billingCountry;
    }

    public void setBillingCountry(String billingCountry) {
        this.billingCountry = billingCountry;
    }

    public String getBillingZip() {
        return billingZip;
    }

    public void setBillingZip(String billingZip) {
        this.billingZip = billingZip;
    }

    public String getBillingLatitude() {
        return billingLatitude;
    }

    public void setBillingLatitude(String billingLatitude) {
        this.billingLatitude = billingLatitude;
    }

    public String getBillingLongitude() {
        return billingLongitude;
    }

    public void setBillingLongitude(String billingLongitude) {
        this.billingLongitude = billingLongitude;
    }

    @ColumnInfo(name = "deliveryAddress")
    private  String  deliveryAddress;

    @ColumnInfo(name = "deliveryCity")
    private  String  deliveryCity;

    @ColumnInfo(name = "deliveryState")
    private  String  deliveryState;

    @ColumnInfo(name = "deliveryCountry")
    private  String  deliveryCountry;

    @ColumnInfo(name = "deliveryZip")
    private  String  deliveryZip;



    @ColumnInfo(name = "deliveryLatitude")
    private  String  deliveryLatitude;

    @ColumnInfo(name = "deliveryLongitude")
    private  String  deliveryLongitude;

    @ColumnInfo(name = "billingAddress")
    private  String  billingAddress;

    @ColumnInfo(name = "billingCity")
    private  String  billingCity;

    @ColumnInfo(name = "billingState")
    private  String  billingState;

    @ColumnInfo(name = "billingCountry")
    private  String  billingCountry;


    @ColumnInfo(name = "billingZip")
    private  String  billingZip;

    @ColumnInfo(name = "billingLatitude")
    private String billingLatitude;

    @ColumnInfo(name = "billingLongitude")
    private String billingLongitude;







}
