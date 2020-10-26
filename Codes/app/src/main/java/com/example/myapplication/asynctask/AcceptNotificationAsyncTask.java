package com.example.myapplication.asynctask;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.util.ApplicationHolder;
import com.example.myapplication.util.JSONParser;



public class AcceptNotificationAsyncTask extends AsyncTask {
    Context context;
    String response;
    ProgressDialog progressDialog;
    public AcceptNotificationAsyncTask(Context context){
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
        response= JSONParser.makeHttpRequest(context, ApplicationHolder.base_url+ApplicationHolder.accept_url+params[0], "GET", null);

        return null;
    }
    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);

        if(progressDialog!=null){
            progressDialog.dismiss();
            progressDialog.cancel();
                Toast.makeText(context,response, Toast.LENGTH_SHORT).show();
        }

    }
}
