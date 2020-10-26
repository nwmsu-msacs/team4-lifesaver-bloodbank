package com.example.myapplication.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import com.example.myapplication.model.ShowAllRequestsModel;
import com.example.myapplication.model.ShowDonors;



public class Utill {

    public static String LOG="Tag>>>>>>>>>>>>>>>>>>>";

    public static boolean isNetworkAvailable(Context ctx) {
        ConnectivityManager mConnectivityManager = (ConnectivityManager) ctx
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (mConnectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED
                || mConnectivityManager.getNetworkInfo(
                ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            return true;
        } else
            return false;
    }

    public static void showToast(Context context, String message){
        Toast.makeText(context,message, Toast.LENGTH_SHORT).show();
    }




    public static ShowAllRequestsModel showAllRequestsModel;

    public static ShowAllRequestsModel getShowAllRequestsModel() {
        return showAllRequestsModel;
    }

    public static ShowDonors showDonors;
    public static ShowDonors getDonorsModel() {
        return showDonors;
    }



}
