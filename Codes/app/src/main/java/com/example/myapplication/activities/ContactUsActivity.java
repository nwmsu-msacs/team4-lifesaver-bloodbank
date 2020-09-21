package com.example.myapplication.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.asynctask.ContactUsAsyncTask;
import com.example.myapplication.asynctask.NotificationCountAsycTask;
import com.example.myapplication.util.Utill;

public class ContactUsActivity extends SuperClasses implements View.OnClickListener{
    RelativeLayout notificationsBellLayout;
    TextView countNotifications;
    EditText nameEdit,numberEdit,emailEdit,subjectEdit,messsageEdit;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    String emailPatternCo = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+\\.+[a-z]+";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_contact_us);
        loadGenericValues(this, getResources().getString(R.string.menu_contactus));
        notificationsBellLayout=(RelativeLayout) findViewById(R.id.notificationsBellLayout);
        notificationsBellLayout.setOnClickListener(this);
        countNotifications=(TextView)findViewById(R.id.creditsId);
        new NotificationCountAsycTask(this).execute();
        nameEdit=(EditText)findViewById(R.id.nameEdit);
        numberEdit=(EditText)findViewById(R.id.mobileEdit);
        emailEdit=(EditText)findViewById(R.id.emailEdit);
        subjectEdit=(EditText)findViewById(R.id.subjectEdit);
        messsageEdit=(EditText)findViewById(R.id.messageEdit);
        ((Button)findViewById(R.id.sendButton)).setOnClickListener(this);
       // contactUs.setText(Html.fromHtml(getResources().getString(R.string.about_us)));
       // ((TextView)findViewById(R.id.contactPointTxt)).setText(Html.fromHtml(getResources().getString(R.string.contact_point)));
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.notificationsBellLayout){
            startActivity(new Intent(this,NotificationsActivity.class));
        }
        if(v.getId()==R.id.sendButton){
            validationSend();
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
    public void validationSend(){
        String name=nameEdit.getText().toString();
        String mobile=nameEdit.getText().toString();
        String email=nameEdit.getText().toString();
        String subject=nameEdit.getText().toString();
        String message=nameEdit.getText().toString();

        if(name.equals(""))
            Utill.showToast(this,"Please enter name");
        else if(mobile.equals(""))
            Utill.showToast(this,"Please enter mobile number");
        else if(email.equals(""))
            Utill.showToast(this,"Please enter email id");
        else if(email.matches(emailPattern)&&email.length()>0||email.matches(emailPatternCo)&&email.length()>0)
            Utill.showToast(this,"Please enter valid email id");
        else if(subject.equals(""))
            Utill.showToast(this,"Please enter subject");
        else if(message.equals(""))
            Utill.showToast(this,"Please enter message");

        else{
            if(!Utill.isNetworkAvailable(this)){
               Utill.showToast(this,"Please Connect internet");
            }
            else{
               new ContactUsAsyncTask(this).execute("username="+name+"&email="+email+"&subject="+subject+"&phone="+mobile+"&message="+message);
            }
        }
    }
    public void responseContactUs(String response){
        if(response.equals("Information Send Successfully")){
            nameEdit.setText("");
            numberEdit.setText("");
            emailEdit.setText("");
            subjectEdit.setText("");
            messsageEdit.setText("");
        }
        Utill.showToast(this,response);
    }
}

