package com.example.myapplication.asynctask;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;


import com.example.myapplication.R;
import com.example.myapplication.util.ApplicationHolder;
import com.example.myapplication.util.JSONParser;

import java.util.HashMap;
import java.util.List;

public class UpdateProfileAsyncTask extends AsyncTask {

    Context context;
    String response="";
    ProgressDialog progressDialog;
    String responseStatus="";
    public List<HashMap<String, String>> listJobs;
    String errorMsg="Could not connect to the server";
    boolean message=false;
    public UpdateProfileAsyncTask(Context context){
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

        response= JSONParser.makeHttpRequest(context, ApplicationHolder.base_url+ApplicationHolder.updateProfile_url+params[0], "GET", null);
       /* if(response!=null){
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
        }*/

        return null;
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);

        if(progressDialog!=null){
            progressDialog.dismiss();
            progressDialog.cancel();
            /*if(responseStatus.equalsIgnoreCase("Updated Successfully")){

            }
            else*/
                Toast.makeText(context,response, Toast.LENGTH_SHORT).show();
        }

    }
}





