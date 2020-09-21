package com.example.myapplication.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.asynctask.NotificationCountAsycTask;


public class BloodTipsActivity extends SuperClasses implements View.OnClickListener {
    RelativeLayout notificationsBellLayout;
    TextView countNotifications;
    LinearLayout mainLayout;
    String tips[] = {"The most important constituent of the blood is Iron. It is important to include iron-rich foods in your regular diet such as red meat, fish, beef, lentils, beans, spinach, iron-fortified cereals and raisins.",
            "Healthy blood is oxygen-rich blood. For your blood to be strong with oxygen, exercise regularly and have some consistent physical activity outdoors",
            "Drinking lots and lots of water is highly essential",
            "Vegetables, especially green-leaf, are considered healthy for the blood",
            "Eat fruits and vegetables on a daily basis to keep the blood fresh and healthy",
            "Fiber-rich food content is also very much helpful in maintaining healthy blood",
            "Avoid smoking and alcoholic consumption as they can damage the health of the blood and eventually the health of the body very quickly",
            "Excess of sugar in foods is also harmful for blood. Limit the consumption of sweet beverages and soft drinks.",
            "Use natural products for skin health. Heavy use of artificial cosmetic products can affect the blood under as the constituents of these products can seep through the skin and cause damage"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_bloodtips);
        loadGenericValues(this, getResources().getString(R.string.menu_blood_tips));
        mainLayout = (LinearLayout) findViewById(R.id.main_layout);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //TextView text=(TextView)findViewById(R.id.bloodTipsTxt);
        //text.setText(Html.fromHtml(getResources().getString(R.string.blood_tips)));
        notificationsBellLayout = (RelativeLayout) findViewById(R.id.notificationsBellLayout);
        notificationsBellLayout.setOnClickListener(this);
        countNotifications = (TextView) findViewById(R.id.creditsId);
        new NotificationCountAsycTask(this).execute();

        for (int i = 0; i < tips.length; i++) {
            View someView = inflater.inflate(R.layout.tips_items, null, false);
            TextView point = (TextView) someView.findViewById(R.id.point);
            point.setText(tips[i]);
            mainLayout.addView(someView);
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.notificationsBellLayout) {
            startActivity(new Intent(this, NotificationsActivity.class));
        }
    }

    public void responseCount(String response) {
        int k = Integer.parseInt(response);
        if (k > 0) {
            countNotifications.setVisibility(View.VISIBLE);
            countNotifications.setText(response);
        } else {
            countNotifications.setVisibility(View.GONE);
        }
    }
}
