package com.alkline.alkasilverlake.responsebean;

public class UserInfoBean {


    /**
     * status : success
     * message : Authentication successfully done!
     * userDetail : {"id":"99","fullName":"Prachi","email":"prachi@gmail.com","password":"$2y$10$VGQyyqFN67kd/JNQcnz5rOppl2F84IoZZyyB6ov15f/.17Qr1Xhg.","contact":"0","userType":"user","address":"","profileImage":"0","deviceType":"0","deviceToken":"0","authToken":"e8f04f0908ee17ff86021842344efbd10d3ad92c","socialId":"0","socialType":"","customer_stripe_id":"cus_EmmseNxUFFq5Vi","password_token":"1","crd":"2019-03-28 22:31:04","upd":"2019-03-28 22:44:43","status":"1","thumbImage":"http://www.app.alkasilverlake.com/backend_asset/images/user_placeholder.png","delivery_add":{},"billing_add":{}}
     */

    private String status;
    private String message;
    private UserDetailBean userDetail;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public UserDetailBean getUserDetail() {
        return userDetail;
    }

    public void setUserDetail(UserDetailBean userDetail) {
        this.userDetail = userDetail;
    }

    public static class UserDetailBean {
        /**
         * id : 99
         * fullName : Prachi
         * email : prachi@gmail.com
         * password : $2y$10$VGQyyqFN67kd/JNQcnz5rOppl2F84IoZZyyB6ov15f/.17Qr1Xhg.
         * contact : 0
         * userType : user
         * address :
         * profileImage : 0
         * deviceType : 0
         * deviceToken : 0
         * authToken : e8f04f0908ee17ff86021842344efbd10d3ad92c
         * socialId : 0
         * socialType :
         * customer_stripe_id : cus_EmmseNxUFFq5Vi
         * password_token : 1
         * crd : 2019-03-28 22:31:04
         * upd : 2019-03-28 22:44:43
         * status : 1
         * thumbImage : http://www.app.alkasilverlake.com/backend_asset/images/user_placeholder.png
         * delivery_add : {}
         * billing_add : {}
         */

        private String id;
        private String fullName;
        private String email;
        private String password;

        private String birthDate;
        private String lastName;

        public String getBirthDate() {
            return birthDate;
        }

        public void setBirthDate(String birthDate) {
            this.birthDate = birthDate;
        }
        private String contact;
        private String userType;
        private String address;
        private String profileImage;
        private String deviceType;
        private String deviceToken;
        private String authToken;
        private String socialId;
        private String socialType;
        private String customer_stripe_id;
        private String password_token;
        private String crd;
        private String upd;
        private String status;
        private String thumbImage;
        private DeliveryAddBean delivery_add;
        private BillingAddBean billing_add;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getFullName() {
            return fullName;
        }

        public void setFullName(String fullName) {
            this.fullName = fullName;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
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

        public DeliveryAddBean getDelivery_add() {
            return delivery_add;
        }

        public void setDelivery_add(DeliveryAddBean delivery_add) {
            this.delivery_add = delivery_add;
        }

        public BillingAddBean getBilling_add() {
            return billing_add;
        }

        public void setBilling_add(BillingAddBean billing_add) {
            this.billing_add = billing_add;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public static class DeliveryAddBean {
        }

        public static class BillingAddBean {
        }
    }
}
