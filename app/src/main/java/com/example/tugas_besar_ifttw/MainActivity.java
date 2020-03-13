package com.example.tugas_besar_ifttw;


import android.content.Context;
import android.net.wifi.WifiManager;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.view.View;
import android.content.Intent;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private TabLayout mainTabLayout;
    private ViewPager mainViewPager;
    private ViewPagerAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        this.mainTabLayout = findViewById(R.id.mainTabLayout);
        this.mainViewPager = findViewById(R.id.mainViewPager);

        // Apply fragment from fragment_active_routine.xml and fragment_inactive_routine.xml
        loadFragments();

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Start new activity
                Intent newIntent = new Intent(MainActivity.this, AddRoutineActivity.class);
                MainActivity.this.startActivity(newIntent);
            }
        });

        ControllerSensorRoutine.startSensorService(getApplicationContext());
    }

    protected void onResume() {
        super.onResume();
        loadFragments();
    }

    protected void loadFragments() {
        adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new FragmentActiveRoutine(), "Active");
        adapter.addFragment(new FragmentInactiveRoutine(), "Inactive");
        this.mainViewPager.setAdapter(adapter);
        this.mainTabLayout.setupWithViewPager(mainViewPager);
    }

}
