package com.fabianisai.android.selforder;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by fabianisai on 7/11/16.
 */

public class SelfOrderApp extends Application {
    private final static String SHARED_PREFERENCES_NAME = "UserPrefs";
    private final static String ORDEN_KEY = "orden_id";
    private final static String NEGOCIO_KEY = "negocio_id";
    private final static String NEGOCIO_DESCR = "negocio_descr";


    private final static String USER_KEY = "usuario_id";



    private final static String USER_EMAIL = "usuario_email";



    private final static String USER_PASS = "usuario_pass";
    private static SelfOrderApp sInstance;

    public static SelfOrderApp getInstance() {
        return sInstance;
    }

    public static Context getAppContext() {
        return sInstance.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance=this;
    }


    public static SharedPreferences getSharedPreferences(){return sInstance.getSharedPreferences(SHARED_PREFERENCES_NAME,Context.MODE_PRIVATE);}
    public static String getOrdenKey() {
        return ORDEN_KEY;
    }

    public static String getNegocioKey() {
        return NEGOCIO_KEY;
    }

    public static String getNegocioDescr() {
        return NEGOCIO_DESCR;
    }
    public static String getUserKey() {
        return USER_KEY;
    }
    public static String getUserPass() {
        return USER_PASS;
    }
    public static String getUserEmail() {
        return USER_EMAIL;
    }
}





    //  private static DBMovies mDatabase;



