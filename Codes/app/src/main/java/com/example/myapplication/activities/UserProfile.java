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

	
private void viewRegistration() {

		nameTV=(EditText)findViewById(R.id.nameTV);
		mobileNOTV=(EditText)findViewById(R.id.mobileNOTV);
		recidentNoTV=(EditText)findViewById(R.id.recidentNoTV);
		bloodGroupTv=(EditText)findViewById(R.id.bloodGroupTv);
		genderTv=(EditText)findViewById(R.id.genderTv);
		dobTv=(EditText)findViewById(R.id.dobTv);
		weghtTv=(EditText)findViewById(R.id.weghtTv);
		mailTv=(EditText)findViewById(R.id.mailTv);
		howOftenTv=(EditText)findViewById(R.id.howOftenTv);
		cityTv=(EditText)findViewById(R.id.cityTv);
		addressTv=(EditText)findViewById(R.id.addressTv);
		updateButton=(Button)findViewById(R.id.update);
		editProfile=(TextView)findViewById(R.id.imageViewupdateprofile);
		editProfile.setOnClickListener(this);
		updateButton.setOnClickListener(this);
		dobTv.setOnClickListener(this);

	}

	private boolean validation() {
		nameStr= nameTV.getText().toString().trim();
		mobileNOStr= mobileNOTV.getText().toString().trim();
		recidentNoStr=recidentNoTV.getText().toString().trim();
		bloodGroupStr= bloodGroupTv.getText().toString().trim();
		genderStr= genderTv.getText().toString().trim();
		dobStr= dobTv.getText().toString().trim();
		weghtStr= weghtTv.getText().toString().trim();
		mailStr= mailTv.getText().toString().trim();
		howOftenStr= howOftenTv.getText().toString().trim();
		cityStr= cityTv.getText().toString().trim();
		addressStr= addressTv.getText().toString().trim();

		if(nameStr.length()==0){
			Toast.makeText(UserProfile.this, getResources().getString(R.string.pleaseEnter)+" "+ getResources().getString(R.string.Name), Toast.LENGTH_SHORT).show();
		}else if(mobileNOStr.length()==0){
			Toast.makeText(UserProfile.this, getResources().getString(R.string.pleaseEnter)+" "+ getResources().getString(R.string.MobileNo), Toast.LENGTH_SHORT).show();
		}else if(recidentNoStr.length()==0){
			Toast.makeText(UserProfile.this, getResources().getString(R.string.pleaseEnter)+" "+ getResources().getString(R.string.ResidentNo), Toast.LENGTH_SHORT).show();
		}else if(bloodGroupStr.length()==0){
			Toast.makeText(UserProfile.this, getResources().getString(R.string.pleaseEnter)+" "+ getResources().getString(R.string.BloodGroup), Toast.LENGTH_SHORT).show();
		}else if(mobileNOStr.length()==0){
			Toast.makeText(UserProfile.this, getResources().getString(R.string.pleaseEnter)+" "+ getResources().getString(R.string.Name), Toast.LENGTH_SHORT).show();
		}else if(mobileNOStr.length()==0){
			Toast.makeText(UserProfile.this, getResources().getString(R.string.pleaseEnter)+" "+ getResources().getString(R.string.Name), Toast.LENGTH_SHORT).show();
		}


		return true;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()){
			case R.id.imageViewupdateprofile:setEditable(true);
				break;
			case R.id.update:updateProfile();
				break;
			case R.id.notificationsBellLayout:
				startActivity(new Intent(this,NotificationsActivity.class));
				break;
			case R.id.dobTv:date();
				break;
			default:break;
		}

	}

    public void setEditable(boolean editable){
		nameTV.setEnabled(editable);
		mobileNOTV.setEnabled(editable);
		recidentNoTV.setEnabled(editable);
		bloodGroupTv.setEnabled(editable);
		//genderTv.setEnabled(editable);
		dobTv.setEnabled(editable);
		weghtTv.setEnabled(editable);
		mailTv.setEnabled(editable);
		howOftenTv.setEnabled(editable);
		cityTv.setEnabled(editable);
		addressTv.setEnabled(editable);
		dobTv.setClickable(true);
		if(!editable) {
			updateButton.setVisibility(View.GONE);
			editProfile.setVisibility(View.VISIBLE);
		}
		else {
			updateButton.setVisibility(View.VISIBLE);
			editProfile.setVisibility(View.GONE);
		}

			}

	public boolean isNetworkAvailable() {

		ConnectivityManager cm = (ConnectivityManager) mContext
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = cm.getActiveNetworkInfo();
		if (networkInfo != null && networkInfo.isConnected()) {
			Log.e("Network Testing", "***Available***");
			return true;
		}
		Log.e("Network Testing", "***Not Available***");
		return false;
	}
	
	
	
	
	@Override
	protected void onStop() {
	    super.onStop();

	    if(progressDialog1!= null)
	    	progressDialog1.dismiss();
	}
	
	
	/****************************************selecting profile image start*************************************************/	
	private void selectImage() {

		final CharSequence[] options = { "Take Photo", "Choose from Gallery" };

		AlertDialog.Builder builder = new AlertDialog.Builder(UserProfile.this);
		builder.setTitle("Add Photo!");
		builder.setItems(options, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int item) {
				if (options[item].equals("Take Photo")) {
					
					Calendar currentDate = Calendar.getInstance();
					SimpleDateFormat formatter = new SimpleDateFormat("yyyy:MMM:dd:HH:mm:ss");
					String dateformat = formatter.format(currentDate.getTime());
					String date[] = dateformat.split(":");
					String year = date[0]; String month = date[1];
					String d = date[2]; String time = date[3];
					String min =date[4]; String sec =date[5];
					fileName ="upload"+year+"-"+month+"-"+d+"-"+time+"-"+min+"-"+sec;
					
					destination = null;
					
					Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
					destination = new File(android.os.Environment
							.getExternalStorageDirectory(), fileName+".jpg");
		            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(destination));
		            startActivityForResult(intent, 1);

				
				} else if (options[item].equals("Choose from Gallery")) {
					
					Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
					startActivityForResult(intent, 2);

				}
			}
		});
		builder.show();
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		
		if (resultCode == RESULT_OK) {
			
			if (requestCode == 1) {
				try {
					if (destination != null) {
						FileInputStream in = new FileInputStream(destination);
		                BitmapFactory.Options options = new BitmapFactory.Options();
		                options.inSampleSize = 10;
//		                imagePath = destination.getAbsolutePath();
		                String filePath = destination.getAbsolutePath();
		                
		                picturePath = reduce(filePath);
		                encodeFileToBase64Binary(filePath); 
		                
						int fileNameIndex =  filePath.lastIndexOf("/");
						BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
						Bitmap bitmap= BitmapFactory.decodeFile(filePath,bitmapOptions);
						imageViewprofile.setImageBitmap(getRoundedShape(bitmap));

					}
	                
	            } catch (FileNotFoundException e) {
	                e.printStackTrace();
	            }

			} else if (requestCode == 2) {

				Uri selectedImage = data.getData();
				String[] filePath = { MediaStore.Images.Media.DATA };
				Cursor c = getContentResolver().query(selectedImage, filePath,null, null, null);
				c.moveToFirst();
				int columnIndex = c.getColumnIndex(filePath[0]);
				String ppPath = c.getString(columnIndex);
				
				
				picturePath = reduce(ppPath);
				encodeFileToBase64Binary(picturePath); 
				c.close();
				
				Bitmap thumbnail = (BitmapFactory.decodeFile(picturePath));
				
				//Log.v("path of image from gallery......******************.........",picturePath + "");
				
				
				Bitmap bitmap2 = getRoundedShape(thumbnail);
				// imageViewprofile.setImageBitmap(thumbnail);
				imageViewprofile.setImageBitmap(bitmap2);
			}
		}
	}
	public String getPath(Uri uri, Activity activity) {
		String[] projection = { MediaColumns.DATA };
		Cursor cursor = activity
				.managedQuery(uri, projection, null, null, null);
		int column_index = cursor.getColumnIndexOrThrow(MediaColumns.DATA);
		cursor.moveToFirst();
		return cursor.getString(column_index);
	}
	 public Bitmap getRoundedShape(Bitmap scaleBitmapImage) {
		// TODO Auto-generated method stub
		int targetWidth = 90;
		int targetHeight = 90;
		Bitmap targetBitmap = Bitmap.createBitmap(targetWidth, targetHeight, Bitmap.Config.ARGB_8888);

		Canvas canvas = new Canvas(targetBitmap);
		Path path = new Path();
		path.addCircle(((float) targetWidth - 1) / 2,
				((float) targetHeight - 1) / 2,
				(Math.min(((float) targetWidth), ((float) targetHeight)) / 2),
				Path.Direction.CCW);

		canvas.clipPath(path);
		Bitmap sourceBitmap = scaleBitmapImage;
		canvas.drawBitmap(sourceBitmap, new Rect(0, 0, sourceBitmap.getWidth(),
				sourceBitmap.getHeight()), new Rect(0, 0, targetWidth,
				targetHeight), null);
		
		return targetBitmap;
	}
	 

		public String reduce(String path) {

	        Bitmap scaledBitmap = null;
	        File file = new File(path);

	        String strMyImagePath = file.getAbsolutePath();
	        FileOutputStream fos = null;
	        try {
	            //Decode image size
	            BitmapFactory.Options o = new BitmapFactory.Options();
	            o.inJustDecodeBounds = true;
	            BitmapFactory.decodeStream(new FileInputStream(file),null,o);

	            //The new size we want to scale to
	            final int REQUIRED_SIZE = 100;

	            //Find the correct scale value. It should be the power of 2.
	            int scale=1;
	            while(o.outWidth/scale/2>=REQUIRED_SIZE && o.outHeight/scale/2>=REQUIRED_SIZE)
	                scale*=2;

	            //Decode with inSampleSize
	            BitmapFactory.Options o2 = new BitmapFactory.Options();
	            o2.inSampleSize=scale;
	            scaledBitmap = BitmapFactory.decodeStream(new FileInputStream(file), null, o2);

	            fos = new FileOutputStream(file);
	            scaledBitmap.compress(Bitmap.CompressFormat.JPEG, 20, fos);
	            fos.flush();
	            fos.close();

//	            scaledBitmap.recycle();

	        } catch (FileNotFoundException e) {

	            e.printStackTrace();
	        } catch (Exception e) {

	            e.printStackTrace();
	        }
	        return strMyImagePath;
	    }
	/****************************************selecting profile image end*************************************************/
	/***********************************************Base64 start**********************************************************/
		private static byte[] loadFile(File file) throws Exception {
	        InputStream is = new FileInputStream(file);

	        long length = file.length();
	        if (length > Integer.MAX_VALUE) {
	            // File is too large
	        }
	        byte[] bytes = new byte[(int)length];
	        
	        int offset = 0;
	        int numRead = 0;
	        while (offset < bytes.length
	               && (numRead=is.read(bytes, offset, bytes.length-offset)) >= 0) {
	            offset += numRead;
	        }

	        if (offset < bytes.length) {
	            throw new IOException("Could not completely read file "+file.getName());
	        }

	        is.close();
	        return bytes;
	    }
	    
	    private String encodeFileToBase64Binary(String fileName) {
	           
	            
	            try{
	                    File file = new File(fileName);
	                    byte[] bytes = loadFile(file);

	                   
	            }catch(Exception e){
	                    encoded = "large file";
	            }
	            

	            return encoded;
	    }


	@Override
	public void onSuccess(String response) {

		if(progressDialog!=null) {
			progressDialog.dismiss();
			progressDialog.cancel();
		}

		try {
			setEditable(false);
			JSONArray jsonArray=new JSONArray(response);

			JSONArray firstArray=jsonArray.getJSONArray(0);

			JSONObject firstJobj=firstArray.getJSONObject(0);
			JSONArray secondArray=jsonArray.getJSONArray(1);
			JSONObject secondJobj=secondArray.getJSONObject(0);

			nameTV.setText(firstJobj.getString("userfullName"));
			mailTv.setText(firstJobj.getString("userEmail"));

			mobileNOTV.setText(secondJobj.getString("userMobile"));
			recidentNoTV.setText(secondJobj.getString("userResPhone"));
			bloodGroupTv.setText(secondJobj.getString("userBloodGroup"));
			genderTv.setText(secondJobj.getString("userGender"));
			dobTv.setText(secondJobj.getString("userDOB"));
			weghtTv.setText(secondJobj.getString("userWeight"));
			howOftenTv.setText(secondJobj.getString("userHowoften"));
			cityTv.setText(secondJobj.getString("userCity"));
			addressTv.setText(secondJobj.getString("userCity"));



		} catch (JSONException e) {
			e.printStackTrace();
		}


	}

	@Override
	public void onFailure(String failResponse) {
			if(progressDialog!=null) {
				progressDialog.dismiss();
				progressDialog.cancel();
			}
		if(failResponse.equals(""))
		  Toast.makeText(context,"Could not connect to the server", Toast.LENGTH_SHORT).show();
		else{
			Toast.makeText(context,failResponse, Toast.LENGTH_SHORT).show();

		}

	}
	public void updateProfile(){
		if(nameTV.getText().toString().equals(""))
			Utill.showToast(this,"Please enter name");
		else if(mobileNOTV.getText().toString().equalsIgnoreCase(""))
			Utill.showToast(this,"Please enter mobile no");
		else if(recidentNoTV.getText().toString().equalsIgnoreCase(""))
			Utill.showToast(this,"Please enter resident phone no");
		else if(bloodGroupTv.getText().toString().equalsIgnoreCase(""))
			Utill.showToast(this,"Please enter blood group");
		else if(genderTv.getText().toString().equalsIgnoreCase(""))
			Utill.showToast(this,"Please enter gender");
		else if(dobTv.getText().toString().equalsIgnoreCase(""))
			Utill.showToast(this,"Please enter Date of Birth");
		else if(weghtTv.getText().toString().equalsIgnoreCase(""))
			Utill.showToast(this,"Please enter weight");
		else if(mailTv.getText().toString().equalsIgnoreCase(""))
			Utill.showToast(this,"Please enter email");
		else if(howOftenTv.getText().toString().equalsIgnoreCase(""))
			Utill.showToast(this,"Please enter how often");
		else if(cityTv.getText().toString().equalsIgnoreCase(""))
			Utill.showToast(this,"Please enter City");
		else if(addressTv.getText().toString().equalsIgnoreCase(""))
			Utill.showToast(this,"Please enter Address");

		else{
			if (Utill.isNetworkAvailable(UserProfile.this)) {
				/*profileImage.setDrawingCacheEnabled(true);
				profileImage.buildDrawingCache();
				Bitmap bm = profileImage.getDrawingCache();
				ByteArrayOutputStream stream = new ByteArrayOutputStream();
				bm.compress(Bitmap.CompressFormat.PNG, 100, stream);
				byte[] byteArray = stream.toByteArray();*/

				//Profile/updateDonorprofile?donor_name=&dob=&blood=&weight=&res_phno=&address1=&city_name=&mob_no=&userID=&image=
				//http://websitesdev.in/jjyothi/index.php/Profile/updateDonorprofile?donor_name=&dob=&blood=&weight=&res_phno=&address1=&city_name=&mob_no=&userID=
				String placer="email="+sharedPreference.getPrefValue("userMail")+"&donor_name="+nameTV.getText().toString()+"&dob="+dobTv.getText().toString()+"&blood="+bloodGroupTv.getText().toString()+"&weight="+weghtTv.getText().toString()+"&res_phno="+recidentNoTV.getText().toString()+"&address1="+addressTv.getText().toString()+"&city_name="+cityTv.getText().toString()+"&mob_no="+mobileNOTV.getText().toString()+"&userID="+sharedPreference.getPrefValue(ApplicationHolder.USERNAME);
				new UpdateProfileAsyncTask(this).execute(placer.replace(" ","%20"));
			} else {
				Toast.makeText(this, getResources().getString(R.string.noInternet), Toast.LENGTH_SHORT).show();
			}
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

	public void date() {
		final Calendar c = Calendar.getInstance();
		mYear = c.get(Calendar.YEAR);
		mMonth = c.get(Calendar.MONTH);
		mDay = c.get(Calendar.DAY_OF_MONTH);

		DatePickerDialog dpd = new DatePickerDialog(this,
				new DatePickerDialog.OnDateSetListener() {
					@Override
					public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
						dobTv.setText( year+ "-" + (monthOfYear + 1)+ "-" +dayOfMonth );
					}
				}, mYear, mMonth, mDay);
		dpd.show();
		dpd.setIcon(R.drawable.calander);
	}
}
