package com.project.workgroup.model.entidades;

import java.io.Serializable;

/**
 * Created by Junior on 22/10/2015.
 */
public class EventDetail implements Serializable {


    private String id ;
    private String title;
    private String descripcion;
    private String direccion;
    private String categoria_evento;
    private String sub_categoria_evento;
    private String nombre_lugar;
    private String logo_lugar;
    private  float latitud_lugar;
    private  float longitud_lugar;
    private String descripcion_lugar;
    private String tipo_lugar;
    private String organizador;



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }


    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCategoria_evento() {
        return categoria_evento;
    }

    public void setCategoria_evento(String categoria_evento) {
        this.categoria_evento = categoria_evento;
    }

    public String getSub_categoria_evento() {
        return sub_categoria_evento;
    }

    public void setSub_categoria_evento(String sub_categoria_evento) {
        this.sub_categoria_evento = sub_categoria_evento;
    }

    public String getNombre_lugar() {
        return nombre_lugar;
    }

    public void setNombre_lugar(String nombre_lugar) {
        this.nombre_lugar = nombre_lugar;
    }

    public String getLogo_lugar() {
        return logo_lugar;
    }

    public void setLogo_lugar(String logo_lugar) {
        this.logo_lugar = logo_lugar;
    }

    public float getLatitud_lugar() {
        return latitud_lugar;
    }

    public void setLatitud_lugar(float latitud_lugar) {
        this.latitud_lugar = latitud_lugar;
    }

    public float getLongitud_lugar() {
        return longitud_lugar;
    }

    public void setLongitud_lugar(float longitud_lugar) {
        this.longitud_lugar = longitud_lugar;
    }

    public String getDescripcion_lugar() {
        return descripcion_lugar;
    }

    public void setDescripcion_lugar(String descripcion_lugar) {
        this.descripcion_lugar = descripcion_lugar;
    }

    public String getTipo_lugar() {
        return tipo_lugar;
    }

    public void setTipo_lugar(String tipo_lugar) {
        this.tipo_lugar = tipo_lugar;
    }

    public String getOrganizador() {
        return organizador;
    }

    public void setOrganizador(String organizador) {
        this.organizador = organizador;
    }
}
