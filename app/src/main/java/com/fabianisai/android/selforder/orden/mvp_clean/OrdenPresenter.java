package com.fabianisai.android.selforder.orden.mvp_clean;

import com.fabianisai.android.selforder.orden.entities.Orden;
import com.fabianisai.android.selforder.orden.events.OrdenEvent;

/**
 * Created by fabianisai on 7/12/16.
 */

public interface OrdenPresenter {
    void onResume();
    void onPause();
    void onDestroy();
    void verificaOrden();
    void enviaOrden(Orden orden);
    void regresarOrden(Orden orden);
    void onEventMainThread(OrdenEvent event);
}
