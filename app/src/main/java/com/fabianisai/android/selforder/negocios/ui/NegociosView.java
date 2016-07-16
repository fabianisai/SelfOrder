package com.fabianisai.android.selforder.negocios.ui;

import com.fabianisai.android.selforder.negocios.entities.Negocio;

import java.util.List;

/**
 * Created by fabianisai on 7/10/16.
 */

public interface NegociosView {
    void showList();
    void hideList();
    void showProgress();
    void hideProgress();

    void onNegociosError(String error);
    void setNegocios(List<Negocio> items);

}
