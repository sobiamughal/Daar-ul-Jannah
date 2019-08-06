package com.example.roubaisha.counter;

import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.widget.Toast;

import com.bumptech.glide.util.Util;
import com.example.roubaisha.counter.Services.NotifierService;
import com.example.roubaisha.counter.Services.PrayerReminderService;

public class NotificationActionReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "Notification Action Receiver", Toast.LENGTH_LONG).show();
        //LunchTimeNotifier.enqueueWork(context, intent);
//        Intent intent1 = new Intent(context, NotifierService.class);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
//        {
//            context.startForegroundService(intent1);
//        }
//        else context.startService(intent1);
    }
}
