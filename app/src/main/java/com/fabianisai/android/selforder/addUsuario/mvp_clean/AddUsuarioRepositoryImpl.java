package com.fabianisai.android.selforder.addUsuario.mvp_clean;

import android.content.SharedPreferences;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.fabianisai.android.selforder.R;
import com.fabianisai.android.selforder.SelfOrderApp;
import com.fabianisai.android.selforder.addUsuario.events.AddUsuarioEvent;
import com.fabianisai.android.selforder.lib.eventbus.EventBus;
import com.fabianisai.android.selforder.lib.eventbus.GreenRobotEventBus;
import com.fabianisai.android.selforder.lib.network.VolleySingleton;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by fabianisai on 7/28/16.
 */

public class AddUsuarioRepositoryImpl implements AddUsuarioRepository {

    private static final String URL_CREA_USUARIO="https://s-order.herokuapp.com/addUsuario";
    private static final String KEY_RESULT="result";
    private static final String KEY_ERROR="error";
    private static final String KEY_MESSAGE="message";
    private SharedPreferences sharedPreferences;
    private RequestQueue requestQueue;
    private VolleySingleton volleySigleton;
    private EventBus eventBus;

    public AddUsuarioRepositoryImpl(){
        volleySigleton = VolleySingleton.getInstance();
        requestQueue=volleySigleton.getRequestQueue();
        eventBus = GreenRobotEventBus.getInstance();
    }

    @Override
    public void createUser(final String email, final String password, String repassword, final Integer SessionType) {

        if (!password.equals(repassword)){
            String errormsg=SelfOrderApp.getInstance().getResources().getString(R.string.addusuario_valida_password);
            postEvent(AddUsuarioEvent.onSignUpError,errormsg);
        }else {

                    StringRequest request = new StringRequest(Request.Method.POST, URL_CREA_USUARIO, new Response.Listener<String>() {

                        @Override
                        public void onResponse(String response) {
                            try {

                                JSONObject jObjResponse = new JSONObject(response);

                                boolean error;
                                error = jObjResponse.getBoolean(KEY_ERROR);
                                if (!error) {


                                    postEvent(AddUsuarioEvent.onSignUpSuccess);
                                } else {
                                   // String errorMessage=jObjResponse.getString(KEY_MESSAGE);
                                    String errorMessage=SelfOrderApp.getInstance().getResources().getString(R.string.addusuario_message_signup_existe);
                                    postEvent(AddUsuarioEvent.onSignUpError, errorMessage);
                                }


                            } catch (JSONException e) {
                                postEvent(AddUsuarioEvent.onSignUpError, e.getMessage());
                            }


                        }


                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            postEvent(AddUsuarioEvent.onSignUpError, error.getMessage());
                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() {
                            SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
                            String sexo="";
                            String ciudadId="1";
                            String estatusId="1";
                            String dateInString = "1900/01/01";
                            Date fechaNac= null;
                            try {
                                fechaNac = formatter.parse(dateInString);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            Map<String, String> params = new HashMap<String, String>();
                            params.put("email", email);
                            params.put("password", password);
                            params.put("fechaNacimiento", fechaNac.toString());
                            params.put("sexo", sexo);
                            params.put("ciudadId",ciudadId);
                            params.put("estatusId", estatusId);
                            params.put("sesionId",String.valueOf(SessionType));
                            return params;
                        }


                    };

                    requestQueue.add(request);
        }

    }

    private void postEvent(int type,String errorMessage){
        AddUsuarioEvent addUsuarioEvent=new AddUsuarioEvent();
        addUsuarioEvent.setEventType(type);
        if(errorMessage!=null){
            addUsuarioEvent.setErrorMessage(errorMessage);
        }

        EventBus eventBus= GreenRobotEventBus.getInstance();
        eventBus.post(addUsuarioEvent);
    }

    private void postEvent(int type){
        postEvent(type,null);
    }
}
