package com.fabianisai.android.selforder.orden.ui;

import com.fabianisai.android.selforder.orden.entities.Orden;
import com.fabianisai.android.selforder.orden.entities.OrdenProducto;

import java.util.List;

/**
 * Created by fabianisai on 7/12/16.
 */

public interface OrdenView {
    void showProductos();
    void hideProductos();
    void showOrden();
    void hideOrden();
    void showProgress();
    void hideProgress();

    void onOrdenError(String error);
    void onOrdenCreate(Orden orden);
    void onOrdenEnvida();

    void setProductos(Orden orden);
}
