package com.fabianisai.android.selforder.orden.entities;

import com.google.gson.Gson;

import java.util.Date;
import java.util.List;

/**
 * Created by fabianisai on 7/12/16.
 */
public class Orden {
    private Integer ordenId;
    private Integer negocioId;
    private Integer mesaId;
    private Integer usuarioId;
    private Integer empleadoId;
    private Integer estatusId;
    private Integer estatusPorUsuarioId;
    private String idExterna;
    private String idFactura;
    private Integer dividida;
    private Integer propina;
    private Date fechaCreacion;
    private Date fechaCierre;
    private String estatusDescr;
    private Double total;
    List<OrdenProducto> productoList;

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public String getEstatusDescr() {
        return estatusDescr;
    }

    public void setEstatusDescr(String estatusDescr) {
        this.estatusDescr = estatusDescr;
    }

    public List<OrdenProducto> getProductoList() {
        return productoList;
    }

    public void setProductoList(List<OrdenProducto> productoList) {
        this.productoList = productoList;
    }



    public String serializeOrder(){
        Gson ordenJson=new Gson();
        return ordenJson.toJson(this);
    }

    public static Orden createFromJson(String userJson){
        Gson objectJson = new Gson();
        return objectJson.fromJson(userJson,Orden.class);
    }


    public Integer getOrdenId() {
        return ordenId;
    }

    public void setOrdenId(Integer ordenId) {
        this.ordenId = ordenId;
    }

    public Integer getNegocioId() {
        return negocioId;
    }

    public void setNegocioId(Integer negocioId) {
        this.negocioId = negocioId;
    }

    public Integer getMesaId() {
        return mesaId;
    }

    public void setMesaId(Integer mesaId) {
        this.mesaId = mesaId;
    }

    public Integer getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Integer usuarioId) {
        this.usuarioId = usuarioId;
    }

    public Integer getEmpleadoId() {
        return empleadoId;
    }

    public void setEmpleadoId(Integer empleadoId) {
        this.empleadoId = empleadoId;
    }

    public Integer getEstatusId() {
        return estatusId;
    }

    public void setEstatusId(Integer estatusId) {
        this.estatusId = estatusId;
    }

    public Integer getEstatusPorUsuarioId() {
        return estatusPorUsuarioId;
    }

    public void setEstatusPorUsuarioId(Integer estatusPorUsuarioId) {
        this.estatusPorUsuarioId = estatusPorUsuarioId;
    }

    public String getIdExterna() {
        return idExterna;
    }

    public void setIdExterna(String idExterna) {
        this.idExterna = idExterna;
    }

    public String getIdFactura() {
        return idFactura;
    }

    public void setIdFactura(String idFactura) {
        this.idFactura = idFactura;
    }

    public Integer getDividida() {
        return dividida;
    }

    public void setDividida(Integer dividida) {
        this.dividida = dividida;
    }

    public Integer getPropina() {
        return propina;
    }

    public void setPropina(Integer propina) {
        this.propina = propina;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Date getFechaCierre() {
        return fechaCierre;
    }

    public void setFechaCierre(Date fechaCierre) {
        this.fechaCierre = fechaCierre;
    }
}
