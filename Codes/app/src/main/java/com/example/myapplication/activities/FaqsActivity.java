package com.example.myapplication.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.Window;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.asynctask.NotificationCountAsycTask;

public class FaqsActivity extends SuperClasses implements View.OnClickListener{

    RelativeLayout notificationsBellLayout;
    TextView countNotifications;
    ListView faq_list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_faqs);
        loadGenericValues(this,getResources().getString(R.string.menu_faqs));
        //faq_list=(ListView)findViewById(R.id.faq_list);
        //FaqsAdapter adapter=new FaqsAdapter(this, Arrays.asList(getResources().getStringArray(R.array.faqs)),Arrays.asList(getResources().getStringArray(R.array.faqs_ans)));
        //faq_list.setAdapter(adapter);
        TextView text=(TextView)findViewById(R.id.faqTxt);
        text.setText(Html.fromHtml(getResources().getString(R.string.faqs)));
        notificationsBellLayout=(RelativeLayout) findViewById(R.id.notificationsBellLayout);
        notificationsBellLayout.setOnClickListener(this);
        countNotifications=(TextView)findViewById(R.id.creditsId);
        new NotificationCountAsycTask(this).execute();
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.notificationsBellLayout){
            startActivity(new Intent(this,NotificationsActivity.class));
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
}
