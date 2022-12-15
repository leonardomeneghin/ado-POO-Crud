package com.example.pooexercise.model;

public class modelRecyclerViewIntegrante {

    String nameIntegrante, functionIntegrante, urlImageIntegrante, idIntegrante;

    public modelRecyclerViewIntegrante(){

    }

    public modelRecyclerViewIntegrante(String nameIntegrante, String functionIntegrante, String urlImageIntegrante, String idIntegrante) {
        this.nameIntegrante = nameIntegrante;
        this.functionIntegrante = functionIntegrante;
        this.urlImageIntegrante = urlImageIntegrante;
        this.idIntegrante = idIntegrante;
    }

    public String getNameIntegrante() {
        return nameIntegrante;
    }

    public void setNameIntegrante(String nameIntegrante) {
        this.nameIntegrante = nameIntegrante;
    }

    public String getFunctionIntegrante() {
        return functionIntegrante;
    }

    public void setFunctionIntegrante(String functionIntegrante) {
        this.functionIntegrante = functionIntegrante;
    }

    public String getUrlImageIntegrante() {
        return urlImageIntegrante;
    }

    public void setUrlImageIntegrante(String urilImageIntegrante) {
        this.urlImageIntegrante = urilImageIntegrante;
    }

    public String getIdIntegrante() {
        return idIntegrante;
    }

    public void setIdIntegrante(String idIntegrante) {
        this.idIntegrante = idIntegrante;
    }
}
