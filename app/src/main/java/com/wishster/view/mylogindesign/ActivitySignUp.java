package com.wishster.view.mylogindesign;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.wishster.database.AssetDatabaseOpenHelper;
import com.wishster.database.DatabaseHandler;
import com.wishster.database.DbHandler;
import com.wishster.model.TopExceptionHandler;
import com.wishster.model.dialog_model.TransparentProgressDialog;
import com.wishster.model.internet_chacking.CheckInternet;
import com.wishster.model.login_and_signup_module.CountrycodeAndStateCode.CountryCodeAndStateCode;

import com.wishster.model.login_and_signup_module.validation.FocusValidation;
import com.wishster.view.nointernet.ActivityNoActivity;
import com.wishster.view.security_question.ActivitySecurityQuestion;
import com.wishster.view.R;
import com.wishster.viewmodel.MainActivityViewModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.logging.HttpLoggingInterceptor;
import pub.devrel.easypermissions.EasyPermissions;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

import static com.wishster.model.ApiClient_sandree.getUnsafeOkHttpClient;

public class ActivitySignUp extends AppCompatActivity {
    //****
    private static final int REQUEST_LOCATION = 1;
    Button btnGetLocation;
    TextView showLocation;
    LocationManager locationManager;
    String latitude, longitude;
    String city = "";
    String state = "";
    String country = "";
    String postalCode = "";
    //*****
    private static final int REQUEST_GALLERY_CODE = 200;
    private static final int READ_REQUEST_CODE = 300;
    private static final int REQUEST_SELECT_PHONE_NUMBER=99;
    private Uri uri;
    String TAG="addpayment_page";
    MultipartBody.Part fileToUpload;
    private DatePickerDialog fromDatePickerDialog;
    private SimpleDateFormat dateFormatter;
    CircleImageView profile_image;
    EditText fname;
    TextView birthday;

