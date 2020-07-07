package com.wishster.view.plashscreen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.wishster.database.DatabaseHandler;
import com.wishster.view.R;
import com.wishster.view.mylogindesign.ActivityLogin;


public class ActivityPlashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plash_screen2);

        Thread background = new Thread() {
            public void run() {

                try {
                    sleep(2*1000);
                    Intent i=new Intent(getBaseContext(), ActivityLogin.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                    finish();

                } catch (Exception e) {
                }
            }
        };

        background.start();
    }
}
