package com.fabianisai.android.selforder.negocios.mvp_clean;

import com.fabianisai.android.selforder.negocios.entities.Negocio;
import com.fabianisai.android.selforder.negocios.events.NegociosEvent;
import com.fabianisai.android.selforder.lib.eventbus.EventBus;
import com.fabianisai.android.selforder.lib.eventbus.GreenRobotEventBus;
import com.fabianisai.android.selforder.negocios.ui.NegociosView;

import org.greenrobot.eventbus.Subscribe;

import java.util.List;

/**
 * Created by fabianisai on 7/10/16.
 */

public class NegociosPresenterImpl implements NegociosPresenter {

    private EventBus eventBus;
    private NegociosView negociosView;
    private NegociosInteractor negociosInteractor;


    public NegociosPresenterImpl(NegociosView negociosView){
        this.negociosView=negociosView;
        this.negociosInteractor= new NegociosInteractorImpl();
        this.eventBus= GreenRobotEventBus.getInstance();
    }

    @Override
    public void onCreate() {
            //se usa en a inyeccion
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
        negociosView=null;
    }

    @Override
    public void getNegocios() {
        if (this.negociosView != null){
            negociosView.hideList();
            negociosView.showProgress();
        }
        this.negociosInteractor.getNegociosList();

    }

    @Override
    @Subscribe
    public void onEventMainThread(NegociosEvent event) {
        String errorMsg = event.getError();
        if (this.negociosView != null) {
            negociosView.showList();
            negociosView.hideProgress();
            if (errorMsg != null) {
                this.negociosView.onNegociosError(errorMsg);
            } else {
                List<Negocio> items = event.getNegocios();
                if (items != null && !items.isEmpty()) {
                    this.negociosView.setNegocios(items);
                }
            }
        }

    }
}
