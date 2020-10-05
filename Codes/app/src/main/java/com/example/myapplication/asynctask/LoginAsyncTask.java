package com.example.myapplication.asynctask;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;


import com.example.myapplication.interfaces.ServiceResponseHandler;
import com.example.myapplication.util.ApplicationHolder;
import com.example.myapplication.util.JSONParser;
import com.example.myapplication.util.SharedPreference;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class LoginAsyncTask extends AsyncTask {
    Context context;
    String response="";
    SharedPreference sharedPreference;
    private ServiceResponseHandler handler;

    public LoginAsyncTask(Context context){
        this.context=context;
        handler= (ServiceResponseHandler) context;
        sharedPreference=new SharedPreference(context);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

    }

    @Override
    protected Object doInBackground(Object[] params) {
        response= JSONParser.makeHttpRequest(context, ApplicationHolder.base_url+ApplicationHolder.login_url+params[0], "GET", null);

        if(response!=null) {
            Log.v("anand<<<<", response);
            try {
                //message = true;
                JSONArray responseJSONArray = new JSONArray(response);
                JSONArray responseJSon = responseJSONArray.getJSONArray(0);
                JSONObject jsonObject = responseJSon.getJSONObject(0);
                sharedPreference.setPrefValue("userMail", jsonObject.getString("userEmail"));
                
                response = "login";

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);

        handler.onSuccess(response);



    }
}





