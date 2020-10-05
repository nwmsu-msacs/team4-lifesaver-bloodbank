package com.example.myapplication.asynctask;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;


import com.example.myapplication.R;
import com.example.myapplication.activities.ContactUsActivity;
import com.example.myapplication.util.ApplicationHolder;
import com.example.myapplication.util.JSONParser;

import java.util.HashMap;
import java.util.List;


public class ContactUsAsyncTask extends AsyncTask {

    Context context;
    String response="";
    String responseStatus="";
    ProgressDialog progressDialog;
    public List<HashMap<String, String>> listJobs;
    String errorMsg="Could not connect to the server";
    boolean message=false;
    public ContactUsAsyncTask(Context context){
        this.context=context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog=new ProgressDialog(context);
        progressDialog.setMessage(context.getResources().getString(R.string.please_wait));
        progressDialog.setCancelable(false);
        progressDialog.show();


    }

    @Override
    protected Object doInBackground(Object[] params) {

        response= JSONParser.makeHttpRequest(context, ApplicationHolder.base_url+ApplicationHolder.contact_us_url+params[0], "GET", null);
        return null;
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
        if(progressDialog!=null){
            progressDialog.dismiss();
            progressDialog.cancel();
            ((ContactUsActivity)context).responseContactUs(response);
        }
    }
}

