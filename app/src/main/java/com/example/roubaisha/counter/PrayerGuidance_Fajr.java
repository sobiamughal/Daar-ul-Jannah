package com.example.roubaisha.counter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class PrayerGuidance_Fajr extends Fragment {
    private static final String TAG = "PrayerGuidanceActivity";

    private Button btnfajr2;

    public PrayerGuidance_Fajr(){

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: started.");
        View view = inflater.inflate(R.layout.fragment_prayer_guidance_fajr, container, false);

        Button btnfajr1 =(Button)  view.findViewById(R.id.pgfajrbtn1);
        btnfajr1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
            }
        });


        return view;
    }
}
