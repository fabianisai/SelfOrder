package com.fabianisai.android.selforder.addProductos.mvp_clean;

import com.fabianisai.android.selforder.addProductos.entities.Producto;

/**
 * Created by fabianisai on 7/17/16.
 */

public class AddProductosInteractorImpl implements AddProductosInteractor {
    private AddProductosRepository addProductosRepository;

    public AddProductosInteractorImpl(){
        this.addProductosRepository=new AddProductosRepositoryImpl();
    }

    @Override
    public void getProductosList(Integer menuId) {
        addProductosRepository.getProductos(menuId);
    }

    @Override
    public void addProductoOrden(Producto producto) {
        addProductosRepository.addProdutoOrden(producto);
    }
}





