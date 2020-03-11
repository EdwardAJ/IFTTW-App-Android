package com.example.tugas_besar_ifttw;

import android.app.NotificationManager;
import android.content.Context;
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

    public AddRoutineActivity() {
        this.context = this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_routine);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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
}
