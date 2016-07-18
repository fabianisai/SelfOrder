package com.fabianisai.android.selforder.addProductos.entities;

/**
 * Created by fabianisai on 7/17/16.
 */

public class Producto {
    private int id;
    private String nombre;
    private String descripcion;
    private double precio;

    public Producto() {
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
