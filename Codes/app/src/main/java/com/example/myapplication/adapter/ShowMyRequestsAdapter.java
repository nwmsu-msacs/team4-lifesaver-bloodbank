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


public class ShowMyRequestsAdapter extends RecyclerView.Adapter<ShowMyRequestsAdapter.MyViewHolder> {

    ArrayList<ShowAllRequestsModel> dataSet;

    Context context;
    public ShowMyRequestsAdapter(ArrayList<ShowAllRequestsModel> data) {
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
        //    TextView requestActionStatus=holder.requestActionStatus;
        //    TextView requestAcceptedBy=holder.requestAcceptedBy;
        //   TextView requestStatus=holder.requestStatus;

        pName.setText(":   "+dataSet.get(position).getpName());
        pBloodGroup.setText(":   "+dataSet.get(position).getpBloodGroup());
        pBloodOn.setText(":   "+dataSet.get(position).getpBloodOn());
     //   pNoOfUnits.setText(":   "+dataSet.get(position).getpNoOfUnits());
        mobileNo.setText(":   "+dataSet.get(position).getMobileNo());
        hospitalName.setText(":  "+dataSet.get(position).getHospitalName());
        patientAddress.setText(":   "+dataSet.get(position).getPatientAddress());
        //    requestActionStatus.setText(":   "+dataSet.get(position).getRequestActionStatus());
        //    requestAcceptedBy.setText(":   "+dataSet.get(position).getRequestAcceptedBy());
        //    requestStatus.setText(":   "+dataSet.get(position).getRequestStatus());
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    