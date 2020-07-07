package com.wishster.viewmodel;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import com.wishster.model.dialog_model.TransparentProgressDialog;
import com.wishster.model.forgot_password.Forgot_pojo;
import com.wishster.model.forgot_password.change_passF_POJO;
import com.wishster.model.forgot_password.pin_POJO;
import com.wishster.model.login_and_signup_module.LoginandSignupModel;
import com.wishster.model.login_and_signup_module.token;
import com.wishster.model.securityModel.securityModel;
import com.wishster.view.R;
import com.wishster.view.cart.Cart;
import com.wishster.view.friend_freg.FriendFregment;
import com.wishster.view.friends_list.ActivityFriend;
import com.wishster.view.mylogindesign.ActivityLogin;

import com.wishster.view.master.ActivityMaster;
import com.wishster.view.profile_regment.AccountFregment;
import com.wishster.view.profile_regment.ProfileFragment;
import com.wishster.view.profile_regment.UpdateProfileFregment;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivityViewModel extends AndroidViewModel {
    LoginandSignupModel loginandSignupModel;
    Application application;
    public Activity activity2;
    Context ctx;
    static  boolean bool=false;
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    final Fragment profile = new ProfileFragment();
    final Fragment friend_freg = new FriendFregment();
    final Fragment acc_freg = new AccountFregment();
    final Fragment updateProfileFregment = new UpdateProfileFregment();
    final Fragment cart = new Cart();
    public MainActivityViewModel(@NonNull Application application) {
        super(application);
        this.application=application;
        ctx=application;
        loginandSignupModel =new LoginandSignupModel(application);
        pref = ctx.getApplicationContext().getSharedPreferences(ctx.getString(R.string.SESSION), 0);
        editor = pref.edit();
    }

    public LiveData<JSONObject> getLogin() throws JSONException
    {
        return  loginandSignupModel.getLogin(ctx);
    }
    public LiveData<JSONObject> getSecurityQuestion() throws JSONException
    {
        return  loginandSignupModel.getSecurityQuestion(ctx);
    }
    public LiveData<Forgot_pojo> sendEmail() throws JSONException
    {
        return  loginandSignupModel.SendEmail(ctx);
    }
    public LiveData<change_passF_POJO> changePasswordF() throws JSONException
    {
        return  loginandSignupModel.ChangePassF(ctx);
    }

    public LiveData<pin_POJO> checkPin() throws JSONException
    {
        return  loginandSignupModel.CheckPIN(ctx);
    }

    public LiveData<JSONObject> getProfile() throws JSONException
    {
        return  loginandSignupModel.getProfile(ctx);
    }

    public LiveData<JSONObject> getSignUp() throws JSONException
    {
        return  loginandSignupModel.getSignup(ctx);
    }
    public LiveData<securityModel> setSecurity(final JSONObject store_security_question, TransparentProgressDialog pd, TextView aa) throws JSONException
    {
        return  loginandSignupModel.setSecurityQuestion(ctx,store_security_question,pd,aa);
    }



        public Menu callDrawer(final Activity a, androidx.appcompat.widget.Toolbar toolbar, final Spinner menu_spinner, CircleImageView profile_icon, final FragmentManager fn){

        DrawerLayout drawer = (DrawerLayout)a.findViewById(R.id.drawer_id);
        NavigationView navigationView = a.findViewById(R.id.nv);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                a, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        ArrayList menu_item=new ArrayList();
        menu_item.clear();
        menu_item.add("----");
        menu_item.add("Home");
        menu_item.add("Profile");
        menu_item.add("Account");
        menu_item.add("Update Profile");
        menu_item.add("Cart");
        menu_item.add("Logout");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(a.getApplicationContext(), R.layout.simple_spinner_item, menu_item){

            @Override
            public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View v = null;

                // If this is the initial dummy entry, make it hidden
                if (position == 0) {
                    TextView tv = new TextView(getContext());
                    tv.setHeight(0);
                    tv.setVisibility(View.GONE);
                    v = tv;
                }
                else {
                    // Pass convertView as null to prevent reuse of special case views
                    v = super.getDropDownView(position, null, parent);
                }

                // Hide scroll bar because it appears sometimes unnecessarily, this does not prevent scrolling
                parent.setVerticalScrollBarEnabled(false);
                return v;
                }
        };
        menu_spinner.setAdapter(adapter);
        profile_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menu_spinner.performClick();
            }
        });

        menu_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if (bool==true) {
                    if (position == 1)
                    {
                        //FragmentManager fm = a.getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fn.beginTransaction();
                        //fragmentTransaction.replace(R.id.layout, profile);
                        fragmentTransaction.replace(R.id.layout, friend_freg);
                        fragmentTransaction.commit();
                        /*a.startActivity(new Intent(a, ActivityFriend.class));
                        a.finish();*/
                    }
                    if (position == 2)
                    {
                        /*a.startActivity(new Intent(a, ActivityMaster.class));
                        a.finish();*/

                        //FragmentManager fm = a.getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fn.beginTransaction();
                        fragmentTransaction.replace(R.id.layout, profile);
                        //fragmentTransaction.replace(R.id.layout, friend_freg);
                        fragmentTransaction.commit();
                    }
                    if (position == 3)
                    {
                        /*a.startActivity(new Intent(a, ActivityMaster.class));
                        a.finish();*/

                        //FragmentManager fm = a.getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fn.beginTransaction();
                        fragmentTransaction.replace(R.id.layout, acc_freg);
                        //fragmentTransaction.replace(R.id.layout, friend_freg);
                        fragmentTransaction.commit();
                    }
                     if (position == 4)
                    {
                        /*a.startActivity(new Intent(a, ActivityMaster.class));
                        a.finish();*/

                        //FragmentManager fm = a.getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fn.beginTransaction();
                        fragmentTransaction.replace(R.id.layout, updateProfileFregment);
                        //fragmentTransaction.replace(R.id.layout, friend_freg);
                        fragmentTransaction.commit();
                    }

                    if (position == 5)
                    {
                        FragmentTransaction ft=fn.beginTransaction();
                        ft.replace(R.id.layout,cart);
                        ft.commit();
                    }
                    if (position == 6)
                    {
                        editor.putBoolean("isLogin",false);
                        editor.apply();
                        a.startActivity(new Intent(a, ActivityLogin.class));
                        a.finish();
                    }
                }
                bool=true;

            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });
        //drawer start
        try {
            drawer.addDrawerListener(toggle);
        }catch (Exception e){

        }

        toggle.syncState();
        navigationView.setNavigationItemSelectedListener((NavigationView.OnNavigationItemSelectedListener) a);
        Menu menu = navigationView.getMenu();
        View header=navigationView.getHeaderView(0);
        //drawer end

        TextView title_a = (TextView) header.findViewById(R.id.title_a);
        TextView title_b = (TextView) header.findViewById(R.id.title_b);
        CircleImageView imageView = (CircleImageView) header.findViewById(R.id.profile_image_header);

       // return menu;
        String name="";
        String email="";
        try{
            if (!(pref.getString("first_name","").equals(""))){

                name=pref.getString("first_name","");
            }
           if (!(pref.getString("first_name","").equals(""))){

                name+=" "+pref.getString("last_name","");
            }
            if (!(pref.getString("email","").equals(""))){

                email+=" "+pref.getString("last_name","");
            }
            if (!(pref.getString("token","").equals(""))){

                token.token=""+pref.getString("last_name","");
            }

            if (!(pref.getString("profile_img","").equals("")))
            {

                Picasso.with(ctx).load(pref.getString("profile_img",""))
                        .networkPolicy(NetworkPolicy.NO_CACHE)
                        .error(R.drawable.profile_image_default)
                        .placeholder( R.drawable.profile_image_default ).into(imageView);

                Picasso.with(ctx).load(pref.getString("profile_img",""))
                        .networkPolicy(NetworkPolicy.NO_CACHE)
                        .error(R.drawable.profile_image_default)
                        .placeholder( R.drawable.profile_image_default ).into(profile_icon);
            }



           // name="Someone Name";
           // email="someoneemail@test.com";
            title_a.setText(""+name);
            title_b.setText(""+email);


        }catch (Exception e){

        }
        return  menu;

    }




    public void callLanguage(Menu menu)
    {

            MenuItem a1 = menu.findItem(R.id.a1);
            a1.setTitle("Home");
            MenuItem a2 = menu.findItem(R.id.a2);
            a2.setTitle("Profile");
            MenuItem a3 = menu.findItem(R.id.logout);
            a3.setTitle("Logout");


    }
    public void CallGoMenu(MenuItem item,Activity a) {
        Log.d("koooooo", "ABCD");
        int id = item.getItemId();
        activity2 = a;
        if (id == R.id.a1) {

            a.startActivity(new Intent(a, ActivityFriend.class));
            a.finish();

        } else if (id == R.id.a2) {

            a.startActivity(new Intent(a, ActivityMaster.class));
            a.finish();
        } else if (id == R.id.logout) {
            editor.putBoolean("isLogin",false);
            editor.apply();
            a.startActivity(new Intent(a, ActivityLogin.class));
            a.finish();

        }
    }

    }
