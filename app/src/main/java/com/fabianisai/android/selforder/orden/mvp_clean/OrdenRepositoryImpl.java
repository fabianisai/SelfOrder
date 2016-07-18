package com.fabianisai.android.selforder.orden.mvp_clean;

import android.content.SharedPreferences;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.fabianisai.android.selforder.SelfOrderApp;
import com.fabianisai.android.selforder.lib.eventbus.EventBus;
import com.fabianisai.android.selforder.lib.eventbus.GreenRobotEventBus;
import com.fabianisai.android.selforder.lib.network.VolleySingleton;
import com.fabianisai.android.selforder.orden.entities.Orden;
import com.fabianisai.android.selforder.orden.entities.OrdenProducto;
import com.fabianisai.android.selforder.orden.events.OrdenEvent;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by fabianisai on 7/13/16.
 */

public class OrdenRepositoryImpl implements OrdenRepository {
    private VolleySingleton volleySigleton;
    private RequestQueue requestQueue;
    private EventBus eventBus;
    private SharedPreferences sharedPreferences;
   // public static final String URL_NEGOCIOS="https://s-order.herokuapp.com/getNegocios";
    public static final String URL_CREA_ORDEN_WVALUES="https://s-order.herokuapp.com/createOrdenWithValues";
    public static final String URL_ORDEN_DETALLE="https://s-order.herokuapp.com/getDetalleOrden?ordenId=";
    public static final String URL_ACTUALIZA_ESTATUS_ORDEN="https://s-order.herokuapp.com/updateOrderEstatus";
    public static final String URL_PRODUCTOXORDEN="https://s-order.herokuapp.com/getProductosOrden?ordenId=";

    public static final String KEY_RESULT="result";
    public static final String KEY_RESULTS="results";
    public static final String KEY_ID="orden_id";

    public static final String KEY_USUARIO="usuario";
    public static final String KEY_MESERO="mesero";
    public static final String KEY_ESTATUS="estatus";
    public static final String KEY_TOTAL="total";

    public static final String KEY_OP_ID="producto_id";
    public static final String KEY_OP_NOMBRE="producto_nombre";
    public static final String KEY_OP_CANTIDAD="cantidad";
    public static final String KEY_OP_ESTATUS="estatus";
    public static final String KEY_OP_PRECIO="precio";


    public static final String KEY_NOMBRE="nombre_negocio";
    public static final String KEY_LOGO="logo";

    public OrdenRepositoryImpl(){
        volleySigleton = VolleySingleton.getInstance();
        requestQueue=volleySigleton.getRequestQueue();
        eventBus = GreenRobotEventBus.getInstance();
    }

    @Override
    public void verificaOrden() {
        //NO se tiene el backend de esta funcion, por lo cual vamos utilizar sharedpreferences,
        // y algunos valores fijos considerados en la BD...
        //mas adelante implementareos el backend correspondiente que traera la ultima orden del usr


        Orden orden=new Orden();
        sharedPreferences=SelfOrderApp.getSharedPreferences ();

        Integer ordenId=sharedPreferences.getInt(SelfOrderApp.getOrdenKey(),-1);
        Integer negocioId=sharedPreferences.getInt(SelfOrderApp.getNegocioKey(),-1);

        orden.setOrdenId(ordenId);
        orden.setNegocioId(negocioId);

        //valores iniciales por mientras
        orden.setMesaId(1);
        orden.setUsuarioId(2);
        orden.setEmpleadoId(3);
        orden.setEstatusId(19);  //inicial
        orden.setEstatusPorUsuarioId(1);  //temporal
        orden.setIdExterna("0");
        orden.setIdFactura("0");
        orden.setDividida(0);
        orden.setPropina(0);

        post(OrdenEvent.VERIFICA_EVENT,orden);

    }

    @Override
    public void creaOrden(final Orden orden) {

        Log.e("oko","llega a crea orden");

        StringRequest request = new StringRequest(Request.Method.POST,URL_CREA_ORDEN_WVALUES, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                try {

                    JSONObject jObjResponse = new JSONObject(response);

                    int numOrden;
                    numOrden=jObjResponse.getInt(KEY_RESULT);
                    orden.setOrdenId(numOrden);


                } catch (JSONException e) {
                    post(OrdenEvent.CREA_EVENT, e.getMessage());
                }


                sharedPreferences=SelfOrderApp.getSharedPreferences();
                SharedPreferences.Editor editor=sharedPreferences.edit();
                editor.putInt(SelfOrderApp.getOrdenKey(),orden.getOrdenId());
                editor.commit();

                post(OrdenEvent.CREA_EVENT,orden);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                post(OrdenEvent.CREA_EVENT, error.getMessage());
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("negocioId", orden.getNegocioId().toString());
                params.put("mesaId", orden.getMesaId().toString());
                params.put("usuarioId", orden.getUsuarioId().toString());
                params.put("empleadoId", orden.getEmpleadoId().toString());
                params.put("estatusId", orden.getEstatusId().toString());
                params.put("estatusPorUsuarioId", orden.getEstatusPorUsuarioId().toString());
                params.put("idExterna", orden.getIdExterna().toString());
                params.put("idFactura", orden.getIdFactura().toString());
                params.put("dividida", orden.getDividida().toString());
                params.put("propina", orden.getPropina().toString());
                return params;
            }


        };

