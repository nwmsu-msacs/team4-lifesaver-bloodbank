package com.example.myapplication.firebase;

import android.util.Log;

import com.example.myapplication.util.ApplicationHolder;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;



public class MyFirebaseInstanceId extends FirebaseInstanceIdService {

    @Override
    public void onTokenRefresh(){
       // super.onTokenRefresh();
        ApplicationHolder.TOKEN_ID= FirebaseInstanceId.getInstance().getToken();
    }
}
