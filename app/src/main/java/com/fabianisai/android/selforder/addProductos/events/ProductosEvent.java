package com.fabianisai.android.selforder.addProductos.events;

import com.fabianisai.android.selforder.addProductos.entities.Producto;

import java.util.List;

/**
 * Created by fabianisai on 7/17/16.
 */

public class ProductosEvent {
    public Integer getType() {
        return type;
    }

    private Integer type;
    private String error;
    private String nameProducto;
    private List<Producto> productos;
    public final static int ERROR = 0;
    public final static int GET_PRODUCTOS = 1;
    public final static int ADD_PRODUCTOS = 2;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public List<Producto> getProductos() {
        return productos;
    }

    public void setProductos(List<Producto> productos) {
        this.productos = productos;
    }

    public String getNameProducto() {
        return nameProducto;
    }

    public void setNameProducto(String nameProducto) {
        this.nameProducto = nameProducto;
    }

    public void setType(int type) {
        this.type = type;
    }
}
