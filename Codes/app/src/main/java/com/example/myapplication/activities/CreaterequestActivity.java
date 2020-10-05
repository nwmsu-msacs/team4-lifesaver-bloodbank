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
    private void getStringsFromViews(){
        parentNameStr=parentName.getText().toString().trim();
        bGroupStr=bGroup.getSelectedItem().toString().trim();
        needOnStr=needOn.getText().toString().trim();
        unitsStr=units.getText().toString().trim();
        mNumberStr=mNumber.getText().toString().trim();
        locationStr=location.getText().toString().trim();
               if(parentNameStr.equals("")){
            Utill.showToast(this,"Please enter patient name");
        }
        else if(bGroupStr.equals("Select Blood Group")){
            Utill.showToast(this,"Please enter blood group");
        }
        else if(needOnStr.equals("")){
            Utill.showToast(this,"Please enter patient name");
        }
        else if(unitsStr.equals("")){
            Utill.showToast(this,"Please enter units of blood needed");
        }
        else if(mNumberStr.equals("")){
            Utill.showToast(this,"Please enter mobile no");
        }
        else if(locationStr.equals("")){
            Utill.showToast(this,"Please enter patient location");
        }
        else{

            progressDialog=new ProgressDialog(CreaterequestActivity.this);
                   progressDialog.setMessage(getResources().getString(R.string.please_wait));
                   progressDialog.setCancelable(false);
                   progressDialog.show();

            if(flag){
                ShowAllRequestsModel showAllRequestsModel=Utill.getShowAllRequestsModel();
                //Toast.makeText(CreaterequestActivity.this, getResources().getString(R.string.progress), Toast.LENGTH_SHORT).show();

                String placer="pname="+parentNameStr+"&Bgroup="+bGroupStr+"&needon="+needOnStr+"&units="+unitsStr+"&mNumber="+mNumberStr+"&location="+locationStr+"&requestId="+showAllRequestsModel.getRequestId();
                new EditRequestAsyncTask(this).execute(placer.replace(" ","%20"));
                //http://websitesdev.in/jjyothi/index.php/Bloodrequest/editrequestformobile?pname=p&Bgroup=B+&needon=2016-10-31&units=2&mNumber=1234567890&location=kompally&requestId=13
//                new AddRequestAsyncTask(CreaterequestActivity.this).execute("pname="+parentNameStr+"&Bgroup="+bGroupStr+"&needon="+needOnStr+"&units="+unitsStr+"&mNumber="+mNumberStr+"&location="+locationStr+"&requestId="+);

            }else{
            /*    String placer="addedby="+sharedPreference.getPrefValue("userMail")+"&pname="
                        +parentNameStr+"&Bgroup="+bGroupStr+"&needon="+needOnStr
                        +"&units="+unitsStr+"&mNumber="+mNumberStr+"&location="+locationStr;
*/

                //new AddRequestAsyncTask(CreaterequestActivity.this).execute(placer.replace(" ","%20"));

                final String emailId=new SharedPreference(CreaterequestActivity.this).getPrefValue(getString(R.string.email_id));
                final String mobile=new SharedPreference(CreaterequestActivity.this).getPrefValue(getString(R.string.mobile_no));
                DatabaseReference quizRef = FirebaseDatabase.getInstance().getReference("Requests");

                String key=quizRef.push().getKey();
                assert key != null;
                quizRef.child(key).setValue(new CreateRequest(needOnStr,locationStr,emailId,bGroupStr,key,mobile,parentNameStr,unitsStr,""));


                progressDialog.dismiss();
                responseRequest();

                //addedby=suresh.pegadapelli@gmail.com&pname=pegadapeli&Bgroup=O+&needon=2016-12-31&units=2&mNumber=1234567890&location=Ameerpet
                //http://websitesdev.in/jjyothi/index.php/Bloodrequest/addRequest?pname=pegadapeli&Bgroup=B+&needon=2016-10-31&units=2&mNumber=1234567890&location=kompally
            }
        }
    }
    public void date() {
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dpd = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                           needOn.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                    }
                }, mYear, mMonth, mDay);
        dpd.show();
        dpd.setIcon(R.drawable.calander);
    }
