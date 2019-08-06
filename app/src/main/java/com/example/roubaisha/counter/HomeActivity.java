package com.example.roubaisha.counter;

import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.example.roubaisha.counter.Helper.DatabaseHelper;
import com.example.roubaisha.counter.Helper.PrayerAPIHelper;
import com.example.roubaisha.counter.Services.PrayerReminderService;
import com.example.roubaisha.counter.Services.RestartPrayerReminderService;
import com.example.roubaisha.counter.Services.RestartTravelDetectionService;
import com.example.roubaisha.counter.Services.SoundService;
import com.example.roubaisha.counter.Services.TravelDetectionService;

import org.json.JSONObject;

import java.util.Calendar;
import java.util.Locale;

import static android.app.PendingIntent.FLAG_UPDATE_CURRENT;

public class HomeActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    String[] listitem;

    private static final long START_TIME_IN_MILLIS = 1800000;

    private TextView mTextViewCountDown;
    private Button mButtonStartPause;
    private Button mButtonReset;

    private CountDownTimer mCountDownTimer;

    private boolean mTimerRunning;

    private long mTimeLeftInMillis;
    private long mEndTime;

//    private static final int NOTIF_ID = 3;
//    private static final String ChannelId = "3";
//    private static Context context;
//    private void createNotificationChannel() {
//        // Create the NotificationChannel, but only on API 26+ because
//        // the NotificationChannel class is new and not in the support library
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            CharSequence name = "PrayerReminderChannel";
//            String description = "PrayerReminderChannel";
//            int importance = NotificationManager.IMPORTANCE_DEFAULT;
//            NotificationChannel channel = new NotificationChannel(ChannelId, name, importance);
//            channel.setDescription(description);
//            // Register the channel with the system; you can't change the importance
//            // or other notification behaviors after this
//            NotificationManager notificationManager = getSystemService(NotificationManager.class);
//            notificationManager.createNotificationChannel(channel);
//        }
//    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Intent intent2 = new Intent(HomeActivity.this, TravelDetectionService.class);
        startService(intent2);

        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavView_Bar);
        mTextViewCountDown = findViewById(R.id.text_view_countdown);
        mButtonStartPause = findViewById(R.id.button_start_pause);
        mButtonReset = findViewById(R.id.button_reset);

        if (!isMyServiceRunning(TravelDetectionService.class)) {
//            RestartTravelDetectionService objRestartTravelDetectionService = new RestartTravelDetectionService();
//            this.registerReceiver(objRestartTravelDetectionService, new IntentFilter());
            Intent intent = new Intent(HomeActivity.this, RestartTravelDetectionService.class);
            sendBroadcast(intent);
        }
        if (!isMyServiceRunning(PrayerReminderService.class)) {
//            RestartPrayerReminderService objRestartPrayerReminderService = new RestartPrayerReminderService();
//            this.registerReceiver(objRestartPrayerReminderService, new IntentFilter());
            Intent intent = new Intent(HomeActivity.this, RestartPrayerReminderService.class);
            sendBroadcast(intent);
        }
//        context = getApplicationContext();
//        createNotificationChannel();
//        Intent objIntent1 = new Intent(context, StopSound.class);
//
//        objIntent1.setFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS|Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
//        PendingIntent objPendingIntent1 =
//                PendingIntent.getActivity(context, 1, objIntent1, FLAG_UPDATE_CURRENT);
//        NotificationCompat.Builder notificationBuilder =
//                new NotificationCompat.Builder(context, ChannelId)
//                        .setChannelId(ChannelId)
//                        .setOngoing(true)
//                        .setContentTitle("Vehicle Detection")
//                        .setContentText("Please Recite Dua-e-Safar")
//                        .setSmallIcon(R.drawable.circle_icon)
//                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
//                        .setContentIntent(objPendingIntent1)
//                        .addAction(R.drawable.circle_icon, "Stop", objPendingIntent1);
//        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
//        notificationManager.notify(NOTIF_ID, notificationBuilder.build());
//
//        Intent intent1 = new Intent(context, SoundService.class);
//        intent1.putExtra("nameid", 1);
//        startService(intent1);

//        Calendar calender = Calendar.getInstance();
//        calender.set(Calendar.HOUR, 2);
//        calender.set(Calendar.MINUTE, 06);
//        calender.set(Calendar.SECOND, 0);
//
//
//        Intent intent3 = new Intent(HomeActivity.this, NotificationActionReceiver.class);
//        PendingIntent pendingIntent = PendingIntent.getBroadcast(HomeActivity.this, 1, intent3, PendingIntent.FLAG_UPDATE_CURRENT);
//
//        AlarmManager alarmManager =  (AlarmManager) getSystemService(ALARM_SERVICE);
//        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calender.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);

