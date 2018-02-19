package com.example.priyanka.mediatorngo.Network;

/**
 * Created by priyanka on 1/31/18.
 */
import com.example.priyanka.mediatorngo.Models.NGOResponse;
import com.example.priyanka.mediatorngo.Models.PickupRequestResponse;

import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiInterface {

    @POST("add_ngo/")
    @FormUrlEncoded
    Call<ResponseBody> addNGO(@Field("name") String name, @Field("contact_no") String contact_no, @Field("address") String address
    , @Field("area") String area, @Field("registration_no") String registration_no, @Field("email_id") String email_id,
                              @Field("password") String password);

    @POST("get_ngo/")
    @FormUrlEncoded
    Call<NGOResponse> getNGO(@Field("email_id") String email_id);

    @POST("add_pickup_request_for_ngo/")
    @FormUrlEncoded
    Call<ResponseBody> addPickupRequest(@Field("email_id") String email_id, @Field("name") String name, @Field("barcode") String barcode, @Field("expiry_date")
            String expiry_date, @Field("quantity") int quantity);

    @POST("get_pickup_requests_for_ngo/")
    @FormUrlEncoded
    Call<PickupRequestResponse> getPickupRequestsForNGO(@Field("email_id") String email_id);

    @POST("order_medicine_for_ngo/")
    @FormUrlEncoded
    Call<ResponseBody> orderMedicineForNGO(@Field("email_id") String email_id, @Field("name") String name, @Field("barcode") String barcode
            , @Field("quantity") int quantity);

    @POST("send_registration_token_to_server/")
    @FormUrlEncoded
    Call<ResponseBody> sendRegistrationTokenToServer(@Field("token") String token, @Field("email_id") String email_id);

    @POST("fcm/v1/devices/")
    @FormUrlEncoded
    Call<ResponseBody> addDevice(@Field("dev_id") String device_id, @Field("reg_id") String reg_id,@Field("name") String name);


}
