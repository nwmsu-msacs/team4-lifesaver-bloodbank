package com.example.myapplication.activities;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.Nullable;

import com.example.myapplication.R;
import com.example.myapplication.asynctask.AddRequestAsyncTask;
import com.example.myapplication.asynctask.EditRequestAsyncTask;
import com.example.myapplication.asynctask.NotificationCountAsycTask;
import com.example.myapplication.interfaces.ServiceResponseHandler;
import com.example.myapplication.model.CreateRequest;
import com.example.myapplication.model.ShowAllRequestsModel;
import com.example.myapplication.util.SharedPreference;
import com.example.myapplication.util.Utill;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.Calendar;


public class CreaterequestActivity extends Activity implements View.OnClickListener, ServiceResponseHandler {
    private EditText parentName,units,mNumber,location;
    Spinner bGroup;
    private String parentNameStr,bGroupStr,needOnStr,unitsStr,mNumberStr,locationStr;
    private ProgressDialog progressDialog;
    private boolean flag=false;
    RelativeLayout notificationsBellLayout;
    TextView countNotifications,needOn;
    private int mYear, mMonth, mDay, mHour, mMinute;
    SharedPreference sharedPreference;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_create_request);
        sharedPreference=new SharedPreference(this);
        Bundle bundle=getIntent().getExtras();
        if(bundle!=null){
            String sttus=bundle.getString("myrequest");
            if(sttus.equalsIgnoreCase("edit")){
                ((TextView)findViewById(R.id.headTV)).setText("Edit Request");
                flag=true;
            }
        }
        reqisterView();

        /*String key = database.getReference("quiz").push().getKey();*/
    }

    private void reqisterView(){
        ((ImageView)findViewById(R.id.backImage)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                onBackPressed();
            }
        });
        notificationsBellLayout=(RelativeLayout) findViewById(R.id.notificationsBellLayout);
        notificationsBellLayout.setOnClickListener(this);
        countNotifications=(TextView)findViewById(R.id.creditsId);
        new NotificationCountAsycTask(this).execute();


        parentName=(EditText) findViewById(R.id.parentName);
        bGroup=(Spinner) findViewById(R.id.bGroup);
        needOn=(TextView)findViewById(R.id.needOn);
        units=(EditText)findViewById(R.id.units);
        mNumber=(EditText)findViewById(R.id.mNumber);
        location=(EditText)findViewById(R.id.location);
        needOn.setOnClickListener(this);
        if(flag){
            ShowAllRequestsModel showAllRequestsModel= Utill.getShowAllRequestsModel();
            parentName.setText(showAllRequestsModel.getpName());
            bGroup.setSelection(0);
            needOn.setText(showAllRequestsModel.getpBloodOn());
            units.setText(showAllRequestsModel.getpNoOfUnits());
            mNumber.setText(showAllRequestsModel.getMobileNo());
            location.setText(showAllRequestsModel.getPatientAddress());

            ((Button)findViewById(R.id.request)).setText("Update Request");
        }
        ((Button)findViewById(R.id.request)).setOnClickListener(this);


    }
