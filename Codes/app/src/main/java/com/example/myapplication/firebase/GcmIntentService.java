package com.example.myapplication.firebase;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;

import androidx.core.app.NotificationCompat;

import com.example.myapplication.R;
import com.example.myapplication.activities.LoginActivity;
import com.example.myapplication.activities.NotificationsActivity;
import com.example.myapplication.activities.SplashActivity;
import com.example.myapplication.util.ApplicationHolder;
import com.example.myapplication.util.SharedPreference;



public class GcmIntentService extends IntentService {


    public static boolean isService =false;

    public GcmIntentService() {
        super("GcmIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        sendNotification(intent.getStringExtra(MyFirebaseMessagingServiece.REQUEST_STRING));
    }
    private NotificationManager mNotificationManager;
    NotificationCompat.Builder builder;
    private void sendNotification(String message) {
        mNotificationManager = (NotificationManager)
                this.getSystemService(Context.NOTIFICATION_SERVICE);

        Intent startIntent =new Intent(this, SplashActivity.class);
        startIntent.putExtra("hi",true);
        startIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent contentIntent = PendingIntent.getActivity(this, (int) System.currentTimeMillis(),
                startIntent, 0);

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.logo_circle_notification)
                        .setContentTitle("BCFC")
                        .setStyle(new NotificationCompat.BigTextStyle()
                                .bigText(message))
                        .setContentText(message).setAutoCancel(true)
                        .setSound(Settings.System.DEFAULT_NOTIFICATION_URI);


        mBuilder.setContentIntent(contentIntent);
        mNotificationManager.notify((int) System.currentTimeMillis(), mBuilder.build());
        // mNotificationManager.cancelAll();
        // mNotificationManager.cancel(NOTIFICATION_ID);
    }

    public void sendNotification1(String body){
        Intent resultIntent;

        SharedPreference sharedPreference=new SharedPreference(this);
        sharedPreference.getPrefValue(ApplicationHolder.USERNAME);
        if(sharedPreference.getPrefValue(ApplicationHolder.USERNAME).equalsIgnoreCase("")){
            resultIntent = new Intent(this, LoginActivity.class);
        }else{
            resultIntent = new Intent(this, NotificationsActivity.class);
        }

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
// Adds the back stack
        stackBuilder.addParentStack(NotificationsActivity.class);
// Adds the Intent to the top of the stack
        stackBuilder.addNextIntent(resultIntent);
// Gets a PendingIntent containing the entire back stack
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder notificationBuilder=new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.logo_circle_notification)
                .setContentTitle("Life Saver")
                .setContentText(body)
                .setAutoCancel(true)
                .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
                .setContentIntent(resultPendingIntent);




        NotificationManager notificationManager=(NotificationManager)this.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify((int) System.currentTimeMillis(),notificationBuilder.build());

    }
}
