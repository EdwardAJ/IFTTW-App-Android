package com.example.tugas_besar_ifttw;

import android.app.NotificationManager;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;


public class AddRoutineActivity extends AppCompatActivity {
    private NotificationManager mNotificationManager;
    private static final String PRIMARY_CHANNEL_ID = "primary_notification_channel";

    private TabLayout tabLayout;
    private ViewPager addRoutinePager;
    public Context context;

    private TabLayout tabLayoutTwo;
    private ViewPager addRoutinePagerTwo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int orientation = getResources().getConfiguration().orientation;

        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            setContentView(R.layout.activity_add_routine_land);

            this.tabLayout = findViewById(R.id.tabLayout);
            this.tabLayoutTwo = findViewById(R.id.tabLayoutTwo);

            // For fragments
            this.addRoutinePager = findViewById(R.id.viewPager);
            this.addRoutinePagerTwo = findViewById(R.id.viewPagerTwo);

            // Apply fragment from fragment_news.xml and fragment_timer.xml
            ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
            adapter.addFragment(new FragmentTimer(), "Timer");

            this.addRoutinePager.setAdapter(adapter);
            this.tabLayout.setupWithViewPager(addRoutinePager);

            ViewPagerAdapter adapterTwo = new ViewPagerAdapter(getSupportFragmentManager());
            adapterTwo.addFragment(new FragmentNews(), "News");
            adapterTwo.addFragment(new FragmentSensor(), "Sensor");

            this.addRoutinePagerTwo.setAdapter(adapterTwo);
            this.tabLayoutTwo.setupWithViewPager(addRoutinePagerTwo);

        } else {
            setContentView(R.layout.activity_add_routine);

            this.tabLayout = findViewById(R.id.tabLayout);

            // For fragments
            this.addRoutinePager = findViewById(R.id.viewPager);
            // Apply fragment from fragment_news.xml and fragment_timer.xml
            ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
            adapter.addFragment(new FragmentTimer(), "Timer");
            adapter.addFragment(new FragmentNews(), "News");
            adapter.addFragment(new FragmentSensor(), "Sensor");

            this.addRoutinePager.setAdapter(adapter);
            this.tabLayout.setupWithViewPager(addRoutinePager);
        }
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }
}
