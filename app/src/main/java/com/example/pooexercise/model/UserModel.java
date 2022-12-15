package com.example.pooexercise.model;

import android.util.Log;

public class UserModel {

    String userNameInstitution;
    String userEmailInstituion;
    String userCnpjInstitution;
    String userPhoneInstitution;
    String userAddressInstitution;
    String userRoleIstitution;
    String urlImageInstitution;
    String idInstitution;

    public UserModel() {

    }

    public UserModel(String userNameInstitution, String userEmailInstituion, String userCnpjInstitution, String userPhoneInstitution, String userAddressInstitution, String userRoleIstitution, String urlImageInstitution, String idInstitution) {

        Log.d("myTag", "This is my message" + userNameInstitution + " ");

        this.userNameInstitution = userNameInstitution;
        this.userEmailInstituion = userEmailInstituion;
        this.userCnpjInstitution = userCnpjInstitution;
        this.userPhoneInstitution = userPhoneInstitution;
        this.userAddressInstitution = userAddressInstitution;
        this.userRoleIstitution = userRoleIstitution;
        this.urlImageInstitution = urlImageInstitution;
        this.idInstitution = idInstitution;
    }

    public String getUserNameInstitution() {
        return userNameInstitution;
    }

    public void setUserNameInstitution(String userNameInstitution) {
        this.userNameInstitution = userNameInstitution;
    }


    public String getIdInstitution() {
        return idInstitution;
    }

    public void setIdInstitution(String idInstitution) {
        this.idInstitution = idInstitution;
    }

    public String getUrlImageInstitution() {
        return urlImageInstitution;
    }

    public void setUrlImageInstitution(String urlImageInstitution) {
        this.urlImageInstitution = urlImageInstitution;
    }

    public String getUserEmailInstituion() {
        return userEmailInstituion;
    }

    public void setUserEmailInstituion(String userEmailInstituion) {
        this.userEmailInstituion = userEmailInstituion;
    }

    public String getUserCnpjInstitution() {
        return userCnpjInstitution;
    }

    public void setUserCnpjInstitution(String userCnpjInstitution) {
        this.userCnpjInstitution = userCnpjInstitution;
    }

    public String getUserPhoneInstitution() {
        return userPhoneInstitution;
    }

    public void setUserPhoneInstitution(String userPhoneInstitution) {
        this.userPhoneInstitution = userPhoneInstitution;
    }

    public String getUserAddressInstitution() {
        return userAddressInstitution;
    }

    public void setUserAddressInstitution(String userAddressInstitution) {
        this.userAddressInstitution = userAddressInstitution;
    }

    public String getUserRoleIstitution() {
        return userRoleIstitution;
    }

    public void setUserRoleIstitution(String userRoleIstitution) {
        this.userRoleIstitution = userRoleIstitution;
    }
}
