package com.example.myapplication.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.asynctask.NotificationCountAsycTask;
import com.example.myapplication.asynctask.ShowAllRequestsAdapter;
import com.example.myapplication.asynctask.ShowAllRequestsAsyncTask;
import com.example.myapplication.model.ShowAllRequestsModel;
import com.example.myapplication.util.Utill;

import java.util.ArrayList;
import java.util.List;


public class ShowAllRequestsActivity extends Activity implements View.OnClickListener{
    private static RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private static RecyclerView recyclerView;
    private static ArrayList<ShowAllRequestsModel> data;
    public static View.OnClickListener myOnClickListener;
    private static ArrayList<Integer> removedItems;
    SharedPreferences sharedPreferences;

    RelativeLayout notificationsBellLayout;
    TextView countNotifications;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_show_all_requests);
        myOnClickListener = new MyOnClickListener(this);
        sharedPreferences=getSharedPreferences("AuthenticationPref", Context.MODE_PRIVATE);

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
        ((Button)findViewById(R.id.myRequests)).setOnClickListener(this);
        ((Button)findViewById(R.id.addNewRequest)).setOnClickListener(this);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        removedItems = new ArrayList<Integer>();
        if (Utill.isNetworkAvailable(ShowAllRequestsActivity.this)) {
               // new ShowAllRequestsAsyncTask(ShowAllRequestsActivity.this).execute("email="+sharedPreferences.getString("userMail",""));
        } else {
            Toast.makeText(ShowAllRequestsActivity.this, getResources().getString(R.string.noInternet), Toast.LENGTH_SHORT).show();
        }


        //adapter = new ShowAllRequestsAdapter(data);
        //recyclerView.setAdapter(adapter);
    }
 @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.myRequests:
                startActivity(new Intent(ShowAllRequestsActivity.this,MyRequestsActivity.class));
                break;
            case R.id.addNewRequest:
                startActivity(new Intent(ShowAllRequestsActivity.this, CreaterequestActivity.class));
                break;

            case R.id.notificationsBellLayout: startActivity(new Intent(this,NotificationsActivity.class));
                break;

        }

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