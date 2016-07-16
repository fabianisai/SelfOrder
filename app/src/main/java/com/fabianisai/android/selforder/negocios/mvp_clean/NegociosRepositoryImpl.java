package com.fabianisai.android.selforder.negocios.mvp_clean;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.fabianisai.android.selforder.negocios.entities.Negocio;
import com.fabianisai.android.selforder.negocios.events.NegociosEvent;
import com.fabianisai.android.selforder.lib.eventbus.EventBus;
import com.fabianisai.android.selforder.lib.eventbus.GreenRobotEventBus;
import com.fabianisai.android.selforder.lib.network.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fabianisai on 7/11/16.
 */

public class NegociosRepositoryImpl implements NegociosReposirtory{
    private VolleySingleton volleySigleton;
    private RequestQueue requestQueue;
    private EventBus eventBus;
    public static final String URL_NEGOCIOS="https://s-order.herokuapp.com/getNegocios";
    public static final String KEY_RESULTS="results";
    public static final String KEY_ID="negocio_id";
    public static final String KEY_NOMBRE="nombre_negocio";
    public static final String KEY_LOGO="logo";

    public NegociosRepositoryImpl(){
        volleySigleton = VolleySingleton.getInstance();
        requestQueue=volleySigleton.getRequestQueue();
        eventBus = GreenRobotEventBus.getInstance();
    }

    @Override
    public void getNegocios() {
        JsonObjectRequest request=new JsonObjectRequest(Request.Method.GET, URL_NEGOCIOS,(String) null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {

                    JSONArray listNegocios = response.getJSONArray(KEY_RESULTS);

                    List<Negocio> items = new ArrayList<Negocio>();

                    for(int i=0;i<listNegocios.length();i++){

                        JSONObject currentNegocio=listNegocios.getJSONObject(i);

                        int id=currentNegocio.getInt(KEY_ID);
                        String nombre=currentNegocio.getString(KEY_NOMBRE);
                        String logo=currentNegocio.getString(KEY_LOGO);

                        Negocio negocio=new Negocio();
                        negocio.setId(id);
                        negocio.setNombre(nombre);
                        negocio.setLogo(logo);
                        items.add(negocio);
                    }

                    postEvent(items);
                } catch (JSONException e) {
                    postEvent(e.getMessage());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                    postEvent(error.getMessage());
            }
        });

        requestQueue.add(request);

    }

    private void postEvent(String error){
        NegociosEvent event = new NegociosEvent();
        event.setError(error);
        eventBus.post(event);
    }

    private void postEvent(List<Negocio> items) {
        NegociosEvent event = new NegociosEvent();
        event.setHashtags(items);
        eventBus.post(event);
    }
}
