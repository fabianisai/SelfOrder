package com.fabianisai.android.selforder.negocios.events;

import com.fabianisai.android.selforder.negocios.entities.Negocio;

import java.util.List;

/**
 * Created by fabianisai on 7/10/16.
 */

public class NegociosEvent {
    private String error;
    private List<Negocio> negocios;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public List<Negocio> getNegocios() {
        return negocios;
    }

    public void setHashtags(List<Negocio> negocios) {
        this.negocios = negocios;
    }
}
