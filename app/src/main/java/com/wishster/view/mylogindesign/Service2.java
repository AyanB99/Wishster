package com.wishster.view.mylogindesign;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;

/**
 * Created by Robert
 */

interface Service2 {
    /*@Multipart
    @POST("/upload_multi_files/MultiUpload.php")
    Call<ResponseBody> uploadFile(@Part MultipartBody.Part file, @Part("name") RequestBody name);*/
    Call<Testingdata> savePost(@Body RequestBody locationPost);

    @Multipart
    @POST("addBooking")
    Call<UploadObject> uploadSingleFile(@Part MultipartBody.Part file,
                                        @Part("api_key") String api_key,
                                        @Part("bed_id") String bed_id,
                                        @Part("tenant_id") String tenant_id,
                                        @Part("company_id") String company_id,
                                        @Part("shifting_date") String shifting_date,
                                        @Part("billing_start_date") String billing_start_date,
                                        @Part("billing_end_date") String billing_end_date,
                                        @Part("duration") String duration,
                                        @Part("ac_status") String ac_status,
                                        @Part("fooding_status") String fooding_status,
                                        @Part("current_bed_rent") String current_bed_rent,

                                        @Part("security_deposit") String security_deposit,
                                        @Part("one_time_maintenance") String one_time_maintenance,
                                        @Part("total_amount") String total_amount,
                                        @Part("token_amount") String token_amount,
                                        @Part("payment_mode") String payment_mode,
                                        @Part("transaction_id") String transaction_id


    );



    @Multipart
    @POST("register")
    Call<SignupPojo> uploadSingleFile3(@Part MultipartBody.Part file,
                                       @Part("first_name") String first_name_s,
                                       @Part("last_name") String last_name_s,
                                       @Part("email") String email_s,
                                       @Part("password") String password_s,
                                       @Part("mobile") String mobile_s,
                                       @Part("country") String country_s,
                                       @Part("state") String state_s,
                                       @Part("city") String city_s,
                                       @Part("postal_code") String postal_code_s,
                                       @Part("birthday") String birthday_s


    );





    @Multipart
    //@POST("/upload_multi_files/MultiUpload.php")
    @POST("index2.php")
    Call<UploadObject> uploadMultiFile(@Part MultipartBody.Part file1, @Part MultipartBody.Part file2, @Part MultipartBody.Part file3);

    //@Multipart
    //@FormUrlEncoded
    //@POST("/upload_multi_files/MultiUpload.php")
    @POST("index2.php")
    Call<ResponseBody> uploadMultiFile(@Body RequestBody file);

    @FormUrlEncoded
    @PUT("index2.php")
    Call<ResponseBody> getStatus(
            @Field("username") String username,
            @Field("email") String email,
            @Field("password") String password,
            @Field("fbID") String fbID,
            @Field("gmailID") String gmailID,
            @Field("twitID") String twitID,
            @Field("gender") String gender,
            @Field("birthDate") String birthDate,
            @Field("location") String location,
            @Field("longitude") String longitude,
            @Field("latitude") String latitude,
            @Field("profileImage") String profileImage);
    //@Field parameters can only be used with form encoding. (parameter #2)
}
