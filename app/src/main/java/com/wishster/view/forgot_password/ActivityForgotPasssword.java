package com.wishster.view.forgot_password;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewStub;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.wishster.view.R;
import com.wishster.view.test.ActivityTest;
import com.wishster.viewmodel.MainActivityViewModel;

import de.hdodenhof.circleimageview.CircleImageView;

public class ActivityForgotPasssword extends AppCompatActivity {
    ActivityForgotPasssword a;
    MainActivityViewModel mainActivityViewModel;
    ViewStub stub;
    Spinner spinner;
    CircleImageView profile_image;
    final Fragment send_forgot_pass_email = new send_forgot_pass_email();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_forgot_passsword);
        mainActivityViewModel= ViewModelProviders.of(this).get(MainActivityViewModel.class);
        a=ActivityForgotPasssword.this;
        //fregment call
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.cont1, send_forgot_pass_email);
        fragmentTransaction.commit();

//forgot_password_email_freg


    }


    private boolean isOnline()
    {
        try
        {
            ConnectivityManager cm = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
            return cm.getActiveNetworkInfo().isConnectedOrConnecting();
        }
        catch (Exception e)
        {
            return false;
        }
    }

}
