package com.wishster.view.mylogindesign;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.wishster.model.Declaration;
import com.wishster.model.dialog_model.TransparentProgressDialog;
import com.wishster.model.internet_chacking.CheckInternet;
import com.wishster.model.login_and_signup_module.LoginPojo;
import com.wishster.model.login_and_signup_module.token;
import com.wishster.view.R;
import com.wishster.view.forgot_password.ActivityForgotPasssword;
import com.wishster.view.nointernet.ActivityNoActivity;
import com.wishster.view.master.ActivityMaster;
import com.wishster.view.security_question.ActivitySecurityQuestion;
import com.wishster.viewmodel.MainActivityViewModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import es.dmoral.toasty.Toasty;

public class ActivityLogin extends AppCompatActivity
{
    private static final int MY_PERMISSIONS_REQUEST_CODE = 124;
    MainActivityViewModel mainActivityViewModel;
    Observer<LoginPojo> obs;
    ActivityLogin activity;
    Button sign_up_btn,login_btn;
    EditText emainandphoneno,pass;
    TextView f1,f2,forgot_password;
    ActivityLogin mActivity;
    LinearLayout pan;
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    private TransparentProgressDialog pd;
    /*
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd G 'at' HH:mm:ss z");
    String currentDateandTime = sdf.format(new Date());
     */
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        activity=this;
        mainActivityViewModel= ViewModelProviders.of(this).get(MainActivityViewModel.class);
        mActivity = ActivityLogin.this;
        pref = getApplicationContext().getSharedPreferences(getString(R.string.SESSION), MODE_PRIVATE);
        editor = pref.edit();
       /* try {
            getSecurityQuestion();
        } catch (JSONException e) {
            e.printStackTrace();
        }*/
        pd = new TransparentProgressDialog(this, R.drawable.dialog_image);
       /* try {
            getLoginData();
        } catch (JSONException e) {
            e.printStackTrace();
        }*/
        if(!(new CheckInternet(this).getInternetStatus()))
        {

            startActivity(new Intent(this, ActivityNoActivity.class));
            finish();
        }
        try {
            if (pref.getBoolean("isLogin", false) == true)
            {
                startActivity(new Intent(ActivityLogin.this, ActivityMaster.class));
                finish();
            }

             if (pref.getBoolean("isReg", false) == true)
            {
                startActivity(new Intent(ActivityLogin.this, ActivitySecurityQuestion.class));
                finish();
            }

        }catch (Exception e)
        {}


