package com.wishster.view.test;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
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
import com.wishster.viewmodel.MainActivityViewModel;

import de.hdodenhof.circleimageview.CircleImageView;

public class ActivityTest extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    ActivityTest a;
    MainActivityViewModel mainActivityViewModel;
    ViewStub stub;
    Spinner spinner;
    CircleImageView profile_image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frame);
        mainActivityViewModel= ViewModelProviders.of(this).get(MainActivityViewModel.class);
        a=ActivityTest.this;
        androidx.appcompat.widget.Toolbar toolbar = (androidx.appcompat.widget.Toolbar)findViewById(R.id.toolbar);
        //toolbar.setTitle("Testing");
        Toast.makeText(a, ""+isOnline(), Toast.LENGTH_SHORT).show();
        setSupportActionBar(toolbar);
        final ActionBar actionBar = getSupportActionBar();
        if(actionBar!=null)
        {
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        profile_image = (CircleImageView) findViewById(R.id.profile_image);
        spinner = (Spinner) findViewById(R.id.spinner);
        FragmentManager fm = getSupportFragmentManager();
        Menu menu=mainActivityViewModel.callDrawer(a,toolbar,spinner,profile_image,fm);
        //Menu menu=mainActivityViewModel.callDrawer(a,toolbar);
        mainActivityViewModel.callLanguage(menu);
        /*stub= (ViewStub) findViewById(R.id.layout_stub);
        stub.setLayoutResource(R.layout.activity_test);
        View inflated = stub.inflate();*/

    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item)
    {
        mainActivityViewModel.CallGoMenu(item, a);
        DrawerLayout drawer = a.findViewById(R.id.drawer_id);
        //DrawerLayout drawer = (DrawerLayout) findViewById(R.id.nv);
        drawer.closeDrawer(GravityCompat.START);
        return true;
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
