package com.example.myapplication.firebase;

import android.content.Intent;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;


public class MyFirebaseMessagingServiece extends FirebaseMessagingService {

    public final static String REQUEST_STRING="REQUEST_STRING";
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        Intent msgIntent = new Intent(MyFirebaseMessagingServiece.this, GcmIntentService.class);
        msgIntent.putExtra(REQUEST_STRING, remoteMessage.getNotification().getBody());
        startService(msgIntent);
    }





}
