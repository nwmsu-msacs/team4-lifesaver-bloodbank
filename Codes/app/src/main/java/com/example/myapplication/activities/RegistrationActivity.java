package com.example.myapplication.activities;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.core.app.ActivityCompat;

import com.example.myapplication.R;
import com.example.myapplication.asynctask.NotificationCountAsycTask;
import com.example.myapplication.util.ApplicationHolder;
import com.example.myapplication.util.JSONParser;


public class MainActivity extends SuperClasses implements View.OnClickListener {
    LinearLayout messageLayout, requestLayout,notificationLayout,donorsLayout;
    RelativeLayout notificationsBellLayout;
    private Handler mHandler;
    private Runnable mRunnable;
    private int mInterval = 5000;
    public static String response;
    TextView countNotifications;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.user_home_activity);
        loadGenericValues(this, getResources().getString(R.string.menu_home));
        new NotificationCountAsycTask(this).execute();

        int PERMISSION_ALL = 1;
        String[] PERMISSIONS = {Manifest.permission.CALL_PHONE};

        if(!hasPermissions(this, PERMISSIONS)){
            ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL);
        }
//        SharedPreference sharedPreference=new SharedPreference(this);
//        sharedPreference.setPrefValue(ApplicationHolder.USERNAME,"9533320096");
        donorsLayout=(LinearLayout)findViewById(R.id.donorsLayout);
        messageLayout = (LinearLayout) findViewById(R.id.responsesLayput);
        requestLayout = (LinearLayout) findViewById(R.id.requestLayout);
        notificationLayout=(LinearLayout) findViewById(R.id.notificationLayout);

        notificationsBellLayout=(RelativeLayout) findViewById(R.id.notificationsBellLayout);
        notificationsBellLayout.setOnClickListener(this);


        donorsLayout.setOnClickListener(this);
       countNotifications=(TextView)findViewById(R.id.creditsId);
      //  contactLayout.setOnClickListener(this);
        messageLayout.setOnClickListener(this);
        requestLayout.setOnClickListener(this);
        notificationLayout.setOnClickListener(this);
        mHandler = new Handler();

        mRunnable = new Runnable() {
            @Override
            public void run() {
                doTask();
            }
        };
        mHandler.postDelayed(mRunnable, (mInterval));

}



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){

            case R.id.notificationLayout:
              // new ShowAllRequestsAsyncTask(this).execute();
                startActivity(new Intent(MainActivity.this, NotificationsActivity.class));
                //Toast.makeText(this,"Work in progress of Notifications",Toast.LENGTH_SHORT).show();
               break;
            case R.id.responsesLayput:
                startActivity(new Intent(MainActivity.this, ResponseActivity.class));
                break;
            case R.id.requestLayout:
                startActivity(new Intent(MainActivity.this, ShowAllRequestsActivity.class));
               // Toast.makeText(this,"Work in progress of Requests",Toast.LENGTH_SHORT).show();
                break;
            case R.id.notificationsBellLayout:
                startActivity(new Intent(MainActivity.this, NotificationsActivity.class));
                break;
            case R.id.donorsLayout:
                startActivity(new Intent(MainActivity.this, DonorsActivity.class));
                break;
            default:break;
        }
    }