package com.example.pooexercise.model;

public class LoginModel {

    String sEmail, sPassword;

    public LoginModel(String sEmail, String sPassword) {
        this.sEmail = sEmail;
        this.sPassword = sPassword;
    }

    public String getsEmail() {
        return sEmail;
    }

    public void setsEmail(String sEmail) {
        this.sEmail = sEmail;
    }

    public String getsPassword() {
        return sPassword;
    }

    public void setsPassword(String sPassword) {
        this.sPassword = sPassword;
    }
}
