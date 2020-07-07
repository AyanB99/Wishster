package com.wishster.model;
import com.wishster.model.forgot_password.Forgot_pojo;
import com.wishster.model.forgot_password.change_passF_POJO;
import com.wishster.model.forgot_password.pin_POJO;
import com.wishster.model.login_and_signup_module.LoginPojo;
import com.wishster.model.login_and_signup_module.SignUpPojo;
import com.wishster.model.securityModel.securityModel;
import com.wishster.view.mylogindesign.SignupPojo;


import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ApiInterface_sandree {
    // @GET("movie/top_rated")


    /*@GET("movie/{id}")
    Call<MoviesResponse> getMovieDetails(@Path("id") int id, @Query("api_key") String apiKey);*/


    /*@POST("api/getPaymentDetails")
    @FormUrlEncoded
    Call<Testingdata> savePost(@Query("api_key") String api_key,
                               @Query("booking_id") String booking_id);*/


    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })

    @POST("login")
    Call<LoginPojo> allData(@Body RequestBody locationPost);

    @POST("forget_pwd")
    Call<Forgot_pojo> sendEmail(@Body RequestBody locationPost);
    @POST("verify_otp")
    Call<pin_POJO> checkPIN(@Body RequestBody locationPost);
    @POST("reset_pwd")
    Call<change_passF_POJO> changePassF(@Body RequestBody locationPost);

    @POST("save_security_qtn")
    Call<securityModel> setSecurityQuestion(@Body RequestBody locationPost);

    @POST("index.php")
    Call<SignUpPojo> allSignUpData(@Body RequestBody locationPost);

    @POST("login")
    Call<String> allTestLogin(@Body RequestBody locationPost);
}
