package com.example.myapplication.adapter;

import android.content.Context;
import android.content.Intent;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.activities.CreaterequestActivity;
import com.example.myapplication.activities.ShowAllRequestsActivity;
import com.example.myapplication.model.ShowAllRequestsModel;
import com.example.myapplication.util.Utill;

import java.util.ArrayList;

public class ResponseAdapter extends RecyclerView.Adapter<ResponseAdapter.MyViewHolder> {

    ArrayList<ShowAllRequestsModel> dataSet;

    Context context;
    public ResponseAdapter(ArrayList<ShowAllRequestsModel> data) {
        this.dataSet = data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        context=parent.getContext();

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.requests_card, parent, false);
        view.setOnClickListener(ShowAllRequestsActivity.myOnClickListener);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        TextView pName=holder.pName;
        TextView pBloodGroup=holder.pBloodGroup;
        TextView pBloodOn=holder.pBloodOn;
        //  TextView pNoOfUnits=holder.pNoOfUnits;
        TextView mobileNo=holder.mobileNo;
        TextView hospitalName=holder.hospitalName;
        TextView patientAddress=holder.patientAddress;
        

        pName.setText(":   "+dataSet.get(position).getpName());
        pBloodGroup.setText(":   "+dataSet.get(position).getpBloodGroup());
        pBloodOn.setText(":   "+dataSet.get(position).getpBloodOn());
        //   pNoOfUnits.setText(":   "+dataSet.get(position).getpNoOfUnits());
        mobileNo.setText(":   "+dataSet.get(position).getMobileNo());
        hospitalName.setText(":  "+dataSet.get(position).getHospitalName());
        patientAddress.setText(":   "+dataSet.get(position).getPatientAddress());
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public  class MyViewHolder extends RecyclerView.ViewHolder {

        // TextView pName,pPhoneNo,pBloodG,pAge,pLocalNo,pBloodNeedOn,noOfUnits,hospitalName,hospitalAddress,userpincode;

        TextView pName;
        TextView pBloodGroup;
        TextView pBloodOn;
        //    TextView pNoOfUnits;
        TextView mobileNo;
        TextView hospitalName;
        TextView patientAddress;
        //     TextView requestActionStatus;
        //     TextView requestAcceptedBy;
        //    TextView requestStatus;
        ImageView editRequest;

        public MyViewHolder(View itemView) {
            super(itemView);


            this.editRequest = (ImageView) itemView.findViewById(R.id.editRequest);

            editRequest.setTag(getLayoutPosition());
            editRequest.setVisibility(View.GONE);

            editRequest.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Utill.showAllRequestsModel=dataSet.get(getLayoutPosition());
                    context.startActivity(new Intent(context, CreaterequestActivity.class).putExtra("myrequest","edit"));
                }
            });
            this.pName = (TextView) itemView.findViewById(R.id.pName);
            this.pBloodGroup = (TextView) itemView.findViewById(R.id.pBloodGroup);
            this.pBloodOn = (TextView) itemView.findViewById(R.id.bloodNeedOn);
            // this.pNoOfUnits = (TextView) itemView.findViewById(R.id.numberOfUnits);
            this.mobileNo = (TextView) itemView.findViewById(R.id.mobileNo);
            this.hospitalName=(TextView)itemView.findViewById(R.id.hospitalName);
            this.patientAddress = (TextView) itemView.findViewById(R.id.patientAddress);
            //     this.requestActionStatus = (TextView) itemView.findViewById(R.id.requestActionStatus);
            //     this.requestAcceptedBy = (TextView) itemView.findViewById(R.id.requestAcceptedBy);
            //     this.requestStatus = (TextView) itemView.findViewById(R.id.requestStatus);
        }
    }
}

