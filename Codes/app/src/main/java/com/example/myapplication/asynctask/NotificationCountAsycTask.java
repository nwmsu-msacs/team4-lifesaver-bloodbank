package com.example.myapplication.asynctask;

import android.content.Context;
import android.os.AsyncTask;


import com.example.myapplication.activities.AboutUsActivity;
import com.example.myapplication.activities.BloodTipsActivity;
import com.example.myapplication.activities.ContactUsActivity;
import com.example.myapplication.activities.CreaterequestActivity;
import com.example.myapplication.activities.DonorDetailsActivity;
import com.example.myapplication.activities.DonorsActivity;
import com.example.myapplication.activities.FaqsActivity;
import com.example.myapplication.activities.MainActivity;
import com.example.myapplication.activities.MyRequestsActivity;
import com.example.myapplication.activities.ShowAllRequestsActivity;
import com.example.myapplication.activities.UserProfile;
import com.example.myapplication.util.ApplicationHolder;
import com.example.myapplication.util.JSONParser;

import java.util.HashMap;
import java.util.List;


public class NotificationCountAsycTask extends AsyncTask {

    Context context;
    String response="";
    String responseStatus="";
    public List<HashMap<String, String>> listJobs;
    String errorMsg="Could not connect to the server";
    boolean message=false;
    public NotificationCountAsycTask(Context context){
        this.context=context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();



    }

    @Override
    protected Object doInBackground(Object[] params) {


        /*List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
        nameValuePairs.add(new BasicNameValuePair("email",""));
        nameValuePairs.add(new BasicNameValuePair("password",  ""));
        response= JSONParser.makeHttpRequest(context, Constants.base_url+Constants.login_url, "POST", nameValuePairs);*/

//        response= JSONParser.makeHttpRequest(context, ApplicationHolder.base_url+ApplicationHolder.notification_count_url, "GET", null);
        response= "0";
        return null;
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
        if(context.getClass().getSimpleName().equals("MainActivity"))
        ((MainActivity)context).responseCount(response);
        else if(context.getClass().getSimpleName().equals("AboutUsActivity"))
            ((AboutUsActivity)context).responseCount(response);
        else if(context.getClass().getSimpleName().equals("UserProfile"))
            ((UserProfile)context).responseCount(response);
        else if(context.getClass().getSimpleName().equals("ContactUsActivity"))
            ((ContactUsActivity)context).responseCount(response);
        else if(context.getClass().getSimpleName().equals("BloodTipsActivity"))
            ((BloodTipsActivity)context).responseCount(response);
        else if(context.getClass().getSimpleName().equals("FaqsActivity"))
            ((FaqsActivity)context).responseCount(response);
        else if(context.getClass().getSimpleName().equals("ShowAllRequestsActivity"))
            ((ShowAllRequestsActivity)context).responseCount(response);
        else if(context.getClass().getSimpleName().equals("DonorDetailsActivity"))
            ((DonorDetailsActivity)context).responseCount(response);
        else if(context.getClass().getSimpleName().equals("DonorsActivity"))
            ((DonorsActivity)context).responseCount(response);
        else if(context.getClass().getSimpleName().equals("MyRequestsActivity"))
            ((MyRequestsActivity)context).responseCount(response);
        else if(context.getClass().getSimpleName().equals("CreaterequestActivity"))
            ((CreaterequestActivity)context).responseCount(response);

    }
}

