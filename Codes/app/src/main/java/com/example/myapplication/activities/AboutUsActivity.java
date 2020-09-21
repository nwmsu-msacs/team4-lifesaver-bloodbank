package com.example.myapplication.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.Window;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.asynctask.NotificationCountAsycTask;

public class AboutUsActivity extends SuperClasses implements View.OnClickListener{
    RelativeLayout notificationsBellLayout;
    TextView countNotifications;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_aboutus);
        loadGenericValues(this, getResources().getString(R.string.menu_aboutus));
        notificationsBellLayout=(RelativeLayout) findViewById(R.id.notificationsBellLayout);
        notificationsBellLayout.setOnClickListener(this);
        countNotifications=(TextView)findViewById(R.id.creditsId);
        new NotificationCountAsycTask(this).execute();
        TextView aboutaUs=(TextView)findViewById(R.id.aboutPointTxt);
        aboutaUs.setText(Html.fromHtml(getResources().getString(R.string.about_us)));

    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.notificationsBellLayout){
            startActivity(new Intent(this,NotificationsActivity.class));
        }
    }
    public void responseCount(String response){
        int k= Integer.parseInt(response);
        if(k>0) {
            countNotifications.setVisibility(View.VISIBLE);
            countNotifications.setText(response);
        }
        else{
            countNotifications.setVisibility(View.GONE);
        }
    }
}
