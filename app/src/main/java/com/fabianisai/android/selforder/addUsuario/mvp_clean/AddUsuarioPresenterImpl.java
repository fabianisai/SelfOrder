package com.fabianisai.android.selforder.addUsuario.mvp_clean;

import com.fabianisai.android.selforder.addUsuario.events.AddUsuarioEvent;
import com.fabianisai.android.selforder.addUsuario.ui.AddUsuarioView;
import com.fabianisai.android.selforder.lib.eventbus.EventBus;
import com.fabianisai.android.selforder.lib.eventbus.GreenRobotEventBus;

import org.greenrobot.eventbus.Subscribe;

/**
 * Created by fabianisai on 7/28/16.
 */

public class AddUsuarioPresenterImpl implements AddUsuarioPresenter {
    private EventBus eventBus;
    private AddUsuarioView addUsuarioView;
    private AddUsuarioInteractor addUsuarioInteractor;

    public AddUsuarioPresenterImpl(AddUsuarioView addUsuarioView){
        this.addUsuarioView = addUsuarioView;
        this.addUsuarioInteractor = new AddUsuarioInteractorImpl();
        this.eventBus= GreenRobotEventBus.getInstance();
    }

    @Override
    public void onCreate() {
        eventBus.register(this);
    }

    @Override
    public void onDestroy() {
        addUsuarioView=null;
        eventBus.unregister(this);
    }

    @Override
    public void creaUser(String email, String password, String repassword,Integer SessionType) {
        if(addUsuarioView!=null){
            addUsuarioView.disableInputs();
            addUsuarioView.showProgress();
        }

        addUsuarioInteractor.createUser(email,password,repassword,SessionType);

    }

    @Override
    @Subscribe
    public void onEventMainThread(AddUsuarioEvent event) {
        switch (event.getEventType()){
            case AddUsuarioEvent.onSignUpSuccess:
                onSignUpSuccess();
                break;
            case AddUsuarioEvent.onSignUpError:
                onSignUpError(event.getErrorMessage());
                break;
        }
    }

    private void onSignUpSuccess(){
        if(addUsuarioView!=null){
            addUsuarioView.navigateToLogin();
        }
    }

    private void onSignUpError(String error){
        if(addUsuarioView!=null){
            addUsuarioView.hideProgress();
            addUsuarioView.enableInputs();
            addUsuarioView.addUsuarioError(error);
        }
    }
}
