package com.example.roubaisha.counter;

import android.app.NotificationManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.roubaisha.counter.Services.NotifierService;
import com.example.roubaisha.counter.Services.SoundService;

public class NotificationActivity extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        //System.out.println(getIntent().getExtras().getString("Action"));
        System.out.println("in notification activity");
        if(getIntent().getExtras() != null) {
            System.out.println("got something in extras");

            System.out.println(getIntent().getExtras().getString("Action"));
            if (getIntent().getExtras().getString("Action").equals("1")) {
                Toast.makeText(this, "Action One Completed",
                        Toast.LENGTH_LONG).show();
            }
            else if(getIntent().getExtras().getString("Action").equals("2")) {
                System.out.println("it was 2");
                Toast.makeText(this, "Action Two Completed",
                        Toast.LENGTH_LONG).show();

                Intent myService = new Intent(this, SoundService.class);
                stopService(myService);

                NotificationManager manager = (NotificationManager) this.getSystemService(NOTIFICATION_SERVICE);
                manager.cancelAll();
                Intent myService1 = new Intent(this, NotifierService.class);
                stopService(myService1);
            }
        }
        getIntent().putExtra("Action", "");
    }
}
