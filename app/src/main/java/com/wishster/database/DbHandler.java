package com.wishster.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.wishster.view.mylogindesign.ActivitySignUp;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by tutlane on 06-01-2018.
 */

public class DbHandler extends SQLiteOpenHelper {
    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "csc_abc.sqlite";
    private static final String TABLE_Users = "userdetails";
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_LOC = "location";
    private static final String KEY_DESG = "designation";
    Context context;
    public DbHandler(Context context){
        super(context,DB_NAME, null, DB_VERSION);
        this.context=context;
    }
    @Override
    public void onCreate(SQLiteDatabase db){
        /*String CREATE_TABLE = "CREATE TABLE " + TABLE_Users + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_NAME + " TEXT,"
                + KEY_LOC + " TEXT,"
                + KEY_DESG + " TEXT"+ ")";
        db.execSQL(CREATE_TABLE);*/

    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        // Drop older table if exist
        /*db.execSQL("DROP TABLE IF EXISTS " + TABLE_Users);
        // Create tables again*/
        onCreate(db);
    }
    // **** CRUD (Create, Read, Update, Delete) Operations ***** //


    // Get User Details
    public ArrayList<String> GetAllCountry(){
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<HashMap<String, String>> userList = new ArrayList<>();
        String query = "select * from countries";
        Cursor cursor = db.rawQuery(query,null);
        ArrayList<String> country_list=new ArrayList<String>();
        country_list.clear();
        while (cursor.moveToNext()){
            country_list.add(cursor.getString(cursor.getColumnIndex(KEY_NAME)));
            Log.d("pppdata",""+cursor.getString(cursor.getColumnIndex(KEY_NAME)));

        }
        return  country_list;
    }
    public String CountryCode(String countryname){
        SQLiteDatabase db = this.getWritableDatabase();
        String country_id="";
        String query = "select * from countries where name='"+countryname+"'  limit 1";
        Cursor cursor = db.rawQuery(query,null);
        ArrayList<String> country_list=new ArrayList<String>();
        country_list.clear();
        while (cursor.moveToNext())
        {
            country_id=cursor.getString(cursor.getColumnIndex(KEY_ID));
            Log.d("pppdataID",""+cursor.getString(cursor.getColumnIndex(KEY_ID)));

        }
        if (db.isOpen())
        {
            db.close();
        }
        return  country_id;
    }



    public String StateCode(String countryCode,String stateName){
        String stare_id = "";
        try {
            SQLiteDatabase db = this.getWritableDatabase();

            String query = "select * from states where name='" + stateName + "' and country_id='" + countryCode + "' limit 1";
            Cursor cursor = db.rawQuery(query, null);
            ArrayList<String> country_list = new ArrayList<String>();
            country_list.clear();
            while (cursor.moveToNext()) {
                stare_id = cursor.getString(cursor.getColumnIndex(KEY_ID));


            }
            Log.d("stateIDdata",countryCode+":"+stateName);
            if (db.isOpen()) {
                db.close();
            }
        }catch (Exception e){

        }
        return  stare_id;
    }
    public ArrayList<String> GetAllState(String country_id){
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "select * from states where country_id='"+country_id+"'";

        Cursor cursor = db.rawQuery(query,null);
        ArrayList<String> state_list=new ArrayList<String>();
        state_list.clear();
        while (cursor.moveToNext()){
            state_list.add(cursor.getString(cursor.getColumnIndex(KEY_NAME)));


        }
        if (db.isOpen()){
            db.close();
        }
        return  state_list;
    }
    public ArrayList<String> GetAllCity(String state_id){


        SQLiteDatabase db = this.getWritableDatabase();

        String query = "select * from cities where state_id='"+state_id+"'";

        Cursor cursor = db.rawQuery(query,null);
        ArrayList<String> state_list=new ArrayList<String>();
        state_list.clear();
        if (cursor.getCount()>0) {
            while (cursor.moveToNext()) {
                state_list.add(cursor.getString(cursor.getColumnIndex(KEY_NAME)));


            }
        }
        else {
            state_list.add("No city found ");
        }

        if (db.isOpen()){
            db.close();
        }
        return  state_list;
    }


    public boolean chk_country(String countryname) {



        SQLiteDatabase db = this.getWritableDatabase();

        String query = "select * from countries where name='"+countryname+"'";

        Cursor cursor = db.rawQuery(query,null);
        if (cursor.getCount()>0){
          return true;
        }

        return  false;


    }


    public boolean chk_state(String statename, String countryCode) {

        SQLiteDatabase db = this.getWritableDatabase();

        String query = "select * from states where name='"+statename+"' and country_id='"+countryCode+"'";

        Cursor cursor = db.rawQuery(query,null);
        if (cursor.getCount()>0){
            return true;
        }

        return  false;
    }

    public boolean chk_city(String cityname, String stateCode) {

        SQLiteDatabase db = this.getWritableDatabase();

        String query = "select * from cities where name='"+cityname+"' and state_id='"+stateCode+"'";

        Cursor cursor = db.rawQuery(query,null);
        if (cursor.getCount()>0){
            return true;
        }

        return  false;
    }
}
