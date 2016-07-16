package com.fabianisai.android.selforder.orden.mvp_clean;

import android.util.Log;

import com.fabianisai.android.selforder.lib.eventbus.EventBus;
import com.fabianisai.android.selforder.lib.eventbus.GreenRobotEventBus;
import com.fabianisai.android.selforder.orden.entities.Orden;
import com.fabianisai.android.selforder.orden.events.OrdenEvent;
import com.fabianisai.android.selforder.orden.ui.OrdenView;
import com.fabianisai.android.selforder.orden.ui.OrdeneActivity;

import org.greenrobot.eventbus.Subscribe;

/**
 * Created by fabianisai on 7/13/16.
 */

public class OrdenPresenterImpl implements OrdenPresenter {
    private EventBus eventBus;
    private OrdenView ordenView;
    private OrdenInteractor ordenInteractor;



    public OrdenPresenterImpl(OrdenView ordenView){
        this.ordenView=ordenView;
        this.ordenInteractor= new OrdenInteractorImpl();
        this.eventBus= GreenRobotEventBus.getInstance();
        eventBus.register(this);
    }

    @Override
    public void onResume() {
        eventBus.register(this);
    }

    @Override
    public void onPause() {
        eventBus.unregister(this);
    }

    @Override
    public void onDestroy() {
        ordenView=null;
    }

    @Override
    public void verificaOrden() {
        if (this.ordenView != null){
            ordenView.hideProductos();
            ordenView.hideOrden();
            ordenView.showProgress();
        }
        ordenInteractor.verificaOrden();
    }



    @Override
    public void enviaOrden(Orden orden) {
        if (this.ordenView != null){
            ordenView.showProgress();
        }
        this.ordenInteractor.enviaOrden(orden);

    }

    @Override
    public void regresarOrden(Orden orden) {

    }



    @Override
    @Subscribe
    public void onEventMainThread(OrdenEvent event) {

        if (this.ordenView != null) {
            if (this.ordenView != null && (event.getType()!=OrdenEvent.CREATE_READ_EVENT || event.getType()!=OrdenEvent.ENVIA_EVENT || event.getType() == OrdenEvent.PRODUCTOS_EVENT)){
                this.ordenView.hideProgress();
                this.ordenView.showOrden();
                this.ordenView.showProductos();
            }
            String error = event.getError();
            if (error != null) {
                this.ordenView.onOrdenError(error);
            } else {
                if (event.getType() == OrdenEvent.VERIFICA_EVENT) {
                    Orden orden=new Orden();
                    orden=event.getOrden();

                    Log.e("oko","evento VERIFICA_EVENT");

                    if (orden.getOrdenId()==null || orden.getOrdenId()==-1){
                        this.ordenInteractor.creaOrden(orden);
                    } else{
                        this.ordenInteractor.getOrden(event.getOrden(),OrdenEvent.EXISTS_READ_EVENT);
                    }

                } else if (event.getType() == OrdenEvent.CREA_EVENT) {
                    this.ordenInteractor.getOrden(event.getOrden(),OrdenEvent.CREATE_READ_EVENT);
                }  else if (event.getType() == OrdenEvent.CREATE_READ_EVENT) {
                    ordenView.onOrdenCreate(event.getOrden());
                } else if (event.getType() == OrdenEvent.ENVIA_EVENT) {
                        ordenView.onOrdenEnvida();
                } else if (event.getType() == OrdenEvent.EXISTS_READ_EVENT) {
                    this.ordenInteractor.getProductos(event.getOrden());
                } else if (event.getType() == OrdenEvent.PRODUCTOS_EVENT) {
                    ordenView.setProductos(event.getOrden());
                }
            }
        }
    }
}
