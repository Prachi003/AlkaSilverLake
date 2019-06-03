package com.alkline.alkasilverlake.model;

import java.util.List;

public class NotificationModel {


    /**
     * status : success
     * data : [{"notificationId":"1","user_id":"152","title":"Order created successfully","type":"Order","order_id":"387","message":"your order 1981590522 is created successfully,payment 7834 is deducted and delivery will be delivered on 25th May, 2019","is_read":"0","current":"2019-05-22 10:54:51","crd":"2019-05-22 22:54:51","upd":"2019-05-22 22:54:51","current_time":"2019-05-22 11:40:18"}]
     */

    private String status;
    private List<DataBean> data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * notificationId : 1
         * user_id : 152
         * title : Order created successfully
         * type : Order
         * order_id : 387
         * message : your order 1981590522 is created successfully,payment 7834 is deducted and delivery will be delivered on 25th May, 2019
         * is_read : 0
         * current : 2019-05-22 10:54:51
         * crd : 2019-05-22 22:54:51
         * upd : 2019-05-22 22:54:51
         * current_time : 2019-05-22 11:40:18
         */

        private String notificationId;
        private String user_id;
        private String title;
        private String type;
        private String order_id;

        private String invoice_id;

        public String getInvoice_id() {
            return invoice_id;
        }

        public void setInvoice_id(String invoice_id) {
            this.invoice_id = invoice_id;
        }
        private String message;
        private String is_read;
        private String current;
        private String crd;
        private String upd;
        private String current_time;

        public String getNotificationId() {
            return notificationId;
        }

        public void setNotificationId(String notificationId) {
            this.notificationId = notificationId;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getOrder_id() {
            return order_id;
        }

        public void setOrder_id(String order_id) {
            this.order_id = order_id;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getIs_read() {
            return is_read;
        }

        public void setIs_read(String is_read) {
            this.is_read = is_read;
        }

        public String getCurrent() {
            return current;
        }

        public void setCurrent(String current) {
            this.current = current;
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

        public String getCurrent_time() {
            return current_time;
        }

        public void setCurrent_time(String current_time) {
            this.current_time = current_time;
        }
    }
}
