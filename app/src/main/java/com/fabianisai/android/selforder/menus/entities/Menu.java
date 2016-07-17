package com.fabianisai.android.selforder.menus.entities;

/**
 * Created by fabianisai on 7/16/16.
 */

public class Menu {
    private int id;
    private String descripcion;

    public Menu(){

    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
