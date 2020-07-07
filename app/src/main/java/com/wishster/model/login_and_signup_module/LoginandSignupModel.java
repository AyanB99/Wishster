package com.wishster.model.login_and_signup_module;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;


import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.wishster.model.ApiClient_sandree;
import com.wishster.model.ApiInterface_sandree;
import com.wishster.model.Declaration;
import com.wishster.model.HttpsTrustManager;
import com.wishster.model.dialog_model.TransparentProgressDialog;
import com.wishster.model.forgot_password.Forgot_pojo;
import com.wishster.model.forgot_password.change_passF_POJO;
import com.wishster.model.forgot_password.forgotpassModel;
import com.wishster.model.forgot_password.pin_POJO;
import com.wishster.model.securityModel.securityModel;
import com.wishster.view.R;
import com.wishster.view.mylogindesign.ActivityLogin;
import com.wishster.view.mylogindesign.SignupPojo;
import com.wishster.view.security_question.securityQuestionSuccessMessage;

import net.gotev.uploadservice.http.HttpStack;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginandSignupModel {
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    MutableLiveData<String> mutableLiveData_login;
    public static MutableLiveData<LoginPojo> t_data;
    public static MutableLiveData<LoginPojo> t_data2;
    public static MutableLiveData<JSONObject> t_data3;
    public static MutableLiveData<JSONObject> t_data4;
    public static MutableLiveData<securityModel> t_data5;
    public static MutableLiveData<Forgot_pojo> t_data6;
    public static MutableLiveData<pin_POJO> t_data7;
    public static MutableLiveData<change_passF_POJO> t_data8;
    Application application;

    JSONObject jsonObject;
    RequestBody bodyRequest;
    Call<LoginPojo> cell=null;
    Call<String> cell2=null;
    Call<SignUpPojo> cell3=null;
    Call<securityModel> call4=null;
    Call<Forgot_pojo> call5=null;
    Call<pin_POJO> call6=null;
    Call<change_passF_POJO> call7=null;
    public LoginandSignupModel(Application application) {
        this.application = application;
    }

    public MutableLiveData<String> getLogIndata(){
        if (mutableLiveData_login==null){
            mutableLiveData_login=new MutableLiveData<String>();

        }
        mutableLiveData_login.setValue("Kolkata");
        return mutableLiveData_login;
    }





    public MutableLiveData<JSONObject> getLogin(Context ctx)  throws JSONException
    {


          if (t_data4==null)
                {
                    t_data4=new MutableLiveData<JSONObject>();

                }


        jsonObject= new JSONObject();
        jsonObject.put("email", Declaration.user_ph_or_email);
        jsonObject.put("password", Declaration.user_pass);
        HurlStack stack =  new HurlStack(null, new NoSSLv3SocketFactory());
        RequestQueue queue = Volley.newRequestQueue(ctx, stack);
        final RequestQueue requestQueue = Volley.newRequestQueue(ctx);

        /*Log.d("logindatachk","JSON BVODY : "+jsonObject.toString());
        bodyRequest = RequestBody.create(MediaType.parse("application/json"), jsonObject.toString());
        ApiInterface_sandree apiService = ApiClient_sandree.getClient().create(ApiInterface_sandree.class);
        cell=apiService.allData(bodyRequest);
        cell.enqueue(new Callback<LoginPojo>()
        {
            @Override
            public void onResponse(Call<LoginPojo> call, Response<LoginPojo> response)
            {
                Log.d("logindatachk",response.code()+""+response.message());
                try {
                    t_data2.setValue(response.body());
                }catch (Exception e){

                }
            }

            @Override
            public void onFailure(Call<LoginPojo> call, Throwable t)
            {
                Log.d("jsondatachk","Error :11 : "+t.getMessage());

            }
        });*/
        String url=ApiClient_sandree.BASE_URL+"login";
        HttpsTrustManager.allowAllSSL();
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST,url, jsonObject, new com.android.volley.Response.Listener<JSONObject>()
                {

                    @Override
                    public void onResponse(JSONObject response)
                    {
                        t_data4.setValue(response);
                        Log.d("kkkkkkkkk","KOLK : "+response.toString());

                    }
                } , new com.android.volley.Response.ErrorListener()
                {

                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        Log.d("kkkkkkkkk","ERROR "+error.getMessage());
                        error.printStackTrace();

                    }
                }){
            @Override
            protected com.android.volley.Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
                token.token=response.headers.get("token");
                return super.parseNetworkResponse(response);
            }

            //******
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("Content-Type", "application/json");


                return params;

                //***********
            };};
        jsonObjectRequest.setShouldCache(false);
        requestQueue.add(jsonObjectRequest);


        return t_data4;
    }
    //get security Question
    public MutableLiveData<JSONObject> getSecurityQuestion(Context ctx)  throws JSONException
    {


          if (t_data4==null)
                {
                    t_data4=new MutableLiveData<JSONObject>();

                }


        jsonObject= new JSONObject();
        jsonObject.put("q", "");
        final RequestQueue requestQueue = Volley.newRequestQueue(ctx);

        /*Log.d("logindatachk","JSON BVODY : "+jsonObject.toString());
        bodyRequest = RequestBody.create(MediaType.parse("application/json"), jsonObject.toString());
        ApiInterface_sandree apiService = ApiClient_sandree.getClient().create(ApiInterface_sandree.class);
        cell=apiService.allData(bodyRequest);
        cell.enqueue(new Callback<LoginPojo>()
        {
            @Override
            public void onResponse(Call<LoginPojo> call, Response<LoginPojo> response)
            {
                Log.d("logindatachk",response.code()+""+response.message());
                try {
                    t_data2.setValue(response.body());
                }catch (Exception e){

                }
            }

            @Override
            public void onFailure(Call<LoginPojo> call, Throwable t)
            {
                Log.d("jsondatachk","Error :11 : "+t.getMessage());

            }
        });*/
        String url=ApiClient_sandree.BASE_URL+"security_qtn";
        HttpsTrustManager.allowAllSSL();
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET,url, jsonObject, new com.android.volley.Response.Listener<JSONObject>()
                {

                    @Override
                    public void onResponse(JSONObject response)
                    {
                        t_data4.setValue(response);
                        Log.d("kkkkkkkkk","KOLK : "+response.toString());

                    }
                }, new com.android.volley.Response.ErrorListener()
                {

                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        Log.d("kkkkkkkkk","ERROR "+error.getMessage());
                        error.printStackTrace();

                    }
                }){
            //******
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("Content-Type", "application/json");


                return params;

                //***********
            };};
        requestQueue.add(jsonObjectRequest);
        return t_data4;
    }

    // //send email
    public MutableLiveData<Forgot_pojo> SendEmail(Context ctx)  throws JSONException
    {


          if (t_data6==null)
                {
                    t_data6=new MutableLiveData<Forgot_pojo>();

                }
        jsonObject= new JSONObject();
        jsonObject.put("email", forgotpassModel.email);
        final RequestQueue requestQueue = Volley.newRequestQueue(ctx);


        bodyRequest = RequestBody.create(MediaType.parse("application/json"), jsonObject.toString());
        ApiInterface_sandree apiService = ApiClient_sandree.getClient().create(ApiInterface_sandree.class);
        call5=apiService.sendEmail(bodyRequest);
        call5.enqueue(new Callback<Forgot_pojo>()
        {
            @Override
            public void onResponse(Call<Forgot_pojo> call, Response<Forgot_pojo> response)
            {
                Log.d("logindatachk",response.code()+""+response.message());
                try {
                    t_data6.setValue(response.body());
                }catch (Exception e){

                }
            }

            @Override
            public void onFailure(Call<Forgot_pojo> call, Throwable t)
            {
                Log.d("jsondatachk","Error :11 : "+t.getMessage());

            }
        });

        return t_data6;
    }
    //set send e mail
    //// //send pin
    public MutableLiveData<pin_POJO> CheckPIN(Context ctx)  throws JSONException
    {


          if (t_data7==null)
                {
                    t_data7=new MutableLiveData<pin_POJO>();

                }
        jsonObject= new JSONObject();
        jsonObject.put("email", forgotpassModel.email);
        jsonObject.put("otp", forgotpassModel.pin);



        bodyRequest = RequestBody.create(MediaType.parse("application/json"), jsonObject.toString());
        ApiInterface_sandree apiService = ApiClient_sandree.getClient().create(ApiInterface_sandree.class);
        call6=apiService.checkPIN(bodyRequest);
        call6.enqueue(new Callback<pin_POJO>()
        {
            @Override
            public void onResponse(Call<pin_POJO> call, Response<pin_POJO> response)
            {
                Log.d("logindatachk",response.code()+""+response.body().getMessage());
                try {
                    t_data7.setValue(response.body());
                }catch (Exception e){

                }
            }

            @Override
            public void onFailure(Call<pin_POJO> call, Throwable t)
            {
                Log.d("jsondatachk","Error :11 : "+t.getMessage());

            }
        });

        return t_data7;
    }
    //set pin////
    // change pass F
    public MutableLiveData<change_passF_POJO> ChangePassF(Context ctx)  throws JSONException
    {


          if (t_data8==null)
                {
                    t_data8=new MutableLiveData<change_passF_POJO>();

                }
        jsonObject= new JSONObject();
        jsonObject.put("email", forgotpassModel.email);
        jsonObject.put("new_password", forgotpassModel.pass);



        bodyRequest = RequestBody.create(MediaType.parse("application/json"), jsonObject.toString());
        ApiInterface_sandree apiService = ApiClient_sandree.getClient().create(ApiInterface_sandree.class);
        call7=apiService.changePassF(bodyRequest);
        call7.enqueue(new Callback<change_passF_POJO>()
        {
            @Override
            public void onResponse(Call<change_passF_POJO> call, Response<change_passF_POJO> response)
            {

                try {
                    t_data8.setValue(response.body());
                }catch (Exception e)
                {

                }
            }

            @Override
            public void onFailure(Call<change_passF_POJO> call, Throwable t)
            {
                Log.d("jsondatachk","Error :11 : "+t.getMessage());

            }
        });

        return t_data8;
    }
    //set pin

    //
    //
    // get security Question
    public MutableLiveData<JSONObject> getProfile(Context ctx)  throws JSONException
    {


          if (t_data4==null)
                {
                    t_data4=new MutableLiveData<JSONObject>();

                }


        jsonObject= new JSONObject();
        jsonObject.put("q", "");
        final RequestQueue requestQueue = Volley.newRequestQueue(ctx);

        Log.d("logindatachk","JSON BVODY : "+jsonObject.toString());
        bodyRequest = RequestBody.create(MediaType.parse("application/json"), jsonObject.toString());
        ApiInterface_sandree apiService = ApiClient_sandree.getClient().create(ApiInterface_sandree.class);
        cell=apiService.allData(bodyRequest);
        cell.enqueue(new Callback<LoginPojo>()
        {
            @Override
            public void onResponse(Call<LoginPojo> call, Response<LoginPojo> response)
            {
                Log.d("logindatachk",response.code()+""+response.message());
                try {
                    t_data2.setValue(response.body());
                }catch (Exception e){

                }
            }

            @Override
            public void onFailure(Call<LoginPojo> call, Throwable t)
            {
                Log.d("jsondatachk","Error :11 : "+t.getMessage());

            }
        });
        String url=ApiClient_sandree.BASE_URL+"profile";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET,url, jsonObject, new com.android.volley.Response.Listener<JSONObject>()
                {

                    @Override
                    public void onResponse(JSONObject response)
                    {
                        t_data4.setValue(response);
                        Log.d("kkkkkkkkk","KOLK : "+response.toString());

                    }
                }, new com.android.volley.Response.ErrorListener()
                {

                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        Log.d("kkkkkkkkk","ERROR "+error.getMessage());
                        error.printStackTrace();

                    }
                }){
            //******
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("Content-Type", "application/json");
                params.put("Authorization", token.token);


                return params;

                //***********
            };};
        requestQueue.add(jsonObjectRequest);
        return t_data4;
    }
    //set security Question
    public MutableLiveData<securityModel> setSecurityQuestion(final Context ctx, final JSONObject store_security_question, final TransparentProgressDialog pd, final  TextView aa)  throws JSONException
    {

        if (t_data5==null)
        {
            t_data5=new MutableLiveData<securityModel>();

        }
        pref = ctx.getApplicationContext().getSharedPreferences(ctx.getString(R.string.SESSION), 0);
        editor = pref.edit();
        bodyRequest = RequestBody.create(MediaType.parse("application/json"), store_security_question.toString());
        ApiInterface_sandree apiService = ApiClient_sandree.getClient().create(ApiInterface_sandree.class);
        call4=apiService.setSecurityQuestion(bodyRequest);
        call4.enqueue(new Callback<securityModel>()
        {
            @Override
            public void onResponse(Call<securityModel> call, Response<securityModel> response)
            {
                //Log.d("printdataASD",response.body().isStatus()+":"+response.body().getMessage());
                securityQuestionSuccessMessage.msg=response.body().getMessage();
                securityQuestionSuccessMessage.s_boolo=response.body().isStatus();
                if (pd.isShowing())
                {
                    pd.dismiss();
                    Toast.makeText(ctx, ""+securityQuestionSuccessMessage.msg, Toast.LENGTH_SHORT).show();
                    aa.setText(securityQuestionSuccessMessage.msg);
                }
                if (securityQuestionSuccessMessage.s_boolo==true)
                {
                    editor.putBoolean("isReg",false);
                    editor.apply();
                    ctx.startActivity(new Intent(ctx, ActivityLogin.class).putExtra("userid","").addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                }
                try
                {
                    t_data5.setValue(response.body());
                }
                catch (Exception e)
                {}
            }

            @Override
            public void onFailure(Call<securityModel> call, Throwable t)
            {
                Log.d("jsondatachk","Error :11 : "+t.getMessage());

            }
        });
        return t_data5;
    }

    public MutableLiveData<JSONObject> getSignup(Context context)  throws JSONException
    {

                if (t_data3==null)
                {
                    t_data3=new MutableLiveData<JSONObject>();

                }


        /*jsonObject= new JSONObject();
        jsonObject.put("first_name", Declaration.first_name);
        jsonObject.put("last_name", Declaration.last_name);
        jsonObject.put("email", Declaration.email);
        jsonObject.put("password", Declaration.password);
        jsonObject.put("mobile", Declaration.mobile);
        jsonObject.put("country", Declaration.country);
        jsonObject.put("state", Declaration.state);
        jsonObject.put("city", Declaration.city);
        jsonObject.put("postal_code", Declaration.postal_code);
        jsonObject.put("birthday", Declaration.birthday);

        String request="";
        request="{\n" +
                "                \"first_name\": \""+Declaration.first_name+"\",\n" +
                "                \"last_name\": \""+Declaration.last_name+"\",\n" +
                "                \"email\": \""+Declaration.email+"\",\n" +
                "                \"password\": \""+Declaration.password+"\",\n" +
                "                \"mobile\": \""+Declaration.mobile+"\",\n" +
                "                \"country\": \""+Declaration.country+"\",\n" +
                "                \"state\": \""+Declaration.state+"\",\n" +
                "                \"city\": \""+Declaration.city+"\",\n" +
                "                \"postal_code\": \""+Declaration.postal_code+"\",\n" +
                "                \"birthday\": \""+Declaration.birthday+"\"\n" +
                "              }";



        Log.d("signdatachk",Declaration.birthday+"JSON BVODY : "+request);
        bodyRequest = RequestBody.create(MediaType.parse("application/json"), jsonObject.toString());
        ApiInterface_sandree apiService = ApiClient_sandree.getClient().create(ApiInterface_sandree.class);
        cell3=apiService.allSignUpData(bodyRequest);
        cell3.enqueue(new Callback<SignUpPojo>()
        {
            @Override
            public void onResponse(Call<SignUpPojo> call, Response<SignUpPojo> response)
            {
                Log.d("logindatachk",response.code()+""+response.message());
                try {
                    t_data3.setValue(response.body());
                }catch (Exception e){

                }
            }

            @Override
            public void onFailure(Call<SignUpPojo> call, Throwable t)
            {
                Log.d("jsondatachk","Error :11 : "+t.getMessage());

            }
        });*/

        JSONObject jsonObject = new JSONObject();
        jsonObject= new JSONObject();
        jsonObject.put("first_name", Declaration.first_name);
        jsonObject.put("last_name", Declaration.last_name);
        jsonObject.put("email", Declaration.email);
        jsonObject.put("password", Declaration.password);
        jsonObject.put("mobile", Declaration.mobile);
        jsonObject.put("country", Declaration.country);
        jsonObject.put("state", Declaration.state);
        jsonObject.put("city", Declaration.city);
        jsonObject.put("postal_code", Declaration.postal_code);
        jsonObject.put("birthday", Declaration.birthday);

        final RequestQueue requestQueue = Volley.newRequestQueue(context);


        String url=ApiClient_sandree.BASE_URL;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST,url, jsonObject, new com.android.volley.Response.Listener<JSONObject>()
                {

                    @Override
                    public void onResponse(JSONObject response)
                    {
                        t_data3.setValue(response);
                        Log.d("kkkkkkkkk","KOLK : "+response.toString());

                    }
                }, new com.android.volley.Response.ErrorListener()
                {

                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        Log.d("kkkkkkkkk","ERROR "+error.getMessage());
                        error.printStackTrace();

                    }
                }){
            //******
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("Content-Type", "application/json");


                return params;

                //***********
            };};
        requestQueue.add(jsonObjectRequest);



        return t_data3;
    }
    //security




}
