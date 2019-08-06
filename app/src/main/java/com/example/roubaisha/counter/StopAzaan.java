package com.example.roubaisha.counter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.roubaisha.counter.Helper.DatabaseHelper;
import com.example.roubaisha.counter.Services.SoundService;

public class StopAzaan extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent1 = new Intent(StopAzaan.this, SoundService.class);
        stopService(intent1);
        Intent _intent = new Intent(StopAzaan.this, HomeActivity.class);
        startActivity(_intent);
    }
}
