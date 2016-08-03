package com.fabianisai.android.selforder.login.mvp_clean;

import android.content.SharedPreferences;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.fabianisai.android.selforder.R;
import com.fabianisai.android.selforder.SelfOrderApp;
import com.fabianisai.android.selforder.lib.eventbus.EventBus;
import com.fabianisai.android.selforder.lib.eventbus.GreenRobotEventBus;
import com.fabianisai.android.selforder.lib.network.VolleySingleton;
import com.fabianisai.android.selforder.login.events.LoginEvent;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by fabianisai on 7/20/16.
 */

public class LoginRepositoryImpl implements LoginRepository {
    //
    private static final String URL_VALIDA_USUARIO="https://s-order.herokuapp.com/validaUsuario";
    private static final String KEY_RESULT="result";
    private SharedPreferences sharedPreferences;
    private RequestQueue requestQueue;
    private VolleySingleton volleySigleton;
    private EventBus eventBus;

    public LoginRepositoryImpl(){
        volleySigleton = VolleySingleton.getInstance();
        requestQueue=volleySigleton.getRequestQueue();
        eventBus = GreenRobotEventBus.getInstance();
    }

    @Override
    public void signIn(final String email, final String password) {
        StringRequest request = new StringRequest(Request.Method.POST,URL_VALIDA_USUARIO, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                try {

                    JSONObject jObjResponse = new JSONObject(response);

                    int usrId;
                    usrId=jObjResponse.getInt(KEY_RESULT);
                    if(usrId!=-1){

                        sharedPreferences=SelfOrderApp.getSharedPreferences();
                        SharedPreferences.Editor editor=sharedPreferences.edit();
                        editor.putInt(SelfOrderApp.getUserKey(),usrId);
                        editor.putString(SelfOrderApp.getUserPass(),password);
                        editor.putString(SelfOrderApp.getUserEmail(),email);
                        editor.commit();

                        postEvent(LoginEvent.onSignInSuccess);
                    }else{
                        String errormsg=SelfOrderApp.getInstance().getResources().getString(R.string.login_error_message_signin_novalido);
                        postEvent(LoginEvent.onSignInError,errormsg);
                    }


                } catch (JSONException e) {
                    postEvent(LoginEvent.onSignInError, e.getMessage());
                }


            }


        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                postEvent(LoginEvent.onSignInError, error.getMessage());
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("email", email);
                params.put("password", password);
                return params;
            }


        };

        requestQueue.add(request);

    }

    @Override
    public void CheckSession() {
        String usrEmail,usrPass;
        sharedPreferences=SelfOrderApp.getSharedPreferences();
        usrEmail=sharedPreferences.getString(SelfOrderApp.getUserEmail(),null);
        usrPass=sharedPreferences.getString(SelfOrderApp.getUserPass(),null);


        if(usrEmail!=null){
            postEvent(LoginEvent.onSessionActive,usrEmail,usrPass);
        }else{
            postEvent(LoginEvent.onNoSession);
        }
    }

    @Override
    public void checkAlreadyAuthenticated() {

    }

    private void postEvent(int type,String errorMessage){
        LoginEvent loginEvent=new LoginEvent();
        loginEvent.setEventType(type);
        if(errorMessage!=null){
            loginEvent.setErrorMessage(errorMessage);
        }

        EventBus eventBus= GreenRobotEventBus.getInstance();
        eventBus.post(loginEvent);
    }

    private void postEvent(int type){
        postEvent(type,null);
    }
    private void postEvent(int type,String usrEmail,String usrPass){
        LoginEvent loginEvent=new LoginEvent();
        loginEvent.setEventType(type);
        loginEvent.setUsrEmail(usrEmail);
        loginEvent.setUsrPass(usrPass);

        EventBus eventBus=GreenRobotEventBus.getInstance();
        eventBus.post(loginEvent);
    }
}