    EditText pcode;
    public ArrayList<String> country_list,state_list,city_list;
    ArrayAdapter<String> adapter3,adapter4;
    EditText ph;
    EditText password,cpassword;
    EditText email;
    EditText lname;
    Button next,back_btn;
    CheckBox checkBox;
    DbHandler db2;
    static  String filePath="";
    CircleImageView imageView_profile;
    ImageView image_add_icon;
    private Service2 uploadService2;
   //ProgressDialog progressDialog;
    ActivitySignUp activity;
    TextView login_btn;
    MainActivityViewModel mainActivityViewModel;
    static Spinner c_id,s_id,ct_id;
    static  ArrayAdapter<String> dataAdapter3,dataAdapter4;
    LinearLayout pan,social_icon_pan;
    RelativeLayout image_pan;
    TextView image_text_pan;
    private int mYear, mMonth, mDay, mHour, mMinute;
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    private TransparentProgressDialog pd;
    TextView error_data;
    AutoCompleteTextView country_t,state_t,city_t;
    ArrayAdapter<String> adapter2,adapter_city;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Thread.setDefaultUncaughtExceptionHandler(new TopExceptionHandler(this));
        DatabaseHandler db = new DatabaseHandler(this);
        db.addContact();
        activity=this;
        pd = new TransparentProgressDialog(this, R.drawable.dialog_image);
        pref = getApplicationContext().getSharedPreferences(getString(R.string.SESSION), MODE_PRIVATE);
        editor = pref.edit();
        if(!(new CheckInternet(this).getInternetStatus()))
        {
            startActivity(new Intent(this, ActivityNoActivity.class));
            finish();
        }
        init();
        //location start
        pan.setOnClickListener(
        new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                hideKeyboard(activity);
            }
        });
        image_pan.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                hideKeyboard(activity);
            }
        });
        image_text_pan.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                hideKeyboard(activity);
            }
        });
        social_icon_pan.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                hideKeyboard(activity);
            }
        });

       /* ActivityCompat.requestPermissions( this,
                new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);*/
        /*new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                {
                    locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                    if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                        OnGPS();
                    } else
                        {
                            try
                            {
                                getLocation();
                            }
                            catch (IOException e)
                            {
                                e.printStackTrace();
                            }
                        }
                }

            }
        }, 100);*/

        //location end

        login_btn=(TextView)findViewById(R.id.login_btn);
        mainActivityViewModel= ViewModelProviders.of(this).get(MainActivityViewModel.class);

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        // Change base URL to your upload server URL.
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(1, TimeUnit.MINUTES)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(15, TimeUnit.SECONDS)
                .build();
        uploadService2 = new Retrofit.Builder()
                .baseUrl(getString(R.string.baseurl))
               // .client(okHttpClient)
                .client(getUnsafeOkHttpClient() )
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
                .create(Service2.class);

       /* progressDialog = new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        progressDialog.setTitle("Please wait");
        progressDialog.setMessage("Loading dictionary file...");*/
        try
        {
            action();
        } catch (JSONException e)
        {
            e.printStackTrace();
        }
       /* try {
            getCountry();
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        AssetDatabaseOpenHelper abc=new AssetDatabaseOpenHelper(ActivitySignUp.this);
        abc.openDatabase();
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable()
                {
            @Override
            public void run() {
                db2= new DbHandler(ActivitySignUp.this);
                try {
                    countryListFunction2();
;                } catch (JSONException e) {
                    e.printStackTrace();
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, 100);

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                finish();
            }
        });
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        birthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {


                // Get Current Date
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog = new DatePickerDialog(activity,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth)
                            {

                                birthday.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();

            }
        });
        try {
            getSecurityQuestion();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }




    private void action() throws JSONException
    {

        pcode.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                next.performClick();
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {

                    return true;
                }
                return true;
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (filePath.equals(""))
                if (validate())
                {

                    if (checkBox.isChecked())
                    {
                        callSignUp();
                        /*
                        c_id=(Spinner) findViewById(R.id.c_id);
                        s_id=(Spinner) findViewById(R.id.s_id);
                        ct_id=(Spinner) findViewById(R.id.ct_id);
                        // next.setClickable(false);
                        Declaration.first_name=fname.getText().toString();
                        Declaration.last_name=lname.getText().toString();
                        Declaration.email=email.getText().toString();
                        Declaration.password=password.getText().toString();
                        Declaration.mobile=ph.getText().toString();
                        Declaration.country=c_id.getSelectedItem().toString();
                        Declaration.state=s_id.getSelectedItem().toString();
                        Declaration.city=ct_id.getSelectedItem().toString();
                        Declaration.postal_code=pcode.getText().toString();
                        Declaration.birthday=birthday.getText().toString();
                        try
                        {
                            getSignUpData();

                        }
                        catch (JSONException e)
                        {
                            e.printStackTrace();
                        }*/
                    }

                    else
                        Toast.makeText(ActivitySignUp.this, "Select terms and conditions", Toast.LENGTH_SHORT).show();
                }
                else {
                    //Toast.makeText(ActivitySignUp.this, "Invalid", Toast.LENGTH_SHORT).show();
                }
            }
        });
        birthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDatePicker();
            }
        });
      //  countryListFunction();
        image_add_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                profile_image_select();
            }
        });
        focusValidation();
    }
    boolean isFOCUS(){

        if((((fname.getText().toString().length())==0) &&
                ((lname.getText().toString().length())==0) &&
                ((birthday.getText().toString().length())==0) &&
                ((ph.getText().toString().length())==0) &&
                ((email.getText().toString().length())==0) &&
                ((password.getText().toString().length())==0) &&
                ((cpassword.getText().toString().length())==0) &&
                ((country_t.getText().toString().length())==0) &&
                ((state_t.getText().toString().length())==0) &&
                ((city_t.getText().toString().length())==0) &&
                ((pcode.getText().toString().length())==0) )){
            return false;
        }
        else {
            return true;
        }

    }
    private void focusValidation() {



        Log.d("lengthCHK","AA"+isFOCUS());


        fname.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (isFOCUS()) {
                    new FocusValidation().Validate(((TextView) findViewById(R.id.error_data_fname)));
                }
                return false;
            }
        });

        lname.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (isFOCUS()) {
                    new FocusValidation().Validate(((TextView) findViewById(R.id.error_data_lname)));
                    new FocusValidation().Validate(fname, ((TextView) findViewById(R.id.error_data_fname)), "First Name");
                }

                return false;
            }
        });
        birthday.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (isFOCUS()) {
                    new FocusValidation().Validate(((TextView) findViewById(R.id.error_data_birthdate)));
                    new FocusValidation().Validate(fname, ((TextView) findViewById(R.id.error_data_fname)), "First Name");
                    new FocusValidation().Validate(lname, ((TextView) findViewById(R.id.error_data_lname)), "Last  Name");
//error_data_birthdate
                }
                return false;
            }
        });
        ph.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (isFOCUS()) {
                    new FocusValidation().Validate(((TextView) findViewById(R.id.error_data_ph)));
                    new FocusValidation().Validate(fname, ((TextView) findViewById(R.id.error_data_fname)), "First Name");
                    new FocusValidation().Validate(lname, ((TextView) findViewById(R.id.error_data_lname)), "Last  Name");
                    new FocusValidation().Validate(birthday, ((TextView) findViewById(R.id.error_data_birthdate)), "Birthday");
                }
                return false;
            }
        });
        email.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (isFOCUS()) {
                    new FocusValidation().Validate(((TextView) findViewById(R.id.error_data)));
                    new FocusValidation().Validate(fname, ((TextView) findViewById(R.id.error_data_fname)), "First Name");
                    new FocusValidation().Validate(lname, ((TextView) findViewById(R.id.error_data_lname)), "Last  Name");
                    new FocusValidation().Validate(birthday, ((TextView) findViewById(R.id.error_data_birthdate)), "Birthday ");
                    new FocusValidation().Validate(ph, ((TextView) findViewById(R.id.error_data_ph)), "Mobile No");
                }
                return false;
            }
        });
        password.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (isFOCUS()) {
                    new FocusValidation().Validate(((TextView) findViewById(R.id.error_data_pass)));
                    new FocusValidation().Validate(fname, ((TextView) findViewById(R.id.error_data_fname)), "First Name");
                    new FocusValidation().Validate(lname, ((TextView) findViewById(R.id.error_data_lname)), "Last  Name");
                    new FocusValidation().Validate(birthday, ((TextView) findViewById(R.id.error_data_birthdate)), "Birthday ");
                    new FocusValidation().Validate(ph, ((TextView) findViewById(R.id.error_data_ph)), "Mobile No");
                    new FocusValidation().Validate(email, ((TextView) findViewById(R.id.error_data)), "Email");
                }
                return false;
            }
        });
        cpassword.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (isFOCUS()) {
                    new FocusValidation().Validate(((TextView) findViewById(R.id.error_data_cpass)));
                    new FocusValidation().Validate(fname, ((TextView) findViewById(R.id.error_data_fname)), "First Name");
                    new FocusValidation().Validate(lname, ((TextView) findViewById(R.id.error_data_lname)), "Last  Name");
                    new FocusValidation().Validate(birthday, ((TextView) findViewById(R.id.error_data_birthdate)), "Birthday ");
                    new FocusValidation().Validate(ph, ((TextView) findViewById(R.id.error_data_ph)), "Mobile No");
                    new FocusValidation().Validate(email, ((TextView) findViewById(R.id.error_data)), "Email");
                    new FocusValidation().Validate(password, ((TextView) findViewById(R.id.error_data_pass)), "Password ");
                }
                return false;
            }
        });
        country_t.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (isFOCUS()) {
                    new FocusValidation().Validate(((TextView) findViewById(R.id.error_data_country_t)));
                    new FocusValidation().Validate(fname, ((TextView) findViewById(R.id.error_data_fname)), "First Name");
                    new FocusValidation().Validate(lname, ((TextView) findViewById(R.id.error_data_lname)), "Last  Name");
                    new FocusValidation().Validate(birthday, ((TextView) findViewById(R.id.error_data_birthdate)), "Birthday ");
                    new FocusValidation().Validate(ph, ((TextView) findViewById(R.id.error_data_ph)), "Mobile No");
                    new FocusValidation().Validate(email, ((TextView) findViewById(R.id.error_data)), "Email");
                    new FocusValidation().Validate(password, ((TextView) findViewById(R.id.error_data_pass)), "Password ");
                    new FocusValidation().Validate(cpassword, ((TextView) findViewById(R.id.error_data_cpass)), "Confirm Password");
                }
                return false;
            }
        });
        state_t.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (isFOCUS()) {
                    new FocusValidation().Validate(((TextView) findViewById(R.id.error_data_state_t)));
                    new FocusValidation().Validate(fname, ((TextView) findViewById(R.id.error_data_fname)), "First Name");
                    new FocusValidation().Validate(lname, ((TextView) findViewById(R.id.error_data_lname)), "Last  Name");
                    new FocusValidation().Validate(birthday, ((TextView) findViewById(R.id.error_data_birthdate)), "Birthday ");
                    new FocusValidation().Validate(ph, ((TextView) findViewById(R.id.error_data_ph)), "Mobile No");
                    new FocusValidation().Validate(email, ((TextView) findViewById(R.id.error_data)), "Email");
                    new FocusValidation().Validate(password, ((TextView) findViewById(R.id.error_data_pass)), "Password ");
                    new FocusValidation().Validate(cpassword, ((TextView) findViewById(R.id.error_data_cpass)), "Confirm Password");
                    new FocusValidation().Validate(country_t, ((TextView) findViewById(R.id.error_data_country_t)), "Country ");
                }
                return false;
            }
        });
        city_t.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (isFOCUS()) {
                    new FocusValidation().Validate(((TextView) findViewById(R.id.error_data_city_t)));
                    new FocusValidation().Validate(fname, ((TextView) findViewById(R.id.error_data_fname)), "First Name");
                    new FocusValidation().Validate(lname, ((TextView) findViewById(R.id.error_data_lname)), "Last  Name");
                    new FocusValidation().Validate(birthday, ((TextView) findViewById(R.id.error_data_birthdate)), "Birthday ");
                    new FocusValidation().Validate(ph, ((TextView) findViewById(R.id.error_data_ph)), "Mobile No");
                    new FocusValidation().Validate(email, ((TextView) findViewById(R.id.error_data)), "Email");
                    new FocusValidation().Validate(password, ((TextView) findViewById(R.id.error_data_pass)), "Password ");
                    new FocusValidation().Validate(cpassword, ((TextView) findViewById(R.id.error_data_cpass)), "Confirm Password");
                    new FocusValidation().Validate(country_t, ((TextView) findViewById(R.id.error_data_country_t)), "Country ");
                    new FocusValidation().Validate(state_t, ((TextView) findViewById(R.id.error_data_state_t)), "state/territory");
                }
                return false;
            }
        });
        pcode.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (isFOCUS()) {
                    new FocusValidation().Validate(((TextView) findViewById(R.id.error_data_pcode_t)));
                    new FocusValidation().Validate(fname, ((TextView) findViewById(R.id.error_data_fname)), "First Name");
                    new FocusValidation().Validate(lname, ((TextView) findViewById(R.id.error_data_lname)), "Last  Name");
                    new FocusValidation().Validate(birthday, ((TextView) findViewById(R.id.error_data_birthdate)), "Birthday ");
                    new FocusValidation().Validate(ph, ((TextView) findViewById(R.id.error_data_ph)), "Mobile No");
                    new FocusValidation().Validate(email, ((TextView) findViewById(R.id.error_data)), "Email");
                    new FocusValidation().Validate(password, ((TextView) findViewById(R.id.error_data_pass)), "Password ");
                    new FocusValidation().Validate(cpassword, ((TextView) findViewById(R.id.error_data_cpass)), "Confirm Password");
                    new FocusValidation().Validate(country_t, ((TextView) findViewById(R.id.error_data_country_t)), "Country ");
                    new FocusValidation().Validate(state_t, ((TextView) findViewById(R.id.error_data_state_t)), "state/territory");

                    new FocusValidation().Validate(city_t, ((TextView) findViewById(R.id.error_data_city_t)), "City");
                }
                return false;
            }
        });




    }

    void citySetData(final String s, final String toTitleCase){

        if (city_list!=null){
            city_list.clear();
        }
        if (adapter_city!=null){
            adapter_city.notifyDataSetChanged();
        }
        ;

        //Log.d("sssssssssCCCCC",""+db2.CountryCode(s));

        city_list.addAll(db2.GetAllCity(CountryCodeAndStateCode.Statecode));
                adapter_city = new ArrayAdapter<String>
                (ActivitySignUp.this,android.R.layout.select_dialog_item,city_list);

        city_t.setThreshold(1);//will start working from first character
        city_t.setAdapter(adapter_city);//setting the adapter data into the AutoCompleteTextView
       // city_t.setTextColor(getResources().getColor(R.color.hint_text_color));

    }
  private void countryListFunction2() throws JSONException
  {
      country_list=new ArrayList<String>();
      state_list=new ArrayList<String>();
      city_list=new ArrayList<String>();
      city_list.clear();
      country_list.clear();
      country_list.add("Country");
      country_list.addAll(db2.GetAllCountry());
      int index=0;
      try {
          index=country_list.indexOf(new TitleCase().toTitleCase(country.trim()));

      }catch (Exception e){

      }


      ArrayAdapter<String> adapter = new ArrayAdapter<String>
              (this,android.R.layout.select_dialog_item,country_list);
      //Getting the instance of AutoCompleteTextView

      country_t.setThreshold(1);//will start working from first character
      country_t.setAdapter(adapter);//setting the adapter data into the AutoCompleteTextView
      //country_t.setTextColor(getResources().getColor(R.color.hint_text_color));


      country_t.addTextChangedListener(new TextWatcher() {
          @Override
          public void beforeTextChanged(CharSequence s, int start, int count, int after) {

          }

          @Override
          public void onTextChanged(CharSequence s, int start, int before, int count)
          {}

          @Override
          public void afterTextChanged(Editable s) {
              if (country_t.getText().toString().length()>0) {
                  if (state_list!=null)
                  {
                      state_list.clear();

                  }
                  if (city_list!=null) {
                      city_list.clear();

                  }
                  CountryCodeAndStateCode.Countrycode=db2.CountryCode(new TitleCase().toTitleCase(country_t.getText().toString()).toString().trim());
                  state_list.addAll(db2.GetAllState(CountryCodeAndStateCode.Countrycode));
                  adapter2 = new ArrayAdapter<String>
                          (ActivitySignUp.this,android.R.layout.select_dialog_item,state_list);
                  if (adapter2!=null){
                      adapter2.notifyDataSetChanged();
                  }
                  state_t.setThreshold(1);//will start working from first character
                  state_t.setAdapter(adapter2);//setting the adapter data into the AutoCompleteTextView
                  //state_t.setTextColor(getResources().getColor(R.color.hint_text_color));
                }
              else
                  Toast.makeText(activity, "Input Country", Toast.LENGTH_SHORT).show();
          }
      });
      state_t.addTextChangedListener(new TextWatcher() {
          @Override
          public void beforeTextChanged(CharSequence s, int start, int count, int after) {

          }

          @Override
          public void onTextChanged(CharSequence s, int start, int before, int count) {

          }

          @Override
          public void afterTextChanged(Editable s)
          {
              CountryCodeAndStateCode.Statecode=db2.StateCode(CountryCodeAndStateCode.Countrycode,new TitleCase().toTitleCase(s.toString().trim()));
              new Handler().postDelayed(new Runnable()
              {
                  @Override
                  public void run()
                  {
                      citySetData("","");
                  }
              },100);
          }
      });





      pcode.setText(postalCode);
      ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(activity, R.layout.spinner_second, country_list);
      dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
      c_id.setAdapter(dataAdapter2);

      c_id.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
      {
          @Override
          public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
              if (state_list!=null)
              {
                  state_list.clear();
                  if (dataAdapter3!=null){
                      dataAdapter3.notifyDataSetChanged();
                  }
              }
              if (city_list!=null) {
                  city_list.clear();
                  if (dataAdapter4!=null)
                  {
                      dataAdapter4.notifyDataSetChanged();
                  }
              }

            if (position!=0){
                stateListCall(country_list.get(position));
            }


          }

          @Override
          public void onNothingSelected(AdapterView<?> parent) {

          }
      });
      c_id.setSelection(index);
      stateListCall(country_list.get(index));
  }
  void stateListCall(final String s){
      int index=0;
      state_list.clear();
      state_list.add("State/Territory");
      state_list.addAll(db2.GetAllState(db2.CountryCode(s.toString().trim())));

      dataAdapter3= new ArrayAdapter<String>(activity, R.layout.spinner_second, state_list);
      dataAdapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
      s_id.setAdapter(dataAdapter3);

      try {
            index=state_list.indexOf(new TitleCase().toTitleCase(state.trim()));
            cityListCall(db2.CountryCode(s.toString().trim()),state_list.get(index));
            }catch (Exception e){}
      s_id.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
          @Override
          public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
          {
           cityListCall(db2.CountryCode(s.toString().trim()),state_list.get(position));
          }

          @Override
          public void onNothingSelected(AdapterView<?> parent)
          {}
      });
      s_id.setSelection(index);
  }
  void cityListCall(String countrycode, String statename){
      int index=0;
      city_list=new ArrayList<String>();
      city_list.clear();
      if (dataAdapter4!=null)
      {
          dataAdapter4.notifyDataSetChanged();
      }
      city_list.add("Select City");

      city_list.addAll(db2.GetAllCity(db2.StateCode(countrycode,statename)));
      try {
          index=city_list.indexOf(new TitleCase().toTitleCase(city.trim()));
      }catch (Exception e){

      }
      if (dataAdapter4!=null)
      {
          dataAdapter4.notifyDataSetChanged();
      }
      else {

          {

          }
      }
      dataAdapter4= new ArrayAdapter<String>(activity, R.layout.spinner_second, city_list);
      dataAdapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
      ct_id.setAdapter(dataAdapter4);
      ct_id.setSelection(index);
  }


    private void setDatePicker() {
        Calendar newCalendar = Calendar.getInstance();
        fromDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                birthday.setText(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
    }

    private boolean validate() {

        if (!(fname.getText().toString().trim().length()>0))
        {

          fname.setError("Field should not be empty");
            fname.requestFocus();
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(fname, InputMethodManager.SHOW_IMPLICIT);
          return false;
        }





        if (!(fname.getText().toString().trim().matches(ValidateString.pattern_name)))
        {

          fname.setError("First name field required");
            fname.requestFocus();
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(fname, InputMethodManager.SHOW_IMPLICIT);
            return false;
        }



        if (!(lname.getText().toString().length()>0))
        {
            lname.setError("Field should not be empty");
            lname.requestFocus();
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(lname, InputMethodManager.SHOW_IMPLICIT);
            return false;
        }

        if (!(lname.getText().toString().trim().matches(ValidateString.pattern_name)))
        {

            lname.setError("Last  name field required");
            lname.requestFocus();
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(lname, InputMethodManager.SHOW_IMPLICIT);
            return false;
        }

        if (!(birthday.getText().toString().length()>0))
        {
            birthday.setError("Field should not be empty");
            birthday.requestFocus();
            /*InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(birthday, InputMethodManager.SHOW_IMPLICIT);*/
            return false;
        }

        if (!(ph.getText().toString().length()>0))
        {
            ph.setError("Field should not be empty");
            ph.requestFocus();
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(ph, InputMethodManager.SHOW_IMPLICIT);
            return false;
        }

        if (!(ph.getText().toString().trim().matches(ValidateString.pattern)))
        {

            ph.setError("Invalid Mobile No.");
            ph.requestFocus();
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(ph, InputMethodManager.SHOW_IMPLICIT);
            return false;
        }

        if (!(email.getText().toString().length()>0))
        {
            email.setError("Field should not be empty");
            email.requestFocus();
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(email, InputMethodManager.SHOW_IMPLICIT);
            return false;
        }

        if (!(email.getText().toString().trim().matches(ValidateString.Expn)))
        {

            email.setError("Valid Email required");
            email.requestFocus();
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(email, InputMethodManager.SHOW_IMPLICIT);
            return false;
        }


        if (!(password.getText().toString().length()>0))
        {
            password.setError("Field should not be empty");
            password.requestFocus();
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(password, InputMethodManager.SHOW_IMPLICIT);
            return false;
        }


        if(!((password.getText().toString().trim().length()>7) && (password.getText().toString().trim().length()<21)))
        {

            password.setError("The password Must be 8 to 20 characters in length");
            password.requestFocus();
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(password, InputMethodManager.SHOW_IMPLICIT);
            return false;
        }


        if(!((password.getText().toString().trim().matches(ValidateString.pattern_pass))  ))
        {
            password.setError("Must contain at least one letter and one number and a special character from !@#$%^&*()_+");
            password.requestFocus();
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(password, InputMethodManager.SHOW_IMPLICIT);

            return false;
        }

        /*if(!((cpassword.getText().toString().trim().length()==0)  ))
        {
            cpassword.setError("Field should not be empty");


            return false;
        }*/
        if(!((cpassword.getText().toString().trim().equals(password.getText().toString().trim()))  ))
        {
            cpassword.setError("password not match ");

            cpassword.requestFocus();
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(cpassword, InputMethodManager.SHOW_IMPLICIT);
            return false;
        }
        if (!(email.getText().toString().trim().matches(ValidateString.Expn)))
        {

            email.setError("Valid email required");
            email.requestFocus();
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(email, InputMethodManager.SHOW_IMPLICIT);
            return false;
        }






        /*if ((c_id.getSelectedItem().toString().trim().equals("Country")))
        {
            Toast.makeText(activity, "Select country", Toast.LENGTH_SHORT).show();
            c_id.requestFocus();
            return false;
        }*/
        if ((country_t.getText().toString().trim().length()==0))
        {
            Toast.makeText(activity, "Country name required", Toast.LENGTH_SHORT).show();
            country_t.setError("Country name required");
            country_t.requestFocus();
            return false;
        }
        if ((state_t.getText().toString().trim().length()==0))
        {
            Toast.makeText(activity, "Country name required", Toast.LENGTH_SHORT).show();
            state_t.setError("State/Territory name required");
            state_t.requestFocus();
            return false;
        }
        if ((city_t.getText().toString().trim().length()==0))
        {
            Toast.makeText(activity, "City name required", Toast.LENGTH_SHORT).show();
            city_t.setError("Country name required");
            city_t.requestFocus();
            return false;
        }



        /*try {
            if ((s_id.getSelectedItem().toString().trim().equals("State/Territory"))) {
                Toast.makeText(activity, "Select State/Territory", Toast.LENGTH_SHORT).show();
                c_id.requestFocus();
                return false;
            }
        }catch (Exception e){
            Toast.makeText(activity, "Select State/Territory", Toast.LENGTH_SHORT).show();

            return false;
        }
        try {
            if ((ct_id.getSelectedItem().toString().trim().equals("Select City"))) {
                Toast.makeText(activity, "Select City", Toast.LENGTH_SHORT).show();
                ct_id.requestFocus();
                return false;
            }
        }catch (Exception e){
            Toast.makeText(activity, "Select City", Toast.LENGTH_SHORT).show();

            return false;
        }*/

        if (!(pcode.getText().toString().length()>0))
        {
            pcode.setError("Field should not be empty");
            pcode.requestFocus();
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(pcode, InputMethodManager.SHOW_IMPLICIT);
            return false;
        }


/*

        if (!(country_t.getText().toString().length()>0))
        {
            country_t.setError("Field should not be empty");
          return false;
        }
        if (!db2.chk_country((new TitleCase().toTitleCase(country_t.getText().toString().trim()))))
        {
            country_t.setError("Invalid");
            return false;
        }
        if (!(state_t.getText().toString().length()>0))
        {
            state_t.setError("Field should not be empty");
            return false;
        }

        if (!(db2.chk_state(new TitleCase().toTitleCase(state_t.getText().toString().trim()),db2.CountryCode(country_t.getText().toString().trim()))))
        {
            state_t.setError("Invalid");
            return false;
        }

        if (!(city_t.getText().toString().length()>0))
        {
            city_t.setError("Field should not be empty");
            return false;
        }
        if (!(db2.chk_city(new TitleCase().toTitleCase(city_t.getText().toString().trim()),db2.StateCode(db2.CountryCode(country_t.getText().toString().trim()),new TitleCase().toTitleCase(state_t.getText().toString().trim()))  )))
        {
            city_t.setError("Invalid");
            return false;
        }
        if (!(pcode.getText().toString().trim().matches(ValidateString.uszip_code)))
        {

            pcode.setError("Invalid ");
            return false;
        }*/
      return true;
    }


    private void init()
                {
                error_data=(TextView)findViewById(R.id.error_data);
                pan=(LinearLayout)findViewById(R.id.pan);
                social_icon_pan=(LinearLayout)findViewById(R.id.social_icon_pan);
                image_pan=(RelativeLayout) findViewById(R.id.image_pan);
                image_text_pan=(TextView) findViewById(R.id.image_text_pan);
                c_id=(Spinner) findViewById(R.id.c_id);
                country_t=(AutoCompleteTextView) findViewById(R.id.country_t);
                state_t=(AutoCompleteTextView) findViewById(R.id.state_t);
                city_t=(AutoCompleteTextView) findViewById(R.id.city_t);
                s_id=(Spinner) findViewById(R.id.s_id);
                ct_id=(Spinner) findViewById(R.id.ct_id);
                checkBox=(CheckBox) findViewById(R.id.checkBox);
                next=(Button) findViewById(R.id.next);
                back_btn=(Button) findViewById(R.id.back_btn);
                profile_image=(CircleImageView)findViewById(R.id.profile_image);
                birthday=(TextView)findViewById(R.id.birthday);

                pcode=(EditText)findViewById(R.id.pcode);
                ph=(EditText)findViewById(R.id.ph);
                password=(EditText)findViewById(R.id.password);
                cpassword=(EditText)findViewById(R.id.cpassword);
                email=(EditText)findViewById(R.id.email);
                lname=(EditText)findViewById(R.id.lname);
                fname=(EditText)findViewById(R.id.fname);
                imageView_profile=(CircleImageView)findViewById(R.id.profile_image);
                image_add_icon=(ImageView) findViewById(R.id.add_image);
                }




    //for image
    private void profile_image_select()
    {
        Intent openGalleryIntent = new Intent(Intent.ACTION_PICK);
        openGalleryIntent.setType("image/*");
        startActivityForResult(openGalleryIntent, REQUEST_GALLERY_CODE);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_GALLERY_CODE && resultCode == Activity.RESULT_OK) {
            uri = data.getData();
            if (EasyPermissions.hasPermissions(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {

                filePath = getRealPathFromURIPath(uri, ActivitySignUp.this);
                File file = new File(filePath);
                //Log.d(TAG, "filePath=" + filePath);
                //RequestBody mFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                RequestBody mFile = RequestBody.create(MediaType.parse("image/*"), file);
                fileToUpload = MultipartBody.Part.createFormData("profile_img", file.getName(), mFile);
                RequestBody filename = RequestBody.create(MediaType.parse("text/plain"), file.getName());
                try
                {
                    BitmapFactory.Options bmOptions = new BitmapFactory.Options();
                    Bitmap image = BitmapFactory.decodeFile(file.getAbsolutePath(), bmOptions);
                    imageView_profile.setImageBitmap(image);

                }
                catch (Exception e)
                {}
                next.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v) {
                        next.setClickable(false);

                        if (!(filePath.equals(""))){
                            if (validate()) {

                                if (checkBox.isChecked())
                                {
                                    callSignUp();
                                }
                                else
                                    Toast.makeText(ActivitySignUp.this, "Select terms and conditions", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                Toast.makeText(ActivitySignUp.this, "Invalid", Toast.LENGTH_SHORT).show();
                            }
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    next.setClickable(true);
                                }
                            },5000);

                    }
                    }
                });




            } else {
                Log.d("ppTESTchk","e2 : ");
                EasyPermissions.requestPermissions(this, getString(R.string.read_file), READ_REQUEST_CODE, Manifest.permission.READ_EXTERNAL_STORAGE);
            }
        }


    }

    private void callSignUp() {
        //progressDialog = ProgressDialog.show(activity, "", "Please Wait...", false, false);
        pd.show();

        Call<SignupPojo> fileUpload = null;
        String first_name_s = fname.getText().toString();
        String last_name_s = lname.getText().toString();
        String email_s = email.getText().toString();
        String password_s = password.getText().toString();
        String mobile_s = ph.getText().toString();
        String country_s = country_t.getText().toString();
        String state_s = state_t.getText().toString();
        String city_s =city_t.getText().toString();
        /*if (ct_id.getSelectedItem().toString().equals("No city found ")){
            city_s = "";
        }
        else {
            city_s = ct_id.getSelectedItem().toString();
        }*/
        String postal_code_s = pcode.getText().toString();
        String birthday_s = birthday.getText().toString();


        fileUpload = uploadService2.uploadSingleFile3(
                fileToUpload,
                new TitleCase().toTitleCase(first_name_s.trim()),
                new TitleCase().toTitleCase(last_name_s.trim()),
                email_s,
                password_s,
                mobile_s,
                country_s,
                state_s,
                city_s,
                postal_code_s,
                birthday_s);


        fileUpload.enqueue(new Callback<SignupPojo>()
        {
            @Override
            public void onResponse(Call<SignupPojo> call, Response<SignupPojo> response) {
                /*if (pd.isShowing()) {
                    pd.dismiss();
                }*/
                //progressDialog.dismiss();

                //Toast.makeText(ActivityEditProfile.this, "Success " + response.body().getStatus(), Toast.LENGTH_LONG).show();
                // Log.d("KoldarrrKolkata",response.raw().message()+"::\n"+response.body().getStatus());
                //JSONObject jsonObject1=new JSONObject(s);
                // Toasty.info(activity," " + response.body().getMessage(),Toasty.LENGTH_LONG).show();
                error_data.setVisibility(View.GONE);
                try
                {

                    if (response.body().getStatus() == true)
                    {
                        editor.putString("regid", response.body().allUserData.id);
                        editor.putBoolean("isReg",true);
                        editor.apply();
                        startActivity(new Intent(activity, ActivitySecurityQuestion.class).putExtra("userid","").addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run()
                            {
                                finish();
                            }
                        },1000);

                    }
                    if (response.body().getStatus() == false)
                    {
                        if (pd.isShowing())
                        {
                            pd.dismiss();
                        }
                        Toast.makeText(activity, ""+response.body().getMessage(), Toast.LENGTH_LONG).show();
                        error_data.setText(response.body().getMessage());
                        error_data.setVisibility(View.VISIBLE);
                    }
                }
                catch (Exception e)
                {
                    Toast.makeText(activity, "ERROR CODE B1", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SignupPojo> call, Throwable t) {
               // progressDialog.dismiss();
                Log.d("ppTESTchk", "ERROR1 : " + t.getMessage());

            }

        });
    }

    private String getRealPathFromURIPath(Uri contentURI, Activity activity) {
        Cursor cursor = activity.getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) {
            return contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            return cursor.getString(idx);
        }
    }

