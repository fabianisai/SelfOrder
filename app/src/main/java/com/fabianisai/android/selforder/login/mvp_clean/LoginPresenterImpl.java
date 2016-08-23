package com.fabianisai.android.selforder.login.mvp_clean;

import android.util.Log;

import com.fabianisai.android.selforder.lib.eventbus.EventBus;
import com.fabianisai.android.selforder.lib.eventbus.GreenRobotEventBus;
import com.fabianisai.android.selforder.login.events.LoginEvent;
import com.fabianisai.android.selforder.login.ui.LoginView;

import org.greenrobot.eventbus.Subscribe;

/**
 * Created by fabianisai on 7/20/16.
 */

public class LoginPresenterImpl implements LoginPresenter {
    private EventBus eventBus;
    private LoginView loginView;
    private LoginInteractor loginInteractor;

    public LoginPresenterImpl(LoginView loginView){
        this.loginView = loginView;
        this.loginInteractor = new LoginInteractorImpl();
        this.eventBus= GreenRobotEventBus.getInstance();
    }

    @Override
    public void onCreate() {
        eventBus.register(this);
    }

    @Override
    public void onDestroy() {
        loginView=null;
        eventBus.unregister(this);
    }

    @Override
    public void checkForAuthenticatedUser() {
        if(loginView!=null){
            loginView.disableInputs();
            loginView.showProgress();
        }

        loginInteractor.checkSession();
    }

    @Override
    public void validateLogin(String email, String password,Integer sesion) {
        if(loginView!=null){
            loginView.disableInputs();
            loginView.showProgress();
        }

        loginInteractor.doSignIn(email,password,sesion);
    }

    @Override
    @Subscribe
    public void onEventMainThread(LoginEvent event) {
        switch (event.getEventType()){
            case LoginEvent.onSignInSuccess:
                onSignInSuccess();
                break;
            case LoginEvent.onSignInError:
                onSignInError(event.getErrorMessage());
                break;
            case LoginEvent.onFailedToRecoverSession:
                onFailedToRecoverSession();
                break;
            case LoginEvent.onNoSession:
                onFailedToRecoverSession();
                break;
            case LoginEvent.onSessionActive:
                onSessionActive(event.getUsrEmail(),event.getUsrPass(),event.getUsrSesion());
                break;
        }
    }

    private void onSignInSuccess(){
        if(loginView!=null){
            loginView.navigateToMainScreen();
        }
    }

    private void onSignInError(String error){
        if(loginView!=null){
            loginView.hideProgress();
            loginView.enableInputs();
            loginView.loginError(error);
        }
    }

    private void onSessionActive(String usrEmail,String usrPass,Integer usrIdSesion){
        if(loginView!=null){
            validateLogin(usrEmail,usrPass,usrIdSesion);
        }
    }

    private void onFailedToRecoverSession() {
        if(loginView!=null){
            loginView.hideProgress();
            loginView.enableInputs();
        }

       // pendiente implementar
        Log.e("LoginPresenterImp","onFailedToRecoverSession");
    }

}
