package com.example.myapplication.asynctask;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;


import com.example.myapplication.R;
import com.example.myapplication.activities.DonorsActivity;
import com.example.myapplication.model.ShowDonors;
import com.example.myapplication.util.ApplicationHolder;
import com.example.myapplication.util.JSONParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ShowAllDonorsAsycTask extends AsyncTask {

    Context context;
    String response="";
    ProgressDialog progressDialog;
    String responseStatus="";
    public List<ShowDonors> list;
    String errorMsg="Could not connect to the server";
    boolean message=false;
    public ShowAllDonorsAsycTask(Context context){
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




        response= JSONParser.makeHttpRequest(context, ApplicationHolder.base_url+ApplicationHolder.show_all_donors_url, "GET", null);
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

                    ShowDonors showDonors=new ShowDonors();
                    //responseStatus = jsonObject.getString("status");
                    showDonors.setUserId(jsonObject.getString("userId"));
                    showDonors.setUserfirstName(jsonObject.getString("userfirstName"));
                    showDonors.setUserlastName(jsonObject.getString("userlastName"));
                    showDonors.setUserfullName(jsonObject.getString("userfullName"));
                    showDonors.setUserEmail(jsonObject.getString("userEmail"));
                    showDonors.setUserPassword(jsonObject.getString("userPassword"));
                    showDonors.setUserImagepath(jsonObject.getString("userImagepath"));
                    showDonors.setUserActivationNumber(jsonObject.getString("UserActivationNumber"));
                    showDonors.setUserCreatedOn(jsonObject.getString("userCreatedOn"));
                    showDonors.setUserUpdatedOn(jsonObject.getString("userUpdatedOn"));
                    showDonors.setUserStatus(jsonObject.getString("userStatus"));
                    showDonors.setUserMasterId(jsonObject.getString("userMasterId"));
                    showDonors.setUserDOB(jsonObject.getString("userDOB"));
                    showDonors.setUserGender(jsonObject.getString("userGender"));
                    showDonors.setUserBloodGroup(jsonObject.getString("userBloodGroup"));
                    showDonors.setUserHowoften(jsonObject.getString("userHowoften"));
                    showDonors.setUserWeight(jsonObject.getString("userWeight"));
                    showDonors.setUserLastDonation(jsonObject.getString("userLastDonation"));
                    showDonors.setUserResPhone(jsonObject.getString("userResPhone"));
                    showDonors.setUserMobile(jsonObject.getString("userMobile"));
                    showDonors.setUserAddress(jsonObject.getString("userAddress"));
                    showDonors.setUserState(jsonObject.getString("userState"));
                    showDonors.setUserDistrict(jsonObject.getString("userDistrict"));
                    showDonors.setUserCity(jsonObject.getString("userCity"));
                    showDonors.setUserinfoCreatedOn(jsonObject.getString("userinfoCreatedOn"));
                    showDonors.setUserinfoUpdatedOn(jsonObject.getString("userinfoUpdatedOn"));
                    showDonors.setUserinfoStatus(jsonObject.getString("userinfoStatus"));

                

                    list.add(showDonors);


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
            ((DonorsActivity)context).responseDonors(list);
        }

    }
}






