package com.example.roubaisha.counter;

import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.roubaisha.counter.Helper.DatabaseHelper;
import com.example.roubaisha.counter.Helper.PrayerAPIHelper;
import com.example.roubaisha.counter.Services.NotifierService;
import com.example.roubaisha.counter.Services.PrayerReminderService;
import com.example.roubaisha.counter.Services.RestartPrayerReminderService;
import com.example.roubaisha.counter.Services.RestartTravelDetectionService;
import com.example.roubaisha.counter.Services.TravelDetectionService;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import gr.net.maroulis.library.EasySplashScreen;

import static android.app.PendingIntent.FLAG_CANCEL_CURRENT;


public class SplashScreenActivity extends AppCompatActivity {

    private static DatabaseHelper db;
    private PendingIntent pendingIntent;

    SimpleDateFormat inFormat, outFormat, outFormatHour, outFormatMinute;
    String[] Prayers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        try {
//            inFormat = new SimpleDateFormat("hh:mm aa");
//            outFormat = new SimpleDateFormat("HH:mm");
//            outFormatHour = new SimpleDateFormat("HH");
//            outFormatMinute = new SimpleDateFormat("mm");
//            Prayers = new String[5];
//            db = new DatabaseHelper(SplashScreenActivity.this);

//            AlarmManager alarmManager =(AlarmManager) getSystemService(ALARM_SERVICE);
//            Calendar calendar = Calendar.getInstance();
//            calendar.setTimeInMillis(System.currentTimeMillis());
//
//            PrayerAPIHelper.RetrievePrayerTimings(this);
//            Intent serviceintent = new Intent(getApplicationContext(), NotificationActionReceiver.class);
//            pendingIntent = PendingIntent.getBroadcast(SplashScreenActivity.this,0, serviceintent, FLAG_CANCEL_CURRENT);
//
//            for (int i = 0; i < 5; i++) {
//                JSONObject objJSONObject = SplashScreenActivity.db.GetAllPrayerTiming(Integer.toString(i + 1));
//                if (objJSONObject != null) {
//                    Prayers[i] = objJSONObject.getString("PrayerTime");
//                    Prayers[i] = outFormat.format(inFormat.parse(Prayers[i]));
//
//                    calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(outFormatHour.format(outFormat.parse(Prayers[i]))));
//                    calendar.set(Calendar.MINUTE, Integer.parseInt(outFormatMinute.format(outFormat.parse(Prayers[i]))));
//                    calendar.set(Calendar.SECOND, 0);
//
//                    alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
//                }
//            }
//
//            Toast.makeText(this, "Notifications Set Successfully", Toast.LENGTH_LONG).show();
//        }catch (JSONException e) {
//            e.printStackTrace();
//        }
//        catch (Exception ex)
//        {
//
//        }

        Intent intent1 = new Intent(SplashScreenActivity.this, PrayerReminderService.class);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) startForegroundService(intent1);
        else startService(intent1);

        EasySplashScreen config = new EasySplashScreen(SplashScreenActivity.this)
                .withFullScreen()
                .withTargetActivity(HomeActivity.class)
                .withSplashTimeOut(3000)
                .withBackgroundColor(Color.parseColor("#dfb96e"))
                .withAfterLogoText("Daar-ul-Jannah")
                .withLogo(R.mipmap.ic_launcher_round);

        config.getAfterLogoTextView().setTextColor(Color.WHITE);

        View easySplashScreen = config.create();
        setContentView(easySplashScreen);
    }

}
