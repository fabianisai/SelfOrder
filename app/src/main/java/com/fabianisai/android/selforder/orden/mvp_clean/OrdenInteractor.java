package com.fabianisai.android.selforder.orden.mvp_clean;

import com.fabianisai.android.selforder.orden.entities.Orden;
import com.fabianisai.android.selforder.orden.events.OrdenEvent;

/**
 * Created by fabianisai on 7/13/16.
 */

public interface OrdenInteractor {
    void verificaOrden();
    void creaOrden(Orden orden);
    void enviaOrden(Orden orden);
    void regresarOrden(Orden orden);
    void getOrden(Orden orden, Integer tipo);
    void getProductos(Orden orden);
}
