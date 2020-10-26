package com.example.myapplication.model;


public class ContactsModel {
    String userName;
    String userPhone;
    String userBloodG;
    String userState;
    String userCity;
    String userPincode;

    public ContactsModel() {
    }

    public ContactsModel(String userName, String userPhone, String userBloodG, String userState, String userCity, String userPincode) {
        this.userName = userName;
        this.userPhone = userPhone;
        this.userBloodG = userBloodG;
        this.userState = userState;
        this.userCity = userCity;
        this.userPincode = userPincode;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getUserBloodG() {
        return userBloodG;
    }

    public void setUserBloodG(String userBloodG) {
        this.userBloodG = userBloodG;
    }

    public String getUserState() {
        return userState;
    }

    public void setUserState(String userState) {
        this.userState = userState;
    }

    public String getUserCity() {
        return userCity;
    }

    public void setUserCity(String userCity) {
        this.userCity = userCity;
    }

    public String getUserPincode() {
        return userPincode;
    }

    public void setUserPincode(String userPincode) {
        this.userPincode = userPincode;
    }
}
