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
}





    //  private static DBMovies mDatabase;



