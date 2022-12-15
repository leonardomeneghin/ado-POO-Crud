package com.example.pooexercise.model;

public class IntegranteModel {

    String nameIntegrante = "";
    String emailIntegrante = "";
    String functionIntegrante = "";
    String cpfIntegrante = "";
    String rgIntegrante = "";
    String phoneIntegrate = "";
    String urlImageIntegrante = "";
    String userRoleInstitution = "";
    String idInstitution = "";
    String idIntegrante = "";
    String passwordIntegrante = "";

    public IntegranteModel(){

    }

    public IntegranteModel(String nameIntegrante, String emailIntegrante, String functionIntegrante, String cpfIntegrante, String rgIntegrante, String phoneIntegrate, String urlImageIntegrante, String userRoleInstitution, String idInstitution, String idIntegrante, String passwordIntegrante) {
        this.nameIntegrante = nameIntegrante;
        this.emailIntegrante = emailIntegrante;
        this.functionIntegrante = functionIntegrante;
        this.cpfIntegrante = cpfIntegrante;
        this.rgIntegrante = rgIntegrante;
        this.phoneIntegrate = phoneIntegrate;
        this.urlImageIntegrante = urlImageIntegrante;
        this.userRoleInstitution = userRoleInstitution;
        this.idInstitution = idInstitution;
        this.idIntegrante = idIntegrante;
        this.passwordIntegrante = passwordIntegrante;
    }

    public String getPasswordIntegrante() {
        return passwordIntegrante;
    }

    public void setPasswordIntegrante(String passwordIntegrante) {
        this.passwordIntegrante = passwordIntegrante;
    }

    public String getIdIntegrante() {
        return idIntegrante;
    }

    public void setIdIntegrante(String idIntegrante) {
        this.idIntegrante = idIntegrante;
    }

    public String getNameIntegrante() {
        return nameIntegrante;
    }

    public void setNameIntegrante(String nameIntegrante) {
        this.nameIntegrante = nameIntegrante;
    }

    public String getEmailIntegrante() {
        return emailIntegrante;
    }

    public void setEmailIntegrante(String emailIntegrante) {
        this.emailIntegrante = emailIntegrante;
    }

    public String getFunctionIntegrante() {
        return functionIntegrante;
    }

    public void setFunctionIntegrante(String functionIntegrante) {
        this.functionIntegrante = functionIntegrante;
    }

    public String getCpfIntegrante() {
        return cpfIntegrante;
    }

    public void setCpfIntegrante(String cpfIntegrante) {
        this.cpfIntegrante = cpfIntegrante;
    }

    public String getRgIntegrante() {
        return rgIntegrante;
    }

    public void setRgIntegrante(String rgIntegrante) {
        this.rgIntegrante = rgIntegrante;
    }

    public String getPhoneIntegrate() {
        return phoneIntegrate;
    }

    public void setPhoneIntegrate(String phoneIntegrate) {
        this.phoneIntegrate = phoneIntegrate;
    }

    public String getUrlImageIntegrante() {
        return urlImageIntegrante;
    }

    public void setUrlImageIntegrante(String urlImageIntegrante) {
        this.urlImageIntegrante = urlImageIntegrante;
    }

    public String getUserRoleInstitution() {
        return userRoleInstitution;
    }

    public void setUserRoleInstitution(String userRoleInstitution) {
        this.userRoleInstitution = userRoleInstitution;
    }

    public String getIdInstitution() {
        return idInstitution;
    }

    public void setIdInstitution(String idInstitution) {
        this.idInstitution = idInstitution;
    }
}
