package com.example.roubaisha.counter.Helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "daruljannah.db";
    public static final String PrayerTimings = "PrayerTimings";
    public static final String PendingPrayers = "PendingPrayers";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(
                "create table " + PrayerTimings + " " +
                        "(PrayerId integer primary key, " +
                        "PrayerName text not null, " +
                        "PrayerTime text not null, " +
                        "PrayerTime24 text not null, " +
                        "CreatedAt text not null," +
                        "UpdatedAt text)"
        );

        db.execSQL(
                "create table " + PendingPrayers + " " +
                        "(PendingPrayerId integer primary key AUTOINCREMENT, " +
                        "PrayerId integer not null, " +
                        "CreatedAt text not null," +
                        "IsQaza integer DEFAULT 0," +
                        "QazaMarkedAt text," +
                        "PerformedAt text," +
                        "IsPerformed DEFAULT 0)"
        );

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS " + PrayerTimings);
        onCreate(db);
    }



    public boolean IsPrayerTimingExist(Integer PrayerId) {
        boolean IsExist = false;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from PrayerTimings where PrayerId=" + PrayerId + "", null);
        if (res.getCount() > 0)
            IsExist = true;
        return IsExist;
    }

    public boolean InsertPrayerTiming(Integer PrayerId, String PrayerName, String PrayerTime) {
        boolean IsInserted = false;
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = new Date();
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put("PrayerId", PrayerId);
            contentValues.put("PrayerName", PrayerName);
            contentValues.put("PrayerTime", PrayerTime);
            contentValues.put("PrayerTime24", Convert12to24Hr(PrayerTime));
            contentValues.put("CreatedAt", dateFormat.format(date));
            long s = db.insert("PrayerTimings", null, contentValues);
            IsInserted = true;
        } catch (Exception ex) {
            Log.i("Exception", ex.getMessage());
        }
        return IsInserted;
    }

    public boolean UpdatePrayerTiming(Integer PrayerId, String PrayerName, String PrayerTime) {
        boolean IsUpdated = false;
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = new Date();
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put("PrayerName", PrayerName);
            contentValues.put("PrayerTime", PrayerTime);
            contentValues.put("UpdatedAt", dateFormat.format(date));
            db.update("PrayerTimings", contentValues, "PrayerId = ? ", new String[]{Integer.toString(PrayerId)});
        } catch (Exception ex) {
            Log.i("Exception", ex.getMessage());
        }
        return IsUpdated;
    }

    public JSONObject GetPrayerTiming(String PrayerTime) {
        JSONObject objJSONObject = null;
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.query("PrayerTimings", null, "PrayerTime = ?", new String[]{PrayerTime}, null, null, null);
            cursor.moveToFirst();
            while (cursor.isAfterLast() == false) {
                objJSONObject = new JSONObject();
                int totalColumn = cursor.getColumnCount();
                for (int i = 0; i < totalColumn; i++) {
                    if (cursor.getColumnName(i) != null) {
                        try {
                            objJSONObject.put(cursor.getColumnName(i), cursor.getString(i));
                        } catch (Exception e) {
                            Log.d("Test", e.getMessage());
                        }
                    }
                }
                cursor.moveToNext();
            }
        } catch (Exception ex) {
            Log.i("Exception", ex.getMessage());
        }
        return objJSONObject;
    }

    public JSONObject GetAllPrayerTiming(String PrayerId) {
        JSONObject objJSONObject = null;
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.query("PrayerTimings", null, "PrayerId = ?", new String[]{PrayerId}, null, null, null);
            cursor.moveToFirst();
            while (cursor.isAfterLast() == false) {
                objJSONObject = new JSONObject();
                int totalColumn = cursor.getColumnCount();
                for (int i = 0; i < totalColumn; i++) {
                    if (cursor.getColumnName(i) != null) {
                        try {
                            objJSONObject.put(cursor.getColumnName(i), cursor.getString(i));
                        } catch (Exception e) {
                            Log.d("Test", e.getMessage());
                        }
                    }
                }
                cursor.moveToNext();
            }
        } catch (Exception ex) {
            Log.i("Exception", ex.getMessage());
        }
        return objJSONObject;
    }

    public JSONArray GetPendingPrayers(int PrayerId) {
        JSONArray objJSONArray = null;
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.query("PendingPrayers", null, "IsPerformed = 0 and PrayerId = ?", new String[]{Integer.toString(PrayerId)}, null, null, null);
            cursor.moveToFirst();
            objJSONArray = new JSONArray();
            while (cursor.isAfterLast() == false) {
                JSONObject objJSONObject = new JSONObject();
                int totalColumn = cursor.getColumnCount();
                for (int i = 0; i < totalColumn; i++) {
                    if (cursor.getColumnName(i) != null) {
                        try {
                            objJSONObject.put(cursor.getColumnName(i), cursor.getString(i));
                        } catch (Exception e) {
                            Log.d("Test", e.getMessage());
                        }
                    }
                }
                objJSONArray.put(objJSONObject);
                cursor.moveToNext();
            }
        } catch (Exception ex) {
            Log.i("Exception", ex.getMessage());
        }
        return objJSONArray;
    }

    public JSONObject GetPreviousPendingPrayer() {
        JSONObject objJSONObject = null;
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");

            Date date = new Date();
            String FormattedDate = dateFormat.format(date);

            Calendar now = Calendar.getInstance();
            now.add(Calendar.MINUTE, 30);
            Date currentTime = now.getTime();
            String timewith30mins = timeFormat.format(Date.parse(currentTime.toString()));
            timewith30mins = timewith30mins.substring(0, timewith30mins.length() - 3);
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery("\n" +
                    "Select pt.PrayerName, pt.PrayerTime, pt.PrayerTime24, pp.* from PrayerTimings pt\n" +
                    "CROSS JOIN PendingPrayers pp\n" +
                    "where pt.PrayerId = pp.PrayerId \n" +
                    "and pp.PrayerId = (Select PrayerId-1 from PrayerTimings where PrayerTime24 like ?)\n" +
                    "and pp.CreatedAt like ?\n" +
                    "and pp.IsQaza = 0\n" +
                    "and pp.IsPerformed = 0;", new String[]{timewith30mins + "%", FormattedDate + "%"});
            cursor.moveToFirst();
            while (cursor.isAfterLast() == false) {
                objJSONObject = new JSONObject();
                int totalColumn = cursor.getColumnCount();
                for (int i = 0; i < totalColumn; i++) {
                    if (cursor.getColumnName(i) != null) {
                        try {
                            objJSONObject.put(cursor.getColumnName(i), cursor.getString(i));
                        } catch (Exception e) {
                            Log.d("Test", e.getMessage());
                        }
                    }
                }
                cursor.moveToNext();
            }
        } catch (Exception ex) {
            Log.i("Exception", ex.getMessage());
        }
        return objJSONObject;
    }

    public boolean DeletePrayerTiming() {
        boolean IsDeleted = false;
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            db.delete("PrayerTimings", null, null);
            IsDeleted = true;
        } catch (Exception ex) {
            Log.i("Exception", ex.getMessage());
        }
        return IsDeleted;
    }

    public boolean InsertPendingPrayer(Integer PrayerId) {
        boolean IsInserted = false;
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = new Date();
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put("PrayerId", PrayerId);
            contentValues.put("CreatedAt", dateFormat.format(date));
            long s = db.insert("PendingPrayers", null, contentValues);
            IsInserted = true;
        } catch (Exception ex) {
            Log.i("Exception", ex.getMessage());
        }
        return IsInserted;
    }

    public boolean MarkPrayerAsQaza(Integer PendingPrayerId) {
        boolean IsUpdated = false;
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = new Date();
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put("IsQaza", 1);
            contentValues.put("QazaMarkedAt", dateFormat.format(date));
            db.update("PendingPrayers", contentValues, "PendingPrayerId = ? ", new String[]{Integer.toString(PendingPrayerId)});
        } catch (Exception ex) {
            Log.i("Exception", ex.getMessage());
        }
        return IsUpdated;
    }

    public boolean PerformPendingPrayer(Integer PendingPrayerId) {
        boolean IsUpdated = false;
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = new Date();
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put("PerformedAt", dateFormat.format(date));
            contentValues.put("IsPerformed", 1);
            db.update("PendingPrayers", contentValues, "PendingPrayerId = ? ", new String[]{Integer.toString(PendingPrayerId)});
        } catch (Exception ex) {
            Log.i("Exception", ex.getMessage());
        }
        return IsUpdated;
    }

    public String Convert12to24Hr(String TimeIn12Hr) throws ParseException {
        DateFormat df = new SimpleDateFormat("hh:mm aa");
        DateFormat outputformat = new SimpleDateFormat("HH:mm:ss");
        Date date = null;
        String output = null;
        //Converting the input String to Date
        date = df.parse(TimeIn12Hr);
        //Changing the format of date and storing it in String
        output = outputformat.format(date);
        return output;
    }
}