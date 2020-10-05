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
