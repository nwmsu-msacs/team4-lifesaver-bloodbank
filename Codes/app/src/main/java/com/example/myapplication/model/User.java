package com.example.myapplication.model;

public class User {

    private String city;
    private String dob;
    private String email;
    private String gender;
    private String group;
    private String mobile;
    private String name;
    private String password;

    public User(String city, String dob, String email, String gender, String group, String mobile, String name, String password) {
        this.city = city;
        this.dob = dob;
        this.email = email;
        this.gender = gender;
        this.group = group;
        this.mobile = mobile;
        this.name = name;
        this.password = password;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
