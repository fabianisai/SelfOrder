package com.fabianisai.android.selforder.addProductos.ui;

import com.fabianisai.android.selforder.addProductos.entities.Producto;

import java.util.List;

/**
 * Created by fabianisai on 7/17/16.
 */

public interface AddProductosView {
    void showList();
    void hideList();
    void showProgress();
    void hideProgress();

    void onProductosError(String error);
    void setProductos(List<Producto> items);
    void onAddProducto(String nameProducto);
}
