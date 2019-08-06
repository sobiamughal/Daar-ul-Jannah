package com.example.roubaisha.counter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;

public class PendingPrayerLayerActivity extends AppCompatActivity {


    private CardView CvFajr;
    private CardView CvZuhar;
    private CardView CvAsr;
    private CardView CvMaghrib;
    private CardView CvIsha;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pending_prayer_layer);

        CvFajr = findViewById(R.id.CvFajr);
        CvZuhar = findViewById(R.id.CvZuhar);
        CvAsr = findViewById(R.id.CvAsr);
        CvMaghrib = findViewById(R.id.CvMaghrib);
        CvIsha = findViewById(R.id.CvIsha);

        //OnClickListeners
        CvFajr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPendingPrayersActivity(1);
            }
        });
        CvZuhar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPendingPrayersActivity(2);
            }
        });
        CvAsr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPendingPrayersActivity(3);
            }
        });
        CvMaghrib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPendingPrayersActivity(4);
            }
        });
        CvIsha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPendingPrayersActivity(5);
            }
        });

    }
    public void openPendingPrayersActivity(int PrayerId){
        Intent intent = new Intent(this, PendingPrayersActivity.class);
        intent.putExtra("PrayerId", PrayerId);
        startActivity(intent);

    }
}
