package com.example.roubaisha.counter;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.roubaisha.counter.Services.SoundService;

public class StopSound extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = new Intent(StopSound.this, SoundService.class);
        stopService(intent);
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.cancel(3);


        Intent _intent = new Intent(StopSound.this, HomeActivity.class);
        _intent.putExtra("Response", 1);
        startActivity(_intent);

    }
}
