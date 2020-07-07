package com.wishster.model;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.wishster.view.error_package.ActivityErrorLog;

import java.io.FileOutputStream;
import java.io.IOException;

public class TopExceptionHandler implements Thread.UncaughtExceptionHandler {
    private Thread.UncaughtExceptionHandler defaultUEH;
    private Activity app = null;

    public TopExceptionHandler(Activity app)
    {
        this.defaultUEH = Thread.getDefaultUncaughtExceptionHandler();
        this.app = app;
    }

    public void uncaughtException(Thread t, Throwable e) {
        StackTraceElement[] arr = e.getStackTrace();
        String report = e.toString()+"\n\n";
        report += "--------- Stack trace ---------\n\n";
        for (int i=0; i<arr.length; i++) {
            report += "    "+arr[i].toString()+"\n";
        }
        report += "-------------------------------\n\n";

        // If the exception was thrown in a background thread inside
        // AsyncTask, then the actual exception can be found with getCause

        report += "--------- Cause ---------\n\n";
        Throwable cause = e.getCause();
        if(cause != null) {
            report += cause.toString() + "\n\n";
            arr = cause.getStackTrace();
            for (int i=0; i<arr.length; i++) {
                report += "    "+arr[i].toString()+"\n";
            }
        }
        report += "-------------------------------\n\n";
        app.startActivity(new Intent(app.getApplicationContext(), ActivityErrorLog.class).putExtra("e",""+report));
        try {
            FileOutputStream trace = app.openFileOutput("stack.txt",
                    Context.MODE_PRIVATE);
            trace.write(report.getBytes());
            trace.close();
        } catch(IOException ioe) {
            // ...
        }

        defaultUEH.uncaughtException(t, e);
    }
}