//        Intent intent1 = new Intent(this, PrayerReminderService.class);
//        startService(intent1);

//        int ResponseId = this.getIntent().getIntExtra("Response", -1);
//        if(ResponseId != -1)
//        {
//            Intent homeIntent = new Intent(Intent.ACTION_MAIN);
//            homeIntent.addCategory( Intent.CATEGORY_HOME );
//            homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//            startActivity(homeIntent);
//            System.exit(0);
//        }

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {

                    case R.id.ic_home:

                        break;
                    case R.id.ic_tasbih:
                        Intent intent1 = new Intent(HomeActivity.this, TasbihActivity.class);
                        startActivity(intent1);
                        break;
                    case R.id.ic_prayerg:
                        Intent intent2 = new Intent(HomeActivity.this, PrayerGuidance.class);
                        startActivity(intent2);
                        break;
                    case R.id.ic_more:
                        Intent intent3 = new Intent(HomeActivity.this, MoreActivity.class);
                        startActivity(intent3);
                        break;
                }
                return false;
            }
        });

        mButtonStartPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        mButtonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetTimer();
            }
        });

        PrayerAPIHelper.RetrievePrayerTimings(HomeActivity.this);
        showStartDialog();
        updateCountDownText();

    }

    @Override
    protected void onStop() {
        super.onStop();

        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        editor.putLong("millisLeft", mTimeLeftInMillis);
        editor.putBoolean("timerRunning", mTimerRunning);
        editor.putLong("endTime", mEndTime);

        editor.apply();

        if (mCountDownTimer != null) {
            mCountDownTimer.cancel();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);

        mTimeLeftInMillis = prefs.getLong("millisLeft", START_TIME_IN_MILLIS);
        mTimerRunning = prefs.getBoolean("timerRunning", false);

        updateCountDownText();
        updateButtons();

        if (mTimerRunning) {
            mEndTime = prefs.getLong("endTime", 0);
            mTimeLeftInMillis = mEndTime - System.currentTimeMillis();

            if (mTimeLeftInMillis < 0) {
                mTimeLeftInMillis = 0;
                mTimerRunning = false;
                updateCountDownText();
                updateButtons();
            } else {
                startTimer();
            }
        }
    }
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//
//        ActivityManager activityManager = (ActivityManager) getApplicationContext()
//                .getSystemService(Context.ACTIVITY_SERVICE);
//
//        activityManager.moveTaskToFront(getTaskId(), 0);
//    }

    private void showStartDialog() {
        listitem = new String[]{"Muslim", "Muslimah"};
        new AlertDialog.Builder(this)
                .setTitle("Select Gender")
                .setSingleChoiceItems(listitem, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (mTimerRunning) {
                            pauseTimer();
                        } else {
                            startTimer();
                        }
                        dialog.dismiss();
                    }
                }).create().show();


        //SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        //SharedPreferences.Editor editor = prefs.edit();
        //editor.putBoolean("firstStart", false);
        // editor.apply();
    }

    private void updateCountDownText() {
        int minutes = (int) (mTimeLeftInMillis / 1000) / 60;
        int seconds = (int) (mTimeLeftInMillis / 1000) % 60;

        String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);

        mTextViewCountDown.setText(timeLeftFormatted);
    }

    private void startTimer() {
        mEndTime = System.currentTimeMillis() + mTimeLeftInMillis;

        mCountDownTimer = new CountDownTimer(mTimeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTimeLeftInMillis = millisUntilFinished;
                updateCountDownText();
            }

            @Override
            public void onFinish() {
                mTimerRunning = false;
                updateButtons();
            }
        }.start();

        mTimerRunning = true;
        updateButtons();
    }

    private void pauseTimer() {
        mCountDownTimer.cancel();
        mTimerRunning = false;
        updateButtons();

    }

    private void resetTimer() {
        mTimeLeftInMillis = START_TIME_IN_MILLIS;
        updateCountDownText();
        updateButtons();
    }

    private void updateButtons() {
        if (mTimerRunning) {
            mButtonReset.setVisibility(View.INVISIBLE);
            mButtonStartPause.setText("Pause");
        } else {
            mButtonStartPause.setText("Start");

            if (mTimeLeftInMillis < 1000) {
                mButtonStartPause.setVisibility(View.INVISIBLE);
            } else {
                mButtonStartPause.setVisibility(View.INVISIBLE);
            }

            if (mTimeLeftInMillis < START_TIME_IN_MILLIS) {
                mButtonReset.setVisibility(View.INVISIBLE);
            } else {
                mButtonReset.setVisibility(View.INVISIBLE);
            }
        }
    }

    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }
}
