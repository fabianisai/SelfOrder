package com.fabianisai.android.selforder.orden.events;

import com.fabianisai.android.selforder.orden.entities.Orden;

import java.util.List;

/**
 * Created by fabianisai on 7/13/16.
 */

public class OrdenEvent {
    private int type;
    private String error;
    private Orden orden;
    public final static int VERIFICA_EVENT = 0;
    public final static int CREA_EVENT = 1;
    public final static int CREATE_READ_EVENT = 2;
    public final static int EXISTS_READ_EVENT = 3;
    public final static int ENVIA_EVENT = 4;
    public final static int PRODUCTOS_EVENT = 5;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setOrden(Orden orden) {
        this.orden = orden;
    }

    public int getType() {
        return type;
    }

    public Orden getOrden() {
        return orden;
    }
}
