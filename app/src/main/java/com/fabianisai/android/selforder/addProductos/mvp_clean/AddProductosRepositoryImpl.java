package com.fabianisai.android.selforder.addProductos.mvp_clean;

import android.content.SharedPreferences;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.fabianisai.android.selforder.SelfOrderApp;
import com.fabianisai.android.selforder.addProductos.entities.Producto;
import com.fabianisai.android.selforder.addProductos.events.ProductosEvent;
import com.fabianisai.android.selforder.lib.eventbus.EventBus;
import com.fabianisai.android.selforder.lib.eventbus.GreenRobotEventBus;
import com.fabianisai.android.selforder.lib.network.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by fabianisai on 7/17/16.
 */

public class AddProductosRepositoryImpl implements AddProductosRepository {
    private VolleySingleton volleySigleton;
    private RequestQueue requestQueue;
    private EventBus eventBus;
    private static final String URL_PRODUCTOXMENU="https://s-order.herokuapp.com/getProductosPorMenu?menuId=";
    private static final String URL_ADD_PRODUCTO="https://s-order.herokuapp.com/addProductoOrden";
    private static final String KEY_ID="producto_id";
    private static final String KEY_DESCRIPCION="descripcion";
    private static final String KEY_NOMBRE="nombre";
    private static final String KEY_PRECIO="precio";
    private static final String KEY_RESULTS="results";
    private static final String KEY_ERROR="error";
    private SharedPreferences sharedPreferences;
    private Integer productoId;
    private Integer ordenId;


    public AddProductosRepositoryImpl(){
        volleySigleton = VolleySingleton.getInstance();
        requestQueue=volleySigleton.getRequestQueue();
        eventBus = GreenRobotEventBus.getInstance();

        sharedPreferences= SelfOrderApp.getSharedPreferences ();

        ordenId=sharedPreferences.getInt(SelfOrderApp.getOrdenKey(),-1);

    }


    @Override
    public void getProductos(Integer menuId) {
        JsonObjectRequest request=new JsonObjectRequest(Request.Method.GET, URL_PRODUCTOXMENU+String.valueOf(menuId),(String) null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {

                try {
                    List<Producto> items = new ArrayList<Producto>();
                    JSONArray arrayProductos = response.getJSONArray(KEY_RESULTS);

                    if (response.has(KEY_RESULTS)) {


                        for (int i = 0; i < arrayProductos.length(); i++) {

                            JSONObject currentProducto = arrayProductos.getJSONObject(i);

                            int id = currentProducto.getInt(KEY_ID);
                            String descripcion = currentProducto.getString(KEY_DESCRIPCION);
                            String nombre = currentProducto.getString(KEY_NOMBRE);
                            Double  precio = currentProducto.getDouble(KEY_PRECIO);


                            Producto producto = new Producto();
                            producto.setId(id);
                            producto.setDescripcion(descripcion);
                            producto.setNombre(nombre);
                            producto.setPrecio(precio);
                            items.add(producto);

                        }

                       // postEvent(items);
                        post(ProductosEvent.GET_PRODUCTOS,items);
                    }

                } catch (JSONException e) {
                    post(ProductosEvent.ERROR,e.getMessage());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                post(ProductosEvent.ERROR,error.getMessage());
            }
        });

        requestQueue.add(request);

    }

    @Override
    public void addProdutoOrden(final Producto producto) {
        this.productoId=producto.getId();
        StringRequest request = new StringRequest(Request.Method.POST,URL_ADD_PRODUCTO, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                post(ProductosEvent.ADD_PRODUCTOS,producto.getNombre());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                post(ProductosEvent.ERROR,error.getMessage());
            }
        }) {
            @Override
            protected Map<String, String> getParams() {


                Map<String, String> params = new HashMap<String, String>();
                params.put("productoId", String.valueOf(productoId));
                params.put("ordenId", String.valueOf(ordenId) );
                params.put("estatusId", "25");
                params.put("estatusPorUsuarioId", "1" );

                return params;
            }


        };

        requestQueue.add(request);

    }
/*
    private void postEvent(String error){
        ProductosEvent event = new ProductosEvent();
        event.setError(error);
        eventBus.post(event);
    }

    private void postEvent(String nameProducto){
        ProductosEvent event = new ProductosEvent();
        event.setNameProducto(nameProducto);
        eventBus.post(event);
    }

    private void postEvent(List<Producto> items) {
        ProductosEvent event = new ProductosEvent();
        event.setProductos(items);
        eventBus.post(event);
    }  */



    private void post(int type, String data){
        if (type==ProductosEvent.ERROR){
            post(type, null, data,null);
        }else{
            post(type,null,null,data);
        }
    }

    private void post(int type, List<Producto> items){
        post(type, items, null,null);
    }


    private void post(int type, List<Producto> items, String error,String nameProducto){
        ProductosEvent event = new ProductosEvent();
        event.setType(type);
        event.setError(error);
        event.setProductos(items);
        event.setNameProducto(nameProducto);
        eventBus.post(event);
    }


}
