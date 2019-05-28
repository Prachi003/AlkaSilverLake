package com.alkline.alkasilverlake.Interface;


import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

/**
 * Created by mindiii on 13/11/18.
 */

public interface Reginterface {
    @FormUrlEncoded
    @POST("userRegistration")
    Call<ResponseBody> createuser(

            @Field("first_name") String fullName,
            @Field("email") String email,
            @Field("last_name") String last_name,
            @Field("password") String password, @Field("deviceToken") String deviceToken

    );


    /*@FormUrlEncoded
    @POST("userRegistration")
    Call<ResponseBody> fbuser(

            @Field("fullName") String fullName,
            @Field("email") String email,
            @Field("password") String password,
            @Field("socialType") String socialType,
            @Field("socialId") String socialId,
            @Field("deviceToken") String deviceToken

    );*/


    @FormUrlEncoded
    @POST("update_profile")
    Call<ResponseBody> updateProfile(
            @Header("authToken") String authtoken,
            @Field("first_name") String first_name,
            @Field("last_name") String last_name,
            @Field("email") String email,
            @Field("d_address") String d_address,
            @Field("b_address") String b_address,
            @Field("contact") String contact,
            @Field("password") String password,
            @Field("birth_date") String birth_date

    );


    @FormUrlEncoded
    @POST("ForgotPassword/forgot_password")
    Call<ResponseBody> Forgotuser(

            @Field("email") String email);



    @POST("userLogin")
    @FormUrlEncoded
    Call<ResponseBody> login(@Field("email") String email,
                             @Field("password")
                                     String password, @Field("deviceToken") String deviceToken);








  /*  @FormUrlEncoded
    @POST("add_addresses")
    Call<ResponseBody> profileedit(
            @Header("authToken") String lang12,
            @Field("user_name") String user_name,
            @Field("d_contact_name") String d_contact_name,
            @Field("d_address") String d_address,
            @Field("d_city") String d_city,
            @Field("d_state") String d_state,
            @Field("d_contact_no") String d_contact_no,
            @Field("d_zip_code") String d_zip_code,
            @Field("d_lattitude") String d_lattitude,
            @Field("d_longitude") String d_longitude,
            @Field("b_contact_name") String b_contact_name,
            @Field("b_address") String b_address,
            @Field("b_city") String b_city,
            @Field("b_state") String b_state,
            @Field("b_contact_no") String b_contact_no,
            @Field("b_zip_code") String b_zip_code,
            @Field("b_lattitude") String b_lattitude,
            @Field("b_longitude") String b_longitude
    );*/


/*
    @POST("add_addresses")
    @FormUrlEncoded
    Call<ResponseBody> checkdata(@Header("authToken") String lang12,

                                 @Field("d_contact_name") String d_contact_name,
                                 @Field("d_contact_no") String d_contact_no,
                                 @Field("d_email") String d_email,
                                 @Field("d_address") String d_address,
                                 @Field("d_city") String d_city,
                                 @Field("d_state") String d_state,
                                 @Field("d_zip_code") String d_zip_code,
                                 @Field("d_lattitude") String d_lattitude,
                                 @Field("d_longitude") String d_longitude,
                                 @Field("same_address") String same_address,
                                 @Field("b_city") String b_city,
                                 @Field("b_state") String b_state,
                                 @Field("b_address") String b_address,
                                 @Field("b_zip_code") String b_zip_code,
                                 @Field("b_lattitude") String b_lattitude,
                                 @Field("b_longitude") String b_longitude,
                                 @Field("b_contact_no") String b_contact_no,
                                 @Field("b_email") String b_email,
                                 @Field("b_contact_name") String b_contact_name,
                                 @Field("user_name") String user_name
    );
*/


    @POST("product/order")
    @FormUrlEncoded
    Call<ResponseBody> paydata(@Header("authToken") String lang12,
                               @Field("grand_total") String grand_total,
                               @Field("payment_method") String payment_method,
                               @Field("auto_pay") String auto_pay,
                               @Field("card_id") String card_id,
                               @Field("schedule_type") String schedule_type,
                               @Field("start_date") String start_date,
                               @Field("end_not_define") String end_not_define,
                               @Field("address") String address,
                               @Field("product") String product,
                               @Field("recycle") String recycle,
                               @Field("end_date") String end_date,
                               @Field("autherized") String autherized,
                               @Field("delivery_distance") String delivery_distance,
                               @Field("distance_charge") String distance_charge,
                               @Field("source_type") String source_type,
                               @Field("product_charge") String product_charge)
            ;

/*
    @GET("product/get_order?order_status=pending")
    Call<ResponseBody> inprogressorderdata(@Header("authToken") String lang12);
*/

/*
    @GET("product/get_order?order_status=complete")
    Call<ResponseBody> completeorderdata(@Header("authToken") String lang12);

*/

    /*@GET("product/notification_list")
    Call<ResponseBody> notification(@Header("authToken") String lang12);
*/

    @GET("product/product_list")
    Call<ResponseBody> Productlistdata(@Header("authToken") String lang12
    );

    @GET("product/notification_list")
    Call<ResponseBody> getNotification(@Header("authToken") String lang12
    );

    @GET("product/get_order")
    Call<ResponseBody> getOrders(@Header("authToken") String lang12
    );

    @GET("product/get_favourite_order")
    Call<ResponseBody> getFav(@Header("authToken") String lang12
    );


    /*  @POST("product/order_detail")
      @FormUrlEncoded
      Call<ResponseBody> ipsummeryorder_detail(@Header("authToken") String lang12,
                                               @Field("order_id") String order_id
      );
  */
    @POST("product/add_favourite_order ")
    @FormUrlEncoded
    Call<ResponseBody> addName(@Header("authToken") String lang12,
                               @Field("comment") String comment, @Field("order_id") String order_id
    );

 /*   @POST("product/cancelOrder")
    @FormUrlEncoded
    Call<ResponseBody> ordercancel(@Header("authToken") String lang12,
                                   @Field("order_id") String order_id
    );*/
/*
    @GET("product/waterList")
    Call<ResponseBody> water_List(@Header("authToken") String lang12);

    @POST("schedule/manualPayment")
    @FormUrlEncoded
    Call<ResponseBody> manualPayment(@Header("authToken") String lang12,
                                     @Field("payment_id") String payment_id,
                                     @Field("card_id") String card_id,
                                     @Field("custumer_id") String custumer_id,
                                     @Field("source_type") String source_type);*/

}


