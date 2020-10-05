package com.example.myapplication.model;

public class CreateResponse {

    private String Date;
    private String city;
    private String email;
    private String group;
    private String id;
    private String mobile;
    private String patientName;
    private String units;
    private String isClosed;
    private String emailClient;

    public String getEmailClient() {
        return emailClient;
    }

    public void setEmailClient(String emailClient) {
        this.emailClient = emailClient;
    }

    public String getIsClosed() {
        return isClosed;
    }

    public void setIsClosed(String isClosed) {
        this.isClosed = isClosed;
    }

    public CreateResponse(String date, String city, String email, String group, String id, String mobile, String patientName, String units, String isClosed,String emailClient) {
        Date = date;
        this.city = city;
        this.email = email;
        this.group = group;
        this.id = id;
        this.mobile = mobile;
        this.patientName = patientName;
        this.units = units;
        this.isClosed = isClosed;
        this.emailClient = emailClient;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getUnits() {
        return units;
    }

    public void setUnits(String units) {
        this.units = units;
    }
}
