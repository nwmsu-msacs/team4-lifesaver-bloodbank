package com.example.myapplication.adapter;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.activities.ResponseActivity;
import com.example.myapplication.activities.ShowAllRequestsActivity;
import com.example.myapplication.asynctask.AcceptNotificationAsyncTask;
import com.example.myapplication.model.CreateRequest;
import com.example.myapplication.model.CreateResponse;
import com.example.myapplication.model.NotificationModel;
import com.example.myapplication.util.SharedPreference;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;


public class NotificationsAdpter extends RecyclerView.Adapter<NotificationsAdpter.MyViewHolder> {

    static Context context;

    private static ArrayList<NotificationModel> dataSet;
    public NotificationsAdpter(ArrayList<NotificationModel> data) {
        this.dataSet = data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        context=parent.getContext();
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.notifications_card, parent, false);

        view.setOnClickListener(ShowAllRequestsActivity.myOnClickListener);

        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        TextView title=holder.title;
        TextView description=holder.description;
        //TextView contactPerson=holder.contactPerson;
        TextView contactNo=holder.contactNo;
        TextView date=holder.date;



        title.setText(dataSet.get(position).getNotificationTitle());
        description.setText("  "+dataSet.get(position).getNotificationDesc());
        //contactPerson.setText(":   "+dataSet.get(position).getNotificationContactPerson());
        contactNo.setText(":   "+dataSet.get(position).getNotificationFrom());
        date.setText(":   "+dataSet.get(position).getNotificationCreatedOn());
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        // TextView pName,pPhoneNo,pBloodG,pAge,pLocalNo,pBloodNeedOn,noOfUnits,hospitalName,hospitalAddress,userpincode;

        TextView title;
        TextView description;
        Button acceptRequest;
       // TextView contactPerson;
        TextView contactNo;
        TextView date;


        public MyViewHolder(View itemView) {
            super(itemView);
           // this.contactPerson = (TextView) itemView.findViewById(R.id.contactPerson);
            this.title = (TextView) itemView.findViewById(R.id.notificationTitle);
            this.description = (TextView) itemView.findViewById(R.id.notificationDescription);
            this.contactNo = (TextView) itemView.findViewById(R.id.mobileNo);
            this.date = (TextView) itemView.findViewById(R.id.date);
            this.acceptRequest=(Button)itemView.findViewById(R.id.acceptId);
            final SharedPreference sharedPreference=new SharedPreference(context);
            acceptRequest.setTag(getLayoutPosition());
            acceptRequest.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    final String emailId=new SharedPreference(context).getPrefValue(context.getString(R.string.email_id));
                    final String mobile=new SharedPreference(context).getPrefValue(context.getString(R.string.mobile_no));
                    DatabaseReference quizRef = FirebaseDatabase.getInstance().getReference("Responses");
                    DatabaseReference req = FirebaseDatabase.getInstance().getReference("Requests");
                    String key=quizRef.push().getKey();
                    assert key != null;
                    req.child(dataSet.get(getLayoutPosition()).getNotificationId()).setValue(new CreateRequest(dataSet.get(getLayoutPosition()).getNotificationCreatedOn(),
                            dataSet.get(getLayoutPosition()).getNotificationAddress(),
                            dataSet.get(getLayoutPosition()).getEmail(),
                            dataSet.get(getLayoutPosition()).getGroup(),
                            dataSet.get(getLayoutPosition()).getNotificationId(),
                            mobile,
                            dataSet.get(getLayoutPosition()).getNotificationContactPerson(),
                            dataSet.get(getLayoutPosition()).getUnits(),"closed"));
                    quizRef.child(key).setValue(new CreateResponse(dataSet.get(getLayoutPosition()).getNotificationCreatedOn(),
                            dataSet.get(getLayoutPosition()).getNotificationAddress(),
                            dataSet.get(getLayoutPosition()).getEmail(),
                            dataSet.get(getLayoutPosition()).getGroup(),
                            dataSet.get(getLayoutPosition()).getNotificationId(),
                            mobile,
                            dataSet.get(getLayoutPosition()).getNotificationContactPerson(),
                            dataSet.get(getLayoutPosition()).getUnits(),"closed",emailId));

dataSet.remove(getLayoutPosition());
                    notifyDataSetChanged();
                    //new AcceptNotificationAsyncTask(context).execute("notificationId="+dataSet.get(getLayoutPosition()).getNotificationId()+"&email="+sharedPreference.getPrefValue("userMail"));
                }
            });
        }
    }

}
