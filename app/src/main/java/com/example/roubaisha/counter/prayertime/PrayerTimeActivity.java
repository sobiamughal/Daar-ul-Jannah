package com.example.roubaisha.counter.prayertime;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.roubaisha.counter.R;

import org.json.JSONException;
import org.json.JSONObject;

public class PrayerTimeActivity extends AppCompatActivity {

    private static final String TAG = "PrayerTimeActivity";

    String url;
    // Tag used to cancel the request
    String tag_json_obj = "json_obj_req";
    //Progress Dialog
    ProgressDialog pDialog;

    TextView mFajrTv,mZuharTv,mAsrTv,mMaghribTv,mIshaTv,mLocationTv,mDateTv;
    EditText mSearchEt;
    Button mSearchBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prayer_time);

        getSupportActionBar().setTitle("Prayer Time");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mFajrTv     = (TextView) findViewById(R.id.fajrTv);
        mZuharTv    = (TextView) findViewById(R.id.zuharTv);
        mAsrTv      = (TextView) findViewById(R.id.asrTv);
        mMaghribTv  = (TextView) findViewById(R.id.maghribTv);
        mIshaTv     = (TextView) findViewById(R.id.ishaTv);
        mLocationTv = (TextView) findViewById(R.id.locationTv);
        mDateTv     = (TextView) findViewById(R.id.dateTv);
        mSearchEt   = (EditText) findViewById(R.id.searchEt);
        mSearchBtn  = (Button)   findViewById(R.id.searchBtn);


        mSearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //get text from editText
                String mLocation = mSearchEt.getText().toString().trim();
                //validate if it is null or not
                if (mLocation.isEmpty()){
                    Toast.makeText(PrayerTimeActivity.this, "Please enter location", Toast.LENGTH_SHORT).show();
                }else{
                    url = "http://muslimsalat.com/"+mLocation+".json?key=b2d880ae06d9e5a5cc7088d1ae0b0158";
                    searchLocation();
                }
            }
        });







    }

    private void searchLocation() {

        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.show();


        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                url, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        //get data from JSON
                        try {
                            //get location
                            String country = response.get("country").toString();
                            String state = response.get("state").toString();
                            String city = response.get("city").toString();
                            String location = country+", "+state+", "+city;
                            //get date
                            String date = response.getJSONArray("items").getJSONObject(0).get("date_for").toString();
                            //get names and timings
                            String mFajr = response.getJSONArray("items").getJSONObject(0).get("fajr").toString();
                            String mZuhar = response.getJSONArray("items").getJSONObject(0).get("dhuhr").toString();
                            String mAsr = response.getJSONArray("items").getJSONObject(0).get("asr").toString();
                            String mMaghrib = response.getJSONArray("items").getJSONObject(0).get("maghrib").toString();
                            String mIsha = response.getJSONArray("items").getJSONObject(0).get("isha").toString();

                            //set this data to textView
                            mFajrTv.setText(mFajr);
                            mZuharTv.setText(mZuhar);
                            mAsrTv.setText(mAsr);
                            mMaghribTv.setText(mMaghrib);
                            mIshaTv.setText(mIsha);
                            mLocationTv.setText(location);
                            mDateTv.setText(date);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        pDialog.hide();
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                Toast.makeText(PrayerTimeActivity.this,"Error",Toast.LENGTH_SHORT).show();
                // hide the progress dialog
                pDialog.hide();
            }
        });

// Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);
    }
}
