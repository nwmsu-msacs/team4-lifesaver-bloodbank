package com.example.myapplication.asynctask;

import android.content.Context;
import android.os.AsyncTask;

import com.example.myapplication.interfaces.ServiceResponseHandler;
import com.example.myapplication.util.ApplicationHolder;
import com.example.myapplication.util.JSONParser;


public class UserProfileAsyncTask extends AsyncTask {

    Context context;
    String response="";
    String responseStatus="";
    String errorMsg="Could not connect to the server";
    private ServiceResponseHandler handler;


    public UserProfileAsyncTask(Context context){
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

        response= JSONParser.makeHttpRequest(context, ApplicationHolder.base_url+ApplicationHolder.showProfile_url+params[0], "GET", null);




        return null;
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);





            if(!response.equals("")){




                handler.onSuccess(response);

            }
            else
                handler.onFailure("");
        }


}






