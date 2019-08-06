package com.example.roubaisha.counter.Services;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;
import android.widget.Toast;

import com.example.roubaisha.counter.NoResponse;
import com.example.roubaisha.counter.NotificationActivity;
import com.example.roubaisha.counter.R;
import com.example.roubaisha.counter.StopAzaan;
import com.example.roubaisha.counter.StopSound;
import com.example.roubaisha.counter.YesResponse;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import static android.app.PendingIntent.FLAG_UPDATE_CURRENT;

public class NotifierService extends Service {

    private static final int NOTIF_ID = 2;
    private static final String ChannelId = "3";
    private static Context context;

    @Override
    public int onStartCommand(Intent intent, int flags, int startid) {
        context = this;
        createNotificationChannel();
        String PrayerName = "Fajar";
        int PrayerId = 1;
        Intent objNoIntent = new Intent(NotifierService.context, NoResponse.class);
        Intent objYesIntent = new Intent(NotifierService.context, YesResponse.class);
        Intent objpauseIntent = new Intent(NotifierService.context, StopAzaan.class);
        objNoIntent.putExtra("ResponseTypeId", 0);
        objNoIntent.putExtra("PrayerId", PrayerId);

        PendingIntent objNoPendingIntent =
                PendingIntent.getActivity(NotifierService.context, 1, objNoIntent, FLAG_UPDATE_CURRENT);
        PendingIntent objYesPendingIntent =
                PendingIntent.getActivity(NotifierService.context, 1, objYesIntent, FLAG_UPDATE_CURRENT);
        PendingIntent objPausePendingIntent =
                PendingIntent.getActivity(NotifierService.context, 1, objpauseIntent, FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(NotifierService.context, ChannelId)
                        .setChannelId(ChannelId)
//                        .setOngoing(true)
                        .setContentTitle("Prayer Alert - " + PrayerName)
                        .setContentText("It's " + PrayerName + ". Time to offer " + PrayerName + ".")
                        .setSmallIcon(R.drawable.circle_icon)
                        .setContentIntent(objNoPendingIntent)
                        .setContentIntent(objYesPendingIntent)
                        .setContentIntent(objPausePendingIntent)
                        .addAction(R.drawable.circle_icon, "No, will perform later", objNoPendingIntent)
                        .addAction(R.drawable.circle_icon, "I'm Offering Salah", objYesPendingIntent)
                        .addAction(R.drawable.circle_icon, "Pause Azaan", objPausePendingIntent)
                        .setAutoCancel(true);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(NotifierService.context);
        notificationManager.notify(NOTIF_ID, notificationBuilder.build());
        Intent intent3 = new Intent(NotifierService.context, SoundService.class);
        intent3.putExtra("nameid", 2);
        startService(intent3);
        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "PrayerReminderChannel";
            String description = "PrayerReminderChannel";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(ChannelId, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

}
