package com.fabianisai.android.selforder.addProductos.mvp_clean;

import com.fabianisai.android.selforder.addProductos.entities.Producto;

/**
 * Created by fabianisai on 7/17/16.
 */

public interface AddProductosRepository {
    void getProductos(Integer menuId);
    void addProdutoOrden(Producto producto);
}
