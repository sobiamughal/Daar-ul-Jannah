package com.example.roubaisha.counter.Services;

import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.example.roubaisha.counter.Helper.DatabaseHelper;
import com.example.roubaisha.counter.HomeActivity;
import com.example.roubaisha.counter.MainActivity;
import com.example.roubaisha.counter.NotificationActionReceiver;
import com.example.roubaisha.counter.R;
import com.example.roubaisha.counter.StopSound;

import static android.app.PendingIntent.FLAG_UPDATE_CURRENT;

public class TravelDetectionService extends Service implements SensorEventListener {

    public  final class ServiceHandler extends Handler {
        public ServiceHandler(Looper looper) {
            super(looper);
        }
        @Override
        public void handleMessage(Message msg) {
            // Normally we would do some work here, like download a file.
            // For our sample, we just sleep for 5 seconds.
            try {
                Thread.sleep(500000000);
            } catch (InterruptedException e) {
                // Restore interrupt status.
                Thread.currentThread().interrupt();
            }
            // Stop the service using the startId, so that we don't stop
            // the service in the middle of handling another job
            stopSelf(msg.arg1);
        }
    }


    private static final int NOTIF_ID = 3;
    private static final String ChannelId = "3";
    private static Context context;

    private Looper serviceLooper;
    private ServiceHandler serviceHandler;

    private SensorManager sensorMan;
    private Sensor accelerometer;

    private float[] mGravity;
    private float mAccel;
    private float mAccelCurrent;
    private float mAccelLast;

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

    @Override
    public void onCreate(){
        createNotificationChannel();
        context = getApplicationContext();
        sensorMan = (SensorManager)getSystemService(SENSOR_SERVICE);
        accelerometer = sensorMan.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
        mAccel = 0.00f;
        mAccelCurrent = SensorManager.GRAVITY_EARTH;
        mAccelLast = SensorManager.GRAVITY_EARTH;

        //BackgroundService
        HandlerThread thread = new HandlerThread("ServiceStartArguments",
                Process.THREAD_PRIORITY_URGENT_AUDIO);
        thread.start();
        // Get the HandlerThread's Looper and use it for our Handler
        serviceLooper = thread.getLooper();
        serviceHandler = new ServiceHandler(serviceLooper);


//        //////NotificationWork//////
//        createNotificationChannel("2");
//        Notification notification = null;
//        //Snooze intent
//        RemoteViews contentView = new RemoteViews(this.getPackageName(), R.layout.activity_notify_handler);
//        Intent snoozeIntent = new Intent(this, MainActivity.class);
//        snoozeIntent.putExtra("do_action","play");
//        contentView.setOnClickPendingIntent(R.id.action_settings, PendingIntent.getActivity(this, 0, snoozeIntent, 0));
//        PendingIntent snoozePendingIntent =
//                PendingIntent.getBroadcast(this, 0, snoozeIntent, 0);
//        //
//        Intent viewIntent = new Intent(this, MainActivity.class);
//        PendingIntent viewPendingIntent =
//                PendingIntent.getActivity(this, 0, viewIntent, 0);
//        NotificationCompat.Builder notificationBuilder =
//                new NotificationCompat.Builder(this,"2")
//                        .setChannelId("2")
//                        .setContentTitle(getString(R.string.app_name))
////                        .setOngoing(true)
//                        .setVisibility(NotificationCompat.VISIBILITY_SECRET)
//                        .setContentText("Service is running background")
//                        .setSmallIcon(R.drawable.circle_icon)
//                        .setContentIntent(viewPendingIntent)
////                        .setContentIntent(snoozePendingIntent)
//                        .addAction(R.drawable.circle_icon, "play", snoozePendingIntent);
//        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
//        notificationManager.notify(NOTIF_ID, notificationBuilder.build());
//
//        //BackgroundService
//        HandlerThread thread = new HandlerThread("ServiceStartArguments",
//                Process.THREAD_PRIORITY_URGENT_AUDIO);
//        thread.start();
//        // Get the HandlerThread's Looper and use it for our Handler
//        serviceLooper = thread.getLooper();
//        serviceHandler = new ServiceHandler(serviceLooper);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if(sensorMan != null){
            if(accelerometer != null){
                sensorMan.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_GAME);
            }
        }
        Message msg = serviceHandler.obtainMessage();
        msg.arg1 = startId;
        serviceHandler.sendMessage(msg);
//        super.onStartCommand(intent, flags, startId);
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        stopService(new Intent(context, SoundService.class));
//        super.onDestroy();
        Intent intent1 = new Intent(context, TravelDetectionService.class);
        startService(intent1);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if(isMyServiceRunning(SoundService.class)){
//            Toast.makeText(this, "Service is Already running", Toast.LENGTH_LONG).show();
        }else{
            if (event.sensor.getType() == Sensor.TYPE_LINEAR_ACCELERATION){
                mGravity = event.values.clone();
                // Shake detection
                float x = mGravity[0];
                float y = mGravity[1];
                float z = mGravity[2];
                mAccelLast = mAccelCurrent;
                mAccelCurrent = Float.parseFloat(Math.sqrt(x*x + y*y + z*z)+ "" );
                float delta = mAccelCurrent - mAccelLast;
                mAccel = mAccel * 0.9f + delta;
                // Make this higher or lower according to how much
                // motion you want to detect
                if(mAccel > 20) {
                    // do something
                    try {
                        //SoundService
//                    ToneGenerator toneGen1 = new ToneGenerator(AudioManager.STREAM_MUSIC, 100);
//                    toneGen1.startTone(ToneGenerator.TONE_CDMA_PIP, 150);
                        Intent objIntent1 = new Intent(context, StopSound.class);

//                        objIntent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        PendingIntent objPendingIntent1 =
                                PendingIntent.getActivity(context, 1, objIntent1, FLAG_UPDATE_CURRENT);
                        NotificationCompat.Builder notificationBuilder =
                                new NotificationCompat.Builder(context, ChannelId)
                                        .setChannelId(ChannelId)
                                        .setOngoing(true)
                                        .setContentTitle("Vehicle Detection")
                                        .setContentText("Please Recite Dua-e-Safar")
                                        .setSmallIcon(R.drawable.circle_icon)
                                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                                        .setContentIntent(objPendingIntent1)
                                        .addAction(R.drawable.circle_icon, "Stop", objPendingIntent1);
                        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
                        notificationManager.notify(NOTIF_ID, notificationBuilder.build());

                        Intent intent = new Intent(TravelDetectionService.this, SoundService.class);
                        intent.putExtra("nameid", 1);
                        startService(intent);
                    }catch (Exception e) {
                    }
                }
            }
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // required method
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