        requestQueue.add(request);
    }


    @Override
    public void enviaOrden(final Orden orden) {

        StringRequest request = new StringRequest(Request.Method.POST,URL_ACTUALIZA_ESTATUS_ORDEN, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                post(OrdenEvent.ENVIA_EVENT);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                post(OrdenEvent.ENVIA_EVENT, error.getMessage());
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("ordenId", orden.getOrdenId().toString());
                params.put("estatusDescr", orden.getEstatusDescr());
                return params;
            }


        };

        requestQueue.add(request);
    }

    @Override
    public void regresarOrden(Orden orden) {

    }

    @Override
    public void getOrden(final Orden orden, final Integer tipo) {

        final JsonObjectRequest request=new JsonObjectRequest(Request.Method.GET, URL_ORDEN_DETALLE+String.valueOf(orden.getOrdenId()),(String) null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                try {

                    JSONArray arrayResult = response.getJSONArray(KEY_RESULTS);
                    JSONObject currentOrdenDetalle=arrayResult.getJSONObject(0);

                    int id=currentOrdenDetalle.getInt(KEY_ID);
                    String usuario=currentOrdenDetalle.getString(KEY_USUARIO);
                    String mesero=currentOrdenDetalle.getString(KEY_MESERO);
                    String estatus=currentOrdenDetalle.getString(KEY_ESTATUS);
                    double total=currentOrdenDetalle.getDouble(KEY_TOTAL);


                    orden.setEstatusDescr(estatus);
                    orden.setTotal(total);

                } catch (JSONException e) {
                    if (OrdenEvent.CREATE_READ_EVENT==tipo){
                        post(OrdenEvent.CREATE_READ_EVENT, e.getMessage());
                    } else{post(OrdenEvent.EXISTS_READ_EVENT, e.getMessage()); }

                }
                if (OrdenEvent.CREATE_READ_EVENT==tipo){
                    post(OrdenEvent.CREATE_READ_EVENT, orden);
                } else{post(OrdenEvent.EXISTS_READ_EVENT, orden); }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (OrdenEvent.CREATE_READ_EVENT==tipo){
                    post(OrdenEvent.CREATE_READ_EVENT, error.getMessage());
                } else{post(OrdenEvent.EXISTS_READ_EVENT, error.getMessage()); }

            }
        });

        requestQueue.add(request);

    }

    @Override
    public void getProductos(final Orden orden) {
        final Orden orden1= new Orden();
        orden1.setOrdenId(orden.getOrdenId());
        orden1.setNegocioId(orden.getNegocioId());
        orden1.setTotal(orden.getTotal());
        orden1.setEstatusDescr(orden.getEstatusDescr());

        final ArrayList<OrdenProducto> listInProductos=new ArrayList<>();

        JsonObjectRequest request=new JsonObjectRequest(Request.Method.GET, URL_PRODUCTOXORDEN+orden.getOrdenId(),(String) null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                try {

                    //cuando se crea no tendra productos, el if evita que se dispare una excepcion
                    if (response.has(KEY_RESULTS)) {


                            JSONArray arrayProductos = response.getJSONArray(KEY_RESULTS);


                            for(int i=0;i<arrayProductos.length();i++){


                                JSONObject currentProducto=arrayProductos.getJSONObject(i);

                                int id=currentProducto.getInt(KEY_OP_ID);
                                String nombre=currentProducto.getString(KEY_OP_NOMBRE);
                                String estatus=currentProducto.getString(KEY_OP_ESTATUS);
                                double precio=currentProducto.getDouble(KEY_OP_PRECIO);
                                int cantidad=currentProducto.getInt(KEY_OP_CANTIDAD);

                                OrdenProducto ordenProducto=new OrdenProducto();
                                ordenProducto.setProductoId(id);
                                ordenProducto.setProductoNombre(nombre);
                                ordenProducto.setCantidad(cantidad);
                                ordenProducto.setPrecio(precio);
                                ordenProducto.setEstatus(estatus);


                                listInProductos.add(ordenProducto);

                                }
                    }
                    Log.e("oko","cuantos productos "+listInProductos.size());
                    orden1.setProductoList(listInProductos);
                    post(OrdenEvent.PRODUCTOS_EVENT, orden1);

                } catch (JSONException e) {
                    post(OrdenEvent.PRODUCTOS_EVENT, e.getMessage());
                }
            }
        },
                new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                post(OrdenEvent.PRODUCTOS_EVENT, error.getMessage());
            }
        });

        requestQueue.add(request);
    }


    private void post(int type, Orden orden){
        post(type, orden, null);
    }

    private void post(int type, String error){
        post(type, null, error);
    }

    private void post(int type){
        post(type, null, null);
    }

    private void post(int type, Orden orden, String error){
        OrdenEvent event = new OrdenEvent();
        event.setType(type);
        event.setError(error);
        event.setOrden(orden);
        eventBus.post(event);
    }


}
