package com.fabianisai.android.selforder.addProductos.mvp_clean;

import com.fabianisai.android.selforder.addProductos.entities.Producto;
import com.fabianisai.android.selforder.addProductos.events.ProductosEvent;
import com.fabianisai.android.selforder.addProductos.ui.AddProductosView;
import com.fabianisai.android.selforder.lib.eventbus.EventBus;
import com.fabianisai.android.selforder.lib.eventbus.GreenRobotEventBus;

import org.greenrobot.eventbus.Subscribe;

import java.util.List;

/**
 * Created by fabianisai on 7/17/16.
 */

public class AddProductosPresenterImpl implements AddProductosPresenter {
    private EventBus eventBus;
    private AddProductosView addProductosView;
    private AddProductosInteractor addProductosInteractor;


    public AddProductosPresenterImpl(AddProductosView addProductosView){
        this.addProductosView=addProductosView;
        this.addProductosInteractor= new AddProductosInteractorImpl();
        this.eventBus= GreenRobotEventBus.getInstance();
         eventBus.register(this);
    }

    @Override
    public void onCreate() {

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
        addProductosView=null;
    }

    @Override
    public void getProductos(Integer menuId) {
        if (this.addProductosView != null){
            addProductosView.hideList();
            addProductosView.showProgress();
        }
        this.addProductosInteractor.getProductosList(menuId);
    }

    @Override
    public void addProducto(Producto producto) {
        if (this.addProductosView != null){
            addProductosView.hideList();
            addProductosView.showProgress();
        }
        this.addProductosInteractor.addProductoOrden(producto);
    }

    @Override
    @Subscribe
    public void onEventMainThread(ProductosEvent event) {
        String errorMsg = event.getError();
        if (this.addProductosView != null) {
            addProductosView.showList();
            addProductosView.hideProgress();
            if (errorMsg != null) {
                this.addProductosView.onProductosError(errorMsg);
            } else {
                if(event.getType()==ProductosEvent.GET_PRODUCTOS) {
                    List<Producto> items = event.getProductos();
                    if (items != null && !items.isEmpty()) {
                        this.addProductosView.setProductos(items);
                    }
                }else{
                    this.addProductosView.onAddProducto(event.getNameProducto());
                    //toast
                }
            }
        }
    }
}
