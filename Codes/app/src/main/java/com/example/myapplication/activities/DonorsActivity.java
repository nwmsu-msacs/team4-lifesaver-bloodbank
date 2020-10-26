package com.example.myapplication.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.adapter.ShowDonorsAdapter;
import com.example.myapplication.adapter.ShowMyRequestsAdapter;
import com.example.myapplication.asynctask.NotificationCountAsycTask;
import com.example.myapplication.asynctask.SearchDonorsAsyncTask;
import com.example.myapplication.asynctask.ShowAllDonorsAsycTask;
import com.example.myapplication.model.ShowAllRequestsModel;
import com.example.myapplication.model.ShowDonors;
import com.example.myapplication.util.PlaceJSONParser;
import com.example.myapplication.util.Utill;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;




public class DonorsActivity extends Activity implements View.OnClickListener, AdapterView.OnItemClickListener {
    private static RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private static RecyclerView recyclerView;
    private static ArrayList<ShowDonors> data;
    public static View.OnClickListener myOnClickListener;
    private static ArrayList<Integer> removedItems;
    SharedPreferences sharedPreferences;
    LinearLayout searchLayout;
    Spinner spinner;
    RelativeLayout notificationsBellLayout;
    TextView countNotifications;

    AutoCompleteTextView city;
    PlacesTask placesTask;
    ParserTask parserTask;



    public void onCreate(Bundle b){
        super.onCreate(b);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_donors);

        searchLayout=(LinearLayout)findViewById(R.id.searchLayout);

        notificationsBellLayout=(RelativeLayout) findViewById(R.id.notificationsBellLayout);
        notificationsBellLayout.setOnClickListener(this);
        countNotifications=(TextView)findViewById(R.id.creditsId);
        new NotificationCountAsycTask(this).execute();

        //city=(EditText)findViewById(R.id.don_city);
        city=(AutoCompleteTextView) findViewById(R.id.don_city);
        myOnClickListener = new MyOnClickListener(this);
        sharedPreferences=getSharedPreferences("AuthenticationPref", Context.MODE_PRIVATE);
        spinner=(Spinner)findViewById(R.id.don_group) ;
        MySpinnerAdapter adapter = new MySpinnerAdapter(this, R.layout.view_spinner_item, Arrays.asList(getResources().getStringArray(R.array.blood_groups)));
        spinner.setAdapter(adapter);
        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        ((Button)findViewById(R.id.searchDonars)).setOnClickListener(this);
        ((Button)findViewById(R.id.allDonors)).setOnClickListener(this);
        ((Button)findViewById(R.id.search)).setOnClickListener(this);
        ((ImageView)findViewById(R.id.backImage)).setOnClickListener(this);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        removedItems = new ArrayList<Integer>();

        city.setThreshold(1);





