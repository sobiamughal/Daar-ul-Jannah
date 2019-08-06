package com.example.roubaisha.counter.Services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.widget.Toast;

public class RestartPrayerReminderService extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
//
//        Intent intent1 = new Intent(f
//        ' -0987y t6rewq   context, PrayerReminderService.class);
//        context.startService(intent1);


//        Toast.makeText(context, "Notification Action Receiver", Toast.LENGTH_LONG).show();
//        LunchTimeNotifier.enqueueWork(context, intent);

        Toast.makeText(context, "Broadcast Receiver Called", Toast.LENGTH_LONG).show();

        Intent intent1 = new Intent(context, PrayerReminderService.class);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            context.startForegroundService(intent1);
        else
            context.startService(intent1);

//        context.startService(new Intent(context, PrayerReminderService.class));
    }
}