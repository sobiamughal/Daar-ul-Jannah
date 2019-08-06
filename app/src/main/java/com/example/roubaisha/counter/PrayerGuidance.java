package com.example.roubaisha.counter;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

public class PrayerGuidance extends AppCompatActivity {
    private static final String TAG = "PrayerGuidance";

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prayer_guidance);
        Log.d(TAG, "onCreate: started");


        Log.d(TAG, "onCreate: toolabr call");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        Log.d(TAG, "onCreate: set mViewPager adapter");

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_prayer_guidance, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            Log.d(TAG, "newInstance: started");
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            Log.d(TAG, "newInstance: end");
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            Log.d(TAG, "onCreateView: started");
            View rootView = null;
            switch(getArguments().getInt(ARG_SECTION_NUMBER)){
                case 1:
                    Log.d(TAG, "onCreateView: case1");
                    //do something
                    rootView = inflater.inflate(R.layout.fragment_prayer_guidance_fajr, container, false);
                    break;
                case 2:
                    Log.d(TAG, "onCreateView: case2");
                    //load another page
                    rootView = inflater.inflate(R.layout.fragment_prayer_guidance_zuhar, container, false);
                    break;
                case 3:
                    Log.d(TAG, "onCreateView: case3");
                    //load another page
                    rootView = inflater.inflate(R.layout.fragment_prayer_guidance_asr, container, false);
                    break;
                case 4:
                    Log.d(TAG, "onCreateView: case4");
                    //load another page
                    rootView = inflater.inflate(R.layout.fragment_prayer_guidance_maghrib, container, false);
                    break;
                case 5:
                    Log.d(TAG, "onCreateView: case5");
                    //load another page
                    rootView = inflater.inflate(R.layout.fragment_prayer_guidance_isha, container, false);
                    break;
            }

            return rootView;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {
        private static final String TAG = "SectionsPagerAdapter";

        public SectionsPagerAdapter(FragmentManager fm) {

            super(fm);
            Log.d(TAG, "SectionsPagerAdapter: started");
        }

        @Override
        public Fragment getItem(int position) {
            Log.d(TAG, "getItem: started");

            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            Log.d(TAG, "getCount: started");
            // Show character3 total pages.
            return 5;
        }
    }
}
