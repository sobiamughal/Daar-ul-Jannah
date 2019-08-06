package com.example.roubaisha.counter.Services;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

import com.example.roubaisha.counter.R;

public class SoundService extends Service {
    MediaPlayer player;
    int audioFile1 = R.raw.safar_ke_iraday_ki_dua;
    int audioFile2 = R.raw.azan;
    int audioFile3 = R.raw.dua_after_azaan;
    public static boolean IsRunning = false;
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if(!SoundService.IsRunning) {
            SoundService.IsRunning = true;
            int nameid = intent.getIntExtra("nameid", -1);
            if (nameid == 1) {
                player = MediaPlayer.create(this, audioFile1);
            } else if (nameid == 2) {
                player = MediaPlayer.create(this, audioFile2);
            } else if (nameid == 3) {
                player = MediaPlayer.create(this, audioFile3);
            }
            player.setLooping(false); //set looping
            player.start();
        }

        return Service.START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        IsRunning = false;
        player.stop();
        player.release();
        stopSelf();
        super.onDestroy();
    }
}
