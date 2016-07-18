package com.fabianisai.android.selforder.addProductos.mvp_clean;

import com.fabianisai.android.selforder.addProductos.entities.Producto;
import com.fabianisai.android.selforder.addProductos.events.ProductosEvent;
/**
 * Created by fabianisai on 7/17/16.
 */

public interface AddProductosPresenter {
    void onCreate();
    void onResume();
    void onPause();
    void onDestroy();
    void getProductos(Integer menuId);
    void addProducto(Producto producto);
    void onEventMainThread(ProductosEvent event);
}
