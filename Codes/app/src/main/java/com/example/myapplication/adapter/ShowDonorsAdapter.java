package com.example.myapplication.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;


import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.activities.DonorDetailsActivity;
import com.example.myapplication.activities.ShowAllRequestsActivity;
import com.example.myapplication.model.ShowDonors;
import com.example.myapplication.util.Utill;

import java.util.ArrayList;

public class ShowDonorsAdapter extends RecyclerView.Adapter<ShowDonorsAdapter.MyViewHolder> {

    ArrayList<ShowDonors> dataSet;

    Context context;
    public ShowDonorsAdapter(ArrayList<ShowDonors> data) {
        this.dataSet = data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        context=parent.getContext();

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.donors_list_item, parent, false);
        view.setOnClickListener(ShowAllRequestsActivity.myOnClickListener);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        TextView pName=holder.pName;
        TextView pBloodGroup=holder.pBloodGroup;
        TextView mobileNo=holder.mobileNo;
        TextView patientAddress=holder.patientAddress;

        pName.setText(":   "+dataSet.get(position).getUserfullName());
        pBloodGroup.setText(":   "+dataSet.get(position).getUserBloodGroup());
        mobileNo.setText(":   "+dataSet.get(position).getUserMobile());
        patientAddress.setText(":   "+dataSet.get(position).getUserCity());
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public  class MyViewHolder extends RecyclerView.ViewHolder {

        // TextView pName,pPhoneNo,pBloodG,pAge,pLocalNo,pBloodNeedOn,noOfUnits,hospitalName,hospitalAddress,userpincode;

        TextView pName;
        TextView pBloodGroup;
        TextView mobileNo;
        TextView patientAddress;
        ImageButton callToDonor;
       // ImageView editRequest;

        public MyViewHolder(View itemView) {
            super(itemView);


        /*    this.editRequest = (ImageView) itemView.findViewById(R.id.editRequest);

            editRequest.setTag(getLayoutPosition());

            editRequest.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Utill.showDonors=dataSet.get(getLayoutPosition());
                    context.startActivity(new Intent(context,CreaterequestActivity.class).putExtra("myrequest","edit"));
                }
            });*/
           itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Utill.showDonors=dataSet.get(getLayoutPosition());
                    context.startActivity(new Intent(context, DonorDetailsActivity.class));
                }
            });

            this.pName = (TextView) itemView.findViewById(R.id.Name);
            this.pBloodGroup = (TextView) itemView.findViewById(R.id.bloodGroup);
            this.mobileNo = (TextView) itemView.findViewById(R.id.mobileNo);
            this.patientAddress = (TextView) itemView.findViewById(R.id.location);
            this.callToDonor=(ImageButton)itemView.findViewById(R.id.callToDonor);
            callToDonor.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent in=new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+dataSet.get(getLayoutPosition()).getUserMobile()));
                    try{
                        context.startActivity(in);
                    }
                    catch (android.content.ActivityNotFoundException ex){
                        Toast.makeText(context,"your call is not founded", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}

