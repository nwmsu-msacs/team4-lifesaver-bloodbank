package com.example.myapplication.activities;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.adapter.NotificationsAdpter;
import com.example.myapplication.adapter.ShowMyRequestsAdapter;
import com.example.myapplication.asynctask.NotificationsAsyncTask;
import com.example.myapplication.model.NotificationModel;
import com.example.myapplication.model.ShowAllRequestsModel;
import com.example.myapplication.util.SharedPreference;
import com.example.myapplication.util.Utill;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;
import java.util.List;

public class NotificationsActivity extends Activity {
    private static RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private static RecyclerView recyclerView;
    private static ArrayList<NotificationModel> data;
    public static View.OnClickListener myOnClickListener;
    private static ArrayList<Integer> removedItems;
    SharedPreferences sharedPreferences;
    private List<NotificationModel> list;

    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_notification);
        myOnClickListener = new MyOnClickListener(this);
        sharedPreferences = getSharedPreferences("AuthenticationPref", Context.MODE_PRIVATE);

        ((ImageView) findViewById(R.id.backImage)).setOnClickListener(new View.OnClickListener() {
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

        removedItems = new ArrayList<Integer>();
        if (Utill.isNetworkAvailable(NotificationsActivity.this)) {
            Query query1 = FirebaseDatabase.getInstance().getReference("Requests");/*.orderByChild("email").equalTo(emailId);*/
            data = new ArrayList<>();
            final String emailId=new SharedPreference(NotificationsActivity.this).getPrefValue(getString(R.string.email_id));
            query1.addChildEventListener(new ChildEventListener()
            {

                @Override
                public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                    if(snapshot.exists() && !emailId.equalsIgnoreCase(snapshot.child("email").getValue(String.class)) && !"closed".equalsIgnoreCase(snapshot.child("isClosed").getValue(String.class))){
                        NotificationModel notificationModel=new NotificationModel();
                        notificationModel.setNotificationTitle(snapshot.child("group").getValue(String.class)+ " Blood Group Request");
                        notificationModel.setNotificationId(snapshot.child("id").getValue(String.class));
                        notificationModel.setGroup(snapshot.child("group").getValue(String.class));
                        notificationModel.setNotificationAddress(snapshot.child("city").getValue(String.class));
                        notificationModel.setNotificationContactPerson(snapshot.child("patientName").getValue(String.class));
                        notificationModel.setNotificationDesc("Requited Quantity "+snapshot.child("units").getValue(String.class)+" Units");
                        notificationModel.setNotificationCreatedOn(snapshot.child("date").getValue(String.class));
                        notificationModel.setNotificationFrom(snapshot.child("mobile").getValue(String.class));
                        notificationModel.setUnits(snapshot.child("units").getValue(String.class));
                        notificationModel.setEmail(snapshot.child("email").getValue(String.class));
                        data.add(notificationModel);
                        adapter = new NotificationsAdpter(data);
                        recyclerView.setAdapter(adapter);
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

                }
            });




            //  new NotificationsAsyncTask(NotificationsActivity.this).execute();
        } else {
            Toast.makeText(NotificationsActivity.this, getResources().getString(R.string.noInternet), Toast.LENGTH_SHORT).show();
        }


        //adapter = new ShowAllRequestsAdapter(data);
        //recyclerView.setAdapter(adapter);
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



           /* TextView textViewName = (TextView) viewHolder.itemView.findViewById(R.id.userName);
            String selectedName = (String) textViewName.getText();
            int selectedItemId = -1;
            for (int i = 0; i < ApplicationHolder.userNames.length; i++) {
                if (selectedName.equals(ApplicationHolder.userNames[i])) {
                    //selectedItemId = MyData.id_[i];
                }
            }
            removedItems.add(selectedItemId);
            data.remove(selectedItemPosition);
            adapter.notifyItemRemoved(selectedItemPosition);*/
        }
    }

    public static void responseRequests(List<NotificationModel> list) {
        data = (ArrayList) list;
        adapter = new NotificationsAdpter(data);
        recyclerView.setAdapter(adapter);
    }
}