  /*      city.addTextChangedListener(new TextWatcher() {

            int count=0;

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                count=city.getText().toString().trim().length();
               if(!stop) {
                   placesTask = new PlacesTask();
                  placesTask.execute(s.toString());
            }else{

             }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void afterTextChanged(Editable s) {



                if(count<city.getText().toString().trim().length()){
                    stop=false;
                }

                if(!stop){
                    placesTask = new PlacesTask();
                    placesTask.execute(s.toString());
                }
            }
        });
        city.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(view!=null){

                    stop=true;
                    showSpecifiedDonors();
//                    if(city.getText().toString().trim().length()>=0){
//                        new SeearchLatLang().execute(city.getText().toString().trim());
//
//                    }
                }
            }
        });*/

    }

    boolean stop=false;

    protected View.OnKeyListener onEnter = new View.OnKeyListener() {

        @Override
        public boolean onKey(View v, int keyCode, KeyEvent event) {
            if ((event.getAction() == KeyEvent.ACTION_DOWN)
                    && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                //here do what you want
                Log.v("anand<<<","Enter.....");
                //Toast.makeText(this,"enter",Toast.LENGTH_SHORT).show();
            }
            return false; // very important
        }
    };
    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.allDonors:
                showAllDonors();
                break;
            case R.id.searchDonars:recyclerView.setVisibility(View.GONE);
                searchLayout.setVisibility(View.VISIBLE);
                break;
            case R.id.search:showSpecifiedDonors();
                break;
            case R.id.backImage:onBackPressed();
                break;
            case R.id.notificationsBellLayout: startActivity(new Intent(this,NotificationsActivity.class));
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    private static class MySpinnerAdapter extends ArrayAdapter<String> {
        // Initialise custom font, for example:
        Typeface font = Typeface.createFromAsset(getContext().getAssets(), "customfont.otf");

        private MySpinnerAdapter(Context context, int resource, List<String> items) {
            super(context, resource, items);
        }

        // Affects default (closed) state of the spinner
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TextView view = (TextView) super.getView(position, convertView, parent);
            view.setTypeface(font);
            return view;
        }

        // Affects opened state of the spinner
        @Override
        public View getDropDownView(int position, View convertView, ViewGroup parent) {
            TextView view = (TextView) super.getDropDownView(position, convertView, parent);
            view.setTypeface(font);
            return view;
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

            Utill.showDonors=data.get(selectedItemPosition);
            context.startActivity(new Intent(context,CreaterequestActivity.class));

        }
    }

    public void responseDonors(List<ShowDonors> list){
        if(list.size()==0){
            Utill.showToast(this,"Donors are not available");
        }else {
            data = (ArrayList) list;

            searchLayout.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            adapter = new ShowDonorsAdapter(data);
            recyclerView.setAdapter(adapter);
        }
    }
    public void showAllDonors(){
        final List<ShowDonors> list=new ArrayList<>();
        if (Utill.isNetworkAvailable(DonorsActivity.this)) {

            Query query1= FirebaseDatabase.getInstance().getReference("Users");

            query1.addChildEventListener(new ChildEventListener() {

                @Override
                public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                    if(snapshot.exists()){

                        ShowDonors showDonors=new ShowDonors();
                        showDonors.setUserfullName(snapshot.child("name").getValue(String.class));
                        showDonors.setUserBloodGroup(snapshot.child("group").getValue(String.class));
                        showDonors.setUserMobile(snapshot.child("mobile").getValue(String.class));
                        showDonors.setUserCity(snapshot.child("city").getValue(String.class));
                        list.add(showDonors);

                    }else{
                        Utill.showToast(DonorsActivity.this,"No Data found");
                    }
                    responseDonors(list);
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


        } else {
            Toast.makeText(DonorsActivity.this, getResources().getString(R.string.noInternet), Toast.LENGTH_SHORT).show();
        }
    }

    public void showSpecifiedDonors(){
        final List<ShowDonors> list=new ArrayList<>();
        if(spinner.getSelectedItemPosition()==0){
            Utill.showToast(this,"Please select Blood group");
        }
        else if(city.getText().toString().equals("")){
            Utill.showToast(this,"Please enter city");
        }
        else {
            String cityName[]=city.getText().toString().split(",");
        if (Utill.isNetworkAvailable(DonorsActivity.this)) {


            Query query1 = FirebaseDatabase.getInstance().getReference("Users").orderByChild("group").equalTo(spinner.getSelectedItem().toString().trim() /*+ "_" + city.getText().toString().trim()*/);

            query1.addChildEventListener(new ChildEventListener() {

                @Override
                public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                    if(snapshot.exists()){


                        if(city.getText().toString().trim().equalsIgnoreCase(snapshot.child("city").getValue(String.class))){
                            ShowDonors showDonors=new ShowDonors();
                            showDonors.setUserfullName(snapshot.child("name").getValue(String.class));
                            showDonors.setUserBloodGroup(snapshot.child("group").getValue(String.class));
                            showDonors.setUserMobile(snapshot.child("mobile").getValue(String.class));
                            showDonors.setUserCity(snapshot.child("city").getValue(String.class));
                            list.add(showDonors);
                        }


                    }else{
                        Utill.showToast(DonorsActivity.this,"No Data found");
                    }
                    responseDonors(list);

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
          //  new SearchDonorsAsyncTask(DonorsActivity.this).execute("bloodgroup="+spinner.getSelectedItem().toString()+"&city="+cityName[0].replace(" ","%20"));
        } else {
            Toast.makeText(DonorsActivity.this, getResources().getString(R.string.noInternet), Toast.LENGTH_SHORT).show();
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




    /** A method to download json data from url */
    private String downloadUrl(String strUrl) throws IOException {
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try{
            URL url = new URL(strUrl);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.connect();
            iStream = urlConnection.getInputStream();
           BufferedReader br = new BufferedReader(new InputStreamReader(iStream));
            StringBuffer sb  = new StringBuffer();
            String line = "";
            while( ( line = br.readLine())  != null){
                sb.append(line);
            }
            data = sb.toString();

            br.close();

        }catch(Exception e){
            Log.d("Exception", e.toString());
        }finally{
            iStream.close();
            urlConnection.disconnect();
        }
        return data;
    }

    private class PlacesTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... place) {
            String data = "";
            // Obtain browser key from https://code.google.com/apis/console
            String key = "key=AIzaSyCRLa4LQZWNQBcjCYcIVYA45i9i8zfClqc";

            //String key ="key=AIzaSyDul8RSZhIYBEi6TDb3y1t7lW9dq9aNV28";
            String input="";

            try {
                input = "input=" + URLEncoder.encode(place[0], "utf-8");
            } catch (UnsupportedEncodingException e1) {
                e1.printStackTrace();
            }


            // place type to be searched
            String types = "types=geocode";

            // Sensor enabled
            String sensor = "sensor=true";

            // Building the parameters to the web service
            String parameters = input+"&"+types+"&"+sensor+"&"+key;

            // Output format
            String output = "json";

            // Building the url to the web service
            String url = "https://maps.googleapis.com/maps/api/place/autocomplete/"+output+"?"+parameters;

            try{
                // Fetching the data from web service in background

                Log.d("url>>>>>>>>>>>>>>>>",url);
                data = downloadUrl(url);
            }catch(Exception e){
                Log.d("Background Task",e.toString());
            }
            return data;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            // Creating ParserTask
            parserTask = new ParserTask();
            parserTask.execute(result);
        }
    }

/** A class to parse the Google Places in JSON format */
    private class ParserTask extends AsyncTask<String, Integer, List<HashMap<String, String>>> {

        JSONObject jObject;
        List<String> placeString;

        @Override
        protected List<HashMap<String, String>> doInBackground(String... jsonData) {

            List<HashMap<String, String>> places = null;

            PlaceJSONParser placeJsonParser = new PlaceJSONParser();

            try{
                jObject = new JSONObject(jsonData[0]);

                Log.v("anand>>>",">>>>>>"+jObject);
                //placeString.add(jObject.getString())
                // Getting the parsed data as a List construct

                places = placeJsonParser.parse(jObject);

            }catch(Exception e){
                Log.d("Exception",e.toString());
            }
            return places;
        }

        @Override
        protected void onPostExecute(List<HashMap<String, String>> result) {

            String[] from = new String[] { "description"};
           // String[] from = new String[] { new String("main_text")};
            int[] to = new int[] { android.R.id.text1 };

            // Creating a SimpleAdapter for the AutoCompleteTextView
            SimpleAdapter adapter = new SimpleAdapter(getBaseContext(), result, android.R.layout.simple_list_item_1, from, to);

            // Setting the adapter
            city.setAdapter(adapter);
            if (!stop)
            city.showDropDown();
            else
                city.dismissDropDown();
        }
    }

    private class SeearchLatLang extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String[] objects) {
            String data = "";
            InputStream iStream = null;
            HttpURLConnection urlConnection = null;
            try{


                String temUrl=objects[0];
                if(temUrl.contains(" ")){
                    temUrl=temUrl.replace(" ","%20");
                }
                URL url = new URL("http://maps.google.com/maps/api/geocode/json?address="+temUrl+"&sensor=false");

                // Creating an http connection to communicate with url
                urlConnection = (HttpURLConnection) url.openConnection();

                // Connecting to url
                urlConnection.connect();

                // Reading data from url
                iStream = urlConnection.getInputStream();

                BufferedReader br = new BufferedReader(new InputStreamReader(iStream));

                StringBuffer sb  = new StringBuffer();

                String line = "";
                while( ( line = br.readLine())  != null){
                    sb.append(line);
                }

                data = sb.toString();

                br.close();

            }catch(Exception e){
                Log.d("Exception", e.toString());
            }finally{
                try {
                    iStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                urlConnection.disconnect();
            }
            return data;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {

                JSONObject jsonObject=new JSONObject(s);

                JSONArray results=jsonObject.getJSONArray("results");
                if(results==null?false:results.length()>0?true:false){
                    JSONObject iii=results.getJSONObject(0);

                    JSONObject geometry=iii.getJSONObject("geometry");
                    JSONObject location=geometry.getJSONObject( "location");

                    //String lat=location.getString()

                }




            }catch (Exception e){

            }

            Log.v("Tag>>>>>>>>>>>",""+s);
        }
    }

}
 
