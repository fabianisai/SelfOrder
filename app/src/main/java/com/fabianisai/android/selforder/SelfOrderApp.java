package com.fabianisai.android.selforder;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.fabianisai.android.selforder.login.ui.LoginActivity;
import com.facebook.AccessToken;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;

/**
 * Created by fabianisai on 7/11/16.
 */

public class SelfOrderApp extends Application {
    private final static String SHARED_PREFERENCES_NAME = "UserPrefs";
    private final static String ORDEN_KEY = "orden_id";
    private final static String NEGOCIO_KEY = "negocio_id";
    private final static String NEGOCIO_DESCR = "negocio_descr";
    private final static String USER_KEY = "usuario_id";
    private final static String USER_SESSION_TYPE = "user_sesion";
    private final static String USER_EMAIL = "usuario_email";
    private final static String USER_PASS = "usuario_pass";



    private final static String USER_EMAIL_FACE = "usuario_email_face";

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
        initFacebook();
    }

    private void initFacebook(){
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
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
    public static String getUserSessionType() {
        return USER_SESSION_TYPE;
    }
    public static String getUserEmailFace() {return USER_EMAIL_FACE;}

    public void logout() {
        //cerrar session de face
        if (AccessToken.getCurrentAccessToken()!=null) {LoginManager.getInstance().logOut(); }
        //limpiamos variables de sesion
        SelfOrderApp.getSharedPreferences().edit().clear().commit();
        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                | Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}





    //  private static DBMovies mDatabase;



