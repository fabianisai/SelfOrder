package com.fabianisai.android.selforder.orden.mvp_clean;

import com.fabianisai.android.selforder.orden.entities.Orden;

/**
 * Created by fabianisai on 7/13/16.
 */

public class OrdenInteractorImpl implements OrdenInteractor {
    private OrdenRepositoryImpl ordenRepository;

    public OrdenInteractorImpl(){
        this.ordenRepository=new OrdenRepositoryImpl();
    }

    @Override
    public void verificaOrden() {
        ordenRepository.verificaOrden();
    }

    @Override
    public void creaOrden(Orden orden) {
        ordenRepository.creaOrden(orden);
    }

    @Override
    public void enviaOrden(Orden orden) {
        ordenRepository.enviaOrden(orden);
    }

    @Override
    public void regresarOrden(Orden orden) {

    }

    @Override
    public void getOrden(Orden orden,Integer tipo) {
        ordenRepository.getOrden(orden,tipo);
    }

    @Override
    public void getProductos(Orden orden) {
            ordenRepository.getProductos(orden);
    }
}
