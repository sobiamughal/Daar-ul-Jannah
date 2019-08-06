package com.example.roubaisha.counter.Helper;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.roubaisha.counter.prayertime.AppController;

import org.json.JSONException;
import org.json.JSONObject;

import static android.support.constraint.Constraints.TAG;

public class PrayerAPIHelper {

    public static String PrayerAPIURL = "http://muslimsalat.com/karachi.json?key=b2d880ae06d9e5a5cc7088d1ae0b0158";
    public static String APIIdentifier = "Call01";
    private static DatabaseHelper objDBHelper;

    public static void RetrievePrayerTimings(Context context) {
        objDBHelper = new DatabaseHelper(context);
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                PrayerAPIURL, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject TimingsObject = response.getJSONArray("items").getJSONObject(0);

                            String Fajr = TimingsObject.get("fajr").toString().replace("am", "AM").replace("pm", "PM");
                            String Zuhr = TimingsObject.get("dhuhr").toString().replace("am", "AM").replace("pm", "PM");
                            String Asar = TimingsObject.get("asr").toString().replace("am", "AM").replace("pm", "PM");
                            String maghrib = TimingsObject.get("maghrib").toString().replace("am", "AM").replace("pm", "PM");
                            String isha = TimingsObject.get("isha").toString().replace("am", "AM").replace("pm", "PM");

                            if (!Fajr.equals("")) {
                                if(objDBHelper.IsPrayerTimingExist(1))
                                    objDBHelper.UpdatePrayerTiming(1, "Fajr", Fajr);
                                else
                                    objDBHelper.InsertPrayerTiming(1, "Fajr", Fajr);
                            }

                            if (!Zuhr.equals("")) {
                                if(objDBHelper.IsPrayerTimingExist(2))
                                    objDBHelper.UpdatePrayerTiming(2, "Zuhr", Zuhr);
                                else
                                    objDBHelper.InsertPrayerTiming(2, "Zuhr", Zuhr);
                            }

                            if (!Asar.equals("")) {
                                if(objDBHelper.IsPrayerTimingExist(3))
                                    objDBHelper.UpdatePrayerTiming(3, "Asar", Asar);
                                else
                                    objDBHelper.InsertPrayerTiming(3, "Asar", Asar);
                            }

                            if (!maghrib.equals("")) {
                                if(objDBHelper.IsPrayerTimingExist(4))
                                    objDBHelper.UpdatePrayerTiming(4, "Maghrib", maghrib);
                                else
                                    objDBHelper.InsertPrayerTiming(4, "Maghrib", maghrib);
                            }

                            if (!isha.equals("")) {
                                if(objDBHelper.IsPrayerTimingExist(5))
                                    objDBHelper.UpdatePrayerTiming(5, "Isha", isha);
                                else
                                    objDBHelper.InsertPrayerTiming(5, "Isha", isha);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
            }
        });

        AppController.getInstance().addToRequestQueue(jsonObjReq, APIIdentifier);
    }

}
