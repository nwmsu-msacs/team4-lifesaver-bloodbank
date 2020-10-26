package com.example.myapplication.asynctask;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;


import com.example.myapplication.interfaces.ServiceResponseHandler;
import com.example.myapplication.util.ApplicationHolder;
import com.example.myapplication.util.JSONParser;

import java.util.HashMap;
import java.util.List;


public class AddRequestAsyncTask extends AsyncTask {

    Context context;
    String response="";
    String responseStatus="";
    public List<HashMap<String, String>> listJobs;
    String errorMsg="Could not connect to the server";
    boolean message=false;
    private ServiceResponseHandler handler;
    public AddRequestAsyncTask(Context context){
        this.context=context;
        handler= (ServiceResponseHandler) context;
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

        response= JSONParser.makeHttpRequest(context, ApplicationHolder.base_url+ ApplicationHolder.addRequest_url+params[0], "GET", null);
        if(response!=null){
            Log.v("anand<<<<",response);
           /* try {
                message = true;
                // JSONArray responseJSONArray=new JSONArray(response);
                JSONObject jsonObject = new JSONObject(response);
                responseStatus=jsonObject.getString("status");

            } catch (JSONException e) {
                e.printStackTrace();
            }*/
        }else{
            errorMsg="Could not connect to the server";
        }
        return null;
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);

        handler.onSuccess(response);

    }
}





