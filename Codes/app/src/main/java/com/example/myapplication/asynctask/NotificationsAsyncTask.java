package com.example.myapplication.asynctask;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;


import com.example.myapplication.R;
import com.example.myapplication.activities.NotificationsActivity;
import com.example.myapplication.model.NotificationModel;
import com.example.myapplication.util.ApplicationHolder;
import com.example.myapplication.util.JSONParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Anand on 25/09/2016.
 */
public class NotificationsAsyncTask extends AsyncTask {

    Context context;
    String response="";
    ProgressDialog progressDialog;
    String responseStatus="";
    public List<NotificationModel> list;
    String errorMsg="Could not connect to the server";
    boolean message=false;
    public NotificationsAsyncTask(Context context){
        this.context=context;
        list=new ArrayList<>();
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




        response= JSONParser.makeHttpRequest(context, ApplicationHolder.base_url + ApplicationHolder.notification_url, "GET", null);
        if(response!=null){
            try {
                message = true;
                JSONArray responseJSONArray=new JSONArray(response);
                JSONArray innerArray=responseJSONArray.getJSONArray(0);

                //JSONArray responseJSONArray1=new JSONArray(responseJSONArray);
                Log.v("anand>>", ">>>>>sssssss" + response);
                for(int i=0;i<innerArray.length();i++) {
                    HashMap<String, String> map = new HashMap<String, String>();
                    JSONObject jsonObject = innerArray.getJSONObject(i);

                    NotificationModel notificationModel=new NotificationModel();
                    notificationModel.setNotificationId(jsonObject.getString("notificationId"));
                    notificationModel.setNotificationTitle(jsonObject.getString("notificationTitle"));
                    notificationModel.setNotificationDesc(jsonObject.getString("notificationDesc"));
                    notificationModel.setNotificationFrom(jsonObject.getString("notificationFrom"));
                    notificationModel.setNotificationTo(jsonObject.getString("notificationTo"));
                    notificationModel.setNotificationContactPerson(jsonObject.getString("notificationContactPerson"));
                    notificationModel.setNotificationContactNumber(jsonObject.getString("notificationContactNumber"));
                    notificationModel.setNotificationDate(jsonObject.getString("notificationDate"));
                    notificationModel.setNotificationAddress(jsonObject.getString("notificationAddress"));
                    notificationModel.setNotificationCreatedOn(jsonObject.getString("notificationCreatedOn"));
                    notificationModel.setNotificationUpdatedOn(jsonObject.getString("notificationUpdatedOn"));
                    notificationModel.setNotificationStatus(jsonObject.getString("notificationStatus"));

                    list.add(notificationModel);
                }

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

        if(progressDialog!=null) {
            progressDialog.dismiss();
            progressDialog.cancel();
            NotificationsActivity.responseRequests(list);
        }

    }
}





