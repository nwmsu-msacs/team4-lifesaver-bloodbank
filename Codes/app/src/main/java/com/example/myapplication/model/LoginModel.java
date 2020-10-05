package com.example.myapplication.model;

import com.google.gson.annotations.SerializedName;


public class LoginModel {
    public String userName;
    public String password;

    public LoginModel(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public LoginModel() {
    }
    @SerializedName("username")
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
