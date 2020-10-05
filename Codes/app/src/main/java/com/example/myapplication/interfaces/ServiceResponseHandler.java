package com.example.myapplication.interfaces;


public interface ServiceResponseHandler {

    void onSuccess(String response);
    void onFailure(String failResponse);
}
