package com.wishster.view.error_package;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;

import com.wishster.model.TopExceptionHandler;
import com.wishster.view.R;

public class ActivityErrorLog extends AppCompatActivity {
Intent i;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_error_log);
        Thread.setDefaultUncaughtExceptionHandler(new TopExceptionHandler(this));
        /*

         <service
            android:name=".MyService"
            android:enabled="true"
            android:exported="true" />
         */
        setContentView(R.layout.activity_error_log);
        i=getIntent();
        final PackageManager pm = this.getPackageManager();
        try {
            Intent waIntent = new Intent(Intent.ACTION_SEND);
            waIntent.setType("text/plain");
            String text = "YOUR TEXT HERE";
            PackageInfo info = pm.getPackageInfo("com.whatsapp", PackageManager.GET_META_DATA);
            waIntent.setPackage("com.whatsapp");
            waIntent.putExtra(Intent.EXTRA_TEXT, i.getStringExtra("e"));
            startActivity(Intent.createChooser(waIntent, "Share with"));
        } catch (PackageManager.NameNotFoundException e) {

        }
    }
}
