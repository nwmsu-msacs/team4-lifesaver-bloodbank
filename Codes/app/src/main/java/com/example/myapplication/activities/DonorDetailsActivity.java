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
name=(TextView)findViewById(R.id.nameTxt);
        bGroup=(TextView)findViewById(R.id.groupTxt);
        mobileNo=(TextView)findViewById(R.id.phoneTxt);
        emailId=(TextView)findViewById(R.id.emailTxt);
        gender=(TextView)findViewById(R.id.genderTxt);
        location=(TextView)findViewById(R.id.locationTxt);
((ImageView)findViewById(R.id.backImage)).setOnClickListener(this);
        showDonors= Utill.getDonorsModel();

        name.setText(showDonors.getUserfullName());
        bGroup.setText(showDonors.getUserBloodGroup());
        mobileNo.setText(showDonors.getUserMobile());
        emailId.setText(showDonors.getUserEmail());
        gender.setText(showDonors.getUserGender());
        location.setText(showDonors.getUserCity());

        ((Button)findViewById(R.id.callToDonor)).setOnClickListener(this);

        

    }


