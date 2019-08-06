package com.example.roubaisha.counter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.roubaisha.counter.Adapter.PendingPrayerAdapter;
import com.example.roubaisha.counter.Helper.DatabaseHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.prolificinteractive.materialcalendarview.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class PendingPrayersActivity extends AppCompatActivity {

//    private RecyclerView recyclerView;
//    private RecyclerView.Adapter mAdapter;
//    private RecyclerView.LayoutManager layoutManager;
    DatabaseHelper db;
    MaterialCalendarView materialCalendarView;
    CalendarDay calendarDay;
    TextView PrayerName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pending_prayers);

        materialCalendarView = findViewById(R.id.calendarView);
        materialCalendarView.setSelectionMode(MaterialCalendarView.SELECTION_MODE_MULTIPLE);
        PrayerName = findViewById(R.id.TvPrayerName);
        int PrayerId = getIntent().getIntExtra("PrayerId",0);
        if(PrayerId == 1){
            PrayerName.setText("Fajr");
        }else if(PrayerId == 2){
            PrayerName.setText("Zuhar");
        }else if(PrayerId == 3){
            PrayerName.setText("Asr");
        }else if(PrayerId == 4){
            PrayerName.setText("Maghrib");
        }else if(PrayerId == 5){
            PrayerName.setText("Isha");
        }else{

        }

            db = new DatabaseHelper(this);
        JSONArray objJSONArray = null;
        objJSONArray = db.GetPendingPrayers(PrayerId);

        if(objJSONArray.length() > 0 )
        {
            for(int i=0; i < objJSONArray.length(); i++)
            {
                try {
                    JSONObject objJSONObject = objJSONArray.getJSONObject(i);
                    String CreatedAt = objJSONObject.getString("CreatedAt");

                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date dtCreatedAt = dateFormat.parse(CreatedAt);
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(dtCreatedAt);

                    int Year = calendar.get(Calendar.YEAR);
                    int Month = calendar.get(Calendar.MONTH) + 1;
                    int Day = calendar.get(Calendar.DAY_OF_MONTH);

                    calendarDay = CalendarDay.from(Year, Month, Day);
                    materialCalendarView.setDateSelected(calendarDay,true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }


//        [{"PendingPrayerId":"1","PrayerId":"1","CreatedAt":"2019-06-16 18:10:05","IsQaza":"0","IsPerformed":"0"}]

        PendingPrayerAdapter adapter = new PendingPrayerAdapter(objJSONArray);


//        List<EventDay> events = new ArrayList<>();
//        Calendar calendar = Calendar.getInstance();
//        calendar.set(2019, 7, 5);
//        events.add(new EventDay(calendar));
//
//        CalendarView calendarView = (CalendarView) findViewById(R.id.calendarView);
//        calendarView.setEvents(events);
//        try {
//            calendarView.setDate(calendar);
//        } catch (OutOfDateRangeException e) {
//            e.printStackTrace();
//        }

//        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
//        recyclerView.setHasFixedSize(true);
//        layoutManager = new LinearLayoutManager(this);
//        recyclerView.setLayoutManager(layoutManager);

        // specify an adapter (see also next example)
//        mAdapter = new PendingPrayerAdapter();
//        recyclerView.setAdapter(mAdapter);
//

//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        recyclerView.setAdapter(adapter);

    }
}