        new Handler().postDelayed(new Runnable(){
            @Override
            public void run()
            {
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                {
                    checkPermission();
                }
            }
        }, 100);
        init();
        action();
        sign_up_btn=(Button)findViewById(R.id.sign_up_btn);
        sign_up_btn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(ActivityLogin.this, ActivitySignUp.class));
            }
        });
        pan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                hideKeyboard(activity);
            }
        });

    }

    private void getLoginData() throws JSONException
    {
      //  Declaration.user_ph_or_email="abc@abc.com";
      //  Declaration.user_pass="Asdf@123456";
        try {
                mainActivityViewModel.getLogin().observe(activity, new Observer<JSONObject>()
                {
                @Override
                public void onChanged(JSONObject s)
                {
                    login_btn.setClickable(true);
                    try {
                        //Toast.makeText(activity, ""+s.getString("message"), Toast.LENGTH_SHORT).show();
                        if (s.getString("message").trim().equals("Security Question Not Added")){
                            getSecurityQuestion();

                        }
                    } catch (JSONException e)
                    {

                        if (pd.isShowing())
                        {
                            pd.dismiss();
                        }
                        e.printStackTrace();
                    }
                    try {

                        if (s.getBoolean("status"))
                        {

                            f1.setText("");
                            f1.setVisibility(View.GONE);
                            JSONObject jsonObject1=s.getJSONObject("data");

                            /*editor.putString("id", jsonObject1.getString("id"));
                            editor.putString("church", jsonObject1.getString("church"));

                            editor.putBoolean("isLogin",true);
                            editor.apply();*/
                            editor.putString("id", jsonObject1.getString("id"));
                            editor.putString("first_name", jsonObject1.getString("first_name"));
                            editor.putString("last_name", jsonObject1.getString("last_name"));
                            editor.putString("email", jsonObject1.getString("email"));
                            editor.putString("mobile", jsonObject1.getString("mobile"));
                            editor.putString("country", jsonObject1.getString("country"));
                            editor.putString("state", jsonObject1.getString("state"));
                            editor.putString("city", jsonObject1.getString("city"));
                            editor.putString("postal_code", jsonObject1.getString("postal_code"));
                            editor.putString("birthday", jsonObject1.getString("birthday"));
                            editor.putString("profile_img", jsonObject1.getString("profile_img"));
                            editor.putString("created_at", jsonObject1.getString("created_at"));
                            editor.putString("updated_at", jsonObject1.getString("updated_at"));
                            editor.putString("token", token.token);
                            editor.putBoolean("isLogin",true);
                            editor.apply();

                          startActivity(new Intent(ActivityLogin.this, ActivityMaster.class));
                          finish();
                        }
                        else
                            {
                                //Toasty.warning(ActivityLogin.this, "Beware of the dog.", Toast.LENGTH_SHORT, true).show();
                                //f1.setVisibility(View.VISIBLE);
                                //f1.setText("Invalid email or password");
                                Toasty.error(ActivityLogin.this, "Invalid email or password", Toast.LENGTH_SHORT, true).show();
                                if (pd.isShowing())
                                {
                                    pd.dismiss();
                                }
                            }
                    } catch (JSONException e)
                    {

                        if (pd.isShowing()) {
                            pd.dismiss();
                        }
                        e.printStackTrace();
                    }

                }
            });
        }catch (Exception e)
        {
            Log.d("Stttttt", "ERROR " + e.getMessage());
        }
    }

    private void action() {

        pass.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                login_btn.performClick();
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    return true;
                }
                return false;
            }
        });
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if (validate())
                {
                    Declaration.user_ph_or_email=emainandphoneno.getText().toString().trim();
                    Declaration.user_pass=pass.getText().toString().trim();
                    try
                    {
                        if (pd!=null)
                        {
                            pd.show();
                        }
                        login_btn.setClickable(false);
                        getLoginData();
                    } catch (JSONException e)
                    {
                        e.printStackTrace();
                    }
                }

            }
        });
        forgot_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ActivityLogin.this, ActivityForgotPasssword.class));
            }
        });

    }

    private boolean validate() {
        f1.setVisibility(View.GONE);
        if(!(emainandphoneno.getText().toString().trim().length()>0))
        {
            Toasty.error(ActivityLogin.this, "Field should not be empty", Toast.LENGTH_SHORT, true).show();
          //f1.setText("Field should not be empty");
          //f1.setVisibility(View.VISIBLE);
          return false;
        }
        else {
            f1.setVisibility(View.GONE);
        }
        /*if(!((emainandphoneno.getText().toString().trim().matches(ValidateString.Expn)) || emainandphoneno.getText().toString().trim().matches(ValidateString.pattern) ))
         //if(!((emainandphoneno.getText().toString().trim().matches(ValidateString.Expn))  ))
        {
            Toasty.error(ActivityLogin.this, "Invalid email", Toast.LENGTH_SHORT, true).show();
          //f1.setText("Invalid email");
           // f1.setVisibility(View.VISIBLE);
          return false;
        }
        else {
            f1.setVisibility(View.GONE);

        }*/

        if(!(pass.getText().toString().trim().length()>0))
        {
           // f2.setText("Password field must not be blank");
           // f2.setVisibility(View.VISIBLE);
            Toasty.error(ActivityLogin.this, "Password field must not be blank", Toast.LENGTH_SHORT, true).show();
            return false;
        }
        else {
            f2.setVisibility(View.GONE);
        }
        /*if(!((pass.getText().toString().trim().length()>5) && (pass.getText().toString().trim().length()<21)))
        {
            f2.setText("The password Must be 6 to 20 characters in length");
            f2.setVisibility(View.VISIBLE);
            return false;
        }
        else
            {
                f2.setVisibility(View.GONE);
            }*/

       /* if(!((pass.getText().toString().trim().matches(ValidateString.pattern_pass))  ))
        {
            f2.setText("Must contain at least one letter and one number and a special character from !@#$%^&*()_+");
            f2.setVisibility(View.VISIBLE);
            return false;
        }
        else
            {
                f2.setVisibility(View.GONE);
            }*/
        return true;
    }

    private void init()
    {
        emainandphoneno=(EditText)findViewById(R.id.emainandphoneno);
        f1=(TextView)findViewById(R.id.f1);
        f2=(TextView)findViewById(R.id.f2);
        login_btn=(Button) findViewById(R.id.login_btn);
        pass=(EditText) findViewById(R.id.pass);
        pan=(LinearLayout)findViewById(R.id.pan);
        forgot_password=(TextView) findViewById(R.id.fp);
    }

    protected void checkPermission()
    {
        if(ContextCompat.checkSelfPermission(mActivity, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                +ContextCompat.checkSelfPermission(mActivity, Manifest.permission.ACCESS_FINE_LOCATION)
                + ContextCompat.checkSelfPermission(
                mActivity,Manifest.permission.ACCESS_COARSE_LOCATION)

                != PackageManager.PERMISSION_GRANTED){

            // Do something, when permissions not granted
            if(  ActivityCompat.shouldShowRequestPermissionRationale(
                    mActivity,Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    ||ActivityCompat.shouldShowRequestPermissionRationale(
                    mActivity,Manifest.permission.ACCESS_FINE_LOCATION)
                    || ActivityCompat.shouldShowRequestPermissionRationale(
                    mActivity,Manifest.permission.ACCESS_COARSE_LOCATION))
            {
                AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
                builder.setMessage("Location" +
                        " ");
                builder.setTitle("Please grant Location");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ActivityCompat.requestPermissions(
                                mActivity,
                                new String[]{
                                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                        Manifest.permission.ACCESS_FINE_LOCATION,
                                        Manifest.permission.ACCESS_COARSE_LOCATION

                                },
                                MY_PERMISSIONS_REQUEST_CODE
                        );
                    }
                });
                builder.setNeutralButton("Cancel",null);
                AlertDialog dialog = builder.create();
                dialog.show();
            }else{
                // Directly request for required permissions, without explanation
                ActivityCompat.requestPermissions(
                        mActivity,
                        new String[]{
                                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                Manifest.permission.ACCESS_FINE_LOCATION,
                                Manifest.permission.ACCESS_COARSE_LOCATION,

                        },
                        MY_PERMISSIONS_REQUEST_CODE
                );
            }
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults)
    {
        switch (requestCode){
            case MY_PERMISSIONS_REQUEST_CODE:{
                // When request is cancelled, the results array are empty
                if(
                        (grantResults.length >0) &&
                                (grantResults[0]
                                        + grantResults[1]+ grantResults[2]

                                        == PackageManager.PERMISSION_GRANTED
                                )
                ){
                    //Permissions granted
                }else
                {

                    Toast.makeText(mActivity,"Permissions denied.",Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
    }
    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    private void getSecurityQuestion() throws JSONException
    {

        try {
            mainActivityViewModel.getSecurityQuestion().observe(activity, new Observer<JSONObject>()
            {
                @Override
                public void onChanged(JSONObject s)
                {

                    try {
                        if (s.getBoolean("status"))
                        {

                            final JSONArray jsonArray = s.getJSONArray("data");
                            if (jsonArray.length() > 0) {
                                com.wishster.model.SecurityQuestionModel.questionJsondata=jsonArray;
                                com.wishster.model.SecurityQuestionModel.totalQuestion=jsonArray.length();
                                //startActivity(new Intent(ActivitySignUp.this,ActivitySecurityQuestion.class));

                                editor.putString("questionJsondata", jsonArray.toString());
                                editor.putInt("totalQuestion", jsonArray.length());
                                editor.putBoolean("hasQuestion",true);
                                editor.putBoolean("isReg",true);
                                editor.apply();
                                startActivity(new Intent(ActivityLogin.this,ActivitySecurityQuestion.class));
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            });
        }catch (Exception e)
        {
            Log.d("Stttttt", "ERROR " + e.getMessage());
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (pd.isShowing()) {
            pd.dismiss();
        }

    }

    @Override
    protected void onPause()
    {
        super.onPause();
        if (pd.isShowing())
        {
            pd.dismiss();
        }
    }
}
