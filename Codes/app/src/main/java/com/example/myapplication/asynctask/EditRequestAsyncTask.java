package com.example.myapplication.asynctask;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;


import com.example.myapplication.R;
import com.example.myapplication.util.ApplicationHolder;
import com.example.myapplication.util.JSONParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

public class EditRequestAsyncTask extends AsyncTask {

    Context context;
    String response="";
    ProgressDialog progressDialog;
    String responseStatus="";
    public List<HashMap<String, String>> listJobs;
    String errorMsg="Could not connect to the server";
    boolean message=false;
    public EditRequestAsyncTask(Context context){
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


        /*List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
        nameValuePairs.add(new BasicNameValuePair("email",""));
        nameValuePairs.add(new BasicNameValuePair("password",  ""));
        response= JSONParser.makeHttpRequest(context, Constants.base_url+Constants.login_url, "POST", nameValuePairs);*/

        response= JSONParser.makeHttpRequest(context, ApplicationHolder.base_url+ApplicationHolder.editRequest_url+params[0], "GET", null);
        if(response!=null){
            try {
                message = true;
                // JSONArray responseJSONArray=new JSONArray(response);
                JSONObject jsonObject = new JSONObject(response);
                responseStatus=jsonObject.getString("status");

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }else{
            errorMsg="Could not connect to the server";
        }

        return null;
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);

        if(progressDialog!=null){
            progressDialog.dismiss();
            progressDialog.cancel();
            if(responseStatus.equalsIgnoreCase("success")){

            }
            else
                Toast.makeText(context,"Invalid User Name or Password", Toast.LENGTH_SHORT).show();
        }

    }
}






