package com.example.myapplication.asynctask;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;


import com.example.myapplication.R;
import com.example.myapplication.activities.ShowAllRequestsActivity;
import com.example.myapplication.model.ShowAllRequestsModel;
import com.example.myapplication.util.ApplicationHolder;
import com.example.myapplication.util.JSONParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ShowAllRequestsAsyncTask extends AsyncTask {

    Context context;
    String response="";
    ProgressDialog progressDialog;
    String responseStatus="";
    public List<ShowAllRequestsModel> list;
    String errorMsg="Could not connect to the server";
    boolean message=false;
    public ShowAllRequestsAsyncTask(Context context){
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




        response= JSONParser.makeHttpRequest(context, ApplicationHolder.base_url+ApplicationHolder.show_allRequests_url, "GET", null);
        if(response!=null){
            try {
                message = true;
                JSONArray responseJSONArray=new JSONArray(response);
                JSONArray innerArray=responseJSONArray.getJSONArray(0);

                //JSONArray responseJSONArray1=new JSONArray(responseJSONArray);
                Log.v("anand>>",">>>>>sssssss"+response);
                for(int i=0;i<innerArray.length();i++) {
                    HashMap<String, String> map = new HashMap<String, String>();
                    JSONObject jsonObject = innerArray.getJSONObject(i);

                    ShowAllRequestsModel showAllRequestsModel=new ShowAllRequestsModel();
                    //responseStatus = jsonObject.getString("status");
                    showAllRequestsModel.setRequestId(jsonObject.getString("requestId"));
                    showAllRequestsModel.setpName(jsonObject.getString("patientName"));//patientName
                    showAllRequestsModel.setpBloodGroup(jsonObject.getString("patientBloodgroup"));
                    showAllRequestsModel.setpAge(jsonObject.getString("patientAge"));
                    showAllRequestsModel.setpBloodOn(jsonObject.getString("bloodneedOn"));
                    showAllRequestsModel.setpNoOfUnits(jsonObject.getString("noofUnits"));
                    showAllRequestsModel.setMobileNo(jsonObject.getString("mobileNumber"));
                    showAllRequestsModel.setLocalNo(jsonObject.getString("localNumber"));
                    showAllRequestsModel.setHospitalName(jsonObject.getString("hospitalName"));
                    showAllRequestsModel.setHospitalAddress(jsonObject.getString("hospitalAddress"));
                    showAllRequestsModel.setPurpose(jsonObject.getString("purpose"));
                    showAllRequestsModel.setContactPerson(jsonObject.getString("contactPersonName"));
                    showAllRequestsModel.setContactPersonEmail(jsonObject.getString("contactPersonEmail"));
                    showAllRequestsModel.setPatientAddress(jsonObject.getString("patientAddress"));
                    showAllRequestsModel.setRequestCreateOn(jsonObject.getString("requestCreateOn"));
                    showAllRequestsModel.setRequestActionStatus(jsonObject.getString("requestActionStatus"));
                    showAllRequestsModel.setRequestAcceptedBy(jsonObject.getString("requestAcceptedBy"));
                    showAllRequestsModel.setRequestUpdatedOn(jsonObject.getString("requestUpdatedOn"));
                    showAllRequestsModel.setRequestStatus(jsonObject.getString("requestStatus"));

                   // Log.v("anand>>", ">>>>>" + map);

                    list.add(showAllRequestsModel);


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
            ShowAllRequestsActivity.responseRequests(list);
        }

    }
}





