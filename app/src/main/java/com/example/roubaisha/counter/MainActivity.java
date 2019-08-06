package com.example.roubaisha.counter;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.provider.Telephony;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.roubaisha.counter.Services.PrayerReminderService;
import com.example.roubaisha.counter.Services.TravelDetectionService;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(0);
        menuItem.setChecked(true);*/



        bottomNavigationView=(BottomNavigationView)findViewById(R.id.bottomNavView_Bar);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){

                    case R.id.ic_home:

                        break;
                    case R.id.ic_tasbih:
                        Intent intent1 = new Intent(MainActivity.this, TasbihActivity.class);
                        startActivity(intent1);

                        break;
                    case R.id.ic_more:
                        Intent intent2 = new Intent(MainActivity.this, MoreActivity.class);
                        startActivity(intent2);
                        break;
                }


                return false;
            }
        });
    }
}