//location start
private void OnGPS() {
    final AlertDialog.Builder builder = new AlertDialog.Builder(this);
    builder.setMessage("Enable Location").setCancelable(false).setPositiveButton("Yes", new  DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
        }
    }).setNegativeButton("No", new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            dialog.cancel();
        }
    });
    final AlertDialog alertDialog = builder.create();
    alertDialog.show();
}
    private void getLocation() throws IOException {
        if (ActivityCompat.checkSelfPermission
                (
                activity,Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                activity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
        } else {
            Location locationGPS = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (locationGPS != null) {
                double lat = locationGPS.getLatitude();
                double longi = locationGPS.getLongitude();
                latitude = String.valueOf(lat);
                longitude = String.valueOf(longi);
                Geocoder geocoder;
                List<Address> addresses;
                geocoder = new Geocoder(this, Locale.getDefault());

                addresses = geocoder.getFromLocation(lat, longi, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5

                String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                city = addresses.get(0).getLocality();
                state = addresses.get(0).getAdminArea();
                country = addresses.get(0).getCountryName();
                postalCode = addresses.get(0).getPostalCode();
                String knownName = addresses.get(0).getFeatureName();


            } else {
                //Toast.makeText(this, "Unable to find location.", Toast.LENGTH_SHORT).show();
            }
        }
    }
    //location end


    public static void hideKeyboard(Activity activity)
    {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null)
        {
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
                        if (s.getBoolean("status")) {
                            final JSONArray jsonArray = s.getJSONArray("data");
                            if (jsonArray.length() > 0) {
                                com.wishster.model.SecurityQuestionModel.questionJsondata=jsonArray;
                                com.wishster.model.SecurityQuestionModel.totalQuestion=jsonArray.length();
                                //startActivity(new Intent(ActivitySignUp.this,ActivitySecurityQuestion.class));

                                editor.putString("questionJsondata", jsonArray.toString());
                                editor.putInt("totalQuestion", jsonArray.length());
                                editor.putBoolean("hasQuestion",true);
                                editor.apply();
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
    protected void onDestroy()
    {
        super.onDestroy();
        if (pd.isShowing())
        {
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
