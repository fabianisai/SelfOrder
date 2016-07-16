package com.fabianisai.android.selforder.negocios.mvp_clean;

/**
 * Created by fabianisai on 7/11/16.
 */

public class NegociosInteractorImpl implements NegociosInteractor {
    private NegociosReposirtory negociosReposirtory;

    public NegociosInteractorImpl(){
        this.negociosReposirtory=new NegociosRepositoryImpl();
    }

    @Override
    public void getNegociosList() {
        negociosReposirtory.getNegocios();
    }
}

