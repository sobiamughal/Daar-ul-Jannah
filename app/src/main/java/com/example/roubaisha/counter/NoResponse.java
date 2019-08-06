package com.example.roubaisha.counter;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.android.volley.Response;
import com.example.roubaisha.counter.Helper.DatabaseHelper;
import com.example.roubaisha.counter.Services.SoundService;

public class NoResponse extends Activity {

    DatabaseHelper db;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = new DatabaseHelper(NoResponse.this);

        int PrayerId = getIntent().getIntExtra("PrayerId",-1);
        int ResponseTypeId = getIntent().getIntExtra("ResponseTypeId",-1);
        int PendingPrayerId = getIntent().getIntExtra("PendingPrayerId",-1);

        if(ResponseTypeId == 0)
        {
            if(PrayerId != -1)
            {
                db.InsertPendingPrayer(PrayerId);
                Toast.makeText(this,"OK, I am here to remind you again :)", Toast.LENGTH_LONG).show();
            }
        }
        else if(ResponseTypeId == 1)
        {
            if(PendingPrayerId != -1)
            {
                db.MarkPrayerAsQaza(PendingPrayerId);
                Toast.makeText(this,"Oh? Ok then, i have saved your Salah in Pending Prayers Menu.", Toast.LENGTH_LONG).show();
            }
        }

        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.cancel(1);
        Intent intent1 = new Intent(NoResponse.this, SoundService.class);
        stopService(intent1);
        Intent intent = new Intent(NoResponse.this, HomeActivity.class);
        intent.putExtra("Response", 1);
        startActivity(intent);

    }

}
