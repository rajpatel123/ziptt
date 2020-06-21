package com.ziploan.team.verification_module.services;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.ziploan.team.R;
import com.ziploan.team.SplashActivity;

/**
 * Created by ZIploan-Nitesh on 3/8/2017.
 * Currently This file is not being used.
 */

public class NotifyService extends Service {

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        startNotification("Your session has been expired, Please login again");
        return START_STICKY;
    }

    public static void setAlertNotification(Context context,String notifyTime){
        if(notifyTime!=null){
            Intent myIntent = new Intent(context , NotifyService.class);
            AlarmManager alarmManager = (AlarmManager)context.getSystemService(ALARM_SERVICE);
            PendingIntent pendingIntent = PendingIntent.getService(context, 0, myIntent, 0);
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, Long.parseLong(notifyTime), AlarmManager.INTERVAL_DAY , pendingIntent);  //set repeating every 24 hours
        }
    }

    /**
     * This will create a notification which will diplay the status of file upload
     */
    public void startNotification(String message){

        Intent intent = new Intent(this, SplashActivity.class);
        PendingIntent pIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        Notification n  = new Notification.Builder(this)
                .setContentTitle("ZipLoan")
                .setContentText(message)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentIntent(pIntent)
                .setAutoCancel(true).build();
        int notificationId = (int)(System.currentTimeMillis()/1000);
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(notificationId, n);
    }
}