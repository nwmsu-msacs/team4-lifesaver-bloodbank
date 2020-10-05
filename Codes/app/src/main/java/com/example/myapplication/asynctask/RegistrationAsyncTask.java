package com.example.myapplication.asynctask;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;


import com.example.myapplication.R;
import com.example.myapplication.activities.RegistrationActivity;
import com.example.myapplication.util.ApplicationHolder;
import com.example.myapplication.util.JSONParser;

import java.util.HashMap;
import java.util.List;


public class RegistrationAsyncTask extends AsyncTask {

    Context context;
    String response="";
    ProgressDialog progressDialog;
    String responseStatus="";
    public List<HashMap<String, String>> listJobs;
    String errorMsg="Could not connect to the server";
    boolean message=false;
    public RegistrationAsyncTask(Context context){
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


  

        response= JSONParser.makeHttpRequest(context, ApplicationHolder.base_url+ApplicationHolder.registration_url+params[0], "GET", null);
    

        return null;
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);

        if(progressDialog!=null){
            progressDialog.dismiss();
            progressDialog.cancel();
            ((RegistrationActivity)context).responseRegister(response);
        }

    }
}





