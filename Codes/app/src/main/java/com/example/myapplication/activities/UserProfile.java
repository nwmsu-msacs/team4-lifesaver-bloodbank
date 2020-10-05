package com.example.myapplication.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.Rect;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.MediaStore.MediaColumns;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.example.myapplication.CircleImageView;
import com.example.myapplication.R;
import com.example.myapplication.animation.CircleDisplay;
import com.example.myapplication.asynctask.NotificationCountAsycTask;
import com.example.myapplication.asynctask.UpdateProfileAsyncTask;
import com.example.myapplication.asynctask.UserProfileAsyncTask;
import com.example.myapplication.interfaces.ServiceResponseHandler;
import com.example.myapplication.util.ApplicationHolder;
import com.example.myapplication.util.SharedPreference;
import com.example.myapplication.util.Utill;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class UserProfile extends SuperClasses implements OnClickListener, ServiceResponseHandler {

	ImageView profileImage;
//	ImageView profileImage;

	CircleImageView imageViewprofile;
	TextView perentage_tv,editProfile;
	Context mContext = UserProfile.this;
	TextView specialities_title;
	ProgressDialog progressDialog1;
	static String userIdString;
	SharedPreference sharedPreference;
	private int mYear, mMonth, mDay;

	EditText nameTV,mobileNOTV,recidentNoTV,bloodGroupTv,genderTv,dobTv,weghtTv,mailTv,howOftenTv,cityTv,addressTv;
	String nameStr,mobileNOStr,recidentNoStr,bloodGroupStr,genderStr,dobStr,weghtStr,mailStr,howOftenStr,cityStr,addressStr;

	private CircleDisplay mCircleDisplay;
	String picturePath="";
	String encoded = null;
	String fileName;
	File destination;
	ImageView event_share_btn;
	ProgressDialog progressDialog;
	SharedPreference sharedpreferences ;

	Button updateButton;
	RelativeLayout notificationsBellLayout;
	TextView countNotifications;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_userprofile);
		sharedpreferences = new SharedPreference(this);

		sharedPreference=new SharedPreference(this);
		viewRegistration();
		loadGenericValues(this, getResources().getString(R.string.menu_profile));

		notificationsBellLayout=(RelativeLayout) findViewById(R.id.notificationsBellLayout);
		notificationsBellLayout.setOnClickListener(this);
		countNotifications=(TextView)findViewById(R.id.creditsId);
		new NotificationCountAsycTask(this).execute();


		imageViewprofile = (CircleImageView) findViewById(R.id.imageViewprofile);
		imageViewprofile.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				selectImage();

			}
		});

		mCircleDisplay=new CircleDisplay(this);
		perentage_tv = (TextView)findViewById(R.id.perentage_tv);
		if (Utill.isNetworkAvailable(UserProfile.this)) {
			if(validation()){

				//new UserProfileAsyncTask(UserProfile.this).execute("email="+sharedpreferences.getPrefValue("userMail"));

			}
//
		} else {
			Toast.makeText(UserProfile.this, getResources().getString(R.string.noInternet), Toast.LENGTH_SHORT).show();
		}

	}

	
