package com.wishster.view.master;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewStub;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import com.wishster.model.internet_chacking.CheckInternet;
import com.wishster.view.R;
import com.wishster.view.brand.Brand;
import com.wishster.view.friend_freg.FriendFregment;
import com.wishster.view.nointernet.ActivityNoActivity;
import com.wishster.view.profile_regment.ProfileFragment;
import com.wishster.viewmodel.MainActivityViewModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class ActivityMaster extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    ActivityMaster a;
    MainActivityViewModel mainActivityViewModel;
    ViewStub stub;
    Spinner spinner;
    CircleImageView profile_image;
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    final Fragment profile = new ProfileFragment();
    final Fragment friend_freg = new FriendFregment();

    final Fragment brand = new Brand();
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frame);
        mainActivityViewModel= ViewModelProviders.of(this).get(MainActivityViewModel.class);
        a= ActivityMaster.this;
        pref = getApplicationContext().getSharedPreferences(getString(R.string.SESSION), MODE_PRIVATE);
        editor = pref.edit();
        androidx.appcompat.widget.Toolbar toolbar = (androidx.appcompat.widget.Toolbar)findViewById(R.id.toolbar);
        //toolbar.setTitle("Testing");
        if(!(new CheckInternet(this).getInternetStatus()))
        {

            startActivity(new Intent(this, ActivityNoActivity.class));
            finish();
        }
        setSupportActionBar(toolbar);
        final ActionBar actionBar = getSupportActionBar();
        if(actionBar!=null)
        {
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

                profile_image = (CircleImageView) findViewById(R.id.profile_image);
                FragmentManager fm = getSupportFragmentManager();
                spinner = (Spinner) findViewById(R.id.spinner);
                Menu menu=mainActivityViewModel.callDrawer(a,toolbar,spinner,profile_image,fm);
                //Menu menu=mainActivityViewModel.callDrawer(a,toolbar);
                mainActivityViewModel.callLanguage(menu);
             /*   stub= (ViewStub) findViewById(R.id.layout_stub);
                stub.setLayoutResource(R.layout.profile);
                View inflated = stub.inflate();
                deviceSize();
                String uName="";
        if (!(pref.getString("first_name","").equals(""))){
            uName=""+pref.getString("first_name","");
        }
        if (!(pref.getString("last_name","").equals(""))){
            uName+=" "+pref.getString("last_name","");
        }
        if (!(pref.getString("country","").equals(""))){
            ((TextView)findViewById(R.id.user_user_country)).setText(""+pref.getString("country",""));

        }
        ((TextView)findViewById(R.id.user_name)).setText(""+uName);
        if (!(pref.getString("profile_img","").equals("")))
        {


                      CircleImageView profa=(CircleImageView)findViewById(R.id.profile_image_user);
                      Picasso.with(a).load(pref.getString("profile_img", ""))
                     .networkPolicy(NetworkPolicy.NO_CACHE)
                     .error(R.drawable.profile_image_default)
                     .placeholder(R.drawable.profile_image_default).into(profa);

        }*/
        //FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        //fragmentTransaction.replace(R.id.layout, profile);
        fragmentTransaction.replace(R.id.layout, friend_freg);
        fragmentTransaction.commit();



    }

    private void deviceSize() {
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = (size.y-100);
        RelativeLayout rl = (RelativeLayout) findViewById(R.id.main_container);
        rl.getLayoutParams().height = height;
        //rl.getLayoutParams().width = px;
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

    private void getProfile() throws JSONException
    {

        try {
            mainActivityViewModel.getProfile().observe(a, new Observer<JSONObject>()
            {
                @Override
                public void onChanged(JSONObject s)
                {
                    Log.d("aaajsondata","AAA "+s);
                    try {
                        if (s.getBoolean("status")) {
                            final JSONArray jsonArray = s.getJSONArray("data");
                            if (jsonArray.length() > 0) {


                               /* editor.putString("questionJsondata", jsonArray.toString());
                                editor.putInt("totalQuestion", jsonArray.length());
                                editor.putBoolean("hasQuestion",true);
                                editor.putBoolean("isReg",true);
                                editor.apply();
                                */
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

}
