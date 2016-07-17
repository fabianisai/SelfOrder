package com.fabianisai.android.selforder.menus.mvp_clean;

import android.content.Context;
import android.content.SharedPreferences;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.fabianisai.android.selforder.SelfOrderApp;
import com.fabianisai.android.selforder.lib.eventbus.EventBus;
import com.fabianisai.android.selforder.lib.eventbus.GreenRobotEventBus;
import com.fabianisai.android.selforder.lib.network.VolleySingleton;
import com.fabianisai.android.selforder.menus.entities.Menu;
import com.fabianisai.android.selforder.menus.events.MenusEvent;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fabianisai on 7/16/16.
 */

public class MenusRepositoryImpl implements MenusRepository {
    private VolleySingleton volleySigleton;
    private RequestQueue requestQueue;
    private EventBus eventBus;
    private static final String URL_MENUSXNEGOCIO="https://s-order.herokuapp.com/getMenuPorNegocio?negocioId=";
    private static final String KEY_RESULTS="results";
    private static final String KEY_ID="menu_id";
    private static final String KEY_DESCRIPCION="menu_descripcion";
    private SharedPreferences sharedPreferences;


    public MenusRepositoryImpl(){
        volleySigleton = VolleySingleton.getInstance();
        requestQueue=volleySigleton.getRequestQueue();
        eventBus = GreenRobotEventBus.getInstance();
    }

    @Override
    public void getMenus() {

        sharedPreferences= SelfOrderApp.getSharedPreferences ();

        Integer negocioId=sharedPreferences.getInt(SelfOrderApp.getNegocioKey(),-1);

        JsonObjectRequest request=new JsonObjectRequest(Request.Method.GET, URL_MENUSXNEGOCIO+String.valueOf(negocioId),(String) null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {

                try {
                    List<Menu> items = new ArrayList<Menu>();
                    JSONArray arrayMenus = response.getJSONArray(KEY_RESULTS);

                    if (response.has(KEY_RESULTS)) {


                        for (int i = 0; i < arrayMenus.length(); i++) {

                            JSONObject currentMenu = arrayMenus.getJSONObject(i);

                            int id = currentMenu.getInt(KEY_ID);
                            String descripcion = currentMenu.getString(KEY_DESCRIPCION);


                            Menu menu = new Menu();
                            menu.setId(id);
                            menu.setDescripcion(descripcion);
                            items.add(menu);

                        }

                        postEvent(items);

                    }

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
        MenusEvent event = new MenusEvent();
        event.setError(error);
        eventBus.post(event);
    }

    private void postEvent(List<Menu> items) {
        MenusEvent event = new MenusEvent();
        event.setMenus(items);
        eventBus.post(event);
    }

}
