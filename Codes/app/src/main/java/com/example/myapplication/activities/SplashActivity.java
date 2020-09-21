package com.example.myapplication.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;

import com.example.myapplication.R;
import com.example.myapplication.util.ApplicationHolder;
import com.example.myapplication.util.SharedPreference;


public class SplashActivity extends Activity {

    private Handler handler;
    private boolean isFrom=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash);


       // WifiManager manager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
       // WifiInfo info = manager.getConnectionInfo();
       // ApplicationHolder.MAC_ID = info.getMacAddress();

        Bundle bundle=getIntent().getExtras();

        if(bundle!=null){


            isFrom=bundle.getBoolean("hi");
        }

        //Intent intent = new Intent(SplashActivity.this, MyFirebaseInstanceId.class);
       // startService(intent);

        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                SharedPreference sharedPreference=new SharedPreference(SplashActivity.this);
                sharedPreference.getPrefValue(ApplicationHolder.USERNAME);
                if(sharedPreference.getPrefValue(ApplicationHolder.USERNAME).equalsIgnoreCase("")) {

                        startActivity(new Intent(SplashActivity.this, LoginActivity.class));


                    finish();
                }else{
                if(isFrom){
                    startActivity(new Intent(SplashActivity.this, NotificationsActivity.class));
                }else {
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                }
                    finish();
                }
            }
        }, 1000);

    }

    @Override
    protected void onStart() {
        super.onStart();

    }
}
