package com.example.myapplication.activities;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.asynctask.NotificationCountAsycTask;
import com.example.myapplication.model.ShowDonors;
import com.example.myapplication.util.Utill;



public class DonorDetailsActivity extends Activity implements View.OnClickListener{
    TextView name,bGroup,mobileNo,emailId,gender,location;
    ShowDonors showDonors;
    RelativeLayout notificationsBellLayout;
    TextView countNotifications;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_donor_details);

        notificationsBellLayout=(RelativeLayout) findViewById(R.id.notificationsBellLayout);
        notificationsBellLayout.setOnClickListener(this);
        countNotifications=(TextView)findViewById(R.id.creditsId);
        new NotificationCountAsycTask(this).execute();

        

    }


