package com.project.workgroup.model.entidades;

import java.io.Serializable;

/**
 * Created by Junior on 14/10/2015.
 */
public class Lugar  implements Serializable{

    private int id;
    private String nombre;
    private String logo;
    private float longitud;
    private float latitud;
    private String direccion;
    private String web;
    private String facebook;
    private Tipo_lugar tipo_lugar;

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

    public float getLongitud() {
        return longitud;
    }

    public void setLongitud(float longitud) {
        this.longitud = longitud;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public float getLatitud() {
        return latitud;
    }

    public void setLatitud(float latitud) {
        this.latitud = latitud;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getWeb() {
        return web;
    }

    public void setWeb(String web) {
        this.web = web;
    }

    public String getFacebook() {
        return facebook;
    }

    public void setFacebook(String facebook) {
        this.facebook = facebook;
    }

    public Tipo_lugar getTipo_lugar() {
        return tipo_lugar;
    }

    public void setTipo_lugar(Tipo_lugar tipo_lugar) {
        this.tipo_lugar = tipo_lugar;
    }
}
