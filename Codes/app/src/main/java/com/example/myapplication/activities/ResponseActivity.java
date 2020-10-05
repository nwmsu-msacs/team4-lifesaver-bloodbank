package com.example.myapplication.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.adapter.ResponseAdapter;
import com.example.myapplication.adapter.ShowMyRequestsAdapter;
import com.example.myapplication.asynctask.NotificationCountAsycTask;
import com.example.myapplication.asynctask.ShowMyRequestsAsyncTask;
import com.example.myapplication.interfaces.ServiceResponseHandler;
import com.example.myapplication.model.CreateRequest;
import com.example.myapplication.model.ShowAllRequestsModel;
import com.example.myapplication.util.SharedPreference;
import com.example.myapplication.util.Utill;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ResponseActivity extends Activity implements ServiceResponseHandler, View.OnClickListener {

    RelativeLayout notificationsBellLayout;
    TextView countNotifications,myRequestsTitle;
    SharedPreference sharedPreferences;
    public static View.OnClickListener myOnClickListener;
    private static RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private ProgressDialog progressDialog;
    private List<ShowAllRequestsModel> list;
    private static RecyclerView.Adapter adapter;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_my_requests);

        myRequestsTitle=(TextView)findViewById(R.id.myRequestsTitle);
        myRequestsTitle.setText("Response");
        myOnClickListener = new MyOnClickListener(this);

        notificationsBellLayout=(RelativeLayout) findViewById(R.id.notificationsBellLayout);
        notificationsBellLayout.setOnClickListener(this);
        countNotifications=(TextView)findViewById(R.id.creditsId);
        new NotificationCountAsycTask(this).execute();

        ((ImageView)findViewById(R.id.backImage)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        sharedPreferences=new SharedPreference(this);

        if (Utill.isNetworkAvailable(this)) {
            progressDialog=new ProgressDialog(this);
            progressDialog.setMessage(this.getResources().getString(R.string.please_wait));
            progressDialog.setCancelable(false);
            progressDialog.show();

            final String emailId=new SharedPreference(ResponseActivity.this).getPrefValue(getString(R.string.email_id));
            final String mobile=new SharedPreference(ResponseActivity.this).getPrefValue(getString(R.string.mobile_no));
            Query query1= FirebaseDatabase.getInstance().getReference("Responses").orderByChild("emailClient").equalTo(emailId);
            list=new ArrayList<>();
            query1.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                    if(snapshot.exists()){


                        ShowAllRequestsModel showAllRequestsModel=new ShowAllRequestsModel();
                        showAllRequestsModel.setRequestId(snapshot.child("id").getValue(String.class));
                        showAllRequestsModel.setpName(snapshot.child("patientName").getValue(String.class));
                        showAllRequestsModel.setpBloodGroup(snapshot.child("group").getValue(String.class));
                        showAllRequestsModel.setpBloodOn(snapshot.child("Date").getValue(String.class));
                        showAllRequestsModel.setpNoOfUnits(snapshot.child("units").getValue(String.class));
                        showAllRequestsModel.setMobileNo(snapshot.child("mobile").getValue(String.class));
                        showAllRequestsModel.setPatientAddress(snapshot.child("city").getValue(String.class));
                        progressDialog.dismiss();
                        list.add(showAllRequestsModel);
                        adapter = new ResponseAdapter((ArrayList<ShowAllRequestsModel>) list);
                        recyclerView.setAdapter(adapter);
                        progressDialog.dismiss();
                    }else{
                        Utill.showToast(ResponseActivity.this,"No Data found");
                    }
                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                }

                @Override
                public void onChildRemoved(@NonNull DataSnapshot snapshot) {

                }

                @Override
                public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    progressDialog.dismiss();
                }
            });
        } else {
            Toast.makeText(this, getResources().getString(R.string.noInternet), Toast.LENGTH_SHORT).show();
            progressDialog.dismiss();
        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.notificationsBellLayout: startActivity(new Intent(this,NotificationsActivity.class));
                break;
        }


    }

     @Override
    public void onSuccess(String response) {


        list=new ArrayList<>();

        if (response != null) {

            try {
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
                    showAllRequestsModel.setpName(jsonObject.getString("patientName"));
                    showAllRequestsModel.setpBloodGroup(jsonObject.getString("patientBloodgroup"));
                    showAllRequestsModel.setpBloodOn(jsonObject.getString("bloodneedOn"));
                    showAllRequestsModel.setpNoOfUnits(jsonObject.getString("noofUnits"));
                    showAllRequestsModel.setMobileNo(jsonObject.getString("mobileNumber"));
                    showAllRequestsModel.setPatientAddress(jsonObject.getString("hospitalAddress"));
            

                    list.add(showAllRequestsModel);
                    adapter = new ResponseAdapter((ArrayList<ShowAllRequestsModel>) list);
                    recyclerView.setAdapter(adapter);

                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

        } else {
            Toast.makeText(this, "Could not get the details", Toast.LENGTH_SHORT).show();

        }

        if (progressDialog != null) {
            progressDialog.dismiss();
            progressDialog.cancel();
        }

    }

    @Override
    public void onFailure(String failResponse) {

    }

 private static class MyOnClickListener implements View.OnClickListener {

        private final Context context;

        private MyOnClickListener(Context context) {
            this.context = context;
        }

        @Override
        public void onClick(View v) {
            removeItem(v);
        }

        private void removeItem(View v) {
            int selectedItemPosition = recyclerView.getChildPosition(v);
            RecyclerView.ViewHolder viewHolder = recyclerView.findViewHolderForPosition(selectedItemPosition);

        }
    }
     public void responseCount(String response){
        int k= Integer.parseInt(response);
        if(k>0) {
            countNotifications.setVisibility(View.VISIBLE);
            countNotifications.setText(response);
        }
        else{
            countNotifications.setVisibility(View.GONE);
        }
    }
}

   